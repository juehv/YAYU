/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.util.container;

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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return new String(password);
    }
}
