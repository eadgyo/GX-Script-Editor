package org.eadge;

import org.eadge.gxscript.data.entity.classic.entity.control.AssertGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.control.ExceptionGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.displayer.PrintGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.imbrication.conditionals.IfGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.imbrication.conditionals.SwitchGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.imbrication.loops.DoWhileGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.imbrication.loops.ForEachGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.imbrication.loops.ForGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.imbrication.loops.WhileGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.types.EqualGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.types.bool.BoolGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.types.bool.ModifyBoolGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.types.bool.transform.InvertBoolGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.types.number.IntGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.types.number.RealGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.types.number.comparison.BetweenNumbersGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.types.number.comparison.InferiorThanNumberGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.types.number.comparison.SuperiorThanNumberGXEntity;
import org.eadge.gxscript.data.entity.classic.entity.types.number.operations.maths.*;
import org.eadge.gxscript.data.entity.model.script.InputScriptGXEntity;
import org.eadge.gxscript.data.entity.model.script.OutputScriptGXEntity;
import org.eadge.gxscript.data.io.EGX;
import org.eadge.gxscript.data.io.EGXGroup;
import org.eadge.gxscript.tools.io.IOGXManager;

/**
 * Created by eadgyo on 10/03/17.
 */
public class GenerateLib
{
    public static void main(String[] args)
    {
        EGX egx = generateLib();
        IOGXManager.getInstance().createFolder("EGX");
        IOGXManager.getInstance().saveEGX("EGX/classic.egx", egx);
    }

    public static EGX generateLib() {
        EGX egx = new EGX();

        EGXGroup booleanGroup = new EGXGroup("Boolean");
        booleanGroup.add(new BoolGXEntity());
        booleanGroup.add(new ModifyBoolGXEntity());
        booleanGroup.add(new InvertBoolGXEntity());
        egx.add(booleanGroup);

        EGXGroup numberGroup = new EGXGroup("Number");
        numberGroup.add(new IntGXEntity());
        numberGroup.add(new RealGXEntity());
        numberGroup.add(new BetweenNumbersGXEntity());
        numberGroup.add(new EqualGXEntity());
        numberGroup.add(new InferiorThanNumberGXEntity());
        numberGroup.add(new SuperiorThanNumberGXEntity());
        numberGroup.add(new AddNumbersGXEntity());
        numberGroup.add(new DivideNumbersGXEntity());
        numberGroup.add(new InverseNumberGXEntity());
        numberGroup.add(new MultiplyNumbersGXEntity());
        numberGroup.add(new NegNumberGXEntity());
        numberGroup.add(new SubNumbersGXEntity());
        egx.add(numberGroup);


        EGXGroup controlGroup = new EGXGroup("Control");
        controlGroup.add(new AssertGXEntity());
        controlGroup.add(new ExceptionGXEntity());
        egx.add(controlGroup);

        EGXGroup displayerGroup = new EGXGroup("Display");
        displayerGroup.add(new PrintGXEntity());
        egx.add(displayerGroup);

        EGXGroup printGroup = new EGXGroup("Print");
        printGroup.add(new PrintGXEntity());
        egx.add(printGroup);

        EGXGroup conditionalsGroup = new EGXGroup("Conditionals");
        conditionalsGroup.add(new IfGXEntity());
        conditionalsGroup.add(new SwitchGXEntity());
        egx.add(conditionalsGroup);

        EGXGroup loopsGroup = new EGXGroup("Loops Group");
        loopsGroup.add(new DoWhileGXEntity());
        loopsGroup.add(new ForEachGXEntity());
        loopsGroup.add(new ForGXEntity());
        loopsGroup.add(new WhileGXEntity());
        egx.add(loopsGroup);

        EGXGroup entriesGroup = new EGXGroup("Entries");
        entriesGroup.add(new InputScriptGXEntity());
        entriesGroup.add(new OutputScriptGXEntity());
        egx.add(entriesGroup);

        return egx;
    }
}
