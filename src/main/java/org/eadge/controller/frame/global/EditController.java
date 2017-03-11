package org.eadge.controller.frame.global;

import org.eadge.ConstantsView;
import org.eadge.model.Models;
import org.eadge.model.script.GXElement;
import org.eadge.utils.Copy;
import org.eadge.utils.GTools;
import org.eadge.view.MenuView;
import org.eadge.view.MyFrame;

import javax.swing.*;
import javax.swing.tree.MutableTreeNode;
import java.awt.event.ActionEvent;
import java.util.Collection;

/**
 * Created by eadgyo on 17/02/17.
 *
 * Manage main function for 
 */
public class EditController
{
    private Models m;

    public EditController(MyFrame frame, Models m)
    {
        this.m = m;

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
            GXElement selectedElement = m.addListModel.getSelectedElement();
            MutableTreeNode firstSelectedElement = m.getFirstSelectedElementOrRoot();
            m.script.addEntity(selectedElement, firstSelectedElement);
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
            Collection<MutableTreeNode> selectedElements = m.selectionModel.getSelectedElements();
            for (MutableTreeNode selectedElement : selectedElements)
            {
                m.script.removeNode(selectedElement);
            }
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
            Collection<MutableTreeNode> selectedElements = m.selectionModel.getSelectedElements();
            m.copyModel.saveCopyOfElements(selectedElements);
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
            // Copy selected elements
            Collection<MutableTreeNode> selectedElements = m.selectionModel.getSelectedElements();
            m.copyModel.saveCopyOfElements(selectedElements);

            // And remove all nodes
            m.script.removeNodes(selectedElements);
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
            // Get saved elements
            Collection<MutableTreeNode> copiedElements = Copy.copyElements(m.copyModel.getSavedElements());

            // Get left of screen
            double leftX = m.sceneModel.computeXInScene(0);
            double leftY = m.sceneModel.computeYInScene(0);

            // Move rect containing all elements to left of screen
            GTools.moveElementsTo(leftX, leftY, copiedElements);

            // Get selected node
            MutableTreeNode parent = m.getFirstSelectedElementOrRoot();

            // Add elements
            m.script.addNodes(copiedElements, parent);
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
