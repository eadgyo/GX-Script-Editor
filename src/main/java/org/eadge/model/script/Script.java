package org.eadge.model.script;


import org.eadge.gxscript.data.compile.script.CompiledGXScript;
import org.eadge.gxscript.data.compile.script.RawGXScript;
import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.gxscript.tools.compile.GXCompiler;
import org.eadge.renderer.ElementFinder;

import javax.swing.tree.MutableTreeNode;
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
        rawGXScript.addEntity(element);
        layeredScript.insertNodeInto(element, parent,0);
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
        layeredScript.insertNodeInto(gxLayer, parent, 0);
        elementFinder.addElement(gxLayer);

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
        layeredScript.removeNodeFromParent(element);

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
            removeEntity((GXElement) removed);
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
     * Detach GXLayer from parent without removing subElements
     *
     * @param gxLayer removed GXLayer
     */
    public void detachLayer(GXLayer gxLayer)
    {
        layeredScript.removeNodeFromParent(gxLayer);

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
        onInputEntity.addLinkInput(inputIndex, outputIndex, onInputEntity);

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
            gxElement.unlinkAsInput(entryIndex);
        }
        else
        {
            // Remove all output entities
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
        layeredScript = script.layeredScript;
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
