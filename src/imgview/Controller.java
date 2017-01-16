package imgview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.List;

public class Controller {
    @FXML private TextField addressBar;
    @FXML private BorderPane rootContainer;
    private File lastPath;
    private ImageFlowView imageFlowView;
    private ObservableList<ImageItem> imageList;
//    private ImageListModel imageListModel;

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
//        imageListModel = new ImageListModel();
        imageFlowView = new ImageFlowView(imageList);
        // TODO: no hardcoded default path
        lastPath = new File("pics").getAbsoluteFile();
        addressBar.setText(lastPath.toString());
        rootContainer.setCenter(imageFlowView);
//        imageContainer.getChildren().add(imageFlowView);
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
//        imageContainer.getChildren().clear();
        imageList.clear();
        File[] images = directory.listFiles((File dir, String name) ->
                            name.endsWith(".png") || name.endsWith(".jpg"));
        for (File imagePath : images) {
            ImageItem imageItem = new ImageItem(imagePath);
            imageItem.setOnMousePressed(event -> {updateInfoBox(imageItem);});
            imageList.add(imageItem);
//            imageContainer.getChildren().add(imageItem);
        }


////        List<ImageFile> images = imageList.loadImages(directory);
//        for (int i = 0; i < images.size(); i++) {
////        for (ImageFile imageFile : images) {
//            ImageFile imageFile = images.get(i);
//            ImageFlowView iv = new ImageFlowView(imageFile.getImage());
//            iv.setFitHeight(thumbnailHeight);
//            iv.setFitWidth(thumbnailWidth);
//            iv.setSmooth(true);
//            iv.setPreserveRatio(true);
//            iv.setOnMousePressed(event -> {
//                    // TODO: don't select by number ffs
//                    imageList.select(imageFile, iv);
//                    updateInfoBox(iv);
//            });
//            imageContainer.getChildren().add(iv);
//        }

//        File[] images = directory.listFiles((File dir, String name) ->
//                            name.endsWith(".png") || name.endsWith(".jpg"));
//        for (File imagePath : images) {
//            imageContainer.getChildren().add(new ImageFlowView(new Image("file:"+imagePath.getPath(),
//                    thumbnailWidth, thumbnailHeight,
//                    true, true, true)));
//        }
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
}
