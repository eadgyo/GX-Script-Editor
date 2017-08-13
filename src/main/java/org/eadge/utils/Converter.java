package org.eadge.utils;

import org.eadge.gxscript.data.compile.script.CompiledGXScript;
import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.gxscript.data.entity.model.def.DefaultGXEntity;
import org.eadge.gxscript.data.io.EGX;
import org.eadge.gxscript.data.io.EGXGroup;
import org.eadge.gxscript.tools.compile.GXEntityCreator;
import org.eadge.model.script.GXElement;

import java.util.Collection;

/**
 * Created by eadgyo on 07/03/17.
 */
public class Converter
{
    public static GXEntity compiledScriptToEntity(CompiledGXScript compiledGXScript)
    {
        return GXEntityCreator.createGXEntity(compiledGXScript);
    }

    public static GXElement compiledScriptToElement(CompiledGXScript compiledGXScript)
    {
        return entityToElement(compiledScriptToEntity(compiledGXScript));
    }

    public static EGXGroup convertIfNeededToGXElements(EGXGroup egxGroup)
    {
        if (egxGroup.size() == 0 || egxGroup.iterator().next() instanceof GXElement)
        {
            return egxGroup;
        }

        // Convert
        EGXGroup transformedGroup = new EGXGroup(egxGroup.getName());
        for (GXEntity gxEntity : egxGroup)
        {
            transformedGroup.add(entityToElement(gxEntity));
        }

        return transformedGroup;
    }

    public static EGX convertIfNeededToGXElements(EGX egx)
    {
        Collection<EGXGroup> values = egx.values();
        if (values.size() == 0 || values.iterator().next().size() == 0 || values.iterator().next().iterator().next() instanceof GXElement)
        {
            return egx;
        }

        // Convert EGX
        EGX transformedEGX = new EGX();

        for (EGXGroup value : values)
        {
            EGXGroup egxGroup = new EGXGroup(value.getName());
            for (GXEntity gxEntity : value)
            {
                egxGroup.add(entityToElement(gxEntity));
            }
            transformedEGX.add(egxGroup);
        }

        return transformedEGX;
    }

    public static GXElement entityToElement(GXEntity entity)
    {
        return new GXElement((DefaultGXEntity) entity);
    }
}
