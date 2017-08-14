package org.eadge.controller.frame.global;

import org.eadge.ConstantsView;
import org.eadge.controller.Actions;
import org.eadge.gxscript.data.compile.script.DisplayCompiledGXScript;
import org.eadge.gxscript.data.compile.script.RawGXScript;
import org.eadge.gxscript.tools.compile.GXCompilerDisplay;
import org.eadge.gxscript.tools.run.GXRunnerDisplay;
import org.eadge.model.Models;
import org.eadge.view.MenuView;
import org.eadge.view.MyFrame;
import org.eadge.view.console.CustomOutputStream;

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
            myFrame.consoleView.consoleText.setText("");
            int i = m.testsModel.validateAll(m.script.getRawGXScriptPure());
//            try
//            {
//                m.testsModel.getTestStream().flush();
//                m.testsModel.getTestStream().write(ConstantsView.COMPILATION_START_MESSAGE.getBytes());
//                m.testsModel.getTestStream().write("\n".getBytes());
//                if (i != -1)
//                {
//                    m.testsModel.getTestStream().write((ConstantsView.COMPILATION_ECHEC + 1 + ConstantsView.ERREUR)
//                                                               .getBytes());
//                }
//                else
//                {
//                    m.testsModel.getTestStream().write((ConstantsView.COMPILATION_SUCCESS).getBytes());
//                }
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
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
            else if (rawGXScriptPure.containsInput())
            {
                JOptionPane.showMessageDialog(myFrame, ConstantsView.INPUTS_SCRIPT_PRESENT);
            }
            else
            {
                GXCompilerDisplay compiler = new GXCompilerDisplay();
                m.script.getRawGXScript().updateEntities();
                DisplayCompiledGXScript compile = compiler.compile(rawGXScriptPure);

                GXRunnerDisplay    runner     = new GXRunnerDisplay();
                CustomOutputStream testStream = (CustomOutputStream) m.testsModel.getTestStream();
                testStream.clear();
                myFrame.consoleView.consoleText.setText("");
                runner.run(compile, testStream);
            }
        }
    }

}
