package org.eadge.model.script;

import org.eadge.gxscript.data.entity.Entity;
import org.eadge.gxscript.data.script.RawGXScript;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Collection;
import java.util.Observable;

/**
 * Created by eadgyo on 15/02/17.
 *
 * Holds Raw GXScript data
 */
public class Script extends Observable
{
    /**
     * Raw GXScript
     */
    private RawGXScript rawGXScript;

    /**
     * Holds script layer model
     */
    private GXLayerModel layeredScript;


    public Script(RawGXScript rawGXScript, GXLayerModel layeredScript)
    {
        this.rawGXScript = rawGXScript;
        this.layeredScript = layeredScript;
    }

    /**
     * Add GXElement to the scene in the corresponding layer
     *
     * @param element added GXElement
     * @param parent parent GXLayer
     */
    public void addEntity(GXElement element, MutableTreeNode parent)
    {
        rawGXScript.addEntity(element);
        parent.insert(element, 0);

        callObservers();
    }

    /**
     * Add GXLayer to the scene in parent GXLayer
     *
     * @param gxLayer added GXLayer
     * @param parent parent GXLayer
     */
    public void addLayer(GXLayer gxLayer, MutableTreeNode parent)
    {
        parent.insert(gxLayer, 0);

        callObservers();
    }

    /**
     * Remove GXElement from script
     *
     * @param element removed GXElement
     */
    public void removeEntity(GXElement element)
    {
        rawGXScript.removeEntity(element);
        element.removeFromParent();

        callObservers();
    }

    /**
     * Remove node
     *
     * @param removed node
     */
    public void removeNode(TreeNode removed)
    {
        if (removed instanceof GXLayer)
            removeLayer((GXLayer) removed);
        else
            removeEntity((GXElement) removed);
    }

    /**
     * Remove GXLayer from script and all contained GXLayers and GXElements
     *
     * @param gxLayer removed GXLayer
     */
    public void removeLayer(GXLayer gxLayer)
    {
        int numberOfChildren = gxLayer.getChildCount();
        for (int childIndex = 0; childIndex < numberOfChildren; childIndex++)
        {
            TreeNode childAt = gxLayer.getChildAt(childIndex);
            removeNode(childAt);
        }

        gxLayer.removeFromParent();

        callObservers();
    }

    /**
     * Detach GXLayer from parent without removing subElements
     *
     * @param gxLayer removed GXLayer
     */
    public void detachLayer(GXLayer gxLayer)
    {
        gxLayer.removeFromParent();

        callObservers();
    }

    public Collection<Entity> getEntities()
    {
        return rawGXScript.getEntities();
    }

    public RawGXScript getRawGXScript()
    {
        return rawGXScript;
    }

    public GXLayerModel getLayeredScript()
    {
        return layeredScript;
    }

    public void callObservers()
    {
        setChanged();
        notifyObservers();
    }
}
