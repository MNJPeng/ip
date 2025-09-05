package mael;

import javafx.application.Application;
import mael.gui.GUI;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(GUI.class, args);
    }
    
}
