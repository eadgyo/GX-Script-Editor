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
import java.awt.event.*;
import java.io.IOException;
import java.util.Collection;

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
            return new Rect2D(event.getX() - size / 2, event.getY() - size / 2, size, size);
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent)
        {
            Rect2D mouseRect = createMouseRec(mouseEvent, 4 );

            // Get the selected element
            MutableTreeNode node = elementFinder.retrieveFirstElement(mouseRect);
            selectionModel.setSelectionState(SelectionModel.SelectionState.NONE);

            if (mouseEvent.isControlDown())
            {
                if (node != null)
                {
                    // Si element déjà selectionné
                    if (selectionModel.contains(node))
                    {
                        // Deselectionne
                        selectionModel.addSelectedElement(node);
                    }
                    else
                    {
                        // Ajoute à la selection
                        selectionModel.removeSelectedElement(node);
                    }
                }
            }
            else
            {
                selectionModel.setSelectedElements(node);

                if (node instanceof GXElement)
                {
                    GXElement gxElement = (GXElement) node;

                    // Check if it's connecting on entry
                    EntryFinder.EntryResult entryIndex = entryFinder.getEntryIndex(gxElement, mouseRect);
                    if (entryIndex.entryIndex != -1)
                    {
                        selectionModel.setSelectionState(SelectionModel.SelectionState.CONNECTING);

                        if (mouseEvent.getButton() == MouseEvent.BUTTON1)
                        {
                            connectionModel.setStartIndex(entryIndex.entryIndex, entryIndex.isInput);
                        }
                        else if (mouseEvent.getButton() == MouseEvent.BUTTON2)
                        {
                            script.disconnectEntityOnEntry(gxElement, entryIndex.isInput, entryIndex.entryIndex);
                        }
                    }
                }
            }

            lastMouseX = mouseEvent.getX();
            lastMouseY = mouseEvent.getY();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent)
        {
            Rect2D mouseRect = createMouseRec(mouseEvent, 4 );

            // Get the selected element
            MutableTreeNode node = elementFinder.retrieveFirstElement(mouseRect);

            // If connecting
            if (selectionModel.isSelectionStateEquals(SelectionModel.SelectionState.CONNECTING))
            {
                // Create new bigger mouse rect
                mouseRect = createMouseRec(mouseEvent, 6);

                // If mouse is dragging toward another element
                if (node != null && node instanceof GXElement && selectionModel.contains(node))
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

            selectionModel.setSelectionState(SelectionModel.SelectionState.NONE);

            lastMouseX = mouseEvent.getX();
            lastMouseY = mouseEvent.getY();
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent)
        {
            Rect2D mouseRect = createMouseRec(mouseEvent, 4 );

            // Get the selected element
            MutableTreeNode node = elementFinder.retrieveFirstElement(mouseRect);

            // If connecting
            if (selectionModel.isSelectionStateEquals(SelectionModel.SelectionState.CONNECTING))
            {
                // Create new bigger mouse rect
                mouseRect = createMouseRec(mouseEvent, 6);

                // If mouse is dragging toward another element
                if (node != null && node instanceof GXElement && selectionModel.contains(node))
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
                        connectionModel.setDesiredPos(mouseEvent.getX(), mouseEvent.getY());
                    }
                }
                else
                {
                    connectionModel.setDesiring(true);
                    connectionModel.setDesiredPos(mouseEvent.getX(), mouseEvent.getY());
                }
            }
            else
            {
                double translateX = lastMouseX - mouseEvent.getX();
                double translateY = lastMouseY - mouseEvent.getY();

                if (selectionModel.hasSelectedElements())
                {
                    // Move all selected elements
                    Collection<MutableTreeNode> selectedElements = selectionModel.getSelectedElements();
                    for (MutableTreeNode selectedElement : selectedElements)
                    {
                        Rect2DInter rect2DInter = (Rect2DInter) selectedElement;
                        rect2DInter.translate(translateX, translateY);
                    }
                }
                else
                {
                    // Translate the scene
                    sceneModel.translateX(translateX);
                    sceneModel.translateY(translateY);
                }
            }

            lastMouseX = mouseEvent.getX();
            lastMouseY = mouseEvent.getY();
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent)
        {
            float FACTOR = 3;
            int wheelRotation = mouseWheelEvent.getWheelRotation();

            float desiredScale;
            float scale = sceneModel.getScale();

            if (wheelRotation < 0)
            {
                desiredScale = scale * FACTOR/ -wheelRotation;
            }
            else
            {
                desiredScale = scale * wheelRotation / FACTOR;
            }

            // Check if desired scale is valid
            if (desiredScale < 0.01f)
            {
                desiredScale = 0.01f;
            }
            else if (desiredScale > 5f)
            {
                desiredScale = 5f;
            }

            sceneModel.setScale(desiredScale);
        }
    }
}
