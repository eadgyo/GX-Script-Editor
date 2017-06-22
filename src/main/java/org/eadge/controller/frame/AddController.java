package org.eadge.controller.frame;

import org.eadge.controller.Actions;
import org.eadge.model.frame.AddListModel;
import org.eadge.view.AddView;
import org.eadge.view.MyFrame;

import javax.swing.*;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by eadgyo on 17/02/17.
 *
 * Handle mouse motion on Add list
 */
public class AddController
{
    private MyFrame myFrame;
    private AddView addView;
    private AddListModel addListModel;
    private Actions a;

    public AddController(MyFrame myFrame, AddListModel addListModel, Actions actions)
    {
        this.myFrame = myFrame;
        this.addView = myFrame.addView;
        this.a = actions;

        // Set adding elements list model
        this.addListModel = addListModel;
        this.addListModel.addObserver(new AddObserver());
        addView.addListPanel.setAddListModel(addListModel);

        // Add drag and drop support
        addView.addListPanel.setTransferHandler(new AddTransferHandler());
        addView.addListPanel.addMouseListener(new AddMouseListener());

        // Add listener on group change
        this.addView.groupList.setModel(addListModel.getComboBoxModel());
        a.actionGroupChange = new ActionGroupChange();
        this.addView.groupList.addActionListener(a.actionGroupChange );
    }

    /**
     * Get selected element index from coordinate
     * @param x coordinate
     * @param y coordinate
     */
    public int getSelectedIndex(int x, int y)
    {
        return addView.addListPanel.getAddListRenderer().getSelectedElementIndex(x, y, addListModel);
    }

    private class AddMouseListener implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent)
        {
            // Get the pressed button index
            int pressedButton = mouseEvent.getButton();

            // If left mouse button is pressed
            if (pressedButton == MouseEvent.BUTTON1)
            {
                // Retrieve selected index from the mouse coordinates
                int mouseX = mouseEvent.getX();
                int mouseY = mouseEvent.getY();
                int selectedIndexOnClick = getSelectedIndex(mouseX, mouseY);

                // Update selection
                addListModel.setSelectedElement(selectedIndexOnClick);

                // Init drag and drop
                JComponent src = (JComponent) mouseEvent.getSource();
                TransferHandler transferHandler = src.getTransferHandler();

                // Set addView as the source object
                transferHandler.exportAsDrag(addView, mouseEvent, TransferHandler.COPY);

                System.out.println("Export as drag");
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent)
        {

        }
    }

    public class ActionGroupChange implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            // Get selected group
            int selectedGroupIndex = addView.groupList.getSelectedIndex();

            if (selectedGroupIndex == -1)
                return;

            // Update selected group
            addListModel.setSelectedElement(0);

            // Update panel size
            addView.addListPanel.updateLength();
        }
    }

    public class AddObserver implements Observer
    {
        @Override
        public void update(Observable observable, Object o)
        {
            a.addElementAction.setEnabled(addListModel.isSelectedElement());
            myFrame.addView.repaint();
        }
    }

    private class AddTransferHandler extends TransferHandler implements DropTargetListener, DragSourceMotionListener,
            DragGestureListener
    {
        public void dragGestureRecognized(DragGestureEvent dragGestureEvent)
        {
            System.out.println("Recognised");
        }

        public void dragEnter(DragSourceDragEvent dragSourceDragEvent)
        {
            System.out.println("Drag enter");
        }

        public void dragOver(DragSourceDragEvent dragSourceDragEvent)
        {
            System.out.println("Drag over");
        }

        public void dropActionChanged(DragSourceDragEvent dragSourceDragEvent)
        {
            System.out.println("Drop Action");
        }

        public void dragExit(DragSourceEvent dragSourceEvent)
        {
            System.out.println("Drag exit");
        }

        public void dragDropEnd(DragSourceDropEvent dragSourceDropEvent)
        {
            System.out.println("Drag end");
        }

        @Override
        public void dragMouseMoved(DragSourceDragEvent dragSourceDragEvent)
        {
            System.out.println("Motion");
        }

        @Override
        public void dragEnter(DropTargetDragEvent dropTargetDragEvent)
        {
            System.out.println("Dragged enter");
        }

        @Override
        public void dragOver(DropTargetDragEvent dropTargetDragEvent)
        {
            System.out.println("Dragged out");
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent)
        {
            System.out.println("Dragg action");
        }

        @Override
        public void dragExit(DropTargetEvent dropTargetEvent)
        {
            System.out.println("Dragg exit");
        }

        @Override
        public void drop(DropTargetDropEvent dropTargetDropEvent)
        {

        }
    }
}
