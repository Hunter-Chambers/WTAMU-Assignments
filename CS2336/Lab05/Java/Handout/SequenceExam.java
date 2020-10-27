import java.util.Scanner;
//FILE:  SequenceExam.java
//Written by:  H. Paul Haiduk
//Non-interactive test program for the SLinkedSequence<Long> class
//
// DESCRIPTION:
// Each function of this program tests part of the sequence class, returning
// some number of points to indicate how much of the test was passed.
// A description and result of each test is printed to stdout.
// Maximum number of points awarded by this program is determined by the
// constants POINTS[1], POINTS[2]...

public class SequenceExam {

    public static final int MANY_TESTS = 8;
    public static final int[] POINTS = { 300, // Total points for all tests
                                          50, // Test 1 points
                                          50, // Test 2 points
                                          50, // Test 3 points
                                          30, // Test 4 points
                                          30, // Test 5 points
                                          30, // Test 6 points
                                          30, // Test 7 points
                                          30  // Test 8 points
    };

    public static final String[] DESCRIPTION = {
        "Tests for the SLinkedSequence<Long> class implemented with singly-linked list",
         "Testing addBefore, addAfter, and the non-mutating methods",
         "Testing situations where the cursor goes off the sequence",
         "Testing removeCurrent",
         "Testing the copy constructor",
         "Testing the clone method",
         "Testing addBefore/addAfter for somewhat larger sequence",
         "Testing equals method",
         "Testing concatenation method"
    };


