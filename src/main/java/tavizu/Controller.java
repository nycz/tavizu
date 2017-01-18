package main.java.tavizu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    @FXML private ToggleGroup sortDirectionGroup;
    @FXML private ToggleGroup sortKeyGroup;

    // Info box
    @FXML private VBox infoBox;
    @FXML private Label infoName;
    @FXML private Label infoDimensions;
    @FXML private Label infoSize;
    @FXML private Label infoModified;

    private double thumbnailWidth = 140;
    private double thumbnailHeight = 140;

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
            imageItem.setSizeConstraints(thumbnailWidth, thumbnailHeight);
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


    private void updateThumbnailSizes() {
        for (ImageItem imageItem : imageList) {
            imageItem.setSizeConstraints(thumbnailWidth, thumbnailWidth);
        }
    }
}
