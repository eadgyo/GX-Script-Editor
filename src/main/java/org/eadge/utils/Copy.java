package org.eadge.utils;

import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.model.script.GXElement;
import org.eadge.model.script.GXLayer;

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
        Map<MutableTreeNode, MutableTreeNode> entityMap = createReplacementMap(copiedElements);
        return replaceNodes(entityMap, copiedElements);
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

    private static Map<MutableTreeNode, MutableTreeNode> createReplacementMap(Collection<MutableTreeNode>
                                                                                      copiedElements)
    {
        List<MutableTreeNode> copyCopiedElements = new LinkedList<>(copiedElements);

        // Copy all elements and create replacement map
        Map<MutableTreeNode, MutableTreeNode> replacementMap = new HashMap<>();
        for (int i = 0; i < copyCopiedElements.size(); i++)
        {
            MutableTreeNode treeNode = copyCopiedElements.get(i);
            if (treeNode instanceof GXElement)
            {
                GXElement clone = ((GXElement) treeNode).deepClone();
                replacementMap.put(treeNode, clone);
            }
            else if (treeNode instanceof GXLayer)
            {
                GXLayer clone= ((GXLayer) treeNode).clone();
                replacementMap.put(treeNode, clone);

                for (int childIndex = 0; childIndex < clone.getChildCount(); childIndex++)
                {
                    copyCopiedElements.add((MutableTreeNode) clone.getChildAt(childIndex));
                }
            }
        }
        return replacementMap;
    }

    public static Set<MutableTreeNode> replaceNodes(Map<MutableTreeNode, MutableTreeNode> replacementMap,
                                       Collection<MutableTreeNode> copiedElements)
    {
        Set<MutableTreeNode> hierarchy = new HashSet<>();

        for (MutableTreeNode copiedElement : copiedElements)
        {
            MutableTreeNode treeNode = replacementMap.get(copiedElement);
            MutableTreeNode replacedParent = replacementMap.get(treeNode.getParent());
            treeNode.setParent(replacedParent);

            if (treeNode instanceof GXLayer)
            {
                ((GXLayer) treeNode).replaceChildren(replacementMap);
            }
            else if (treeNode instanceof GXElement)
            {
                ((GXElement) treeNode).replaceEntities(replacementMap);
            }

            hierarchy.add(treeNode);
        }
        return hierarchy;
    }



}
