package org.eadge.renderer;

import org.eadge.model.script.GXElement;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by eadgyo on 19/02/17.
 */
public class ElementRenderer
{
    private int textHeight;
    private int betweenSize;
    private int insideBetweenSize;
    private int rectInputOutputSize;


    private Color backgroundColor;
    private Color rectColor;
    private Color textNameColor;
    private Color textInsideColor;


    public ElementRenderer(int textHeight,
                           int betweenSize,
                           int insideBetweenSize,
                           int rectInputOutputSize,
                           Color backgroundColor,
                           Color rectColor,
                           Color textNameColor,
                           Color textInsideColor)
    {
        this.textHeight = textHeight;
        this.betweenSize = betweenSize;
        this.insideBetweenSize = insideBetweenSize;
        this.rectInputOutputSize = rectInputOutputSize;

        this.backgroundColor = backgroundColor;
        this.rectColor = rectColor;
        this.textNameColor = textNameColor;
        this.textInsideColor = textInsideColor;
    }

    public void paint(Graphics2D g, int width, int height, GXElement element)
    {
        // Draw name of element
        g.setColor(textNameColor);
        int fontHeight = g.getFontMetrics().getHeight();
        g.drawString(element.getName(), betweenSize, (height - fontHeight) * 0.5f);

        // Render background element
        g.setColor(backgroundColor);
        g.fillRect(0, textHeight + betweenSize, width, height);
        g.setColor(rectColor);
        g.drawRect(0, textHeight + betweenSize, width, height);

        // Save transformation matrix
        AffineTransform savedMatrix = g.getTransform();


        // -- Compute input/output height --
        // Compute input/output element size
        int numberOfInputs = element.getNumberOfInputs();
        int numberOfOutputs = element.getNumberOfOutputs();

        // Get max to align input and outputs
        int maxOfInputsOutputs = (numberOfInputs > numberOfOutputs) ? numberOfInputs : numberOfOutputs;

        // Compute height
        int heightOfInputOrInput = (height - insideBetweenSize * 2)/maxOfInputsOutputs;


        // -- Draw inputs --
        g.setColor(textInsideColor);
        for (int inputIndex = 0; inputIndex < numberOfInputs; inputIndex++)
        {
            // Fill connexion rect
            g.fillRect(insideBetweenSize,
                       (heightOfInputOrInput - rectInputOutputSize) / 2,
                       insideBetweenSize + rectInputOutputSize,
                       (heightOfInputOrInput + rectInputOutputSize) / 2);

            // Draw text
            String text = element.getInputName(inputIndex);
            g.drawString(text, insideBetweenSize + rectInputOutputSize * 3, insideBetweenSize);

            // Translate for the next inputs
            g.translate(0, heightOfInputOrInput);
        }

        // Reset matrix transformation
        g.setTransform(savedMatrix);


        // -- Draw inputs --
        g.setColor(textInsideColor);
        for (int outputIndex = 0; outputIndex < numberOfOutputs; outputIndex++)
        {
            // Fill connexion rect
            g.fillRect(width - insideBetweenSize - rectInputOutputSize,
                       (heightOfInputOrInput - rectInputOutputSize) / 2,
                       width - insideBetweenSize,
                       (heightOfInputOrInput + rectInputOutputSize) / 2);

            String text = element.getOutputName(outputIndex);
            int textWidth = g.getFontMetrics().stringWidth(text);
            g.drawString(text, width - insideBetweenSize - rectInputOutputSize * 2 - textWidth, insideBetweenSize);

            // Translate for the next inputs
            g.translate(0, heightOfInputOrInput);
        }

        // Reset matrix transformation
        g.setTransform(savedMatrix);
    }
}
