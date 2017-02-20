package org.eadge.controller.frame;

import org.eadge.ConstantsView;
import org.eadge.model.script.GXLayer;
import org.eadge.model.script.Script;
import org.eadge.view.ElementsView;
import org.eadge.view.MyFrame;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;

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
    private Script script;

    public ElementsController(MyFrame myFrame, Script script)
    {
        elementsView = myFrame.elementsView;

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
}
