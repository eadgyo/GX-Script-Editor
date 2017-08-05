package org.eadge.model.script;


import org.eadge.gxscript.data.compile.script.address.DataAddress;
import org.eadge.gxscript.data.compile.script.address.FuncDataAddresses;
import org.eadge.gxscript.data.compile.script.address.OutputAddresses;
import org.eadge.gxscript.data.compile.script.func.Func;
import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.gxscript.data.entity.model.def.DefaultGXEntity;
import org.eadge.gxscript.tools.check.GXLiaisonChecker;
import org.eadge.renderer.ElementRenderer;
import org.eadge.renderer.Rect2D;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by eadgyo on 19/02/17.
 *
 * Holds entity and graphics for element representation
 */
public class GXElement extends Rect2D implements Cloneable, GXEntity, MutableTreeNode
{
    private DefaultGXEntity entity;
    private MutableTreeNode parent = null;

    public GXElement(DefaultGXEntity entity)
    {
        this.entity = entity;
    }

    @Override
    public GXElement clone()
    {
        GXElement clone = (GXElement) super.clone();
        // Replacement will be done after copy
        clone.entity = entity;
        return clone;
    }

    public GXElement deepClone()
    {
        GXElement clone = (GXElement) super.clone();
        clone.entity = (DefaultGXEntity) entity.clone();
        return clone;
    }

    /**
     * Replace contained entity with the new one
     * @param replacementMap replacement map
     */
    public void replaceEntity(Map<GXEntity, GXEntity> replacementMap)
    {
        this.entity = (DefaultGXEntity) replacementMap.get(entity);
    }

    public DefaultGXEntity getEntity()
    {
        return entity;
    }

    public String getName()
    {
        return entity.getName();
    }

    public int getNumberOfInputs()
    {
        return entity.getNumberOfInputs();
    }

    public int getNumberOfOutputs()
    {
        return entity.getNumberOfOutputs();
    }

    @Override
    public int getNumberOfOutputEntities(int i)
    {
        return 0;
    }

    public String getInputName(int inputIndex)
    {
        return entity.getInputName(inputIndex);
    }

    public String getInputDetail(int inputIndex) {
        return entity.getInputName(inputIndex) + " (" + entity.getInputClass(inputIndex).getSimpleName() + ")";
    }

    public String getOutputName(int outputIndex)
    {
        return entity.getOutputName(outputIndex);
    }

    public String getOutputDetail(int outputIndex)
    {
        return entity.getOutputName(outputIndex) + " (" + entity.getOutputClass(outputIndex).getSimpleName() + ")";
    }

    public GXEntity getInputEntity(int inputIndex)
    {
        return entity.getInputEntity(inputIndex);
    }

    public Collection<GXEntity> getOutputEntities(int outputIndex)
    {
        return entity.getOutputEntities(outputIndex);
    }

    public int getNumberOfOutputEntitiesAtIndex(int outputIndex)
    {
        return getOutputEntities(outputIndex).size();
    }

    public void setName(String name)
    {
        entity.setName(name);
    }

    public Class getInputClass(int index)
    {
        return entity.getInputClass(index);
    }

    public Collection<Class> getAllInputClasses()
    {
        return entity.getAllInputClasses();
    }

    public int getNumberOfUsedInputs()
    {
        return entity.getNumberOfUsedInputs();
    }

    public boolean hasInputsUsed()
    {
        return entity.hasInputsUsed();
    }

    public boolean isInputUsed(int index)
    {
        return entity.isInputUsed(index);
    }

    public int getInputIndex(String name)
    {
        return entity.getInputIndex(name);
    }

    public GXEntity getInputEntity(String name)
    {
        return entity.getInputEntity(name);
    }

    public boolean inputContains(int inputIndex, GXEntity entity)
    {
        return this.entity.inputContains(inputIndex, entity);
    }

    @Override
    public String toString()
    {
        return getName();
    }

    public boolean outputContains(int outputIndex, GXEntity entity)
    {
        return this.entity.outputContains(outputIndex, entity);
    }

    public int getIndexOfOutputFromEntityOnInput(int inputIndex)
    {
        return entity.getIndexOfOutputFromEntityOnInput(inputIndex);
    }

    public Class getOutputClassFromInputEntity(int inputIndex)
    {
        return entity.getOutputClassFromInputEntity(inputIndex);
    }

    public Collection<GXEntity> getAllInputEntities()
    {
        return entity.getAllInputEntities();
    }

    public Class getOutputClass(int index)
    {
        return entity.getOutputClass(index);
    }

    @Override
    public Collection<Class> getOutputClasses()
    {
        return entity.getOutputClasses();
    }

