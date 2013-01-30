/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.reporter.client;

import de.juehvtech.yayu.reporter.gui.ReportListener;
import de.juehvtech.yayu.util.communication.ReportingClientActions;
import de.juehvtech.yayu.util.communication.ReportingProperties;
import de.juehvtech.yayu.util.container.ReportingPackage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
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
public class ReportingClient extends UnicastRemoteObject implements ReportingClientActions {

    private final ReportListener listener;
    private final Socket pimsSocket;

    public ReportingClient(InetAddress server, ReportListener listener)
            throws RemoteException, IOException {
        super();
        this.listener = listener;
        pimsSocket = new Socket(server, ReportingProperties.SERVER_PORT);
    }

    public void registerClient() throws RemoteException {
        // TODO prevent mulit registration
        listener.clearInformations();
        // start RMI Server
        String registry = ReportingProperties.CLIENT_REGISTRY_BASE + (Math.random() * 1024);
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Naming.rebind(registry, this);
        } catch (MalformedURLException | RemoteException ex) {
            Logger.getLogger(ReportingClient.class.getName())
                    .log(Level.SEVERE, null, ex);
            throw new RemoteException("Error with RMI Server", ex);
        }
        // send registration to server
        pimsSocket.segetChannel().write(ReportingProperties.);
    }

    @Override
    public void fullUpdate(ReportingPackage info) throws RemoteException {
        listener.updateInformations(info);
    }

    @Override
    public void dynamicUpdate(String eventLine) throws RemoteException {
        listener.updateEvent(eventLine);
    }
}
