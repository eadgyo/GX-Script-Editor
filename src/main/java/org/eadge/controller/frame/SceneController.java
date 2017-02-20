package org.eadge.controller.frame;

import org.eadge.model.frame.SceneModel;
import org.eadge.model.frame.global.MyTransferableElement;
import org.eadge.model.script.MyElement;
import org.eadge.model.script.Script;
import org.eadge.view.MyFrame;
import org.eadge.view.SceneView;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Created by eadgyo on 17/02/17.
 *
 * Handle scene support
 */
public class SceneController
{
    private Script script;
    private MyFrame    myFrame;
    private SceneView  sceneView;
    private SceneModel sceneModel;

    private class SceneTransferHandler extends TransferHandler
    {
        @Override
        public boolean importData(TransferSupport transferSupport)
        {
            if (!canImport(transferSupport))
                return false;

            MyElement element;
            try
            {
                element = (MyElement) transferSupport.getTransferable().getTransferData(MyTransferableElement.myElementFlavor);
            }
            catch (UnsupportedFlavorException | IOException e)
            {
                e.printStackTrace();
                return false;
            }

            // Add the element to the scene
            Point dropPoint = transferSupport.getDropLocation().getDropPoint();
            MyElement cloned = (MyElement) element.clone();

            double elementX = sceneModel.computeXInScene(dropPoint.getX());
            double elementY = sceneModel.computeYInScene(dropPoint.getY());

            cloned.setX(elementX);
            cloned.setY(elementY);

            script.addEntity(cloned);

            return true;
        }

        @Override
        public boolean canImport(TransferSupport transferSupport)
        {
            return transferSupport.isDataFlavorSupported(MyTransferableElement.myElementFlavor);
        }

        @Override
        public int getSourceActions(JComponent jComponent)
        {
            return COPY_OR_MOVE;
        }

        @Override
        protected Transferable createTransferable(JComponent jComponent)
        {
            return super.createTransferable(jComponent);
        }
    }

    public SceneController(MyFrame myFrame, SceneModel sceneModel, Script script)
    {
        this.myFrame = myFrame;
        this.sceneView = myFrame.sceneView;
        this.sceneModel = sceneModel;
        this.script = script;

        // Add drag and drop from view
        sceneView.setTransferHandler(new SceneTransferHandler());

    }


}
