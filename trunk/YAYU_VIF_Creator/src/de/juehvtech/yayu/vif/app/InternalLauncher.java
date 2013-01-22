/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.vif.app;

import de.juehvtech.yayu.vif.gui.MainGui;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Jens
 */
public class InternalLauncher {

    public static void launchVIFCreator(final JFrame parent) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGui gui = new MainGui();
                gui.setLocationRelativeTo(parent);
                gui.setVisible(true);
            }
        });
    }
}
