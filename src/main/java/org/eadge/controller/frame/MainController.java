package org.eadge.controller.frame;

import org.eadge.controller.Actions;
import org.eadge.controller.frame.global.EditController;
import org.eadge.controller.frame.global.FileController;
import org.eadge.controller.frame.global.ScriptController;
import org.eadge.model.Models;
import org.eadge.view.MyFrame;

/**
 * Created by eadgyo on 17/02/17.
 */
public class MainController
{
    private FileController fileController;
    private EditController editController;
    private ScriptController scriptController;

    private MyFrame frame;
    private Models m;

    public MainController(MyFrame myFrame, Models m, Actions a)
    {
        this.m = m;
        this.frame = myFrame;

        fileController = new FileController(myFrame, m, a);
        editController = new EditController(myFrame, m, a);
        scriptController = new ScriptController(myFrame, m, a);

        // Create popup menu
        frame.addElementItem.setAction(a.addElementAction);
        frame.addLayerItem.setAction(a.addLayerAction);
        frame.removeElementItem.setAction(a.removeNodeAction);
        frame.propertyLayerItem.setAction(a.propertyLayerAction);
        frame.copyItem.setAction(a.copyAction);
        frame.cutItem.setAction(a.cutAction);
        frame.pasteItem.setAction(a.pasteAction);
        frame.validateItem.setAction(a.validateScriptAction);
        frame.launchItem.setAction(a.runScriptAction);
    }

}
