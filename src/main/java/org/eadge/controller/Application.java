package org.eadge.controller;

import org.eadge.controller.frame.*;
import org.eadge.gxscript.data.script.RawGXScript;
import org.eadge.model.frame.AddListModel;
import org.eadge.model.frame.global.ConnectionModel;
import org.eadge.model.frame.global.SelectionModel;
import org.eadge.model.script.GXLayer;
import org.eadge.model.script.GXLayerModel;
import org.eadge.model.script.Script;
import org.eadge.view.MyFrame;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by eadgyo on 16/02/17.
 *
 * Holds models view and controller creation
 */
public class Application
{
    // View
    private MyFrame myFrame;


    // Model
    private RawGXScript rawGXScript;
    private GXLayerModel gxLayerModel;
    private Script       script;
    private AddListModel addListModel;
    private SelectionModel selectionModel;
    private ConnectionModel connectionModel;

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
        createView();
        createModels();
        createControllers();
        createAction();
        createObserver();

        myFrame.pack();
        myFrame.setVisible(true);
    }

    private void createView()
    {
        myFrame = new MyFrame();
    }

    private void createModels()
    {
        rawGXScript = new RawGXScript();
        gxLayerModel = new GXLayerModel(new GXLayer());
        script = new Script(rawGXScript, gxLayerModel);
        addListModel = new AddListModel();
        connectionModel = new ConnectionModel();
        selectionModel = new SelectionModel(connectionModel);
    }

    private void createControllers()
    {
        addController = new AddController(myFrame, addListModel);
        consoleController = new ConsoleController(myFrame);
        elementsController = new ElementsController(myFrame, script, selectionModel);
        testsController = new TestsController(myFrame);
        sceneController = new SceneController(myFrame, script);

        mainController = new MainController(myFrame);
    }

    private void createAction()
    {
    }

    private void createObserver()
    {
        selectionModel.addObserver(new SelectionChangeAction());
        script.addObserver(new ScriptChangeAction());
    }

    private class ScriptChangeAction implements Observer
    {
        @Override
        public void update(Observable observable, Object o)
        {
            // Redraw scene
            myFrame.sceneView.repaint();
        }
    }

    private class SelectionChangeAction implements Observer
    {
        @Override
        public void update(Observable observable, Object o)
        {
            // Redraw scene
            myFrame.sceneView.repaint();
        }
    }
}
