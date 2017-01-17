package main.java.tavizu;

import java.util.List;

public interface AutoCompletionPopup<T> {
    void clear();
    String getSelection();
    void hide();
    void setItems(List<T> items);
    void show();
    void toggleVisibility();
}
