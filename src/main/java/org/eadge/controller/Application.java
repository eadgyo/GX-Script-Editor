package org.eadge.controller;

import org.eadge.controller.frame.*;
import org.eadge.gxscript.data.compile.script.RawGXScript;
import org.eadge.model.Models;
import org.eadge.model.frame.AddListModel;
import org.eadge.model.frame.global.ConnectionModel;
import org.eadge.model.frame.global.SelectionModel;
import org.eadge.model.frame.global.project.FileModel;
import org.eadge.model.script.GXLayer;
import org.eadge.model.script.GXLayerModel;
import org.eadge.model.script.Script;
import org.eadge.renderer.ElementFinder;
import org.eadge.renderer.EntryFinder;
import org.eadge.view.MyFrame;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by eadgyo on 16/02/17.
 *
 * Holds m view and controller creation
 */
public class Application
{
    // View
    private MyFrame myFrame;

    // Model

    private Models m = new Models();

    // Controller
    private MainController mainController;

    private AddController      addController;
    private ConsoleController  consoleController;
    private ElementsController elementsController;
    private TestsController    testsController;
    private SceneController    sceneController;

    private EntryFinder entryFinder;
    private ElementFinder elementFinder;

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
        m.rawGXScript = new RawGXScript();
        m.gxLayerModel = new GXLayerModel(new GXLayer());
        m.script = new Script(m.rawGXScript, m.gxLayerModel);
        m.addListModel = new AddListModel();
        m.connectionModel = new ConnectionModel();
        m.selectionModel = new SelectionModel(m.connectionModel);
        m.fileModel = new FileModel();
    }

    private void createControllers()
    {

        addController = new AddController(myFrame, m.addListModel);
        consoleController = new ConsoleController(myFrame);
        elementsController = new ElementsController(myFrame, m.script, m.selectionModel);
        testsController = new TestsController(myFrame);
        sceneController = new SceneController(m.script, myFrame, m.selectionModel, elementFinder, entryFinder);

        mainController = new MainController(myFrame, m);
    }

    private void createAction()
    {
    }

    private void createObserver()
    {
        m.selectionModel.addObserver(new SelectionChangeAction());
        m.script.addObserver(new ScriptChangeAction());
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
