/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.reporter.app;

import de.juehvtech.yayu.reporter.gui.ReporterGui;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
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

    public void launchReporter(final JFrame parent, final InetAddress server)
            throws InterruptedException, InvocationTargetException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ReporterGui window = new ReporterGui(server);
                window.setLocationRelativeTo(parent);
                window.setVisible(true);
            }
        });
    }
}
