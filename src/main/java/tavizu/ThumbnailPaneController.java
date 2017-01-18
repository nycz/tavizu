package main.java.tavizu;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ThumbnailPaneController {
    @FXML private FlowPane imagePane;
    private ObservableList<Thumbnail> imageList = FXCollections.observableArrayList();

    // Thumbnail dimensions
    private DoubleProperty thumbnailWidth = new SimpleDoubleProperty(140);
    private DoubleProperty thumbnailHeight = new SimpleDoubleProperty(140);

    // Sorting
    private enum SortDirection {ASCENDING, DESCENDING}
    private enum SortKey {LASTMODIFIED, NAME, SIZE}
    private SortDirection sortDirection = SortDirection.DESCENDING;
    private SortKey sortKey = SortKey.LASTMODIFIED;

    protected void setImages(List<ImageData> images) {
        List<ImageData> sortedImages = new ArrayList<>(images);
//        sortedImages.sort(Comparator.comparing(ImageData::getName).reversed());
        imageList.clear();
        for (ImageData image : sortImages(images)) {
            imagePane.getChildren().add(new Thumbnail(image.getImage(),
                                                      image.getName()));
        }
    }

    private List<ImageData> sortImages(List<ImageData> images) {
        List<ImageData> sortedImages = new ArrayList<>(images);
//        Comparator<ImageData> comparator;
//        switch (sortKey) {
//            case NAME:
//                comparator = Comparator.comparing(ImageData::getName);
//                break;
//            case LASTMODIFIED:
//                comparator = Comparator.comparing(ImageData::get);
//        }
//        if (sortKey == SortKey.NAME) {
//
//        }
//        if (sortDirection == SortDirection.ASCENDING) {}
        return sortedImages;
    }

//    private void sort

    protected void setThumbnailSize(double size) {
        thumbnailWidth.set(size);
        thumbnailHeight.set(size);
    }

    class Thumbnail extends VBox {
        private Image image;
        private ImageView imageView;
        private Label nameLabel;

        public Thumbnail(Image image, String name) {
            this.image = image;
            getStyleClass().add("thumbnail");
            maxWidthProperty().bind(thumbnailWidth);
            imageView = new ImageView(image);
            imageView.setSmooth(true);
            imageView.setPreserveRatio(true);
            imageView.fitWidthProperty().bind(thumbnailWidth);
            imageView.fitHeightProperty().bind(thumbnailHeight);
            getChildren().add(imageView);
            nameLabel = new Label(name);
            nameLabel.setWrapText(true);
            getChildren().add(nameLabel);
        }

        public Image getImage() {
            return image;
        }

        public ImageView getImageView() { return imageView; }
    }
}

