package org.eadge.controller.frame.global;

import org.eadge.ConstantsView;
import org.eadge.gxscript.data.compile.script.CompiledGXScript;
import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.gxscript.data.io.EGX;
import org.eadge.gxscript.tools.check.GXValidator;
import org.eadge.gxscript.tools.compile.GXCompiler;
import org.eadge.gxscript.tools.compile.GXEntityCreator;
import org.eadge.model.AdvIOM;
import org.eadge.model.frame.TestsModel;
import org.eadge.model.frame.global.project.FileModel;
import org.eadge.model.script.Script;
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
    private Script script;
    private FileModel fileModel;
    private TestsModel testsModel;

    private NewFileAction newFileAction;
    private OpenAction    openAction;
    private SaveAction    saveAction;
    private SaveAsAction  saveAsAction;
    private ExportAction  exportAction;
    private ImportScriptAction importScriptAction;
    private ImportElementsAction importElementsAction;

    public FileController(MyFrame frame, Script script, FileModel fileModel, TestsModel testsModel)
    {
        this.frame = frame;
        this.script = script;
        this.fileModel = fileModel;
        this.testsModel = testsModel;

        NewFileAction newFileAction = new NewFileAction();
        OpenAction    openAction    = new OpenAction();
        SaveAction    saveAction    = new SaveAction();
        SaveAsAction  saveAsAction  = new SaveAsAction();
        ExportAction  exportAction  = new ExportAction();
        ImportScriptAction importScriptAction = new ImportScriptAction();
        ImportElementsAction importElementsAction = new ImportElementsAction();

        MenuView menuView = frame.menuView;
        menuView.newFileItem.setAction(newFileAction);
        menuView.openItem.setAction(openAction);
        menuView.saveItem.setAction(saveAction);
        menuView.saveAsItem.setAction(saveAsAction);
        menuView.exportItem.setAction(exportAction);
        menuView.importScriptItem.setAction(importScriptAction);
        menuView.importElementsItem.setAction(importElementsAction);
    }

    private class NewFileAction extends AbstractAction
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
                script.clear();
            }
        }
    }

    private class OpenAction extends AbstractAction
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
                File file = frame.chooseFile.getSelectedFile();
                Script importedScript = AdvIOM.getAdv().loadScript(file.getPath());

                if (importedScript == null)
                {
                    JOptionPane.showMessageDialog(frame, ConstantsView.MESSAGE_INVALID_FILE);
                    return;
                }

                script.set(importedScript);
            }
        }
    }

    private class SaveAction extends AbstractAction
    {
        public SaveAction()
        {
            super(ConstantsView.NAME_SAVE_FILE);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_SAVE_FILE);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            if (fileModel.getScriptPath() == null)
            {
                int returnVal = frame.chooseFile.showSaveDialog(frame);

                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = frame.chooseFile.getSelectedFile();
                    fileModel.setScriptPath(file.getPath());
                    AdvIOM.getAdv().saveScript(script, fileModel.getScriptPath());
                }
            }
            else
            {
                AdvIOM.getAdv().saveScript(script, fileModel.getScriptPath());
            }
        }
    }


    private class SaveAsAction extends AbstractAction
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
                AdvIOM.getAdv().saveScript(script, file.getPath());
            }
        }
    }

    private class ExportAction extends AbstractAction
    {
        public ExportAction()
        {
            super(ConstantsView.NAME_EXPORT);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_EXPORT);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            // If tests show valid script
            if (!testsModel.canExportCompiled())
            {
                JOptionPane.showMessageDialog(frame, ConstantsView.MESSAGE_CANT_EXPORT);
                return;
            }

            // Script can be saved
            int returnVal = frame.chooseFile.showSaveDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = frame.chooseFile.getSelectedFile();
                AdvIOM.getAdv().saveCompiledGXScript(file.getPath(), );
            }
        }
    }

    private class ImportScriptAction extends AbstractAction
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

                if (compiledGXScript == null)
                {
                    JOptionPane.showMessageDialog(frame, ConstantsView.MESSAGE_INVALID_FILE);
                    return;
                }

                // Add to list of elements this compiled script

            }
        }
    }

    private class ImportElementsAction extends AbstractAction
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

                // Add to list of elements this egx groups
            }
        }
    }
}
