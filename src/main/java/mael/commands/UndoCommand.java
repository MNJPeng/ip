package mael.commands;

import mael.MaelException;
import mael.storage.Storage;
import mael.tasklist.TaskList;
import mael.ui.UI;

public class UndoCommand extends Command {
    @Override
    public void execute(TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        // No implementation needed
    }

    @Override
    public String executeReturnString(CommandList commandList, Storage commandStorage, 
        TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        Command lastCommand = commandList.getLastCommand();
        if (lastCommand != null) {
            return lastCommand.undoReturnString(commandList, commandStorage, taskList, taskStorage, ui);
        } else {
            throw new MaelException("No commands to undo");
        }
    }

    @Override
    public String undoReturnString(CommandList commandList, Storage commandStorage,
            TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        throw new MaelException("Undo command cannot be undone");
    }

    @Override
    public String toString() {
        return "Undo";
    }
    
}
