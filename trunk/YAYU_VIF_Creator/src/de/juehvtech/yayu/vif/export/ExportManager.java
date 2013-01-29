/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.vif.export;

import de.juehvtech.yayu.util.container.UserPackage;
import de.juehvtech.yayu.util.container.VideoInfo;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ProgressMonitor;

/**
 *
 * @author Jens
 */
public class ExportManager {

    private static final String ACTION_FOLDER = "upload";
    private String error = "No Errors";
    private final ProgressMonitor monitor;
    private final ExportListener listener;
    public static final Charset ENCODING = StandardCharsets.UTF_8;

    public ExportManager(ProgressMonitor monitor, ExportListener listener) {
        this.monitor = monitor;
        this.listener = listener;
    }

    public void export(final List<VideoInfo> videos, final String saveDir,
            final UserPackage user) {
        Thread task = new Thread(new Runnable() {
            @Override
            public void run() {
                monitor.setProgress(5);
                monitor.setNote("Init...");
                // create action folder
                String actionDir = saveDir + "/" + ACTION_FOLDER;
                if (!new File(actionDir).mkdirs()) {
                    listener.error("Error while creating action dir.");
                    monitor.close();
                    return;
                }
                // start file video file copy in background
                List<String> videoFiles = new ArrayList<>();
                for (VideoInfo video : videos) {
                    videoFiles.add(video.getFileName());
                }
                Thread videoCopyWorker = new Thread(new VideoCopyWorker(listener,
                        videoFiles, actionDir));
                videoCopyWorker.start();
                // create decription files
                monitor.setProgress(10);
                monitor.setNote("Create descriptions...");
                List<String> descriptions = new ArrayList<>();
                for (VideoInfo video : videos) {
                    descriptions.add(video.getVideoText());
                }
                try {
                    DescriptionExportWorker.writeDescription(actionDir, descriptions);
                } catch (IOException ex) {
                    listener.error(ex.getMessage());
                    monitor.close();
                    return;
                }

                // create vif file  
                monitor.setProgress(20);
                monitor.setNote("Create .vif file...");
                VideoInfoFileExportWorker vifExporter =
                        new VideoInfoFileExportWorker(actionDir);
                try {
                    vifExporter.writeVideoInfoFile(videos);
                } catch (IOException ex) {
                    listener.error(ex.getMessage());
                    monitor.close();
                    return;
                }


                // copy server
                try {
                    ServerCoreCopyWorker.writeScriptsAndCopy(actionDir, user);
                } catch (IOException ex) {
                    listener.error(ex.getMessage());
                    monitor.close();
                    return;
                }

                // wait for copy task
                monitor.setProgress(20);
                monitor.setNote("Wait for video copy...");
                try {
                    videoCopyWorker.join();
                    monitor.setProgress(99);
                    Thread.sleep(2000);
                    monitor.close();
                } catch (InterruptedException ex) {
                    // not interesting
                }
                listener.success();
            }
        });
        task.start();
    }

    public String getErrors() {
        return error;
    }

    public boolean checkSize(final List<VideoInfo> videos, final String saveDir) {
        File test = new File(saveDir);
        long diskspace = test.getFreeSpace() / 1048576;
        Logger.getLogger(ExportManager.class.getName())
                .log(Level.INFO, "Found diskspace: {0} MB", diskspace);
        long videosize = 0;
        for (VideoInfo video : videos) {
            File vidFile = new File(video.getFileName());
            long vidSize = vidFile.length() / 1048576;
            videosize += vidSize + 1; // +1 MB for description
            Logger.getLogger(ExportManager.class.getName())
                    .log(Level.INFO, "Add \"{0}\" with {1} MB", new Object[]{video.getVideoTitel(), vidSize});
        }
        Logger.getLogger(ExportManager.class.getName())
                .log(Level.INFO, "Total video size: {0} MB", videosize);
        return videosize < diskspace;
    }
}
