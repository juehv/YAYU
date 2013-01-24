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

    MP4("video/mp4", "mp4"), AVI("video/avi", "avi"),
    MKV("video/mkv,", "mkv"), WMV("video/wmv", "wmv"),
    MPG("video/mpg", "mpg"), UNSET("", "");
    //Definition:
    private String text;
    private String fileExtention;

    MIMEType(String text, String fileExtention) {
        this.text = text;
        this.fileExtention = fileExtention;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getFileExtention() {
        return fileExtention;
    }

    public static MIMEType fromFileExtention(String extention) {
        for (MIMEType type : MIMEType.values()) {
            if (type.getFileExtention().equalsIgnoreCase(extention)) {
                return type;
            }
        }
        return UNSET;
    }
}