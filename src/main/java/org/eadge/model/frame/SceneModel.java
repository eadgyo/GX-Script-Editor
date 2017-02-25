package org.eadge.model.frame;

import org.eadge.renderer.ElementFinder;
import org.eadge.renderer.Rect2D;

import javax.swing.tree.MutableTreeNode;
import java.util.Collection;

/**
 * Created by eadgyo on 20/02/17.
 *
 * Holds scene state
 */
public class SceneModel
{
    private int translateX;
    private int translateY;

    private float scale;

    private ElementFinder elementFinder;

    public SceneModel(ElementFinder elementFinder)
    {
        this.elementFinder = elementFinder;
    }

    public ElementFinder getElementFinder()
    {
        return elementFinder;
    }

    public void setElementFinder(ElementFinder elementFinder)
    {
        this.elementFinder = elementFinder;
    }

    public Collection<MutableTreeNode> findElementsInScene(double paneWidth, double paneHeight)
    {
        return elementFinder.retrieveElements(createRect(paneWidth, paneHeight));
    }

    public Rect2D createRect(double width, double height)
    {
        double paneX = computeXInScene(0);
        double paneY = computeYInScene(0);
        double paneWidth = computeHeightInScene(width);
        double paneHeight = computeHeightInScene(height);
        return new Rect2D(paneX, paneY, paneWidth, paneHeight);
    }

    public int getTranslateX()
    {
        return translateX;
    }

    public void setTranslateX(int translateX)
    {
        this.translateX = translateX;
    }

    public int getTranslateY()
    {
        return translateY;
    }

    public void setTranslateY(int translateY)
    {
        this.translateY = translateY;
    }

    public float getScale()
    {
        return scale;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }

    /**
     * Compute the corresponding in scene location from pane location
     * @param paneX pane location
     * @return scene x location
     */
    public double computeXInScene(double paneX)
    {
        return (paneX - this.translateX) / scale;
    }

    /**
     * Compute the corresponding in scene location from pane location
     * @param paneY pane location
     * @return scene y location
     */
    public double computeYInScene(double paneY)
    {
        return (paneY - this.translateY) / scale;
    }

    /**
     * Transform size from pane to scene size
     * @param paneSize pane size
     * @return scene size
     */
    public double computeHeightInScene(double paneSize)
    {
        return paneSize / scale;
    }


    /**
     * Compute the corresponding in scene location from pane location
     * @param sceneX scene location
     * @return pane x location
     */
    public double computeXInPane(double sceneX)
    {
        return (sceneX * scale) + this.translateX;
    }

    /**
     * Compute the corresponding in scene location from pane location
     * @param sceneY scene location
     * @return pane y location
     */
    public double computeYInPane(double sceneY)
    {
        return (sceneY * scale) + this.translateY;
    }

    /**
     * Transform size from size to pane size
     * @param sceneSize scene size
     * @return pane size
     */
    public double computeSizeInPane(double sceneSize)
    {
        return sceneSize * scale;
    }

    /**
     * Translate the scene on x axis
     * @param x translated value on x axis
     */
    public void translateX(double x)
    {
        translateX += x;
    }

    /**
     * Translate the scene on y axis
     * @param y translated value on y axis
     */
    public void translateY(double y)
    {
        translateY += y;
    }
}
