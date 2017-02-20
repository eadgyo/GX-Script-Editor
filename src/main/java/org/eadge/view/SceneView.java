package org.eadge.view;

import org.eadge.ConstantsView;
import org.eadge.model.frame.SceneModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by eadgyo on 15/02/17.
 */
public class SceneView extends JPanel
{
    private SceneModel sceneModel;

    public SceneView()
    {
        sceneModel = new SceneModel();
        setPreferredSize(new Dimension(ConstantsView.PREFERRED_DRAW_SIZE_WIDTH, ConstantsView.PREFERRED_DRAW_SIZE_HEIGHT));
    }

    public SceneModel getSceneModel()
    {
        return sceneModel;
    }

    public void setSceneModel(SceneModel sceneModel)
    {
        this.sceneModel = sceneModel;
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        // Clear background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
