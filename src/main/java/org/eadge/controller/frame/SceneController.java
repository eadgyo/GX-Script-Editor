package org.eadge.controller.frame;

import org.eadge.controller.Actions;
import org.eadge.model.frame.SceneModel;
import org.eadge.model.global.ConnectionModel;
import org.eadge.model.global.MyTransferableElement;
import org.eadge.model.global.SelectionModel;
import org.eadge.model.script.GXElement;
import org.eadge.model.script.GXLayer;
import org.eadge.model.script.Script;
import org.eadge.renderer.ElementFinder;
import org.eadge.renderer.EntryFinder;
import org.eadge.renderer.Rect2D;
import org.eadge.renderer.Rect2DInter;
import org.eadge.view.MyFrame;
import org.eadge.view.SceneView;

import javax.swing.*;
import javax.swing.tree.MutableTreeNode;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eadgyo on 17/02/17.
 *
 * Handle scene support
 */
public class SceneController
{
    private Script script;
    private MyFrame    myFrame;
    private SceneView  sceneView;

    private SceneModel      sceneModel;
    private SelectionModel  selectionModel;
    private ConnectionModel connectionModel;

    private ElementFinder elementFinder;
    private EntryFinder entryFinder;



    public SceneController(Script script,
                           MyFrame myFrame,
                           SelectionModel selectionModel,
                           SceneModel sceneModel,
                           ElementFinder elementFinder,
                           EntryFinder entryFinder, Actions a)
    {
        this.script = script;
        this.myFrame = myFrame;
        this.sceneView = myFrame.sceneView;
        this.sceneModel = sceneModel;
        this.connectionModel = selectionModel.getConnectionModel();
        this.selectionModel = selectionModel;
        this.elementFinder = elementFinder;
        this.entryFinder = entryFinder;

        // Add drag and drop from view
        sceneView.setTransferHandler(new SceneTransferHandler());

        // Add mouse listener to this painter
        SceneMouseListener sceneMouseListener = new SceneMouseListener();
        sceneView.addMouseListener(sceneMouseListener);
        sceneView.addMouseMotionListener(sceneMouseListener);
        sceneView.addMouseWheelListener(sceneMouseListener);

        // Set model
        sceneView.setSceneModel(sceneModel);
        sceneView.setSelectionModel(selectionModel);
    }

    private class SceneTransferHandler extends TransferHandler
    {
        @Override
        public boolean importData(TransferSupport transferSupport)
        {
            if (!canImport(transferSupport))
                return false;

            GXElement element;
            try
            {
                element = (GXElement) transferSupport.getTransferable().getTransferData(MyTransferableElement.myElementFlavor);
            }
            catch (UnsupportedFlavorException | IOException e)
            {
                e.printStackTrace();
                return false;
            }

            // Add the element to the scene
            Point     dropPoint = transferSupport.getDropLocation().getDropPoint();
            GXElement cloned    = (GXElement) element.clone();

            double elementX = sceneView.getSceneModel().computeXInScene(dropPoint.getX());
            double elementY = sceneView.getSceneModel().computeYInScene(dropPoint.getY());

            cloned.setX(elementX);
            cloned.setY(elementY);

            // Get selected node
            GXLayer selectedLayer = myFrame.elementsView.getSelectedLayer();
            script.addEntity(cloned, selectedLayer);

            return true;
        }

        @Override
        public boolean canImport(TransferSupport transferSupport)
        {
            return transferSupport.isDataFlavorSupported(MyTransferableElement.myElementFlavor);
        }

        @Override
        public int getSourceActions(JComponent jComponent)
        {
            return COPY_OR_MOVE;
        }

        @Override
        protected Transferable createTransferable(JComponent jComponent)
        {
            return super.createTransferable(jComponent);
        }
    }

    private class SceneMouseListener implements MouseMotionListener, MouseListener, MouseWheelListener
    {
        private int lastMouseX, lastMouseY;

        public Rect2D createMouseRec(MouseEvent event, int size)
        {
            double mouseX = sceneModel.computeXInScene(event.getX());
            double mouseY = sceneModel.computeYInScene(event.getY());
            return new Rect2D(mouseX, mouseY, size, size);
        }

        public double getXinScene(MouseEvent event)
        {
            return sceneModel.computeXInScene(event.getX());
        }

