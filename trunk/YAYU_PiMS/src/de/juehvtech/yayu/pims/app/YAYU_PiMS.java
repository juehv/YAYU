/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.pims.app;

import de.juehvtech.yayu.discovering.server.DiscoveringServer;
import de.juehvtech.yayu.discovering.server.DiscoveringServerFactory;
import de.juehvtech.yayu.discovering.util.IdGenerator;
import de.juehvtech.yayu.pims.rmi.ActionServer;
import de.juehvtech.yayu.util.encryption.TextDecrypter;
import de.juehvtech.yayu.util.encryption.TextEncrypter;
import java.awt.event.TextEvent;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class YAYU_PiMS {

    private static final String versionString = "ROM 0.8 BETA";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, InterruptedException, Exception {
        System.out.println("Server-Code: " + (new IdGenerator().generateServerId(null)));

        Thread main = new Thread(new Runnable() {
            @Override
            public void run() {
                DiscoveringServer disServer = DiscoveringServerFactory.getServer(versionString);
                disServer.startServer();
                ActionServer rmiServer;
                try {
                    rmiServer = new ActionServer(disServer);
                    rmiServer.startServer();
                } catch (RemoteException ex) {
                    Logger.getLogger(YAYU_PiMS.class.getName())
                            .log(Level.SEVERE, "RMI not started", ex);
                }
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(YAYU_PiMS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        main.start();
        main.join();

    }
}
