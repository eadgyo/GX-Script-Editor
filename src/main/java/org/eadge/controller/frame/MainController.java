package org.eadge.controller.frame;

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

    public MainController(MyFrame myFrame, Models m)
    {
        fileController = new FileController(myFrame, m);
        editController = new EditController(myFrame, m);
        scriptController = new ScriptController(myFrame, m);
    }
}
