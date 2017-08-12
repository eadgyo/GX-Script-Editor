package org.eadge.utils;

import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.model.script.GXElement;
import org.eadge.model.script.GXLayer;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
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
        Map<GXEntity, GXEntity> entityMap = createReplacementMap(entities);
        Map<MutableTreeNode, MutableTreeNode> layersMap = copyAllElements(copiedElements);

        HashSet<MutableTreeNode> newElements = new HashSet<>();
        for (GXEntity gxEntity : entityMap.values())
            newElements.add((MutableTreeNode) gxEntity);
        newElements.addAll(layersMap.values());

        replaceGXEntities(entityMap, layersMap, newElements);
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
            else if (copiedElement instanceof GXElement)
            {
                entities.add((GXEntity) copiedElement);
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
            GXEntity clone = ((GXElement) entity).deepClone();
            replacementMap.put(entity, clone);
        }
        return replacementMap;
    }

    private static Map<MutableTreeNode, MutableTreeNode> copyAllElements(Collection<MutableTreeNode> copiedElements)
    {
        Map<MutableTreeNode, MutableTreeNode> replacementLayers = new HashMap<>();

        for (MutableTreeNode toBeCopiedElement : copiedElements)
        {
            if (toBeCopiedElement instanceof GXLayer)
            {
                GXLayer layer = (GXLayer) toBeCopiedElement;
                GXLayer clone   = layer.clone();
                replacementLayers.put(toBeCopiedElement, clone);
            }
        }

        return replacementLayers;
    }

    private static void replaceGXEntities(Map<GXEntity, GXEntity> entityMap, Map<MutableTreeNode, MutableTreeNode>
            layersMap, Set<MutableTreeNode> newElements)
    {
        for (MutableTreeNode savedElement : newElements)
        {
            TreeNode parent = savedElement.getParent();
            MutableTreeNode replacedParent = layersMap.get(parent);
            savedElement.setParent(replacedParent);

            if (savedElement instanceof GXLayer)
            {
                ((GXLayer) savedElement).replaceChildren(entityMap);
            }
            else if (savedElement instanceof GXElement)
            {
                ((GXElement) savedElement).replaceEntities(entityMap);
            }

        }
    }

}
