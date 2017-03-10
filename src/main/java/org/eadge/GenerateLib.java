package org.eadge;

import org.eadge.gxscript.data.entity.classic.types.EqualGXEntity;
import org.eadge.gxscript.data.entity.classic.types.bool.BoolGXEntity;
import org.eadge.gxscript.data.entity.classic.types.bool.ModifyBoolGXEntity;
import org.eadge.gxscript.data.entity.classic.types.bool.transform.InvertBoolGXEntity;
import org.eadge.gxscript.data.entity.classic.types.number.IntGXEntity;
import org.eadge.gxscript.data.entity.classic.types.number.RealGXEntity;
import org.eadge.gxscript.data.entity.classic.types.number.comparison.BetweenNumbersGXEntity;
import org.eadge.gxscript.data.entity.classic.types.number.comparison.InferiorThanNumberGXEntity;
import org.eadge.gxscript.data.entity.classic.types.number.comparison.SuperiorThanNumberGXEntity;
import org.eadge.gxscript.data.entity.classic.types.number.operations.maths.*;
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
        EGX egx = new EGX();

        EGXGroup booleanGroup = new EGXGroup("Boolean");
        booleanGroup.add(new BoolGXEntity());
        booleanGroup.add(new ModifyBoolGXEntity());
        booleanGroup.add(new InvertBoolGXEntity());
        egx.add(booleanGroup);


        EGXGroup numberGroup = new EGXGroup("Nombre");
        numberGroup.add(new IntGXEntity());
        numberGroup.add(new ModifyBoolGXEntity());
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

        IOGXManager.getInstance().createFolder("EGX");
        IOGXManager.getInstance().saveEGX("EGX/classic.egx", egx);
    }
}
