import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Mael {

    private static final boolean DELAY = true; // Set to false for no delays

    private static final Random RNG = new Random(100);
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String LOGO = 
        """
       .oXXXXXXXXXXXXXXXXXXo.
     .oXXXXX/XXXXXXXX\\XXXXXXXo.
    /XXXXXX-\\XXXXXXXX/-XXXXXXXx\\
   dXXXXXX/XX\\XXXXXX/XX\\XXXXXXXXb
  XXXXXXXXXXXX\\XXXX/XXXXXXXXXXXXX
  XXX'~   ~`OO8bXXXd8OO'~  ~`XXXXX
  9XX'        `98v8P'         `XXP'
   9X.        .db|db.         .XP
    )b. .dbo.dP'`v'`9b.odb. .dX(
  ,dXXXXXXXXXXb     dXXXXXXXXXXb.
 dXXXXXXXXXXP'   .   `9XXXXXXXXXXb
dXXXXXXXXXXXb   d|b   dXXXXXXXXXXXb
9XXb'  `XXXXXb.dX|Xb.dXXXXX'  `dXXP
 `'     9XXXXXX(   )XXXXXXP     `'
         XXXX X.`N'.X XXXX
         XP^X'`O   0'`X^XX
         X. R  `   '  9 )X
         `K  `       '  7'
          `             '
        """;

    /** 
     * Class to encapsulate {@code Task} and its subclasses
     */ 
    private abstract static class Task {
        private final String title;
        private boolean completed;
        private static DateTimeFormatter userFormat = DateTimeFormatter.ofPattern("ddMMyyyy HHmm");
        private static DateTimeFormatter printFormat = DateTimeFormatter.ofPattern("dd MMM yyyy HHmm");

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
         */
        public static Task of(String text) throws MaelException, DateTimeParseException {
            String[] sections = text.split("/");
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
         */
        public static void generate(String text) throws MaelException {
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
                                    LocalDateTime.parse(sections[3], userFormat), 
                                    sections[1].equals("X")
                                    ));
                        } else {
                            throw new MaelException("Corrupted Deadline");
                        }
                        break;
                    case "E":
                        if (sections.length == 5) {
                            tasks.add(new Event(sections[2], 
                                    LocalDateTime.parse(sections[3], userFormat), 
                                    LocalDateTime.parse(sections[4], userFormat), 
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
         */
        private String getComplete() {
            return this.completed ? "X" : " ";
        }

        /**
         * Helper function to convert {@code Task} to text for storage
         */
        public abstract String saveString();

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
            * Constructor of a {@code ToDo} task during user input
            * 
            * @param sections String array taken from the user input, split by "/"
            */
            public ToDo(String[] sections) {
                super(sections[0].substring(5));
            }

            /**
            * Helper function to convert {@code ToDo} to text for storage
            */
            @Override
            public String saveString() {
                return "T | " + super.getComplete() + " | " + super.title + "\n";
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
            * Constructor of a {@code Deadline} task during user input
            * 
            * @param sections String array taken from the user input, split by "/"
            */
            public Deadline(String[] sections) {
                super(sections[0].substring(9));
                this.deadline = LocalDateTime.parse(sections[1].substring(3));
            }

            /**
            * Helper function to convert {@code Deadline} to text for storage
            */
            @Override
            public String saveString() {
                return "D | " + super.getComplete() + " | " + super.title + " | " + this.deadline.format(userFormat) + "\n";
            }

            @Override
            public String toString() {
                return "[D]" + super.toString() + "(Imminent: " + this.deadline.format(printFormat) + ")";
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
            * Constructor of a {@code Event} task during user input
            * 
            * @param sections String array taken from the user input, split by "/"
            */
            public Event(String[] sections) throws DateTimeParseException {
                super(sections[0].substring(6));
                this.start = LocalDateTime.parse(sections[1].substring(5), userFormat);
                this.end = LocalDateTime.parse(sections[2].substring(3), userFormat);
            }

            /**
            * Helper function to convert {@code Event} to text for storage
            */
            @Override
            public String saveString() {
                return "E | " + super.getComplete() + " | " + super.title + " | " + this.start.format(userFormat) + " | " + this.end.format(userFormat) + "\n";
            }

            @Override
            public String toString() {
                return "[E]" + super.toString() + "(alpha: " + this.start.format(printFormat) + ", delta: " + this.end.format(printFormat) + ")";
            }

        }
    }

    // List of Tasks
    private static final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * For exceptions specific to Mael
     */ 
    private static class MaelException extends Exception {

        public MaelException(String message) {
            super(message);
        }

        @Override
        public String toString() {
            return "\tMael encountered a critical error...\n\t\t" 
                    + super.getMessage() + "\n\n\t\t-Terminated request-";
        }
        
    }

    /**
     * Method to display the initial text when running Mael
     */ 
    private static void launch() throws InterruptedException  {
        String[] text = new String[] {"Injecting Mael", ".", ".", ".\n", null, 
                "Mael injection complete\n", "Awaiting instructions", ".", ".", ".\n\n"};
        int[] delays = new int[] {400, 400, 400, 800, 1200, 600, 400, 400, 400, 400};

        for (int i = 0; i < text.length; i++) {
            if (i == 4) {
                Mael.lineByLine(LOGO);
            } else {
                System.out.print(text[i]);
            }
            if (DELAY) {
                Thread.sleep(delays[i] + RNG.nextInt(0,400) - 200);
            }
        }
        
    }

    /** 
     * Method to display the final text when ending Mael
     */ 
    private static void close() throws InterruptedException {
        String[] text = new String[] {"\nWiping Mael", ".", ".", ".\n", null, 
                "Mael Erased\n", "Like you were never here...\n"};
        int[] delays = new int[] {400, 400, 400, 800, 1200, 600, 1200};

        for (int i = 0; i < text.length; i++) {
            if (i == 4) {
                Mael.lineByLine(LOGO);
            } else {
                System.out.print(text[i]);
            }
            if (DELAY) {
                Thread.sleep(delays[i] + RNG.nextInt(0,400) - 200);
            }
        }
    }

    /** 
     * Method to display the line divider text between inputs and outputs of Mael
     */ 
    private static void line() {
        String[] symbols = new String[] {"~", "-", "=", "+", "#"};
        String line = "";
        for (int i = 0; i < 50; i++) {
            line += symbols[RNG.nextInt(0, symbols.length - 1)];
        }
        System.out.println("\n" + line);
    }

    /** 
     * Method to display the logo line by line with delays
     * 
     * @param text Text to be displayed line by line
     */
    private static void lineByLine(String text) throws InterruptedException {
        String[] lines = text.split("\n");
        for (String line : lines) {
            System.out.println(line);
            if (DELAY) {
                Thread.sleep(50 + RNG.nextInt(0, 100));
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        launch();
        File taskFolder = new File("./data");
        File taskFile = new File("./data/Mael.txt");
        try {
            if (!taskFolder.exists()) {
                taskFolder.mkdir();
            } else if (taskFolder.isFile()) {
                taskFolder.delete();
                taskFolder.mkdir();
            }
            if (!taskFile.exists()) {
                taskFile.createNewFile();
            } else if (taskFile.isDirectory()) {
                taskFile.delete();
                taskFile.createNewFile();
            }
            Scanner taskReader = new Scanner(taskFile);
            while (taskReader.hasNextLine()) {
                String currLine = taskReader.nextLine();
                try {
                    Task.generate(currLine);
                } catch (MaelException e) {
                    System.out.println(e);
                }
                
            }
            taskReader.close();
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                taskFile.delete();
                taskFile.createNewFile();
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        String input = SCANNER.nextLine();
        while (!input.equals("bye")) {  // Early termination when "bye" is input
            line();
            switch (input.split(" ")[0]) {  // Switch only checking for the first word
                case "list", "ls" -> {
                    if (input.split(" ").length == 1) {
                        System.out.println("\t\t-Outstanding Missions-");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println("\t" + (i + 1) + "." + tasks.get(i));
                        }
                    } else {
                        System.err.println(new MaelException("Unknown command for list"));
                    }
                }
                case "mark", "m" -> {
                    if (input.split(" ").length == 2) {
                        try {
                            int taskNum = Integer.parseInt(input.split(" ")[1]);
                            tasks.get(taskNum - 1).markComplete();
                            System.out.println("\t" + tasks.get(taskNum - 1));
                            System.out.println("\t\t-Mission Completed-");
                        } catch (NumberFormatException e) {
                            System.err.println(new MaelException("Mark details unclear"));
                        } catch (IndexOutOfBoundsException e) {
                            System.err.println(new MaelException("Mission unspecified"));
                        } catch (MaelException e) {
                            System.err.println(e);
                        }
                    } else {
                        System.err.println(new MaelException("Unknown command for mark"));
                    }
                }
                case "unmark", "um" -> {
                    if (input.split(" ").length == 2) {
                        try {
                            int taskNum = Integer.parseInt(input.split(" ")[1]);
                            tasks.get(taskNum - 1).markIncomplete();
                            System.out.println("\t" + tasks.get(taskNum - 1));
                            System.out.println("\t\t-Mission Unsuccessful-");
                        } catch (NumberFormatException e) {
                            System.err.println(new MaelException("Unmark details unclear"));
                        } catch (IndexOutOfBoundsException e) {
                            System.err.println(new MaelException("Mission unspecified"));
                        } catch (MaelException e) {
                            System.err.println(e);
                        }
                    } else {
                        System.err.println(new MaelException("Unknown command for unmark"));
                    } 
                }
                case "delete", "del" -> {
                    if (input.split(" ").length == 2) {
                        try {
                            int taskNum = Integer.parseInt(input.split(" ")[1]);
                            System.out.println("\t" + tasks.get(taskNum - 1));
                            tasks.remove(taskNum - 1);
                            System.out.println("\t\t-Mission Terminated-");
                        } catch (NumberFormatException e) {
                            System.err.println(new MaelException("Termination details unclear"));
                        } catch (IndexOutOfBoundsException e) {
                            System.err.println(new MaelException("Mission unspecified"));
                        }
                    } else {
                        System.err.println(new MaelException("Unknown command for delete"));
                    } 
                }
                default -> {
                    try {
                        tasks.add(Task.of(input));
                        System.out.println("\t>>> " + tasks.get(tasks.size() - 1));
                        System.out.println("\t\t-Mael Acknowleged-");
                    } catch (MaelException e) {
                        System.err.println(e);
                    } 
                }
            }
            line();
            input = SCANNER.nextLine();
        }

        try {
            FileWriter taskWriter = new FileWriter("./data/Mael.txt");
            for (Task task : tasks) {
                taskWriter.write(task.saveString());
            }
            taskWriter.close();
        } catch (IOException e) {
            System.err.println(e);
        }
        close();
    }
}
