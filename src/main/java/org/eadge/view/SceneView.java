package org.eadge.view;

import org.eadge.ConstantsView;
import org.eadge.model.frame.SceneModel;
import org.eadge.model.global.SelectionModel;
import org.eadge.renderer.frame.SceneRenderer;

import javax.swing.*;
import javax.swing.tree.MutableTreeNode;
import java.awt.*;
import java.util.Collection;

/**
 * Created by eadgyo on 15/02/17.
 */
public class SceneView extends JPanel
{
    private SceneModel sceneModel;
    private SceneRenderer sceneRenderer;
    private SelectionModel selectionModel;


    public SceneView()
    {
        Dimension dimension = new Dimension(ConstantsView.PREFERRED_DRAW_SIZE_WIDTH,
                                            ConstantsView.PREFERRED_DRAW_SIZE_HEIGHT);
        setPreferredSize(dimension);
//        setMinimumSize(dimension);
    }

    public SceneModel getSceneModel()
    {
        return sceneModel;
    }

    public void setSceneModel(SceneModel sceneModel)
    {
        this.sceneModel = sceneModel;
    }

    public void setSceneRenderer(SceneRenderer sceneRenderer)
    {
        this.sceneRenderer = sceneRenderer;
    }

    public void setSelectionModel(SelectionModel selectionModel)
    {
        this.selectionModel = selectionModel;
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        Collection<MutableTreeNode> elementsInScene = sceneModel.findElementsInScene(getWidth(), getHeight());
        sceneRenderer.paint((Graphics2D) graphics, getWidth(), getHeight(), elementsInScene, sceneModel, selectionModel);
    }
}
