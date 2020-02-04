import java.util.Scanner;

public class BagTest {

    public static Scanner si = new Scanner( System.in ); //tie Scanner to keyboard

    public static void main( String [] args ) {

        DoubleArrayBag bag1, bag2;
        bag1 = new DoubleArrayBag(); bag2 = new DoubleArrayBag();
        char choice = ' ';
        double number = 0.0;

        do {
            System.out.printf("\033c"); //clear screen
            System.out.printf("\n");
            print_menu();
            choice = get_user_command();

            switch (choice) {
                case 'c' : bag1 = new DoubleArrayBag(get_integer_number());
                           break;
                case 'I' : bag1.insert(get_double_number());
                           break;
                case 'i' : bag2.insert(get_double_number());
                           break;
                case 'X' : bag1.erase_one(get_double_number());
                           break;
                case 'x' : bag2.erase_one(get_double_number());
                           break;
                case 'R' : bag1.erase(get_double_number());
                           break;
                case 'r' : bag2.erase(get_double_number());
                           break;
                case 'L' : bag2 = bag1.clone();
                           break;
                case 'C' : bag2 = new DoubleArrayBag(bag1);
                           break;
                case 'e' : System.out.printf("bag1.equals(bag2)? %s\n",
                                             bag1.equals(bag2) );
                           pressEnterToContinue();
                           break;
                case 'n' : System.out.printf("!bag1.equals(bag2)? %s\n",
                                             !bag1.equals(bag2) );
                           pressEnterToContinue();
                           break;
                case 'E' : bag1.ensureCapacity(get_integer_number());
                           break;
                case 'T' : bag1.trimToSize();
                           break;
                case 'P' : bag1.plusEquals(bag2);
                           break;
                case 'd' :
                case 'D' : System.out.printf("bag1 is: \n%s\n", bag1);
                           System.out.printf("bag2 is: \n%s\n", bag2);
                           pressEnterToContinue();
                           break;
                case 'U' : System.out.printf("The union of bag1 and bag2 is: \n%s\n",
                                             DoubleArrayBag.union(bag1, bag2) );
                           pressEnterToContinue();
                           break;
                case 'Q' :
                case 'q' : System.out.printf("\tRidicule is the best test of truth . . . \n");
                           break;
                default  : System.out.printf("\t\tInvalid choice . . . try again\n");
                           pressEnterToContinue();
            }
        } while ( (choice != 'Q') && (choice != 'q') );
    }

    public static void print_menu( ) {
        System.out.printf("\n");
        System.out.printf("\n\nThe following choices are available with 2 bags: \n");
        System.out.printf("\t\t c  Set bag1 to a new bag with user-defined capacity\n");
        System.out.printf("\t\t I  Insert an item into bag1\n");
        System.out.printf("\t\t i  Insert an item into bag2\n");
        System.out.printf("\t\t X  Erase one of item from bag1\n");
        System.out.printf("\t\t x  Erase one of item from bag2\n");
        System.out.printf("\t\t R  Erase all of item from bag1\n");
        System.out.printf("\t\t r  Erase all of item from bag2\n");
        System.out.printf("\t\t L  Use clone() to make bag2 equal to bag1\n");
        System.out.printf("\t\t C  Use copy constructor to make bag2 equal to bag1\n");
        System.out.printf("\t\t e  Test if bag1 equal to bag2 using equals()\n");
        System.out.printf("\t\t n  Test if bag1 not equal to bag2 using equals()\n");
        System.out.printf("\t\t E  Ensure capacity for bag1\n");
        System.out.printf("\t\t T  Trim to size for bag1\n");
        System.out.printf("\t\t P  Use plusEquals to make bag1 += bag2\n");
        System.out.printf("\t\t D  Display both bags\n");
        System.out.printf("\t\t U  Display the union of bag1 and bag2\n");
        System.out.printf("\t\t Q  Quit this test program\n");
    }

    public static void pressEnterToContinue() {
        System.out.printf("\t\tPress Enter to continue . . .");
        try {
            System.in.read();
        } catch(Exception e) { }
    }

    public static char get_user_command( ) {
        char command = ' ';

        System.out.printf("\tEnter choice: ");
        try {
            if ( si.hasNext() ) 
                command = si.next( ).charAt(0);
            else
                command = (char) System.in.read();
        }
        catch ( Exception e ) {}
        return command;
    }

    public static double get_double_number( ) {
        double result;

        System.out.printf("\tPlease enter a double number: ");
        result = si.nextDouble();
        return result;
    }

    public static int get_integer_number( ) {
        int result;
        System.out.printf("\tPlease enter an integer number: ");
        result = si.nextInt();
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
