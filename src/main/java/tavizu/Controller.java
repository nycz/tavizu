package main.java.tavizu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;

import static java.lang.Long.signum;

public class Controller {
    @FXML private TextField addressBar;
    private File lastPath;
    @FXML private FlowPane imagePane;
    private ObservableList<ImageItem> imageList;

    @FXML private TextField terminalInput;
    @FXML private TextField terminalOutput;

    @FXML private ToggleGroup sortDirectionGroup;
    @FXML private ToggleGroup sortKeyGroup;

    // Info box
    @FXML private VBox infoBox;
    @FXML private Label infoName;
    @FXML private Label infoDimensions;
    @FXML private Label infoSize;
    @FXML private Label infoModified;

    private double thumbnailWidth = 200;
    private double thumbnailHeight = 200;

    enum SortDirection {ASCENDING, DESCENDING}
    enum SortKey {LASTMODIFIED, NAME, SIZE}

    private SortDirection sortDirection = SortDirection.DESCENDING;
    private SortKey sortKey = SortKey.LASTMODIFIED;


    @FXML
    public void initialize() {
        imageList = FXCollections.observableArrayList();
        // TODO: no hardcoded default path
        lastPath = new File("pics").getAbsoluteFile();
        addressBar.setText(lastPath.toString());
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
            imageItem.setOnMousePressed(event -> updateInfoBox(imageItem));
            imageList.add(imageItem);
        }
        reloadImages();
    }

    private void reloadImages() {
        imagePane.getChildren().clear();
        imagePane.getChildren().addAll(sortImages(imageList));
    }

    private SortedList<ImageItem> sortImages(ObservableList<ImageItem> images) {
        return new SortedList<>(images, (o1, o2) -> {
            int result;
            switch (sortKey) {
                case LASTMODIFIED:
                    result = signum(o1.getFile().lastModified() - o2.getFile().lastModified());
                    break;
                case NAME:
                    result = o1.getFile().compareTo(o2.getFile());
                    break;
                case SIZE:
                    result = 0;
                    break;
                default:
                    result = 0;
            }
            return sortDirection == SortDirection.ASCENDING ? result : -result;
        });
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
        System.out.printf("cmd: \"%s\", arg: \"%s\"", cmd, arg);
        SortKey key = sortKey;
        SortDirection dir = sortDirection;
        switch (cmd) {
            case "sort-key":
                switch (arg) {
                    case "date":
                        key = SortKey.LASTMODIFIED;
                        break;
                    case "size":
                        key = SortKey.SIZE;
                        break;
                    case "name":
                        key = SortKey.NAME;
                        break;
                    default:
                        terminalOutput.setText("invalid key");
                }
                break;
            case "sort-dir":
                switch (arg) {
                    case "desc":
                    case "descending":
                        dir = SortDirection.DESCENDING;
                        break;
                    case "asc":
                    case "ascending":
                        dir = SortDirection.ASCENDING;
                        break;
                    default:
                        terminalOutput.setText("invalid direction");
                }
                break;
            case "thumbsize":
                int newSize;
                try {
                    newSize = Integer.parseInt(arg);
                    thumbnailHeight = newSize;
                    thumbnailWidth = newSize;
                    updateThumbnailSizes();
                } catch (NumberFormatException nfe) {
                    terminalOutput.setText("invalid thumbnail size");
                }
        }
        if (key != sortKey || dir != sortDirection) {
            sortKey = key;
            sortDirection = dir;
            reloadImages();
        }
        terminalInput.clear();
    }

    private void updateThumbnailSizes() {
        for (ImageItem imageItem : imageList) {
            imageItem.getImageView().setFitWidth(thumbnailWidth);
            imageItem.getImageView().setFitHeight(thumbnailHeight);
        }
    }
}
