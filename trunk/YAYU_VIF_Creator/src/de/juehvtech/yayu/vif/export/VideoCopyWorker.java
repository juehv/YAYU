/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.vif.export;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class VideoCopyWorker implements Runnable {

    public static final String VIDEO_FILE_NAME = "videofile";
    private final List<String> sourceFiles;
    private final String destinationPath;
    private final ExportListener listener;

    public VideoCopyWorker(ExportListener listener, List<String> sourceFiles, String destinationPath) {
        this.sourceFiles = sourceFiles;
        this.destinationPath = destinationPath;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            int counter = 0;
            StringBuilder sb;
            for (String file : sourceFiles) {
                // create new filename
                sb = new StringBuilder();
                sb.append(destinationPath).append("/").append(VIDEO_FILE_NAME)
                        .append(counter++)
                        .append(file.substring(file.lastIndexOf(".")));
                // copy the file
                Files.copy(Paths.get(file), Paths.get(sb.toString()),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            Logger.getLogger(VideoCopyWorker.class.getName()).log(Level.SEVERE, null, ex);
            listener.error(ex.getMessage());
        }
    }
}
