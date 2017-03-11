package org.eadge.controller;

import org.eadge.controller.frame.*;
import org.eadge.gxscript.data.compile.script.RawGXScript;
import org.eadge.gxscript.data.io.EGX;
import org.eadge.model.Models;
import org.eadge.model.frame.AddListModel;
import org.eadge.model.frame.SceneModel;
import org.eadge.model.frame.TestsModel;
import org.eadge.model.global.ConnectionModel;
import org.eadge.model.global.CopyModel;
import org.eadge.model.global.SelectionModel;
import org.eadge.model.global.project.FileModel;
import org.eadge.model.script.GXLayer;
import org.eadge.model.script.GXLayerModel;
import org.eadge.model.script.Script;
import org.eadge.renderer.ElementFinder;
import org.eadge.renderer.EntryFinder;
import org.eadge.utils.AdvIOM;
import org.eadge.utils.Converter;
import org.eadge.utils.GTools;
import org.eadge.view.MyFrame;

import java.util.ArrayList;
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

    public Application()
    {
        createView();
        createModels();
        createControllers();
        createAction();
        createObserver();

        loadDefault();

        myFrame.pack();
        myFrame.setVisible(true);
    }

    private void loadDefault()
    {
        AdvIOM instance = AdvIOM.getAdv();
        ArrayList<String> egxNames = instance.getAllFilesName("EGX", true);
        for (String egxName : egxNames)
        {
            // Load the corresponding EGX
            EGX egx = instance.loadEGX("EGX/" + egxName);
            egx = Converter.convertIfNeededToGXElements(egx);
            GTools.applyRendererProperties(myFrame.elementRenderer, egx);
            m.addListModel.addGroups(egx);
        }
        myFrame.addView.addListPanel.updateLength();
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
        m.copyModel = new CopyModel();
        m.entryFinder = new EntryFinder(myFrame.elementRenderer);
        m.elementFinder = new ElementFinder();
        m.sceneModel = new SceneModel(m.elementFinder);
        m.testsModel = new TestsModel();
    }

    private void createControllers()
    {
        addController = new AddController(myFrame, m.addListModel);
        consoleController = new ConsoleController(myFrame);
        elementsController = new ElementsController(myFrame, m.script, m.selectionModel);
        testsController = new TestsController(myFrame, m);
        sceneController = new SceneController(m.script, myFrame, m.selectionModel, m.sceneModel, m.elementFinder, m.entryFinder);

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
