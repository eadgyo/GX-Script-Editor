package org.eadge.model.script;

import org.eadge.model.frame.SceneModel;

import java.io.Serializable;

public class SavedScript implements Serializable
{
    public Script script;
    public double x;
    public double y;
    public double scale;

    public SavedScript(Script script, SceneModel sceneModel)
    {
        this.script = script;
        this.x = sceneModel.getTranslateX();
        this.y = sceneModel.getTranslateY();
        this.scale = sceneModel.getScale();
    }
}
