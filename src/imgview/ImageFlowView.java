package imgview;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import static java.lang.Long.signum;

public class ImageFlowView extends ScrollPane {
    enum SortKey {LASTMODIFIED, NAME, SIZE}
    enum SortDirection {ASCENDING, DESCENDING}

    private Pane layout;
    private SortedList<ImageItem> sortedImageList;
    private SortDirection sortDirection = SortDirection.DESCENDING;
    private SortKey sortKey = SortKey.LASTMODIFIED;

    @FXML
    private RadioMenuItem sortAscending;

    public ImageFlowView(ObservableList<ImageItem> imageList,
                         ReadOnlyObjectProperty<Toggle> sortDirectionProperties,
                         ReadOnlyObjectProperty<Toggle> sortKeyProperties) {
        layout = new FlowPane();
        setFitToWidth(true);
        setContent(layout);
        sortedImageList = new SortedList<>(imageList);
        sortedImageList.setComparator((o1, o2) -> compareFileOrder(o1, o2));
        // TODO: fix this so it works with sort order and stuff?
        sortedImageList.addListener((ListChangeListener<? super ImageItem>) change -> {
            while (change.next()) {
                for (ImageItem remitem : change.getRemoved()) {
                    layout.getChildren().remove(remitem);
                }
                for (ImageItem newitem : change.getAddedSubList()) {
                    layout.getChildren().add(newitem);
                }
            }
        });
//        sortDirectionProperties.addListener((observable, oldValue, newValue) -> {
//            if (oldValue != null)
//                System.out.println(((MenuItem)oldValue).toString());
//            System.out.println("NEW: " + newValue);
//        });
//        sortKeyProperties.addListener((observable, oldValue, newValue) -> {
//            System.out.println("OLD: " + oldValue);
//            System.out.println("NEW: " + newValue);
//        });
    }

    private int compareFileOrder(ImageItem o1, ImageItem o2) {
        // is this even good practise?
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
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public SortKey getSortKey() {
        return sortKey;
    }

    public void setSortKey(SortKey sortKey) {
        this.sortKey = sortKey;
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
