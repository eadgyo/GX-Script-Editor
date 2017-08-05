package org.eadge.controller.frame.global;

import org.eadge.ConstantsView;
import org.eadge.controller.Actions;
import org.eadge.model.Models;
import org.eadge.model.script.GXElement;
import org.eadge.utils.Copy;
import org.eadge.utils.GTools;
import org.eadge.view.ElementsView;
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

    public EditController(MyFrame frame, Models m, Actions a)
    {
        this.m = m;

        MenuView menuView = frame.menuView;
        ElementsView elementsView = frame.elementsView;

        a.addElementAction = new AddElementAction();
        a.removeElementAction = new RemoveElementAction();
        a.copyAction = new CopyAction();
        a.cutAction = new CutAction();
        a.pasteAction = new PasteAction();
        a.undoAction = new UndoAction();
        a.redoAction = new RedoAction();

        menuView.addElementItem.setAction(a.addElementAction);
        menuView.removeElementItem.setAction(a.removeElementAction);
        menuView.copyItem.setAction(a.copyAction);
        menuView.cutItem.setAction(a.cutAction);
        menuView.pasteItem.setAction(a.pasteAction);
        menuView.undoItem.setAction(a.undoAction);
        menuView.redoItem.setAction(a.redoAction);

        elementsView.addElementButton.setAction(a.addElementAction);
    }

    public class AddElementAction extends AbstractAction
    {
        public AddElementAction()
        {
            super(ConstantsView.NAME_ADD_ELEMENT);
            putValue(SHORT_DESCRIPTION, ConstantsView.DESC_ADD_ELEMENT);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            GXElement selectedElement = m.addListModel.getSelectedElement().deepClone();
            MutableTreeNode firstSelectedElement = m.getFirstSelectedLayerOrRoot();
            m.script.addEntity(selectedElement, firstSelectedElement);
        }
    }

    public class RemoveElementAction extends AbstractAction
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
            m.selectionModel.clearSelection();
        }
    }


    public class CopyAction extends AbstractAction
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
    public class CutAction extends AbstractAction
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
    public class PasteAction extends AbstractAction
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
            MutableTreeNode parent = m.getFirstSelectedLayerOrRoot();

            // Add elements
            m.script.addNodes(copiedElements, parent);
        }
    }
    public class UndoAction extends AbstractAction
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
    public class RedoAction extends AbstractAction
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
