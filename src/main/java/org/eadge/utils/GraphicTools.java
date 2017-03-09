package org.eadge.utils;

import org.eadge.renderer.Rect2D;

import javax.swing.tree.MutableTreeNode;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by eadgyo on 09/03/17.
 */
public class GraphicTools
{
    public static Rect2D createRectFromNodes(Collection<MutableTreeNode> nodes)
    {
        if (nodes.size() == 0)
            return new Rect2D();

        double leftX, leftY, rightX, rightY;

        Iterator<MutableTreeNode> iterator = nodes.iterator();

        // Init with first element
        MutableTreeNode next = iterator.next();
        Rect2D rect2D = (Rect2D) next;
        leftX = rect2D.getX();
        leftY = rect2D.getY();
        rightX = rect2D.getX() + rect2D.getWidth();
        rightY = rect2D.getY() + rect2D.getHeight();

        // Check all elements bounds
        while (iterator.hasNext())
        {
            next = iterator.next();
            rect2D = (Rect2D) next;
            leftX = rect2D.getX();
            leftY = rect2D.getY();
            rightX = rect2D.getX() + rect2D.getWidth();
            rightY = rect2D.getY() + rect2D.getHeight();
        }

        return new Rect2D(leftX, leftY, rightX - leftX, rightY - leftY);
    }

    public static void translateElements(double x, double y, Collection<MutableTreeNode> nodes)
    {
        for (MutableTreeNode node : nodes)
        {
            Rect2D rect2d = (Rect2D) node;
            rect2d.translate(x, y);
        }
    }

    public static void moveElementsTo(double leftDestX, double leftDestY, Collection<MutableTreeNode> nodes)
    {
        // Create rect containing elements
        Rect2D rectFromNodes = GraphicTools.createRectFromNodes(nodes);

        // Compute translate vector
        double translateX = leftDestX - rectFromNodes.getX();
        double translateY = leftDestY - rectFromNodes.getY();

        // Translate all nodes
        translateElements(translateX, translateY, nodes);
    }

}
