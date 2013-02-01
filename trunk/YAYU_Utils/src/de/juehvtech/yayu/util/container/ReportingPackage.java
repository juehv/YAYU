/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.util.container;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jens
 */
public class ReportingPackage {

    // Hardware
    private String cpuLoad = "";
    private String cpuFreq = "";
    private String memoryUsed = "";
    private String memoryTotal = "";
    private String boardRevision = "";
    // Events
    private List<String> events = new ArrayList<>();
    // Versions
    private String romVersion = "";
    private String pimsVersion = "";
    private String launcherVersion = "";
    private String osVersion = "";
    private String updaterVersion = "";
    private String uploaderVersion = "";

    public String getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(String cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public String getCpuFreq() {
        return cpuFreq;
    }

    public void setCpuFreq(String cpuFreq) {
        this.cpuFreq = cpuFreq;
    }

    public String getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(String memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public String getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(String memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public String getBoardRevision() {
        return boardRevision;
    }

    public void setBoardRevision(String boardRevision) {
        this.boardRevision = boardRevision;
    }

    public String[] getEvents() {
        return events.toArray(new String[0]);
    }

    public List<String> getEventList() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public String getRomVersion() {
        return romVersion;
    }

    public void setRomVersion(String romVersion) {
        this.romVersion = romVersion;
    }

    public String getPimsVersion() {
        return pimsVersion;
    }

    public void setPimsVersion(String pimsVersion) {
        this.pimsVersion = pimsVersion;
    }

    public String getLauncherVersion() {
        return launcherVersion;
    }

    public void setLauncherVersion(String launcherVersion) {
        this.launcherVersion = launcherVersion;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getUpdaterVersion() {
        return updaterVersion;
    }

    public void setUpdaterVersion(String updaterVersion) {
        this.updaterVersion = updaterVersion;
    }

    public String getUploaderVersion() {
        return uploaderVersion;
    }

    public void setUploaderVersion(String uploaderVersion) {
        this.uploaderVersion = uploaderVersion;
    }
}
