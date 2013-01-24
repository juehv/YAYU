/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.server.parsing;

import com.csvreader.CsvReader;
import de.juehvtech.yayu.util.container.MIMEType;
import de.juehvtech.yayu.util.container.VideoInfo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class VideoInfoFileInterpreter {

    private static final Charset ENCODING = StandardCharsets.UTF_8;
    private static final Logger LOG = Logger.getLogger(VideoInfoFileInterpreter.class.getName());
    private final String filePath;
    private final String videoInfoFileName;
    private List<VideoInfo> videoInfos = new ArrayList<>();

    public VideoInfoFileInterpreter(String filePath, String fileName) {
        String tmp = filePath.replace("\\", "/");
        if (tmp.endsWith("/")) {
            tmp = tmp.substring(0, tmp.length() - 1);
        }
        this.filePath = tmp;
        this.videoInfoFileName = fileName;
    }

    private List<String[]> parseFile(String filePathString) {
        try {
            List<String[]> values = new ArrayList<>();
            CsvReader reader = new CsvReader(filePathString, ',', ENCODING);
            //reader.setDelimiter(',');
            reader.setTextQualifier('"');
            reader.setUseComments(true); //ignores comments
            //reader.setSkipEmptyRecords(true);

            String[] version;
            if (reader.readHeaders()) {
                version = reader.getHeaders();
                if (version[0].equalsIgnoreCase("version")) {
                    LOG.log(Level.WARNING, "Found document version: {0}", version[1]);
                    //TODO version checker
                }
            } else {
                LOG.warning("Could not find a version info!");
            }
            while (reader.readRecord()) {
                values.add(reader.getValues());
            }

            for (String[] value : values) {
                LOG.log(Level.INFO, "{0}, {1}, {2}, {3}",
                        new String[]{value[0], value[1], value[2], value[3]});
            }

            return values;
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, "Video Info File not found!", ex);
            return null;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "IO Exception. Check permissions?", ex);
            return null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            LOG.log(Level.SEVERE,
                    "Invalid vif file! Please use the vif tool", ex);
            return null;
        }
    }

    private boolean interpretValues(List<String[]> values) {
        try {
            for (String[] item : values) {
                if (item[0].isEmpty()) {
                    LOG.severe("Empty video file path!");
                    return false;
                }
                VideoInfo container = new VideoInfo(filePath + "/" + item[0],
                        item[1], item[2], "",
                        MIMEType.fromFileExtention(item[7]),
                        item[6]);
                if (item[3].isEmpty()) {
                    container.setTextResolved(true);
                } else {
                    container.setVideoText(filePath + "/" + item[3]);
                }

                videoInfos.add(container);
                LOG.info(container.toString());
            }
            return true;
        } catch (ArrayIndexOutOfBoundsException ex) {
            LOG.log(Level.SEVERE,
                    "Invalid vif file! Please use the vif tool", ex);
            return false;
        }
    }

    private boolean resolveVideoTextFiles() {
        try {
            for (VideoInfo item : videoInfos) {
                // check if it is already resolved
                if (item.isTextResolved()) {
                    continue;
                }
                // check text file is present
                File f = new File(item.getVideoText());
                if (!f.exists()) {
                    LOG.log(Level.SEVERE, "Cannod find video text file:\"{0}\"",
                            item.getVideoText());
                    return false;
                }
                // read file
                List<String> lines = Files.readAllLines(Paths.get(item.getVideoText()), ENCODING);
                StringBuilder videoText = new StringBuilder();
                for (String line : lines) {
                    videoText.append(line).append("\n");
                }

                // save text to container
                item.setVideoText(videoText.toString());
                item.setTextResolved(true);

                LOG.info(item.toString());
            }
            return true;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error while reading the video text file!", ex);
            return false;
        }
    }

    private boolean checkVideoFilesArePresent() {
        for (VideoInfo item : videoInfos) {
            File f = new File(item.getFileName());
            if (!f.exists()) {
                LOG.log(Level.SEVERE, "Cannod find video file:\"{0}\"",
                        item.getFileName());
                return false;
            }
            item.setVideoFileChecked(true);

            LOG.log(Level.INFO, "Found video file: {0}", item.getFileName());
        }

        return true;
    }

    public boolean execute() {
        List<String[]> values;

        // parse file
        values = parseFile(filePath + "/" + videoInfoFileName);

        if (values == null) {
            return false;
        }
        // interpret file
        if (!interpretValues(values)) {
            return false;
        }
        // resolve video text files
        if (!resolveVideoTextFiles()) {
            return false;
        }
        // check the video files
        if (!checkVideoFilesArePresent()) {
            return false;
        }

        return true;
    }

    /**
     * Returns the video info if execute was called.
     *
     * @return the video info if execute was calles. null else.
     */
    public List<VideoInfo> getVideoInfos() {
        return videoInfos;
    }
}
