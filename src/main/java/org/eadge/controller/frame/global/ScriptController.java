package org.eadge.controller.frame.global;

import org.eadge.ConstantsView;
import org.eadge.gxscript.data.compile.script.CompiledGXScript;
import org.eadge.gxscript.tools.compile.GXCompiler;
import org.eadge.gxscript.tools.run.GXRunner;
import org.eadge.model.Models;
import org.eadge.view.MenuView;
import org.eadge.view.MyFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by eadgyo on 17/02/17.
 */
public class ScriptController
{
    private MyFrame myFrame;
    private Models m;

    public ScriptController(MyFrame myFrame, Models m)
    {
        this.myFrame = myFrame;
        this.m = m;

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
            m.testsModel.validateAll(m.rawGXScript);
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
            GXCompiler compiler = new GXCompiler();
            CompiledGXScript compile = compiler.compile(m.rawGXScript);

            GXRunner runner = new GXRunner();
            runner.run(compile);
        }
    }

}
