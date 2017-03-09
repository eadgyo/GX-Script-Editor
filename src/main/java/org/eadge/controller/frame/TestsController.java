package org.eadge.controller.frame;

import org.eadge.model.Models;
import org.eadge.view.MyFrame;
import org.eadge.view.TestsView;

import java.awt.*;

/**
 * Created by eadgyo on 17/02/17.
 */
public class TestsController
{
    public TestsController(MyFrame myFrame, Models m)
    {
        TestsView testsView = myFrame.testsView;
        testsView.messageConsole.redirectOut(Color.black, m.testsModel.getTestStream());
        testsView.validTests.setModel(m.testsModel);
    }
}
