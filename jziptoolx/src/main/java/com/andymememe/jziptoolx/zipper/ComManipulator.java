/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andymememe.jziptoolx.zipper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.andymememe.jziptoolx.treer.TreeManipulator;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;

/**
 *
 * @author andymememe
 */
public abstract class ComManipulator {

    protected TreeManipulator _treeManipulator;

    public ComManipulator(TreeManipulator treeMan) {
        _treeManipulator = treeMan;
    }

    public TreeItem<String> getRoot() {
        return _treeManipulator.getRoot();
    }

    public abstract boolean openCom(File file);

    public abstract boolean extractCom(String dir);

    /* Make new folder */
    protected boolean _doMkDir(String rootName, String docName) {
        File newDir = new File(rootName + File.separator + docName);
        /* If dirctory is exist or not */
        if (!newDir.exists()) {
            return newDir.mkdirs();
        }
        return true;
    }

    protected int _alertWhenExit(String fullPath) {
        AtomicInteger result = new AtomicInteger();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Found duplicate file");
        alert.setHeaderText("Found duplicate file: " + fullPath);
        alert.setContentText("Replace?");

        alert.showAndWait().ifPresent((btnType) -> {
            if (btnType == ButtonType.OK) {
                result.set(0);
            } else {
                result.set(1);
            }
        });

        return result.get();
    }

    /* When find exist file, what should this application do */
    protected File _doReplaceJob(File newFile, int replaceResult) {
        File result = newFile;
        switch (replaceResult) {
            case 0:
                result.delete(); // Delete old file
                 {
                    try {
                        result.createNewFile(); // Create new file
                    } catch (IOException ex) {
                        result = null;
                        Logger.getLogger(ZipManipulator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 1:
                break;
        }
        return result;
    }
}
