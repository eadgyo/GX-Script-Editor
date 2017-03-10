package org.eadge.model.global;

import org.eadge.model.global.project.SelectionObservable;

/**
 * Created by eadgyo on 19/02/17.
 *
 * Holds selected connection information
 */
public class ConnectionModel
{
    /**
     * Store the starting element input/output index
     */
    private int startIndex = -1;

    /**
     * True if the start connection is on input entries, False on output entries
     */
    private boolean isStartInput = true;

    /**
     * Store the index of the
     */
    private int endIndex = -1;

    /**
     * True if the end connection is on output entries, False on output entries
     */
    private boolean isEndInput = true;

    /**
     * Keeps selection observable
     */
    private SelectionObservable selectionObservable;

    /**
     * Keeps the desired Point on dragged X coordinate
     */
    private double desiredX;

    /**
     * Keeps the desired Point on dragged Y coordinate
     */
    private double desiredY;

    /**
     * Keeps state of desiring point
     */
    private boolean isDesiring;

    public void setSelectionObservable(SelectionObservable selectionObservable)
    {
        this.selectionObservable = selectionObservable;
    }

    public void callObservers()
    {
        selectionObservable.callObservers();
    }

    public void clearAll()
    {
        startIndex = -1;
        endIndex = -1;
    }

    public void clearStartConnection()
    {
        startIndex = -1;
    }

    public void clearEndConnection()
    {
        endIndex = -1;
    }

    public int getStartIndex()
    {
        return startIndex;
    }

    public void setStartIndex(int startIndex)
    {
        this.startIndex = startIndex;
    }

    public void setStartIndex(int startIndex, boolean isStartInput)
    {
        this.startIndex = startIndex;
        this.isStartInput = isStartInput;
    }

    public boolean isStartInput()
    {
        return isStartInput;
    }

    public void setStartInput(boolean startInput)
    {
        isStartInput = startInput;
    }

    public int getEndIndex()
    {
        return endIndex;
    }

    public void setEndIndex(int endIndex)
    {
        this.endIndex = endIndex;
    }

    public boolean isEndInput()
    {
        return isEndInput;
    }

    public void setEndInput(boolean endInput)
    {
        isEndInput = endInput;
    }

    public void setEndIndex(int endIndex, boolean isEndInput)
    {
        this.endIndex = endIndex;
        this.isEndInput = isEndInput;
    }

    public double getDesiredX()
    {
        return desiredX;
    }

    public void setDesiredX(double desiredX)
    {
        this.desiredX = desiredX;
    }

    public double getDesiredY()
    {
        return desiredY;
    }

    public void setDesiredY(double desiredY)
    {
        this.desiredY = desiredY;
    }

    public void setDesiredPos(double x, double y)
    {
        this.desiredX = x;
        this.desiredY = y;
    }

    public void setDesiring(boolean isDesiring)
    {
        this.isDesiring = isDesiring;
    }

    public boolean isDesiring()
    {
        return isDesiring;
    }
}
