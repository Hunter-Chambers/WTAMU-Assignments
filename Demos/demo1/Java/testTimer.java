// To execute this program:
//     javac testTimer.java
//     java testTimer
//
public class testTimer {

    public static void main( String [] args ) {

        Timer myTimer = new Timer();

        System.out.printf("Setting timer for 5 seconds\n");
        myTimer.run_timer(5);

        System.out.printf("Timer ran for %s seconds\n", myTimer );

        System.exit(0);
    }
}
