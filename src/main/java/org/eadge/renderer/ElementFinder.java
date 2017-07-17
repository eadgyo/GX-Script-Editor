package org.eadge.renderer;

import org.eadge.model.global.SelectionModel;
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
    private Set<MutableTreeNode> elements;

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

    public void setElements(Set<MutableTreeNode> elements)
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

    /**
     * Get first element in the selection square with advance selection for the first element
     *
     * @param mouseRect square selecting elements
     * @param selectionModel selection model used to determine which element is already selected
     */
    public MutableTreeNode retrieveFirstElementOut(Rect2D mouseRect, SelectionModel selectionModel)
    {
        // Retrieve all selected elements using mouse square rect
        Set<MutableTreeNode> mutableTreeNodes = retrieveElements(mouseRect);
        Set<MutableTreeNode> notSelectedElements = selectionModel.getNotSelectedElements(mutableTreeNodes);

        // Iter
        myIter:
        for (MutableTreeNode testedParent : mutableTreeNodes)
        {
            // Check if the element is not already selected or there a no other element to select in the area
            if (notSelectedElements.contains(testedParent) || notSelectedElements.size() == 0)
            {
                // If element has children
                if (!testedParent.isLeaf())
                {
                    // Check if there is already one sub element not selected
                    for (MutableTreeNode mutableTreeNode : notSelectedElements)
                    {
                        if (selectionModel.isParent(testedParent, mutableTreeNode))
                        {
                            // One sub element is not selected, select it first
                            continue myIter;
                        }
                    }
                    // No sub element not selected
                }

                return testedParent;
            }
        }

        return null;
    }

    /**
     * Get first element in the selection square with advance selection for the first element
     *
     * @param mouseRect square selecting elements
     * @param selectionModel selection model used to determine which element is already selected
     */
    public MutableTreeNode retrieveFirstElementIn(Rect2D mouseRect, SelectionModel selectionModel)
    {
        // Retrieve all selected elements using mouse square rect
        Set<MutableTreeNode> mutableTreeNodes = retrieveElements(mouseRect);
        Set<MutableTreeNode> selectedElements = selectionModel.getAlreadySelectedElements(mutableTreeNodes);

        // Get first if possible already selected element
        if (selectedElements.iterator().hasNext())
        {
            return selectedElements.iterator().next();
        }
        else if (mutableTreeNodes.iterator().hasNext())
        {
            return retrieveFirstElementOut(mouseRect, selectionModel);
        }
        return null;
    }
}
