package org.eadge.controller.frame.global;

import org.eadge.ConstantsView;
import org.eadge.controller.Actions;
import org.eadge.gxscript.data.compile.script.CompiledGXScript;
import org.eadge.gxscript.data.compile.script.RawGXScript;
import org.eadge.gxscript.tools.compile.GXCompiler;
import org.eadge.gxscript.tools.run.GXRunner;
import org.eadge.model.Models;
import org.eadge.view.MenuView;
import org.eadge.view.MyFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

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
            int i = m.testsModel.validateAll(m.script.getRawGXScriptPure());
            try
            {
                m.testsModel.getTestStream().flush();
                m.testsModel.getTestStream().write(ConstantsView.COMPILATION_START_MESSAGE.getBytes());
                m.testsModel.getTestStream().write("\n".getBytes());
                if (i != -1)
                {
                    m.testsModel.getTestStream().write((ConstantsView.COMPILATION_ECHEC + 1 + ConstantsView.ERREUR)
                                                               .getBytes());
                }
                else
                {
                    m.testsModel.getTestStream().write((ConstantsView.COMPILATION_SUCCESS).getBytes());
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
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

            RawGXScript rawGXScriptPure = m.script.getRawGXScriptPure();
            int i = m.testsModel.validateAll(rawGXScriptPure);

            if (i != -1)
            {
                JOptionPane.showMessageDialog(myFrame, ConstantsView.INVALIDATE_SCRIPT + ": " + m.testsModel
                        .getElementAt(i).getName());
            }
            else
            {
                GXCompiler compiler = new GXCompiler();
                m.script.getRawGXScript().updateEntities();
                CompiledGXScript compile = compiler.compile(rawGXScriptPure);

                GXRunner runner = new GXRunner();
                runner.run(compile);
            }
        }
    }

}
