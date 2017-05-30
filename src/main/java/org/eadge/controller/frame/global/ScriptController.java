package org.eadge.controller.frame.global;

import org.eadge.ConstantsView;
import org.eadge.controller.Actions;
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

    public ScriptController(MyFrame myFrame, Models m, Actions a)
    {
        this.myFrame = myFrame;
        this.m = m;

        MenuView menuView = myFrame.menuView;

        a.validateScriptAction = new ValidateScriptAction();
        a.runScriptAction = new RunScriptAction();

        menuView.validateScriptItem.setAction(a.validateScriptAction);
        menuView.runScriptItem.setAction(a.runScriptAction);
    }

    public class ValidateScriptAction extends AbstractAction
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


    public class RunScriptAction extends AbstractAction
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
