/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.pims.rmi;

import de.juehvtech.yayu.discovering.server.DiscoveringServer;
import de.juehvtech.yayu.util.communication.PiMSEvents;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class LocalEventServer extends UnicastRemoteObject implements PiMSEvents {

    private static final String REGISTRY = "PiMS";
    private final DiscoveringServer disServer;

    public LocalEventServer(DiscoveringServer disServer) throws RemoteException {
        super();
        this.disServer = disServer;
    }

    public void startServer() {
        try {
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
    }
}