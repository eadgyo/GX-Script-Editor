package org.eadge.renderer;

import java.awt.geom.Rectangle2D;

/**
 * Created by eadgyo on 19/02/17.
 */
public class Rect2D extends Rectangle2D.Double implements Rect2DInter, Cloneable
{
    public Rect2D()
    {
    }

    public Rect2D(double v, double v1, double v2, double v3)
    {
        super(v, v1, v2, v3);
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void setPos(double x, double y)
    {
        setX(x);
        setY(y);
    }

    public void setCenterX(double centerX)
    {
        this.x = centerX - width * 0.5;
    }

    public void setCenterY(double centerY)
    {
        this.y = centerY - height * 0.5;
    }

    public void setCenter(double centerX, double centerY)
    {
        setCenterX(centerX);
        setCenterY(centerY);
    }

    public void setWidth(double width)
    {
        this.width = width;
    }

    public void setHeight(double height)
    {
        this.height = height;
    }

    public void setLength(double width, double height)
    {
        setWidth(width);
        setHeight(height);
    }

    @Override
    public Rect2D getRect2D()
    {
        return this;
    }

    @Override
    public void translateX(double x)
    {
        this.x += x;
    }

    @Override
    public void translateY(double y)
    {
        this.y += y;
    }

    @Override
    public void translate(double x, double y)
    {
        translateX(x);
        translateY(y);
    }
}
