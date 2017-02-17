package org.eadge.controler;

import org.eadge.controler.draw.SceneControler;
import org.eadge.view.MyFrame;

import javax.swing.*;
import javax.swing.event.ListDataListener;

/**
 * Created by ronan-j on 16/02/17.
 */
public class Application
{
    // View
    private MyFrame myFrame;

    // Model
    private String testGroup[] = {"group1", "group2"};

    private AddControler      addControler;
    private ConsoleControler  consoleControler;
    private ElementsControler elementsControler;
    private TestsControler testsControler;
    private SceneControler    sceneControler;

    private MainControler

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
        myFrame = new MyFrame();

        myFrame.addView.groupList.setModel(comboGroupModel);
        myFrame.addView.elementsList.setModel(listElementsModel);

        // Create other contoler
        addControler = new AddControler(myFrame);
        consoleControler = new ConsoleControler()

        myFrame.pack();
        myFrame.setVisible(true);
    }
}
