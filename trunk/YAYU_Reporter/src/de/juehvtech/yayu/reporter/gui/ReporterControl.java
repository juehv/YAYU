/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.reporter.gui;

import de.juehvtech.yayu.util.container.ReportingPackage;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jens
 */
public class ReporterControl {

    private final ReporterGui parent;

    public ReporterControl(ReporterGui parent) {
        this.parent = parent;
    }

    public void performRefreshActrion() {
        parent.clearInformations();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReportingPackage report = null;
                // client starten

                if (report == null) {
                    report = new ReportingPackage();
                    report.setRomVersion("error");
                    JOptionPane.showMessageDialog(parent, "Error while updateing Info.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                parent.updateInformations(report);
            }
        }).start();
    }

    public void performShutdownAction() {
        // client starten
    }

    public void performDonateAction() {
        try {
            Desktop.getDesktop().browse(new URI(
                    "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=LQSVEL8MPF7Q8"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ReporterGui.class.getName()).log(Level.SEVERE,
                    null, ex);
            JOptionPane.showMessageDialog(parent,
                    "An unexpected error ocured: " + ex.getLocalizedMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
