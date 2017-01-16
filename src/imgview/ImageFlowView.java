package imgview;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ImageFlowView extends ScrollPane {
//    enum SortKey {NAME, MODIFIED, SIZE, DIMENSIONS}
//    enum SortOrder {ASCENDING, DESCENDING}

    private Pane layout;
    private ObservableList<ImageItem> imageList;
//    private SortOrder sortOrder = SortOrder.ASCENDING;
//    private SortKey sortKey = SortKey.NAME;

    public ImageFlowView(ObservableList<ImageItem> imageList) {
        layout = new FlowPane();
        setFitToWidth(true);
        setContent(layout);
        this.imageList = imageList;
        imageList.addListener((ListChangeListener<? super ImageItem>) c -> {
                while (c.next()) {
                    for (ImageItem remitem : c.getRemoved()) {
                        layout.getChildren().remove(remitem);
                    }
                    for (ImageItem newitem : c.getAddedSubList()) {
                        layout.getChildren().add(newitem);
                    }
                }
                System.out.println("change");
        });
    }

//    public void populate(List<ImageItem> items) {
//        layout.getChildren().clear();
////        items.sort(Comparator.comparing(ImageItem::getPath));
//        for (Node item : items) {
//            layout.getChildren().add(item);
//        }
//    }
//
//    public void clear() {
//        layout.getChildren().clear();
//    }
}
