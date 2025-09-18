package mael.tasklist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import mael.MaelException;
import mael.commands.Command;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.ui.UI;

public class TaskList {

    private final List<Task> tasks = new ArrayList<>();

    /**
     * Default Constructor of TaskList
     *
     * @param storage Storage to load and save {@code Task}
     * @param ui UI to display {@code Task}
     */
    public TaskList(Storage storage, UI ui) {
        storage.load().forEach(text -> {
            try {
                Command c = Parser.parseTaskStorage(text);
                c.execute(this, storage, ui);
            } catch (MaelException e) {
                ui.printException(e);
            }
        });
    }

    /**
     * Adds {@code Task} with title
     *
     * @param title Title of task
     * @param date1 Deadline or Start Date (null if none)
     * @param date2 End Date (null if none)
     * @param isCompleted Completion state of Task
     * @return {@code Task.toString()}
     */
    public String add(String title, LocalDateTime date1, LocalDateTime date2, boolean isCompleted) {
        Task task = Task.of(title, date1, date2, isCompleted);
        tasks.add(task);
        return task.toString();
    }

    /**
     * Inserts {@code Task} with title at index
     *
     * @param title Title of task
     * @param date1 Deadline or Start Date (null if none)
     * @param date2 End Date (null if none)
     * @param isCompleted Completion state of Task
     * @param index Index to insert task at
     * @return {@code Task.toString()}
     * @throws MaelException If index is out of bounds
     */
    public String insertAtIndex(String title, LocalDateTime date1, 
        LocalDateTime date2, boolean isCompleted, int index) throws MaelException {
        Task task = Task.of(title, date1, date2, isCompleted);
        try { 
            tasks.add(index - 1, task);
        } catch (IndexOutOfBoundsException e) {
            throw new MaelException("Error re-adding mission");
        }
        return task.toString();
    }

    /**
     * Returns a list of Strings which represent {@code Task} to save
     *
     * @param tasks List of Tasks to convert
     * @param function Function to convert {@code Task} to String
     * @return List of Strings representing {@code Task}
     */
    private List<String> getTasksAsStrings(
        List<? extends Task> tasks, 
        Function<? super Task, 
        ? extends String> function) {
        return tasks.stream().map(function).collect(Collectors.toList());
    }

    /**
     * Returns a list of Strings which represent {@code Task} to print
     *
     * @return List of Strings to print
     */
    public List<String> getTasksAsPrintStrings() {
        return getTasksAsStrings(tasks, Task::toString);
    }

    /**
     * Returns a list of Strings which represent {@code Task} to save
     *
     * @return List of Strings to save in storage
     */
    public List<String> getTasksAsSaveStrings() {
        return getTasksAsStrings(tasks, Task::saveString);
    }

    /**
     * Marks task {@code num} as complete
     *
     * @param num Task number
     * @return toString of Task completed
     * @throws MaelException If task is already complete
     */
    public String markComplete(int num) throws MaelException {
        try {
            tasks.get(num - 1).markComplete();
        } catch (IndexOutOfBoundsException e) {
            throw new MaelException("Mission not found");
        }
        return tasks.get(num - 1).toString();
    }

    /**
     * Marks task {@code num} as incomplete
     *
     * @param num Task number
     * @return toString of Task marked incomplete
     * @throws MaelException If task is already incomplete
     */
    public String markIncomplete(int num) throws MaelException {
        try {
            tasks.get(num - 1).markIncomplete();
        } catch (IndexOutOfBoundsException e) {
            throw new MaelException("Mission not found");
        }
        return tasks.get(num - 1).toString();
    }

    /**
     * Deletes task {@code num}
     *
     * @param num Task number
     * @return toString of Task deleted
     * @throws MaelException If task does not exist
     */
    public String delete(int num) throws MaelException {
        try {
            return tasks.remove(num - 1).toString();
        } catch (IndexOutOfBoundsException e) {
            throw new MaelException("Mission not found");
        }
    }

