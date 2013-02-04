/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.pims.hardware;

import de.juehvtech.yayu.pims.app.YAYU_PiMS;
import de.juehvtech.yayu.util.container.ReportingPackage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 *
 * @author Jens
 */
public class ScriptInterface {

    private static final String SCRIPT_DIR = "/opt/yayu/";
    private static final String HARDWARE_STATUS_CMD = SCRIPT_DIR+"hardware.sh";
    private static final String VERSION_CMD = SCRIPT_DIR+"version.sh";
    private static final String SHUTDOWN_CMD = SCRIPT_DIR+"shutdown.sh";

    public static String performHardwareStatusScript() throws IOException {
        Process script = Runtime.getRuntime().exec(HARDWARE_STATUS_CMD);
        InputStream output = script.getInputStream();
        Scanner s = new Scanner(output).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static void performAndInterpretHardwareStatusScript(ReportingPackage msg) throws IOException {
        String status = performHardwareStatusScript();
        String[] info = status.split(":");

        if (info.length < 4) {
            return;
        }

        msg.setCpuLoad(info[0]);
        msg.setCpuFreq(info[1]);
        msg.setMemoryUsed(info[2]);
        msg.setMemoryTotal(info[3]);
    }

    public static String performVersionScript() throws IOException {
        Process script = Runtime.getRuntime().exec(VERSION_CMD);
        InputStream output = script.getInputStream();
        Scanner s = new Scanner(output).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static void performAndInterpretVersionScript(ReportingPackage msg) throws IOException {
        String version = performVersionScript();
        String[] info = version.split(":");

        if (info.length < 6) {
            return;
        }

        msg.setBoardRevision(info[0]);
        msg.setRomVersion(info[1]);
        //TODO answer internally
        msg.setPimsVersion(YAYU_PiMS.versionString);
        msg.setLauncherVersion(info[2]);
        msg.setOsVersion(info[3]);
        msg.setUpdaterVersion(info[4]);
        msg.setUploaderVersion(info[5]);
    }

    public static void performShutdownScript() throws IOException {
        Runtime.getRuntime().exec(SHUTDOWN_CMD);
    }
}
