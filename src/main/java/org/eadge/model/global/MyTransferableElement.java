package org.eadge.model.global;

import org.eadge.model.script.GXElement;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Created by eadgyo on 20/02/17.
 *
 * Handle GXElement transfer for drag and drop support
 */
public class MyTransferableElement  implements Transferable
{
    private GXElement GXElement;

    // Transferable
    public static DataFlavor   myElementFlavor  = new DataFlavor(GXElement.class, "GXElement Object");
    public static DataFlavor[] supportedFlavors = {myElementFlavor};


    public MyTransferableElement(GXElement GXElement)
    {
        this.GXElement = GXElement;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors()
    {
        return supportedFlavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor dataFlavor)
    {
        return dataFlavor.equals(myElementFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException, IOException
    {
        if (dataFlavor.equals(myElementFlavor))
            return GXElement;

        throw new UnsupportedFlavorException(dataFlavor);
    }

}
