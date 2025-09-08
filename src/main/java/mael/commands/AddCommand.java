package mael.commands;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import mael.MaelException;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.taskList.TaskList;
import mael.ui.UI;

public class AddCommand extends Command {

    private String title;
    private LocalDateTime date1;
    private LocalDateTime date2;
    private boolean isCompleted;
    private boolean isDisplayed;

    /**
     * Constructor for AddCommand for ToDo Tasks
     *
     * @param title Task title
     * @param isCompleted Completion state of Task
     */
    public AddCommand(String title, boolean isCompleted, boolean isDisplayed) {
        this.title = title;
        this.isCompleted = isCompleted;
        this.isDisplayed = isDisplayed;
    }

    /**
     * Constructor for AddCommand for Deadline Tasks
     *
     * @param title Task title
     * @param deadline Deadline of deadline task
     * @param isCompleted Completion state of Task
     */
    public AddCommand(String title, String deadline, boolean isCompleted, boolean isDisplayed) throws MaelException {
        this.title = title;
        try {
            this.date1 = Parser.parseDate(deadline);
        } catch (DateTimeException e) {
            throw new MaelException("Date not given in specified format");
        }
        this.isCompleted = isCompleted;
        this.isDisplayed = isDisplayed;
    }

    /**
     * Constructor for AddCommand for Event Tasks
     *
     * @param title Task title
     * @param from Start date of Event
     * @param by End date of Event
     * @param isCompleted Completion state of Task
     */
    public AddCommand(String title, String from, String by, boolean isCompleted, boolean isDisplayed) throws MaelException {
        this.title = title;
        try {
            this.date1 = Parser.parseDate(from);
            this.date2 = Parser.parseDate(by);
        } catch (DateTimeException e) {
            throw new MaelException("Dates not given in specified format");
        }
        this.isCompleted = isCompleted;
        this.isDisplayed = isDisplayed;
    }

    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) {
        String task = taskList.add(title, date1, date2, isCompleted);
        if (isDisplayed) {
            ui.printAddHeader(task);
        }
    }

    @Override
    public String executeReturnString(TaskList taskList, UI ui, Storage storage) {
        String task = taskList.add(title, date1, date2, isCompleted);
        return ui.getAddHeaderString(task);
    }
}
