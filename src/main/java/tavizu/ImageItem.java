package main.java.tavizu;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class ImageItem extends BorderPane {
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
        setCenter(imageView);
        name = new Label(path.getName());
        setBottom(name);
    }

    public Image getImage() {
        return image;
    }

    public File getFile() {
        return path;
    }

    public ImageView getImageView() { return imageView; }
}
