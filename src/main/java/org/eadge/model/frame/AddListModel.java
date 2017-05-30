package org.eadge.model.frame;

import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.gxscript.data.io.EGX;
import org.eadge.gxscript.data.io.EGXGroup;
import org.eadge.model.frame.addlist.MyGroupsOfElements;
import org.eadge.model.script.GXElement;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.Observable;

/**
 * Created by eadgyo on 19/02/17.
 */
public class AddListModel extends Observable implements ListModel
{
    private MyGroupsOfElements els = new MyGroupsOfElements();

    private MyComboBoxModel comboBoxModel = new MyComboBoxModel();

    private int selectedGroup = -1;
    private int selectedElement = -1;

    /**
     * Add collection of elements to group
     * @param egxGroup added element group
     */
    public void addGroup(EGXGroup egxGroup)
    {
        els.add(egxGroup);

        selectedGroup = 0;

        setChanged();
        notifyObservers();
    }

    public void addGroups(EGX egx)
    {
        for (EGXGroup egxGroup : egx.values())
        {
            addGroup(egxGroup);
        }
    }

    public void removeGroup(int egxGroup)
    {
        els.remove(egxGroup);
    }

    public void addEntity(int groupIndex, GXEntity gxEntity)
    {
        els.addEntity(groupIndex, gxEntity);
    }

    public void removeEntity(int groupIndex, GXEntity gxEntity)
    {
        els.removeEntity(groupIndex, gxEntity);
    }

    public void removeEntity(int groupIndex, int entityIndex)
    {
        els.removeEntity(groupIndex, entityIndex);
    }

    public void removeEntity(String groupName, GXEntity gxEntity)
    {
        els.removeEntity(groupName, gxEntity);
    }

    public void removeEntity(String groupName, int entityIndex)
    {
        els.removeEntity(groupName, entityIndex);
    }

    public int getNumberOfGroups()
    {
        return els.size();
    }

    public int getNumberOfElements(int groupIndex) { return els.size(groupIndex); }

    public int getNumberOfElements() { return getSize(); }

    @Override
    public int getSize()
    {
        return els.size(selectedGroup);
    }

    @Override
    public Object getElementAt(int elementIndex)
    {
        return els.get(selectedGroup, elementIndex);
    }

    @Override
    public void addListDataListener(ListDataListener listDataListener)
    {

    }

    @Override
    public void removeListDataListener(ListDataListener listDataListener)
    {

    }

    public ComboBoxModel<String> getComboBoxModel()
    {
        return comboBoxModel;
    }

    public GXElement getSelectedElement()
    {
        return els.get(selectedGroup, selectedElement);
    }

    public EGXGroup getSelectedGroup()
    {
        return els.get(selectedGroup);
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


    private class MyComboBoxModel implements ComboBoxModel<String>
    {
        @Override
        public void setSelectedItem(Object o)
        {
            selectedGroup = els.size() - 1;
            while (selectedGroup != -1 && !els.get(selectedGroup).getName().equals(o))
            {
                selectedGroup--;
            }
        }

        @Override
        public Object getSelectedItem()
        {
            return selectedGroup == -1 ? null : getSelectedGroup().getName();
        }

        @Override
        public int getSize()
        {
            return els.size();
        }

        @Override
        public String getElementAt(int i)
        {
            return i == -1 ? "" : els.get(i).getName();
        }

        @Override
        public void addListDataListener(ListDataListener listDataListener)
        {
        }

        @Override
        public void removeListDataListener(ListDataListener listDataListener)
        {
        }
    };

    public boolean isSelectedElement() {
        return getSelectedElementIndex() != -1;
    }
}
