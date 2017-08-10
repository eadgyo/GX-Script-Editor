package org.eadge.controller;

import org.eadge.controller.frame.*;
import org.eadge.gxscript.data.compile.script.RawGXScript;
import org.eadge.gxscript.data.entity.classic.entity.displayer.PrintGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.imbrication.loops.ForGXEntity;
import org.eadge.gxscript.data.io.EGX;
import org.eadge.model.Models;
import org.eadge.model.frame.AddListModel;
import org.eadge.model.frame.SceneModel;
import org.eadge.model.frame.TestsModel;
import org.eadge.model.global.ConnectionModel;
import org.eadge.model.global.CopyModel;
import org.eadge.model.global.SelectionModel;
import org.eadge.model.global.project.FileModel;
import org.eadge.model.script.GXElement;
import org.eadge.model.script.GXLayer;
import org.eadge.model.script.GXLayerModel;
import org.eadge.model.script.Script;
import org.eadge.renderer.ElementFinder;
import org.eadge.renderer.EntryFinder;
import org.eadge.utils.AdvIOM;
import org.eadge.utils.Converter;
import org.eadge.utils.GTools;
import org.eadge.view.MyFrame;

import javax.swing.tree.MutableTreeNode;
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
    private Actions a = new Actions();

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

        test2();

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
        if (m.addListModel.getNumberOfElements() > 0)
            m.addListModel.setSelectedElement(0);
    }

    private void createView()
    {
        myFrame = new MyFrame();
    }

    private void createModels()
    {
        m.rawGXScript = new RawGXScript();
        m.gxLayerModel = new GXLayerModel(new GXLayer("Root"));
        m.elementFinder = new ElementFinder();
        m.script = new Script(m.rawGXScript, m.gxLayerModel, m.elementFinder);
        m.addListModel = new AddListModel();
        m.connectionModel = new ConnectionModel();
        m.selectionModel = new SelectionModel(m.connectionModel);
        m.fileModel = new FileModel();
        m.copyModel = new CopyModel();
        m.entryFinder = new EntryFinder(myFrame.elementRenderer);
        m.sceneModel = new SceneModel(m.elementFinder);
        m.testsModel = new TestsModel();
    }

    private void test1()
    {
        MutableTreeNode root = (MutableTreeNode) m.script.getLayeredScript().getRoot();

        // Create one layer
        GXLayer layer = new GXLayer("Test1");
        m.script.addLayer(layer, root);

        // Create second layer
        GXLayer layer2 = new GXLayer("Moveable");
        m.script.addLayer(layer2, root);

        // Add one element
        m.script.addEntity(m.addListModel.getSelectedElement().deepClone(), layer);
    }

    private void test2()
    {
        MutableTreeNode root          = (MutableTreeNode) m.script.getLayeredScript().getRoot();
        ForGXEntity     forEntity     = new ForGXEntity();
        PrintGXEntity   printGXEntity = new PrintGXEntity();

        GXElement forElement = new GXElement(forEntity);
        GXElement printElement = new GXElement(printGXEntity);

        printElement.linkAsInput(PrintGXEntity.NEXT_INPUT_INDEX, ForGXEntity.DO_OUTPUT_INDEX, forElement);
        printElement.linkAsInput(PrintGXEntity.SOURCE_INPUT_INDEX, ForGXEntity.INDEX_FOR_OUTPUT_INDEX, forElement);


        forElement.computeSize(myFrame.elementRenderer);
        printElement.computeSize(myFrame.elementRenderer);

        m.script.addEntity(forElement, root);
        m.script.addEntity(printElement, root);

    }

    private void createControllers()
    {
        addController = new AddController(myFrame, m.addListModel, a);
        consoleController = new ConsoleController(myFrame, a);
        elementsController = new ElementsController(myFrame, m.script, m.selectionModel, a);
        testsController = new TestsController(myFrame, m, a);
        sceneController = new SceneController(m.script, myFrame, m.selectionModel, m.sceneModel, m.elementFinder, m
                .entryFinder, a);

        mainController = new MainController(myFrame, m, a);
    }

    private void createAction()
    {
    }

    private void createObserver()
    {
        m.selectionModel.addObserver(new SelectionChangeAction());
        m.script.addObserver(new ScriptChangeAction());
    }

    /**
     * Handle script change and call observer to update frame
     */
    private class ScriptChangeAction implements Observer
    {
        @Override
        public void update(Observable observable, Object o)
        {
            // Redraw scene
            myFrame.sceneView.repaint();
            myFrame.elementsView.repaint();
        }
    }

    /**
     * Handle selection change and call observer to update frame
     */
    private class SelectionChangeAction implements Observer
    {
        @Override
        public void update(Observable observable, Object o)
        {
            // Redraw scene
            myFrame.sceneView.repaint();
            myFrame.elementsView.repaint();
        }
    }
}
