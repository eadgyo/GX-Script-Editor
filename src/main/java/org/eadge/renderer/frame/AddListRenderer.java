package org.eadge.renderer.frame;

import org.eadge.model.frame.AddListModel;
import org.eadge.model.script.MyElement;
import org.eadge.renderer.ElementRenderer;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by eadgyo on 19/02/17.
 */
public class AddListRenderer
{
    private int blockHeight;

    private Color backgroundColor;
    private Color selectedColor;

    private ElementRenderer elementRenderer;

    public AddListRenderer(int textHeight,
                           int blockHeight,
                           int betweenSize,
                           Color backgroundColor, Color selectedColor,
                           ElementRenderer elementRenderer)
    {
        this.blockHeight = blockHeight;
        this.backgroundColor = backgroundColor;
        this.selectedColor = selectedColor;
        this.elementRenderer = elementRenderer;
    }

    public void paint(Graphics2D g, int width, int height, AddListModel addListModel)
    {
        // Renderer background
        g.setColor(backgroundColor);
        g.drawRect(0, 0, width, height);

        // Renderer background for selected element
        int posY = blockHeight*addListModel.getSelectedElementIndex();
        g.setColor(selectedColor);
        g.fillRect(0, posY, width, blockHeight);

        // Save transformation matrix
        AffineTransform savedMatrix = g.getTransform();

        // Renderer all element
        AddListModel.MyGroup selectedGroup = addListModel.getSelectedGroup();
        int numberOfElements = selectedGroup.size();

        for (int elIndex = 0; elIndex < numberOfElements; elIndex++)
        {
            MyElement element = selectedGroup.get(elIndex);

            // Render element
            elementRenderer.paint(g, width, blockHeight, element);

            // Translate to start of next element
            g.translate(0, blockHeight);
        }

        // Reset matrix transformation
        g.setTransform(savedMatrix);
    }
}
