package org.eadge.model.script;

import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.renderer.Rect2D;
import org.eadge.renderer.Rect2DInter;
import org.eadge.utils.AColor;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
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
    private Color backgroundColor = new AColor(60, 150, 100, 175);
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

    @Override
    public GXLayer clone()
    {
        GXLayer clone = (GXLayer) super.clone();
        clone.backgroundColor = new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), backgroundColor.getAlpha());
        //noinspection unchecked
        clone.children = new Vector(children);
        return clone;
    }

    /**
     * Replace children, after creating gxEntity clone. You need to copy all entities first.
     * @param replacementMap used to replace entities (do use for node)
     */
    public void replaceChildren(Map<MutableTreeNode, MutableTreeNode> replacementMap)
    {
        for (int i = 0; i < getChildCount(); i++)
        {
            TreeNode child = getChildAt(i);
            MutableTreeNode treeNode = replacementMap.get(child);
            treeNode.setParent(this);
            if (child instanceof GXLayer)
            {
                // Get replacementNode
                GXLayer layer = (GXLayer) treeNode;

                // Replace child at the given index
                setChildAt(i, layer);

                // Replace children for this layer
                layer.replaceChildren(replacementMap);
            }
            else if (child instanceof GXElement)
            {
                // Get replacementNode
                GXElement element = (GXElement) treeNode;

                // Replace child at the given index
                setChildAt(i, element);

                // Replace entities
                element.replaceEntities(replacementMap);
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
                entities.add(((GXElement) child));
            }
        }
    }

    public void setChildAt(int i, MutableTreeNode mutableTreeNode)
    {
        this.children.set(i, mutableTreeNode);
    }

    public void setX(double x)
    {
        double x1 = this.getX();
        translateX(x - x1);
    }

    public void setY(double y)
    {
        double y1 = this.getY();
        translateY(y - y1);
    }

    public void setPos(double x, double y)
    {
        setX(x);
        setY(y);
    }

    public void setCenterX(double centerX)
    {
        translateX(centerX - getCenterX());
    }

    public void setCenterY(double centerY)
    {
        translateY(centerY - getCenterY());
    }

    public void setCenter(double centerX, double centerY)
    {
        setCenterX(centerX);
        setCenterY(centerY);
    }

    public double getCenterX()
    {
        return getX() + getWidth() * 0.5;
    }

    public double getCenterY()
    {
        return getY() + getHeight() * 0.5;
    }

    public void setWidth(double width)
    {
        System.out.println("Can't change width");
    }

    public void setHeight(double height)
    {
        System.out.println("Can't change height");
    }

    public void setLength(double width, double height)
    {
        setWidth(width);
        setHeight(height);
    }

    public double getMinX()
    {
        if (children == null || children.size() == 0)
            return 0;

        double v = Double.MAX_VALUE;
        for (Object child : children)
        {
            Rect2DInter rect = (Rect2DInter) child;
            if (rect.getX() < v)
                v = rect.getX();
        }
        return v;
    }

    public double getMaxX()
    {
        if (children == null || children.size() == 0)
            return 0;

        double v = -Double.MAX_VALUE;
        for (Object child : children)
        {
            Rect2DInter rect = (Rect2DInter) child;
            if (rect.getX() + rect.getWidth() > v)
                v = rect.getX() + rect.getWidth();
        }
        return v;
    }

    public double getMinY()
    {
        if (children == null || children.size() == 0)
            return 0;

        double v = Double.MAX_VALUE;
        for (Object child : children)
        {
            Rect2DInter rect = (Rect2DInter) child;
            if (rect.getY() < v)
                v = rect.getY();
        }
        return v;
    }

    public double getMaxY()
    {
        if (children == null || children.size() == 0)
            return 0;

        double v = -Double.MAX_VALUE;
        for (Object child : children)
        {
            Rect2DInter rect = (Rect2DInter) child;
            if (rect.getY() + rect.getHeight()> v)
                v = rect.getY() + rect.getHeight();
        }
        return v;
    }

    public double getX()
    {
        return getMinX();
    }

    public double getY()
    {
        return getMinY();
    }

    public double getWidth()
    {
        return getMaxX() - getMinX();
    }

    public double getHeight()
    {
        return getMaxY() - getMinY();
    }

    public void setRect(double v, double v1, double v2, double v3)
    {
        System.out.println("Can't change rect Layer");
    }

    public void setRect(Rectangle2D rectangle2D)
    {
        System.out.println("Can't chang rect layer");
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
    public Rect2D getRect2D()
    {
        double x = getMinX();
        double y = getMinY();
        double xMax = getMaxX();
        double yMax = getMaxY();
        return new Rect2D(x, y, xMax - x, yMax - y);
    }

    @Override
    public void translateX(double x)
    {
        // Translate children
        for (Object child: children)
        {
            Rect2DInter rect = (Rect2DInter) child;
            rect.translateX(x);
        }
    }

    @Override
    public void translateY(double y)
    {
        // Translate children
        for (Object child: children)
        {
            Rect2DInter rect = (Rect2DInter) child;
            rect.translateY(y);
        }
    }

    @Override
    public void translate(double x, double y)
    {
        translateX(x);
        translateY(y);
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public void setUserObject(Object o)
    {
        if (o instanceof String)
        {
            name = (String) o;
        }
    }
}
