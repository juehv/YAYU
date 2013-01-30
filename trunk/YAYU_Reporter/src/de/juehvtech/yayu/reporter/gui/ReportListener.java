/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.reporter.gui;

import de.juehvtech.yayu.util.container.ReportingPackage;

/**
 *
 * @author Jens
 */
public interface ReportListener {

    void updateInformations(ReportingPackage info);

    void updateEvent(String eventLine);

    void clearInformations();
}
