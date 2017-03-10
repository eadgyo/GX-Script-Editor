package org.eadge.model.frame.addlist;

import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.gxscript.data.io.EGX;
import org.eadge.gxscript.data.io.EGXGroup;
import org.eadge.model.script.GXElement;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by eadgyo on 10/03/17.
 */

public class MyGroupsOfElements extends Vector<EGXGroup>
{
    private EGX egx = new EGX();

    public int size(int groupIndex)
    {
        return get(groupIndex).size();
    }

    public GXElement get(int groupIndex, int elementIndex)
    {
        return (GXElement) get(groupIndex).get(elementIndex);
    }

    @Override
    public synchronized void addElement(EGXGroup egxGroup)
    {
        EGXGroup retGroup = egx.get(egxGroup.getName());

        if (retGroup == null)
        {
            retGroup = new EGXGroup(egxGroup.getName());
            egx.add(retGroup);
            super.addElement(retGroup);
        }

        retGroup.addAll(egxGroup);
    }

    @Override
    public synchronized boolean add(EGXGroup egxGroup)
    {
        EGXGroup retGroup = egx.get(egxGroup.getName());

        if (retGroup == null)
        {
            retGroup = new EGXGroup(egxGroup.getName());
            egx.add(retGroup);
            boolean a = super.add(egxGroup);
        }

        retGroup.addAll(egxGroup);
        return true;
    }

    @Override
    public synchronized void insertElementAt(EGXGroup egxGroup, int i)
    {
        EGXGroup retGroup = egx.get(egxGroup.getName());
        remove(retGroup);

        retGroup = new EGXGroup(egxGroup.getName());
        egx.add(retGroup);
        retGroup.addAll(egxGroup);
        super.insertElementAt(retGroup, i);
    }

    @Override
    public synchronized void removeElementAt(int i)
    {
        EGXGroup egxGroup = get(i);
        egx.remove(egxGroup);
        super.removeElementAt(i);
    }

    @Override
    public synchronized boolean removeElement(Object o)
    {
        egx.remove((EGXGroup) o);
        return super.removeElement(o);
    }

    @Override
    public synchronized void removeAllElements()
    {
        egx.clear();
        super.removeAllElements();
    }

    @Override
    public boolean remove(Object o)
    {
        egx.remove((EGXGroup) o);
        return super.remove(o);
    }

    @Override
    public synchronized EGXGroup remove(int i)
    {
        EGXGroup egxGroup = get(i);
        egx.remove(egxGroup);
        return super.remove(i);
    }

    @Override
    public synchronized boolean removeAll(Collection<?> collection)
    {
        for (Object o : collection)
        {
            remove(o);
        }
        return true;
    }

    @Override
    protected synchronized void removeRange(int i, int i1)
    {
        for (int index = i; index < i1; index++)
        {
            remove(i);
        }
    }

    @Override
    public synchronized boolean addAll(Collection<? extends EGXGroup> collection)
    {
        for (EGXGroup egxGroup : collection)
        {
            add(egxGroup);
        }

        return true;
    }

    @Override
    public synchronized boolean addAll(int i, Collection<? extends EGXGroup> collection)
    {
        for (EGXGroup egxGroup : collection)
        {
            insertElementAt(egxGroup, i);
        }

        return true;
    }

    public void addEntity(int groupIndex, GXEntity gxEntity)
    {
        EGXGroup egxGroup = get(groupIndex);
        egxGroup.add(gxEntity);
    }

    public void removeEntity(int groupIndex, GXEntity gxEntity)
    {
        EGXGroup egxGroup = get(groupIndex);
        egxGroup.remove(gxEntity);
    }

    public void removeEntity(int groupIndex, int entityIndex)
    {
        EGXGroup egxGroup = get(groupIndex);
        egxGroup.remove(entityIndex);
    }

    public void removeEntity(String groupName, GXEntity gxEntity)
    {
        EGXGroup egxGroup = egx.get(groupName);
        egxGroup.remove(gxEntity);
    }

    public void removeEntity(String groupName, int entityIndex)
    {
        EGXGroup egxGroup = egx.get(groupName);
        egxGroup.remove(entityIndex);
    }
}
