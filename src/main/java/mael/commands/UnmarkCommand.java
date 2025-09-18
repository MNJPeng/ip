package mael.commands;

import mael.MaelException;
import mael.storage.Storage;
import mael.tasklist.TaskList;
import mael.ui.UI;

public class UnmarkCommand extends Command {
    
    private final int TASK_NUM;

    /**
     * Default constructor for UnmarkCommand
     * 
     * @param taskNum Task number to mark as incomplete
     */
    public UnmarkCommand(int taskNum) {
        this.TASK_NUM = taskNum;
    }

    @Override
    public void execute(TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        ui.printUnmarkHeader(taskList.markIncomplete(TASK_NUM));
    }

    @Override
    public String executeReturnString(CommandList commandList, Storage commandStorage, 
        TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        commandList.addCommandtoList(this);
        return ui.getUnmarkHeaderString(taskList.markIncomplete(TASK_NUM));
    }

    @Override
    public String undoReturnString(CommandList commandList, Storage commandStorage,
        TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        taskList.markComplete(TASK_NUM);
        commandList.removeCommand(this);
        return ui.getUndoHeaderString("Remarking Mission...");
    }

    @Override
    public String toString() {
        return "Unmark | " + TASK_NUM;
    }
}
