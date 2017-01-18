package main.java.tavizu;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;

public class ImageItem extends VBox {
    private Image image;
    private ImageView imageView;
    private File path;
    private Label name;

    public ImageItem(File path) {
        this.path = path;
        image = new Image("file:" + path.getPath(), true);
        imageView = new ImageView(image);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);
        name = new Label(path.getName());
        name.setWrapText(true);
        getChildren().add(name);
    }

    public void setSizeConstraints(double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        setMaxWidth(width);
    }

    public Image getImage() {
        return image;
    }

    public File getFile() {
        return path;
    }

    public ImageView getImageView() { return imageView; }
}
