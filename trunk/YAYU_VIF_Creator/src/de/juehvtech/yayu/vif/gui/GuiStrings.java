/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.vif.gui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class GuiStrings {

    private static final String baseName = "resources.vifgui";
    private static GuiStrings INSTANCE = null;
    private ResourceBundle bundle = null;

    private GuiStrings() {
        try {
            bundle = ResourceBundle.getBundle(baseName);
        } catch (MissingResourceException ex) {
            Logger.getLogger(GuiStrings.class.getName()).log(Level.SEVERE,
                    "Error while loading language file!", ex);
        }
        // LOAD STRINGS HERE
        LABEL_LIST_VIDEO = bundle.getString("label_video");
    }

    public static GuiStrings getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GuiStrings();
        }
        return INSTANCE;
    }
    
    // ADD Strings HERE
    public final String LABEL_LIST_VIDEO;
}