    public int getOutputIndex(String name)
    {
        return entity.getOutputIndex(name);
    }

    public Collection<GXEntity> getOutputEntities(String name)
    {
        return entity.getOutputEntities(name);
    }

    public boolean isOutputUsed(int index)
    {
        return entity.isOutputUsed(index);
    }

    public int getIndexOfInputFromEntityOnOutput(int outputIndex, GXEntity outputEntity)
    {
        return entity.getIndexOfInputFromEntityOnOutput(outputIndex, outputEntity);
    }

    public Collection<? extends Collection<GXEntity>> getAllOutputEntities()
    {
        return entity.getAllOutputEntities();
    }

    public boolean hasAllNeededInput()
    {
        return entity.hasAllNeededInput();
    }

    public int getNumberOfVariableOutput()
    {
        return entity.getNumberOfVariableOutput();
    }

    public int getNumberOfVariableInput()
    {
        return entity.getNumberOfVariableInput();
    }

    public int getNumberOfUsedVariableInput()
    {
        return entity.getNumberOfUsedVariableInput();
    }

    public boolean isVariableInput(int inputIndex)
    {
        return entity.isVariableInput(inputIndex);
    }

    public void addInputEntry(String inputName, Class cl)
    {
        entity.addInputEntry(inputName, cl);
    }

    public void addInputEntryNotNeeded(String inputName, Class cl)
    {
        entity.addInputEntryNotNeeded(inputName, cl);
    }

    public void addInputEntry(int inputIndex, String inputName, Class cl)
    {
        entity.addInputEntry(inputIndex, inputName, cl);
    }

    public boolean isInputNeeded(int inputIndex)
    {
        return entity.isInputNeeded(inputIndex);
    }

    public Collection<Integer> getAllInputsNeeded()
    {
        return entity.getAllInputsNeeded();
    }

    public void addInputEntryNotNeeded(int inputIndex, String inputName, Class cl)
    {
        entity.addInputEntryNotNeeded(inputIndex, inputName, cl);
    }

    public void setInputName(int inputIndex, String inputName)
    {
        entity.setInputName(inputIndex, inputName);
    }

    public void setInputClass(int inputIndex, Class cl)
    {
        entity.setInputClass(inputIndex, cl);
    }

    public void clearLinkedInput(int inputIndex)
    {
        entity.clearLinkedInput(inputIndex);
    }

    public void removeInputEntry(int inputIndex)
    {
        entity.removeInputEntry(inputIndex);
    }

    public void addOutputEntry(String outputName, Class cl)
    {
        entity.addOutputEntry(outputName, cl);
    }

    public void addOutputEntry(int outputIndex, String outputName, Class cl)
    {
        entity.addOutputEntry(outputIndex, outputName, cl);
    }

    public void setOutputName(int outputIndex, String outputName)
    {
        entity.setOutputName(outputIndex, outputName);
    }

    public void setOutputClass(int outputIndex, Class cl)
    {
        entity.setOutputClass(outputIndex, cl);
    }

    public void clearLinkedOutputs(int outputIndex)
    {
        entity.clearLinkedOutputs(outputIndex);
    }

    public void removeOutputEntry(int outputIndex)
    {
        entity.removeOutputEntry(outputIndex);
    }

    public boolean isVariableOutput(int outputIndex)
    {
        return entity.isVariableOutput(outputIndex);
    }

    public void linkAsInput(int inputIndex, int entityOutput, GXEntity entity)
    {
        this.entity.linkAsInput(inputIndex, entityOutput, entity);
    }

    public void unlinkAsInput(int inputIndex)
    {
        entity.unlinkAsInput(inputIndex);
    }

    public void addLinkInput(int inputIndex, int outputEntityIndex, GXEntity entity)
    {
        this.entity.addLinkInput(inputIndex, outputEntityIndex, entity);
    }

    public void removeLinkInput(int inputIndex)
    {
        entity.removeLinkInput(inputIndex);
    }

    public void addLinkOutput(int outputIndex, int inputEntityIndex, GXEntity entity)
    {
        this.entity.addLinkOutput(outputIndex, inputEntityIndex, entity);
    }

    public void removeLinkOutput(int outputIndex, GXEntity entity)
    {
        this.entity.removeLinkOutput(outputIndex, entity);
    }

    public void changeIndexOfOutputFromEntityOnInput(int inputIndex, int newOutputIndex)
    {
        entity.changeIndexOfOutputFromEntityOnInput(inputIndex, newOutputIndex);
    }

