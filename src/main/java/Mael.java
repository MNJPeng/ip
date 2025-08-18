
import java.util.Random;


public class Mael {

    private static Random rng = new Random();
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


    private static void launch() throws InterruptedException  {
        String[] text = new String[] {"Loading Mael", ".", ".", ".\n",null,"Mael loaded\n","Awaiting instructions", ".", ".", ".\n"};
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

    private static void line_by_line(String text) throws InterruptedException {
        String[] lines = text.split("\n");
        for (String line : lines) {
            System.out.println(line);
            Thread.sleep(50 + rng.nextInt(0, 100));
        }
    }
    public static void main(String[] args) throws InterruptedException {
        launch();
    }
}
