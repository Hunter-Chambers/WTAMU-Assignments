// FILE:  BagExam.java
// Non-interactive test program for the DoubleArrayBag class
//
// DESCRIPTION:
// Each function tests part of the bag class, returning some
// number of points to indicate how much of the test was passed.
// A description and result of each test is printed to cout.
// Maximum number of points awarded by this program is determined by the
// constants POINTS[1], POINTS[2]...
//
import java.util.Scanner;

public class BagExam {

    public static final int MANY_TESTS = 5;
    public static final int[] POINTS = { 100, // Total points for all tests
                                          32, // Test 1 points
                                          12, // Test 2 points
                                          12, // Test 3 points
                                          32, // Test 4 points
                                          12  // Test 5 points
    };

    public static final String[] DESCRIPTION = {
        "Tests for the DoubleArrayBag Class",
        "Testing insert, size, and occurrences",
        "Testing the copy constructor",
        "Testing the clone and equals methods",
        "Testing erase and erase_one methods",
        "Testing plusEquals and union methods"
    };


    // **************************************************************************
    //  main( )
    //   The main program calls all tests and prints the sum of all points
    //   earned from the tests.
    // **************************************************************************
    public static void main(String[] args) {
        Scanner keyboard = new Scanner( System.in );
        int sum = 0;
        String answer;
        boolean done_erase;
        boolean done_union;
        
        System.out.printf( "Running " + DESCRIPTION[0] + "\n" );
        System.out.printf( "Have you implemented erase and erase_one yet? [Y or N]: " );
        answer = keyboard.next();
        done_erase = (answer.contains("Y") || answer.contains("y"));
        System.out.printf( "Have you implemented plusEquals and union yet? [Y or N]: ");
        answer = keyboard.next();
        done_union = (answer.contains("Y") || answer.contains("y"));

    
        sum += run_a_test(1, DESCRIPTION[1], POINTS[1]);
        sum += run_a_test(2, DESCRIPTION[2], POINTS[2]);
        sum += run_a_test(3, DESCRIPTION[3], POINTS[3]);
        if (done_erase)
            sum += run_a_test(4, DESCRIPTION[4], POINTS[4]);
        if (done_union)
            sum += run_a_test(5, DESCRIPTION[5], POINTS[5]);
    
        System.out.printf( "If you submit your bag to Prof. Haiduk now, you will have\n" );
        System.out.printf( "%d points out of the ", sum );
        System.out.printf( "%d", POINTS[0] );
        System.out.printf( " points from this test program.\n" );
    }

    // **************************************************************************
    // boolean correct(DoubleArrayBag test, int n)
    //   This function determines if the bag (test) is "correct" according to
    //   this requirement:
    //   a. it has exactly n items.
    // **************************************************************************
    public static boolean correct(DoubleArrayBag test, int n) {
        int i;
        boolean answer = true;
        if (test.size( ) != n)
            answer = false;
        System.out.print( (answer ? "Test passed.\n\n" : "Test failed.\n\n") );
        return answer;
    }

    public static int run_a_test(int number, String message, int max) {
        int result = 0;
        
        System.out.printf( "\n START OF TEST " + number + ":\n" );
        System.out.printf(  message + " (" + max + " points).\n");
        switch ( number ) {
            case 1 : result = test1(); break;
            case 2 : result = test2(); break;
            case 3 : result = test3(); break;
            case 4 : result = test4(); break;
            case 5 : result = test5(); break;
            default : System.out.printf("Something foobarred\n");
        }
        if (result > 0) {
            System.out.printf("Test " + number + " got " + result + " points" );
            System.out.printf(" out of a possible " + max + ".\n" );
        }
        else
            System.out.printf( "Test " + number + " failed.\n" );
        System.out.printf("END OF TEST " + number + ".\n\n" );
        
        return result;
    }
    

