package mael;

import java.util.Random;
import java.util.Scanner;

class UI {

    /**
     * Default constructor for UI
     * 
     * @param delay Enables delay during display 
     * @param sequences Enables launching and closing animations
     */
    public UI(boolean delay, boolean sequences) {
        this.DELAY = delay;
        this.SEQUENCES = sequences;
    }

    private boolean DELAY = true; // Set to false for no delays
    private boolean SEQUENCES = true; // Set to false for no delays

    private final Random RNG = new Random(100);
    private final Scanner SCANNER = new Scanner(System.in);
    private final String LOGO = 
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
     * Method to display the initial text when running Mael
     * 
     * @throws InterruptedException If sleep is interrupted
     */ 
    public void launch() throws InterruptedException  {
        if (SEQUENCES) {
            String[] text = new String[] {"Injecting Mael", ".", ".", ".\n", null, 
                    "Mael injection complete\n", "Awaiting instructions", ".", ".", ".\n\n"};
            int[] delays = new int[] {400, 400, 400, 800, 1200, 600, 400, 400, 400, 400};

            for (int i = 0; i < text.length; i++) {
                if (i == 4) {
                    lineByLine(LOGO);
                } else {
                    System.out.print(text[i]);
                }
                if (DELAY) {
                    Thread.sleep(delays[i] + RNG.nextInt(0,400) - 200);
                }
            }
        }
    }

    /** 
     * Method to display the final text when ending Mael
     * 
     * @throws InterruptedException If sleep is interrupted
     */ 
    public void close() throws InterruptedException {
        if (SEQUENCES) {
            String[] text = new String[] {"\nWiping Mael", ".", ".", ".\n", null, 
                    "Mael Erased\n", "Like you were never here...\n"};
            int[] delays = new int[] {400, 400, 400, 800, 1200, 600, 1200};

            for (int i = 0; i < text.length; i++) {
                if (i == 4) {
                    lineByLine(LOGO);
                } else {
                    System.out.print(text[i]);
                }
                if (DELAY) {
                    Thread.sleep(delays[i] + RNG.nextInt(0,400) - 200);
                }
            }
        }
    }

    /** 
     * Method to display the line divider text between inputs and outputs of Mael
     */ 
    public void line() {
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
     * @throws InterruptedException If sleep is interrupted
     */
    private void lineByLine(String text) throws InterruptedException {
        String[] lines = text.split("\n");
        for (String line : lines) {
            System.out.println(line);
            if (DELAY) {
                Thread.sleep(50 + RNG.nextInt(0, 100));
            }
        }
    }
}
