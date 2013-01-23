/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.vif.export;

import de.juehvtech.yayu.util.container.UserPackage;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Jens
 */
public class ServerCoreCopyWorker {

//    public static final String SCRIPT_FILE_NAME = "start_upload.sh";
//    public static final String LAUNCHER_FILE_NAME = "launcher.sh";
    public static final String ARG_FILE_NAME = "args";
    public static final String SERVER_FILE_NAME = "yayu-server";
    public static final String ACTION_FILE_NAME = "actionUpload";

    public static void writeScriptsAndCopy(final String path,
            final UserPackage user)
            throws IOException {

        StringBuilder sb;
        OutputStreamWriter outFile;
        // create arg file
        sb = new StringBuilder();
        sb.append(path).append("/").append(ARG_FILE_NAME);
        //      create output stream (no filewriter because of encoding)
        // using utf to enable complex passwords
        outFile = new OutputStreamWriter(
                new FileOutputStream(sb.toString()), StandardCharsets.UTF_8);
        try (PrintWriter out = new PrintWriter(outFile)) {
            // write args
            //TODO centralize strings
            out.println("-u");
            out.println(user.getUsername());
            out.println("-p");
            out.println(user.getEncryptedPassword());
            out.println("-d");
            out.println("./");
            out.println("-F");
            out.println("videoInfos.vif");
            out.flush();
            out.close();
        }

        // DOESNT WORK ... PROBLEMS WITH ENCODING!
//        // create launcher
//        sb = new StringBuilder();
//        sb.append(path).append("/").append(LAUNCHER_FILE_NAME);
//        //      create output stream (no filewriter because of encoding)
//        // using ansi ti avoid problems with encoding on linux
//        outFile = new OutputStreamWriter(
//                new FileOutputStream(sb.toString()), StandardCharsets.US_ASCII);
//        
//        try (PrintWriter out = new PrintWriter(outFile)) {
//            // write commands
//            out.println("#!/bin/bash");
//            out.println("java -jar ./YAYU_PI_Server.jar 2>&1 >log.txt");
//            out.flush();
//            out.close();
//        }
//
//        // create start script
//        sb = new StringBuilder();
//        sb.append(path).append("/").append(SCRIPT_FILE_NAME);
//        //      create output stream (no filewriter because of encoding)
//        outFile = new OutputStreamWriter(
//                new FileOutputStream(sb.toString()), StandardCharsets.US_ASCII);
//        try (PrintWriter out = new PrintWriter(outFile)) {
//            // write commands
//            out.println("#!/bin/bash");
//            out.println("screen -S yayu -d -m " + LAUNCHER_FILE_NAME);
//            out.flush();
//            out.close();
//        }

        // copy server jar
        sb = new StringBuilder();
        sb.append(path).append("/").append(SERVER_FILE_NAME);
        Files.copy(Paths.get(SERVER_FILE_NAME), Paths.get(sb.toString()),
                StandardCopyOption.REPLACE_EXISTING);

        // create action file
        sb = new StringBuilder();
        sb.append(path).append("/").append(ACTION_FILE_NAME);
        // Touches the file.
        new FileWriter(sb.toString()).close();

    }
}
