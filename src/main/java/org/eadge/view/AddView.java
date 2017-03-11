package org.eadge.view;

import org.eadge.model.frame.AddListModel;
import org.eadge.renderer.frame.AddListRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by eadgyo on 15/02/17.
 *
 * Adding elements view
 */
public class AddView extends JPanel
{
    public JComboBox<String> groupList    = new JComboBox<>();
    public AddListPanel addListPanel = new AddListPanel();
    public JScrollPane addScrollingPane;

    public AddView()
    {
        // Add all elements in the container
        setLayout(new BorderLayout());
        add(groupList, BorderLayout.PAGE_START);

        addScrollingPane = new JScrollPane(addListPanel);
        add(addScrollingPane, BorderLayout.CENTER);
    }

    public class AddListPanel extends JPanel
    {
        private AddListRenderer addListRenderer;
        private AddListModel    addListModel;

        public AddListPanel()
        {
        }

        /**
         * Change list renderer
         * @param addListRenderer used list renderer
         */
        public void setCellRenderer(AddListRenderer addListRenderer)
        {
            this.addListRenderer = addListRenderer;
        }

        /**
         * Change list model
         * @param addListModel used list model
         */
        public void setAddListModel(AddListModel addListModel)
        {
            this.addListModel = addListModel;
        }

        public AddListRenderer getAddListRenderer()
        {
            return addListRenderer;
        }

        public AddListModel getAddListModel()
        {
            return addListModel;
        }

        @Override
        public void paint(Graphics graphics)
        {
            super.paint(graphics);
            addListRenderer.paint((Graphics2D) graphics, getWidth(), getHeight(), addListModel);
        }

        /**
         * Set width and height for the panel
         * @param width desired width
         * @param height desired height
         */
        public void setLength(int width, int height)
        {
            // Set desired length
            setPreferredSize(new Dimension(width, height));

            // Revalidate panel
            addListPanel.revalidate();
            addListPanel.repaint();
            addScrollingPane.getVerticalScrollBar().setValue(0);
        }

        public void updateLength()
        {
            int width = addListPanel.getWidth();
            int height = (int) addListRenderer.getTotalHeight(addListModel.getSelectedGroup());
            setLength(width, height);
        }
    }
}
