package org.eadge.controler;

import org.eadge.view.Frame;

import javax.swing.*;
import javax.swing.event.ListDataListener;

/**
 * Created by ronan-j on 16/02/17.
 */
public class Application
{
    // View
    private Frame frame;

    // Model
    private String testGroup[] = {"group1", "group2"};

    private ComboBoxModel<String> comboGroupModel = new DefaultComboBoxModel<>(testGroup);
    private ListModel<String> listElementsModel = new ListModel<String>()
    {
        private String testElements[] = {"element1", "element2"};

        @Override
        public int getSize()
        {
            return testElements.length;
        }

        @Override
        public String getElementAt(int i)
        {
            return testElements[i];
        }

        @Override
        public void addListDataListener(ListDataListener listDataListener) {}

        @Override
        public void removeListDataListener(ListDataListener listDataListener) {}
    };

    public Application()
    {
        frame = new Frame();

        frame.addView.groupList.setModel(comboGroupModel);
        frame.addView.elementsList.setModel(listElementsModel);

        frame.pack();
        frame.setVisible(true);
    }
}