    /**
     * Checks date against tasks
     *
     * @param dateBy Date to check whether in event durations, or after
     * deadlines
     * @return List of Events where {@code dateBy} is in the duration, and
     * Deadlines where {@code dateBy} is after the deadline
     */
    public List<String> checkDate(LocalDateTime dateBy) {
        return getTasksAsStrings(tasks.stream()
                .filter(task -> task.checkDate(dateBy))
                .collect(Collectors.toList()), 
            Task::toString);
    }

    /**
     * Finds index of task in task list given its string representation
     * 
     * @param keyword String representation of task
     * @return Index of task in task list + 1, -1 if not found
     */
    public int findIndexInTaskList(String keyword) {
        for (Task task : tasks) {
            if (task.toString().equals(keyword)) {
                return tasks.indexOf(task) + 1;
            }
        }
        return -1;
    }

    /**
     * Gets the save string of the task at the given index
     * 
     * @param index Index of task in task list (1-based)
     * @return Save string of task at index
     * @throws MaelException If index is invalid
     */
    public String getSaveStringFromIndex(int index) throws MaelException {
        try {
            String saveString = tasks.get(index - 1).saveString();
            return saveString.substring(0, saveString.length() - 1); // remove newline
        } catch (IndexOutOfBoundsException e) {
            throw new MaelException("Mission not found");
        }
    }

    /**
     * Class to encapsulate {@code Task} and its subclasses
     */
    private abstract static class Task {

        private final String title;
        private boolean isCompleted;

        /**
         * Default constructor of a {@code Task}
         *
         * @param title Title of the {@code Task}
         */
        private Task(String title) {
            this.title = title;
        }

        /**
         * Factory method for {@code Task}
         *
         * @param title Title of task
         * @param date1 Deadline or Start Date (null if none)
         * @param date2 End Date (null if none)
         * @param isCompleted Completion state of Task
         * @return Subclass of {@code Task} depending on input
         */
        public static Task of(String title, LocalDateTime date1, LocalDateTime date2, boolean isCompleted) {
            if (date2 != null) {
                return new Event(title, date1, date2, isCompleted);
            } else if (date1 != null) {
                return new Deadline(title, date1, isCompleted);
            } else {
                return new ToDo(title, isCompleted);
            }
        }

        /**
         * Marks {@code Task} as complete
         *
         * @throws MaelException if {@code Task} is already complete
         */
        public void markComplete() throws MaelException {
            if (this.isCompleted) {
                throw new MaelException("Mission already complete");
            }
            this.isCompleted = true;
        }

        /**
         * Marks {@code Task} as incomplete
         *
         * @throws MaelException if {@code Task} is already incomplete
         */
        public void markIncomplete() throws MaelException {
            if (!this.isCompleted) {
                throw new MaelException("Mission not yet complete");
            }
            this.isCompleted = false;
        }

        /**
         * Helper function to convert completion state to text
         *
         * @return "X" if isCompleted and " " if not isCompleted
         */
        private String getComplete() {
            return this.isCompleted ? "X" : " ";
        }

        /**
         * Helper function to convert {@code Task} to text for storage
         *
         * @return String to be stored in Mael.txt
         */
        public abstract String saveString();

        /**
         * Returns true if task is incomplete and {@code dateTime} is before the
         * deadline or during the event time
         *
         * @param dateTime Date to check
         * @return True if task is an {@code Event} such that {@code dateTime}
         * is during the event time or task is a {@code Deadline} and
         * {@code dateTime} is before the deadline
         */
        public abstract boolean checkDate(LocalDateTime dateTime);

        @Override
        public String toString() {
            return "[" + this.getComplete() + "] " + this.title;
        }

        /**
         * Subclass that encapsulates {@code ToDo} tasks
         */
        public static class ToDo extends Task {

            /**
             * Default constructor of a {@code ToDo} task from storage
             *
             * @param title Title of the {@code ToDo}
             * @param isCompleted Completion state of the {@code ToDo}
             */
            public ToDo(String title, boolean isCompleted) {
                super(title);
                if (isCompleted) {
                    super.isCompleted = isCompleted;
                }
            }

