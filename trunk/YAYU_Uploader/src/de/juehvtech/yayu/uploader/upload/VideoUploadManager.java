/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.uploader.upload;

import de.juehvtech.yayu.uploader.reporting.ProgressListener;
import de.juehvtech.yayu.util.container.VideoInfo;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JUehV
 */
public class VideoUploadManager extends Thread {

    private final static Logger LOG = Logger.getLogger(VideoUploadManager.class.getName());
    private static VideoUploadManager INSTANCE = null;
    private Deque<VideoInfo> videos = new ArrayDeque<>();
    private UploadWorker worker = new UploadWorker();
    private static ProgressListener progress = null;

    private VideoUploadManager() {
        //empty
    }

    public static VideoUploadManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VideoUploadManager();
        }
        return INSTANCE;
    }

    public synchronized void addVideoToQueue(VideoInfo video) {
        videos.add(video);
    }

    public static void setProgress(ProgressListener progress) {
        VideoUploadManager.progress = progress;
    }

    private synchronized VideoInfo getNextVideo() {
        return videos.poll();
    }

    private void startUploadVideo(VideoInfo video) {
        if (progress != null) {
            progress.reportStatus("Start uploading \"" + video.getVideoTitel()
                    + "\"");
        }
        worker.setVideo(video);
        worker.run(); // must be run because "start" can only run once
        // can be fixed with a thread pool architecture
    }

    @Override
    public void run() {
        while (!videos.isEmpty()) {
            try {
                if (!videos.isEmpty()) {
                    startUploadVideo(this.getNextVideo());
                }
                worker.join();
                if (progress != null) {
                    progress.reportStatus("Upload done.");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(VideoUploadManager.class.getName()).log(Level.SEVERE, "Error while joining the UploadWorker!", ex);
            }
//            } else {
//                try {
//                    VideoCacheManager.getInstance().resetFileCounter();
//                    Logger.getLogger(VideoUploadManager.class.getName()).log(Level.FINE,
//                            "Reset the Cache-Video-Counter");
//                    Thread.sleep(5000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(VideoUploadManager.class.getName()).log(Level.SEVERE, "Error while go to sleep!", ex);
//                }
        }
        LOG.info("No (more) video files. Terminate video upload manager.");
    }
//    public void writeQueueToFile(String filePath) throws IOException {
//        File file = new File(filePath);
//        BufferedWriter out = new BufferedWriter(new FileWriter(file));
//
//        XStream xstream = new XStream(new StaxDriver());
//        String xmlString = xstream.toXML(videos);
//
//        out.write(xmlString);
//        out.close();
//    }
//
//    // ACHTUNG überschreibt QUEUE --> warnungen anzeigen
//    public void loadQueueFromFile(String filePath) {
//        File file = new File(filePath);
//
//        XStream xstream = new XStream(new StaxDriver());
//        videos = (Deque<Video>) xstream.fromXML(file);
//        if (!file.delete()) {
//            Logger.getLogger(VideoUploadManager.class.getName()).log(Level.WARNING,
//                    "Error while deleting the Queue cache File! This may cause errors "
//                    + "while the next startup, please remove the file by hand.");
//        }
//    }
}
