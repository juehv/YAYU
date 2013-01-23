/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.starter.gui;

import de.juehvtech.yayu.discovering.container.ServerInformationPackage;

/**
 *
 * @author Jens
 */
public interface DiscoveringCallbackListener {

    void updateStatus(ServerInformationPackage info);
    void resetStatus();
}
