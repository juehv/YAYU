/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.vif.export;

import com.csvreader.CsvWriter;
import de.juehvtech.yayu.util.container.VideoInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jens
 */
public class VideoInfoFileExportWorker {

    private static final String VERSION = "0.1";
    private static final String PROGRAM_VERSION = "0.1";
    public static final String VIDEO_INFO_FILE_NAME = "videoInfos.vif";
    private static final String videoFileName = VideoCopyWorker.VIDEO_FILE_NAME;
    private static final String descriptionFileName =
            DescriptionExportWorker.DESCRIPTION_FILE_NAME;
    private static final String descriptionFileMime =
            DescriptionExportWorker.DESCRIPTION_FILE_MIME;
    private final String outputDir;

    public VideoInfoFileExportWorker(String outputDir) {
        this.outputDir = outputDir;
    }

    public void writeVideoInfoFile(List<VideoInfo> videos) throws IOException {
        // create  file names
        int counter = 0;
        List<String> videoFileNames = new ArrayList<>();
        List<String> descriptionFileNames = new ArrayList<>();
        StringBuilder fileNameBuilder;
        for (VideoInfo video : videos) {
            fileNameBuilder = new StringBuilder();
            fileNameBuilder.append(videoFileName).append(counter)
                    .append(video.getFileName().substring(
                    video.getFileName().lastIndexOf(".")));
            videoFileNames.add(fileNameBuilder.toString());
            fileNameBuilder = new StringBuilder();
            fileNameBuilder.append(descriptionFileName).append(counter++)
                    .append(descriptionFileMime);
            descriptionFileNames.add(fileNameBuilder.toString());
        }

        // create lines

        // create writer
        CsvWriter writer = new CsvWriter(outputDir + "/" + VIDEO_INFO_FILE_NAME, ',',
                ExportManager.ENCODING);
        // write to vif (csv)
        // create format info
        writer.writeComment("Video Info File for YAYU Server");
        writer.writeComment("File Format Version: " + VERSION);
        writer.writeComment("Created through YAYU VIF Creator Version: " + PROGRAM_VERSION);
        writer.writeComment("This is free Software, if you have paied for it contact me!");
        writer.writeComment("License: GPLv3");
        writer.writeComment("For more Information and other License mail me: info@juehv-tech.de");
        writer.writeComment("Format Info:");
        writer.writeComment("VideoFilePath, Titel, \"Tag1,Tag2\", DecriptionFilePath, Privacy, License, Category, MimeType, VideoChecksum");
        writer.writeComment("--------- Data start here ---------");
        // create version line
        writer.writeRecord(new String[]{"version", VERSION, "", "", "", "", "", "", ""});
        // create entrys
        for (int i = 0; i < videos.size(); i++) {
            VideoInfo video = videos.get(i);
            writer.writeRecord(new String[]{videoFileNames.get(i),
                        video.getVideoTitel(), video.getVideoTags(),
                        descriptionFileNames.get(i), video.getPrivacy().toString(),
                        video.getLicense().toString(), video.getCategory(),
                        video.getFileName().substring(video.getFileName().lastIndexOf(".")),
                        "noChecksumSupported"});
        }
        // close writer
        writer.flush();
        writer.close();

    }
}
