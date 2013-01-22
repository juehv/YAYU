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
    
    public void startSterverDiscovering(){
        if (isDiscovering){
            return;
        }
        Thread runner = new Thread(new Runnable() {

            @Override
            public void run() {
                isDiscovering = true;
                DiscoveringClient client = DiscoveringClientFactory.getClient();
                ServerInformationPackage server = client.searchForServers(
                        Messages.DEFAULT_PORT);
                parent.updateStatus(server);
                isDiscovering = false;
            }
        });
        runner.start();
    }
}
