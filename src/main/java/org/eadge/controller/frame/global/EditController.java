package org.eadge.controller.frame.global;

import org.eadge.ConstantsView;
import org.eadge.model.script.Script;
import org.eadge.view.MenuView;
import org.eadge.view.MyFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by eadgyo on 17/02/17.
 *
 * Manage main function for 
 */
public class EditController
{
    public EditController(MyFrame frame, Script script)
    {
        MenuView menuView = frame.menuView;

        AddElementAction addElementAction = new AddElementAction();
        RemoveElementAction removeElementAction = new RemoveElementAction();
        CopyAction copyAction = new CopyAction();
        CutAction cutAction = new CutAction();
        PasteAction pasteAction = new PasteAction();
        UndoAction undoAction = new UndoAction();
        RedoAction redoAction = new RedoAction();

        menuView.addElementItem.setAction(addElementAction);
        menuView.removeElementItem.setAction(removeElementAction);
        menuView.copyItem.setAction(copyAction);
        menuView.cutItem.setAction(cutAction);
        menuView.pasteItem.setAction(pasteAction);
        menuView.undoItem.setAction(undoAction);
        menuView.redoItem.setAction(redoAction);
    }

    private class AddElementAction extends AbstractAction
    {
        public AddElementAction()
        {
            super(ConstantsView.NAME_ADD_ELEMENT);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_ADD_ELEMENT);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {

        }
    }

    private class RemoveElementAction extends AbstractAction
    {
        public RemoveElementAction()
        {
            super(ConstantsView.NAME_DEL_ELEMENT);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_DEL_ELEMENT);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {

        }
    }


    private class CopyAction extends AbstractAction
    {
        public CopyAction()
        {
            super(ConstantsView.NAME_COPY);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_COPY);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {

        }
    }
    private class CutAction extends AbstractAction
    {
        public CutAction()
        {
            super(ConstantsView.NAME_CUT);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_CUT);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {

        }
    }
    private class PasteAction extends AbstractAction
    {
        public PasteAction()
        {
            super(ConstantsView.NAME_PASTE);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_PASTE);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {

        }
    }
    private class UndoAction extends AbstractAction
    {
        public UndoAction()
        {
            super(ConstantsView.NAME_UNDO);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_UNDO);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {

        }
    }
    private class RedoAction extends AbstractAction
    {
        public RedoAction()
        {
            super(ConstantsView.NAME_REDO);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_REDO);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {

        }
    }


}
