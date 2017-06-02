package org.eadge;

/**
 * Created by eadgyo on 02/06/17.
 */
public class DebugTools
{
    public static boolean isPrintingDebug = true;

    public static void PrintDebug(String text)
    {
        if (isPrintingDebug)
            System.out.println(text);
    }

}