            /**
             * Helper function to convert {@code ToDo} to text for storage
             *
             * @return String to be stored in Mael.txt
             */
            @Override
            public String saveString() {
                return "T | " + super.getComplete() + " | " + super.title + "\n";
            }

            /**
             * Returns true if task is incomplete and {@code dateTime} is before
             * the deadline or during the event time
             *
             * @param dateTime Date to check
             * @return false
             */
            @Override
            public boolean checkDate(LocalDateTime dateTime) {
                return false;
            }

            @Override
            public String toString() {
                return "[T]" + super.toString();
            }

        }

        /**
         * Subclass that encapsulates {@code Deadline} tasks
         */
        public static class Deadline extends Task {

            private final LocalDateTime DEADLINE;

            /**
             * Default constructor of a {@code Deadline} task from storage
             *
             * @param title Title of the {@code Deadline}
             * @param deadline Deadline of the {@code Deadline}
             * @param isCompleted Completion state of the {@code Deadline}
             */
            public Deadline(String title, LocalDateTime deadline, boolean isCompleted) {
                super(title);
                this.DEADLINE = deadline;
                if (isCompleted) {
                    super.isCompleted = isCompleted;
                }
            }

            /**
             * Helper function to convert {@code Deadline} to text for storage
             *
             * @return String to be stored in Mael.txt
             */
            @Override
            public String saveString() {
                return "D | " + super.getComplete() + " | " + super.title + " | "
                 + this.DEADLINE.format(Parser.USER_FORMAT) + "\n";
            }

            /**
             * Returns true if task is incomplete and {@code dateTime} is before
             * the deadline or during the event time
             *
             * @param dateTime Date to check
             * @return True if task is an {@code Deadline} such that
             * {@code dateTime} is before the deadline
             */
            @Override
            public boolean checkDate(LocalDateTime dateTime) {
                return this.DEADLINE.isBefore(dateTime) && !super.isCompleted;
            }

            @Override
            public String toString() {
                return "[D]" + super.toString() + " (Imminent: " + this.DEADLINE.format(Parser.PRINT_FORMAT) + ")";
            }

        }

        /**
         * Subclass that encapsulates {@code Event} tasks
         */
        public static class Event extends Task {

            private final LocalDateTime START;
            private final LocalDateTime END;

            /**
             * Default constructor of a {@code Event} task from storage
             *
             * @param title Title of the {@code Event}
             * @param start Start Date of the {@code Event}
             * @param end End Date the {@code Event}
             * @param isCompleted Completion state of the {@code Event}
             */
            public Event(String title, LocalDateTime start, LocalDateTime end, boolean isCompleted) {
                super(title);
                this.START = start;
                this.END = end;
                if (isCompleted) {
                    super.isCompleted = isCompleted;
                }
            }

            /**
             * Helper function to convert {@code Event} to text for storage
             *
             * @return String to be stored in Mael.txt
             */
            @Override
            public String saveString() {
                return "E | " + super.getComplete() + " | " + super.title + " | "
                 + this.START.format(Parser.USER_FORMAT) + " | " + this.END.format(Parser.USER_FORMAT) + "\n";
            }

            /**
             * Returns true if task is incomplete and {@code dateTime} is before
             * the deadline or during the event time
             *
             * @param dateTime Date to check
             * @return True if task is an {@code Event} such that
             * {@code dateTime} is during the event time
             */
            @Override
            public boolean checkDate(LocalDateTime dateTime) {
                return this.START.isBefore(dateTime) && this.END.isAfter(dateTime) && !super.isCompleted;
            }

            @Override
            public String toString() {
                return "[E]" + super.toString() + " (alpha: " + this.START.format(Parser.PRINT_FORMAT)
                 + ", delta: " + this.END.format(Parser.PRINT_FORMAT) + ")";
            }

        }
    }

}
