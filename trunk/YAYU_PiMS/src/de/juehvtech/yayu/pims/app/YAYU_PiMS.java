/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.pims.app;

import de.juehvtech.yayu.discovering.client.DiscoveringClientFactory;
import de.juehvtech.yayu.discovering.server.DiscoveringServer;
import de.juehvtech.yayu.discovering.server.DiscoveringServerFactory;
import de.juehvtech.yayu.discovering.util.IdGenerator;
import java.awt.EventQueue;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.java2d.loops.GraphicsPrimitive;

/**
 *
 * @author Jens
 */
public class YAYU_PiMS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, InterruptedException {
        System.out.println("Code: " + (new IdGenerator().generateServerId(null)));


        Thread main = new Thread(new Runnable() {
            @Override
            public void run() {
                DiscoveringServer server = DiscoveringServerFactory.getServer();
                server.startServer();
                while (true){
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
