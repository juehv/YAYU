/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.vif.app;

import de.juehvtech.yayu.vif.gui.MainGui;
import java.awt.Event;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Jens
 */
public class YAYU_VIF_Creator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            String name = UIManager.getSystemLookAndFeelClassName();
            try {
                UIManager.setLookAndFeel(name);
            } catch (Exception ex) {}
        MainGui gui = new MainGui();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
    }
}
