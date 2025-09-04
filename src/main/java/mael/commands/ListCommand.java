package mael.commands;

import mael.storage.Storage;
import mael.taskList.TaskList;
import mael.ui.UI;

public class ListCommand extends Command {
    
    @Override
    public void execute(TaskList tasklist, UI ui, Storage storage) {
        ui.printListHeader();
        ui.printList(tasklist.getTasksAsPrintStrings());
    }
}
