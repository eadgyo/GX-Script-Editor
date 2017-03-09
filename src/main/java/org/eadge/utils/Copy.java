package org.eadge.utils;

import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.model.script.GXElement;
import org.eadge.model.script.GXLayer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.util.*;

/**
 * Created by eadgyo on 09/03/17.
 *
 * Copy elements
 */
public class Copy
{
    public static Set<MutableTreeNode> copyElements(Collection<MutableTreeNode> copiedElements)
    {
        Set<GXEntity> entities = retrieveGXEntities(copiedElements);
        Map<GXEntity, GXEntity> replacementMap = createReplacementMap(entities);
        Set<MutableTreeNode> newElements = copyAllElements(copiedElements);
        replaceGXEntities(replacementMap, newElements);
        return newElements;
    }

    private static Set<GXEntity> retrieveGXEntities(Collection<MutableTreeNode> copiedElements)
    {
        // Retrieve all GXEntities (not GXElement)
        Set<GXEntity> entities = new HashSet<>();
        for (MutableTreeNode copiedElement : copiedElements)
        {
            if (copiedElement instanceof GXLayer)
            {
                ((GXLayer) copiedElement).fillWithGXEntities(entities);
            }
        }
        return entities;
    }

    private static Map<GXEntity, GXEntity> createReplacementMap(Collection<GXEntity> entities)
    {
        // Copy all elements and create replacement map
        Map<GXEntity, GXEntity> replacementMap = new HashMap<>();
        for (GXEntity entity : entities)
        {
            GXEntity clone = (GXEntity) entity.clone();
            replacementMap.put(entity, clone);
        }
        return replacementMap;
    }

    private static Set<MutableTreeNode> copyAllElements(Collection<MutableTreeNode> copiedElements)
    {
        Set<MutableTreeNode> newElements = new HashSet<>();

        for (MutableTreeNode toBeCopiedElement : copiedElements)
        {
            MutableTreeNode copiedElement = (MutableTreeNode) ((DefaultMutableTreeNode) toBeCopiedElement).clone();
            newElements.add(copiedElement);
        }

        return newElements;
    }

    private static void replaceGXEntities(Map<GXEntity, GXEntity> replacementMap, Set<MutableTreeNode> newElements)
    {
        for (MutableTreeNode savedElement : newElements)
        {
            if (savedElement instanceof GXLayer)
            {
                ((GXLayer) savedElement).replaceChildren(replacementMap);
            }
            else if (savedElement instanceof GXElement)
            {
                ((GXElement) savedElement).replaceEntity(replacementMap);
            }
        }
    }

}
