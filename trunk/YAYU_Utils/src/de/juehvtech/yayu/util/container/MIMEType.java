/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.util.container;

/**
 *
 * @author JUehV
 */
public enum MIMEType {

    MP4("video/mp4", ".mp4"),AVI("video/avi",".avi");
    //Definition:
    private String text;
    private String fileExtention;

    MIMEType(String text, String fileExtention) {
        this.text = text;
        this.fileExtention = fileExtention;
    }
    
    @Override
    public String toString(){
        return text;
    }
    
    public String getFileextention(){
        return fileExtention;
    }
}