package mael.taskList;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mael.MaelException;
import mael.parser.Parser;
import mael.storage.Storage;

public class TaskList {

    private final List<Task> tasks = new ArrayList<>();

    /**
     * Default Constructor of TaskList
     * 
     * @param storage Storage to load and save {@code Task}
     */
    public TaskList(Storage storage) {
        storage.load().forEach(text -> {
            try {
                tasks.add(Task.generate(text));
            } catch (MaelException e) {
                System.out.println(e);
            }
        });
    }

    /**
     * Adds Todo Task with title
     * 
     * @param title Title of ToDo Task
     * @return Task added
     */
    public String add(String title, LocalDateTime date1, LocalDateTime date2) {
        Task task = Task.of(title, date1, date2);
        tasks.add(task);
        return task.toString();
    }

    /**
     * Returns a list of Strings which represent {@code Task} to save
     * 
     * @return List of Strings to save in storage
     */
    public List<String> getTasks() {
        return tasks.stream().map(task -> task.saveString()).collect(Collectors.toList());
    }

    
    /** 
     * Class to encapsulate {@code Task} and its subclasses
     */ 
    private abstract static class Task {
        private final String title;
        private boolean completed;

        /**
         * Default constructor of a {@code Task}
         * 
         * @param title Title of the {@code Task}
         */
        private Task(String title) {
            this.title = title;
        }

        /**
         * Factory method for {@code Task} during user inputs 
         * 
         * @param title Title of task
         * @param date1 Deadline or Start Date
         * @param date2 End Date
         * @return Subclass of {@code Task} depending on input
         */
        public static Task of(String title, LocalDateTime date1, LocalDateTime date2) {
            if (date2 != null) {
                return new Event(title, date1, date2, false);
            } else if (date1 != null) {
                return new Deadline(title, date1, false);
            } else {
                return new ToDo(title, false);
            }
        }

        /**
         * Factory method for {@code Task} from text file
         * 
         * @param text Takes inputs with the form specified in save file ../data/Mael.txt
         * @return Returns {@code Task} based on input
         * @throws MaelException Thrown when text input is of an unspecified form
         * @throws DateTimeParseException If text cannot be parsed in {@code USER_FORMAT} DateTime format
         */
        public static Task generate(String text) throws MaelException, DateTimeException {
            
        }
        /**
         * Marks {@code Task} as complete
         * 
         * @throws MaelException if {@code Task} is already complete
         */
        public void markComplete() throws MaelException {
            if (this.completed) {
                throw new MaelException("Mission already complete");
            }
            this.completed = true;
        }

        /**
         * Marks {@code Task} as incomplete
         * 
         * @throws MaelException if {@code Task} is already incomplete
         */
        public void markIncomplete() throws MaelException {
            if (!this.completed) {
                throw new MaelException("Mission not yet complete");
            }
            this.completed = false;
        }

        /**
         * Helper function to convert completion state to text
         * 
         * @return "X" if completed and " " if not completed
         */
        private String getComplete() {
            return this.completed ? "X" : " ";
        }

        /**
         * Helper function to convert {@code Task} to text for storage
         * 
         * @return String to be stored in Mael.txt
         */
        public abstract String saveString();

        /**
         * Returns true if task is incomplete and {@code dateTime} is before the deadline or during the event time
         * 
         * @param dateTime Date to check
         * @return True if task is an {@code Event} such that {@code dateTime} is during the event time 
         * or task is a {@code Deadline} and {@code dateTime} is before the deadline
         */
        public abstract boolean isBefore(LocalDateTime dateTime);

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
             * @param completed Completion state of the {@code ToDo}
             */
            public ToDo(String title, boolean completed) {
                super(title);
                if (completed) {
                    super.completed = completed;
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
             * Returns true if task is incomplete and {@code dateTime} is before the deadline or during the event time
             * 
             * @param dateTime Date to check
             * @return false
             */
            @Override
            public boolean isBefore(LocalDateTime dateTime) {
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
            private LocalDateTime deadline;

            /**
             * Default constructor of a {@code Deadline} task from storage
             * 
             * @param title Title of the {@code Deadline}
             * @param deadline Deadline of the {@code Deadline}
             * @param completed Completion state of the {@code Deadline}
             */
            public Deadline(String title, LocalDateTime deadline, boolean completed) {
                super(title);
                this.deadline = deadline;
                if (completed) {
                    super.completed = completed;
                }
            }

            /**
            * Helper function to convert {@code Deadline} to text for storage
            *
            * @return String to be stored in Mael.txt
            */
            @Override
            public String saveString() {
                return "D | " + super.getComplete() + " | " + super.title + " | " + this.deadline.format(Parser.USER_FORMAT) + "\n";
            }

            /**
             * Returns true if task is incomplete and {@code dateTime} is before the deadline or during the event time
             * 
             * @param dateTime Date to check
             * @return True if task is an {@code Deadline} such that {@code dateTime} is before the deadline
             */
            @Override
            public boolean isBefore(LocalDateTime dateTime) {
                return this.deadline.isAfter(dateTime) && !super.completed;
            }

            @Override
            public String toString() {
                return "[D]" + super.toString() + " (Imminent: " + this.deadline.format(Parser.PRINT_FORMAT) + ")";
            }

        }

        /**
         * Subclass that encapsulates {@code Event} tasks
         */
        public static class Event extends Task {
            private LocalDateTime start;
            private LocalDateTime end;

            /**
             * Default constructor of a {@code Event} task from storage
             * 
             * @param title Title of the {@code Event}
             * @param start Start Date of the {@code Event}
             * @param end End Date the {@code Event}
             * @param completed Completion state of the {@code Event}
             */
            public Event(String title, LocalDateTime start, LocalDateTime end, boolean completed) {
                super(title);
                this.start = start;
                this.end = end;
                if (completed) {
                    super.completed = completed;
                }
            }

            /**
            * Helper function to convert {@code Event} to text for storage
            *
            * @return String to be stored in Mael.txt
            */
            @Override
            public String saveString() {
                return "E | " + super.getComplete() + " | " + super.title + " | " + this.start.format(Parser.USER_FORMAT) + " | " + this.end.format(Parser.USER_FORMAT) + "\n";
            }

            /**
             * Returns true if task is incomplete and {@code dateTime} is before the deadline or during the event time
             * 
             * @param dateTime Date to check
             * @return True if task is an {@code Event} such that {@code dateTime} is during the event time
             */
            @Override
            public boolean isBefore(LocalDateTime dateTime) {
                return this.start.isBefore(dateTime) && this.end.isAfter(dateTime) && !super.completed;
            }

            @Override
            public String toString() {
                return "[E]" + super.toString() + " (alpha: " + this.start.format(Parser.PRINT_FORMAT) + ", delta: " + this.end.format(Parser.PRINT_FORMAT) + ")";
            }

        }
    }

}
