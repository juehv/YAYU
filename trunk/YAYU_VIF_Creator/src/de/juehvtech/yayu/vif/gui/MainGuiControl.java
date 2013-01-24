/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.vif.gui;

import de.juehvtech.yayu.util.container.MIMEType;
import de.juehvtech.yayu.util.container.UserPackage;
import de.juehvtech.yayu.util.container.VideoInfo;
import de.juehvtech.yayu.vif.export.ExportListener;
import de.juehvtech.yayu.vif.export.ExportManager;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

/**
 *
 * @author Jens
 */
public class MainGuiControl {

    private static final String bundleString =
            "de/juehvtech/yayu/vif/resources/vifgui_en";
    private static final ResourceBundle bundle = ResourceBundle
            .getBundle(bundleString);
    private final JFrame parent;
    //TODO convert to map and remove videolist
    private final List<String> videoList = new ArrayList<>();
    private final List<VideoInfo> videoDataList = new ArrayList<>();
    private final Map<String, VideoInfo> presetDataList;

    private void savePresets() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(presetDataList);
            oos.flush();
            bos.flush();
            Preferences.userNodeForPackage(MainGuiControl.class)
                    .putByteArray("presets", bos.toByteArray());
        } catch (IOException ex) {
            Logger.getLogger(MainGuiControl.class.getName()).log(Level.SEVERE,
                    "Error while writing presets", ex);
            JOptionPane.showMessageDialog(parent,
                    bundle.getString("dialog_error_presets_write") + ex.getLocalizedMessage(),
                    bundle.getString("dialog_error_unknownerror"), JOptionPane.ERROR_MESSAGE);
        }
    }

    // removes entrys with the same video file
    private void clearDoubleVideoFiles(String filePath) {
        for (VideoInfo video : videoDataList) {
            if (video.getFileName() == null ? filePath == null
                    : video.getFileName().equals(filePath)) {
                videoDataList.remove(video);
                return;
            }
        }
    }

    private void updateVideoList() {
        videoList.clear();
        for (VideoInfo video : videoDataList) {
            videoList.add(video.getVideoTitel());
        }
    }

    private Map<String, VideoInfo> loadPresets() {
        byte[] bytes = Preferences.userNodeForPackage(MainGuiControl.class)
                .getByteArray("presets", new byte[0]);
        if (bytes.length == 0) {
            return new HashMap<>();
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (HashMap<String, VideoInfo>) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MainGuiControl.class.getName()).log(Level.SEVERE,
                    "Error while reading presets", ex);
            JOptionPane.showMessageDialog(parent,
                    bundle.getString("dialog_error_presets_read") + ex.getLocalizedMessage(),
                    bundle.getString("dialog_error_unknownerror"), JOptionPane.ERROR_MESSAGE);
        }
        return new HashMap<>();
    }

    public MainGuiControl(JFrame parent) {
        this.parent = parent;
        presetDataList = loadPresets();
    }

    public String[] getPresets() {
        return presetDataList.keySet().toArray(new String[0]);
    }

    public String performBrowseAction(String startPath) {
        JFileChooser fileChooser = new JFileChooser(); // this line may block a while
        if (!startPath.isEmpty()) {
            fileChooser.setCurrentDirectory(new File(startPath)
                    .getAbsoluteFile());
        }
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                String mime = f.getName().toLowerCase();
                for (MIMEType type : MIMEType.values()) {
                    if (mime.endsWith("." + type.getFileExtention())) {
                        return true;
                    }
                }
                return f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Supported Video Files";
            }
        });
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.CANCEL_OPTION) {
            return null;
        }
        File file = fileChooser.getSelectedFile();
        return file.getAbsolutePath();
    }

    //TODO replace many argument with container object (guiPack)
    public List<String> performSaveAction(String path, String titel,
            String tags, String description, String category) {
        if (titel.isEmpty() || path.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                    bundle.getString("dialog_error_insertfilepath"),
                    bundle.getString("dialog_error_invaliddata"),
                    JOptionPane.WARNING_MESSAGE);
            return videoList;
        }
        VideoInfo video = new VideoInfo(path, titel, tags, description,
                MIMEType.fromFileExtention(path.substring(path.lastIndexOf('.'))),
                category);
        //make shure that there are no double saves
        //videoDataList.remove(video);
        // make shure that there are no entrys with the same file
        clearDoubleVideoFiles(path);
        videoDataList.add(video);

        updateVideoList();
        return videoList;
    }

    public String[] performSavePresetAction(String presetName, String titel,
            String tags, String description, String category) {
        if (titel.isEmpty() || presetName.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                    bundle.getString("dialog_error_invalidpreset"),
                    bundle.getString("dialog_error_invaliddata"),
                    JOptionPane.WARNING_MESSAGE);
            return presetDataList.keySet().toArray(new String[0]);
        }
        if ((presetDataList.get(presetName)) != null) {
            int result = JOptionPane.showConfirmDialog(parent,
                    bundle.getString("dialog_confirm_replacepreset"),
                    bundle.getString("dialog_confirm_replace"),
                    JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return presetDataList.keySet().toArray(new String[0]);
            }
        }
        VideoInfo video = new VideoInfo("", titel, tags, description,
                MIMEType.UNSET, category);

        presetDataList.put(presetName, video);
        savePresets();
        return presetDataList.keySet().toArray(new String[0]);
    }

    public List<String> performDeleteAction(String path, String titel,
            String tags, String description) {
        int result = JOptionPane.showConfirmDialog(parent,
                bundle.getString("dialog_confirm_deletevideo"),
                bundle.getString("dialog_confirm_delete"),
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {

            VideoInfo video = new VideoInfo(path, titel, tags, description,
                    null, null);

            videoDataList.remove(video);
            updateVideoList();
        }
        return videoList;
    }

    public boolean performProcessAction(String path, String titel,
            String tags, String description) {
        // check data
        if (!path.isEmpty() || !titel.isEmpty()
                || !tags.isEmpty() || !description.isEmpty()) {
            int result = JOptionPane
                    .showConfirmDialog(parent,
                    bundle.getString("dialog_confirm_videofilelost"),
                    bundle.getString("dialog_confirm_proceed"),
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.NO_OPTION) {
                return false;
            }
        }

        // get user data
        UserDialog dialog = new UserDialog(parent);
        UserPackage user = dialog.showDialog();
        if (user == null) {
            return false;
        }

        //get save dir
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.CANCEL_OPTION) {
            return false;
        }
        File saveDir = fileChooser.getSelectedFile();

        // add progress bar
        ProgressMonitor progress = new ProgressMonitor(parent,
                bundle.getString("dialog_progress_working"), "", 0, 100); //NOI18N
        progress.setMillisToDecideToPopup(0);
        progress.setMillisToPopup(0);
        // add event listener
        ExportListener el = new ExportListener(parent);
        ExportManager manager = new ExportManager(progress, el);
        // check size
        if (!manager.checkSize(videoDataList, saveDir.getAbsolutePath())) {
            JOptionPane.showMessageDialog(parent, "Not enough disk space!", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // do the job
        manager.export(videoDataList, saveDir.getAbsolutePath(), user);
        return true;
    }

    public VideoInfo getVideoInfo(int index) {
        return videoDataList.get(index);
    }

    public VideoInfo getPresetInfo(String presetName) {
        return presetDataList.get(presetName);
    }

    public void playVideo(String path) {
        try {
            if (path == null || path.isEmpty()) {
                JOptionPane.showMessageDialog(parent,
                        bundle.getString("dialog_error_invalidfilepath"),
                        bundle.getString("dialog_error_invalidfilepath"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            File video = new File(path);
            if (!video.exists()) {
                JOptionPane.showMessageDialog(parent,
                        bundle.getString("dialog_error_nofileexist"),
                        bundle.getString("dialog_error_nofile"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Desktop.getDesktop().open(video);
        } catch (IOException ex) {
            Logger.getLogger(MainGuiControl.class.getName()).log(Level.SEVERE,
                    null, ex);
            JOptionPane.showMessageDialog(parent,
                    bundle.getString("dialog_error_openfile") + ex.getMessage(),
                    bundle.getString("dialog_error_openfile"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isVideoEmpty() {
        updateVideoList();
        return videoList.isEmpty();
    }
}
