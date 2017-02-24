package org.eadge.renderer;

import org.eadge.model.script.GXElement;

import java.awt.*;

/**
 * Created by eadgyo on 19/02/17.
 *
 * Element renderer
 */
public class ElementRenderer
{
    /**
     * Height reserved to the text naming the element
     */
    private int textHeight;

    /**
     * Size between text and rect
     */
    private int betweenSize;


    private EntryRenderer entryRenderer;

    /**
     * Background element color
     */
    private Color backgroundColor;

    /**
     * Rect color
     */
    private Color rectColor;

    /**
     * Color of element name
     */
    private Color textNameColor;


    public ElementRenderer(int textHeight,
                           int betweenSize,
                           Color backgroundColor,
                           Color rectColor,
                           Color textNameColor,
                           EntryRenderer entryRenderer)
    {
        this.textHeight = textHeight;
        this.betweenSize = betweenSize;

        this.backgroundColor = backgroundColor;
        this.rectColor = rectColor;
        this.textNameColor = textNameColor;

        this.entryRenderer = entryRenderer;
    }

    public void paint(Graphics2D g, GXElement element)
    {
        // Get element width
        int elementWidth = (int) element.getWidth();
        int elementHeight = (int) element.getHeight();

        // Draw name of element
        g.setColor(textNameColor);
        int fontHeight = g.getFontMetrics().getHeight();
        g.drawString(element.getName(), betweenSize, (elementHeight - fontHeight) * 0.5f);

        // Render background element
        g.setColor(backgroundColor);
        g.fillRect(0, textHeight + betweenSize, elementWidth, elementHeight);
        g.setColor(rectColor);
        g.drawRect(0, textHeight + betweenSize, elementWidth, elementHeight);


        // Get height of input rect
        int heightOfEntry = getHeightOfEntry(element);

        // Draw inputs
        entryRenderer.paintInputs(g, heightOfEntry, element);

        // Draw outputs
        entryRenderer.paintOutputs(g, heightOfEntry, element);

    }

    /**
     * Get the height of entry block
     * @param element used element
     * @return height of one entry block
     */
    public int getHeightOfEntry(GXElement element)
    {
        // Compute input/output element size
        int numberOfInputs = element.getNumberOfInputs();
        int numberOfOutputs = element.getNumberOfOutputs();

        // Get max to align input and outputs
        int maxOfInputsOutputs = (numberOfInputs > numberOfOutputs) ? numberOfInputs : numberOfOutputs;

        return (int) ((element.getHeight() - entryRenderer.getSizeBetween() * 2)/maxOfInputsOutputs);
    }

    public double getRelativeInputX(GXElement gxElement)
    {
        return entryRenderer.getRelativeInputX(gxElement);
    }

    public double getRelativeInputY(int inputIndex, GXElement gxElement)
    {
        return entryRenderer.getRelativeInputY(getHeightOfEntry(gxElement), inputIndex, gxElement);
    }

    public double getRelativeOutputX(GXElement gxElement)
    {
        return entryRenderer.getRelativeOutputX(gxElement);
    }

    public double getRelativeOutputY(int outputIndex, GXElement gxElement)
    {
        return entryRenderer.getRelativeOutputY(getHeightOfEntry(gxElement), outputIndex, gxElement);
    }

    public double getRelativeEntryX(GXElement gxElement, boolean isInput)
    {
        return (isInput) ? getRelativeInputX(gxElement) : getRelativeOutputX(gxElement);
    }

    public double getRelativeEntryY(int entryIndex, GXElement gxElement, boolean isInput)
    {
        return (isInput) ? getRelativeInputY(entryIndex, gxElement) : getRelativeOutputY(entryIndex, gxElement);
    }
}
