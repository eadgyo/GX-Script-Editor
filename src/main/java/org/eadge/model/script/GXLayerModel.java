package org.eadge.model.script;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * Created by eadgyo on 20/02/17.
 *
 * Holds GXLayer
 */
public class GXLayerModel extends DefaultTreeModel
{
    public GXLayerModel(TreeNode treeNode)
    {
        super(treeNode);
    }

    public GXLayerModel(TreeNode treeNode, boolean b)
    {
        super(treeNode, b);
    }

    public void clear()
    {
        super.setRoot(new GXLayer("Root"));
    }

    @Override
    public void insertNodeInto(MutableTreeNode mutableTreeNode, MutableTreeNode parentNode, int i)
    {
        super.insertNodeInto(mutableTreeNode, parentNode, i);

        if (  parentNode == getRoot()  ) {
            nodeStructureChanged((TreeNode) getRoot());
        }
    }
}
