package org.eadge.renderer;

import org.eadge.model.script.GXElement;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by eadgyo on 24/02/17.
 *
 * Entry renderer
 */
public class EntryRenderer
{
    /**
     * Color of the entry text
     */
    private Color textColor;

    /**
     * Size of the entry rect
     */
    private int   rectSize;

    /**
     * Size between between bounds and inside entries
     */
    private int sizeBetween;

    /**
     * @param textColor Color of the entry text
     * @param rectSize Size of the entry rect
     * @param sizeBetween Size between between bounds and inside entries
     */
    public EntryRenderer(Color textColor, int rectSize, int sizeBetween)
    {
        this.textColor = textColor;
        this.rectSize = rectSize;
        this.sizeBetween = sizeBetween;
    }

    public void paintInputs(Graphics graphics, int inputHeight, GXElement gxElement)
    {
        Graphics2D g = (Graphics2D) graphics;

        // Save transformation matrix
        AffineTransform savedMatrix = g.getTransform();

        int numberOfInputs = gxElement.getNumberOfInputs();
        g.setColor(textColor);
        for (int inputIndex = 0; inputIndex < numberOfInputs; inputIndex++)
        {
            int inputX = (int) getRelativeOutputX(gxElement);
            int inputY = (int) getRelativeOutputY(inputHeight, inputIndex, gxElement);

            // Fill connexion rect
            g.fillRect( inputX - rectSize / 2,
                        inputY - rectSize / 2,
                        inputX + rectSize / 2,
                        inputY + rectSize /2);

            // Draw text
            String text = gxElement.getInputName(inputIndex);
            g.drawString(text, sizeBetween + rectSize * 3, sizeBetween);

            // Translate for the next inputs
            g.translate(0, inputHeight);
        }

        // Reset matrix transformation
        g.setTransform(savedMatrix);
    }

    public void paintOutputs(Graphics graphics, int outputHeight, GXElement gxElement)
    {
        Graphics2D g = (Graphics2D) graphics;

        int elementWidth = (int) gxElement.getWidth();

        int numberOfOutputs = gxElement.getNumberOfOutputs();
        g.setColor(textColor);
        for (int outputIndex = 0; outputIndex < numberOfOutputs; outputIndex++)
        {
            int outputX = (int) getRelativeOutputX(gxElement);
            int outputY = (int) getRelativeOutputY(outputHeight, outputIndex, gxElement);

            // Fill connexion rect
            g.fillRect( outputX - rectSize / 2,
                       outputY - rectSize / 2,
                        outputX + rectSize / 2,
                       outputY + rectSize /2);

            String text = gxElement.getOutputName(outputIndex);
            int textWidth = g.getFontMetrics().stringWidth(text);
            g.drawString(text, elementWidth - sizeBetween - rectSize * 2 - textWidth, sizeBetween);
        }
    }

    /**
     * Get input X relative to the start of the element
     * @param gxElement used element
     * @return relative x
     */
    public double getRelativeInputX(GXElement gxElement)
    {
        return sizeBetween + rectSize / 2;
    }

    /**
     * Get input Y relative to the start of the element
     * @param heightOfInput input height block
     * @param inputIndex input index
     * @param gxElement used element
     * @return relative y
     */
    public double getRelativeInputY(int heightOfInput, int inputIndex, GXElement gxElement)
    {
        return heightOfInput * (inputIndex + 0.5);
    }

    /**
     * Get output X relative to the start of the element
     * @param gxElement used element
     * @return relative x
     */
    public double getRelativeOutputX(GXElement gxElement)
    {
        return gxElement.getWidth() - sizeBetween - rectSize / 2;
    }

    /**
     * Get output Y relative to the start of the element
     * @param heightOfOutput output height block
     * @param outputIndex output index
     * @param gxElement used element
     * @return relative y
     */
    public double getRelativeOutputY(int heightOfOutput, int outputIndex, GXElement gxElement)
    {
        return heightOfOutput * (outputIndex + 0.5);
    }

    public Color getTextColor()
    {
        return textColor;
    }

    public void setTextColor(Color textColor)
    {
        this.textColor = textColor;
    }

    public int getRectSize()
    {
        return rectSize;
    }

    public void setRectSize(int rectSize)
    {
        this.rectSize = rectSize;
    }

    public int getSizeBetween()
    {
        return sizeBetween;
    }

    public void setSizeBetween(int sizeBetween)
    {
        this.sizeBetween = sizeBetween;
    }
}
