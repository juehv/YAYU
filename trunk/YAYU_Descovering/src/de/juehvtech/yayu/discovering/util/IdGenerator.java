/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.discovering.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class IdGenerator {

    public int generateHardwareID() {
        List<Byte> hardwareIdBytes = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
            while (nifs.hasMoreElements()) {
                NetworkInterface nif = nifs.nextElement();
                if (!nif.isLoopback()) {
                    byte[] b = nif.getHardwareAddress();
                    if (b != null) {
                        for (byte bt : b) {
                            hardwareIdBytes.add(bt);
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(IdGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return hardwareIdBytes.hashCode();
        }
    }

    public String generateServerId(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            prefix = "2305";
        }
        return prefix +"-"+ Integer.toHexString(generateHardwareID()).toUpperCase();
    }
}
