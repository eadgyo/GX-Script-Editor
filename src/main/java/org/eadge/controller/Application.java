package org.eadge.controller;

import org.eadge.controller.draw.SceneController;
import org.eadge.view.MyFrame;

import javax.swing.*;
import javax.swing.event.ListDataListener;

/**
 * Created by eadgyo on 16/02/17.
 */
public class Application
{
    // View
    private MyFrame myFrame;

    // Model
    private String testGroup[] = {"group1", "group2"};

    private AddController      addController;
    private ConsoleController  consoleController;
    private ElementsController elementsController;
    private TestsController    testsController;
    private SceneController    sceneController;

    private MainController mainController;

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

        // Create other controller
        addController = new AddController(myFrame);
        consoleController = new ConsoleController(myFrame);
        elementsController = new ElementsController(myFrame);
        testsController = new TestsController(myFrame);
        sceneController = new SceneController(myFrame);

        mainController = new MainController(myFrame);

        myFrame.pack();
        myFrame.setVisible(true);
    }
}