    public void changeIndexOfInputFromEntityOnOutput(int outputIndex, GXEntity outputEntity, int newInputIndex)
    {
        entity.changeIndexOfInputFromEntityOnOutput(outputIndex, outputEntity, newInputIndex);
    }

    public void linkAsOutput(int outputIndex, int entityInput, GXEntity entity)
    {
        this.entity.linkAsOutput(outputIndex, entityInput, entity);
    }

    public void unlinkAsOutput(int outputIndex, GXEntity entity)
    {
        this.entity.unlinkAsOutput(outputIndex, entity);
    }

    public void unlinkAsOutput(int outputIndex)
    {
        entity.unlinkAsOutput(outputIndex);
    }

    public void pushEntityCode(ArrayList<Func> calledFunctions,
                               ArrayList<FuncDataAddresses> calledFunctionAddresses,
                               Map<GXEntity, OutputAddresses> addressesMap)
    {
        entity.pushEntityCode(calledFunctions, calledFunctionAddresses, addressesMap);
    }

    public OutputAddresses createAndAllocOutputs(DataAddress currentDataAddress)
    {
        return entity.createAndAllocOutputs(currentDataAddress);
    }

    public FuncDataAddresses createAndLinkFuncDataAddresses(Map<GXEntity, OutputAddresses> addressesMap)
    {
        return entity.createAndLinkFuncDataAddresses(addressesMap);
    }

    public Collection<GXEntity> getAllOutputEntitiesCollection()
    {
        return entity.getAllOutputEntitiesCollection();
    }

    public boolean isValidOutput(int index)
    {
        return entity.isValidOutput(index);
    }

    public boolean hasValidInput()
    {
        return entity.hasValidInput();
    }

    public boolean isValidInput(int inputIndex)
    {
        return entity.isValidInput(inputIndex);
    }

    public boolean hasValidOutput()
    {
        return entity.hasValidOutput();
    }

    public Func getFunc()
    {
        return entity.getFunc();
    }

    @Override
    public TreeNode getChildAt(int i)
    {
        return null;
    }

    @Override
    public int getChildCount()
    {
        return 0;
    }

    @Override
    public TreeNode getParent()
    {
        return parent;
    }

    @Override
    public int getIndex(TreeNode treeNode)
    {
        System.out.println("Index");
        return 0;
    }

    @Override
    public boolean getAllowsChildren()
    {
        return false;
    }

    @Override
    public boolean isLeaf()
    {
        return true;
    }

    @Override
    public Enumeration children()
    {
        return null;
    }

    @Override
    public void insert(MutableTreeNode mutableTreeNode, int i)
    {
        System.out.println("Insert");
    }

    @Override
    public void remove(int i)
    {
        System.out.println("Remove");
    }

    @Override
    public void remove(MutableTreeNode mutableTreeNode)
    {
        System.out.println("Remove");
    }

    @Override
    public void setUserObject(Object o)
    {

    }

    @Override
    public void removeFromParent()
    {
        parent.remove(this);
    }

    @Override
    public void setParent(MutableTreeNode mutableTreeNode)
    {
        parent = mutableTreeNode;
    }

    /**
     * Check if MyGroupsOfElements connection can be made between this element and another
     * @param isInput  is connecting element on input
     * @param entryIndex used entry index
     * @param otherElement element to connect to
     * @param isOtherInput is connecting other element on input
     * @param otherEntryIndex used entry index
     * @return true if MyGroupsOfElements connection can be made, false otherwise
     */
    public boolean canConnectOnEntry(boolean isInput, int entryIndex, GXElement otherElement, boolean isOtherInput, int otherEntryIndex)
    {
        if (isInput == isOtherInput || otherElement == this)
            return false;

        if (isInput)
        {
            return GXLiaisonChecker.canConnect(otherElement, otherEntryIndex, this, entryIndex);
        }
        else
        {
            return GXLiaisonChecker.canConnect(this, entryIndex, otherElement, otherEntryIndex);
        }
    }

    public void replaceEntities(Map<GXEntity, GXEntity> replacementMap)
    {
        this.entity.replaceEntities(replacementMap);
    }

    public void computeSize(ElementRenderer elementRenderer)
    {
        setWidth(elementRenderer.computeGXElementWidth(this));
        setHeight(elementRenderer.computeGXElementHeight(this));
    }

    @Override
    public boolean equals(Object o)
    {
        return o == this;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (entity != null ? entity.hashCode() : 0);
        return result;
    }

    /**
     * Remove GXElement from script
     *
     * @param script
     */
    public void removeEntity(Script script)
    {
        script.rawGXScript.removeEntity(this);
        script.layeredScript.removeNodeFromParent(this);

        script.callObservers();
    }

}
