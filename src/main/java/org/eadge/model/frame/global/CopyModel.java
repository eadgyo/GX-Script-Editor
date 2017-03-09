package org.eadge.model.frame.global;

import org.eadge.utils.Copy;

import javax.swing.tree.MutableTreeNode;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by eadgyo on 19/02/17.
 *
 * Handle copy functions
 */
public class CopyModel
{
    /**
     * Holds copied elements
     */
    private Collection<MutableTreeNode> savedElements = new HashSet<>();

    public void saveCopyOfElements(Collection<MutableTreeNode> copiedElements)
    {
        this.savedElements = Copy.copyElements(copiedElements);
    }

    public Collection<MutableTreeNode> getSavedElements()
    {
        return savedElements;
    }
}
