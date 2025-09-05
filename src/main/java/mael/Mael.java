package mael;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mael.commands.Command;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.taskList.TaskList;
import mael.ui.UI;

public class Mael extends Application{

    private final String DATA_PATH_NAME;
    private final String DEFAULT_DATA_PATH_NAME = "data/Mael.txt";

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/mael.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/mael.png"));

    /**
     * Alternative constructor of Mael for {@code Launcher}
     */
    public Mael() {
        this.DATA_PATH_NAME = DEFAULT_DATA_PATH_NAME;
    }

    /**
     * Default constructor of Mael
     * 
     * @param dataPathName Path of Task data
     */
    public Mael(String dataPathName) {
        this.DATA_PATH_NAME = dataPathName;
    }
    /**
     * Runs instance of Mael
     * 
     * @param hasDelay Enables delay during display 
     * @param hasSequences Enables launching and closing animations
     */
    public void run(boolean hasDelay, boolean hasSequences) {
        UI ui = new UI(hasDelay, hasSequences);
        Storage storage = new Storage(DATA_PATH_NAME);
        TaskList tasks = new TaskList(storage, ui);

        try {
            ui.launch();
        } catch (InterruptedException e) {
            ui.safeLaunch();
        }
        
        boolean isExit = false;
        
        while (!isExit) { 
        try {
            String input = ui.nextLine();
            ui.printDividerLine();
            Command command = Parser.parseInput(input);
            command.execute(tasks, ui, storage);
            isExit = command.isExit();
        } catch (MaelException e) {
            ui.printException(e);
        } finally {
            ui.printDividerLine();
        }
        }

        try {
            ui.close();
        } catch (InterruptedException e) {
            ui.safeClose();
        }
    }

    @Override
    public void start(Stage stage) {

        //Setting up required components

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        DialogBox dialogBox = new DialogBox("Hello!", userImage);
        dialogContainer.getChildren().addAll(dialogBox);

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);

        stage.setTitle("Duke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        stage.show();

        //More code to be added here later
        // run(true, true);
    }

    public static void main(String[] args) throws InterruptedException {
        new Mael("data/Mael.txt").run(true, true);
    }
}
