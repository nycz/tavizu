package main.java.tavizu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class TerminalController {
    // TODO: use an ObservableList and a FilteredList instead
    private final String[] commands = {
            "sort-key",
            "sort-direction",
            "help"
    };

    @FXML private TextField terminalInput;
    @FXML private TextField terminalOutput;
    @FXML private AutoCompletionPopup autoCompletionPopup;

    @FXML
    public void initialize() {
        terminalInput.textProperty().addListener((observable, oldValue, newValue) -> {
            setAutoCompletionPrefix(newValue);
        });
        terminalInput.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (!event.isAltDown() && !event.isControlDown() && !event.isMetaDown()) {
                if (!event.isShiftDown() && event.getCode() == KeyCode.TAB) {
                    completeInput();
                    event.consume();
                }
                if (!event.isShiftDown() && event.getCode() == KeyCode.ENTER) {
                    runCommand();
                    event.consume();
                }
            }

        });
    }

    private void setAutoCompletionPrefix(String prefix) {
        if (prefix.isEmpty()) {
            autoCompletionPopup.hide();
        } else {
            List<String> suggestedCommands = new ArrayList<>();
            for (String command : commands) {
                if (command.startsWith(prefix)) {
                    suggestedCommands.add(command);
                }
            }
            autoCompletionPopup.setItems(suggestedCommands);
        }
    }

    private void completeInput() {
        String completion = autoCompletionPopup.getSelection();
        if (completion != null) {
            terminalInput.setText(completion);
            terminalInput.end();
        }
    }

    @FXML
    public void handleTerminalInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            runCommand();
        } else if (event.getCode() == KeyCode.TAB) {
            System.out.println("DED");
        }
    }

    private void runCommand() {
        String[] input = terminalInput.getText().split("\\s+", 2);
        String cmd = input[0];
        String arg = input.length == 2 ? input[1] : "";
        System.out.printf("cmd: \"%s\", arg: \"%s\"", cmd, arg);
//        switch (cmd) {
////            case "test":
////                ObservableList<String> testList = FXCollections.observableArrayList();
////                testList.add("numero uno");
////                testList.add("and another thing");
////                autoCompletionPopup.setItems(testList);
////                autoCompletionPopup.show();
////                break;
//            case "sort-key":
//                switch (arg) {
//                    case "date":
//                        key = SortKey.LASTMODIFIED;
//                        break;
//                    case "size":
//                        key = SortKey.SIZE;
//                        break;
//                    case "name":
//                        key = SortKey.NAME;
//                        break;
//                    default:
//                        terminalOutput.setText("invalid key");
//                }
//                break;
//            case "sort-dir":
//                switch (arg) {
//                    case "desc":
//                    case "descending":
//                        dir = SortDirection.DESCENDING;
//                        break;
//                    case "asc":
//                    case "ascending":
//                        dir = SortDirection.ASCENDING;
//                        break;
//                    default:
//                        terminalOutput.setText("invalid direction");
//                }
//                break;
//            case "thumbsize":
//                int newSize;
//                try {
//                    newSize = Integer.parseInt(arg);
//                    thumbnailHeight = newSize;
//                    thumbnailWidth = newSize;
//                    updateThumbnailSizes();
//                } catch (NumberFormatException nfe) {
//                    terminalOutput.setText("invalid thumbnail size");
//                }
//        }
//        if (key != sortKey || dir != sortDirection) {
//            sortKey = key;
//            sortDirection = dir;
//            reloadImages();
//        }
        terminalInput.clear();
    }

}
