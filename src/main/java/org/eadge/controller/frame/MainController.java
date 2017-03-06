package org.eadge.controller.frame;

import org.eadge.controller.frame.global.EditController;
import org.eadge.controller.frame.global.FileController;
import org.eadge.controller.frame.global.ScriptController;
import org.eadge.model.script.Script;
import org.eadge.view.MyFrame;

/**
 * Created by eadgyo on 17/02/17.
 */
public class MainController
{
    private FileController fileController;
    private EditController editController;
    private ScriptController scriptController;

    public MainController(MyFrame myFrame, Script script)
    {
        fileController = new FileController(myFrame, script);
        editController = new EditController(myFrame, script);
        scriptController = new ScriptController(myFrame, script);
    }
}
