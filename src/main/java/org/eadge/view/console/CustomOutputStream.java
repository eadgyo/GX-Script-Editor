package org.eadge.view.console;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class CustomOutputStream extends OutputStream
{
    private JTextArea textArea;

    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void write(String text) throws IOException
    {
        // redirects data to the text area
        textArea.append(text);
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    @Override
    public void write(byte[] bytes) throws IOException
    {
        // redirects data to the text area
        textArea.append(new String(bytes));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    @Override
    public void write(int b) throws IOException
    {
        // redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    @Override
    public void flush() throws IOException
    {
        textArea.append("\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void clear()
    {
        textArea.setText("");
    }
}