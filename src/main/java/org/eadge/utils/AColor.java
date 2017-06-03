package org.eadge.utils;

import java.awt.*;
import java.awt.color.ColorSpace;

/**
 * Created by eadgyo on 02/06/17.
 */
public class AColor extends Color
{
    int alpha = 255;

    public AColor(int i, int i1, int i2)
    {
        super(i, i1, i2);
    }

    public AColor(int i, int i1, int i2, int i3)
    {
        super(i, i1, i2, i3);
        alpha = i3;
    }

    public AColor(int i)
    {
        super(i);
    }

    public AColor(int i, boolean b)
    {
        super(i, b);
    }

    public AColor(float v, float v1, float v2)
    {
        super(v, v1, v2);
    }

    public AColor(float v, float v1, float v2, float v3)
    {
        super(v, v1, v2, v3);
        alpha = (int) (v3 / 255);
    }

    public AColor(ColorSpace colorSpace, float[] floats, float v)
    {
        super(colorSpace, floats, v);
    }

    @Override
    public int getAlpha()
    {
        return alpha;
    }
}
