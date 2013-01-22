/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.discovering.client;

import de.juehvtech.yayu.discovering.container.Messages;
import de.juehvtech.yayu.discovering.container.ServerInformationPackage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
class SimpleDiscoveryClient implements DiscoveringClient {

    @Override
    public ServerInformationPackage searchForServers(int port) {
        String retval = "";
        try {
            InetAddress group = Inet4Address.getByName(Messages.GROUP);
            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(group);
            socket.send(new DatagramPacket(new byte[]{Messages.REQUEST}, 1,
                    group, port));
            // wait for answer
            DatagramPacket answer = new DatagramPacket(new byte[256], 256);
            // buffer can't be larger than 256 because the length fiels is only
            // one byte
            int round = 0;
            while (round < 2) { // 1st run should receive the request
                socket.receive(answer); // blocks until receives
                // if there is no server it will block forever
                byte[] data = answer.getData();
                if (data[0] == Messages.ANSWER_REQUEST) {
                    int length = (byte) data[1];
                    byte[] stringArray = new byte[length];
                    for (int i = 2; i < length + 2; i++) {
                        stringArray[i - 2] = data[i];
                    }
                    retval = new String(stringArray);
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(SimpleDiscoveryClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return ServerInformationPackage.parseString(retval);
        }
    }
}
