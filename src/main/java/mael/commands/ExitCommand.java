package mael.commands;

import mael.MaelException;
import mael.storage.Storage;
import mael.tasklist.TaskList;
import mael.ui.UI;

public class ExitCommand extends Command{
    
    @Override
    public void execute(TaskList taskList, Storage taskStorage, UI ui) {
        taskStorage.save(taskList);
    }

    @Override
    public String executeReturnString(CommandList commandList, Storage commandStorage, 
        TaskList taskList, Storage taskStorage, UI ui) {
        taskStorage.save(taskList);
        return ui.guiCloseString();
    }

    @Override
    public String undoReturnString(CommandList commandList, Storage commandStorage,
        TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        throw new MaelException("Exit command cannot be undone");
    }

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public String toString() {
        return "Exit";
    }
}
