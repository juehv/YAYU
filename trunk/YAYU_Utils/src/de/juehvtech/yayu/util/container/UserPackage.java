/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.util.container;

import de.juehvtech.yayu.util.encryption.TextDecrypter;
import de.juehvtech.yayu.util.encryption.TextEncrypter;

/**
 *
 * @author Jens
 */
public class UserPackage {

    private final String username;
    private final char[] password;

    public UserPackage(String username, char[] password) {
        this.username = username;
        this.password = password;
    }

    public UserPackage(String username, String encryptedPassword) {
        this(username, TextDecrypter.decryptBase64IgnoreError(encryptedPassword));
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return new String(password);
    }

    public String getEncryptedPassword() {
        return TextEncrypter.encryptBase64IgnoreError(password);
    }

    public String getPasswordDummy() {
        StringBuilder pwDummyString = new StringBuilder();
        for (int i = 0; i < password.length; i++) {
            pwDummyString.append("*");
        }
        return pwDummyString.toString();
    }
}
