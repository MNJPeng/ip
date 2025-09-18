package mael.commands;

import java.util.stream.Collectors;

import mael.MaelException;
import mael.storage.Storage;
import mael.tasklist.TaskList;
import mael.ui.UI;

public class FindCommand extends Command {

    private String keyword;

    /**
     * Default constructor for FindCommand
     *
     * @param keyword Keyword to search for in task titles
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList taskList, Storage taskStorage, UI ui) {
        ui.printFindHeader(keyword);
        ui.printList(taskList.getTasksAsPrintStrings()
            .stream()
            .filter(task -> task.contains(keyword))
            .collect(Collectors.toList())
        );
    }

    @Override
    public String executeReturnString(CommandList commandList, Storage commandStorage, 
        TaskList taskList, Storage taskStorage, UI ui) {
        String response = "";
        response += ui.getFindHeaderString(keyword);
        response += ui.getListString(taskList.getTasksAsPrintStrings()
            .stream()
            .filter(task -> task.contains(keyword))
            .collect(Collectors.toList())
        );
        return response;
    }

    @Override
    public String undoReturnString(CommandList commandList, Storage commandStorage,
        TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        throw new MaelException("Find command cannot be undone");
    }

    @Override
    public String toString() {
        return "Find | " + keyword;
    }
}
