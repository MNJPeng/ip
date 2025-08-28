package mael;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

class TaskList {

    private final ArrayList<Task> tasks;

    /**
     * Default Constructor of TaskList
     * 
     * @param storage 
     */
    public TaskList(Storage storage) {
        this.tasks = storage.load();
    }

    
    /** 
     * Class to encapsulate {@code Task} and its subclasses
     */ 
    private abstract class Task {
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
         * @param text Takes user inputs with the form specified by Level-4 requirements
         * @return Subclass of {@code Task} depending on input
         * @throws MaelException Thrown when text input is of an unspecified form
         * @throws DateTimeParseException If text cannot be parsed in {@code userFormat} DateTime format
         */
        public Task of(String text) throws MaelException, DateTimeParseException {
            String[] sections = text.split(" /");
            switch (text.split(" ")[0]) {
                case "event" -> {
                    if (text.split(" ").length == 1) {
                        throw new MaelException("Event activity unspecified");
                    } else if (sections.length != 3) {
                        throw new MaelException("Event details unclear");
                    } else if (!sections[1].substring(0, 4).equals("from") 
                            || !sections[2].substring(0, 2).equals("to")) {
                        throw new MaelException("Event boundaries unclear");
                    }
                    return new Event(sections);
                }
                case "deadline" -> {
                    if (text.split(" ").length == 1) {
                        throw new MaelException("Deadline activity unspecified");
                    } else if (sections.length != 2) {
                        throw new MaelException("Deadline details unclear");
                    } else if (!sections[1].substring(0, 2).equals("by")) {
                        throw new MaelException("Deadline unclear");
                    }
                    return new Deadline(sections);
                }
                case "todo" -> {
                    if (text.split(" ").length == 1) {
                        throw new MaelException("ToDo activity unspecified");
                    } else if (sections.length != 1) {
                        throw new MaelException("ToDo details unclear");
                    }
                    return new ToDo(sections);
                }
                default -> throw new MaelException("Unknown Mission");
            }

        }

        /**
         * Loads {@code Task} from text file into active list
         * 
         * @param text Takes user inputs with the form specified in save file ../data/Mael.txt
         * @throws MaelException Thrown when text input is of an unspecified form
         * @throws DateTimeParseException If text cannot be parsed in {@code userFormat} DateTime format
         */
        public void generate(String text) throws MaelException, DateTimeException {
            String[] sections = text.split(" \\| ");
            try {
                switch (sections[0]) {
                    case "T":
                        if (sections.length == 3) {
                            tasks.add(new ToDo(sections[2], sections[1].equals("X")));
                        } else {
                            throw new MaelException("Corrupted ToDo");
                        }
                        break;
                    case "D":
                        if (sections.length == 4) {
                            tasks.add(new Deadline(sections[2], 
                                    LocalDateTime.parse(sections[3], Mael.userFormat), 
                                    sections[1].equals("X")
                                    ));
                        } else {
                            throw new MaelException("Corrupted Deadline");
                        }
                        break;
                    case "E":
                        if (sections.length == 5) {
                            tasks.add(new Event(sections[2], 
                                    LocalDateTime.parse(sections[3], Mael.userFormat), 
                                    LocalDateTime.parse(sections[4], Mael.userFormat), 
                                    sections[1].equals("X")));
                        } else {
                            throw new MaelException("Corrupted Event");
                        }
                        break;
                    default:
                        throw new MaelException("Unable to load unknown task");
                }
            } catch (DateTimeException e) {
                throw new MaelException("Date corrupted");
            }
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
        public class ToDo extends Task {

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
            * Constructor of a {@code ToDo} task during user input
            * 
            * @param sections String array taken from the user input, split by "/"
            */
            public ToDo(String[] sections) {
                super(sections[0].substring(5));
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
        public class Deadline extends Task {
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
            * Constructor of a {@code Deadline} task during user input
            * 
            * @param sections String array taken from the user input, split by "/"
            */
            public Deadline(String[] sections) {
                super(sections[0].substring(9));
                this.deadline = LocalDateTime.parse(sections[1].substring(3),Mael.userFormat);
            }

            /**
            * Helper function to convert {@code Deadline} to text for storage
            *
            * @return String to be stored in Mael.txt
            */
            @Override
            public String saveString() {
                return "D | " + super.getComplete() + " | " + super.title + " | " + this.deadline.format(Mael.userFormat) + "\n";
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
                return "[D]" + super.toString() + " (Imminent: " + this.deadline.format(Mael.printFormat) + ")";
            }

        }

        /**
         * Subclass that encapsulates {@code Event} tasks
         */
        public class Event extends Task {
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
            * Constructor of a {@code Event} task during user input
            * 
            * @param sections String array taken from the user input, split by "/"
            * @throws DateTimeParseException If text cannot be parsed in {@code userFormat} DateTime format
            */
            public Event(String[] sections) throws DateTimeParseException {
                super(sections[0].substring(6));
                this.start = LocalDateTime.parse(sections[1].substring(5), Mael.userFormat);
                this.end = LocalDateTime.parse(sections[2].substring(3), Mael.userFormat);
            }

            /**
            * Helper function to convert {@code Event} to text for storage
            *
            * @return String to be stored in Mael.txt
            */
            @Override
            public String saveString() {
                return "E | " + super.getComplete() + " | " + super.title + " | " + this.start.format(Mael.userFormat) + " | " + this.end.format(Mael.userFormat) + "\n";
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
                return "[E]" + super.toString() + " (alpha: " + this.start.format(Mael.printFormat) + ", delta: " + this.end.format(Mael.printFormat) + ")";
            }

        }
    }

}
