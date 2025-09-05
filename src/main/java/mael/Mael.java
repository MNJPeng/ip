package mael;

import mael.commands.Command;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.taskList.TaskList;
import mael.ui.UI;

public class Mael {

    private final String DATA_PATH_NAME;
    private final String DEFAULT_DATA_PATH_NAME = "data/Mael.txt";

    private UI ui;
    private Storage storage;
    private TaskList tasks;
    private boolean hasDelay;
    private boolean hasSequences;

    /**
     * Alternative constructor of Mael for {@code Launcher}
     */
    public Mael() {
        this.DATA_PATH_NAME = DEFAULT_DATA_PATH_NAME;
        ui = new UI(false, false);
        storage = new Storage(DATA_PATH_NAME);
        tasks = new TaskList(storage, ui);
    }

    /**
     * Default constructor of Mael
     * 
     * @param dataPathName Path of Task data
     */
    public Mael(String dataPathName, boolean hasDelay, boolean hasSequences) {
        this.DATA_PATH_NAME = dataPathName;
        ui = new UI(hasDelay, hasSequences);
        storage = new Storage(DATA_PATH_NAME);
        tasks = new TaskList(storage, ui);
    }

    /**
     * Runs instance of Mael
     * 
     * @param hasDelay Enables delay during display 
     * @param hasSequences Enables launching and closing animations
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

    public String getResponse(String input) {
        String response = "";

        try {
            response += ui.getDividerLineString();
            Command command = Parser.parseInput(input);
            response += command.executeReturnString(tasks, ui, storage);
        } catch (MaelException e) {
            response += ui.getExceptionString(e);
        } finally {
            response += ui.getDividerLineString();
        }
        return response;
    }

    public String getWelcomeMessage() {
        return ui.getLogoString();
    }

    public static void main(String[] args) throws InterruptedException {
        new Mael("data/Mael.txt",true, true).run();
    }
}
