import java.util.Scanner;

public class SequenceTest {

    public static Scanner si = new Scanner( System.in ); //tie scanner to stdin

    public static void main( String [] args ) {

        DLinkedSequence<Long> test1, test2, test3;
        test1 = new DLinkedSequence<Long>();
        test2 = new DLinkedSequence<Long>();
        test3 = new DLinkedSequence<Long>();

        char choice = ' ';
        long number = 0L;

        System.out.printf("\033c"); //clear screen
        System.out.printf("I have initialized three empty sequences of numbers: test1, test2, and test3.\n");
        pressEnterToContinue();

        do {
            System.out.printf("\033c"); //clear screen
            print_menu();
            choice = get_user_command();

            switch (choice) {
                case 's' : test1.begin();
                           break;
                case 'a' : if ( test1.isCurrent() ) 
                               test1.advance();
                           else {
                               System.out.printf("Cannot advance since cursor is not current\n");
                               pressEnterToContinue();
                           }
                           break;
                case 'r' : if ( test1.isCurrent() ) 
                               test1.retreat();
                           else {
                               System.out.printf("Cannot retreat since cursor is not current\n");
                               pressEnterToContinue();
                           }
                           break;
                case '?' : if ( test1.isCurrent() )
                               System.out.printf("There is a current item\n");
                           else 
                               System.out.printf("There is no current item\n");
                           pressEnterToContinue();
                           break;
                case 'g' : if ( test1.isCurrent() )
                               System.out.printf("Current item is: %d\n", test1.getCurrent());
                           else
                               System.out.printf("There is no current element\n");
                           pressEnterToContinue();
                           break;
                case '1' : showSequence( test1 );
                           pressEnterToContinue();
                           break;
                case '2' : showSequence( test2 );
                           pressEnterToContinue();
                           break;
                case '3' : showSequence( test3 );
                           pressEnterToContinue();
                           break;
                case 'S' : System.out.printf("Size of test1 is: %d\n", test1.size());
                           pressEnterToContinue();
                           break;
                case 'I' : test1.addBefore( get_long_number() );
                           break;
                case 'A' : test1.addAfter( get_long_number() );
                           break;
                case 'R' : if ( test1.isCurrent() ) {
                               test1.removeCurrent();
                               System.out.printf("test1's current item has been removed\n");
                           } 
                           else System.out.printf("test1's cursor not current -- not removed\n");
                           pressEnterToContinue();
                           break;
                case '+' : showSequence( test1.concatenation( test3 ) );
                           pressEnterToContinue();
                           break;
                case 'e' : System.out.printf("test1.equals(test3)? %s\n", test1.equals(test3) );
                           pressEnterToContinue();
                           break;
                case 'C' : test3 = new DLinkedSequence<Long>( test1 );
                           break;
                case 'c' : test3 = test1.clone();
                           break;
                case 'q' :
                case 'Q' : System.out.printf("\t\tRidicule is the best test of truth\n");
                           pressEnterToContinue();
                           break;
                default  : System.out.printf("\t\t%c is invalid . . . \n", choice);
                           pressEnterToContinue();
                           break;
            }
        } while ( (choice != 'Q') && (choice != 'q') );

    }

    public static void print_menu( ) {
        System.out.printf("\n\n\t\tThe following choices are available: \n");
        System.out.printf("\t\t s  Activate the start() method for test1\n");
        System.out.printf("\t\t a  Activate the advance() method for test1\n");
        System.out.printf("\t\t r  Activate the retreat() method for test1\n");
        System.out.printf("\t\t ?  Display the result from the isCurrent() for test1\n");
        System.out.printf("\t\t g  Display the result from the getCurrent() for test1\n");
        System.out.printf("\t\t 1  Display test1\n");
        System.out.printf("\t\t 2  Display test2\n");
        System.out.printf("\t\t 3  Display test3\n");
        System.out.printf("\t\t S  Display the result for the size() method for test1\n");
        System.out.printf("\t\t I  Add a new number to test1 with addBefore()\n");
        System.out.printf("\t\t A  Add a new number to test1 with addAfter()\n");
        System.out.printf("\t\t R  Remove current value from test1 with removeCurrent()\n");
        System.out.printf("\t\t +  Display the concatenation of test1 and test3\n");
        System.out.printf("\t\t e  Check whether test1.equals(test3)\n");
        System.out.printf("\t\t C  Use copy constructor test3 = new DLinkedSequence<Long>(test1)\n");
        System.out.printf("\t\t c  Use clone of test1 by test3 = test1.clone()\n");
        System.out.printf("\t\t Q  Quit this test program\n");
    }

    public static void pressEnterToContinue() {
        System.out.printf("\t\tPress Enter to continue . . .");
        try {
            System.in.read();
        } catch(Exception e) { }
    }

    public static void showSequence( DLinkedSequence<Long> seq ) {
        System.out.printf("%s\n", seq);
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

    public static long get_long_number( ) {
        long result;
        System.out.printf("\tPlease enter a long number: ");
        result = si.nextLong();
        return result;
    }

    public static int get_int_number( ) {
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

