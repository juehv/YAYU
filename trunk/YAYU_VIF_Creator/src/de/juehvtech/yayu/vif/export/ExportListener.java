/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.vif.export;

import java.util.EventListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jens
 */
public class ExportListener implements EventListener {

    private final JFrame parent;
    private boolean error = false;

    public ExportListener(JFrame parent) {
        this.parent = parent;
    }

    public void success() {
        if (error){
            return;
        }
        JOptionPane.showMessageDialog(parent, "Everything is done.", "done.",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void error(String msg) {
        error = true;
        JOptionPane.showMessageDialog(parent, "There are some errors:\n"
                + msg, "Error.",
                JOptionPane.ERROR_MESSAGE);
    }
}