    // **************************************************************************
    // int test1( )
    //   Performs some basic tests of insert, size and occurrences
    //   functions. Returns POINTS[1] if the tests are passed. Otherwise returns 0.
    // **************************************************************************
    public static int test1( ) {
        final int TESTSIZE = 3000;
        DoubleArrayBag test = new DoubleArrayBag();
        char test_letter = 'A';
        int i;
        int next;
    
        System.out.printf( test_letter++ + ". Testing size for an empty bag.\n");
        if (!correct(test, 0)) return 0;
    
        System.out.printf( test_letter++ + ". Adding the number 4 to the bag, and then testing\n");
        System.out.printf("   size.\n");
        test.insert(4);
        if (!correct(test, 1)) return 0;
    
        System.out.printf( test_letter++ + ". Inserting the number 2 into the bag.\n");
        System.out.printf("   Then checking size.\n");
        test.insert(2);
        if (!correct(test, 2)) return 0;
    
        System.out.printf( test_letter++ + ". Inserting the number 1 into the bag.\n");
        System.out.printf( "   Then checking size.\n");
        test.insert(1);
        if (!correct(test, 3)) return 0;
    
        System.out.printf( test_letter++ + ". Inserting the number 3 into the bag.\n");
        System.out.printf( "   Then checking size.\n"); 
        test.insert(3);
        if (!correct(test, 4)) return 0;
    
        System.out.printf( test_letter++ + ". Inserting another 2 into the bag.\n");
        System.out.printf("   Then checking size.\n");
        test.insert(2);
        if (!correct(test, 5)) return 0;
        System.out.printf( "   Then checking occurrences of 2.\n");
        if (test.occurrences(2) != 2) return 0;
        else System.out.printf("Test passed.\n\n");
    
        System.out.printf( test_letter++ + ". Inserting the numbers 5, 6, and 7 into the bag.\n");
        System.out.printf( "   Then checking size.\n");
        test.insert(5);
        test.insert(6);
        test.insert(7);
        if (!correct(test, 8)) return 0;
    
        System.out.printf( test_letter++ + ". Inserting two more 2's into the bag.\n");
        System.out.printf( "   and then checking occurrences of 2's\n");
        test.insert(2); test.insert(2);
        if ( test.occurrences(2) != 4) return 0;
        else System.out.printf( "Test passed.\n\n" );
    
        System.out.printf( test_letter++ + ". Inserting " + TESTSIZE + " random items between 0 and 49\n");
        System.out.printf( "   and then checking size.\n" );
        for (i = 0; i < TESTSIZE; i++) {
            next = (int) Math.random( ) % 50;
            test.insert(next);
        }
        if (!correct(test, TESTSIZE+10)) return 0;
    
        return POINTS[1];
    }

    // **************************************************************************
    // int test2( )
    //   Performs some tests of the copy constructor and equals method.
    //   Returns POINTS[2] if the tests are passed. Otherwise returns 0.
    // **************************************************************************
    public static int test2( ) {
        DoubleArrayBag test = new DoubleArrayBag();
    
        System.out.printf("A. Testing that copy constructor works okay for empty bag...\n");
        DoubleArrayBag copy1 = new DoubleArrayBag(test);
        if (!correct(copy1, 0)) return 0;
    
        System.out.printf("B. Testing copy constructor with 4-item bag...\n");
        test.insert(1);
        test.insert(1);
        test.insert(1);
        test.insert(1);
        DoubleArrayBag copy2 = new DoubleArrayBag(test);
        System.out.printf("   and now testing equals method...\n");
        if ( !test.equals(copy2) || !copy2.equals(test) ) {
            System.out.printf("Test failed.\n\n");
            return 0;
        }
        else System.out.printf("Test passed.\n\n");
        test.insert(1); // Alter the original, but not the copy
        System.out.printf("   then checking size of the copy\n");
        if (!correct(copy2, 4)) return 0;
        System.out.printf("   then checking size of the original after inserting another item\n");
        if (!correct(test, 5)) return 0;
    
        System.out.printf("Copy constructor seems okay.\n");
        return POINTS[2];
    }
    

