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
public class AddController implements MouseListener, Observer
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
        //this.addListModel.addObserver(new AddObserver());
        addView.addListPanel.setAddListModel(addListModel);

        // Add drag and drop support
        addView.addListPanel.setTransferHandler(new TransferHandler("GXElement Object"));

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
        // Retrieve number of elements in the selected group
        int numberOfElements = addListModel.getSize();

        // If the list is empty
        if (numberOfElements == 0)
            return -1;

        // Get block height
        int blockHeight = addView.addListPanel.getAddListRenderer().getBlockHeight();

        // Compute selected element from the list renderer
        int selectedElement = y / blockHeight;

        // If the selected element is out of bound, reset
        if (selectedElement >= numberOfElements)
            return -1;

        return selectedElement;
    }

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

    @Override
    public void update(Observable observable, Object o)
    {
        addView.addListPanel.repaint();
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
            addView.addScrollingPane.repaint();
        }
    }

    private class AddObserver implements Observer
    {
        @Override
        public void update(Observable observable, Object o)
        {
            System.out.println("Change");
            myFrame.addView.repaint();
        }
    }
}
