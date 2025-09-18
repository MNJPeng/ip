package mael.commands;

import mael.MaelException;
import mael.storage.Storage;
import mael.tasklist.TaskList;
import mael.ui.UI;

public class MarkCommand extends Command {
    
    private final int TASK_NUM;

    /**
     * Default constructor for MarkCommand
     * 
     * @param taskNum Task number to mark as complete
     */
    public MarkCommand(int taskNum) {
        this.TASK_NUM = taskNum;
    }

    @Override
    public void execute(TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        ui.printMarkHeader(taskList.markComplete(TASK_NUM));
    }

    @Override
    public String executeReturnString(CommandList commandList, Storage commandStorage, 
        TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        commandList.addCommandtoList(this);
        return ui.getMarkHeaderString(taskList.markComplete(TASK_NUM));
    }

    @Override
    public String undoReturnString(CommandList commandList, Storage commandStorage,
        TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        taskList.markIncomplete(TASK_NUM);
        commandList.removeCommand(this);
        return ui.getUndoHeaderString("Unmarking Mission...");
    }

    @Override
    public String toString() {
        return "Mark | " + TASK_NUM;
    }
}
