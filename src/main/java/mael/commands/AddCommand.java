package mael.commands;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import mael.MaelException;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.tasklist.TaskList;
import mael.ui.UI;

public class AddCommand extends Command {

    private String title;
    private LocalDateTime date1;
    private LocalDateTime date2;
    private boolean isCompleted;
    private boolean isDisplayed;

    private int taskNumber;

    /**
     * Constructor for AddCommand for ToDo Tasks
     *
     * @param title Task title
     * @param isCompleted Completion state of Task
     * @param isDisplayed Display state of Task
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
     * @param isDisplayed Display state of Task
     */
    public AddCommand(String title, String deadline, boolean isCompleted, boolean isDisplayed) 
        throws MaelException {
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
     * @param isDisplayed Display state of Task
     */
    public AddCommand(String title, String from, String by, boolean isCompleted, boolean isDisplayed) 
        throws MaelException {
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

    /**
     * Inserts task at specified index in task list. Used for undoing delete commands.
     * 
     * @param taskList TaskList object to add task to
     * @param taskStorage Storage object to save task to
     * @param ui UI object to display messages
     * @param index Index to insert task at
     * @throws MaelException If index is invalid
     */
    public void insertAtIndex(TaskList taskList, Storage taskStorage, UI ui, int index) throws MaelException {
        taskList.insertAtIndex(title, date1, date2, isCompleted, index);
        taskNumber = index;
    }

    @Override
    public void execute(TaskList taskList, Storage taskStorage, UI ui) {
        String task = taskList.add(title, date1, date2, isCompleted);
        if (isDisplayed) {
            ui.printAddHeader(task);
        }
        taskNumber = taskList.findIndexInTaskList(task);
    }

    @Override
    public String executeReturnString(CommandList commandList, Storage commandStorage, 
        TaskList taskList, Storage taskStorage, UI ui) {
        String task = taskList.add(title, date1, date2, isCompleted);
        taskNumber = taskList.findIndexInTaskList(task);
        commandList.addCommandtoList(this);
        return ui.getAddHeaderString(task);
    }

    @Override
    public String undoReturnString(CommandList commandList, Storage commandStorage,
        TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        if (taskNumber > 0) {
            taskList.delete(taskNumber);
            commandList.removeCommand(this);
            return ui.getUndoHeaderString("Removing Mission...");
        } else {
            throw new MaelException("Cannot undo add command as task not found");
        }
    }

    @Override
    public String toString() {
        return "Add | " + title + " | " + date1.format(Parser.USER_FORMAT) + " | "
         + date2.format(Parser.USER_FORMAT) + " | " + isCompleted;
    }
}
