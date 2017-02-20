package org.eadge.controller;

import org.eadge.controller.frame.*;
import org.eadge.model.frame.AddListModel;
import org.eadge.model.frame.SceneModel;
import org.eadge.model.script.Script;
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
    private Script       script;
    private AddListModel addListModel;
    private SceneModel   sceneModel;


    // Controller
    private MainController mainController;

    private AddController      addController;
    private ConsoleController  consoleController;
    private ElementsController elementsController;
    private TestsController    testsController;
    private SceneController    sceneController;


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
        // View
        myFrame = new MyFrame();

        // Models
        script = new Script();
        sceneModel = new SceneModel();
        addListModel = new AddListModel();

        // Controllers
        addController = new AddController(myFrame, addListModel);
        consoleController = new ConsoleController(myFrame);
        elementsController = new ElementsController(myFrame);
        testsController = new TestsController(myFrame);
        sceneController = new SceneController(myFrame, sceneModel, script);

        mainController = new MainController(myFrame);

        myFrame.pack();
        myFrame.setVisible(true);
    }
}
