/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.starter.app;

import de.juehvtech.yayu.starter.gui.StarterGui;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Jens
 */
public class YAYU_Suite {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // empty
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StarterGui gui = new StarterGui();
                gui.setLocationRelativeTo(null);
                gui.setVisible(true);
            }
        });
    }
}
