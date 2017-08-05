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
    private double translateX;
    private double translateY;
    private double scale = 1;

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

    public double getTranslateX()
    {
        return translateX;
    }

    public void setTranslateX(double translateX)
    {
        this.translateX = translateX;
    }

    public double getTranslateY()
    {
        return translateY;
    }

    public void setTranslateY(double translateY)
    {
        this.translateY = translateY;
    }

    public double getScale()
    {
        return scale;
    }

    public double setScale(double desiredScale)
    {
        // Check if desired scale is valid
        if (desiredScale < 0.01f)
        {
            desiredScale = 0.01f;
        }
        else if (desiredScale > 5f)
        {
            desiredScale = 5f;
        }

        double scaledFactor = desiredScale/this.scale;
        this.scale = desiredScale;
        return scaledFactor;
    }

    /**
     * Scale the scene from the center (0, 0)
     * @param factor scale factor
     */
    public double scaleScene(double factor)
    {
        return setScale(scale * factor);
    }

    /**
     * Scale the scene from a different point in the pane coordinates
     * @param factor scale factor
     * @param paneX X in pane coordinates
     * @param paneY Y in pane coordinates
     */
    public void scalePane(double factor, double paneX, double paneY)
    {
        double oldScale = this.scale;
        factor = scaleScene(factor);

        double xScaled = scaleD(translateX * oldScale, paneX, factor);
        double yScaled = scaleD(translateY * oldScale, paneY, factor);

        setTranslateX(xScaled / this.scale);
        setTranslateY(yScaled / this.scale);
    }

    /**
     * Scale the scene from a different point in the scene coordinates
     * @param factor scale factor
     * @param sceneX X in pane coordinates
     * @param sceneY Y in pane coordinates
     */
    public void scaleScene(double factor, double sceneX, double sceneY)
    {
        double paneX = computeXInPane(sceneX);
        double paneY = computeYInPane(sceneY);

        scalePane(factor, paneX, paneY);
    }

    public double scaleD(double v, double center, double factor)
    {
        return (v - center) * factor + center;
    }

    /**
     * Compute the corresponding in scene location from pane location
     * @param paneX pane location
     * @return scene x location
     */
    public double computeXInScene(double paneX)
    {
        return (paneX / this.scale) - this.translateX;
        //return (paneX - this.translateX) / this.scale;
    }

    /**
     * Compute the corresponding in scene location from pane location
     * @param paneY pane location
     * @return scene y location
     */
    public double computeYInScene(double paneY)
    {
        return (paneY / this.scale) - this.translateY;
        //return (paneY - this.translateY) / this.scale;
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
        //return (sceneX + this.translateX) * this.scale;
        return sceneX * this.scale + this.translateX;
    }

    /**
     * Compute the corresponding in scene location from pane location
     * @param sceneY scene location
     * @return pane y location
     */
    public double computeYInPane(double sceneY)
    {
        //return (sceneY + this.translateY) * scale;
        return sceneY * this.scale + this.translateY;
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

    public void resetCamera()
    {
        translateX = 0;
        translateY = 0;
    }

    public void set(double translateX, double translateY, double scale)
    {
        this.translateX = translateX;
        this.translateY = translateY;
        this.scale = scale;
    }
}
