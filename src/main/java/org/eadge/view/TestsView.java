package org.eadge.view;

import org.eadge.view.console.MessageConsole;

import javax.swing.*;
import java.awt.*;

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
        this.setLayout(new BorderLayout());
        DefaultListModel<Object> objectDefaultListModel = new DefaultListModel<>();
        objectDefaultListModel.add(0, "test");
        validTests = new JList(objectDefaultListModel);

        consoleTestsText = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(consoleTestsText);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jScrollPane, validTests);
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);
    }
}