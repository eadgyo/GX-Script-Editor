package org.eadge.renderer.frame;

import org.eadge.model.frame.global.SelectionModel;
import org.eadge.model.script.GXElement;
import org.eadge.renderer.ElementRenderer;

import java.awt.*;

/**
 * Created by eadgyo on 24/02/17.
 *
 * Render selected element
 */
public class SelectionRenderer
{
    private ElementRenderer selectedElementRenderer;
    private ElementRenderer invalidMovingRenderer;
    private ElementRenderer movingElementRenderer;

    public SelectionRenderer(ElementRenderer selectedElementRenderer,
                             ElementRenderer invalidMovingRenderer,
                             ElementRenderer movingElementRenderer)
    {

        this.selectedElementRenderer = selectedElementRenderer;
        this.invalidMovingRenderer = invalidMovingRenderer;
        this.movingElementRenderer = movingElementRenderer;
    }

    public void paint(Graphics2D g, GXElement gxElement, SelectionModel selectionModel)
    {
        if (selectionModel.getSelectionState() == SelectionModel.SelectionState.MOVING)
        {
            if (selectionModel.isActionValid())
            {
                movingElementRenderer.paint(g, gxElement);
            }
            else
            {
                invalidMovingRenderer.paint(g, gxElement);
            }
        }
        else
        {
            selectedElementRenderer.paint(g, gxElement);
        }
    }

    public ElementRenderer getSelectedElementRenderer()
    {
        return selectedElementRenderer;
    }

    public void setSelectedElementRenderer(ElementRenderer selectedElementRenderer)
    {
        this.selectedElementRenderer = selectedElementRenderer;
    }

    public ElementRenderer getInvalidMovingRenderer()
    {
        return invalidMovingRenderer;
    }

    public void setInvalidMovingRenderer(ElementRenderer invalidMovingRenderer)
    {
        this.invalidMovingRenderer = invalidMovingRenderer;
    }

    public ElementRenderer getMovingElementRenderer()
    {
        return movingElementRenderer;
    }

    public void setMovingElementRenderer(ElementRenderer movingElementRenderer)
    {
        this.movingElementRenderer = movingElementRenderer;
    }

}
