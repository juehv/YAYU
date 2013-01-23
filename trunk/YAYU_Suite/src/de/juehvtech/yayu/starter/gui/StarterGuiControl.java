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
import java.util.logging.Level;
import java.util.logging.Logger;
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
}
