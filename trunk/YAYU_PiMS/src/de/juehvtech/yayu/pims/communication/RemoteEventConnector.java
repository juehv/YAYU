/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.pims.communication;

import de.juehvtech.yayu.pims.hardware.ScriptInterface;
import de.juehvtech.yayu.util.communication.PiMSRemoteEvents;
import de.juehvtech.yayu.util.container.ReportingPackage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class RemoteEventConnector {
//TODO MOVE REGISTRY TO CENTRAL CLASS

    private static String REGISTRY = "PiMSRemote";
    private PiMSRemoteEvents connection = null;
    private final List<String> messages = new ArrayList<>();

    public RemoteEventConnector() {
    }

    public void connect(InetAddress server) {
        try {
            connection = (PiMSRemoteEvents) Naming.lookup("//"
                    + server.getHostAddress() + ":2222/" + REGISTRY);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(RemoteEventConnector.class.getName())
                    .log(Level.WARNING, "No PiMS Server found.", ex);
            connection = null;
        }
    }

    public void disconnect() {
        connection = null;
    }

    public void performDynamicUpdate(String msg) {
        if (connection == null) {
            return;
        }
        try {
            messages.add(msg);
            connection.dynamicUpdate(msg);
        } catch (RemoteException ex) {
            Logger.getLogger(RemoteEventConnector.class.getName())
                    .log(Level.WARNING, null, ex);
        }
    }

    public void performFullUpdate() {
        if (connection == null) {
            return;
        }
        try {
            ReportingPackage msg = new ReportingPackage();
            ScriptInterface.performAndInterpretHardwareStatusScript(msg);
            msg.setEvents(messages);
            ScriptInterface.performAndInterpretVersionScript(msg);
            connection.fullUpdate(msg);
        } catch (IOException ex) {
            Logger.getLogger(RemoteEventConnector.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void performHardwareUpdate() {
        if (connection == null) {
            return;
        }
        try {
            connection.hardwareUpdate(ScriptInterface
                    .performHardwareStatusScript());
        } catch (IOException ex) {
            Logger.getLogger(RemoteEventConnector.class.getName())
                    .log(Level.WARNING, null, ex);
        }
    }
}
