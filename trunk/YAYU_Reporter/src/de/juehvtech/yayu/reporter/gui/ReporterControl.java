/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.reporter.gui;

import de.juehvtech.yayu.reporter.client.ReportingClient;
import de.juehvtech.yayu.util.container.ReportingPackage;
import java.awt.Desktop;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jens
 */
public class ReporterControl {

    private final ReporterGui parent;
    private ReportingClient client = null;

    public ReporterControl(ReporterGui parent, InetAddress server) {
        this.parent = parent;
        try {
            this.client = new ReportingClient(server, parent);
        } catch (IOException ex) {
            Logger.getLogger(ReporterControl.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void registerClient() {
        parent.clearInformations();
        try {
            client.registerClient();
        } catch (RemoteException ex) {
            Logger.getLogger(ReporterControl.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void performShutdownAction() {
        client.sendShutdownCommand();
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
