package org.eadge.renderer;

/**
 * Created by eadgyo on 24/02/17.
 *
 * Let GXLayer and GXElement contain rect2
 */
public interface Rect2DInter
{
    Rect2D getRect2D();

    void translateX(double x);

    void translateY(double y);

    void translate(double x, double y);
}
