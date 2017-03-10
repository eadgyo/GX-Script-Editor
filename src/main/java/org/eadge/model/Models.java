package org.eadge.model;

import org.eadge.gxscript.data.compile.script.RawGXScript;
import org.eadge.model.frame.AddListModel;
import org.eadge.model.frame.SceneModel;
import org.eadge.model.frame.TestsModel;
import org.eadge.model.global.ConnectionModel;
import org.eadge.model.global.CopyModel;
import org.eadge.model.global.SelectionModel;
import org.eadge.model.global.project.FileModel;
import org.eadge.model.script.GXLayer;
import org.eadge.model.script.GXLayerModel;
import org.eadge.model.script.Script;
import org.eadge.renderer.ElementFinder;
import org.eadge.renderer.EntryFinder;

import javax.swing.tree.MutableTreeNode;

/**
 * Created by eadgyo on 07/03/17.
 *
 * Contains all models
 */
public class Models
{
    public      RawGXScript     rawGXScript;
    public      GXLayerModel    gxLayerModel;
    public      Script          script;
    public      AddListModel    addListModel;
    public      SelectionModel  selectionModel;
    public      ConnectionModel connectionModel;
    public      FileModel       fileModel;
    public      TestsModel      testsModel;
    public      CopyModel       copyModel;
    public      SceneModel      sceneModel;
    public      EntryFinder     entryFinder;
    public      ElementFinder   elementFinder;

    public MutableTreeNode getFirstSelectedElementOrRoot()
    {
        MutableTreeNode firstSelectedElement = selectionModel.getFirstSelectedElement();
        return firstSelectedElement == null ? (MutableTreeNode) gxLayerModel.getRoot() : firstSelectedElement;
    }

    public GXLayer getFirstSelectedLayerOrRoot()
    {
        MutableTreeNode firstSelectedElementOrRoot = getFirstSelectedElementOrRoot();

        if (!(firstSelectedElementOrRoot instanceof GXLayer))
        {
            return (GXLayer) firstSelectedElementOrRoot.getParent();
        }

        return (GXLayer) firstSelectedElementOrRoot;
    }
}