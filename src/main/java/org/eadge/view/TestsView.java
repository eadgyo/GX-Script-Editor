package org.eadge.view;

import org.eadge.ConstantsView;
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
        JTabbedPane tabbedPane = new JTabbedPane();
        this.setLayout(new BorderLayout());
        DefaultListModel<Object> objectDefaultListModel = new DefaultListModel<>();
        objectDefaultListModel.add(0, "test");
        validTests = new JList(objectDefaultListModel);
        JScrollPane scrollTests = new JScrollPane(validTests);

        consoleTestsText = new JTextArea();
        JScrollPane scrollText = new JScrollPane(consoleTestsText);
        tabbedPane.add(scrollText, ConstantsView.TAB_TESTS);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, scrollTests);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);
    }
}