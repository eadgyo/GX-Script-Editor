package org.eadge.utils;

import org.eadge.gxscript.data.compile.script.CompiledGXScript;
import org.eadge.gxscript.tools.check.GXValidator;
import org.eadge.gxscript.tools.compile.GXCompiler;
import org.eadge.gxscript.tools.io.IOGXManager;
import org.eadge.model.script.SavedScript;
import org.eadge.model.script.Script;

/**
 * Created by eadgyo on 06/03/17.
 */
public class AdvIOM extends IOGXManager
{
    public void saveScript(SavedScript script, String path)
    {
        saveObject(script, path);
    }

    public SavedScript loadScript(String path)
    {
        return (SavedScript) getObject(path);
    }

    public CompiledGXScript loadCompiledOrScript(String path)
    {
        CompiledGXScript compiledGXScript = loadCompiledGXScript(path);

        if (compiledGXScript == null)
        {
            // Try loading script
            SavedScript savedScript = loadScript(path);
            Script script = savedScript.script;

            if (script != null)
            {
                // Validate this script
                GXValidator gxValidator = new GXValidator();
                boolean     validate    = gxValidator.validate(script.getRawGXScript());

                if (validate)
                {
                    GXCompiler compiler = new GXCompiler();
                    // Test script:

                    compiledGXScript = compiler.compile(script.getRawGXScript());
                }
            }
        }

        return compiledGXScript;
    }

    public static AdvIOM getAdv()
    {
        if (iogxManager == null)
            iogxManager = new AdvIOM();
        return (AdvIOM) iogxManager;
    }
}
