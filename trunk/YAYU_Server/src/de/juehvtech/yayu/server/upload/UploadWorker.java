/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.server.upload;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.media.mediarss.MediaCategory;
import com.google.gdata.data.media.mediarss.MediaDescription;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaTitle;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeNamespace;
import com.google.gdata.util.ServiceException;
import de.juehvtech.yayu.server.account.YoutubeAccountManager;
import de.juehvtech.yayu.util.container.Privacy;
import de.juehvtech.yayu.util.container.VideoInfo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JUehV
 */
public class UploadWorker extends Thread {

    private static final String UPLOAD_URL = "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";
    private VideoInfo video;

    public void setVideo(VideoInfo video) {
        this.video = video;
    }

    private void processKeywords(VideoInfo video, YouTubeMediaGroup mg) {
        mg.setKeywords(new MediaKeywords());
        String[] tags = video.getVideoTags().split(",");
        for (int i = 0; i < tags.length; i++) {
            mg.getKeywords().addKeyword(tags[i]);
        }
    }

    @Override
    public void run() {
        try {
            YouTubeService service = YoutubeAccountManager.getInstance().getService();
            if (service == null) {
                Logger.getLogger(UploadWorker.class.getName())
                        .severe("Got no yt service. Exit.");
                return;
            }
            VideoEntry newEntry = new VideoEntry();

            YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
            // Title
            mg.setTitle(new MediaTitle());
            mg.getTitle().setPlainTextContent(video.getVideoTitel());
            // Category
            mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, video.getCategory()));

            // Keywords
            processKeywords(video, mg);
            // Description
            mg.setDescription(new MediaDescription());
            mg.getDescription().setPlainTextContent(video.getVideoText());
            // Privacy
            if (video.getPrivacy() == Privacy.PRIVATE) {
                mg.setPrivate(true);
            } else {
                mg.setPrivate(false);
            }

            // MediaFileSource ms = new MediaFileSource(new File("B:\\test.mp4"), "video/mp4");
            File videoFile = new File(video.getFileName());
            MediaFileSource ms = new MediaFileSource(videoFile, video.getType().toString());
            newEntry.setMediaSource(ms);

            Logger.getLogger(UploadWorker.class.getName()).log(Level.INFO, "Start uploading video with title: \"{0}\"", video.getVideoTitel());
            // VideoEntry createdEntry = service.insert(new URL(uploadUrl), newEntry);
            service.insert(new URL(UPLOAD_URL), newEntry);
            // TODO delete everything with commandline switch
//            if (!videoFile.delete()) {
//                Logger.getLogger(UploadWorker.class.getName()).log(Level.WARNING, "Can't delete video file:\"{0}\"", video.getFileName());
//            }
            Logger.getLogger(UploadWorker.class.getName()).log(Level.INFO, "Upload of \"{0}\" done.", video.getVideoTitel());
        } catch (IOException ex) {
            Logger.getLogger(UploadWorker.class.getName()).log(Level.SEVERE, "Error while reading or writing the video file", ex);
        } catch (ServiceException ex) {
            Logger.getLogger(UploadWorker.class.getName()).log(Level.SEVERE, "Error while loading up the video", ex);
        }
    }
}
