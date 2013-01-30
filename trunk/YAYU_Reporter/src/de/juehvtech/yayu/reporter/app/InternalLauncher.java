/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.reporter.app;

import de.juehvtech.yayu.reporter.gui.ReporterGui;
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

    public void launchReporter(final JFrame parent) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    ReporterGui window = new ReporterGui();
                    window.setLocationRelativeTo(parent);
                    window.setVisible(true);
                }
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(InternalLauncher.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
