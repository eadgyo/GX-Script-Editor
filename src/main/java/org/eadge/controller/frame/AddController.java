package org.eadge.controller.frame;

import org.eadge.model.frame.AddListModel;
import org.eadge.view.AddView;
import org.eadge.view.MyFrame;

import javax.swing.*;
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

    public AddController(MyFrame myFrame, AddListModel addListModel)
    {
        this.myFrame = myFrame;
        this.addView = myFrame.addView;

        // Set adding elements list model
        this.addListModel = addListModel;
        this.addListModel.addObserver(new AddObserver());
        addView.addListPanel.setAddListModel(addListModel);

        // Add drag and drop support
        addView.addListPanel.setTransferHandler(new TransferHandler("GXElement Object"));
        addView.addListPanel.addMouseListener(new AddMouseListener());

        // Add listener on group change
        this.addView.groupList.setModel(addListModel.getComboBoxModel());
        this.addView.groupList.addActionListener(new ActionGroupChange());
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
            System.out.println("Pressed");

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

    private class ActionGroupChange implements ActionListener
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

    private class AddObserver implements Observer
    {
        @Override
        public void update(Observable observable, Object o)
        {
            myFrame.addView.repaint();
        }
    }
}
