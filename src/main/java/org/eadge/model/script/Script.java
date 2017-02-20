package org.eadge.model.script;

import org.eadge.gxscript.data.entity.Entity;
import org.eadge.gxscript.data.script.RawGXScript;

import java.util.Collection;
import java.util.Set;

/**
 * Created by eadgyo on 15/02/17.
 *
 * Holds Raw GXScript data
 */
public class Script
{
    private RawGXScript rawGXScript;

    public Script()
    {
        this.rawGXScript = new RawGXScript();
    }

    public void addEntity(Entity entity)
    {
        rawGXScript.addEntity(entity);
    }

    public void addAllEntities(Collection<Entity> entities)
    {
        rawGXScript.addAllEntities(entities);
    }

    public void removeEntity(Entity entity)
    {
        rawGXScript.removeEntity(entity);
    }

    public Collection<Entity> getEntities()
    {
        return rawGXScript.getEntities();
    }

    public void setEntities(Set<Entity> entities)
    {
        rawGXScript.setEntities(entities);
    }
}
