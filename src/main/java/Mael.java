import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Mael {

    private static final Random rng = new Random();
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

        public static Task of(String text) {
            String[] sections = text.split("/");
            switch (text.split(" ")[0]) {
                case "event":
                    return new Event(sections[0].substring(6), 
                            sections[1].substring(5), 
                            sections[2].substring(3)
                        );
                case "deadline":
                    return new Deadline(sections[0].substring(9), 
                            sections[1].substring(3)
                        );
                case "todo":
                    return new ToDo(sections[0].substring(5));
                default:
                    throw new IllegalArgumentException("Task is not of defined types");
            }

        }

        public void markComplete() {
            this.completed = true;
        }

        public void markIncomplete() {
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

            public ToDo(String title) {
                super(title);
            }

            @Override
            public String toString() {
                return "[T]" + super.toString();
            }

        }

        public static class Deadline extends Task {
            private String deadline;

            public Deadline(String title, String deadline) {
                super(title);
                this.deadline = deadline;
            }

            @Override
            public String toString() {
                return "[D]" + super.toString() + "(Imminent: " + this.deadline + ")";
            }

        }

        public static class Event extends Task {
            private String start;
            private String end;

            public Event(String title, String start, String end) {
                super(title);
                this.start = start;
                this.end = end;
            }

            @Override
            public String toString() {
                return "[E]" + super.toString() + "(alpha: " + this.start + ", delta: " + this.end + ")";
            }

        }
    }

    private static ArrayList<Task> tasks = new ArrayList<>();

    private static void launch() throws InterruptedException  {
        String[] text = new String[] {"Injecting Mael", ".", ".", ".\n",null,"Mael injection complete\n","Awaiting instructions", ".", ".", ".\n"};
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
                    System.out.println("\t\t-Outstanding Missions-");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println("\t" + (i + 1) + "." + tasks.get(i));
                    }
                }
                case "mark" -> {
                    task_num = Integer.parseInt(input.split(" ")[1]);
                    tasks.get(task_num - 1).markComplete();
                    System.out.println("\t" + tasks.get(task_num - 1));
                    System.out.println("\t\t-Mission Completed-");
                }
                case "unmark" -> {
                    task_num = Integer.parseInt(input.split(" ")[1]);
                    tasks.get(task_num - 1).markIncomplete();
                    System.out.println("\t" + tasks.get(task_num - 1));
                    System.out.println("\t\t-Mission Unsuccessful-");
                }
                default -> {
                    tasks.add(Task.of(input));
                    System.out.println("\t>>> " + tasks.get(tasks.size() - 1));
                    System.out.println("\t\t-Mael Acknowleged-");
                }
            }
            line();
            input = scanner.nextLine();
        }
        close();
    }
}
