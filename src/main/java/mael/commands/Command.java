package mael.commands;

import mael.MaelException;
import mael.storage.Storage;
import mael.tasklist.TaskList;
import mael.ui.UI;

public abstract class Command {
    // The following javadoc comments have been written with the help of copilot
    
    /**
     * Executes associated command without updating command list
     * 
     * @param taskList Associated {@code TaskList}
     * @param ui Associated {@code UI}
     * @param storage Associated {@code Storage}
     */
    public abstract void execute(TaskList taskList, Storage taskStorage, UI ui) throws MaelException;
    
    /**
     * Executes associated command and returns the result as a string
     * 
     * @param commandList Associated {@code CommandList}
     * @param commandStorage Associated {@code Storage} for commands
     * @param taskList Associated {@code TaskList}
     * @param taskStorage Associated {@code Storage} for tasks
     * @param ui Associated {@code UI}
     * @return Result of execution as a string
     */
    public abstract String executeReturnString(CommandList commandList, Storage commandStorage, 
            TaskList taskList, Storage taskStorage, UI ui) throws MaelException;

    /**
     * Undoes associated command and returns the result as a string
     *
     * @param commandList Associated {@code CommandList}
     * @param commandStorage Associated {@code Storage} for commands
     * @param taskList Associated {@code TaskList}
     * @param taskStorage Associated {@code Storage} for tasks
     * @param ui Associated {@code UI}
     * @return Result of execution as a string
     */
    public abstract String undoReturnString(CommandList commandList, Storage commandStorage, 
            TaskList taskList, Storage taskStorage, UI ui) throws MaelException;

    /**
     * Returns true if is {@code ExitCommand}, else false
     * 
     * @return true if is {@code ExitCommand}, else false
     */
    public boolean isExit() {
        return false;
    }

}
