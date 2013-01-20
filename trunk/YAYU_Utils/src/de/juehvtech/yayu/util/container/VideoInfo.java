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
public class VideoInfo implements Serializable{

    private String fileName;
    private String videoTitel;
    private String videoTags;
    private String videoText;
    private boolean textResolved = false;
    private boolean videoFileChecked = false; // add checksum
    //TODO implement following funtions
    private Privacy privacy = Privacy.PRIVATE;
    private License license = License.STD;
    private MIMEType type = MIMEType.MP4;
    private String category = "Games";

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

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVideoTitel() {
        return videoTitel;
    }

    public void setVideoTitel(String videoTitel) {
        this.videoTitel = videoTitel;
    }

    public String getVideoTags() {
        return videoTags;
    }

    public void setVideoTags(String videoTags) {
        this.videoTags = videoTags;
    }

    public String getVideoText() {
        return videoText;
    }

    public void setVideoText(String videoText) {
        this.videoText = videoText;
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

    public void setType(MIMEType type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VideoInfo)) {
            return false;
        }
        VideoInfo tmp = (VideoInfo) o;
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
