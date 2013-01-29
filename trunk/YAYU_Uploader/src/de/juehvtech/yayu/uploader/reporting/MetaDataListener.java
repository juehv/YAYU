/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.uploader.reporting;

/**
 *
 * @author Jens
 */
public interface MetaDataListener {
    void registerServer(String versionString);
    void unregisterServer();
}
