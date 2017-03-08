package org.eadge.model;

import org.eadge.gxscript.data.compile.script.RawGXScript;
import org.eadge.model.frame.AddListModel;
import org.eadge.model.frame.TestsModel;
import org.eadge.model.frame.global.ConnectionModel;
import org.eadge.model.frame.global.SelectionModel;
import org.eadge.model.frame.global.project.FileModel;
import org.eadge.model.script.GXLayerModel;
import org.eadge.model.script.Script;

import javax.swing.tree.MutableTreeNode;

/**
 * Created by eadgyo on 07/03/17.
 */
public class Models
{
    public RawGXScript     rawGXScript;
    public GXLayerModel    gxLayerModel;
    public Script          script;
    public AddListModel    addListModel;
    public SelectionModel  selectionModel;
    public ConnectionModel connectionModel;
    public FileModel       fileModel;
    public TestsModel      testsModel;

    public MutableTreeNode getFirstSelectedElementOrRoot()
    {
        MutableTreeNode firstSelectedElement = selectionModel.getFirstSelectedElement();
        return firstSelectedElement == null ? (MutableTreeNode) gxLayerModel.getRoot() : firstSelectedElement;
    }
}