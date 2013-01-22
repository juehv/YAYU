/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.discovering.server;

import de.juehvtech.yayu.discovering.container.Messages;

/**
 *
 * @author Jens
 */
public class DiscoveringServerFactory {

    public static DiscoveringServer getServer() {
        return new SimpleDiscoveringServer("locahost", "ALPHA",
                Messages.DEFAULT_PORT);
    }
}
