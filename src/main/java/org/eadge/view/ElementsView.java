package org.eadge.view;

import org.eadge.model.script.GXLayer;
import org.eadge.view.layer.LayerPropertiesDialog;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

/**
 * Created by eadgyo on 15/02/17.
 *
 * Display selected
 */
public class ElementsView extends JPanel
{
    public JScrollPane scrollPane;
    public JTree elementsTree = new JTree();

    public JButton addLayerButton = new JButton();
    public JButton removeLayerButton = new JButton();
    public JButton hideLayerButton = new JButton();
    public JButton propertyLayerButton = new JButton();

    public LayerPropertiesDialog layerPropertiesDialog;


    public ElementsView(MyFrame myFrame)
    {
        super(new BorderLayout());

        layerPropertiesDialog = new LayerPropertiesDialog(myFrame);
        layerPropertiesDialog.setVisible(true);

        // Top parts
        scrollPane = new JScrollPane(elementsTree);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Part
        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
        buttonsPane.add(addLayerButton);
        buttonsPane.add(removeLayerButton);
        buttonsPane.add(hideLayerButton);
        buttonsPane.add(propertyLayerButton);
        add(buttonsPane, BorderLayout.PAGE_END);
    }

    public GXLayer getSelectedLayer()
    {
        // Get selected element
        DefaultMutableTreeNode lastSelectedPathComponent = getSelectedNodeOrRoot();

        // If the selected element is a GXLayer
        if (lastSelectedPathComponent instanceof GXLayer)
        {
            return (GXLayer) lastSelectedPathComponent;
        }
        else // The parent is a GXLayer
        {
            return (GXLayer) lastSelectedPathComponent.getParent();
        }
    }

    public DefaultMutableTreeNode getSelectedNode()
    {
        // Get selected element
        Object lastSelectedPathComponent = elementsTree.getLastSelectedPathComponent();

        // If no element are selected
        if (lastSelectedPathComponent == null)
        {
            return null;
            // Return the root element
            // return (DefaultMutableTreeNode) elementsTree.getModel().getRoot();
        }

        // Return the selected element
        return (DefaultMutableTreeNode) lastSelectedPathComponent;
    }

    public DefaultMutableTreeNode getSelectedNodeOrRoot()
    {
        // Get selected element
        Object lastSelectedPathComponent = elementsTree.getLastSelectedPathComponent();

        // If no element are selected
        if (lastSelectedPathComponent == null)
        {
            // Return the root element
            return (DefaultMutableTreeNode) elementsTree.getModel().getRoot();
        }

        // Return the selected element
        return (DefaultMutableTreeNode) lastSelectedPathComponent;
    }
}
