package org.eadge.renderer.frame;

import org.eadge.model.frame.global.ConnectionModel;
import org.eadge.model.frame.global.SelectionModel;
import org.eadge.model.script.GXElement;
import org.eadge.renderer.ElementRenderer;

import java.awt.*;

/**
 * Created by eadgyo on 24/02/17.
 *
 * Connection renderer
 */
public class ConnectionRenderer
{
    /**
     * Keeps entry renderer to compute X and Y elements
     */
    private ElementRenderer elementRenderer;

    /**
     * Keeps the color of the valid line
     */
    private Color validLineColor;

    /**
     * Keeps the color of the invalid line
     */
    private Color invalidLineColor;

    public ConnectionRenderer(ElementRenderer elementRenderer, Color validLineColor, Color invalidLineColor)
    {
        this.elementRenderer = elementRenderer;
        this.validLineColor = validLineColor;
        this.invalidLineColor = invalidLineColor;
    }

    /**
     * Render connection
     * @param g used to render
     * @param selectionModel information on selection
     */
    public void paint(Graphics2D g, SelectionModel selectionModel)
    {
        if (selectionModel.getSelectionState().equals(SelectionModel.SelectionState.CONNECTING))
        {
            // Get first element
            GXElement firstSelectedElement = (GXElement) selectionModel.getFirstSelectedElement();

            // Get the on drag element
            GXElement onDragElement = (GXElement) selectionModel.getOnDragElement();

            // Compute X and Y coordinates of each points
            ConnectionModel connectionModel = selectionModel.getConnectionModel();

            boolean isStartInput = connectionModel.isStartInput();
            int startIndexEntry = connectionModel.getStartIndex();
            int startX = (int) (elementRenderer.getRelativeEntryX(firstSelectedElement, isStartInput) + firstSelectedElement.getX());
            int startY = (int) (elementRenderer.getRelativeEntryY(startIndexEntry, firstSelectedElement, isStartInput) + firstSelectedElement.getY());

            boolean isEndInput = connectionModel.isEndInput();
            int endIndexEntry = connectionModel.getEndIndex();
            int endX = (int) (elementRenderer.getRelativeEntryX(onDragElement, isEndInput) + onDragElement.getX());
            int endY = (int) (elementRenderer.getRelativeEntryY(endIndexEntry, onDragElement, isEndInput) + onDragElement.getY());


            // Draw line using different line color
            boolean actionValid = selectionModel.isActionValid();

            if (actionValid)
            {
                g.setColor(validLineColor);
            }
            else
            {
                g.setColor(invalidLineColor);
            }

            g.drawLine(startX, startY, endX, endY);
        }
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
