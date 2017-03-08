package org.eadge.model.frame;

/**
 * Created by eadgyo on 19/02/17.
 */
public class TestsModel
{
    public boolean canExportCompiled()
    {
        return false;
    }

    /**
     * Check if the script needs inputs
     * @return true if the script needs inputs, false otherwise
     */
    public boolean isScriptIndependent() { return false; }
}
