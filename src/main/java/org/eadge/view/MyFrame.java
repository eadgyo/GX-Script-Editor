package org.eadge.view;

import org.eadge.ConstantsView;
import org.eadge.renderer.ElementRenderer;
import org.eadge.renderer.frame.AddListRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by eadgyo on 15/02/17.
 */
public class MyFrame extends JFrame
{
    // Renderer
    public ElementRenderer elementRenderer = new ElementRenderer(20, 7, 5, 2, Color.white, Color.black, Color.black, Color.black);
    public AddListRenderer addListRenderer = new AddListRenderer(100, Color.white, new Color(127, 0, 0), elementRenderer);


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


    // MyFrame components

    // --> Top part
    public JButton runButton = new JButton();
    public JButton validateButton = new JButton();

    // --> Left Part
    public AddView addView;
    public ElementsView elementsView;

    // --> Right Part
    // -- --> Top part
    public SceneView sceneView;

    // -- --> Bottom part
    public ConsoleView consoleView;
    public TestsView testsView;


    public MyFrame()
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
        // Top part
        JPanel buttonsPart = new JPanel();
        buttonsPart.setLayout(new BoxLayout(buttonsPart, BoxLayout.LINE_AXIS));
        buttonsPart.add(runButton);
        buttonsPart.add(validateButton);

        // --> left part
        addView = new AddView();
        addView.addListPanel.setCellRenderer(addListRenderer);

        elementsView = new ElementsView(this);
        JSplitPane leftPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, addView, elementsView);

        // --> right part
        // -- --> top part
        sceneView = new SceneView();

        // -- --> bottom part
        consoleView = new ConsoleView();
        testsView = new TestsView();

        JPanel bottomRightPane = new JPanel(new CardLayout());
        bottomRightPane.add(consoleView, ConstantsView.TAB_CONSOLE);
        bottomRightPane.add(testsView, ConstantsView.TAB_TESTS);

        // --> Finalize
        JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sceneView, bottomRightPane);

        // Finalize
        JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        add(mainPane);
    }
}
