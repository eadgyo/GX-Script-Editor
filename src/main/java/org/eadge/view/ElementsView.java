package org.eadge.view;

import org.eadge.view.layer.LayerPropertiesDialog;

import javax.swing.*;
import java.awt.*;

/**
 * Created by eadgyo on 15/02/17.
 */
public class ElementsView extends JPanel
{
    public JScrollPane scrollPane;
    public JList elementsList = new JList();

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
        scrollPane = new JScrollPane(elementsList);
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
}
