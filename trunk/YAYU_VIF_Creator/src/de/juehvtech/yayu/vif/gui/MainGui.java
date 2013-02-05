/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.vif.gui;

import de.juehvtech.yayu.util.container.Category;
import de.juehvtech.yayu.util.container.VideoInfo;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Jens
 */
public class MainGui extends javax.swing.JFrame {

    private MainGuiControl control;

    private void setWaitCursor() {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }

    private void setNormalCursor() {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void clearFields() {
        descriptionArea.setText(""); //NOI18N
        filePathField.setText(""); //NOI18N
        tagField.setText(""); //NOI18N
        titelField.setText(""); //NOI18N
    }

    private void loadCatogories() {
        DefaultComboBoxModel<String> model =
                ((DefaultComboBoxModel<String>) categoryComboBox.getModel());
        for (String value : Category.getAsList()) {
            model.addElement(value);
        }
    }

    private void checkSave() {
        if (filePathField.getText().isEmpty()
                || titelField.getText().isEmpty()) {
            saveButton.setEnabled(false);
        } else {
            saveButton.setEnabled(true);
        }
    }

    private void checkSaveAsPreset() {
        if (titelField.getText().isEmpty()) {
            saveAsPresetButton.setEnabled(false);
        } else {
            saveAsPresetButton.setEnabled(true);
        }
    }

    private void checkProcess() {
        if (control.isVideoEmpty()) {
            processButton.setEnabled(false);
            processMenuItem.setEnabled(false);
        } else {
            processButton.setEnabled(true);
            processMenuItem.setEnabled(true);
        }
    }

    private void checkDeletePreset() {
        if (presetList.getSelectedIndex() >= 0) {
            deletePresetButton.setEnabled(true);
        } else {
            deletePresetButton.setEnabled(false);
        }
    }

    private void checkDelete() {
        if (videoList.getSelectedIndex() >= 0) {
            deleteButton.setEnabled(true);
        } else {
            deleteButton.setEnabled(false);
        }
    }

    private void checkPlayVideo() {
        if (filePathField.getText().isEmpty()) {
            playMovieButton.setEnabled(false);
        } else {
            playMovieButton.setEnabled(true);
        }
    }

    private void checkButtons() {
        checkSave();
        checkSaveAsPreset();
        checkProcess();
        checkDeletePreset();
        checkDelete();
        checkPlayVideo();
    }

    /**
     * Creates new form MainGui
     */
    public MainGui() {
        control = new MainGuiControl(this);
        initComponents();
        loadCatogories();
        checkButtons();
        presetList.setListData(control.getPresets());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                MainGui.this.dispose();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        detailsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filePathField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        titelField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tagField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionArea = new javax.swing.JTextArea();
        categoryComboBox = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        videoList = new javax.swing.JList();
        deleteButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        newButton = new javax.swing.JButton();
        processButton = new javax.swing.JButton();
        playMovieButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        presetList = new javax.swing.JList();
        jLabel6 = new javax.swing.JLabel();
        deletePresetButton = new javax.swing.JButton();
        saveAsPresetButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        processMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        helpMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        aboutMenuItem = new javax.swing.JMenuItem();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/juehvtech/yayu/vif/resources/vifgui_en"); // NOI18N
        jMenu3.setText(bundle.getString("mainmenu")); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(bundle.getString("app_titel")); // NOI18N

        detailsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                detailsPanelMouseEntered(evt);
            }
        });

        jLabel1.setText(bundle.getString("label_filepath")); // NOI18N

        filePathField.setEditable(false);

        jLabel4.setText(bundle.getString("label_titel")); // NOI18N

        titelField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                titelFieldKeyTyped(evt);
            }
        });

        browseButton.setText(bundle.getString("button_browse")); // NOI18N
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Tags (seperate with \",\"):");

        jLabel3.setText(bundle.getString("label_description")); // NOI18N

        descriptionArea.setColumns(20);
        descriptionArea.setRows(5);
        jScrollPane2.setViewportView(descriptionArea);

        jLabel7.setText(bundle.getString("label_category")); // NOI18N

        javax.swing.GroupLayout detailsPanelLayout = new javax.swing.GroupLayout(detailsPanel);
        detailsPanel.setLayout(detailsPanelLayout);
        detailsPanelLayout.setHorizontalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detailsPanelLayout.createSequentialGroup()
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tagField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titelField)
                    .addComponent(categoryComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, detailsPanelLayout.createSequentialGroup()
                        .addComponent(filePathField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseButton))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        detailsPanelLayout.setVerticalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filePathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titelField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tagField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
        );

        jLabel5.setText(bundle.getString("label_video")); // NOI18N

        videoList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        videoList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                videoListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(videoList);

        deleteButton.setText(bundle.getString("button_video_delete")); // NOI18N
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        saveButton.setText(bundle.getString("button_video_save")); // NOI18N
        saveButton.setEnabled(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(deleteButton)
                .addGap(27, 27, 27)
                .addComponent(saveButton))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteButton)
                    .addComponent(saveButton)))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        newButton.setText(bundle.getString("button_video_new")); // NOI18N
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(newButton);

        processButton.setText(bundle.getString("button_process")); // NOI18N
        processButton.setEnabled(false);
        processButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(processButton);

        playMovieButton.setText(bundle.getString("button_video_play")); // NOI18N
        playMovieButton.setEnabled(false);
        playMovieButton.setFocusable(false);
        playMovieButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playMovieButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        playMovieButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playMovieButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(playMovieButton);

        presetList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        presetList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                presetListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(presetList);

        jLabel6.setText(bundle.getString("label_preset")); // NOI18N

        deletePresetButton.setText(bundle.getString("button_preset_delete")); // NOI18N
        deletePresetButton.setEnabled(false);
        deletePresetButton.setFocusable(false);
        deletePresetButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deletePresetButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deletePresetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePresetButtonActionPerformed(evt);
            }
        });

        saveAsPresetButton.setText(bundle.getString("button_preset_save")); // NOI18N
        saveAsPresetButton.setEnabled(false);
        saveAsPresetButton.setFocusable(false);
        saveAsPresetButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveAsPresetButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveAsPresetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsPresetButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(deletePresetButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(saveAsPresetButton))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deletePresetButton))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(saveAsPresetButton))
        );

        jMenu1.setText(bundle.getString("menu_file")); // NOI18N

        exitMenuItem.setText(bundle.getString("menu_file_exit")); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(exitMenuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText(bundle.getString("menu_edit")); // NOI18N

        processMenuItem.setText(bundle.getString("menu_edit_process")); // NOI18N
        processMenuItem.setEnabled(false);
        processMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(processMenuItem);

        jMenuItem1.setText(bundle.getString("menu_edit_options")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        jMenu4.setText(bundle.getString("menu_help")); // NOI18N

        helpMenuItem.setText(bundle.getString("menu_help_help")); // NOI18N
        helpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(helpMenuItem);
        jMenu4.add(jSeparator2);

        aboutMenuItem.setText(bundle.getString("menu_help_about")); // NOI18N
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(aboutMenuItem);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(detailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(detailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        setWaitCursor();
        String path = control.performBrowseAction(filePathField.getText());
        if (path != null) {
            filePathField.setText(path);
        }
        checkButtons();
        setNormalCursor();
    }//GEN-LAST:event_browseButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        List<String> result = control.performSaveAction(
                filePathField.getText(),
                titelField.getText(),
                tagField.getText(),
                descriptionArea.getText(),
                categoryComboBox.getSelectedItem().toString());
        videoList.setListData(result.toArray());
        clearFields();
        checkButtons();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        List<String> result = control.performDeleteAction(
                filePathField.getText(),
                titelField.getText(),
                tagField.getText(),
                descriptionArea.getText());
        videoList.setListData(result.toArray());
        checkButtons();
        clearFields();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        if (!filePathField.getText().isEmpty()
                || !titelField.getText().isEmpty()
                || !descriptionArea.getText().isEmpty()
                || !tagField.getText().isEmpty()) {
            int result = JOptionPane.showConfirmDialog(this,
                    java.util.ResourceBundle.getBundle(
                    "de/juehvtech/yayu/vif/resources/vifgui_en").getString(
                    "dialog_confirm_dropvideo"),
                    java.util.ResourceBundle.getBundle(
                    "de/juehvtech/yayu/vif/resources/vifgui_en").getString(
                    "dialog_confirm_drop"), JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }
        checkButtons();
        clearFields();
    }//GEN-LAST:event_newButtonActionPerformed

    private void processButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processButtonActionPerformed
        setWaitCursor();
        boolean result = control.performProcessAction(
                filePathField.getText(),
                titelField.getText(),
                tagField.getText(),
                descriptionArea.getText());
        if (result) {
            clearFields();
            videoList.setListData(new Object[0]);
            control = new MainGuiControl(this);
        }
        checkButtons();
        setNormalCursor();
    }//GEN-LAST:event_processButtonActionPerformed

    private void videoListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_videoListMouseClicked
        setWaitCursor();
        int index = videoList.getSelectedIndex();
        if (index < 0) {
            setNormalCursor();
            return;
        }
        clearFields();

        VideoInfo videoInfo = control.getVideoInfo(index);
        filePathField.setText(videoInfo.getFileName());
        titelField.setText(videoInfo.getVideoTitel());
        tagField.setText(videoInfo.getVideoTags());
        descriptionArea.setText(videoInfo.getVideoText());
        categoryComboBox.setSelectedItem(videoInfo.getCategory());
        checkButtons();
        setNormalCursor();
    }//GEN-LAST:event_videoListMouseClicked

    private void playMovieButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playMovieButtonActionPerformed
        setWaitCursor();
        control.playVideo(filePathField.getText());
        checkButtons();
        setNormalCursor();
    }//GEN-LAST:event_playMovieButtonActionPerformed

    private void presetListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_presetListMouseClicked
        setWaitCursor();
        int index = presetList.getSelectedIndex();
        if (index < 0) {
            setNormalCursor();
            return;
        }

        VideoInfo videoInfo = control.getPresetInfo(presetList
                .getSelectedValue().toString());
        if (videoInfo != null) {
            filePathField.setText(videoInfo.getFileName());
            titelField.setText(videoInfo.getVideoTitel());
            tagField.setText(videoInfo.getVideoTags());
            descriptionArea.setText(videoInfo.getVideoText());
            categoryComboBox.setSelectedItem(videoInfo.getCategory());
        } else {
            clearFields();
        }
        checkButtons();
        setNormalCursor();
    }//GEN-LAST:event_presetListMouseClicked

    private void saveAsPresetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsPresetButtonActionPerformed
        setWaitCursor();
        String presetName = JOptionPane.showInputDialog(this,
                java.util.ResourceBundle.getBundle(
                "de/juehvtech/yayu/vif/resources/vifgui_en").getString(
                "dialog_input_insetpresetname"));
        if (presetName == null || presetName.isEmpty()) {
            checkButtons();
            setNormalCursor();
            return;
        }
        String[] result = control.performSavePresetAction(
                presetName,
                titelField.getText(),
                tagField.getText(),
                descriptionArea.getText(),
                categoryComboBox.getSelectedItem().toString());
        presetList.setListData(result);
        checkButtons();
        setNormalCursor();
    }//GEN-LAST:event_saveAsPresetButtonActionPerformed

    private void deletePresetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePresetButtonActionPerformed

        checkButtons();
    }//GEN-LAST:event_deletePresetButtonActionPerformed

    private void titelFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_titelFieldKeyTyped

        checkButtons();
    }//GEN-LAST:event_titelFieldKeyTyped

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void processMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processMenuItemActionPerformed
        processButtonActionPerformed(evt);
    }//GEN-LAST:event_processMenuItemActionPerformed

    private void helpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, "Not supported in this Version!");
    }//GEN-LAST:event_helpMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, "Not supported in this Version!");
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void detailsPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detailsPanelMouseEntered
        videoList.clearSelection();
        presetList.clearSelection();
        checkButtons();
    }//GEN-LAST:event_detailsPanelMouseEntered

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JOptionPane.showMessageDialog(this, "Not supported in this Version!");
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton browseButton;
    private javax.swing.JComboBox categoryComboBox;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton deletePresetButton;
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JTextField filePathField;
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton newButton;
    private javax.swing.JButton playMovieButton;
    private javax.swing.JList presetList;
    private javax.swing.JButton processButton;
    private javax.swing.JMenuItem processMenuItem;
    private javax.swing.JButton saveAsPresetButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField tagField;
    private javax.swing.JTextField titelField;
    private javax.swing.JList videoList;
    // End of variables declaration//GEN-END:variables
}
