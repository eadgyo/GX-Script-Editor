package org.eadge.view;

import org.eadge.ConstantsView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by eadgyo on 15/02/17.
 */
public class DrawView extends JPanel
{
    public DrawView()
    {
        setPreferredSize(new Dimension(ConstantsView.PREFERRED_DRAW_SIZE_WIDTH, ConstantsView.PREFERRED_DRAW_SIZE_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        // Clear background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
