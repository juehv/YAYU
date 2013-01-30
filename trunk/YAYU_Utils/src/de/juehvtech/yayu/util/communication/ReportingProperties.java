/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.util.communication;

import java.io.Serializable;

/**
 *
 * @author Jens
 */
public final class ReportingProperties implements Serializable {

    private ReportingProperties() {
        // empty
    }
    public static final int SERVER_PORT = 2305;
    public static final String CLIENT_REGISTRY_BASE = "YAYU_REPORTING";
    public static final byte MESSAGE_REGISTER = 7;
}
