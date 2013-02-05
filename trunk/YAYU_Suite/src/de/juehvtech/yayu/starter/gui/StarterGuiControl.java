/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.starter.gui;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import de.juehvtech.yayu.discovering.client.DiscoveringClient;
import de.juehvtech.yayu.discovering.client.DiscoveringClientFactory;
import de.juehvtech.yayu.discovering.container.Messages;
import de.juehvtech.yayu.discovering.container.ServerInformationPackage;
import de.juehvtech.yayu.reporter.gui.ReporterGui;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sun.java2d.loops.GraphicsPrimitive;

/**
 *
 * @author Jens
 */
public class StarterGuiControl {

    private StarterGui parent;
    private boolean isDiscovering = false;

    public StarterGuiControl(StarterGui parent) {
        this.parent = parent;
    }

    public void startSterverDiscovering() {
        if (isDiscovering) {
            return;
        }
        isDiscovering = true;
        Thread runner = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isDiscovering) {
                    parent.resetStatus();
                    DiscoveringClient client = DiscoveringClientFactory.getClient();
                    ServerInformationPackage server = client.searchForServers(
                            Messages.DEFAULT_PORT);
                    parent.updateStatus(server);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        // not interesting
                    }
                }
                isDiscovering = false;
            }
        });
        runner.start();
    }

    public void stopDiscovering() {
        isDiscovering = false;
    }

    public void performDonateAction() {
        try {
            Desktop.getDesktop().browse(new URI(
                    "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5465UQ6WHWV7E"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ReporterGui.class.getName()).log(Level.SEVERE,
                    null, ex);
            JOptionPane.showMessageDialog(parent,
                    "An unexpected error ocured: " + ex.getLocalizedMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
