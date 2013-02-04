/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.reporter.client;

import de.juehvtech.yayu.reporter.gui.ReportListener;
import de.juehvtech.yayu.util.communication.PiMSRemoteEvents;
import de.juehvtech.yayu.util.communication.PiMSRequest;
import de.juehvtech.yayu.util.container.ReportingPackage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class ReportingClient extends UnicastRemoteObject implements PiMSRemoteEvents {

    private static String REGISTRY = "PiMSRemote";
    private static final int SERVER_PORT = 2306;
    private final ReportListener listener;
    private final Socket pimsSocket;
    private List<String> eventListCache;

    public ReportingClient(InetAddress server, ReportListener listener)
            throws IOException {
        super();
        this.listener = listener;
        pimsSocket = new Socket(server, SERVER_PORT);
    }

    public void registerClient() throws RemoteException {
        // TODO prevent mulit registration
        listener.clearInformations();
        // start RMI Server
        try {
            LocateRegistry.createRegistry(2222);
            LocateRegistry.getRegistry(2222).rebind(REGISTRY, this);
        } catch ( RemoteException ex) {
            Logger.getLogger(ReportingClient.class.getName())
                    .log(Level.SEVERE, null, ex);
            throw new RemoteException("Error with RMI Server", ex);
        }
        try (ObjectOutputStream os =
                        new ObjectOutputStream(pimsSocket.getOutputStream())) {
            os.writeObject(
                    new PiMSRequest(PiMSRequest.Type.REGISTER_EVENT_LISTENER));
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(ReportingClient.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void fullUpdate(ReportingPackage info) throws RemoteException {
        listener.updateInformations(info);
        eventListCache = info.getEventList();
    }

    @Override
    public void dynamicUpdate(String eventLine) throws RemoteException {
        eventListCache.add(eventLine);
        listener.updateEvent(eventListCache.toArray(new String[0]));
    }

    @Override
    public void hardwareUpdate(String parameter) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendShutdownCommand() {
        try (ObjectOutputStream os =
                        new ObjectOutputStream(pimsSocket.getOutputStream())) {
            os.writeObject(
                    new PiMSRequest(PiMSRequest.Type.SHUTDOWN));
        } catch (IOException ex) {
            Logger.getLogger(ReportingClient.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
