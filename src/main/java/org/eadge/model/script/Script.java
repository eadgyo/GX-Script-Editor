package org.eadge.model.script;


import org.eadge.gxscript.data.compile.script.CompiledGXScript;
import org.eadge.gxscript.data.compile.script.RawGXScript;
import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.gxscript.tools.compile.GXCompiler;
import org.eadge.renderer.ElementFinder;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.Collection;
import java.util.Observable;

/**
 * Created by eadgyo on 15/02/17.
 *
 * Holds Raw GXScript data
 */
public class Script extends Observable implements Serializable
{
    /**
     * Raw GXScript
     */
    protected RawGXScript rawGXScript;

    /**
     * Holds script layer model
     */
    protected GXLayerModel layeredScript;

    /**
     * Holds element finder
     */
    private ElementFinder elementFinder;

    public Script(RawGXScript rawGXScript, GXLayerModel layeredScript, ElementFinder elementFinder)
    {
        this.rawGXScript = rawGXScript;
        this.layeredScript = layeredScript;
        this.elementFinder = elementFinder;
    }

    /**
     * Add GXElement to the scene in the corresponding layer
     *
     * @param element added GXElement
     * @param parent parent GXLayer
     */
    public void addEntity(GXElement element, MutableTreeNode parent)
    {
        addEntity(element, parent, parent.getChildCount());
    }


    /**
     * Add GXElement to the scene in the corresponding layer
     *
     * @param element added GXElement
     * @param parent parent GXLayer
     * @param index inserted index
     */
    public void addEntity(GXElement element, MutableTreeNode parent, int index)
    {
        rawGXScript.addEntity(element);
        layeredScript.insertNodeInto(element, parent, index);
        elementFinder.addElement(element);

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
        addLayer(gxLayer, parent,  parent.getChildCount());
    }

    /**
     * Add GXLayer to the scene in parent GXLayer
     *
     * @param gxLayer added GXLayer
     * @param parent parent GXLayer
     * @param index inserted index
     */
    public void addLayer(GXLayer gxLayer, MutableTreeNode parent, int index)
    {
        layeredScript.insertNodeInto(gxLayer, parent, index);
        elementFinder.addElement(gxLayer);

        callObservers();
    }

    /**
     * Remove node
     *
     * @param removed node
     */
    public void removeNode(MutableTreeNode removed)
    {
        elementFinder.removeElement(removed);

        if (removed instanceof GXLayer)
            removeLayer((GXLayer) removed);
        else
            ((GXElement) removed).removeEntity(this);
    }

    /**
     * Remove GXLayer from script and all contained GXLayers and GXElements
     *
     * @param gxLayer removed GXLayer
     */
    public void removeLayer(GXLayer gxLayer)
    {
        if (gxLayer == layeredScript.getRoot())
            return;

        int numberOfChildren = gxLayer.getChildCount();
        for (int childIndex = 0; childIndex < numberOfChildren; childIndex++)
        {
            MutableTreeNode childAt = (MutableTreeNode) gxLayer.getChildAt(childIndex);
            removeNode(childAt);
        }
        layeredScript.removeNodeFromParent(gxLayer);

        callObservers();
    }

    /**
     * Detach Node from parent without removing subElements
     *
     * @param node removed GXLayer
     */
    public void detachNode(MutableTreeNode node)
    {
        layeredScript.removeNodeFromParent(node);

        callObservers();
    }

    public Collection<GXEntity> getEntities()
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

    public void connectEntities(GXElement onOutputEntity, int outputIndex, GXElement onInputEntity, int inputIndex)
    {
        onInputEntity.addLinkInput(inputIndex, outputIndex, onOutputEntity);

        callObservers();
    }

    public void connectEntities(GXElement selected,
                                boolean startInput,
                                int startIndex,
                                GXElement onDragged,
                                int endIndex)
    {
        if (startInput)
        {
            connectEntities(onDragged, endIndex, selected, startIndex);
        }
        else
        {
            connectEntities(selected, startIndex, onDragged, endIndex);
        }
    }

    public void disconnectEntityOnEntry(GXElement gxElement, boolean isInput, int entryIndex)
    {
        if (isInput)
        {
            // Remove input
            if (gxElement.isInputUsed(entryIndex))
                gxElement.unlinkAsInput(entryIndex);
        }
        else
        {
            // Remove all output entities
            if (gxElement.isOutputUsed(entryIndex))
                gxElement.unlinkAsOutput(entryIndex);
        }

        callObservers();
    }

    public void clear()
    {
        rawGXScript.clear();
        layeredScript.clear();
        elementFinder.clear();
    }

    public void set(Script script)
    {
        rawGXScript = script.rawGXScript;
        layeredScript.setRoot((TreeNode) script.layeredScript.getRoot());
        elementFinder.setElementsFromRoot((GXLayer) script.layeredScript.getRoot());
    }

    public CompiledGXScript getCompiledRawGXScript()
    {
        GXCompiler gxCompiler = new GXCompiler();
        return gxCompiler.compile(rawGXScript);
    }

    public void removeNodes(Collection<MutableTreeNode> selectedElements)
    {
        for (MutableTreeNode selectedElement : selectedElements)
        {
            removeNode(selectedElement);
        }
    }

    public void addNode(MutableTreeNode mutableTreeNode, MutableTreeNode parent)
    {
        if (mutableTreeNode instanceof GXLayer)
        {
            addLayer((GXLayer) mutableTreeNode, parent);
        }
        else if (mutableTreeNode instanceof GXElement)
        {
            addEntity((GXElement) mutableTreeNode, parent);
        }
    }

    public void addNodes(Collection<MutableTreeNode> savedElements, MutableTreeNode parent)
    {
        for (MutableTreeNode savedElement : savedElements)
        {
            addNode(savedElement, parent);
        }
    }
}
