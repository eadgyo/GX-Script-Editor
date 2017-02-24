package org.eadge.model.script;

import org.eadge.renderer.Rect2D;
import org.eadge.renderer.Rect2DInter;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by eadgyo on 20/02/17.
 *
 * Holds GXElements
 */
public class GXLayer extends DefaultMutableTreeNode implements Rect2DInter
{
    private Rect2D rect2D;
    private Color backgroundColor;
    private boolean isDisplayed = true;
    private boolean isRectDisplayed = true;

    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    public Rect2D getRect2D()
    {
        return rect2D;
    }

    public void setRect2D(Rect2D rect2D)
    {
        this.rect2D = rect2D;
    }

    public void setX(double x)
    {

        rect2D.setX(x);
    }

    public void setY(double y)
    {
        rect2D.setY(y);
    }

    public void setPos(double x, double y)
    {
        rect2D.setPos(x, y);
    }

    public void setCenterX(double centerX)
    {
        rect2D.setCenterX(centerX);
    }

    public void setCenterY(double centerY)
    {
        rect2D.setCenterY(centerY);
    }

    public void setCenter(double centerX, double centerY)
    {
        rect2D.setCenter(centerX, centerY);
    }

    public void setWidth(double width)
    {
        rect2D.setWidth(width);
    }

    public void setHeight(double height)
    {
        rect2D.setHeight(height);
    }

    public void setLength(double width, double height)
    {
        rect2D.setLength(width, height);
    }

    public double getX()
    {
        return rect2D.getX();
    }

    public double getY()
    {
        return rect2D.getY();
    }

    public double getWidth()
    {
        return rect2D.getWidth();
    }

    public double getHeight()
    {
        return rect2D.getHeight();
    }

    public void setRect(double v, double v1, double v2, double v3)
    {
        rect2D.setRect(v, v1, v2, v3);
    }

    public void setRect(Rectangle2D rectangle2D)
    {
        rect2D.setRect(rectangle2D);
    }

    public boolean isDisplayed()
    {
        return isDisplayed;
    }

    public void setDisplayed(boolean displayed)
    {
        isDisplayed = displayed;
    }

    public boolean isRectDisplayed()
    {
        return isRectDisplayed;
    }

    public void setRectDisplayed(boolean rectDisplayed)
    {
        isRectDisplayed = rectDisplayed;
    }
}
