package org.eadge.controller;

import org.eadge.controller.frame.AddController;
import org.eadge.controller.frame.ElementsController;
import org.eadge.controller.frame.global.EditController;
import org.eadge.controller.frame.global.FileController;
import org.eadge.controller.frame.global.ScriptController;

/**
 * Created by eadgyo on 29/05/17.
 */
public class Actions
{
    public  ElementsController.PropertyLayerAction propertyLayerAction;
    public  ElementsController.HideLayerAction     hideLayerAction;
    public  ElementsController.RemoveNodeAction    removeNodeAction;
    public  ElementsController.AddLayerAction      addLayerAction;
    public  ElementsController.UpdateNameAction    updateNameAction;

    public AddController.ActionGroupChange actionGroupChange;

    public  FileController.NewFileAction        newFileAction;
    public  FileController.OpenAction           openAction;
    public  FileController.SaveAction           saveAction;
    public  FileController.SaveAsAction         saveAsAction;
    public  FileController.ExportAction         exportAction;
    public  FileController.ImportScriptAction   importScriptAction;
    public  FileController.ImportElementsAction importElementsAction;


    public EditController.AddElementAction addElementAction;
    public EditController.RemoveElementAction removeElementAction;
    public EditController.CopyAction          copyAction;
    public EditController.CutAction           cutAction;
    public EditController.PasteAction         pasteAction;
    public EditController.UndoAction          undoAction;
    public EditController.RedoAction          redoAction;

    public ScriptController.ValidateScriptAction validateScriptAction;
    public ScriptController.RunScriptAction      runScriptAction;
}
