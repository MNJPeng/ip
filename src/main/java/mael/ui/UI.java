package mael.ui;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import mael.parser.Parser;

public class UI {

    /**
     * Default constructor for UI
     *
     * @param has_delay Enables delay during display
     * @param has_sequences Enables launching and closing animations
     */
    public UI(boolean has_delay, boolean has_sequences) {
        this.HAS_DELAY = has_delay;
        this.SEQUENCES = has_sequences;
    }

    private final boolean HAS_DELAY; // Set to false for no delays
    private final boolean SEQUENCES; // Set to false for no delays

    private final Random RNG = new Random(100);
    private final Scanner SCANNER = new Scanner(System.in);
    private final String LOGO
            = """
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
     * Displays initial text when running Mael
     *
     * @throws InterruptedException If sleep is interrupted
     */
    public void launch() throws InterruptedException {
        if (SEQUENCES) {
            String[] texts = new String[]{"Injecting Mael", ".", ".", ".\n", null,
                "Mael injection complete\n", "Awaiting instructions", ".", ".", ".\n\n"};
            int[] delays = new int[]{400, 400, 400, 800, 1200, 600, 400, 400, 400, 400};

            for (int i = 0; i < texts.length; i++) {
                if (i == 4) {
                    lineByLine(LOGO);
                } else {
                    System.out.print(texts[i]);
                }
                if (HAS_DELAY) {
                    Thread.sleep(delays[i] + RNG.nextInt(0, 400) - 200);
                }
            }
        }
    }

    /**
     * Displays text when default {@code launch} is interrupted
     */
    public void safeLaunch() {
        System.out.println("\tLaunch Sequence Interrupted..\n\n\tEnabling quick start");
    }

    /**
     * Displays final text when closing Mael
     *
     * @throws InterruptedException If sleep is interrupted
     */
    public void close() throws InterruptedException {
        if (SEQUENCES) {
            String[] texts = new String[]{"\nWiping Mael", ".", ".", ".\n", null,
                "Mael Erased\n", "Like you were never here...\n"};
            int[] delays = new int[]{400, 400, 400, 800, 1200, 600, 1200};

            for (int i = 0; i < texts.length; i++) {
                if (i == 4) {
                    lineByLine(LOGO);
                } else {
                    System.out.print(texts[i]);
                }
                if (HAS_DELAY) {
                    Thread.sleep(delays[i] + RNG.nextInt(0, 400) - 200);
                }
            }
        }
    }

    /**
     * Displays text when default {@code close} is interrupted
     */
    public void safeClose() {
        System.out.println("\tClose Sequence Interrupted..\n\n\tClosing immediately");
    }

    /**
     * Displays line divider text between inputs and outputs of Mael
     */
    public void line() {
        String[] symbols = new String[]{"~", "-", "=", "+", "#"};
        String line = "";
        for (int i = 0; i < 50; i++) {
            line += symbols[RNG.nextInt(0, symbols.length - 1)];
        }
        System.out.println("\n" + line);
    }

    /**
     * Displays text line by line with delays
     *
     * @param text Text to be displayed line by line
     * @throws InterruptedException If sleep is interrupted
     */
    private void lineByLine(String text) throws InterruptedException {
        String[] lines = text.split("\n");
        for (String line : lines) {
            System.out.println(line);
            if (HAS_DELAY) {
                Thread.sleep(50 + RNG.nextInt(0, 100));
            }
        }
    }

    /**
     * Prints {@code Exception} input
     *
     * @param e {@code Exception} to be printed
     */
    public void printException(Exception e) {
        System.out.println(e);
    }

    /**
     * Prints {@code String} input
     *
     * @param texts {@code String} to be printed
     */
    public void printList(List<String> texts) {
        int i = 1;
        for (String text : texts) {
            System.out.println("\t" + i++ + ": " + text);
        }

    }

    /**
     * Returns next user input
     *
     * @return User input
     */
    public String nextLine() {
        return SCANNER.nextLine();
    }

    /**
     * Displays text for add command
     *
     * @param taskString toString of task
     */
    public void printAddHeader(String taskString) {
        System.out.println("\t>>> " + taskString + "\n\t\t-Mael Acknowleged-");
    }

    /**
     * Displays text for list command
     */
    public void printListHeader() {
        System.out.println("\t\t-Outstanding Missions-");
    }

    /**
     * Displays text for mark command
     *
     * @param taskString toString of task
     */
    public void printMarkHeader(String taskString) {
        System.out.println("\t" + taskString + "\n\t\t-Mission Completed-");
    }

    /**
     * Displays text for unmark command
     *
     * @param taskString toString of task
     */
    public void printUnmarkHeader(String taskString) {
        System.out.println("\t" + taskString + "\n\t\t-Mission Unsuccessful-");
    }

    /**
     * Displays text for delete command
     *
     * @param taskString toString of task
     */
    public void printDeleteHeader(String taskString) {
        System.out.println("\t" + taskString + "\n\t\t-Mission Terminated-");
    }

    /**
     * Displays text for check command
     *
     * @param dateBy Date to check
     */
    public void printCheckHeader(LocalDateTime dateBy) {
        System.out.println("\t\t-Missions by " + dateBy.format(Parser.PRINT_FORMAT) + "-");
    }
}
