/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.pims.communication;

import de.juehvtech.yayu.discovering.server.DiscoveringServer;
import de.juehvtech.yayu.util.communication.PiMSLocalEvents;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class LocalEventServer extends UnicastRemoteObject
        implements PiMSLocalEvents {

    private static final String REGISTRY = "PiMS";
    private final DiscoveringServer disServer;
    private final RemoteEventConnector remoteEventConnector;

    public LocalEventServer(DiscoveringServer disServer,
            RemoteEventConnector remoteEventConnector)
            throws RemoteException {
        super();
        this.disServer = disServer;
        this.remoteEventConnector = remoteEventConnector;
    }

    public void startServer() {
        try {
            //TODO change to local and add ssl
            // http://stackoverflow.com/questions/9307764/localhost-only-rmi
            // http://docs.oracle.com/javase/1.5.0/docs/guide/security/jsse/samples/index.html
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Naming.rebind(REGISTRY, this);
        } catch (MalformedURLException | RemoteException ex) {
            Logger.getLogger(LocalEventServer.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void reportServerStatus(String status) throws RemoteException {
        disServer.rebuildMsg(status);
        remoteEventConnector.performDynamicUpdate(status);
    }
}
