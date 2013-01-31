/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.vif.app;

import de.juehvtech.yayu.vif.gui.MainGui;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Jens
 */
public class InternalLauncher {

    public static void launchVIFCreator(final JFrame parent)
            throws InterruptedException, InvocationTargetException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                MainGui gui = new MainGui();
                gui.setLocationRelativeTo(parent);
                gui.setVisible(true);
            }
        });
    }
}
