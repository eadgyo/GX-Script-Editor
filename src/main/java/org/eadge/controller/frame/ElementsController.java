package org.eadge.controller.frame;

import org.eadge.ConstantsView;
import org.eadge.controller.Actions;
import org.eadge.model.global.MyTransferableElement;
import org.eadge.model.global.SelectionModel;
import org.eadge.model.script.GXElement;
import org.eadge.model.script.GXLayer;
import org.eadge.model.script.Script;
import org.eadge.view.ElementsView;
import org.eadge.view.MyFrame;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by eadgyo on 17/02/17.
 *
 * Control elements tree view
 */
public class ElementsController
{

    private ElementsView elementsView;

    private SelectionModel selectionModel;
    private Script         script;
    private MyFrame        myFrame;
    private Actions        a;


    public ElementsController(MyFrame myFrame, Script script, SelectionModel selectionModel, Actions a)
    {
        this.myFrame = myFrame;
        this.elementsView = myFrame.elementsView;
        this.selectionModel = selectionModel;
        this.script = script;
        this.a = a;

        // Set the right model
        elementsView.elementsTree.setModel(script.getLayeredScript());

        // Create action
        a.addLayerAction      = new AddLayerAction();
        a.removeNodeAction    = new RemoveNodeAction();
        a.hideLayerAction     = new HideLayerAction();
        a.propertyLayerAction = new PropertyLayerAction();

        elementsView.addLayerButton.setAction(a.addLayerAction);
        elementsView.removeLayerButton.setAction(a.removeNodeAction);
        elementsView.hideLayerButton.setAction(a.hideLayerAction);
        elementsView.propertyLayerButton.setAction(a.propertyLayerAction);

        // Create tree selection listener
        SelectedElementAction selectedElementAction = new SelectedElementAction();
        elementsView.elementsTree.addTreeSelectionListener(selectedElementAction);

        elementsView.elementsTree.addMouseListener(new MouseElementsListener());
        elementsView.elementsTree.addMouseMotionListener(new MouseMotionElementsListener());
        elementsView.elementsTree.setSelectionModel(selectionModel);

        // Add drop support
        ElementTransferHandler transferHandler = new ElementTransferHandler();
        elementsView.elementsTree.setTransferHandler(transferHandler);
        myFrame.sceneView.setTransferHandler(transferHandler);

        elementsView.elementsTree.setDragEnabled(true);

        createLayerProperties();
    }

    private void createLayerProperties()
    {
        OkPropertyAction     okPropertyAction     = new OkPropertyAction();
        CancelPropertyAction cancelPropertyAction = new CancelPropertyAction();

        elementsView.layerPropertiesDialog.okButton.setAction(okPropertyAction);
        elementsView.layerPropertiesDialog.cancelButton.setAction(cancelPropertyAction);
    }

    public class AddLayerAction extends AbstractAction
    {
        public AddLayerAction()
        {
            super(ConstantsView.NAME_ADD_LAYER);
            this.putValue(SHORT_DESCRIPTION, ConstantsView.DESC_ADD_LAYER);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            GXLayer selectedLayer = elementsView.getSelectedLayer();
            script.addLayer(new GXLayer("Layer"), selectedLayer);
            script.callObservers();
        }
    }


    public class RemoveNodeAction extends AbstractAction
    {
        public RemoveNodeAction()
        {
            super(ConstantsView.NAME_REMOVE_NODE);
            this.putValue(SHORT_DESCRIPTION, ConstantsView.DESC_REMOVE_NODE);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            MutableTreeNode selectedNode = elementsView.getSelectedNode();
            script.removeNode(selectedNode);
            selectionModel.clearSelection();
        }
    }


    public class HideLayerAction extends AbstractAction
    {
        public HideLayerAction()
        {
            super(ConstantsView.NAME_HIDE_LAYER);
            this.putValue(SHORT_DESCRIPTION, ConstantsView.DESC_HIDE_LAYER);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            GXLayer selectedLayer = elementsView.getSelectedLayer();
            selectedLayer.setDisplayed(!selectedLayer.isDisplayed());
            script.callObservers();
        }
    }


    public class PropertyLayerAction extends AbstractAction
    {
        public PropertyLayerAction()
        {
            super(ConstantsView.NAME_PROPERTY_LAYER);
            this.putValue(SHORT_DESCRIPTION, ConstantsView.DESC_PROPERTY_LAYER);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            GXLayer selectedLayer = elementsView.getSelectedLayer();
            boolean hasChanged = elementsView.layerPropertiesDialog.open(selectedLayer);
            if (hasChanged)
                script.callObservers();
        }
    }

    public class SelectedElementAction implements TreeSelectionListener
    {
        @Override
        public void valueChanged(TreeSelectionEvent treeSelectionEvent)
        {
            MutableTreeNode selectedNode = elementsView.getSelectedNode();

            boolean isSelected = (selectedNode != null);
            a.removeNodeAction.setEnabled(isSelected);

            boolean isLayerSelected = isSelected && (selectedNode instanceof GXLayer);
            a.propertyLayerAction.setEnabled(isLayerSelected);
            a.hideLayerAction.setEnabled(isLayerSelected);
        }
    }

