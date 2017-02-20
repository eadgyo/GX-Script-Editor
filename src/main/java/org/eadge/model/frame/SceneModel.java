package org.eadge.model.frame;

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
}
