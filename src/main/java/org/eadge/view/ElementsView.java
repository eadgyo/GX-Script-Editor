package org.eadge.view;

import org.eadge.model.script.GXElement;
import org.eadge.model.script.GXLayer;
import org.eadge.view.layer.LayerPropertiesDialog;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
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

    public JButton addElementButton = new JButton();
    public JButton addLayerButton = new JButton();
    public JButton removeLayerButton = new JButton();
    public JButton hideLayerButton = new JButton();
    public JButton propertyLayerButton = new JButton();

    public LayerPropertiesDialog layerPropertiesDialog;


    public ElementsView(MyFrame myFrame)
    {
        super(new BorderLayout());

        layerPropertiesDialog = new LayerPropertiesDialog(myFrame);
        layerPropertiesDialog.setVisible(false);

        // Top parts
        elementsTree.setShowsRootHandles(true);
        elementsTree.setRootVisible(false);
        elementsTree.setCellRenderer(new LayerCellRenderer());
        elementsTree.setEditable(true);
        scrollPane = new JScrollPane(elementsTree);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Part
        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.PAGE_AXIS));

        JPanel buttonsPaneTop = new JPanel();
        buttonsPaneTop.setLayout(new BoxLayout(buttonsPaneTop, BoxLayout.LINE_AXIS));
        buttonsPaneTop.add(addElementButton);
        buttonsPaneTop.add(addLayerButton);

        JPanel buttonsPaneBot = new JPanel();
        buttonsPaneBot.setLayout(new BoxLayout(buttonsPaneBot, BoxLayout.LINE_AXIS));
//        buttonsPaneBot.add(hideLayerButton);
        buttonsPaneBot.add(propertyLayerButton);
        buttonsPaneBot.add(removeLayerButton);

        buttonsPane.add(buttonsPaneTop);
        buttonsPane.add(buttonsPaneBot);

        add(buttonsPane, BorderLayout.PAGE_END);
    }

    public GXLayer getSelectedLayer()
    {
        // Get selected element
        MutableTreeNode lastSelectedPathComponent = getSelectedNodeOrRoot();

        // If the selected element is MyGroupsOfElements GXLayer
        if (lastSelectedPathComponent instanceof GXLayer)
        {
            return (GXLayer) lastSelectedPathComponent;
        }
        else // The parent is MyGroupsOfElements GXLayer
        {
            return (GXLayer) lastSelectedPathComponent.getParent();
        }
    }

    public MutableTreeNode getSelectedNode()
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
        return (MutableTreeNode) lastSelectedPathComponent;
    }

    public MutableTreeNode getSelectedNodeOrRoot()
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
        return (MutableTreeNode) lastSelectedPathComponent;
    }

    private class LayerCellRenderer implements TreeCellRenderer
    {
        JLabel firstNameLabel = new JLabel(" ");

        JPanel renderer = new JPanel();

        DefaultTreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer();

        Color backgroundSelectionColor;

        Color backgroundNonSelectionColor;

        public LayerCellRenderer() {
            firstNameLabel.setForeground(Color.BLUE);
            renderer.add(firstNameLabel);
            renderer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            backgroundSelectionColor = defaultRenderer.getBackgroundSelectionColor();
            backgroundNonSelectionColor = defaultRenderer.getBackgroundNonSelectionColor();
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
                                                      boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Component returnValue = null;
            if ((value != null) && (value instanceof MutableTreeNode)) {
                MutableTreeNode userObject = ((MutableTreeNode) value);
                if (userObject instanceof GXLayer)
                {
                    returnValue = defaultRenderer.getTreeCellRendererComponent(tree, value, selected, expanded,
                                                                               false, row, hasFocus);
                }
                else if (userObject instanceof GXElement)
                {
                    GXElement element = (GXElement) userObject;
                    firstNameLabel.setText(element.toString());
                    if (selected)
                    {
                        renderer.setBackground(backgroundSelectionColor);
                    }
                    else
                    {
                        renderer.setBackground(backgroundNonSelectionColor);
                    }
                    renderer.setEnabled(tree.isEnabled());
                    returnValue = renderer;
                }
            }
            if (returnValue == null) {
                returnValue = defaultRenderer.getTreeCellRendererComponent(tree, value, selected, expanded,
                                                                           leaf, row, hasFocus);
            }
            return returnValue;
        }
    }

}


