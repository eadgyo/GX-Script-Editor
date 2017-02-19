package org.eadge.renderer;

import java.awt.geom.Rectangle2D;

/**
 * Created by eadgyo on 19/02/17.
 */
public class Rect2D extends Rectangle2D.Double
{
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
}
