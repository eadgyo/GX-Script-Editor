package org.eadge.renderer.frame;

import org.eadge.model.script.GXElement;
import org.eadge.renderer.ElementRenderer;

import java.awt.*;

/**
 * Created by eadgyo on 25/02/17.
 *
 * Renderer connection between elements
 */
public class ConnectionRenderer
{
    /**
     * Keeps the color of the connection
     */
    private Color colorConnection;

    /**
     * Keeps entry renderer to compute X and Y elements
     */
    private ElementRenderer elementRenderer;

    public ConnectionRenderer(Color colorConnection, ElementRenderer elementRenderer)
    {
        this.colorConnection = colorConnection;
        this.elementRenderer = elementRenderer;
    }

    public void paint(Graphics2D g, GXElement gxElement)
    {
        paintAbs(g, gxElement);
    }

    /**
     * Render connection
     * @param g used to render
     * @param gxElement draw input connections
     */
    public void paintAbs(Graphics2D g, GXElement gxElement)
    {
        g.setColor(colorConnection);

        // Draw all inputs connection
        for (int inputIndex = 0; inputIndex < gxElement.getNumberOfInputs(); inputIndex++)
        {
            // Get the corresponding on input element
            GXElement onInputElement  = (GXElement) gxElement.getInputEntity(inputIndex);

            if (onInputElement != null)
            {
                int startX = (int) (elementRenderer.getRelativeInputX(gxElement) + gxElement.getX());
                int startY = (int) (elementRenderer.getRelativeInputY(inputIndex, gxElement) + gxElement.getY());

                int outputIndex = gxElement.getIndexOfOutputFromEntityOnInput(inputIndex);
                int endX        = (int) (elementRenderer.getRelativeOutputX(onInputElement) + onInputElement.getX());
                int endY = (int) (elementRenderer.getRelativeOutputY(outputIndex,
                                                                     onInputElement) + onInputElement.getY());
                g.drawLine(startX, startY, endX, endY);
            }
        }
    }
}
