package mael.commands;

import mael.storage.Storage;
import mael.taskList.TaskList;
import mael.ui.UI;

public class ExitCommand extends Command{
    
    @Override
    public void execute(TaskList taskList, UI ui, Storage storage){
        storage.save(taskList);
    }

    @Override
    public String executeReturnString(TaskList taskList, UI ui, Storage storage) {
        storage.save(taskList);
        return ui.getLogoString();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
