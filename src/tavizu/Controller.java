package tavizu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class Controller {
    @FXML private TextField addressBar;
    @FXML private BorderPane rootContainer;
    private File lastPath;
    private ImageFlowView imageFlowView;
    private ObservableList<ImageItem> imageList;

    @FXML private TextField terminalInput;
    @FXML private TextField terminalOutput;

    @FXML private ToggleGroup sortDirection;
    @FXML private ToggleGroup sortKey;

    // Info box
    @FXML private VBox infoBox;
    @FXML private Label infoName;
    @FXML private Label infoDimensions;
    @FXML private Label infoSize;
    @FXML private Label infoModified;

    private double thumbnailWidth = 200;
    private double thumbnailHeight = 200;

    @FXML
    public void initialize() {
        imageList = FXCollections.observableArrayList();
        imageFlowView = new ImageFlowView(imageList, sortDirection.selectedToggleProperty(),
                                          sortKey.selectedToggleProperty());
        // TODO: no hardcoded default path
        lastPath = new File("pics").getAbsoluteFile();
        addressBar.setText(lastPath.toString());
        rootContainer.setCenter(imageFlowView);
        loadImages(lastPath);
    }

    private void updateInfoBox(ImageItem img) {
        infoBox.setVisible(true);
        infoName.setText(img.getFile().getName());
        int width = (int) img.getImage().getWidth();
        int height = (int) img.getImage().getHeight();
        infoDimensions.setText(width + " x " + height);
//        infoSize.setText(img.getFile());
        infoModified.setText("" + img.getFile().lastModified());
    }

    private void loadImages(File directory) {
        imageList.clear();
        File[] images = directory.listFiles((File dir, String name) ->
                            name.endsWith(".png") || name.endsWith(".jpg"));
        for (File imagePath : images) {
            ImageItem imageItem = new ImageItem(imagePath);
            imageItem.setOnMousePressed(event -> {updateInfoBox(imageItem);});
            imageList.add(imageItem);
        }
    }

    @FXML
    protected void setPath() {
        File path;
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(lastPath);
        try {
            path = directoryChooser.showDialog(addressBar.getScene()
                                               .getWindow());
            if (path != null) {
                lastPath = path;
                addressBar.setText(path.getPath());
                loadImages(path);
            }
        } catch (Exception e) {
            // TODO: better error handling here
            e.printStackTrace();
        }
    }

    @FXML
    public void handleTerminalInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            runCommand();
        }
    }

    private void runCommand() {
        String[] input = terminalInput.getText().split("\\s+", 2);
        String cmd = input[0];
        String arg = input.length == 2 ? input[1] : "";

//        switch (cmd) {
//            case "sort-key":
//
//                switch (arg) {
//
//                imageFlowView.setSortDirection();
//        }

    }
}
