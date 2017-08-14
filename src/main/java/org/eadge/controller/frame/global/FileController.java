package org.eadge.controller.frame.global;

import org.eadge.ConstantsView;
import org.eadge.controller.Actions;
import org.eadge.gxscript.data.compile.script.CompiledGXScript;
import org.eadge.gxscript.data.io.EGX;
import org.eadge.gxscript.data.io.EGXGroup;
import org.eadge.model.Models;
import org.eadge.model.script.GXElement;
import org.eadge.model.script.SavedScript;
import org.eadge.utils.AdvIOM;
import org.eadge.utils.Converter;
import org.eadge.utils.GTools;
import org.eadge.view.MenuView;
import org.eadge.view.MyFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created by eadgyo on 17/02/17.
 */
public class FileController
{
    private MyFrame frame;

    private Models m;
    private Actions a;


    public FileController(MyFrame frame, Models m, Actions actions)
    {
        this.frame = frame;
        this.m = m;
        this.a = actions;

        a.newFileAction = new NewFileAction();
        a.openAction    = new OpenAction();
        a.saveAction    = new SaveAction();
        a.saveAsAction  = new SaveAsAction();
        a.exportAction  = new ExportAction();
        a.importScriptAction = new ImportScriptAction();
        a.importElementsAction = new ImportElementsAction();

        MenuView menuView = frame.menuView;
        menuView.newFileItem.setAction(a.newFileAction);
        menuView.openItem.setAction(a.openAction);
        menuView.saveItem.setAction(a.saveAction);
        menuView.saveAsItem.setAction(a.saveAsAction);
        menuView.exportItem.setAction(a.exportAction);
        menuView.importScriptItem.setAction(a.importScriptAction);
        menuView.importElementsItem.setAction(a.importElementsAction);
    }

    public class NewFileAction extends AbstractAction
    {
        public NewFileAction()
        {
            super(ConstantsView.NAME_NEW_FILE);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_NEW_FILE);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            int n = JOptionPane.showConfirmDialog(
                    frame,
                    ConstantsView.MESSAGE_CONFIRM_NEW_FILE,
                    ConstantsView.NAME_NEW_FILE,
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.OK_OPTION)
            {
                m.script.clear();
                m.sceneModel.resetCamera();
                m.script.callObservers();
            }
        }
    }

    public class OpenAction extends AbstractAction
    {
        public OpenAction()
        {
            super(ConstantsView.NAME_OPEN_FILE);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_OPEN_FILE);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            int returnVal = frame.chooseFile.showOpenDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File        file           = frame.chooseFile.getSelectedFile();
                SavedScript importedScript = AdvIOM.getAdv().loadScript(file.getPath());

                if (importedScript == null)
                {
                    JOptionPane.showMessageDialog(frame, ConstantsView.MESSAGE_INVALID_FILE);
                    return;
                }


                m.script.set(importedScript.script);
                m.sceneModel.set(importedScript.x, importedScript.y, importedScript.scale);
                m.script.callObservers();
            }
        }
    }

    public class SaveAction extends AbstractAction
    {
        public SaveAction()
        {
            super(ConstantsView.NAME_SAVE_FILE);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_SAVE_FILE);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            if (m.fileModel.getScriptPath() == null)
            {
                int returnVal = frame.chooseFile.showSaveDialog(frame);

                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = frame.chooseFile.getSelectedFile();
                    m.fileModel.setScriptPath(file.getPath());
                    SavedScript savedScript = new SavedScript(m.script, m.sceneModel);
                    AdvIOM.getAdv().saveScript(savedScript, m.fileModel.getScriptPath());
                }
            }
            else
            {
                SavedScript savedScript = new SavedScript(m.script, m.sceneModel);
                AdvIOM.getAdv().saveScript(savedScript, m.fileModel.getScriptPath());
            }
        }
    }


    public class SaveAsAction extends AbstractAction
    {
        public SaveAsAction()
        {
            super(ConstantsView.NAME_SAVE_AS_FILE);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_SAVE_AS_FILE);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            int returnVal = frame.chooseFile.showSaveDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = frame.chooseFile.getSelectedFile();
                SavedScript savedScript = new SavedScript(m.script, m.sceneModel);
                AdvIOM.getAdv().saveScript(savedScript, file.getPath());
            }
        }
    }

    public class ExportAction extends AbstractAction
    {
        public ExportAction()
        {
            super(ConstantsView.NAME_EXPORT);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_EXPORT);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            m.testsModel.validateAll(m.script.getRawGXScriptPure());

            // If tests show valid script
            if (!m.testsModel.isCanExportCompiled())
            {
                JOptionPane.showMessageDialog(frame, ConstantsView.MESSAGE_CANT_EXPORT);
                return;
            }

            // Script can be saved
            int returnVal = frame.chooseFile.showSaveDialog(frame);

            String groupName = JOptionPane.showInputDialog(ConstantsView.INPUT_ASK_GROUP_NAME, "");

            if (groupName == null || groupName.equals(""))
            {
                return;
            }

            String name = JOptionPane.showInputDialog(ConstantsView.INPUT_ASK_NAME, "");

            if (name == null || name.equals(""))
            {
                return;
            }



            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = frame.chooseFile.getSelectedFile();
                CompiledGXScript compiledRawGXScript = m.script.getCompiledRawGXScript();
                compiledRawGXScript.setName(name);
                compiledRawGXScript.setGroup(groupName);
                AdvIOM.getAdv().saveCompiledGXScript(file.getPath(), compiledRawGXScript);
            }
        }
    }

    public class ImportScriptAction extends AbstractAction
    {
        public ImportScriptAction()
        {
            super(ConstantsView.NAME_IMPORT_SCRIPT);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_IMPORT_SCRIPT);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            int returnVal = frame.chooseFile.showOpenDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File             file = frame.chooseFile.getSelectedFile();

                // Load this compiled script or compile existing script
                CompiledGXScript compiledGXScript = AdvIOM.getAdv().loadCompiledOrScript(file.getPath());

                // Check if the script has been loaded
                if (compiledGXScript == null)
                {
                    JOptionPane.showMessageDialog(frame, ConstantsView.MESSAGE_INVALID_FILE);
                    return;
                }

                EGX egx = new EGX();
                EGXGroup egxGroup = new EGXGroup(compiledGXScript.getGroup());
                GXElement element = Converter.compiledScriptToElement(compiledGXScript);
                element.setName(compiledGXScript.getName());
                egxGroup.add(element);
                egx.add(egxGroup);
                GTools.applyRendererProperties(frame.elementRenderer, egx);

                // Add to list of elements this compiled script
                m.addListModel.addGroup(egxGroup);
                frame.addView.groupList.updateUI();
            }
        }
    }

    public class ImportElementsAction extends AbstractAction
    {
        public ImportElementsAction()
        {
            super(ConstantsView.NAME_IMPORT_ELEMENTS);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_IMPORT_ELEMENTS);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            int returnVal = frame.chooseFile.showOpenDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = frame.chooseFile.getSelectedFile();

                // Load EGX elements
                EGX egx = AdvIOM.getAdv().loadEGX(file.getPath());

                if (egx == null)
                {
                    JOptionPane.showMessageDialog(frame, ConstantsView.MESSAGE_INVALID_FILE);
                    return;
                }

                egx = Converter.convertIfNeededToGXElements(egx);

                // Add to list of elements this egx groups
                for (EGXGroup egxGroup : egx.values())
                {
                    m.addListModel.addGroup(egxGroup);
                }
                frame.addView.groupList.updateUI();
            }
        }
    }
}
