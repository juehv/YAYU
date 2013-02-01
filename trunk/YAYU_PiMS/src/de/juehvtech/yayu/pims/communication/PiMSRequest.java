/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.pims.communication;

import java.io.Serializable;

/**
 *
 * @author Jens
 */
public class PiMSRequest implements Serializable {

    public enum Type {

        SHUTDOWN, REGISTER_EVENT_LISTENER, DELETE_EVENT_LISTENER;
    }
    private Type type;

    public Type getType() {
        return type;
    }
}
