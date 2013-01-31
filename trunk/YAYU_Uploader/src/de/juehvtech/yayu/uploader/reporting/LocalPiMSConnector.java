/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.uploader.reporting;

import de.juehvtech.yayu.util.communication.PiMSLocalEvents;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class LocalPiMSConnector implements MetaDataListener, ProgressListener {

    private static final String RMI_URL = "//127.0.0.1/PiMS";
    private static LocalPiMSConnector INSTANCE = null;
    private PiMSLocalEvents pims = null;
    private String registeredServer = "";

    private LocalPiMSConnector() {
        try {
            pims = (PiMSLocalEvents) Naming.lookup(RMI_URL);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(LocalPiMSConnector.class.getName())
                    .log(Level.WARNING, "No PiMS Server found.", ex);
        }
    }

    public static LocalPiMSConnector getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalPiMSConnector();
        }
        return INSTANCE;
    }

    @Override
    public void registerServer(String versionString) {
        registeredServer = versionString;
        try {
            if (pims != null) {
                pims.reportServerStatus("start " + registeredServer);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LocalPiMSConnector.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void unregisterServer() {
        try {
            if (pims != null) {
                pims.reportServerStatus("stop " + registeredServer);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LocalPiMSConnector.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            registeredServer = "";
        }
    }

    @Override
    public void reportStatus(String status) {

        try {
            if (pims != null) {
                String timestamp = new Timestamp(new Date().getTime()).toString();
                pims.reportServerStatus(timestamp + " " + registeredServer
                        + ":" + status);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LocalPiMSConnector.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
