package org.eadge.renderer.frame;

import org.eadge.model.frame.SceneModel;
import org.eadge.model.global.SelectionModel;
import org.eadge.model.script.GXElement;
import org.eadge.model.script.GXLayer;
import org.eadge.renderer.ElementRenderer;
import org.eadge.utils.AColor;

import javax.swing.tree.MutableTreeNode;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eadgyo on 19/02/17.
 *
 * Render the scene
 */
public class SceneRenderer
{
    private Color                       backgroundColor;
    private Color                       selectedColor;
    private ElementRenderer             elementRenderer;
    private SelectionRenderer           selectionRenderer;
    private ConnectionRenderer connectionRenderer;
    private ConnectionSelectionRenderer connectionSelectionRenderer;

    private float LAYER_ALPHA_FACTOR = 0.7f;

    public SceneRenderer(Color backgroundColor,
                         Color selectedColor,
                         ElementRenderer elementRenderer,
                         SelectionRenderer selectionRenderer,
                         ConnectionRenderer connectionRenderer,
                         ConnectionSelectionRenderer connectionSelectionRenderer)
    {
        this.backgroundColor = backgroundColor;
        this.selectedColor = selectedColor;
        this.elementRenderer = elementRenderer;
        this.selectionRenderer = selectionRenderer;
        this.connectionRenderer = connectionRenderer;
        this.connectionSelectionRenderer = connectionSelectionRenderer;
    }

    /**
     * Paint scene
     * @param g graphics object used to render
     * @param width scene width
     * @param height scene height
     * @param inSceneNodes layers and elements in the scene
     * @param sceneModel scene model
     * @param selectionModel selection model
     */
    public void paint(Graphics2D g, int width, int height, Collection<MutableTreeNode> inSceneNodes, SceneModel sceneModel, SelectionModel selectionModel) {
        AffineTransform transform = this.prepareScene(g, width, height, sceneModel);

        List<GXElement> unselectedElements = new LinkedList<>();
        List<GXLayer> unselectedLayers = new LinkedList<>();

        // Retrieve unselected layers and elements
        findUnselectedLayersAndElements(inSceneNodes, selectionModel, unselectedElements, unselectedLayers);

        // Paint layers first and then elements
        paintLayers(g, unselectedLayers);
        paintSelectionLayers(g, selectionModel);
        paintElements(g, unselectedElements);
        paintSelectionElements(g, selectionModel);

        // Paint connection
        paintConnectionSelection(g, selectionModel);
        paintConnections(g, unselectedElements);

        //drawDebug(g, sceneModel);

        paintLayersName(g, unselectedLayers);
        paintLayersName(g, selectionModel.getSelectedLayers());

        // Reset graphics settings
        g.setTransform(transform);

    }

    private AffineTransform prepareScene(Graphics2D g, int width, int height, SceneModel sceneModel)
    {
        // Clear rect
        g.setColor(backgroundColor);
        g.fillRect(0, 0, width, height);

        // Save graphics settings
        AffineTransform transform = g.getTransform();

        // Set scale
        g.scale(sceneModel.getScale(), sceneModel.getScale());

        // Set translation
        g.translate(sceneModel.getTranslateX(), sceneModel.getTranslateY());

        return transform;
    }

    private void findUnselectedLayersAndElements(Collection<MutableTreeNode> inSceneNodes,
                                                 SelectionModel selectionModel,
                                                 List<GXElement> unselectedElements,
                                                 List<GXLayer> unselectedLayers)
    {
        for (MutableTreeNode node : inSceneNodes)
        {
            if (!selectionModel.contains(node))
            {
                if (node instanceof GXLayer)
                {
                    unselectedLayers.add(0, (GXLayer) node);
                }
                else
                {
                    unselectedElements.add(0, (GXElement) node);
                }
            }
        }
    }

    private void paintLayers(Graphics2D g, Collection<GXLayer> layers)
    {
        // Draw layers
        for (GXLayer layer : layers)
        {
            // Retrieve background color
            Color backgroundColor = layer.getBackgroundColor();

            // Change alpha
            Color backGroundColorLight = new AColor(backgroundColor.getRed(),
                                                    backgroundColor.getGreen(),
                                                    backgroundColor.getBlue(),
                                                    (int) (backgroundColor.getAlpha() * LAYER_ALPHA_FACTOR));

            float alpha = (backGroundColorLight.getAlpha()/255.0f);
            g.setColor(backGroundColorLight);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g.fillRect((int) layer.getX(), (int) layer.getY(), (int) layer.getWidth(), (int) layer.getHeight());
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f  ));
            g.setColor(Color.BLACK);
            g.drawRect((int) layer.getX(), (int) layer.getY(), (int) layer.getWidth(), (int) layer.getHeight());
        }
    }

    private void paintLayersName(Graphics2D g, Collection<GXLayer> layers) {
        g.setColor(Color.BLACK);
        Font font = g.getFont();
        g.setFont(new Font("TimesRoman", Font.BOLD, 16));
        // Draw layers
        for (GXLayer layer : layers)
        {
            int nameWidth  = g.getFontMetrics().stringWidth(layer.getName());
            int fontHeight = g.getFontMetrics().getHeight();

            g.drawString(layer.getName(),
                    (float) (layer.getX() + layer.getWidth() * 0.5f - nameWidth * 0.5f),
                    (float) (layer.getY() - fontHeight * 0.5f));
        }
        g.setFont(font);
    }

    private void paintElements(Graphics2D g, Collection<GXElement> entities)
    {
        // Draw all elements
        for (GXElement gxElement : entities)
        {
            elementRenderer.paint(g, gxElement);
        }
    }

    private void paintConnections(Graphics2D g, Collection<GXElement> entities)
    {
        // Draw all elements
        for (GXElement gxElement : entities)
        {
            connectionRenderer.paint(g, gxElement);
        }
    }

    private void paintSelectionLayers(Graphics2D g, SelectionModel selectionModel)
    {
        // Draw the selected elements
        Collection<MutableTreeNode> selectedElements = selectionModel.getSelectedElements();
        for (MutableTreeNode node : selectedElements)
        {
            if (node instanceof GXLayer)
            {
                GXLayer layer = (GXLayer) node;
                g.setColor(layer.getBackgroundColor());
                g.fillRect((int) layer.getX(), (int) layer.getY(), (int) layer.getWidth(), (int) layer.getHeight());
            }
        }
    }

    private void paintSelectionElements(Graphics2D g, SelectionModel selectionModel)
    {
        // Draw the selected elements
        Collection<MutableTreeNode> selectedElements = selectionModel.getSelectedElements();
        for (MutableTreeNode node : selectedElements)
        {
            if (node instanceof GXElement)
            {
                GXElement gxElement = (GXElement) node;
                selectionRenderer.paint(g, gxElement, selectionModel);
                connectionRenderer.paint(g, gxElement);
            }
        }
    }

    private void paintConnectionSelection(Graphics2D g, SelectionModel selectionModel)
    {
        connectionSelectionRenderer.paint(g, selectionModel);
    }

    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    public Color getSelectedColor()
    {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor)
    {
        this.selectedColor = selectedColor;
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
