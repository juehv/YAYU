/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.pims.communication;

import de.juehvtech.yayu.pims.hardware.ScriptInterface;
import de.juehvtech.yayu.util.communication.PiMSRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class RequestServer {

    private static final int SERVER_PORT = 2306;
    private final RemoteEventConnector connector;
    private Thread runner;
    private boolean running = false;
    private ServerSocket socket;

    public RequestServer(RemoteEventConnector connector) {
        this.connector = connector;
    }

    public void startServer() {
        if (running) {
            return;
        }
        running = true;
        runner = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new ServerSocket(SERVER_PORT);

                    while (running) {
                        Socket connection = socket.accept();
                        new Thread(new ServerThread(connection)).start();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(RequestServer.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        });
        runner.start();
    }

    public void shutdownServer() {
        running = false;
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(RequestServer.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        Logger.getLogger(RequestServer.class
                .getName())
                .info("Shutdown complete.");
    }

    private class ServerThread implements Runnable {

        private final Socket connection;

        public ServerThread(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(connection.getInputStream()));

                if (!socket.isClosed()) {
                    try {
                        PiMSRequest request = (PiMSRequest) in.readObject();
                        processRequest(request);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(RequestServer.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(RequestServer.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        private void processRequest(PiMSRequest request) {
            switch (request.getType()) {
                case REGISTER_EVENT_LISTENER:
                    connector.connect(connection.getInetAddress());
                    connector.performFullUpdate();
                    // start hardware runner
                    break;
                case DELETE_EVENT_LISTENER:
                    connector.disconnect();
                    
                    // stop hardware runner
                    break;
                case SHUTDOWN:
                    running = false;
                    try {
                        ScriptInterface.performShutdownScript();
                    } catch (IOException ex) {
                        Logger.getLogger(RequestServer.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                    break;
            }
        }
    }
}