    // **************************************************************************
    // int test3( )
    //   Performs some tests of the clone and equals methods.
    //   Returns POINTS[3] if the tests are passed. Otherwise returns 0.
    // **************************************************************************
    public static int test3( ) {
        DoubleArrayBag test = new DoubleArrayBag();
    
        System.out.printf("A. Testing that clone works okay for empty bag...\n");
        DoubleArrayBag copy1 = new DoubleArrayBag();
        copy1.insert(1);
        copy1 = test.clone();
        if (!correct(copy1, 0)) return 0;
    
        System.out.printf("B. Testing clone method with 4-item bag...\n");
        test.insert(1);
        test.insert(1);
        test.insert(1);
        test.insert(1);
        DoubleArrayBag copy2 = test.clone();
        test.insert(1); // Alter the original, but not the copy
        System.out.printf("   Adding another item to the original and testing occurrences...\n");
        if (test.occurrences(1) != 5 || copy2.occurrences(1) != 4) {
            System.out.printf("Test failed.\n");
            return 0;
        }
        System.out.printf("   And now testing sizes of copy and altered original\n");
        if (!correct(copy2, 4)) return 0;
        if (!correct(test, 5)) return 0;
    
        System.out.printf("C. Testing clone for equality...\n");
        copy2 = test.clone();
        if (!copy2.equals(test)) {
            System.out.printf("Test failed.\n\n"); return 0; }
        if (!test.equals(copy2)) {
            System.out.printf("Test failed.\n\n"); return 0; }
        System.out.printf("Test passed.\n\n");
    
        System.out.printf("Clone and equals method seems okay.\n");
        return POINTS[3];
    }

