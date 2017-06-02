package org.eadge.renderer.frame;

import org.eadge.gxscript.data.entity.model.base.GXEntity;
import org.eadge.gxscript.data.io.EGXGroup;
import org.eadge.model.frame.AddListModel;
import org.eadge.model.script.GXElement;
import org.eadge.renderer.ElementRenderer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Collection;

/**
 * Created by eadgyo on 19/02/17.
 *
 * Add list elements renderer
 */
public class AddListRenderer
{
    private Color backgroundColor;
    private Color selectedColor;

    private ElementRenderer elementRenderer;

    public AddListRenderer(Color backgroundColor, Color selectedColor,
                           ElementRenderer elementRenderer)
    {
        this.backgroundColor = backgroundColor;
        this.selectedColor = selectedColor;
        this.elementRenderer = elementRenderer;
    }

    public void paint(Graphics2D g, int width, int height, AddListModel addListModel)
    {
        // Renderer background
        g.setColor(backgroundColor);
        g.drawRect(0, 0, width, height);

        // Renderer all element
        EGXGroup selectedGroup    = addListModel.getSelectedGroup();
        int      numberOfElements = selectedGroup.size();

        paintSelected(g, width, height, addListModel);

        // Save transformation matrix
        AffineTransform savedMatrix = g.getTransform();

        for (int elIndex = 0; elIndex < numberOfElements; elIndex++)
        {
            GXElement element = (GXElement) selectedGroup.get(elIndex);

            // Translate to center
            int elementWidth = (int) elementRenderer.getBlockWidth(element);
            g.translate((width - elementWidth ) * 0.5, elementRenderer.getTotalTextHeight());

            // Render element
            elementRenderer.paint(g, element);

            // Translate to start of next element
            g.translate(-(width - elementWidth ) * 0.5, -elementRenderer.getTotalTextHeight() + elementRenderer
                    .getBlockHeight(element));
        }

        // Reset matrix transformation
        g.setTransform(savedMatrix);
    }

    public void paintSelected(Graphics2D g, int width, int height, AddListModel addListModel)
    {
        int selectedElementIndex = addListModel.getSelectedElementIndex();

        if (selectedElementIndex == -1)
            return;

        double posY = 0;

        EGXGroup selectedGroup = addListModel.getSelectedGroup();

        // Iterate to the selected element
        for (int elementIndex = 0; elementIndex < selectedElementIndex; elementIndex++)
        {
             posY += elementRenderer.getBlockHeight((GXElement) selectedGroup.get(elementIndex));
        }

        // Renderer background for selected element
        g.setColor(selectedColor);
        g.fillRect(0, (int) posY, width, (int) elementRenderer.getBlockHeight((GXElement) selectedGroup.get(selectedElementIndex)));
    }

    public int getSelectedElementIndex(int x, int y, AddListModel addListModel)
    {
        if (addListModel.getSelectedGroupIndex() == -1)
            return -1;

        // Get selected group
        EGXGroup selectedGroup = addListModel.getSelectedGroup();

        // Start with the first element
        int elementIndex = -1;
        double posY = 0;

        // Iterate to the selected element
        do
        {
            elementIndex++;
            posY += elementRenderer.getBlockHeight((GXElement) selectedGroup.get(elementIndex));

        } while (posY < y && elementIndex < selectedGroup.size());

        return elementIndex;
    }

    public double getTotalHeight(Collection<GXEntity> elements)
    {
        double totalHeight = 0;
        for (GXEntity gxEntity : elements)
        {
            GXElement element = (GXElement) gxEntity;
            totalHeight += elementRenderer.getBlockHeight(element);
        }

        return totalHeight;
    }

    public void setBackgroundColor(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    public void setSelectedColor(Color selectedColor)
    {
        this.selectedColor = selectedColor;
    }

    public void setElementRenderer(ElementRenderer elementRenderer)
    {
        this.elementRenderer = elementRenderer;
    }

    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    public Color getSelectedColor()
    {
        return selectedColor;
    }

    public ElementRenderer getElementRenderer()
    {
        return elementRenderer;
    }
}
