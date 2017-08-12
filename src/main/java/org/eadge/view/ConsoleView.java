package org.eadge.view;

import org.eadge.ConstantsView;
import org.eadge.view.console.MessageConsole;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

/**
 * Created by eadgyo on 15/02/17.
 *
 * Holds console elements
 */
public class ConsoleView extends JPanel
{
    public MessageConsole messageConsole;
    public JTextArea consoleText;

    public ConsoleView()
    {
        JTabbedPane jTabbedPane = new JTabbedPane();
        this.setLayout(new BorderLayout());
        consoleText = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(consoleText);
        messageConsole = new MessageConsole(consoleText);
        messageConsole.redirectOut(Color.black, new PrintStream(System.out));
        messageConsole.setMessageLines(100);
        jTabbedPane.add(jScrollPane, ConstantsView.TAB_CONSOLE);
        add(jTabbedPane);
    }
}