        public double  getYinScene(MouseEvent event)
        {
            return sceneModel.computeYInScene(event.getY());
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent)
        {
            Rect2D mouseRect = createMouseRec(mouseEvent, 4 );

            if (mouseEvent.isControlDown() || mouseEvent.isShiftDown())
            {
                // Get the selected element
                MutableTreeNode node = elementFinder.retrieveFirstElementIn(mouseRect, selectionModel);

                if (node != null)
                {
                    // Si element déjà selectionné
                    if (selectionModel.contains(node))
                    {
                        // Deselectionne
                        selectionModel.removeSelectedElement(node);
                    }
                    else
                    {
                        // Ajoute à la selection
                        selectionModel.addSelectedElement(node);
                    }
                }
            }
            else
            {
                // Get the selected element
                MutableTreeNode node = elementFinder.retrieveFirstElementOut(mouseRect, selectionModel);

                if (node == null)
                {
                    selectionModel.setSelectedElements(new HashSet<MutableTreeNode>());
                }
                else if (!selectionModel.isNewSelection())
                {
                    selectionModel.setSelectedElements(node);
                }
            }

            selectionModel.setSelectionState(SelectionModel.SelectionState.NONE);

            lastMouseX = mouseEvent.getX();
            lastMouseY = mouseEvent.getY();

            script.callObservers();
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent)
        {
            lastMouseX = mouseEvent.getX();
            lastMouseY = mouseEvent.getY();

            connectionModel.setDesiring(true);

            // Check if it's selecting an entry
            if (!mouseEvent.isControlDown() && !mouseEvent.isShiftDown())
            {
                Rect2D mouseRect = createMouseRec(mouseEvent, 4 );
                MutableTreeNode node = elementFinder.retrieveFirstElementIn(mouseRect, selectionModel);

                if (node instanceof GXElement)
                {
                    GXElement gxElement = (GXElement) node;

                    // Check if it's connecting on entry
                    EntryFinder.EntryResult entryIndex = entryFinder.getEntryIndex(gxElement, mouseRect);
                    if (entryIndex.entryIndex != -1)
                    {
                        selectionModel.clearSelection();
                        selectionModel.setSelectedElements(gxElement);
                        selectionModel.setNewSelection(true);
                        selectionModel.setSelectionState(SelectionModel.SelectionState.CONNECTING);

                        // Selecting entry
                        if (mouseEvent.getButton() == MouseEvent.BUTTON1)
                        {
                            connectionModel.setStartIndex(entryIndex.entryIndex, entryIndex.isInput);
                            connectionModel.setDesiredPos(mouseRect.getCenterX(), mouseRect.getCenterY());
                        }
                        else
                        {
                            if (mouseEvent.getButton() == MouseEvent.BUTTON3)
                            {
                                script.disconnectEntityOnEntry(gxElement, entryIndex.isInput, entryIndex.entryIndex);
                            }
                            if (mouseEvent.getButton() == MouseEvent.BUTTON2 && entryIndex.isInput)
                            {
                                String optionValue = JOptionPane.showInputDialog("Option value", gxElement.getOptionValue(entryIndex
                                                                                                                   .entryIndex));
                                if (!optionValue.equals(""))
                                {
                                    gxElement.setOptionValue(entryIndex.entryIndex, optionValue);
                                }
                            }
                            selectionModel.setSelectionState(SelectionModel.SelectionState.NONE);
                        }
                    }
                    else
                    {
                        // Check if it's selecting one node
                        Set<MutableTreeNode> nodes = elementFinder.retrieveElements(mouseRect);
                        selectionModel.setNewSelection(!selectionModel.containsOne(nodes));
                        selectionModel.setSelectedElements(gxElement);
                    }
                }
                else if (node != null)
                {
                    // Check if it's selecting one node
                    Set<MutableTreeNode> nodes = elementFinder.retrieveElements(mouseRect);
                    selectionModel.setNewSelection(!selectionModel.containsOne(nodes));
                    selectionModel.setSelectedElements(node);
                }
                else
                {
                    selectionModel.clearSelection();
                }
            }

            script.callObservers();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent)
        {
            Rect2D mouseRect = createMouseRec(mouseEvent, 4 );

            // Get the selected element
            MutableTreeNode node = elementFinder.retrieveFirstElementIn(mouseRect, selectionModel);

            // If connecting
            if (selectionModel.isSelectionStateEquals(SelectionModel.SelectionState.CONNECTING))
            {
                // Create new bigger mouse rect
                mouseRect = createMouseRec(mouseEvent, 6);

                // If mouse is dragging toward another element
                if (node != null && node instanceof GXElement)
                {
                    GXElement onDragged = (GXElement) node;
                    selectionModel.setOnDragElement(onDragged);

                    // Check connecting on entry
                    EntryFinder.EntryResult entryIndex = entryFinder.getEntryIndex(onDragged, mouseRect);

                    // If entry is selected
                    if (entryIndex.entryIndex != -1)
                    {
                        connectionModel.setEndIndex(entryIndex.entryIndex, entryIndex.isInput);

                        GXElement selected = (GXElement) selectionModel.getFirstSelectedElement();

                        if (mouseEvent.getButton() == MouseEvent.BUTTON1)
                        {

                            // Check if MyGroupsOfElements connection can be made
                            boolean isValid = selected.canConnectOnEntry(connectionModel.isStartInput(),
                                                                         connectionModel.getStartIndex(),
                                                                         onDragged,
                                                                         connectionModel.isEndInput(),
                                                                         connectionModel.getEndIndex());
                            if (isValid)
                            {
                                script.connectEntities(selected,
                                                       connectionModel.isStartInput(),
                                                       connectionModel.getStartIndex(),
                                                       onDragged,
                                                       connectionModel.getEndIndex());
                            }
                        }
                    }
                }

                connectionModel.setDesiring(true);
                selectionModel.setNewSelection(false);
                selectionModel.clearDragElement();
            }

            selectionModel.setSelectionState(SelectionModel.SelectionState.NONE);

            lastMouseX = mouseEvent.getX();
            lastMouseY = mouseEvent.getY();

            selectionModel.updateSelectionPaths();
            script.callObservers();
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent)
        {
            //
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mouseDragged(MouseEvent ev)
        {
            Rect2D mouseRect = createMouseRec(ev, 4 );

            // Get the selected element
            MutableTreeNode node = elementFinder.retrieveFirstElementIn(mouseRect, selectionModel);

            // If connecting
            if (selectionModel.isSelectionStateEquals(SelectionModel.SelectionState.CONNECTING))
            {
                // Create new bigger mouse rect
                mouseRect = createMouseRec(ev, 6);

                // If mouse is dragging toward another element
                if (node != null && node instanceof GXElement)
                {
                    GXElement onDragged = (GXElement) node;
                    selectionModel.setOnDragElement(onDragged);

                    // Check connecting on entry
                    EntryFinder.EntryResult entryIndex = entryFinder.getEntryIndex(onDragged, mouseRect);

                    // If entry is selected
                    if (entryIndex.entryIndex != -1)
                    {
                        connectionModel.setDesiring(false);
                        connectionModel.setEndIndex(entryIndex.entryIndex, entryIndex.isInput);

                        GXElement selected = (GXElement) selectionModel.getFirstSelectedElement();

                        // Check if MyGroupsOfElements connection can be made
                        boolean isValid = selected.canConnectOnEntry(connectionModel.isStartInput(),
                                                   connectionModel.getStartIndex(),
                                                   onDragged,
                                                   connectionModel.isEndInput(),
                                                   connectionModel.getEndIndex());

                        selectionModel.setActionValid(isValid);
                    }
                    else
                    {
                        connectionModel.setDesiring(true);
                        selectionModel.setActionValid(true);
                        connectionModel.setDesiredPos(getXinScene(ev), getYinScene(ev));
                    }
                }
                else
                {
                    connectionModel.setDesiring(true);
                    selectionModel.setActionValid(true);
                    connectionModel.setDesiredPos(getXinScene(ev), getYinScene(ev));
                }
            }
            else
            {
                double translateX = sceneModel.computeHeightInScene(lastMouseX - ev.getX());
                double translateY = sceneModel.computeHeightInScene(lastMouseY - ev.getY());
                Set<MutableTreeNode> nodes = elementFinder.retrieveElements(mouseRect);
                if (selectionModel.hasSelectedElements())
                {
                    selectionModel.setSelectionState(SelectionModel.SelectionState.MOVING);
                    // Move all selected elements
                    Collection<MutableTreeNode> selectedElements = selectionModel.getSelectedElements();
                    for (MutableTreeNode selectedElement : selectedElements)
                    {
                        Rect2DInter rect2DInter = (Rect2DInter) selectedElement;
                        rect2DInter.translate(-translateX, -translateY);
                    }
                }
                else
                {
                    selectionModel.setSelectionState(SelectionModel.SelectionState.NONE);

                    // Translate the scene
                    sceneModel.translateX(-translateX);
                    sceneModel.translateY(-translateY);
                }
            }

            lastMouseX = ev.getX();
            lastMouseY = ev.getY();

            script.callObservers();
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            float FACTOR = 2;
            double factor;
            int wheelRotation = e.getWheelRotation();

            if (wheelRotation < 0)
                factor = FACTOR / -wheelRotation;
            else
                factor =  wheelRotation / FACTOR;

            sceneModel.scalePane(factor, e.getX(), e.getY());
            script.callObservers();
        }
    }

    private class SceneDragListener implements DragGestureListener, DragSourceListener{

        @Override
        public void dragGestureRecognized(DragGestureEvent dragGestureEvent)
        {
        }

        @Override
        public void dragEnter(DragSourceDragEvent dragSourceDragEvent)
        {
        }

        @Override
        public void dragOver(DragSourceDragEvent dragSourceDragEvent)
        {
        }

        @Override
        public void dropActionChanged(DragSourceDragEvent dragSourceDragEvent)
        {

        }

        @Override
        public void dragExit(DragSourceEvent dragSourceEvent)
        {

        }

        @Override
        public void dragDropEnd(DragSourceDropEvent dragSourceDropEvent)
        {

        }
    }
}
