package mael.commands;

import java.time.LocalDateTime;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.taskList.TaskList;
import mael.ui.UI;

public class AddCommand extends Command {

    enum TaskType {
        ToDo,
        Deadline,
        Event
    }

    TaskType tasktype;
    String title;
    LocalDateTime date1;
    LocalDateTime date2;
    boolean isCompleted;

    TaskList taskList;
    UI ui;
    Storage storage;
    
    /**
     * Constructor for AddCommand for ToDo Tasks
     * 
     * @param title Task title
     * @param isCompleted Completion state of Task
     */
    public AddCommand(String title, boolean isCompleted) {
        this.title = title;
        tasktype = TaskType.ToDo;
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
        tasktype = TaskType.Deadline;
    }

    /**
     * Constructor for AddCommand for Event Tasks
     * 
     * @param title Task title
     * @param from Start date of Event
     * @param by End date of Event
     * @param isCompleted Completion state of Task
     */
    public AddCommand(String title, String from, String by, boolean isCompleted) {
        this.title = title;
        this.date1 = Parser.parseDate(from);
        this.date2 = Parser.parseDate(by);
        tasktype = TaskType.Event;
    }

    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) {
        taskList.add(title, null, null)
    }
}
