package org.eadge.model.frame.global;

import org.eadge.model.script.MyElement;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Created by eadgyo on 20/02/17.
 *
 * Handle myElement transfer for drag and drop support
 */
public class MyTransferableElement  implements Transferable
{
    private MyElement myElement;

    // Transferable
    public static DataFlavor   myElementFlavor  = new DataFlavor(MyElement.class, "MyElement Object");
    public static DataFlavor[] supportedFlavors = {myElementFlavor};


    public MyTransferableElement(MyElement myElement)
    {
        this.myElement = myElement;
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
            return myElement;

        throw new UnsupportedFlavorException(dataFlavor);
    }

}
