package mael.commands;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import mael.MaelException;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.taskList.TaskList;
import mael.ui.UI;

public class AddCommand extends Command {

    String title;
    LocalDateTime date1;
    LocalDateTime date2;
    boolean isCompleted;
    
    /**
     * Constructor for AddCommand for ToDo Tasks
     * 
     * @param title Task title
     * @param isCompleted Completion state of Task
     */
    public AddCommand(String title, boolean isCompleted) {
        this.title = title;
    }

    /**
     * Constructor for AddCommand for Deadline Tasks
     * 
     * @param title Task title
     * @param deadline Deadline of deadline task
     * @param isCompleted Completion state of Task
     */
    public AddCommand(String title, String deadline, boolean isCompleted) {
        this.title = title;
        this.date1 = Parser.parseDate(deadline);
    }

    /**
     * Constructor for AddCommand for Event Tasks
     * 
     * @param title Task title
     * @param from Start date of Event
     * @param by End date of Event
     * @param isCompleted Completion state of Task
     */
    public AddCommand(String title, String from, String by, boolean isCompleted) throws MaelException {
        this.title = title;
        try {
            this.date1 = Parser.parseDate(from);
            this.date2 = Parser.parseDate(by);
        } catch (DateTimeException e) {
            throw new MaelException("Dates not given in specified format");
        }
    }

    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) {
        ui.printAddHeader(taskList.add(title, date1, date2, isCompleted));
    }
}
