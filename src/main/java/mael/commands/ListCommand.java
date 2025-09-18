package mael.commands;

import mael.MaelException;
import mael.storage.Storage;
import mael.tasklist.TaskList;
import mael.ui.UI;

public class ListCommand extends Command {
    
    @Override
    public void execute(TaskList taskList, Storage taskStorage, UI ui) {
        ui.printListHeader();
        ui.printList(taskList.getTasksAsPrintStrings());
    }

    @Override
    public String executeReturnString(CommandList commandList, Storage commandStorage, 
        TaskList taskList, Storage taskStorage, UI ui) {
        String response = "";
        response += ui.getListHeaderString();
        response += ui.getListString(taskList.getTasksAsPrintStrings());
        return response;
    }

    @Override
    public String undoReturnString(CommandList commandList, Storage commandStorage,
        TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        throw new MaelException("List command cannot be undone");
    }

    @Override
    public String toString() {
        return "List";
    }
}
