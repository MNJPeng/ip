import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Mael {

    private static final Random rng = new Random(100);
    private static final Scanner scanner = new Scanner(System.in);
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

    private abstract static class Task {
        private final String title;
        private boolean completed;

        private Task(String title) {
            this.title = title;
        }

        public static Task of(String text) throws MaelException {
            String[] sections = text.split("/");
            switch (text.split(" ")[0]) {
                case "event" -> {
                    if (text.split(" ").length == 1) {
                        throw new MaelException("Event activity unspecified");
                    } else if (sections.length != 3) {
                        throw new MaelException("Event details unclear");
                    } else if (!sections[1].substring(0, 4).equals("from") || !sections[2].substring(0, 2).equals("to")) {
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

        public void markComplete() throws MaelException {
            if (this.completed) {
                throw new MaelException("Mission already complete");
            }
            this.completed = true;
        }

        public void markIncomplete() throws MaelException {
            if (!this.completed) {
                throw new MaelException("Mission not yet complete");
            }
            this.completed = false;
        }

        private String getComplete() {
            return this.completed ? "X" : " ";
        }

        @Override
        public String toString() {
            return "[" + this.getComplete() + "] " + this.title;
        }

        public static class ToDo extends Task {

            public ToDo(String[] sections) {
                super(sections[0].substring(5));
            }

            @Override
            public String toString() {
                return "[T]" + super.toString();
            }

        }

        public static class Deadline extends Task {
            private String deadline;

            public Deadline(String[] sections) {
                super(sections[0].substring(9));
                this.deadline = sections[1].substring(3);
            }

            @Override
            public String toString() {
                return "[D]" + super.toString() + "(Imminent: " + this.deadline + ")";
            }

        }

        public static class Event extends Task {
            private String start;
            private String end;

            public Event(String[] sections) {
                super(sections[0].substring(6));
                this.start = sections[1].substring(5);
                this.end = sections[2].substring(3);
            }

            @Override
            public String toString() {
                return "[E]" + super.toString() + "(alpha: " + this.start + ", delta: " + this.end + ")";
            }

        }
    }

    private static ArrayList<Task> tasks = new ArrayList<>();

    private static class MaelException extends Exception {

        public MaelException(String message) {
            super(message);
        }

        @Override
        public String toString() {
            return "\tMael encountered a critical error...\n\t\t" + super.getMessage() + "\n\n\t\t-Terminated request-";
        }
        
    }

    private static void launch() throws InterruptedException  {
        String[] text = new String[] {"Injecting Mael", ".", ".", ".\n",null,"Mael injection complete\n","Awaiting instructions", ".", ".", ".\n\n"};
        int[] delays = new int[] {400, 400, 400, 800, 1200, 600, 400, 400, 400, 400};

        for (int i = 0; i < text.length; i++) {
            if (i == 4) {
                Mael.line_by_line(LOGO);
            } else {
                System.out.print(text[i]);
            }
            Thread.sleep(delays[i] + rng.nextInt(0,400) - 200);
        }
        
    }

    private static void close() throws InterruptedException {
        String[] text = new String[] {"\nWiping Mael", ".", ".", ".\n",null,"Mael Erased\n","Like you were never here...\n"};
        int[] delays = new int[] {400, 400, 400, 800, 1200, 600, 1200};

        for (int i = 0; i < text.length; i++) {
            if (i == 4) {
                Mael.line_by_line(LOGO);
            } else {
                System.out.print(text[i]);
            }
            Thread.sleep(delays[i] + rng.nextInt(0,400) - 200);
        }
    }

    private static void line() {
        String[] symbols = new String[] {"~", "-", "=", "+", "#"};
        String line = "";
        for (int i = 0; i < 50; i++) {
            line += symbols[rng.nextInt(0, symbols.length - 1)];
        }
        System.out.println("\n" + line);
    }

    private static void line_by_line(String text) throws InterruptedException {
        String[] lines = text.split("\n");
        for (String line : lines) {
            System.out.println(line);
            Thread.sleep(50 + rng.nextInt(0, 100));
        }
    }
    public static void main(String[] args) throws InterruptedException {
        launch();
        String input = scanner.nextLine();
        int task_num;
        while (!input.equals("bye")) {
            line();
            switch (input.split(" ")[0]) {
                case "list", "ls" -> {
                    if (input.split(" ").length == 1) {
                        System.out.println("\t\t-Outstanding Missions-");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println("\t" + (i + 1) + "." + tasks.get(i));
                        }
                    } else {
                        System.out.println(new MaelException("Unknown command for list"));
                    }
                }
                case "mark", "m" -> {
                    try {
                        task_num = Integer.parseInt(input.split(" ")[1]);
                        tasks.get(task_num - 1).markComplete();
                        System.out.println("\t" + tasks.get(task_num - 1));
                        System.out.println("\t\t-Mission Completed-");
                    } catch (NumberFormatException e) {
                        System.out.println(new MaelException("Mark details unclear"));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(new MaelException("Mission unspecified"));
                    } catch (MaelException e) {
                        System.out.println(e);
                    }
                }
                case "unmark", "um" -> {
                    try {
                        task_num = Integer.parseInt(input.split(" ")[1]);
                        tasks.get(task_num - 1).markIncomplete();
                        System.out.println("\t" + tasks.get(task_num - 1));
                        System.out.println("\t\t-Mission Unsuccessful-");
                    } catch (NumberFormatException e) {
                        System.out.println(new MaelException("Unmark details unclear"));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(new MaelException("Mission unspecified"));
                    } catch (MaelException e) {
                        System.out.println(e);
                    }
                    
                }
                default -> {
                    try {
                        tasks.add(Task.of(input));
                        System.out.println("\t>>> " + tasks.get(tasks.size() - 1));
                        System.out.println("\t\t-Mael Acknowleged-");
                    } catch (MaelException e) {
                        System.out.println(e);
                    } 
                }
            }
            line();
            input = scanner.nextLine();
        }
        close();
    }
}
