/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.server.reporting;

import de.juehvtech.yayu.pims.rmi.PiMSActions;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class LocalPiMSConnector implements MetaDataListener, ProgressListener {

    private static final String RMI_URL = "//127.0.0.1/PiMS";
    private static LocalPiMSConnector INSTANCE = null;
    private PiMSActions pims = null;
    private String registeredServer = "";

    private LocalPiMSConnector() {
        try {
            pims = (PiMSActions) Naming.lookup(RMI_URL);
            //empty
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
                pims.reportServerStatus("online (" + registeredServer + ")");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LocalPiMSConnector.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void unregisterServer() {
        registeredServer = "";
        try {
            if (pims != null) {
                pims.reportServerStatus("offline.");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LocalPiMSConnector.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void reportStatus(String status) {

        try {
            if (pims != null) {
                pims.reportServerStatus(status + " (" + registeredServer + ")");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LocalPiMSConnector.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
