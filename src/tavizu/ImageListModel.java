package tavizu;

import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageListModel {
    private Set<String> allowedFormats;
    private List<ImageItem> images;

    public ImageListModel() {
        images = new ArrayList<>();
        allowedFormats = new HashSet<>();
        // TODO: don't hardcode this
        allowedFormats.add(".jpg");
        allowedFormats.add(".png");
    }

    protected List<ImageItem> loadImages(File directory) {
        images.clear();
        File[] paths = directory.listFiles((File dir, String name) ->
                                    allowedFormats.contains(name.toLowerCase().substring(name.length()-4)));
        for (File imagePath : paths) {
            Image img = new Image("file:" + imagePath.getPath(), true);
//            images.add(new ImageFile(imagePath, img));
        }
        // TODO: probably not this? returning this var feels kinda shady
        return images;
    }

//    @Override
//    protected Object getModelItem(int index) {
//        return images.get(index);
//    }
//
//    @Override
//    protected int getItemCount() {
//        return images.size();
//    }
}
