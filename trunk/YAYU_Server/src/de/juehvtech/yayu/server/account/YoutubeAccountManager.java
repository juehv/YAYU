/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.server.account;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.util.AuthenticationException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JUehV
 */
public class YoutubeAccountManager {
    //verschlüsseln mit rsa (PGP)
    //client verschlüsselt mit public key und schickt
    //dieser string wird gespeichert
    //vorm auth entschlüsseln und schicken
    // nur einmal entsclhüsseln um footprint gering zu halten

    private static YoutubeAccountManager INSTANCE;
    private static String username = null;
    private static char[] password = null;

    public static void setUser(String username, char[] password) {
        YoutubeAccountManager.username = username;
        YoutubeAccountManager.password = password;
    }

    public static void clearPassword() {
        if (password != null) {
            Arrays.fill(password, '0');
        }
    }

//    @Deprecated
//    public String getPassword() {
//        return password;
//    }
//
//    @Deprecated
//    public String getUsername() {
//        return username;
//    }
    private YoutubeAccountManager() {
        //empty
    }

    public static YoutubeAccountManager getInstance() {
        if (INSTANCE == null) {
            if (username == null || password == null) {
                // Programmierfehler
                throw new IllegalAccessError("Set Username & Password first!");
            }
            INSTANCE = new YoutubeAccountManager();
        }
        return INSTANCE;
    }

//    public static boolean isUserSet() {
//        if (INSTANCE == null) {
//            return false;
//        } else {
//            return true;
//        }
//    }
    public YouTubeService getService() {
        try {
            YouTubeService retval =
                    new YouTubeService(
                    "Yet Anaoter Youtube Uploader by JUehV Tech",
                    "AI39si70rO-pab9h0paKhewSJ0SxLXvFncIlg_PlxD3yhHKcVdgbqqz"
                    + "GWZRqhvshoBJ-Ej2ELktlszVBtcgFI0Fog_YZYzlqdQ");
            retval.setUserCredentials(username, String.copyValueOf(password));
            return retval;
        } catch (AuthenticationException ex) {
            Logger.getLogger(YoutubeAccountManager.class.getName()).log(Level.SEVERE, "Error while getting youtube service (wrong user data??)!", ex);
        }
        return null;
    }
}