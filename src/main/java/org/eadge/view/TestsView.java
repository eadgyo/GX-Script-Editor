package org.eadge.view;

import org.eadge.view.console.MessageConsole;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

/**
 * Created by eadgyo on 15/02/17.
 *
 *
 */
public class TestsView extends JPanel
{
    private MessageConsole messageConsole;
    private JTextArea      consoleTestsText;
    private JList          validTests;
    private PrintStream testPrintStream;

    public TestsView()
    {
        validTests = new JList();
        
        JScrollPane jScrollPane = new JScrollPane(consoleTestsText);
        messageConsole = new MessageConsole(consoleTestsText);
        messageConsole.redirectOut(Color.black, testPrintStream);
        messageConsole.setMessageLines(100);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jScrollPane, validTests);
        add(splitPane);
    }
}