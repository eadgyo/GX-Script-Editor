package org.eadge.view.layer;

import org.eadge.ConstantsView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ronan-j on 19/02/17.
 */
public class LayerPropertiesDialog extends JDialog
{
    public JLabel colorLabel = new JLabel();
    public JColorChooser colorChooser = new JColorChooser();

    public JButton okButton = new JButton();
    public JButton cancelButton = new JButton();

    public LayerPropertiesDialog(Frame frame)
    {
        super(frame, ConstantsView.PROPERTIES_DIALOG_TITLE);

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
}
