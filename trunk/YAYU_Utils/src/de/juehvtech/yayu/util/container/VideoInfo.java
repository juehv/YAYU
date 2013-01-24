/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.util.container;

import java.io.Serializable;

/**
 *
 * @author Jens
 */
public class VideoInfo implements Serializable {

    private final String fileName;
    private final String videoTitel;
    private final String videoTags;
    private String videoText;
    private final MIMEType type;
    private final String category;
    private boolean textResolved = false;
    private boolean videoFileChecked = false; // add checksum
    //TODO implement following funtions
    private Privacy privacy = Privacy.PRIVATE;
    private License license = License.STD;

    public VideoInfo(String fileName, String videoTitel, String videoTags, 
            String videoText, MIMEType type, String category) {
        this.fileName = fileName;
        this.videoTitel = videoTitel;
        this.videoTags = videoTags;
        this.videoText = videoText;
        this.type = type;
        this.category = category;
    }

    // old fields
//    private String title;
//    private String tag;
//    private String description;
//    private License license;
//    private Privacy privacy;
//    private String category;
//    private MIMEType type;
//    private long checksum;
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("VideoInfo-Container for: ").append(fileName)
                .append("\nTitel: ").append(videoTitel)
                .append("\nVideotext is resolved: ").append(textResolved)
                .append("\nTags:\n").append(videoTags)
                .append("\nVideotext:\n").append(videoText)
                .append("\n------------------");
        return sb.toString();
    }

    public String getFileName() {
        return fileName;
    }

    public String getVideoTitel() {
        return videoTitel;
    }

    public String getVideoTags() {
        return videoTags;
    }

    public String getVideoText() {
        return videoText;
    }

    public boolean isTextResolved() {
        return textResolved;
    }

    public void setTextResolved(boolean textResolved) {
        this.textResolved = textResolved;
    }

    public boolean isVideoFileChecked() {
        return videoFileChecked;
    }

    public void setVideoFileChecked(boolean videoFilePresent) {
        this.videoFileChecked = videoFilePresent;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public MIMEType getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public void setVideoText(String videoText) {
        this.videoText = videoText;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VideoInfo)) {
            return false;
        }
        VideoInfo tmp = (VideoInfo) o;
        // only check titel, text, tags and filepath
        if (tmp.getVideoTitel() == null ? this.getVideoTitel() == null
                : tmp.getVideoTitel().equals(this.getVideoTitel())
                && (tmp.getVideoTags() == null ? this.getVideoTags() == null
                : tmp.getVideoTags().equals(this.getVideoTags()))
                && (tmp.getVideoText() == null ? this.getVideoText() == null
                : tmp.getVideoText().equals(this.getVideoText()))
                && (tmp.getFileName() == null ? this.getFileName() == null
                : tmp.getFileName().equals(this.getFileName()))) {
            return true;
        }

        return false;
    }
}
