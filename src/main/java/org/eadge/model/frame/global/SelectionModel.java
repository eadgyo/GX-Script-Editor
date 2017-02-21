package org.eadge.model.frame.global;

import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.MutableTreeNode;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by eadgyo on 19/02/17.
 *
 * Holds selected elements and on dragged elements
 */
public class SelectionModel extends DefaultTreeSelectionModel
{
    public enum SelectionState
    {
        NONE,
        MOVING,
        CONNECTING
    };

    /**
     * Holds selected elements
     */
    private Collection<MutableTreeNode> selectedElements = new HashSet<>();

    /**
     * Store the element on mouse dragged
     */
    private MutableTreeNode onDragElement = null;

    /**
     * Holds the current state of the selection
     */
    private SelectionState selectionState = SelectionState.NONE;

    /**
     * Keeps the connecting input/outputs
     */
    private ConnectionModel connectionModel;

    /**
     * Store if the current action is valid
     */
    private boolean isActionValid = true;

    public SelectionModel(ConnectionModel connectionModel)
    {
        this.connectionModel = connectionModel;
    }

    public Collection<MutableTreeNode> getSelectedElements()
    {
        return selectedElements;
    }

    public void setSelectedElements(Collection<MutableTreeNode> selectedElements)
    {
        this.selectedElements = selectedElements;
    }

    public void clearSelectedElements()
    {
        this.selectedElements.clear();
    }

    public boolean hasSelectedElements()
    {
        return !this.selectedElements.isEmpty();
    }

    public void clearAndAddSelectedElements(Collection<MutableTreeNode> selectedElements)
    {
        this.selectedElements.clear();
        this.selectedElements.addAll(selectedElements);
    }

    public MutableTreeNode getOnDragElement()
    {
        return onDragElement;
    }

    public void setOnDragElement(MutableTreeNode onDragElement)
    {
        this.onDragElement = onDragElement;
    }

    public void clearDragElement()
    {
        onDragElement = null;
    }

    public boolean hasDragElement()
    {
        return onDragElement != null;
    }

    public boolean isParentOrEqual(MutableTreeNode parent, MutableTreeNode child)
    {
        return child == parent || isParent(parent, child);
    }

    public boolean isParent(MutableTreeNode parent, MutableTreeNode child)
    {
        MutableTreeNode treeNode = (MutableTreeNode) child.getParent();
        while (treeNode != null)
        {
            if (treeNode == parent)
                return true;
            treeNode = (MutableTreeNode) treeNode.getParent();
        }

        return false;
    }

    public SelectionState getSelectionState()
    {
        return selectionState;
    }

    public void setSelectionState(SelectionState selectionState)
    {
        this.selectionState = selectionState;
    }

    public ConnectionModel getConnectionModel()
    {
        return connectionModel;
    }

    public void setConnectionModel(ConnectionModel connectionModel)
    {
        this.connectionModel = connectionModel;
    }

    public boolean isActionValid()
    {
        return isActionValid;
    }

    public void setActionValid(boolean actionValid)
    {
        isActionValid = actionValid;
    }


}
