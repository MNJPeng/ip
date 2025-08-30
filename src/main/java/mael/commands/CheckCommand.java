package mael.commands;

import java.time.LocalDateTime;
import mael.MaelException;
import mael.storage.Storage;
import mael.taskList.TaskList;
import mael.ui.UI;

public class CheckCommand extends Command {
    
    private final LocalDateTime DATE_BY;

    /**
     * Default constructor for CheckCommand
     * 
     * @param dateBy Date to check whether in event durations, or after deadlines
     */
    public CheckCommand(LocalDateTime dateBy) {
        this.DATE_BY = dateBy;
    }


    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) throws MaelException {
        ui.printCheckHeader(DATE_BY);
        ui.printList(taskList.checkDate(DATE_BY));
    }
}
