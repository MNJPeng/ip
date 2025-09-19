package mael.commands;

import mael.MaelException;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.tasklist.TaskList;
import mael.ui.UI;

public class DeleteCommand extends Command {
    private final int TASK_NUM;

    private String taskName;

    /**
     * Default constructor for DeleteCommand
     * 
     * @param taskNum Task number to delete
     */
    public DeleteCommand(int taskNum) {
        this.TASK_NUM = taskNum;
    }

    /**
     * Sets the task name of the deleted task. Used for undoing delete commands.
     * 
     * @param taskName Task name of deleted task
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void execute(TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        taskName = taskList.getSaveStringFromIndex(TASK_NUM);
        ui.printDeleteHeader(taskList.delete(TASK_NUM));
    }

    @Override
    public String executeReturnString(CommandList commandList, Storage commandStorage, 
            TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        
        taskName = taskList.getSaveStringFromIndex(TASK_NUM);
        String response = taskList.delete(TASK_NUM);
        commandList.addCommandtoList(this);
        return ui.getDeleteHeaderString(response);
    }

    @Override
    public String undoReturnString(CommandList commandList, Storage commandStorage,
            TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        Parser.parseTaskStorage(taskName).insertAtIndex(taskList, taskStorage, ui, TASK_NUM);
        commandList.removeCommand(this);
        return ui.getUndoHeaderString("Readding Mission...");
    }

    @Override
    public String toString() {
        return "Delete | " + TASK_NUM + " | " + taskName;   
    }
}
