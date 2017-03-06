package org.eadge.controller.frame.global;

import org.eadge.ConstantsView;
import org.eadge.model.script.Script;
import org.eadge.view.MenuView;
import org.eadge.view.MyFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by eadgyo on 17/02/17.
 */
public class ScriptController
{
    public ScriptController(MyFrame myFrame, Script script)
    {
        MenuView menuView = myFrame.menuView;

        ValidateScriptAction validateScriptAction = new ValidateScriptAction();
        RunScriptAction runScriptAction = new RunScriptAction();

        menuView.validateScriptItem.setAction(validateScriptAction);
        menuView.runScriptItem.setAction(runScriptAction);
    }

    private class ValidateScriptAction extends AbstractAction
    {
        public ValidateScriptAction()
        {
            super(ConstantsView.NAME_VALIDATE_SCRIPT);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_VALIDATE_SCRIPT);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {

        }
    }


    private class RunScriptAction extends AbstractAction
    {
        public RunScriptAction()
        {
            super(ConstantsView.NAME_RUN_SCRIPT);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_RUN_SCRIPT);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {

        }
    }

}
