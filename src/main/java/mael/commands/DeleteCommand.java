package mael.commands;

import mael.MaelException;
import mael.storage.Storage;
import mael.taskList.TaskList;
import mael.ui.UI;

public class DeleteCommand extends Command {
    private final int TASK_NUM;

    /**
     * Default constructor for DeleteCommand
     * 
     * @param taskNum Task number to delete
     */
    public DeleteCommand(int taskNum) {
        this.TASK_NUM = taskNum;
    }

    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) throws MaelException {
        ui.printDeleteHeader(taskList.delete(TASK_NUM));
    }
}
