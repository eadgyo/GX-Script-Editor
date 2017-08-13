package org.eadge.view;

import org.eadge.ConstantsView;

import javax.swing.*;

/**
 * Created by eadgyo on 05/03/17.
 */
public class MenuView extends JMenuBar
{

    // Menu components
    // --> File
    public JMenuItem newFileItem        = new JMenuItem();
    public JMenuItem openItem           = new JMenuItem();
    public JMenuItem saveItem           = new JMenuItem();
    public JMenuItem saveAsItem         = new JMenuItem();
    public JMenuItem exportItem         = new JMenuItem();
    public JMenuItem importScriptItem   = new JMenuItem();
    public JMenuItem importElementsItem = new JMenuItem();

    // --> Edit
    public JMenuItem addElementItem = new JMenuItem();
    public JMenuItem removeElementItem = new JMenuItem();
    public JMenuItem addLayerItem = new JMenuItem();
    public JMenuItem propertyLayerItem = new JMenuItem();
    public JMenuItem copyItem = new JMenuItem();
    public JMenuItem cutItem = new JMenuItem();
    public JMenuItem pasteItem = new JMenuItem();
    public JMenuItem undoItem = new JMenuItem();
    public JMenuItem redoItem = new JMenuItem();

    // --> Script
    public JMenuItem validateScriptItem = new JMenuItem();
    public JMenuItem runScriptItem = new JMenuItem();

    public MenuView()
    {

        // --> File menu
        JMenu fileMenu = new JMenu(ConstantsView.MENU_FILE);
        add(fileMenu);
        fileMenu.add(newFileItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(exportItem);
        fileMenu.add(importScriptItem);
        fileMenu.add(importElementsItem);

        // --> Edit menu
        JMenu editMenu = new JMenu(ConstantsView.MENU_EDIT);
        add(editMenu);
        editMenu.add(addElementItem);
        editMenu.add(removeElementItem);
        editMenu.add(addLayerItem);
        editMenu.add(propertyLayerItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(cutItem);
//        editMenu.add(undoItem);
//        editMenu.add(redoItem);

        // --> Script menu
        JMenu scriptMenu = new JMenu(ConstantsView.MENU_SCRIPT);
        add(scriptMenu);
        scriptMenu.add(validateScriptItem);
        scriptMenu.add(runScriptItem);
    }
}
