package mael.commands;

import mael.MaelException;
import mael.storage.Storage;
import mael.taskList.TaskList;
import mael.ui.UI;

public abstract class Command {
    
    /**
     * Executes associated command
     * 
     * @param taskList Associated {@code TaskList}
     * @param ui Associated {@code UI}
     * @param storage Associated {@code Storage}
     */
    public abstract void execute(TaskList taskList, UI ui, Storage storage) throws MaelException;
    
    /**
     * Executes associated command and returns the result as a string
     * 
     * @param taskList Associated {@code TaskList}
     * @param ui Associated {@code UI}
     * @param storage Associated {@code Storage}
     * @return Result of execution as a string
     */
    public abstract String executeReturnString(TaskList taskList, UI ui, Storage storage) throws MaelException;

    /**
     * Returns true if is {@code ExitCommand}, else false
     * 
     * @return true if is {@code ExitCommand}, else false
     */
    public boolean isExit() {
        return false;
    }

}
