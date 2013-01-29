/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.uploader.parsing;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import de.juehvtech.yayu.uploader.account.YoutubeAccountManager;
import de.juehvtech.yayu.uploader.app.YAYU_Uploader;
import de.juehvtech.yayu.uploader.properties.ParserTags;
import de.juehvtech.yayu.uploader.properties.Properties;
import de.juehvtech.yayu.uploader.reporting.LocalPiMSConnector;
import de.juehvtech.yayu.uploader.reporting.MetaDataListener;
import de.juehvtech.yayu.uploader.reporting.ProgressListener;
import de.juehvtech.yayu.uploader.upload.VideoUploadManager;
import de.juehvtech.yayu.util.container.UserPackage;
import de.juehvtech.yayu.util.container.VideoInfo;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class ArgumentParser {

    public static final int RESULT_OK = 0;
    public static final int RESULT_HELP = 1;
    public static final int RESULT_FILE_NOT_FOUND = 2;
    public static final int RESULT_WRONG_CMD = 3;
    public static final int RESULT_PARSER_EXCEPTION = 4;
    public static final int RESULT_OTHER_ERROR = 5;

    public static int parse(String[] args) {
        if (args.length == 0) {
            // try to load ./args
            Path path = Paths.get("./args");
            if (path.toFile().exists()) {
                try {
                    List<String> argsList = Files.readAllLines(path,
                            StandardCharsets.UTF_8);
                    args = argsList.toArray(new String[0]);
                } catch (IOException ex) {
                    Logger.getLogger(ArgumentParser.class.getName())
                            .log(Level.SEVERE, null, ex);
                    return RESULT_FILE_NOT_FOUND;
                }
            }
        } else {
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

                if (config.success()) { // manual args
                    Properties.VIDEO_DIR = config.getString(ParserTags.VIDEO_DIR);
                    Properties.VIDEO_INFO_FILE = config.getString(ParserTags.VIDEO_INFO_FILE);
                    // set username and password
                    UserPackage user = new UserPackage(config.getString(ParserTags.USERNAME),
                            config.getString(ParserTags.PASSWORD));
                    YoutubeAccountManager.setUser(user);

                    Logger.getLogger(YAYU_Uploader.class.getName())
                            .log(Level.INFO,
                            "Got the following values:\n{0}\n{1}\n{2}\n{3}",
                            new Object[]{Properties.VIDEO_DIR, Properties.VIDEO_INFO_FILE,
                                config.getString(ParserTags.USERNAME),
                                user.getPasswordDummy()});
                    return RESULT_OK;

                } else {
                    if (config.getBoolean(ParserTags.HELP)) {
                        System.out.println();
                        System.out.println(jsap.getHelp());
                        System.out.println();
                        return RESULT_HELP;
                    } else if (config.getBoolean(ParserTags.VERSION)) {
                        System.out.println();
                        System.out.println("Yet Another Youtube Uploader (YAYU) Server\n"
                                + "Version: 0.1 Alpha (01/2013)\n"
                                + "License: GPLv3\n"
                                + "http://yayu.juehv-tech.de");
                        System.out.println();
                        return RESULT_HELP;
                    } else {
                        String argstring = "";
                        for (String arg : args) {
                            argstring += "\"" + arg + "\", ";
                        }
                        Logger.getLogger(YAYU_Uploader.class.getName())
                                .log(Level.WARNING, "No correct commandline parameter:\n {0}", argstring);
                        System.err.println();
                        // TODO correct name
                        System.err.println("Usage: yayu-server " + jsap.getUsage());
                        System.err.println();
                        return RESULT_WRONG_CMD;
                    }
                }
            } catch (JSAPException ex) {
                Logger.getLogger(YAYU_Uploader.class.getName()).log(Level.SEVERE,
                        "Exception with commandline parser", ex);
                return RESULT_PARSER_EXCEPTION;
            }
        }
        return RESULT_OTHER_ERROR;
    }
}
