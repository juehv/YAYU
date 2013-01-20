/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.server.upload;

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

    private synchronized VideoInfo getNextVideo() {
        return videos.poll();
    }

    private void startUploadVideo(VideoInfo video) {
        worker.setVideo(video);
        worker.run(); // must be run because start can only run once
    }

    @Override
    public void run() {
        while (!videos.isEmpty()) {
            try {
                if (!videos.isEmpty()) {
                    startUploadVideo(this.getNextVideo());
                }
                worker.join();
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
