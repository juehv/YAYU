/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.reporter.app;

import de.juehvtech.yayu.reporter.gui.ReporterGui;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Jens
 */
public class YAYU_Reporer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //TODO run discovering client
                ReporterGui window = new ReporterGui(null);
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
    }
}
