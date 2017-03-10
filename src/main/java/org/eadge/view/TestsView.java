package org.eadge.view;

import org.eadge.view.console.MessageConsole;

import javax.swing.*;

/**
 * Created by eadgyo on 15/02/17.
 *
 *
 */
public class TestsView extends JPanel
{
    public MessageConsole messageConsole;
    public JTextArea      consoleTestsText;
    public JList          validTests;

    public TestsView()
    {
        validTests = new JList();
        
        JScrollPane jScrollPane = new JScrollPane(consoleTestsText);
        consoleTestsText = new JTextArea();
        messageConsole = new MessageConsole(consoleTestsText);
        messageConsole.setMessageLines(100);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jScrollPane, validTests);
        add(splitPane);
    }
}