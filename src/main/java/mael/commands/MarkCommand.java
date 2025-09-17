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
    public void execute(TaskList taskList, UI ui, Storage storage) throws MaelException {
        ui.printMarkHeader(taskList.markComplete(TASK_NUM));
    }

    @Override
    public String executeReturnString(TaskList taskList, UI ui, Storage storage) throws MaelException {
        return ui.getMarkHeaderString(taskList.markComplete(TASK_NUM));
    }
}
