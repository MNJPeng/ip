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
    public void execute(TaskList taskList, UI ui, Storage storage) throws MaelException {
        ui.printUnmarkHeader(taskList.markIncomplete(TASK_NUM));
    }

    @Override
    public String executeReturnString(TaskList taskList, UI ui, Storage storage) throws MaelException {
        return ui.getUnmarkHeaderString(taskList.markIncomplete(TASK_NUM));
    }

    @Override
    public String toString() {
        return "Unmark | " + TASK_NUM;
    }
}
