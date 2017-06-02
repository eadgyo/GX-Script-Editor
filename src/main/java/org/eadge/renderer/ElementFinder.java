package org.eadge.renderer;

import org.eadge.model.script.GXLayer;

import javax.swing.tree.MutableTreeNode;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eadgyo on 24/02/17.
 *
 * Select elements using MyGroupsOfElements square
 */
public class ElementFinder
{
    private Collection<MutableTreeNode> elements;

    public ElementFinder()
    {
        this.elements = new HashSet<>();
    }

    /**
     * Retrieve all elements in the selection square
     *
     * @param selectionSquare square selecting elements
     */
    public Set<MutableTreeNode> retrieveElements(Rect2D selectionSquare)
    {
        HashSet<MutableTreeNode> inSquareElements = new HashSet<>();

        // Find all elements in this square
        for (MutableTreeNode node : elements)
        {
            Rect2DInter element = (Rect2DInter) node;

            if (selectionSquare.intersects(element.getRect2D()))
            {
                inSquareElements.add(node);
            }
        }

        return inSquareElements;
    }

    public void addElement(MutableTreeNode node)
    {
        elements.add(node);
    }

    public void removeElement(MutableTreeNode node)
    {
        elements.remove(node);
    }

    public void setElements(Collection<MutableTreeNode> elements)
    {
        this.elements = elements;
    }

    public void setElementsFromRoot(GXLayer root)
    {
        elements = new HashSet<>();
        addElementRec(root, elements);
    }

    private void addElementRec(MutableTreeNode node, Collection<MutableTreeNode> nodes)
    {
        for (int i = 0; i < node.getChildCount(); i++)
        {
            // Get the child
            MutableTreeNode child = (MutableTreeNode) node.getChildAt(i);
            nodes.add(child);

            // Get sub elements rec
            addElementRec(child, nodes);
        }
    }

    /**
     * Get first element in the selection square
     *
     * @param selectionSquare square selecting elements
     */
    public MutableTreeNode retrieveFirstElement(Rect2D selectionSquare)
    {
        // Find all elements in this square
        for (MutableTreeNode node : elements)
        {
            if (!(node instanceof GXLayer) || node.getChildCount() != 0)
            {
                Rect2DInter element = (Rect2DInter) node;

                if (selectionSquare.intersects(element.getRect2D()))
                {
                    return node;
                }
            }
        }

        return null;
    }

    public void clear()
    {
        elements.clear();
    }
}
