package org.eadge.view;

import org.eadge.ConstantsView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ronan-j on 15/02/17.
 */
public class Frame extends JFrame
{
    // Menu components
    // --> File
    public JMenuItem newFileItem = new JMenuItem();
    public JMenuItem saveItem = new JMenuItem();
    public JMenuItem saveAsItem = new JMenuItem();
    public JMenuItem addFunctionItem = new JMenuItem();

    // --> Edit
    public JMenuItem addElementItem = new JMenuItem();
    public JMenuItem removeElementItem = new JMenuItem();
    public JMenuItem copyItem = new JMenuItem();
    public JMenuItem cutItem = new JMenuItem();
    public JMenuItem pasteItem = new JMenuItem();
    public JMenuItem undoItem = new JMenuItem();
    public JMenuItem redoItem = new JMenuItem();

    // --> Script
    public JMenuItem validateScriptItem = new JMenuItem();
    public JMenuItem runScriptItem = new JMenuItem();


    // Frame components
    // --> Left Part
    public AddView addView;
    public ElementsView elementsView;

    // --> Right Part
    // -- --> Top part
    public DrawView drawView;

    // -- --> Bottom part
    public ConsoleView consoleView;
    public TestsView testsView;


    public Frame()
    {
        super(ConstantsView.MAIN_WINDOW_TITLE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create menu
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);

        // --> File menu
        JMenu fileMenu = new JMenu(ConstantsView.MENU_FILE);
        jMenuBar.add(fileMenu);
        fileMenu.add(newFileItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(addFunctionItem);

        // --> Edit menu
        JMenu editMenu = new JMenu(ConstantsView.MENU_EDIT);
        jMenuBar.add(editMenu);
        editMenu.add(addElementItem);
        editMenu.add(removeElementItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(cutItem);
        editMenu.add(undoItem);
        editMenu.add(redoItem);

        // --> Script menu
        JMenu scriptMenu = new JMenu(ConstantsView.MENU_SCRIPT);
        jMenuBar.add(scriptMenu);
        scriptMenu.add(validateScriptItem);
        scriptMenu.add(runScriptItem);


        // Add components
        // --> left part
        addView = new AddView();
        elementsView = new ElementsView();
        JSplitPane leftPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, addView, elementsView);

        // --> right part
        // -- --> top part
        drawView = new DrawView();

        // -- --> bottom part
        consoleView = new ConsoleView();
        testsView = new TestsView();

        JPanel bottomRigthPane = new JPanel(new CardLayout());
        bottomRigthPane.add(consoleView, ConstantsView.TAB_CONSOLE);
        bottomRigthPane.add(testsView, ConstantsView.TAB_TESTS);

        // --> Finalize
        JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, drawView, bottomRigthPane);

        // Finalize
        JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        add(mainPane);
    }
}
