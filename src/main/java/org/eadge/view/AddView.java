package org.eadge.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ronan-j on 15/02/17.
 */
public class AddView extends JPanel
{
    class ElementsPreviewRenderer extends JLabel implements ListCellRenderer<Object>
    {
        public ElementsPreviewRenderer()
        {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(JList<?> list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus)
        {

            setText(value.toString());

            Color background;
            Color foreground;

            // check if this cell represents the current DnD drop location
            JList.DropLocation dropLocation = list.getDropLocation();
            if (dropLocation != null
                    && !dropLocation.isInsert()
                    && dropLocation.getIndex() == index)
            {

                background = Color.BLUE;
                foreground = Color.WHITE;

                // check if this cell is selected
            }
            else if (isSelected)
            {
                background = Color.RED;
                foreground = Color.WHITE;

                // unselected, and not the DnD drop location
            }
            else
            {
                background = Color.WHITE;
                foreground = Color.BLACK;
            }
            ;

            setBackground(background);
            setForeground(foreground);

            return this;
        }
    }


    public JComboBox<String> groupList    = new JComboBox<>();
    public JList<String>     elementsList = new JList<String>();

    public AddView()
    {
        // Change cell renderer to display preview of script's element
        elementsList.setCellRenderer(new ElementsPreviewRenderer());

        // Add all elements in the container
        setLayout(new BorderLayout());
        add(groupList, BorderLayout.PAGE_START);
        add(elementsList, BorderLayout.CENTER);
    }
}
