/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.uploader.app;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import com.sun.mail.iap.Argument;
import de.juehvtech.yayu.uploader.account.YoutubeAccountManager;
import de.juehvtech.yayu.uploader.parsing.ArgumentParser;
import de.juehvtech.yayu.util.container.VideoInfo;
import de.juehvtech.yayu.uploader.parsing.VideoInfoFileInterpreter;
import de.juehvtech.yayu.uploader.properties.ParserTags;
import de.juehvtech.yayu.uploader.properties.Properties;
import de.juehvtech.yayu.uploader.reporting.LocalPiMSConnector;
import de.juehvtech.yayu.uploader.reporting.MetaDataListener;
import de.juehvtech.yayu.uploader.reporting.ProgressListener;
import de.juehvtech.yayu.uploader.upload.VideoUploadManager;
import de.juehvtech.yayu.util.container.UserPackage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class YAYU_Uploader {

    public static final String versionString = "Uploader 0.1B";

    public static boolean runUploader() {
        ProgressListener progress = LocalPiMSConnector.getInstance();
        progress.reportStatus("Parsing video files..");

        // Parse video info file
        VideoInfoFileInterpreter parser = new VideoInfoFileInterpreter(
                Properties.VIDEO_DIR, Properties.VIDEO_INFO_FILE);
        if (!parser.execute()) {
            System.err.println("Error while parsing files. See log for details!");
            progress.reportStatus("Parsing error.");
            return false;
        }
        progress.reportStatus("Parsing done.");
        // add videos to cache
        List<VideoInfo> videos = parser.getVideoInfos();
        VideoUploadManager uploadManager = VideoUploadManager.getInstance();
        progress.reportStatus("Import videos..");
        for (VideoInfo video : videos) {
            uploadManager.addVideoToQueue(video);
        }
        progress.reportStatus("Import done.");
        // start uploader
        progress.reportStatus("Start uploads.");
        uploadManager.start();
        progress.reportStatus("All uploads done.");
        try {
            uploadManager.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(YAYU_Uploader.class.getName()).log(
                    Level.SEVERE, "Failed to join upload manager", ex);
            return false;
        }
        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MetaDataListener meta = LocalPiMSConnector.getInstance();
        meta.registerServer(versionString);

        int parserResult = ArgumentParser.parse(args);
        boolean uploaderResult = false;
        if (parserResult == ArgumentParser.RESULT_OK) {
            uploaderResult = runUploader();
        }
        meta.unregisterServer();
        if (parserResult == ArgumentParser.RESULT_OK && uploaderResult) {
            System.out.println("Everything is done. Exit.");
            System.exit(0);
        } else {
            System.out.println("An Error Occured. Exit.");
            System.exit(parserResult);
        }
    }
}
