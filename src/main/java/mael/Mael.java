package mael;

import mael.commands.Command;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.taskList.TaskList;
import mael.ui.UI;

public class Mael {

    private final String DATA_PATH_NAME;

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

    public static void main(String[] args) throws InterruptedException {
        new Mael("data/Mael.txt").run(true, true);
    }
}
