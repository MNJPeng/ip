package mael.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mael.Mael;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Mael mael;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image maelImage = new Image(this.getClass().getResourceAsStream("/images/mael.png"));

    /**
     * Initializes the main window and binds the scroll pane to the height of the dialog container.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Mael instance */
    public void setMael(Mael m) {
        mael = m;
    }

    /**
     * Creates a dialog box for Mael.
     * 
     * @param text The text to be displayed
     */
    public void addMaelDialogBox(String text) {
        dialogContainer.getChildren().add(DialogBox.getMaelDialog(text, maelImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Mael's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = mael.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMaelDialog(response, maelImage)
        );
        userInput.clear();
    }
}
