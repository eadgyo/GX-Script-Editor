package org.eadge.renderer;

import org.eadge.model.script.GXElement;

import java.awt.*;

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

    public final static double FONT_HEIGHT = 10;

    public final static double ENTRY_HEIGHT = 30;

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

    public void paintInputs(Graphics graphics, double inputHeight, double totalTextHeight, GXElement gxElement)
    {
        Graphics2D g = (Graphics2D) graphics;

        int numberOfInputs = gxElement.getNumberOfInputs();
        g.setColor(textColor);
        for (int inputIndex = 0; inputIndex < numberOfInputs; inputIndex++)
        {
            int inputX = (int) getRelativeInputX(gxElement);
            int inputY = (int) getRelativeInputY(inputHeight, totalTextHeight, inputIndex);

            // Fill connexion rect
            int recX = (int) (inputX - rectSize * 0.5);
            int recY = (int) (inputY - rectSize * 0.5);
            g.fillRect( recX,
                        recY,
                        rectSize,
                        rectSize);

            // Draw text
            String text = gxElement.getInputName(inputIndex);
            int textHeight = g.getFontMetrics().getHeight();

            int textX = sizeBetween + rectSize * 2;
            int textY = (int) (inputY + textHeight * 0.5);
            g.drawString(text, textX, textY);
        }
    }

    public void paintOutputs(Graphics graphics, double outputHeight, double totalTextHeight, GXElement gxElement)
    {
        Graphics2D g = (Graphics2D) graphics;

        int elementWidth = (int) gxElement.getWidth();

        int numberOfOutputs = gxElement.getNumberOfOutputs();
        g.setColor(textColor);
        for (int outputIndex = 0; outputIndex < numberOfOutputs; outputIndex++)
        {
            int outputX = (int) getRelativeOutputX(gxElement);
            int outputY = (int) getRelativeOutputY(outputHeight, totalTextHeight, outputIndex);

            // Fill connexion rect
            int recX = (int) (outputX - rectSize * 0.5);
            int recY = (int) (outputY - rectSize * 0.5);
            g.fillRect( recX,
                        recY,
                        rectSize,
                        rectSize);

            String text = gxElement.getOutputName(outputIndex);
            int textHeight = g.getFontMetrics().getHeight();
            int textWidth = g.getFontMetrics().stringWidth(text);

            int textX = elementWidth - sizeBetween - rectSize * 2 - textWidth;
            int textY = (int) (outputY + textHeight * 0.5);
            g.drawString(text, textX, textY);
        }
    }

    /**
     * Get input X relative to the start of the element
     * @param gxElement used element
     * @return relative x
     */
    public double getRelativeInputX(GXElement gxElement)
    {
        return sizeBetween;
    }

    /**
     * Get input Y relative to the start of the element
     * @param heightOfInput input height block
     * @param inputIndex input index
     * @param totalTextHeight height of the text removed from the entries
     * @return relative y
     */
    private double getRelativeInputY(double heightOfInput, double totalTextHeight, int inputIndex)
    {
        return heightOfInput * (inputIndex + 0.5) + totalTextHeight;
    }

    /**
     * Get input Y relative to the start of the element
     * @param inputIndex input index
     * @param gxElement used element
     * @param totalTextHeight height of the text removed from the entries
     * @return relative y
     */
    public double getRelativeInputY(int inputIndex, GXElement gxElement, double totalTextHeight)
    {
        return getRelativeInputY(getHeightOfEntry(gxElement, totalTextHeight), totalTextHeight, inputIndex);
    }

    /**
     * Get output X relative to the start of the element
     * @param gxElement used element
     * @return relative x
     */
    public double getRelativeOutputX(GXElement gxElement)
    {
        return gxElement.getWidth() - sizeBetween;
    }

    /**
     * Get output Y relative to the start of the element entries
     * @param heightOfOutput output height block
     * @param totalTextHeight height of the text height
     * @param outputIndex output index
     * @return relative y
     */
    private double getRelativeOutputY(double heightOfOutput, double totalTextHeight, int outputIndex)
    {
        return heightOfOutput * (outputIndex + 0.5) + totalTextHeight;
    }

    /**
     * Get output Y relative to the start of the element
     * @param outputIndex output index
     * @param gxElement used element
     * @param totalTextHeight height of the text removed from the entry
     * @return relative y
     */
    public double getRelativeOutputY(int outputIndex, GXElement gxElement, double totalTextHeight)
    {
        return getRelativeOutputY(getHeightOfEntry(gxElement, totalTextHeight), totalTextHeight, outputIndex);
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

    public double getFontHeight() { return FONT_HEIGHT; }

    /**
     * Get the height of entry block
     * @param element used element
     * @param totalTextHeight height of the text removed from the entry
     * @return height of one entry block
     */
    public double getHeightOfEntry(GXElement element, double totalTextHeight)
    {
        // Compute input/output element size
        int numberOfInputs = element.getNumberOfInputs();
        int numberOfOutputs = element.getNumberOfOutputs();

        // Get max to align input and outputs
        double maxOfInputsOutputs = (numberOfInputs > numberOfOutputs) ? numberOfInputs : numberOfOutputs;

        return (element.getHeight() - totalTextHeight)/maxOfInputsOutputs;
    }
}
