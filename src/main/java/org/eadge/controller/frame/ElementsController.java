package org.eadge.controller.frame;

import org.eadge.ConstantsView;
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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
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
    private PropertyLayerAction propertyLayerAction;
    private HideLayerAction hideLayerAction;
    private RemoveNodeAction removeNodeAction;
    private AddLayerAction addLayerAction;
    private ElementsView elementsView;

    private SelectionModel selectionModel;
    private Script script;
    private MyFrame myFrame;


    public ElementsController(MyFrame myFrame, Script script, SelectionModel selectionModel)
    {
        this.myFrame = myFrame;
        this.elementsView = myFrame.elementsView;
        this.selectionModel = selectionModel;

        // Set the right model
        elementsView.elementsTree.setModel(script.getLayeredScript());

        // Create action
        addLayerAction      = new AddLayerAction();
        removeNodeAction    = new RemoveNodeAction();
        hideLayerAction     = new HideLayerAction();
        propertyLayerAction = new PropertyLayerAction();

        elementsView.addLayerButton.setAction(addLayerAction);
        elementsView.removeLayerButton.setAction(removeNodeAction);
        elementsView.hideLayerButton.setAction(hideLayerAction);
        elementsView.propertyLayerButton.setAction(propertyLayerAction);

        // Create tree selection listener
        SelectedElementAction selectedElementAction = new SelectedElementAction();
        elementsView.elementsTree.addTreeSelectionListener(selectedElementAction);

        elementsView.elementsTree.addMouseListener(new MouseElementsListener());
        elementsView.elementsTree.addMouseMotionListener(new MouseMotionElementsListener());

        // Add drop support
        elementsView.setTransferHandler(new ElementTransferHandler());

        createLayerProperties();
    }

    private void createLayerProperties()
    {
        OkPropertyAction     okPropertyAction     = new OkPropertyAction();
        CancelPropertyAction cancelPropertyAction = new CancelPropertyAction();

        elementsView.layerPropertiesDialog.okButton.setAction(okPropertyAction);
        elementsView.layerPropertiesDialog.cancelButton.setAction(cancelPropertyAction);
    }

    private class AddLayerAction extends AbstractAction
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
            script.addLayer(new GXLayer(), selectedLayer);

        }
    }


    private class RemoveNodeAction extends AbstractAction
    {
        public RemoveNodeAction()
        {
            super(ConstantsView.NAME_REMOVE_NODE);
            this.putValue(SHORT_DESCRIPTION, ConstantsView.DESC_REMOVE_NODE);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            DefaultMutableTreeNode selectedNode = elementsView.getSelectedNode();
            script.removeNode(selectedNode);
        }
    }


    private class HideLayerAction extends AbstractAction
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


    private class PropertyLayerAction extends AbstractAction
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

    private class SelectedElementAction implements TreeSelectionListener
    {
        @Override
        public void valueChanged(TreeSelectionEvent treeSelectionEvent)
        {
            DefaultMutableTreeNode selectedNode = elementsView.getSelectedNode();

            boolean isSelected = (selectedNode != null);
            removeNodeAction.setEnabled(true);

            boolean isLayerSelected = isSelected && (selectedNode instanceof GXLayer);
            propertyLayerAction.setEnabled(isLayerSelected);
            hideLayerAction.setEnabled(isLayerSelected);
        }
    }

    private class OkPropertyAction extends AbstractAction
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

    private class CancelPropertyAction extends AbstractAction
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


    private class ElementTransferHandler extends TransferHandler
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
    }

    private class MouseElementsListener implements MouseListener, MouseMotionListener
    {

        @Override
        public void mouseClicked(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent)
        {
            // Update selection
            selectionModel.setSelectedElements(getSelectedNodes());
        }

        public Collection<MutableTreeNode> getSelectedNodes()
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
                    if (!selectionModel.isParentOrEqual(selectedElement, insertedLayer))
                    {
                        selectedElement.removeFromParent();
                        insertedLayer.add(selectedElement);
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

        @Override
        public void mouseDragged(MouseEvent mouseEvent)
        {
            if (mouseEvent.getButton() == MouseEvent.BUTTON1)
            {
                // Update selected element
                TreePath pathForLocation = elementsView.elementsTree.getPathForLocation(mouseEvent.getX(),
                                                                                        mouseEvent.getY());
                selectionModel.setSelectionPath(pathForLocation);
            }
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent)
        {

        }
    }

    private class MouseMotionElementsListener implements MouseMotionListener
    {
        @Override
        public void mouseDragged(MouseEvent mouseEvent)
        {
            if (mouseEvent.getButton() == MouseEvent.BUTTON1)
            {
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();

                // Update selected layer
                TreePath pathForLocation = elementsView.elementsTree.getPathForLocation(x, y);
                elementsView.elementsTree.setSelectionPath(pathForLocation);
            }
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent)
        {

        }
    }
}
