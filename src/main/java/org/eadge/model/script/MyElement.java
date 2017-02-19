package org.eadge.model.script;

import org.eadge.gxscript.data.entity.DefaultEntity;
import org.eadge.gxscript.data.entity.Entity;
import org.eadge.renderer.Rect2D;

import java.util.Collection;

/**
 * Created by eadgyo on 19/02/17.
 */
public class MyElement extends Rect2D
{
    DefaultEntity entity;

    public MyElement(DefaultEntity entity)
    {

    }

    public String getName()
    {
        return entity.getName();
    }

    public int getNumberOfInputs()
    {
        return entity.getNumberOfInputs();
    }

    public int getNumberOfOutputs()
    {
        return entity.getNumberOfOutputs();
    }

    public String getInputName(int inputIndex)
    {
        return entity.getInputName(inputIndex);
    }

    public String getOutputName(int outputIndex)
    {
        return entity.getOutputName(outputIndex);
    }

    public Entity getInputEntity(int inputIndex)
    {
        return entity.getInputEntity(inputIndex);
    }

    public Collection<Entity> getOutputEntities(int outputIndex)
    {
        return entity.getOutputEntities(outputIndex);
    }

    public int getNumberOfOutputEntitiesAtIndex(int outputIndex)
    {
        return getOutputEntities(outputIndex).size();
    }
}