    // **************************************************************************
    // int test4( )
    //   Performs basic tests for the erase functions
    //   Returns POINTS[4] if the tests are passed.
    //   Otherwise returns 0.
    // **************************************************************************
    public static int test4( ) {
        DoubleArrayBag test = new DoubleArrayBag();
    
        System.out.printf("Testing erase from empty bag (should have no effect) ...\n");
        test.erase(0);
        if (!correct(test, 0)) return 0;
            
        System.out.printf("Inserting these: 8 6 10 1 7 10 15 3 13 2 5 11 14 4 12\n");
        test.insert( 8);
        test.insert( 6);
        test.insert(10);
        test.insert( 1);
        test.insert( 7);
        test.insert(10);
        test.insert(15);
        test.insert( 3);
        test.insert(13);
        test.insert( 2);
        test.insert( 5);
        test.insert(11);
        test.insert(14);
        test.insert( 4);
        test.insert(12);
        if (!correct(test, 15)) return 0;

        System.out.printf("Now testing capacity -- should be 16\n");
        if ( test.getCapacity() != 16 ) {
            System.out.printf("Test failed.\n\n");
            System.out.printf("%s\n", test);
            return 0;
        }
        else System.out.printf("Test passed.\n\n");
    
        System.out.printf("Erasing 0 (which is not in bag, so bag should be unchanged) ...\n");
        if (test.erase_one(0)) {
            System.out.printf("Test failed.\n");
            return 0;
        }
        if (!correct(test, 15)) return 0;
    
        System.out.printf("Erasing the 6 ...\n");
        test.erase(6);
        if (!correct(test, 14)) return 0;
    
        System.out.printf("Erasing one 10 ...\n");
        if (!test.erase_one(10)) {
            System.out.printf("Test failed\n");
            return 0;
        }
        if (!correct(test, 13)) return 0;
    
        System.out.printf("Erasing the 1 ...\n");
        test.erase(1);
        if (!correct(test, 12)) return 0;
    
        System.out.printf("Erasing the 15 ...\n");
        test.erase(15);
        if (!correct(test, 11)) return 0;
    
        System.out.printf("Erasing the 5 ...\n");
        test.erase(5);
        if (!correct(test, 10)) return 0;
    
        System.out.printf("Erasing the 11 ...\n");
        test.erase(11);
        if (!correct(test, 9)) return 0;
    
        System.out.printf("Erasing the 3 ...\n");
        test.erase(3);
        if (!correct(test, 8)) return 0;
    
        System.out.printf("Erasing the 13 ...\n");
        test.erase(13);
        if (!correct(test, 7)) return 0;
    
        System.out.printf("Erasing the 2 ...\n");
        test.erase(2);
        if (!correct(test, 6)) return 0;
    
        System.out.printf("Erasing the 14 ...\n");
        test.erase_one(14);
        if (!correct(test, 5)) return 0;
    
        System.out.printf("Erasing the 4 ...\n");
        test.erase(4);
        if (!correct(test, 4)) return 0;
    
        System.out.printf("Erasing the 12 ...\n");
        test.erase(12);
        if (!correct(test, 3)) return 0;
    
        System.out.printf("Erasing the 8 ...\n");
        test.erase(8);
        if (!correct(test, 2)) return 0;
    
        System.out.printf("Erasing the 7 ...\n");
        test.erase(7);
        if (!correct(test, 1)) return 0;
    
        System.out.printf("Erasing the other 10 ...\n");
        if ( !test.erase_one(10) ) {
            System.out.printf("Test failed ...\n");
            return 0;
        }
        if (!correct(test, 0)) return 0;

        System.out.printf("Testing capacity again...");
        if ( test.getCapacity() != 16 ) {
            System.out.printf("Test failed.\n\n");
            return 0;
        }
        System.out.printf("Test passed.\n\n");

        System.out.printf("Now trimming to size\n");
        test.trimToSize();
        if ( test.getCapacity() != 1 ) {
            System.out.printf("Test failed.\n");
            System.out.printf("%s\n", test);
            return 0;
        }
        System.out.printf("Test passed.\n\n");
    
        System.out.printf("Now trimming to size again\n");
        test.trimToSize();
        if ( test.getCapacity() != 1 ) {
            System.out.printf("Test failed.\n");
            System.out.printf("%s\n", test);
            return 0;
        }
        System.out.printf("Test passed.\n\n");

        System.out.printf("Inserting value 5000 into the bag ... \n");
        System.out.printf("Inserting three 5's into the bag and then erasing all of them ...\n");
        test.insert(5000);
        test.insert(5); test.insert(5); test.insert(5);
        test.erase(5);
        if (!correct(test,1)) return 0;
        
        System.out.printf("Erase methods seem okay.\n");
        return POINTS[4];
    }

    // **************************************************************************
    // int test5( )
    //   Performs basic tests for the plusEquals and union methods
    //   Returns POINTS[5] if the tests are passed.
    //   Otherwise returns 0.
    // **************************************************************************
    public static int test5() {

        DoubleArrayBag test1, test2, test3;
        test1 = new DoubleArrayBag(); test2 = new DoubleArrayBag();
        test3 = new DoubleArrayBag();

        System.out.printf("Inserting 2000 1's into test1 and 2000 2's into test2\n");
        for (int i=0; i < 2000; ++i) {
            test1.insert(1);
            test2.insert(2);
        }
        System.out.printf("Now testing the plusEqual method ...\n");
        test1.plusEquals(test2);
        System.out.printf("  and now testing for occurrences of 1's and 2's in test1\n");
        if ( test1.occurrences(1) == 2000 && test1.occurrences(2) == 2000 ) {
            System.out.printf("Test passed.\n\n");
        } else {
            System.out.printf("Test failed.\n\n");
            return 0;
        }

        System.out.printf("Now testing the union method ...\n");
        test3 = DoubleArrayBag.union(test2, test2);
        System.out.printf("  and now testing for occurrences of 2's in test3\n");
        if ( test3.occurrences(2) == 4000 ) {
            System.out.printf("Test passed.\n\n");
        } else {
            System.out.printf("Test failed.\n\n");
            return 0;
        }

        System.out.printf("\nplusEquals and union methods seem okay.\n");
        return POINTS[5];
    }

}
