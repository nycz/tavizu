package main.java.tavizu;

import javafx.fxml.FXML;

import java.io.File;

public class MainController {
    // Controllers
    @FXML private ThumbnailPaneController thumbnailPaneController;
    @FXML private TerminalController terminalController;
    @FXML private InfoSidebarController infoSidebarController;

    // Image model
    private ImageList imageList = new ImageList();

    @FXML
    public void initialize() {
        // initial load, very temporary
        File directory = new File("pics").getAbsoluteFile();
        imageList.loadImages(directory);
        thumbnailPaneController.setImages(imageList.getImages());
    }
}
