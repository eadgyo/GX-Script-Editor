package org.eadge.model.frame.global;

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
}
