package mael.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mael.Mael;

/**
 * A GUI for Mael using FXML.
 */
public class GUI extends Application {

    private Mael mael = new Mael();
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setMael(mael);  // inject the Mael instance
            stage.show();
            fxmlLoader.<MainWindow>getController().addMaelDialogBox(mael.getWelcomeMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        stage.close();
    }
}
