package main.java.tavizu;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles interactions with the image files in the file system,
 * such as loading them for displaying or saving changes to their metadata.
 */

public class ImageList {
    private List<ImageData> images = new ArrayList<>();
    private File loadedDirectory = null;
    private String[] allowedExtensions = {".png", ".jpg"};

    class SimpleImageData implements ImageData {
        private Image image;
        private String name;
        SimpleImageData(Image image, String name) {
            this.image = image;
            this.name = name;
        }
        public Image getImage() { return image; }
        public String getName() { return name; }
    }

    protected void loadImages(File directory) {
        loadedDirectory = directory;
        images.clear();
        // TODO: less ugly lambda
        File[] paths = directory.listFiles((File dir, String name) -> {
            for (String ext : allowedExtensions)
                if (name.endsWith(ext))
                    return true;
            return false;
        });
        for (File imagePath : paths) {
            Image image = new Image("file:" + imagePath.getPath(), true);
            images.add(new SimpleImageData(image, imagePath.getName()));
        }
    }
    protected List<ImageData> getImages() {
        return images;
    }
}
