/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.discovering.server;

/**
 *
 * @author Jens
 */
public interface DiscoveringServer {
    void startServer();
    void shutdownServer();
    void rebuildMsg(String status);
    void joinServerThread();
}
