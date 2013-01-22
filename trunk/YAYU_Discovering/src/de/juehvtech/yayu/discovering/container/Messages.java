/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.discovering.container;

/**
 *
 * @author Jens
 */
public final class Messages {
    private Messages(){};
    
    public static final int DEFAULT_PORT=2305;
    public static final String GROUP = "224.0.0.116";
    public static final byte REQUEST = 5;
    /**
     * Answer to Request
     * Packet has the following form:
     * 6
     * length
     * String with ID, IP, Rom Version, status (running / idle)
     */
    public static final byte ANSWER_REQUEST = 6;
    
}
