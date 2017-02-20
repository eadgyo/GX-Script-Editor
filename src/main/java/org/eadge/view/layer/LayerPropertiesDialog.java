package org.eadge.view.layer;

import org.eadge.ConstantsView;
import org.eadge.model.script.GXLayer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by eadgyo on 19/02/17.
 *
 * Handle change of layer properties
 */
public class LayerPropertiesDialog extends JDialog
{
    public JLabel colorLabel = new JLabel();
    public JColorChooser colorChooser = new JColorChooser();

    public JButton okButton = new JButton();
    public JButton cancelButton = new JButton();

    private boolean response = false;

    public LayerPropertiesDialog(Frame frame)
    {
        super(frame, ConstantsView.PROPERTIES_DIALOG_TITLE, true);

        // Top
        JPanel topPane = new JPanel(new BorderLayout());

        topPane.add(colorLabel, BorderLayout.PAGE_START);
        topPane.add(colorChooser, BorderLayout.CENTER);

        // Bottom
        JPanel botPane = new JPanel(new FlowLayout());
        botPane.add(okButton);
        botPane.add(cancelButton);

        // Finalize
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPane, BorderLayout.CENTER);
        mainPanel.add(botPane, BorderLayout.PAGE_END);
        add(mainPanel);
        pack();
    }

    /**
     * Open the property dialog
     *
     * @param selectedLayer modified layer
     * @return true if the layer was modified, false otherwise
     */
    public boolean open(GXLayer selectedLayer)
    {
        loadLayer(selectedLayer);

        this.setVisible(true);

        if (response)
            changeLayer(selectedLayer);

        return response;
    }

    /**
     * Load layer properties in dialog
     *
     * @param selectedLayer used layer
     */
    public void loadLayer(GXLayer selectedLayer)
    {
        colorChooser.setColor(selectedLayer.getBackgroundColor());
    }

    /**
     * Apply change to the used layer
     *
     * @param selectedLayer used layer
     */
    public void changeLayer(GXLayer selectedLayer)
    {
        selectedLayer.setBackgroundColor(colorChooser.getColor());
    }

    public void setResponse(boolean response)
    {
        this.response = response;
    }
}
