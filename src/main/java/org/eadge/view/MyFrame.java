package org.eadge.view;

import org.eadge.ConstantsView;
import org.eadge.renderer.ElementRenderer;
import org.eadge.renderer.EntryRenderer;
import org.eadge.renderer.frame.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by eadgyo on 15/02/17.
 *
 * Keeps frame elements
 */
public class MyFrame extends JFrame
{
    public JFileChooser chooseFile = new JFileChooser();

    // Renderer
    public EntryRenderer entryRenderer = new EntryRenderer(Color.BLACK, 6, 6);
    public ElementRenderer elementRenderer = new ElementRenderer(20, 10, new Color(250, 255, 251), Color.BLACK, Color.BLACK, entryRenderer);
    public AddListRenderer addListRenderer = new AddListRenderer(Color.white, new Color(255, 100, 100), elementRenderer);

    public ElementRenderer selectedElementRenderer = new ElementRenderer(20, 10, Color.WHITE, Color.RED, Color.BLACK,
                                                                         entryRenderer);
    public ElementRenderer invalidMovingElementRenderer = new ElementRenderer(20, 10, Color.RED, Color.BLACK, Color.BLACK, entryRenderer);
    public ElementRenderer movingElementRenderer = new ElementRenderer(20, 10, Color.WHITE, Color.BLUE, Color.BLACK,
                                                                       entryRenderer);

    public ConnectionRenderer connectionRenderer = new ConnectionRenderer(Color.BLACK, elementRenderer);
    public ConnectionSelectionRenderer connectionSelectionRenderer = new ConnectionSelectionRenderer(elementRenderer,
                                                                                                     Color.BLUE,
                                                                                                     Color.GREEN, Color
                                                                                                             .RED);

    public SelectionRenderer selectionRenderer = new SelectionRenderer(selectedElementRenderer, invalidMovingElementRenderer, movingElementRenderer);
    public SceneRenderer sceneRenderer = new SceneRenderer(Color.WHITE, new Color(255, 95, 90), elementRenderer, selectionRenderer, connectionRenderer, connectionSelectionRenderer);


    public MenuView menuView;

    // MyFrame components
    public ButtonsView buttonsView;

    // --> Left Part
    public AddView addView;
    public ElementsView elementsView;

    // --> Right Part
    // -- --> Top part
    public SceneView sceneView;

    // -- --> Bottom part
    public ConsoleView consoleView;
    public TestsView testsView;

    // Popup menu
    public PopupMenuSaver popupMenu = new PopupMenuSaver();

    public JMenuItem addElementItem    = new JMenuItem();
    public JMenuItem addLayerItem      = new JMenuItem();
    public JMenuItem removeElementItem = new JMenuItem();
    public JMenuItem propertyLayerItem = new JMenuItem();
    public JMenuItem copyItem          = new JMenuItem();
    public JMenuItem pasteItem         = new JMenuItem();
    public JMenuItem cutItem           = new JMenuItem();
    public JMenuItem validateItem           = new JMenuItem();
    public JMenuItem launchItem           = new JMenuItem();

    public MyFrame()
    {
        super(ConstantsView.MAIN_WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMenu();
        createComponents();
        createRenderer();
        createPopupMenu();
    }

    private void createPopupMenu()
    {
        popupMenu.add(addElementItem);
        popupMenu.add(removeElementItem);
        popupMenu.add(addLayerItem);
        popupMenu.add(propertyLayerItem);
        popupMenu.add(copyItem);
        popupMenu.add(pasteItem);
        popupMenu.add(cutItem);
        popupMenu.add(validateItem);
        popupMenu.add(launchItem);
    }

    private void createMenu()
    {
        menuView = new MenuView();
        setJMenuBar(menuView);
    }

    private void createComponents()
    {
        // Top part
        buttonsView = new ButtonsView();

        // --> left part
        addView = new AddView();
        addView.addListPanel.setCellRenderer(addListRenderer);
        addView.setPreferredSize(new Dimension(400, 300));

        elementsView = new ElementsView(this);
        JSplitPane leftPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, addView, elementsView);
        leftPane.setContinuousLayout(true);
        leftPane.setResizeWeight(0.7);

        // --> right part
        // -- --> top part
        sceneView = new SceneView();

        // -- --> bottom part
        consoleView = new ConsoleView();
        testsView = new TestsView();

//        tabbedPane.add(consoleView, ConstantsView.TAB_CONSOLE);
//        tabbedPane.add(testsView, ConstantsView.TAB_TESTS);
        JSplitPane tabbedPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, consoleView, testsView);
        tabbedPane.setResizeWeight(0.7);

        // --> Finalize
        JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sceneView, tabbedPane);
        rightPane.setContinuousLayout(true);
        rightPane.setResizeWeight(0.5);

        // Finalize
        JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        rightPane.setResizeWeight(0.7);

        add(buttonsView);
        add(mainPane);
    }

    private void createRenderer()
    {
        sceneView.setSceneRenderer(sceneRenderer);
    }

    public class PopupMenuSaver extends JPopupMenu
    {
        private int lastX = 0;
        private int lastY = 0;
        private boolean useCoordinate = false;

        @Override
        public void show(Component component, int i, int i1)
        {
            super.show(component, i, i1);
            lastX = i;
            lastY = i1;
            useCoordinate = true;
        }

        public boolean isUseCoordinate()
        {
            return useCoordinate;
        }

        public void show(Component component, int i, int i1, boolean useCoordinate)
        {
            super.show(component, i, i1);
            lastX = i;
            lastY = i1;
            useCoordinate = false;
        }

        public int getLastX()
        {
            return lastX;
        }

        public int getLastY()
        {
            return lastY;
        }
    }
}