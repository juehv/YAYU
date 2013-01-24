/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.server.app;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import de.juehvtech.yayu.server.account.YoutubeAccountManager;
import de.juehvtech.yayu.util.container.VideoInfo;
import de.juehvtech.yayu.server.parsing.VideoInfoFileInterpreter;
import de.juehvtech.yayu.server.properties.ParserTags;
import de.juehvtech.yayu.server.properties.Properties;
import de.juehvtech.yayu.server.upload.VideoUploadManager;
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
public class YAYU_Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0 || (args.length == 1 && args[0].isEmpty())) {
            try {
                //  load from file
                Path path = Paths.get("./args");
                List<String> argsList = Files.readAllLines(path, StandardCharsets.UTF_8);
                args = new String[argsList.size()];
                for (int i = 0; i < argsList.size(); i++) {
                    args[i] = argsList.get(i);
                }

            } catch (IOException ex) {
                Logger.getLogger(YAYU_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            JSAP jsap = new JSAP();
            // Example:
            //        FlaggedOption opt1 = new FlaggedOption("count")
            //                                .setStringParser(JSAP.INTEGER_PARSER)
            //                                .setDefault("1")
            //                                .setRequired(true)
            //                                .setShortFlag('n')
            //                                .setLongFlag(JSAP.NO_LONGFLAG);
            //        jsap.registerParameter(opt1);
            //        
            //        Switch sw1 = new Switch("verbose")
            //                .setShortFlag('v')
            //        jsap.registerParameter(sw1);
            //        jsap.registerParameter(sw1);

            // help
            Switch help = new Switch(ParserTags.HELP);
            help.setShortFlag('h')
                    .setLongFlag("help")
                    .setHelp("Shows this page.");
            jsap.registerParameter(help);

            // version info
            Switch version = new Switch(ParserTags.VERSION);
            version.setShortFlag(JSAP.NO_SHORTFLAG)
                    .setLongFlag("version")
                    .setHelp("Shows the version information.");
            jsap.registerParameter(version);

            // username
            FlaggedOption username = new FlaggedOption(ParserTags.USERNAME);
            username.setStringParser(JSAP.STRING_PARSER)
                    .setShortFlag('u')
                    .setLongFlag("username")
                    .setRequired(true)
                    .setList(false)
                    .setHelp("Your Youtube username.");
            jsap.registerParameter(username);

            // password
            FlaggedOption password = new FlaggedOption(ParserTags.PASSWORD);
            password.setStringParser(JSAP.STRING_PARSER)
                    .setShortFlag('p')
                    .setLongFlag("password")
                    .setRequired(true)
                    .setList(false)
                    .setHelp("Your Youtube password.");
            jsap.registerParameter(password);

            // video path
            FlaggedOption videoDir = new FlaggedOption(ParserTags.VIDEO_DIR);
            videoDir.setStringParser(JSAP.STRING_PARSER)
                    .setShortFlag('d')
                    .setLongFlag("video-dir")
                    .setRequired(true)
                    .setList(false)
                    .setHelp("the directory that contains the video files.");
            jsap.registerParameter(videoDir);

            // video information file (opt, def yayu.vif)
            FlaggedOption videoInfoFile = new FlaggedOption(ParserTags.VIDEO_INFO_FILE);
            videoInfoFile.setStringParser(JSAP.STRING_PARSER)
                    .setShortFlag('F')
                    .setLongFlag("video-info-file")
                    .setRequired(false)
                    .setDefault("videoInfos.vif")
                    .setList(false)
                    .setHelp("The filename of the video info file. Should be "
                    + "locatet in the video directory. The default ist \"videoInfos.vif\".");
            jsap.registerParameter(videoInfoFile);

            // get results
            JSAPResult config = jsap.parse(args);

            if (config.success()) {
                Properties.VIDEO_DIR = config.getString(ParserTags.VIDEO_DIR);
                Properties.VIDEO_INFO_FILE = config.getString(ParserTags.VIDEO_INFO_FILE);
                // set username and password
                UserPackage user = new UserPackage(config.getString(ParserTags.USERNAME),
                        config.getString(ParserTags.PASSWORD));
                YoutubeAccountManager.setUser(user);

                Logger.getLogger(YAYU_Server.class.getName())
                        .log(Level.INFO,
                        "Got the following values:\n{0}\n{1}\n{2}\n{3}",
                        new Object[]{Properties.VIDEO_DIR, Properties.VIDEO_INFO_FILE,
                            config.getString(ParserTags.USERNAME),
                            user.getPasswordDummy()});

            } else {
                if (config.getBoolean(ParserTags.HELP)) {
                    System.out.println();
                    System.out.println(jsap.getHelp());
                    System.out.println();
                } else if (config.getBoolean(ParserTags.VERSION)) {
                    System.out.println();
                    System.out.println("Yet Another Youtube Uploader (YAYU) Server\n"
                            + "Version: 0.1 Alpha (01/2013)\n"
                            + "License: GPLv3\n"
                            + "http://yayu.juehv-tech.de");
                    System.out.println();
                } else {
                    String argstring = "";
                    for (String arg : args) {
                        argstring += "\"" + arg + "\", ";
                    }
                    Logger.getLogger(YAYU_Server.class.getName())
                            .log(Level.WARNING, "No correct commandline parameter:\n {0}", argstring);
                    System.err.println();
                    // TODO correct name
                    System.err.println("Usage: yayu-server " + jsap.getUsage());
                    System.err.println();
                }

                System.exit(0);
            }

            // Parse video info file
            VideoInfoFileInterpreter parser = new VideoInfoFileInterpreter(
                    Properties.VIDEO_DIR, Properties.VIDEO_INFO_FILE);
            if (!parser.execute()) {
                System.err.println("Error while parsing files. See log for details!");
                System.exit(-1);
            }
            // add videos to cache
            List<VideoInfo> videos = parser.getVideoInfos();
            VideoUploadManager uploadManager = VideoUploadManager.getInstance();
            for (VideoInfo video : videos) {
                uploadManager.addVideoToQueue(video);
            }
            // start uploader
            uploadManager.start();
            try {
                uploadManager.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(YAYU_Server.class.getName()).log(
                        Level.SEVERE, "Failed to join upload manager", ex);
                System.exit(-1);
            }
            System.out.println("Everything is done. Exit.");
            System.exit(0);
        } catch (JSAPException ex) {
            Logger.getLogger(YAYU_Server.class.getName()).log(Level.SEVERE,
                    "Exception with commandline parser", ex);
            System.exit(-1);
        }
    }
}