    // **************************************************************************
    //  main( )
    //   The main program calls all tests and prints the sum of all points
    //   earned from the tests.
    // **************************************************************************
    public static void main( String [] args ) {
        int sum = 0;

        System.out.printf( "Running " + DESCRIPTION[0] + "\n" );
        sum += run_a_test(1, DESCRIPTION[1], POINTS[1]);
        sum += run_a_test(2, DESCRIPTION[2], POINTS[2]);
        sum += run_a_test(3, DESCRIPTION[3], POINTS[3]);
        sum += run_a_test(4, DESCRIPTION[4], POINTS[4]);
        sum += run_a_test(5, DESCRIPTION[5], POINTS[5]);
        sum += run_a_test(6, DESCRIPTION[6], POINTS[6]);
        sum += run_a_test(7, DESCRIPTION[7], POINTS[7]);
        sum += run_a_test(8, DESCRIPTION[8], POINTS[8]);
    
        System.out.printf( "If you submit your sequence to Prof. Haiduk now, you will have\n" );
        System.out.printf( "%d points out of the ", sum );
        System.out.printf( "%d", POINTS[0] );
        System.out.printf( " points from this test program.\n" );

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
            case 6 : result = test6(); break;
            case 7 : result = test7(); break;
            case 8 : result = test8(); break;
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
    // boolean test_basic(SLinkedSequence<Long> test, int s, boolean has_cursor)
    //   Postcondition: A return value of true indicates:
    //     a. test.size() is s, and
    //     b. test.isCurrent() is has_cursor.
    //   Otherwise the return value is false.
    //   In either case, a description of the test result is printed to cout.
    // **************************************************************************
    public static boolean test_basic(SLinkedSequence<Long> test, int s, boolean has_cursor) {
        boolean answer;
    
        System.out.printf("Testing that size() returns %d\n", s );
        answer = (test.size( ) == s);
        System.out.printf("%s\n", answer ? "Passed." : "Failed.");
        
        if (answer) {
            System.out.printf("%s", "Testing that isCurrent() returns ");
            System.out.printf("%s\n", has_cursor ? "true" : "false");
            answer = (test.isCurrent( ) == has_cursor);
            System.out.printf("%s\n", answer ? "Passed." : "Failed.");
        }
    
        return answer;
    }

    // **************************************************************************
    // boolean test_items(SLinkedSequence<Long> test, int s, int i, long items[])
    //   The function determines if the test sequence has the correct items
    //   Precondition: The size of the items array is at least s.
    //   Postcondition: A return value of true indicates that test.getCurrent()
    //   is equal to items[i], and after test.advance() the result of
    //   test.getCurrent() is items[i+1], and so on through items[s-1].
    //   At this point, one more advance takes the cursor off the sequence.
    //   If any of this fails, the return value is false.
    //   NOTE: The test sequence has been changed by advancing its cursor.
    // **************************************************************************
    public static boolean test_items(SLinkedSequence<Long> test, int s, int i, long items[]) {
        boolean answer = true;
        
        System.out.printf("The cursor should be at item [" + i + "] of the sequence\n");
        System.out.printf("(counting the first item as [0]). I will advance the cursor\n");
        System.out.printf("to the end of the sequence, checking that each item is correct...");
        while ((i < s) && test.isCurrent( ) && (test.getCurrent( ) == items[i])) {
            i++;
            test.advance( );
        }
    
        if ((i != s) && !test.isCurrent( )) {   // The test.isCurrent( ) function returns false too soon.
            System.out.printf( "\n    Cursor fell off the sequence too soon.\n");
            answer = false;
        }
        else if (i != s) {   
            // The test.getCurrent( ) function returned a wrong value.
            System.out.printf("\n    The item [" + i + "] should be " + items[i] + ",\n");
            System.out.printf("    but it was " + test.getCurrent( ) + " instead.\n");
            answer = false;
        }
        else if (test.isCurrent( )) {
            // The test.isCurrent( ) function returns true after moving off the sequence.
            System.out.printf("\n    The cursor was moved off the sequence,");
            System.out.printf(" but isCurrent still returns true.\n");
            answer = false;
        }
    
        System.out.printf("%s\n", answer ? "Passed." : "Failed.");
        return answer;
    }

    // **************************************************************************
    // boolean correct(SLinkedSequence<Long> test, int s, int cursor_spot, long items[])
    //   This function determines if the sequence (test) is "correct" according to
    //   these requirements:
    //   a. it has exactly s items.
    //   b. the items (starting at the front) are equal to
    //      items[0] ... items[s-1]
    //   c. if cursor_spot < s, then test's cursor must be at
    //      the location given by cursor_spot.
    //   d. if cursor_spot >= s, then test must not have a cursor.
    // NOTE: The function also moves the cursor off the sequence.
    // **************************************************************************
    public static boolean correct(SLinkedSequence<Long> test, int size, int cursor_spot, long items[]) {
        boolean has_cursor = (cursor_spot < size); 
    
        // Check the sequence's size and whether it has a cursor.
        if (!test_basic(test, size, has_cursor)) {
            System.out.printf("%s\n\n", "Basic test of size() or isCurrent() failed.");
            return false;
        }
    
        // If there is a cursor, check the items from cursor to end of the sequence.
        if (has_cursor && !test_items(test, size, cursor_spot, items)) {
            System.out.printf("%s\n\n", "Test of the sequence's items failed.");
            return false;
        }
    
        // Restart the cursor at the front of the sequence and test items again.
        System.out.printf("%s\n", "I'll call start() and look at the items one more time...");
        test.start( );
        if (has_cursor && !test_items(test, size, 0, items)) {
            System.out.printf("%s\n\n", "Test of the sequence's items failed.");
            return false;
        }
    
        // If the code reaches here, then all tests have been passed.
        System.out.printf("%s\n\n", "All tests passed for this sequence.");
        return true;
    }

    // **************************************************************************
    // int test1( )
    //   Performs some basic tests of addBefore, addAfter, and the constant member
    //   functions. Returns POINTS[1] if the tests are passed. Otherwise returns 0.
    // **************************************************************************
    public static int test1( ) {
        SLinkedSequence<Long> empty = new SLinkedSequence<Long>();
        SLinkedSequence<Long> test = new SLinkedSequence<Long>();    // A sequence to add items to
        long items1[] = { 5, 10, 20, 30 };  // These 4 items are put in a sequence
        long items2[] = { 10, 15, 20, 30 }; // These are put in another sequence
    
        // Test that the empty sequence is really empty
        System.out.printf("%s\n", "Starting with an empty sequence.");
        if (!correct(empty, 0, 0, items1)) return 0;
    
        // Test the addAfter function to add something to an empty sequence 
        System.out.printf("%s\n", "I am now using addAfter to put 10 into an empty sequence.");
        test.addAfter(10L);
        if (!correct(test, 1, 0, items2)) return 0;
        
        // Test the addBefore function to add something to an empty sequence
        System.out.printf("%s\n","I am now using addBefore to put 10 into an empty sequence.");
        test = new SLinkedSequence<Long>();
        test.addBefore(10L);
        if (!correct(test, 1, 0, items2)) return 0;
        
        // Test the addAfter to put items at the end of a sequence
        System.out.printf("I am now using addAfter to put 10,20,30 in an empty sequence.\n");
        System.out.printf("%s\n","Then I move the cursor to the start and addBefore 5.");
        test = new SLinkedSequence<Long>();
        System.out.printf("test sequence is: %s\n", test);
        test.addAfter(10L);
        System.out.printf("test sequence is: %s\n", test);
        test.addAfter(20L);
        System.out.printf("test sequence is: %s\n", test);
        test.addAfter(30L);
        System.out.printf("test sequence is: %s\n", test);
        test.start( );
        test.addBefore(5L);
        System.out.printf("test sequence is: %s\n", test);
        if (!correct(test, 4, 0, items1)) return 0;
        
        // Test the addBefore function to add an item in the middle of a sequence
        System.out.printf("I am now using addAfter to put 10,20,30 in an empty sequence.\n");
        System.out.printf("Then I move the cursor to the start, advance once, ");
        System.out.printf("%s\n","and addBefore 15.");
        test = new SLinkedSequence<Long>();
        test.addAfter(10L);
        test.addAfter(20L);
        test.addAfter(30L);
        test.start( );
        test.advance( );
        test.addBefore(15L);
        if (!correct(test, 4, 1, items2)) return 0;
    
        // Test the addAfter function to add an item in the middle of a sequence
        System.out.printf("I am now using addAfter to put 10,20,30 in an empty sequence.\n");
        System.out.printf("Then I move the cursor to the start and addAfter 15 ");
        System.out.printf("after the 10.\n");
        test = new SLinkedSequence<Long>();
        test.addAfter(10L);
        test.addAfter(20L);
        test.addAfter(30L);
        test.start( );
        test.addAfter(15L);
        if (!correct(test, 4, 1, items2)) return 0;
    
        // All tests have been passed
        System.out.printf("%s\n", "All tests of this first function have been passed.");
        return POINTS[1];
    }

    // **************************************************************************
    // int test2( )
    //   Performs a test to ensure that the cursor can correctly be run off the end
    //   of the sequence. Also tests that addAfter/addBefore work correctly when there is
    //   no cursor. Returns POINTS[2] if the tests are passed. Otherwise returns 0.
    // **************************************************************************
    public static int test2( ) {
        final int TESTSIZE = 30;
        SLinkedSequence<Long> test = new SLinkedSequence<Long>();
        int i;
    
        // Put three items in the sequence
        System.out.printf("Using addAfter to put 20 and 30 in the sequence, and then calling\n");
        System.out.printf("advance, so that isCurrent should return false ... \n");
        test.addAfter(20L);
        test.addAfter(30L);
        test.advance( );
        if (test.isCurrent( )) {
            System.out.printf("failed.\n");
            return 0;
        }
        System.out.printf("passed.\n");
    
        // Insert 10 at the front and run the cursor off the end again
        System.out.printf("Inserting 10, which should go at the sequence's front.\n");
        System.out.printf("Then calling advance three times to run cursor off the sequence ...\n");
        test.addBefore(10L);
        test.advance( ); // advance to the 20
        test.advance( ); // advance to the 30
        test.advance( ); // advance right off the sequence
        if (test.isCurrent( )) {
            System.out.printf(" failed.\n");
            return 0;
        }
        System.out.printf(" passed.\n");
        
        // Attach more items until the sequence becomes full.
        // Note that the first addAfter should attach to the end of the sequence.
        System.out.printf("Calling addAfter to put the numbers 40, 50, 60 ...");
        System.out.printf(TESTSIZE*10 + " at the sequence's end.\n");
        for (i = 4; i <= TESTSIZE; i++)
            test.addAfter(i*10L);
    
        // Test that the sequence is correctly filled.
        System.out.printf("Now I will test that the sequence has 10, 20, 30, ...");
        System.out.printf(TESTSIZE*10 + ".\n");
        test.start( );
        for (i = 1; i <= TESTSIZE; i++) {
            if ((!test.isCurrent( )) || test.getCurrent( ) != i*10) {
                System.out.printf("    Test failed to find " + i*10 + "\n");
                return 0;
            }
            test.advance( );
        }
        if (test.isCurrent( )) {
            System.out.printf("    There are too many items on the sequence.\n");
            return 0;
        }
    
        // All tests passed
        System.out.printf("All tests of this second function have been passed.\n");    
        return POINTS[2];
    }

    // **************************************************************************
    // int test3( )
    //   Performs basic tests for the removeCurrent function.
    //   Returns POINTS[3] if the tests are passed.
    //   Otherwise returns 0.
    // **************************************************************************
    public static int test3( ) {
        final int TESTSIZE = 30;
        
        SLinkedSequence<Long> test = new SLinkedSequence<Long>();
    
        // Within this function, I create several different sequences using the
        // items in these arrays:
        long items0[] = { };
        long items1[] = { 30 };
        long items2[] = { 10, 30 };
        long items3[] = { 10, 20, 30 };
        
        int i;       // for-loop control variable
    
        // Build a sequence with three items 10, 20, 30, and remove the middle,
        // and last and then first.
        System.out.printf("Using addAfter to build a sequence with 10,30.\n");
        test.addAfter(10L);
        test.addAfter(30L);
        System.out.printf("Insert a 20 before the 30, so entire sequence is 10,20,30.\n");
        test.addBefore(20L);
        if (!correct(test, 3, 1, items3)) return 0;
        System.out.printf("Remove the 20, so entire sequence is now 10,30.\n");
        test.start( );
        test.advance( );
        test.removeCurrent( );
        if (!correct(test, 2, 1, items2)) return 0;
        System.out.printf("Remove the 30, so entire sequence is now just 10 with no cursor.\n");
        test.start( );
        test.advance( );
        test.removeCurrent( );
        if (!correct(test, 1, 1, items2)) return 0;
        System.out.printf("Set the cursor to the start and remove the 10.\n");
        test.start( );
        test.removeCurrent( );
        if (!correct(test, 0, 0, items2)) return 0;
    
        // Build a sequence with three items 10, 20, 30, and remove the middle,
        // and then first and then last.
        System.out.printf("Using addAfter to build another sequence with 10,30.\n");
        test.addAfter(10L);
        test.addAfter(30L);
        System.out.printf("Insert a 20 before the 30, so entire sequence is 10,20,30.\n");
        test.addBefore(20L);
        //System.out.printf("The sequence is now %s\n", test);
        if (!correct(test, 3, 1, items3)) return 0;
        System.out.printf("Remove the 20, so entire sequence is now 10,30.\n");
        test.start( );
        test.advance( );
        test.removeCurrent( );
        if (!correct(test, 2, 1, items2)) return 0;
        System.out.printf("Set the cursor to the start and remove the 10,\n");
        System.out.printf("so the sequence should now contain just 30.\n");
        test.start( );
        test.removeCurrent( );
        if (!correct(test, 1, 0, items1)) return 0;
        System.out.printf("Remove the 30 from the sequence, resulting in an empty sequence.\n");
        test.start( );
        test.removeCurrent( );
        if (!correct(test, 0, 0, items1)) return 0;
    
        // Build a sequence with three items 10, 20, 30, and remove the first.
        System.out.printf("Build a new sequence by addBeforeing 30, 10, 20 (so the sequence\n");
        System.out.printf("is 20, then 10, then 30). Then remove the 20.\n");
        test = new SLinkedSequence<Long>();
        test.addBefore(30L);
        test.addBefore(10L);
        test.addBefore(20L);
        test.removeCurrent( );
        if (!correct(test, 2, 0, items2)) return 0;
        test.start( );
        test.removeCurrent( );
        test.removeCurrent( );
    
        // Just for fun, fill up the sequence, and empty it!
        System.out.printf("Just for fun, I'll empty the sequence then fill it up, then\n");
        System.out.printf("empty it again.\n");
        /*During this process, I'll try to determine\n";
        System.out.printf("whether any of the sequence's member functions access the\n";
        System.out.printf("array outside of its legal indexes."); */
        for (long l = 0; l < TESTSIZE; l++)
            test.addBefore(l);
        for (i = 0; i < TESTSIZE; i++)
            test.removeCurrent( );
    
        // All tests passed
        System.out.printf("All tests of this third function have been passed.");    
        return POINTS[3];
    }

    // **************************************************************************
    // int test4( )
    //   Performs some tests of the copy constructor.
    //   Returns POINTS[4] if the tests are passed. Otherwise returns 0.
    // **************************************************************************
    public static int test4( ) {
        final int TESTSIZE = 30;
        SLinkedSequence<Long> original = new SLinkedSequence<Long>(); // A SLinkedSequence<Long> that we'll copy.
        long items[] = new long[2*TESTSIZE];
        int i;
    
        // Set up the items array to conatin 1...2*TESTSIZE.
        for (i = 1; i <= 2*TESTSIZE; i++)
            items[i-1] = i;
        
        // Test copying of an empty SLinkedSequence<Long>. After the copying, we change original.
        System.out.printf("Copy constructor test: for an empty SLinkedSequence<Long>.\n");
        SLinkedSequence<Long> copy1 = new SLinkedSequence<Long>(original);
        original.addAfter(1L); // Changes the original SLinkedSequence<Long>, but not the copy.
        //System.out.printf("Original:" + original + "\n");
        //System.out.printf("Copy:" + copy1 + "\n");;
        if (!correct(copy1, 0, 0, items)) return 0;
    
        // Test copying of a SLinkedSequence<Long> with current item at the tail.
        System.out.printf("Copy constructor test: for a SLinkedSequence<Long> with cursor at tail.\n");
        for (long l=2; l <= 2*TESTSIZE; l++)
            original.addAfter(l);
        //System.out.printf("Original:" + original + "\n");
        SLinkedSequence<Long> copy2 = new SLinkedSequence<Long>(original);
        //System.out.printf("Copy:" + copy2 + "\n");
        original.removeCurrent( ); // Delete tail from original, but not the copy
        //System.out.printf("Original:" + original + "\n");
        original.start( );
        original.advance( );
        original.removeCurrent( ); // Delete 2 from original, but not the copy.
        //System.out.printf("Original:" + original + "\n");
        //System.out.printf("Copy:" + copy2 + "\n");
        if (!correct
            (copy2, 2*TESTSIZE, 2*TESTSIZE-1, items)
            )
            return 0;
    
        // Test copying of a SLinkedSequence<Long> with cursor near the middle.
        System.out.printf("Copy constructor test: with cursor near middle.\n");
        original.addBefore(2L);
        for (i = 1; i < TESTSIZE; i++)
            original.advance( );
        // Cursor is now at location [TESTSIZE] (counting [0] as the first spot).
        SLinkedSequence<Long> copy3= new SLinkedSequence<Long>(original);
        original.start( );
        original.advance( );
        original.removeCurrent( ); // Delete 2 from original, but not the copy.
        //System.out.printf("Original:" + original + "\n);
        //System.out.printf("Copy:" + copy3 + "\n");
        if (!correct
            (copy3, 2*TESTSIZE-1, TESTSIZE, items)
            )
            return 0;
    
        // Test copying of a SLinkedSequence<Long> with cursor at the front.
        System.out.printf("Copy constructor test: for a SLinkedSequence<Long> with cursor at front.\n");
        original.addBefore(2L);
        original.start( );
        // Cursor is now at the front.
        SLinkedSequence<Long> copy4 = new SLinkedSequence<Long>(original);
        original.start( );
        original.advance( );
        original.removeCurrent( ); // Delete 2 from original, but not the copy.
        //System.out.printf("Original:" + original + "\n);
        //System.out.printf("Copy:" + copy4 + "\n");
        if (!correct
            (copy4, 2*TESTSIZE-1, 0, items)
            )
            return 0;
    
        // Test copying of a SLinkedSequence<Long> with no current item.
        System.out.printf("Copy constructor test: for a SLinkedSequence<Long> with no current item.\n");
        original.addBefore(2L);
        while (original.isCurrent( ))
            original.advance( );
        // There is now no current item.
        SLinkedSequence<Long> copy5 = new SLinkedSequence<Long>(original);
        original.start( );
        original.advance( );
        original.removeCurrent( ); // Delete 2 from original, but not the copy.
        //System.out.printf("Original:" + original + "\n");
        //System.out.printf("Copy:" + copy5 + "\n");
        if (!correct
            (copy5, 2*TESTSIZE-1, 2*TESTSIZE, items)
            )
            return 0;
    
        // All tests passed
        System.out.printf("All tests of this fourth function have been passed.\n");    
        return POINTS[4];
    } 

    // **************************************************************************
    // int test5( )
    //   Performs some tests of clone.
    //   Returns POINTS[4] if the tests are passed. Otherwise returns 0.
    // **************************************************************************
    public static int test5( ) {
        final int TESTSIZE = 30;
        SLinkedSequence<Long> original = new SLinkedSequence<Long>(); // A SLinkedSequence<Long> that we'll copy.
        long items[] = new long[2*TESTSIZE];
        int i;
    
        // Set up the items array to conatin 1...2*TESTSIZE.
        for (i = 1; i <= 2*TESTSIZE; i++)
            items[i-1] = i;
        
        // Test copying of an empty SLinkedSequence<Long>. After the copying, we change original.
        System.out.printf("clone test: for an empty SLinkedSequence<Long>.\n");
        SLinkedSequence<Long> copy1 = original.clone();
        original.addAfter(1L); // Changes the original SLinkedSequence<Long>, but not the copy.
        //System.out.printf("Original:" + original + "\n");
        //System.out.printf("Copy:" + copy1 + "\n");;
        if (!correct(copy1, 0, 0, items)) return 0;
    
        // Test copying of a SLinkedSequence<Long> with current item at the tail.
        System.out.printf("clone test: for a SLinkedSequence<Long> with cursor at tail.\n");
        for (long l=2; l <= 2*TESTSIZE; l++)
            original.addAfter(l);
        //System.out.printf("Original:" + original + "\n");
        SLinkedSequence<Long> copy2 = original.clone();
        //System.out.printf("Copy:" + copy2 + "\n");
        original.removeCurrent( ); // Delete tail from original, but not the copy
        //System.out.printf("Original:" + original + "\n");
        original.start( );
        original.advance( );
        original.removeCurrent( ); // Delete 2 from original, but not the copy.
        //System.out.printf("Original:" + original + "\n");
        //System.out.printf("Copy:" + copy2 + "\n");
        if (!correct
            (copy2, 2*TESTSIZE, 2*TESTSIZE-1, items)
            )
            return 0;
    
        // Test copying of a SLinkedSequence<Long> with cursor near the middle.
        System.out.printf("clone test: with cursor near middle.\n");
        original.addBefore(2L);
        for (i = 1; i < TESTSIZE; i++)
            original.advance( );
        // Cursor is now at location [TESTSIZE] (counting [0] as the first spot).
        SLinkedSequence<Long> copy3= original.clone();
        original.start( );
        original.advance( );
        original.removeCurrent( ); // Delete 2 from original, but not the copy.
        //System.out.printf("Original:" + original + "\n);
        //System.out.printf("Copy:" + copy3 + "\n");
        if (!correct
            (copy3, 2*TESTSIZE-1, TESTSIZE, items)
            )
            return 0;
    
        // Test copying of a SLinkedSequence<Long> with cursor at the front.
        System.out.printf("clone test: for a SLinkedSequence<Long> with cursor at front.\n");
        original.addBefore(2L);
        original.start( );
        // Cursor is now at the front.
        SLinkedSequence<Long> copy4 = original.clone();
        original.start( );
        original.advance( );
        original.removeCurrent( ); // Delete 2 from original, but not the copy.
        //System.out.printf("Original:" + original + "\n);
        //System.out.printf("Copy:" + copy4 + "\n");
        if (!correct
            (copy4, 2*TESTSIZE-1, 0, items)
            )
            return 0;
    
        // Test copying of a SLinkedSequence<Long> with no current item.
        System.out.printf("clone test: for a SLinkedSequence<Long> with no current item.\n");
        original.addBefore(2L);
        while (original.isCurrent( ))
            original.advance( );
        // There is now no current item.
        SLinkedSequence<Long> copy5 = original.clone();
        original.start( );
        original.advance( );
        original.removeCurrent( ); // Delete 2 from original, but not the copy.
        //System.out.printf("Original:" + original + "\n");
        //System.out.printf("Copy:" + copy5 + "\n");
        if (!correct
            (copy5, 2*TESTSIZE-1, 2*TESTSIZE, items)
            )
            return 0;
    
        // All tests passed
        System.out.printf("All tests of this fourth function have been passed.\n");    
        return POINTS[4];
    } 

// **************************************************************************
// int test6( )
//   Performs some basic tests of addBefore and addAfter for the case where the
//   current capacity has been reached (in case of array implementation).
//   Returns POINTS[6] if the tests are passed. Otherwise returns 0.
// **************************************************************************
    public static int test6( ) {
        final int TESTSIZE = 30;
        SLinkedSequence<Long> testa, testi;
        testa = new SLinkedSequence<Long>();
        testi = new SLinkedSequence<Long>();
        long items[] = new long[2*TESTSIZE];
        int i;
    
        // Set up the items array to conatin 1...2*TESTSIZE.
        for (i = 1; i <= 2*TESTSIZE; i++)
            items[i-1] = i;
        
        System.out.printf("Testing to see that addAfter works correctly \n");
        for (long l = 1; l <= 2*TESTSIZE; l++)
            testa.addAfter(l);
        if (!correct
            (testa, 2*TESTSIZE, 2*TESTSIZE-1, items)
            )
            return 0;
    
        System.out.printf("Testing to see that addBefore works correctly \n");
        for (long l = 2*TESTSIZE; l >= 1; l--)
            testi.addBefore(l);
        if (!correct
            (testi, 2*TESTSIZE, 0, items)
            )
            return 0;
    
        // All tests passed
        System.out.printf("All tests of this sixth function have been passed.\n");    
        return POINTS[6];
    }  
    

    // **************************************************************************
    // int test7( )
    //   Performs some basic tests of equals
    //   Returns POINTS[7] if the tests are passed. Otherwise returns 0.
    // **************************************************************************
    public static int test7( ) {
        final int TESTSIZE = 30;
        SLinkedSequence<Long> testa, testi, empty;
        testa = new SLinkedSequence<Long>();
        testi = new SLinkedSequence<Long>();
        empty = new SLinkedSequence<Long>();

        long items[] = new long[2*TESTSIZE];
        int i;
    
        // Set up the items to contain 1...2*TESTSIZE.
        for (i = 1; i <= 2*TESTSIZE; i++)
            items[i-1] = i;
        
        System.out.printf("Creating two sequences with exactly the same items using addAfter\n");
        System.out.printf("Testing ==\n");
        for (long l = 1; l <= 2*TESTSIZE; l++) {
            testa.addAfter(l);
            testi.addAfter(l);
        }
        if ( !(testa.equals(testi)) ) return 0;
          
        //System.out.printf("The two sequences are ==\n");
        //System.out.printf("testa " + testa + "\n");
        //System.out.printf("testi " + testi + "\n");
          
    
        testa = new SLinkedSequence<Long>();
        testi = new SLinkedSequence<Long>();
        System.out.printf("Creating two sequences with exactly the same items using addBefore\n");
        System.out.printf("Testing ==\n");
        for (long l = 1; l <= 2*TESTSIZE; l++) {
            testa.addBefore(l);
            testi.addBefore(l);
        }
        if ( !(testa.equals(testi)) ) return 0;
        //System.out.printf("The two sequences are ==\n");
        //System.out.printf("testa " + testa + "\n");
        //System.out.printf("testi " + testi + "\n"); 
        System.out.printf("I will now call advance on one of the sequences\n");
        System.out.printf("Testing !=\n");
        testa.advance();
        //System.out.printf("testa " + testa + "\n");
        //System.out.printf("testi " + testi + "\n"); 
        if ( (testa.equals(testi)) ) return 0;
    
        // All tests passed
        System.out.printf("All tests of this seventh function have been passed.\n");    
        return POINTS[7];
    }  

    // **************************************************************************
    // int test8( )
    //   Performs some basic tests of concatenation method 
    //   Returns POINTS[8] if the tests are passed. Otherwise returns 0.
    // **************************************************************************
    public static int test8( ) {
        final int TESTSIZE = 30;
        long items[] = new long[2*TESTSIZE];
        SLinkedSequence<Long> seq1, seq2, empty;  //each created empty with capacity of 1
        seq1 = new SLinkedSequence<Long>();
        seq2 = new SLinkedSequence<Long>();
        empty = new SLinkedSequence<Long>();
        int i;
        System.out.printf("Testing concatenation of two empty sequences\n");
        try {
            seq1 = seq2.concatenation(empty); //This could cause exception if it fails
            if ( seq1.size() != 0 ) return 0;
            System.out.printf("Test passed ...\n\n");
        }
        catch (Exception e) {
            System.out.printf("Test failed ...\n\n");
            return 0;
        }
    
        // Set up the items to contain 1...2*TESTSIZE.
        for (i = 1; i <= 2*TESTSIZE; i++)
            items[i-1] = i;
     
        System.out.printf("Creating two sequences with exactly the same items using addAfter\n");
        System.out.printf("Testing equals\n");
        for (long l = 1; l <= 2*TESTSIZE; l++) {
            seq1.addAfter(l);
            seq2.addAfter(l);
        }
        if ( !(seq1.equals(seq2)) ) return 0;
        System.out.printf("Test passed ...\n\n");
        /*
        System.out.printf("The two sequences are ==\n");
        System.out.printf("seq1 " + seq1 + "\n");
        System.out.printf("seq2 " + seq2 + "\n");
        */
    
        System.out.printf("Emptying seq1, then executing seq1 = seq2.concatenation(new SLinkedSequence<Long>())\n");
        seq1 = new SLinkedSequence<Long>();
        seq1 = seq2.concatenation(new SLinkedSequence<Long>());
    
        System.out.printf("Testing that seq1 has no current item\n");
        if ( seq1.isCurrent() ) return 0; //there should not be a current cursor
        System.out.printf("Test passed ...\n\n");
    
        System.out.printf("Setting cursor to beginning of seq1 and testing that all elements are correct\n");
        seq1.start();
        // If there is a cursor, check the items from cursor to end of the sequence.
        if (!test_items(seq1, 2*TESTSIZE, 0, items)) {
            System.out.printf("Test of the sequence's items failed.\n");
            return 0;
        }
        System.out.printf("Test passed ...\n\n");
    
        // Set up the items to contain 1...TESTSIZE,1...TESTSIZE
        for (i = 1; i <= TESTSIZE; i++) {
            items[i-1] = i;
            items[i-1+TESTSIZE] = i;
        }
        System.out.printf("Filling each sequence with value 1 .. TESTSIZE using addAfter\n");
        seq1 = new SLinkedSequence<Long>();
        seq2 = new SLinkedSequence<Long>();
        for (long l = 1; l <= TESTSIZE; l++ ) {
            seq1.addAfter(l);
            seq2.addAfter(l);
        }
    
        System.out.printf("Invoking concatenation by seq1 = seq1.concatenation(seq2)\n");
        seq1 = seq1.concatenation(seq2);
        System.out.printf("Setting cursor to beginning of seq1 and testing that all elements are correct\n");
        seq1.start();
        // check the items from cursor to end of the sequence.
        if (!test_items(seq1, 2*TESTSIZE, 0, items)) {
            System.out.printf("Test of the sequence's items failed.\n\n");
            return 0;
        }
        System.out.printf("Test passed ...\n\n");
    
        // All tests passed
        System.out.printf("All tests of this eighth function have been passed.\n");    
        return POINTS[8];
    }

}
