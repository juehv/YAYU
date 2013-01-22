/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.discovering.client;

/**
 *
 * @author Jens
 */
public class DiscoveringClientFactory {
    
    public static DiscoveringClient getClient(){
        return new SimpleDiscoveryClient();
    }
}
