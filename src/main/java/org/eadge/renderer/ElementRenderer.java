package org.eadge.renderer;

import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.model.script.GXElement;
import org.eadge.utils.GTools;

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
    private int textPadding;


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

    private final double ELEMENT_WIDTH = 250;

    public ElementRenderer(int textHeight,
                           int textPadding,
                           Color backgroundColor,
                           Color rectColor,
                           Color textNameColor,
                           EntryRenderer entryRenderer)
    {
        this.textHeight = textHeight;
        this.textPadding = textPadding;

        this.backgroundColor = backgroundColor;
        this.rectColor = rectColor;
        this.textNameColor = textNameColor;

        this.entryRenderer = entryRenderer;
    }

    public void paint(Graphics2D g, GXElement element)
    {
        g.translate(element.getX(), element.getY());
        paintRel(g, element);
        g.translate(-element.getX(), -element.getY());
        //paintDebugBlocks(g, element);
        paintDebugEntries(g, element);
    }


    public void paintRel(Graphics2D g, GXElement element)
    {
        // Get element width and height
        int elementWidth  = (int) element.getWidth();
        int elementHeight = (int) element.getHeight();

        // Render background element
        g.setColor(backgroundColor);
        g.fillRect(0, 0, elementWidth, elementHeight);
        g.setColor(rectColor);
        g.drawRect(0, 0, elementWidth, elementHeight);

        // Render name of the element
        paintRelTitle(g, element, elementWidth);

        // Translate to the start of the entries
        // g.translate(0, getTotalTextHeight() );

        // Get height of input rect
        double heightOfEntry = getHeightOfEntry(element);

        // Draw inputs
        entryRenderer.paintInputs(g, heightOfEntry, getTotalTextHeight(), element);

        // Draw outputs
        entryRenderer.paintOutputs(g, heightOfEntry, getTotalTextHeight(), element);

        //g.translate(0, -getTotalTextHeight() );

    }

    private void paintDebug(Graphics2D g, GXElement element)
    {
        for (int i = 0; i < element.getNumberOfInputs(); i++)
        {
            Rect2D inputRec = this.createInputRec(element, i);
            GTools.drawRect2D(g, inputRec, Color.YELLOW);
        }

        for (int i = 0; i < element.getNumberOfOutputs(); i++)
        {
            Rect2D outputRec = this.createOutputRec(element, i);
            GTools.drawRect2D(g, outputRec, Color.GREEN);
        }
    }
    private void paintDebugBlocks(Graphics2D g, GXElement element)
    {
        Rect2D inputZoneRect = this.createInputZoneRect(element);
        GTools.drawRect2D(g, inputZoneRect, Color.YELLOW);

        Rect2D outputZoneRect = this.createOutputZoneRect(element);
        GTools.drawRect2D(g, outputZoneRect, Color.GREEN);
    }
    private void paintDebugEntries(Graphics2D g, GXElement element)
    {
        for (int i = 0; i < element.getNumberOfInputs(); i++)
        {
            Rect2D inputRec = this.createInputRec(element, i);
            GTools.drawRect2D(g, inputRec, Color.GREEN);
        }

        for (int i = 0; i < element.getNumberOfOutputs(); i++)
        {
            Rect2D outputRec = this.createOutputRec(element, i);
            GTools.drawRect2D(g, outputRec, Color.blue);
        }
    }

    private void paintRelTitle(Graphics2D g, GXElement element, int elementWidth)
    {
        // Draw name of element
        g.setColor(textNameColor);
        int nameWidth  = g.getFontMetrics().stringWidth(element.getName());
        int fontHeight = g.getFontMetrics().getHeight();
        g.drawString(element.getName(), elementWidth * 0.5f - nameWidth * 0.5f, textPadding + fontHeight * 0.5f);
    }

    public double getRelativeInputX(GXElement gxElement)
    {
        return entryRenderer.getRelativeInputX(gxElement);
    }

    public double getRelativeInputY(int inputIndex, GXElement gxElement)
    {
        return entryRenderer.getRelativeInputY(inputIndex, gxElement, getTotalTextHeight());
    }

    public double getRelativeOutputX(GXElement gxElement)
    {
        return entryRenderer.getRelativeOutputX(gxElement);
    }

    public double getRelativeOutputY(int outputIndex, GXElement gxElement)
    {
        return entryRenderer.getRelativeOutputY(outputIndex, gxElement, getTotalTextHeight());
    }

    public double getRelativeEntryX(GXElement gxElement, boolean isInput)
    {
        return (isInput) ? getRelativeInputX(gxElement) : getRelativeOutputX(gxElement);
    }

    public double getRelativeEntryY(int entryIndex, GXElement gxElement, boolean isInput)
    {
        return (isInput) ? getRelativeInputY(entryIndex, gxElement) : getRelativeOutputY(entryIndex, gxElement);
    }

    public int getTotalTextHeight() { return textHeight + textPadding; }

    public int getTextHeight()
    {
        return textHeight;
    }

    public void setTextHeight(int textHeight)
    {
        this.textHeight = textHeight;
    }

    public int getTextPadding()
    {
        return textPadding;
    }

    public void setTextPadding(int textPadding)
    {
        this.textPadding = textPadding;
    }

    public EntryRenderer getEntryRenderer()
    {
        return entryRenderer;
    }

    public void setEntryRenderer(EntryRenderer entryRenderer)
    {
        this.entryRenderer = entryRenderer;
    }

    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    public Color getRectColor()
    {
        return rectColor;
    }

    public void setRectColor(Color rectColor)
    {
        this.rectColor = rectColor;
    }

    public Color getTextNameColor()
    {
        return textNameColor;
    }

    public void setTextNameColor(Color textNameColor)
    {
        this.textNameColor = textNameColor;
    }

    public Rect2D createInputZoneRect(GXElement gxElement)
    {
        int rectSize = entryRenderer.getRectSize();
        int entryWidth = rectSize + entryRenderer.getSizeBetween();
        double startX = getAbsoluteInputX(gxElement) - rectSize/2;
        double startY = getAbsoluteInputY(gxElement, 0) - rectSize/2;
        return new Rect2D(startX, startY,
                          entryWidth,
                          getHeightOfInputs(gxElement));
    }

    public Rect2D createOutputZoneRect(GXElement gxElement)
    {
        int rectSize = entryRenderer.getRectSize();
        int entryWidth = rectSize + entryRenderer.getSizeBetween();
        double startX = getAbsoluteOutputX(gxElement) - rectSize/2;
        double startY = getAbsoluteOutputY(gxElement, 0) - rectSize / 2;
        return new Rect2D(startX, startY,
                          entryWidth,
                          getHeightOfOutputs(gxElement));
    }

    public Rect2D createInputRec(GXElement gxElement, int inputIndex)
    {
        double absoluteX = entryRenderer.getRelativeInputX(gxElement) + gxElement.getX();
        double absoluteY = entryRenderer.getRelativeInputY(inputIndex, gxElement, getTotalTextHeight()) + gxElement
                .getY();

        double rectSize = entryRenderer.getRectSize();
        return new Rect2D(absoluteX - rectSize / 2, absoluteY - rectSize / 2, rectSize, rectSize);
    }

    public Rect2D createOutputRec(GXElement gxElement, int outputIndex)
    {
        double absoluteX = entryRenderer.getRelativeOutputX(gxElement) + gxElement.getX();
        double absoluteY = entryRenderer.getRelativeOutputY(outputIndex, gxElement, getTotalTextHeight()) + gxElement
                .getY();

        double rectSize = entryRenderer.getRectSize();
        return new Rect2D(absoluteX - rectSize / 2, absoluteY - rectSize / 2, rectSize, rectSize);
    }

    public double computeGXElementWidth(GXEntity gxEntity)
    {
        double entryWidth =  entryRenderer.getRectSize() + entryRenderer.getSizeBetween();
        return entryWidth * 2 + ELEMENT_WIDTH;
    }

    public double computeGXElementHeight(GXElement gxElement)
    {
        double entryHeight = EntryRenderer.ENTRY_HEIGHT;
        int maxInOut = gxElement.getNumberOfOutputs() > gxElement.getNumberOfInputs() ? gxElement.getNumberOfOutputs() : gxElement.getNumberOfInputs();
        return entryHeight * maxInOut + getTotalTextHeight();
    }

    public double getHeightOfEntry(GXElement element)
    {
        return entryRenderer.getHeightOfEntry(element, getTotalTextHeight());
    }

    public double getHeightOfInputs(GXElement element)
    {
        return getHeightOfEntry(element) * element.getNumberOfInputs();
    }

    public double getHeightOfOutputs(GXElement element)
    {
        return getHeightOfEntry(element) * element.getNumberOfOutputs();
    }

    /**
     * Get the absolute x coordinate of start of input
     * @param element element
     * @return absolute x coordinate of input
     */
    public double getAbsoluteInputX(GXElement element)
    {
        return element.getX() + getRelativeInputX(element);
    }

    /**
     * Get the absolute y coordinate of start of input
     * @param element element
     * @param inputIndex input index
     * @return absolute y coordinate of input
     */
    public double getAbsoluteInputY(GXElement element, int inputIndex)
    {
        return element.getY() + getRelativeInputY(inputIndex, element);
    }

    /**
     * Get the absolute x coordinate of start of output
     * @param element element
     * @return absolute x coordinate of output
     */
    public double getAbsoluteOutputX(GXElement element)
    {
        return element.getX() + getRelativeOutputX(element);
    }

    /**
     * Get the absolute y coordinate of start of output
     * @param element element
     * @param outputIndex output index
     * @return absolute y coordinate of output
     */
    public double getAbsoluteOutputY(GXElement element, int outputIndex)
    {
        return element.getY() + getRelativeOutputY(outputIndex, element);
    }

    /**
     * Get height of block containing element and text
     * @param element used element
     * @return height of element + text height
     */
    public double getBlockHeight(GXElement element)
    {
        return element.getHeight() + textHeight + 2 * textPadding;
    }

    /**
     * Get width of block containing element and text
     * @param element used element
     * @return width of element
     */
    public double getBlockWidth(GXElement element)
    {
        return element.getWidth();
    }
}
