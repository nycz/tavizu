package main.java.tavizu;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class SimpleAutoCompletionPopup extends VBox implements AutoCompletionPopup<String> {

    @Override
    public void clear() {
        getChildren().clear();
    }

    @Override
    public String getSelection() {
        if (getChildren().isEmpty()) {
            return null;
        } else {
            return ((Label)getChildren().get(getChildren().size()-1)).getText();
        }
    }

    @Override
    public void hide() {
        setVisible(false);
        setManaged(false);
    }

    @Override
    public void setItems(List<String> items) {
        clear();
        for (String text : items) {
            getChildren().add(new Label(text));
        }
        if (getChildren().isEmpty() && isVisible()) {
            hide();
        } else if (!getChildren().isEmpty() && !isVisible()) {
            show();
        }
    }

    @Override
    public void show() {
        setVisible(true);
        setManaged(true);
    }

    @Override
    public void toggleVisibility() {
        setVisible(!isVisible());
    }
}
