package org.eadge.controller.frame;

import org.eadge.ConstantsView;
import org.eadge.controller.Actions;
import org.eadge.gxscript.tools.check.GXValidator;
import org.eadge.gxscript.tools.check.validator.*;
import org.eadge.model.Models;
import org.eadge.view.MyFrame;
import org.eadge.view.TestsView;
import org.eadge.view.console.CustomOutputStream;

/**
 * Created by eadgyo on 17/02/17.
 */
public class TestsController
{
    public TestsController(MyFrame myFrame, Models m, Actions a)
    {
        TestsView testsView = myFrame.testsView;
        CustomOutputStream customOutputStream = new CustomOutputStream(testsView.consoleTestsText);
        testsView.validTests.setModel(m.testsModel);
        m.testsModel.setTestStream(customOutputStream);

        m.testsModel.addTest(ConstantsView.VALIDATE_ENTITIES_PRESENCE, new ValidateAllEntitiesPresent());
        m.testsModel.addTest(ConstantsView.VALIDATE_INPUT, new ValidateEntityHaveInput());
        m.testsModel.addTest(ConstantsView.VALIDATE_LINKS, new ValidateLinks());
        m.testsModel.addTest(ConstantsView.VALIDATE_INTERDEPENDANCE, new ValidateNoInterdependency());
        m.testsModel.addTest(ConstantsView.VALIDATE_IMBRICATIONS, new ValidateImbrication());
        m.testsModel.addTest(ConstantsView.VALIDATE_PARAMETERS, new ValidateValidParameters());
        m.testsModel.addTest(ConstantsView.VALIDATE_CODE, new GXValidator());
    }
}
