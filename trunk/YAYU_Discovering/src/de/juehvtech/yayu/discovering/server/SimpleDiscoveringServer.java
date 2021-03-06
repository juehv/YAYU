/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.discovering.server;

import de.juehvtech.yayu.discovering.container.Messages;
import de.juehvtech.yayu.discovering.util.IdGenerator;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class SimpleDiscoveringServer implements DiscoveringServer {
    
    private Thread runner;
    private boolean running = false;
    private MulticastSocket socket;
    private final String id;
    private final String rom;
    private final int port;
    private byte[] msg;
    private String status = "unknown";
    
    public SimpleDiscoveringServer(String rom, int port) {
        this.id = new IdGenerator().generateServerId(null);
        this.rom = rom;
        this.port = port;
    }
    
    @Override
    public void startServer() {
        if (running) {
            return;
        }
        running = true;
        runner = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress group = Inet4Address.getByName(Messages.GROUP);
                    socket = new MulticastSocket(port);
                    socket.joinGroup(group);
                    // build msg
                    buildMsg();
                    
                    while (running) {
                        DatagramPacket request = new DatagramPacket(new byte[256], 256);
                        socket.receive(request);
                        byte[] data = request.getData();
                        if (data[0] == Messages.REQUEST) {
                            socket.send(new DatagramPacket(msg, msg.length, group, port));
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(SimpleDiscoveringServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        runner.start();
    }
    
    @Override
    public void shutdownServer() {
        running = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
        Logger.getLogger(SimpleDiscoveringServer.class.getName())
                .info("Shutdown complete.");
        
    }
    
    private void buildMsg() {
        String msgString = id + ";" + rom + ";" + status;
        msg = new byte[msgString.length() + 2];
        msg[0] = Messages.ANSWER_REQUEST;
        msg[1] = (byte) msgString.length();
        int i = 2;
        for (byte b : msgString.getBytes()) {
            msg[i++] = b;
        }
    }
    
    @Override
    public void rebuildMsg(String status) {
        this.status = status;
        buildMsg();
    }
    
    @Override
    public void joinServerThread() {
        try {
            if (runner != null) {
                runner.join();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleDiscoveringServer.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
