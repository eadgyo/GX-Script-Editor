package org.eadge.renderer;

import org.eadge.model.script.GXElement;

/**
 * Created by eadgyo on 24/02/17.
 *
 * Find the corresponding entry
 */
public class EntryFinder
{
    private ElementRenderer elementRenderer;

    public class EntryResult
    {
        public int entryIndex;
        public boolean isInput;

        public EntryResult(int entryIndex, boolean isInput)
        {
            this.entryIndex = entryIndex;
            this.isInput = isInput;
        }
    }

    public EntryFinder(ElementRenderer elementRenderer)
    {
        this.elementRenderer = elementRenderer;
    }

    public int getBlockInputIndex(GXElement element, Rect2D rect2D)
    {
        int blockIndex = getBlockEntryIndex(element, rect2D, elementRenderer.createInputZoneRect(element));
        return (blockIndex < element.getNumberOfInputs()) ? blockIndex : element.getNumberOfInputs() - 1;
    }

    public int getBlockOutputIndex(GXElement element, Rect2D rect2D)
    {
        int blockIndex = getBlockEntryIndex(element, rect2D, elementRenderer.createOutputZoneRect(element));
        return (blockIndex < element.getNumberOfOutputs()) ? blockIndex : element.getNumberOfOutputs() - 1;
    }

    private int getBlockEntryIndex(GXElement element, Rect2D rect2D, Rect2D entryRect)
    {
        // Check if the selected element is in inputRec
        if (!rect2D.intersects(entryRect))
            return -1;

        double heightOfEntry = elementRenderer.getHeightOfEntry(element);
        double relativeY = rect2D.getCenterY() - entryRect.getY();

        if (relativeY < 0)
            return 0;

        return (int) (relativeY / heightOfEntry);
    }

    public int getInputIndex(GXElement element, Rect2D rect2D)
    {
        int blockInputIndex = getBlockInputIndex(element, rect2D);

        if (blockInputIndex == -1)
            return -1;

        // Check if the mouse colliding with the rectangle
        Rect2D inputRec = elementRenderer.createInputRec(element, blockInputIndex);

        if (!inputRec.intersects(rect2D))
            return -1;

        return blockInputIndex;
    }

    public int getOutputIndex(GXElement element, Rect2D rect2D)
    {
        int blockOutputIndex = getBlockOutputIndex(element, rect2D);

        if (blockOutputIndex == -1)
            return -1;

        // Check if the mouse colliding with the rectangle
        Rect2D outputRec = elementRenderer.createOutputRec(element, blockOutputIndex);

        if (!outputRec.intersects(rect2D))
            return -1;

        return blockOutputIndex;
    }

    public EntryResult getEntryBlockIndex(GXElement gxElement, Rect2D rect2D)
    {
        int inputIndex = getBlockInputIndex(gxElement, rect2D);
        if (inputIndex != -1)
        {
            return new EntryResult(inputIndex, true);
        }
        else
        {
            // Check if it's connecting on output
            int outputIndex = getBlockOutputIndex(gxElement, rect2D);
            if (outputIndex != -1)
            {
                return new EntryResult(outputIndex, false);
            }
        }
        return new EntryResult(-1, false);
    }


    public EntryResult getEntryIndex(GXElement gxElement, Rect2D rect2D)
    {
        int inputIndex = getInputIndex(gxElement, rect2D);
        if (inputIndex != -1)
        {
            return new EntryResult(inputIndex, true);
        }
        else
        {
            // Check if it's connecting on output
            int outputIndex = getOutputIndex(gxElement, rect2D);
            if (outputIndex != -1)
            {
                return new EntryResult(outputIndex, false);
            }
        }
        return new EntryResult(-1, false);
    }

    public ElementRenderer getElementRenderer()
    {
        return elementRenderer;
    }

    public void setElementRenderer(ElementRenderer elementRenderer)
    {
        this.elementRenderer = elementRenderer;
    }
}
