package org.eadge.model.script;

import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.renderer.Rect2D;
import org.eadge.renderer.Rect2DInter;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Created by eadgyo on 20/02/17.
 *
 * Holds GXElements
 */
public class GXLayer extends DefaultMutableTreeNode implements Rect2DInter
{
    private Rect2D rect2D = new Rect2D();
    private Color backgroundColor;
    private boolean isDisplayed = true;
    private boolean isRectDisplayed = true;
    private String name;

    public GXLayer(String name) {
        this.name = name;
    }

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

    @Override
    public Object clone()
    {
        GXLayer clone = (GXLayer) super.clone();
        clone.backgroundColor = new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), backgroundColor.getAlpha());
        clone.rect2D = (Rect2D) rect2D.clone();
        //noinspection unchecked
        clone.children = children;
        return clone;
    }

    /**
     * Replace children, after creating gxEntity clone. You need to copy all entities first.
     * @param gxEntityMap used to replace entities (do use for node)
     */
    public void replaceChildren(Map<GXEntity, GXEntity> gxEntityMap)
    {
        Vector oldChildren = this.children;
        this.children = new Vector<MutableTreeNode>();
        for (Object child : children)
        {
            if (child instanceof GXLayer)
            {
                // Copy the element
                GXLayer childLayer = (GXLayer) child;
                GXLayer clone      = (GXLayer) childLayer.clone();

                // Replace entities
                clone.replaceChildren(gxEntityMap);

                // Add this element
                //noinspection unchecked
                this.children.add(clone);
            }
            else if (child instanceof GXElement)
            {
                // Copy the element
                GXElement childElement = (GXElement) child;
                GXElement clone      = (GXElement) childElement.clone();

                // Replace entities
                clone.replaceEntity(gxEntityMap);

                // Add this element
                //noinspection unchecked
                this.children.add(clone);
            }
        }
    }

    /**
     * Add all entities in the set
     * @param entities list of entities
     */
    public void fillWithGXEntities(Set<GXEntity> entities)
    {
        for (Object child : children)
        {
            if (child instanceof GXLayer)
            {
                ((GXLayer) child).fillWithGXEntities(entities);
            }
            else if (child instanceof GXElement)
            {
                entities.add(((GXElement) child).getEntity());
            }
        }
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

    @Override
    public void translateX(double x)
    {
        rect2D.translateX(x);
    }

    @Override
    public void translateY(double y)
    {
        rect2D.translateY(y);
    }

    @Override
    public void translate(double x, double y)
    {
        rect2D.translate(x, y);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
