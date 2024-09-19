package com.andymememe.jziptoolx;

import java.io.File;

import com.andymememe.jziptoolx.treer.TreeManipulator;
import com.andymememe.jziptoolx.zipper.ComManipulator;
import com.andymememe.jziptoolx.zipper.TarManipulator;
import com.andymememe.jziptoolx.zipper.ZipManipulator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {
    private Stage _stage;
    private ComManipulator _manipulator;
    
    public void setStage(Stage stage) {
        _stage = stage;
    }

    @FXML
    private MenuItem abountMenuItem;

    @FXML
    private TreeView<String> fileTreeView;

    @FXML
    private Label filenameLabel;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private MenuItem quitMenuItem;

    @FXML
    private Label statusLabel;

    @FXML
    private Button unzipBtn;

    @FXML
    void onAboutClick(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("JZipToolX");
        alert.setContentText("Creator: Andymememe");

        alert.show();
    }

    @FXML
    void onOpenClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Zip/Tar file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Zip/Tar File", "*.zip", "*.tar")
        );

        File file = fileChooser.showOpenDialog(_stage);
        if (file == null) {
            return;
        }
        if (file.getName().endsWith(".tar")) {
            _manipulator = new TarManipulator(new TreeManipulator());
        } else if (file.getName().endsWith(".zip")) {
            _manipulator = new ZipManipulator(new TreeManipulator());
        } else {
            statusLabel.setText("Unknown file: " + file.getName());
            return;
        }
        filenameLabel.setText(file.getName());
        if (_manipulator.openCom(file)) {
            fileTreeView.setRoot(_manipulator.getRoot());
            unzipBtn.setDisable(false);
            statusLabel.setText("Loaded");
        } else {
            statusLabel.setText("Error when Open");
        }
    }

    @FXML
    void onQuitClick(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void onUnzipClick(ActionEvent event) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Extract to");

        File dir = dirChooser.showDialog(_stage);
        if (dir == null) {
            return;
        }

        if(_manipulator.extractCom(dir.getAbsolutePath())) {
            statusLabel.setText("Extracted");
        } else {
            statusLabel.setText("Error when Extracting");
        }
    }

}
