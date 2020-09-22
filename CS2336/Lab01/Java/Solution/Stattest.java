import java.util.Scanner;

public class Stattest {

    public static Scanner si = new Scanner( System.in ); //tie Scanner to keyboard

    public static void main( String[] args ) {
        Statistician s1, s2, s3;
        s1 = new Statistician(); s2 = new Statistician(); s3 = new Statistician();
        char choice = ' ';
        double x = 0.0;

        System.out.printf("\033c"); //clear screen
        do {
            System.out.println();
            print_menu( );
            choice = toupper( get_user_command( ) );
            switch (choice) {
                case 'R'  :  System.out.printf("\tWhich one should I reset ( 1, 2, 3 )?\n");
                             choice = get_user_command( );
                             switch (choice) {
                                 case '1' : s1.reset( ); break;
                                 case '2' : s2.reset( ); break;
                                 case '3' : s3.reset( ); break;
                                 default  : System.out.printf("\tTry again -- bad choice\n");
                                            //System.out.printf("\033c"); //clear screen
                                            continue;
                              }
                              System.out.printf("\tReset activated for s" + choice + "\n");
                              break;
                case '1'   :  s1.nextNumber( get_number( ) ); break;
                case '2'   :  s2.nextNumber( get_number( ) ); break;
                case '3'   :  s3.nextNumber( get_number( ) ); break;
                case 'C'   :  s3 = new Statistician(s1);
                              System.out.printf("\tStatistician3 set to a copy of Statistician1\n");
                              break;
                case 'D'   :  System.out.printf("\ts1 =  %s\n", s1);
                              System.out.printf("\ts2 =  %s\n", s2);
                              System.out.printf("\ts3 =  %s\n", s3);
                              break;
                case 'E'   :  if ( s1.equals(s2) )
                                  System.out.printf("\ts1 and s2 are equal\n");
                              else
                                  System.out.printf("\ts1 and s2 are not equal\n");
                              break;
                case '+'   :  s3 = Statistician.union(s1, s2);
                              System.out.printf("\ts3 has been set to union(s1, s2)\n");
                              break;
                case '*'   :  System.out.printf("\tPlease enter a value for x: ");
                              x = si.nextDouble();
                              s3 = Statistician.scale( x, s1 );
                              System.out.printf("\ts3 has been set to scale( x, s1 )\n");
                              break;
                case 'Q'   :  System.out.printf("\tRidicule is the best test of truth . . . \n");
                              break;
                default    :  System.out.printf("\t%s is invalid . . .  sorry, try again\n", choice);
                              //System.out.printf("\033c"); //clear screen
            } 
        } while ( ( choice != 'Q' ) );
    }


    public static void print_menu( ) {
        System.out.printf("\n");
        System.out.printf("\tThe following choices are available: \n");
        System.out.printf("\t 1  Add a new number to the 1st Statistician s1\n");
        System.out.printf("\t 2  Add a new number to the 2nd Statistician s2\n");
        System.out.printf("\t 3  Add a new number to the 3rd Statistician s3\n");
        System.out.printf("\t C  Set Statistician 3 to a copy of Statistician 1\n");
        System.out.printf("\t D  Display the state of the statisticians s1, s2 and s3\n");
        System.out.printf("\t E  Test whether s1.equals(s2)\n");
        System.out.printf("\t R  Activate one of the reset( ) functions\n");
        System.out.printf("\t +  Set Statistician s3 equal to union( s1, s2 )\n");
        System.out.printf("\t *  Set Statistician s3 equal to scale( x, s1 )\n");
        System.out.printf("\t Q  Quit this test program\n");
    }

    public static char get_user_command( ) {
        char command;

        System.out.printf("\tEnter choice: ");
        command = si.next( ).charAt(0);
        return command;
    }

    public static double get_number( ) {
        double result;

        System.out.printf("\tPlease enter the next real number for the sequence: ");
        result = si.nextDouble();
        return result;
    }

    public static char toupper( char aChar ) {
        if ( aChar >= 'a' && aChar <= 'z' ) {
            int temp = (int)aChar ^ 0x20;
            aChar = (char)temp;
        }
        return aChar;
    }

}
