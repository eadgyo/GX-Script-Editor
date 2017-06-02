package org.eadge.model.global;

import org.eadge.model.global.project.SelectionObservable;

import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.MutableTreeNode;
import java.util.Collection;
import java.util.HashSet;
import java.util.Observer;

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
     * Make this selectionModel observable
     */
    private SelectionObservable selectionObservable = new SelectionObservable();

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

    public void addSelectedElement(MutableTreeNode node)
    {
        selectedElements.add(node);
    }

    public void removeSelectedElement(MutableTreeNode node)
    {
        selectedElements.remove(node);
    }

    public void addObserver(Observer observer) { selectionObservable.addObserver(observer); }

    public void callObservers() { selectionObservable.callObservers(); }

    public SelectionModel(ConnectionModel connectionModel)
    {
        this.setConnectionModel(connectionModel);
    }

    public MutableTreeNode getFirstSelectedElement()
    {
        return selectedElements.isEmpty() ? null : selectedElements.iterator().next();
    }



    public Collection<MutableTreeNode> getSelectedElements()
    {
        return selectedElements;
    }

    public void setSelectedElements(Collection<MutableTreeNode> selectedElements)
    {
        this.selectedElements.clear();
        this.selectedElements.addAll(selectedElements);

    }

    public void setSelectedElements(MutableTreeNode selectedElement)
    {
        this.selectedElements.clear();
        this.selectedElements.add(selectedElement);
    }

    public void clearSelectedElements()
    {
        this.selectedElements.clear();
    }

    public boolean hasSelectedElements()
    {
        return !this.selectedElements.isEmpty();
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

    public boolean isChanging(MutableTreeNode insertedNode, MutableTreeNode insertedElement)
    {
        return insertedNode != insertedElement.getParent();
    }

    public SelectionState getSelectionState()
    {
        return selectionState;
    }

    public boolean isSelectionStateEquals(SelectionState selectionState)
    {
        return this.selectionState.equals(selectionState);
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
        this.connectionModel.setSelectionObservable(selectionObservable);
    }

    public boolean isActionValid()
    {
        return isActionValid;
    }

    public void setActionValid(boolean actionValid)
    {
        isActionValid = actionValid;
    }

    /**
     * Check if MyGroupsOfElements node is used in selection process
     * @param node checked node
     * @return true if the node is selected, false otherwise
     */
    public boolean contains(MutableTreeNode node)
    {
        return selectedElements.contains(node) || onDragElement == node;
    }

}
