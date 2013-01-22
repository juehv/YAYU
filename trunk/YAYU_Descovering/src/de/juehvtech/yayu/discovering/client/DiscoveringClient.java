/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.discovering.client;

import de.juehvtech.yayu.discovering.container.ServerInformationPackage;

/**
 *
 * @author Jens
 */
public interface DiscoveringClient {

    /**
     * Searches for a Server and returns the first. Running server will wait
     * with an Answer so that it returns an idle server if there is one
     * available.
     *
     * @param port
     * @return
     */
    ServerInformationPackage searchForServers(int port);
}
