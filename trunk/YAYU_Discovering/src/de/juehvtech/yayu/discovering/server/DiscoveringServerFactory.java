/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.discovering.server;

import de.juehvtech.yayu.discovering.container.Messages;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class DiscoveringServerFactory {

    public static DiscoveringServer getServer(String rom) {
        String ip = "unknown";
        try {
            //TODO move into simplediscoveringserver ??
            ip = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(DiscoveringServerFactory.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return new SimpleDiscoveringServer(ip, rom, Messages.DEFAULT_PORT);
    }
}