    public class OkPropertyAction extends AbstractAction
    {
        public OkPropertyAction()
        {
            super(ConstantsView.NAME_OK);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            elementsView.layerPropertiesDialog.setResponse(true);
            elementsView.layerPropertiesDialog.setVisible(false);
        }
    }

    public class CancelPropertyAction extends AbstractAction
    {
        public CancelPropertyAction()
        {
            super(ConstantsView.NAME_CANCEL);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            elementsView.layerPropertiesDialog.setResponse(false);
            elementsView.layerPropertiesDialog.setVisible(false);
        }
    }


    public class ElementTransferHandler extends TransferHandler implements DragGestureListener,
            DragSourceMotionListener, DropTargetListener
    {
        public ElementTransferHandler()
        {
            super();
        }

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
            GXLayer selectedLayer = elementsView.getSelectedLayer();
            GXElement cloned    = (GXElement) element.clone();

            double elementX = myFrame.sceneView.getSceneModel().computeXInScene(0);
            double elementY = myFrame.sceneView.getSceneModel().computeYInScene(0);

            cloned.setX(elementX);
            cloned.setY(elementY);

            // Get selected node
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

        @Override
        public void dragGestureRecognized(DragGestureEvent dragGestureEvent)
        {
            System.out.println("Recognised");
        }

        public void dragEnter(DragSourceDragEvent dragSourceDragEvent)
        {
            System.out.println("Drag enter");
        }

        public void dragOver(DragSourceDragEvent dragSourceDragEvent)
        {
            System.out.println("Drag over");
        }

        public void dropActionChanged(DragSourceDragEvent dragSourceDragEvent)
        {
            System.out.println("Drop Action");
        }

        public void dragExit(DragSourceEvent dragSourceEvent)
        {
            System.out.println("Drag exit");
        }

        public void dragDropEnd(DragSourceDropEvent dragSourceDropEvent)
        {
            System.out.println("Drag end");
        }

        @Override
        public void dragMouseMoved(DragSourceDragEvent dragSourceDragEvent)
        {
            System.out.println("Motion");
        }

        @Override
        public void dragEnter(DropTargetDragEvent dropTargetDragEvent)
        {
            System.out.println("Dragged enter");
        }

        @Override
        public void dragOver(DropTargetDragEvent dropTargetDragEvent)
        {
            System.out.println("Dragged out");
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent)
        {
            System.out.println("Dragg action");
        }

        @Override
        public void dragExit(DropTargetEvent dropTargetEvent)
        {
            System.out.println("Dragg exit");
        }

        @Override
        public void drop(DropTargetDropEvent dropTargetDropEvent)
        {

        }
    }

    private class MouseElementsListener implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            // If mouse is not on an element
            if (elementsView.elementsTree.getPathForLocation(e.getX(), e.getY()) == null) {

                // Set as root selection
                selectionModel.setSelectedElements(new HashSet<MutableTreeNode>());
                elementsView.elementsTree.clearSelection();
            }
            else {
                selectionModel.setSelectedElements(getSelectedNodes());
            }
            // Update selection
            selectionModel.callObservers();
        }



        @Override
        public void mouseReleased(MouseEvent mouseEvent)
        {


            if (selectionModel.hasSelectedElements() && mouseEvent.getSource() == elementsView.elementsTree)
            {
                // Get the inserted GXLayer
                GXLayer insertedLayer = elementsView.getSelectedLayer();

                // Get the last saved path and move to this
                Collection<MutableTreeNode> selectedElements = selectionModel.getSelectedElements();
                for (MutableTreeNode selectedElement : selectedElements)
                {
                    // If is not trying to move on parent in child node
                    if (!selectionModel.isParentOrEqual(selectedElement, insertedLayer) && selectionModel.isChanging
                            (insertedLayer, selectedElement))
                    {
                        script.detachNode(selectedElement);
                        script.addNode(selectedElement, insertedLayer);
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent)
        {
        }

    }

    private class MouseMotionElementsListener implements MouseMotionListener
    {
        @Override
        public void mouseDragged(MouseEvent mouseEvent)
        {
            boolean leftMouseButton = SwingUtilities.isLeftMouseButton(mouseEvent);
            if (leftMouseButton)
            {
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();

                // Update selected layer
                TreePath pathForLocation = elementsView.elementsTree.getPathForLocation(x, y);
                selectionModel.setSelectionPath(pathForLocation);

                // Update the on dragg element
//                selectionModel.setOnDragElement((MutableTreeNode)((pathForLocation == null) ? script.getLayeredScript().getRoot()
//                                                                                            : pathForLocation.getLastPathComponent()));

                // Update selection
                selectionModel.callObservers();
            }
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent)
        {

        }
    }

    private Collection<MutableTreeNode> getSelectedNodes()
    {
        TreePath[] selectionPaths = elementsView.elementsTree.getSelectionPaths();
        HashSet<MutableTreeNode> mutableTreeNodes = new HashSet<>();

        if (selectionPaths == null)
            return mutableTreeNodes;

        start: for (TreePath treePath : selectionPaths)
        {
            // Check if parent is selected
            for (TreePath isParentPath : selectionPaths)
            {
                if (isParentPath != treePath)
                {
                    if (treePath.isDescendant(isParentPath))
                    {
                        continue start;
                    }
                }
            }

            mutableTreeNodes.add((MutableTreeNode) treePath.getLastPathComponent());
        }

        return mutableTreeNodes;
    }
}
