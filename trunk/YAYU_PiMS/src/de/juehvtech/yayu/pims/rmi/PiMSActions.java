/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.pims.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Jens
 */
public interface PiMSActions extends Remote {

    void reportServerStatus(String status) throws RemoteException;
}