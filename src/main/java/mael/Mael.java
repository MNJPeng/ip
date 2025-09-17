package mael;

import mael.commands.Command;
import mael.gui.GUI;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.tasklist.TaskList;
import mael.ui.UI;

public class Mael {

    private final String DATA_PATH_NAME;
    private final String DEFAULT_DATA_PATH_NAME = "data/Mael.txt";

    private UI ui;
    private Storage storage;
    private TaskList tasks;
    private boolean hasDelay;
    private boolean hasSequences;
    private GUI gui;

    /**
     * Alternative constructor of Mael for {@code Launcher}
     * 
     * @param gui GUI displaying Mael
     */
    public Mael(GUI gui) {
        this.DATA_PATH_NAME = DEFAULT_DATA_PATH_NAME;
        this.gui = gui;
        ui = new UI(false, false);
        storage = new Storage(DATA_PATH_NAME);
        tasks = new TaskList(storage, ui);
    }

    /**
     * Default constructor of Mael
     * 
     * @param dataPathName Path of Task data
     * @param hasDelay Enables delay during display 
     * @param hasSequences Enables launching and closing animations
     * @param gui GUI displaying Mael
     */
    public Mael(String dataPathName, boolean hasDelay, boolean hasSequences, GUI gui) {
        this.DATA_PATH_NAME = dataPathName;
        this.gui = gui;
        ui = new UI(hasDelay, hasSequences);
        storage = new Storage(DATA_PATH_NAME);
        tasks = new TaskList(storage, ui);
    }

    /**
     * Runs instance of Mael
     */
    public void run() {
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

    /**
     * Gets Mael's response to user input
     * 
     * @param input User input
     * @return Mael's response
     */
    public String getResponse(String input) {
        String response = "";

        try {
            response += ui.getDividerLineString();
            Command command = Parser.parseInput(input);
            response += command.executeReturnString(tasks, ui, storage);
            if (command.isExit() && gui != null) {
                gui.close();
            }
        } catch (MaelException e) {
            response += ui.getExceptionString(e);
        } finally {
            response += ui.getDividerLineString();
        }
        return response;
    }

    /**
     * Gets Mael's welcome message
     * 
     * @return Mael's welcome message
     */
    public String getWelcomeMessage() {
        return ui.guiLaunchString();
    }

    public static void main(String[] args) throws InterruptedException {
        new Mael("data/Mael.txt",true, true, null).run();
    }
}
