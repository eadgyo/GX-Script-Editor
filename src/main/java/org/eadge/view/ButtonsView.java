package org.eadge.view;

import javax.swing.*;

/**
 * Created by eadgyo on 05/03/17.
 *
 * Keeps top buttons part
 */
public class ButtonsView extends JPanel
{
    // --> Top part
    public JButton runButton = new JButton();
    public JButton validateButton = new JButton();


    public ButtonsView()
    {
        // Top part
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(runButton);
        add(validateButton);
    }

}
