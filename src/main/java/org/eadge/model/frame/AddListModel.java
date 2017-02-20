package org.eadge.model.frame;

import org.eadge.model.script.MyElement;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.Observable;
import java.util.Vector;

/**
 * Created by eadgyo on 19/02/17.
 */
public class AddListModel extends Observable implements ListModel
{
    public class MyGroup extends Vector<MyElement>
    {
        String name;

        public MyGroup(String name)
        {
            this.name = name;
        }
    }

    public class MyGroupsOfElements extends Vector<MyGroup>
    {
        public int size(int groupIndex)
        {
            return get(groupIndex).size();
        }

        public MyElement get(int groupIndex, int elementIndex)
        {
            return get(groupIndex).get(elementIndex);
        }
    }

    private MyGroupsOfElements myGroupsOfElements;
    private int selectedGroup = -1;
    private int selectedElement = -1;

    public int getNumberOfGroups()
    {
        return myGroupsOfElements.size();
    }

    @Override
    public int getSize()
    {
        return myGroupsOfElements.size(selectedGroup);
    }

    @Override
    public Object getElementAt(int elementIndex)
    {
        return myGroupsOfElements.get(selectedGroup, elementIndex);
    }

    @Override
    public void addListDataListener(ListDataListener listDataListener)
    {

    }

    @Override
    public void removeListDataListener(ListDataListener listDataListener)
    {

    }

    public MyElement getSelectedElement()
    {
        return myGroupsOfElements.get(selectedGroup, selectedElement);
    }

    public MyGroup getSelectedGroup()
    {
        return myGroupsOfElements.get(selectedGroup);
    }

    public int getSelectedGroupIndex()
    {
        return selectedGroup;
    }

    public int getSelectedElementIndex()
    {
        return selectedElement;
    }

    public void setSelectedGroup(int selectedGroup)
    {
        this.selectedGroup = selectedGroup;

        setChanged();
        notifyObservers();
    }

    public void setSelectedElement(int selectedElement)
    {
        this.selectedElement = selectedElement;

        setChanged();
        notifyObservers();
    }

    public void clearSelection()
    {
        setSelectedElement(-1);
    }
}
