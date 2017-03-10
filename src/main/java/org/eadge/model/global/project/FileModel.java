package org.eadge.model.global.project;

/**
 * Created by eadgyo on 19/02/17.
 */
public class FileModel
{
    private String scriptPath;

    public FileModel(String scriptPath)
    {
        this.scriptPath = scriptPath;
    }

    public FileModel()
    {
        scriptPath = null;
    }

    public String getScriptPath()
    {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath)
    {
        this.scriptPath = scriptPath;
    }
}
