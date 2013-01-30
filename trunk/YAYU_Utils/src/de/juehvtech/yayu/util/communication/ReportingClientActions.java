/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.util.communication;

import de.juehvtech.yayu.util.container.ReportingPackage;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Jens
 */
public interface ReportingClientActions extends Remote {

    void fullUpdate(ReportingPackage info) throws RemoteException;

    void dynamicUpdate(String eventLine) throws RemoteException;
}
