package org.eadge.view;

import org.eadge.view.console.MessageConsole;

import javax.swing.*;

/**
 * Created by eadgyo on 15/02/17.
 *
 * Holds console elements
 */
public class ConsoleView extends JPanel
{
    private MessageConsole messageConsole;
    private JTextArea consoleText;

    public ConsoleView()
    {
        JScrollPane jScrollPane = new JScrollPane(consoleText);
        consoleText = new JTextArea();
        messageConsole = new MessageConsole(consoleText);
        //messageConsole.redirectOut(Color.black, System.out);
        messageConsole.setMessageLines(100);
        add(jScrollPane);
    }
}
