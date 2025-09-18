package mael;

import mael.commands.Command;
import mael.commands.CommandList;
import mael.gui.GUI;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.tasklist.TaskList;
import mael.ui.UI;

public class Mael {

    private final String TASK_DATA_PATH_NAME;
    private final String DEFAULT_TASK_DATA_PATH_NAME = "data/MaelTasks.txt";

    private final String COMMAND_DATA_PATH_NAME;
    private final String DEFAULT_COMMAND_DATA_PATH_NAME = "data/MaelCommands.txt";

    private final UI ui;
    private final Storage taskStorage;
    private final Storage commandStorage;
    private final TaskList tasks;
    private final CommandList commands;
    private final GUI gui;

    /**
     * Alternative constructor of Mael for {@code Launcher}
     * 
     * @param gui GUI displaying Mael
     */
    public Mael(GUI gui) {
        this.TASK_DATA_PATH_NAME = DEFAULT_TASK_DATA_PATH_NAME;
        this.COMMAND_DATA_PATH_NAME = DEFAULT_COMMAND_DATA_PATH_NAME;
        this.gui = gui;
        ui = new UI(false, false);
        taskStorage = new Storage(TASK_DATA_PATH_NAME);
        tasks = new TaskList(taskStorage, ui);
        commandStorage = new Storage(COMMAND_DATA_PATH_NAME);
        commands = new CommandList(commandStorage, ui);
    }

    /**
     * Default constructor of Mael
     * 
     * @param dataPathName Path of Task data
     * @param hasDelay Enables delay during display 
     * @param hasSequences Enables launching and closing animations
     * @param gui GUI displaying Mael
     */
    public Mael(String commandDataPathName, String taskDataPathName, 
        boolean hasDelay, boolean hasSequences, GUI gui) {
        this.TASK_DATA_PATH_NAME = taskDataPathName;
        this.COMMAND_DATA_PATH_NAME = commandDataPathName;
        this.gui = gui;
        ui = new UI(hasDelay, hasSequences);
        taskStorage = new Storage(TASK_DATA_PATH_NAME);
        tasks = new TaskList(taskStorage, ui);
        commandStorage = new Storage(COMMAND_DATA_PATH_NAME);
        commands = new CommandList(commandStorage, ui);
    }

    /**
     * Runs instance of Mael (Depreciated version that does not support undoCommand)
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
            command.execute(tasks, taskStorage, ui);
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
            response += command.executeReturnString(commands, commandStorage, tasks, taskStorage, ui);
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
        new Mael("data/MaelCommands.txt", "data/MaelTasks.txt", true, true, null).run();
    }
}
