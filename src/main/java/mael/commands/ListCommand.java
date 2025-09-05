package mael.commands;

import mael.storage.Storage;
import mael.taskList.TaskList;
import mael.ui.UI;

public class ListCommand extends Command {
    
    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) {
        ui.printListHeader();
        ui.printList(taskList.getTasksAsPrintStrings());
    }

    @Override
    public String executeReturnString(TaskList taskList, UI ui, Storage storage) {
        String response = "";
        response += ui.getListHeaderString();
        response += ui.getListString(taskList.getTasksAsPrintStrings());
        return response;
    }
}
