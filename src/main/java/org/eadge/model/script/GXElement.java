package org.eadge.model.script;

import org.eadge.gxscript.data.entity.DefaultEntity;
import org.eadge.gxscript.data.entity.Entity;
import org.eadge.gxscript.data.script.Func;
import org.eadge.gxscript.data.script.address.DataAddress;
import org.eadge.gxscript.data.script.address.FuncDataAddresses;
import org.eadge.gxscript.data.script.address.OutputAddresses;
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
public class GXElement extends Rect2D implements Cloneable, Entity, MutableTreeNode
{
    private DefaultEntity entity;

    public GXElement(DefaultEntity entity)
    {

    }

    @Override
    public GXElement clone()
    {
        GXElement clone = (GXElement) super.clone();
        clone.entity = (DefaultEntity) entity.clone();
        return clone;
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

    public String getInputName(int inputIndex)
    {
        return entity.getInputName(inputIndex);
    }

    public String getOutputName(int outputIndex)
    {
        return entity.getOutputName(outputIndex);
    }

    public Entity getInputEntity(int inputIndex)
    {
        return entity.getInputEntity(inputIndex);
    }

    public Collection<Entity> getOutputEntities(int outputIndex)
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

    public Entity getInputEntity(String name)
    {
        return entity.getInputEntity(name);
    }

    public boolean inputContains(int inputIndex, Entity entity)
    {
        return this.entity.inputContains(inputIndex, entity);
    }

    @Override
    public String toString()
    {
        return getName();
    }

    public boolean outputContains(int outputIndex, Entity entity)
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

    public Collection<Entity> getAllInputEntities()
    {
        return entity.getAllInputEntities();
    }

    public Class getOutputClass(int index)
    {
        return entity.getOutputClass(index);
    }

    public Collection<Class> getAllOutputClasses()
    {
        return entity.getAllOutputClasses();
    }

    public int getOutputIndex(String name)
    {
        return entity.getOutputIndex(name);
    }

    public Collection<Entity> getOutputEntities(String name)
    {
        return entity.getOutputEntities(name);
    }

    public boolean isOutputUsed(int index)
    {
        return entity.isOutputUsed(index);
    }

    public int getIndexOfInputFromEntityOnOutput(int outputIndex, Entity outputEntity)
    {
        return entity.getIndexOfInputFromEntityOnOutput(outputIndex, outputEntity);
    }

    public Collection<? extends Collection<Entity>> getAllOutputEntities()
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

    public void linkAsInput(int inputIndex, int entityOutput, Entity entity)
    {
        this.entity.linkAsInput(inputIndex, entityOutput, entity);
    }

    public void unlinkAsInput(int inputIndex)
    {
        entity.unlinkAsInput(inputIndex);
    }

    public void addLinkInput(int inputIndex, int outputEntityIndex, Entity entity)
    {
        this.entity.addLinkInput(inputIndex, outputEntityIndex, entity);
    }

    public void removeLinkInput(int inputIndex)
    {
        entity.removeLinkInput(inputIndex);
    }

    public void addLinkOutput(int outputIndex, int inputEntityIndex, Entity entity)
    {
        this.entity.addLinkOutput(outputIndex, inputEntityIndex, entity);
    }

    public void removeLinkOutput(int outputIndex, Entity entity)
    {
        this.entity.removeLinkOutput(outputIndex, entity);
    }

    public void changeIndexOfOutputFromEntityOnInput(int inputIndex, int newOutputIndex)
    {
        entity.changeIndexOfOutputFromEntityOnInput(inputIndex, newOutputIndex);
    }

    public void changeIndexOfInputFromEntityOnOutput(int outputIndex, Entity outputEntity, int newInputIndex)
    {
        entity.changeIndexOfInputFromEntityOnOutput(outputIndex, outputEntity, newInputIndex);
    }

    public void linkAsOutput(int outputIndex, int entityInput, Entity entity)
    {
        this.entity.linkAsOutput(outputIndex, entityInput, entity);
    }

    public void unlinkAsOutput(int outputIndex, Entity entity)
    {
        this.entity.unlinkAsOutput(outputIndex, entity);
    }

    public void unlinkAsOutput(int outputIndex)
    {
        entity.unlinkAsOutput(outputIndex);
    }

    public void pushEntityCode(ArrayList<Func> calledFunctions,
                               ArrayList<FuncDataAddresses> calledFunctionAddresses,
                               Map<Entity, OutputAddresses> addressesMap)
    {
        entity.pushEntityCode(calledFunctions, calledFunctionAddresses, addressesMap);
    }

    public OutputAddresses createAndAllocOutputs(DataAddress currentDataAddress)
    {
        return entity.createAndAllocOutputs(currentDataAddress);
    }

    public FuncDataAddresses createAndLinkFuncDataAddresses(Map<Entity, OutputAddresses> addressesMap)
    {
        return entity.createAndLinkFuncDataAddresses(addressesMap);
    }

    public Collection<Entity> getAllOutputEntitiesCollection()
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
        return null;
    }

    @Override
    public int getIndex(TreeNode treeNode)
    {
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
        return false;
    }

    @Override
    public Enumeration children()
    {
        return null;
    }

    @Override
    public void insert(MutableTreeNode mutableTreeNode, int i)
    {

    }

    @Override
    public void remove(int i)
    {

    }

    @Override
    public void remove(MutableTreeNode mutableTreeNode)
    {

    }

    @Override
    public void setUserObject(Object o)
    {

    }

    @Override
    public void removeFromParent()
    {

    }

    @Override
    public void setParent(MutableTreeNode mutableTreeNode)
    {

    }
}
