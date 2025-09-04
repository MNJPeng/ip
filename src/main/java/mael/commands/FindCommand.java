package mael.commands;

import java.util.stream.Collectors;

import mael.storage.Storage;
import mael.taskList.TaskList;
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
    public void execute(TaskList taskList, UI ui, Storage storage) {
        ui.printFindHeader(keyword);
        ui.printList(taskList.getTaskTitles()
            .stream()
            .filter(task -> task.contains(keyword))
            .collect(Collectors.toList())
        );
    }
}
