// FILE: sequence_exam.cxx
// Modified by: H. Paul Haiduk (hhaiduk@wtamu.edu) 
// Non-interactive test program for the sequence<Item> class
//
// DESCRIPTION:
// Each function of this program tests part of the sequence<Item> class, returning
// some number of points to indicate how much of the test was passed.
// A description and result of each test is printed to cout.
// Maximum number of points awarded by this program is determined by the
// constants POINTS[1], POINTS[2]...

#include <iostream>     // Provides cout.
#include <cstdlib>      // Provides size_t.
#include "sequence.h"   // Provides the sequence<Item> class
using namespace std;

typedef long Item;

// Descriptions and points for each of the tests:
const size_t MANY_TESTS = 8;
const int POINTS[MANY_TESTS+1] = {
    300,  // Total points for all tests.
     50,  // Test 1 points
     50,  // Test 2 points
     50,  // Test 3 points
     30,  // Test 4 points
     30,  // Test 5 points
     30,  // Test 6 points
     30,  // Test 7 points
     30   // Test 8 points
};

const char DESCRIPTION[MANY_TESTS+1][256] = {
    "tests for sequence class with an dynamic sized array implementation",
    "Testing add_before, add_after, and the constant member functions",
    "Testing situations where the cursor goes off the sequence",
    "Testing remove_current",
    "Testing the copy constructor",
    "Testing the assignment operator",
    "Testing add_before/add_after for somewhat larger sequence",
    "Testing operator == and !=",
    "Testing operator +"
};


// **************************************************************************
// bool test_basic(const sequence<Item>& test, size_t s, bool has_cursor)
//   Postcondition: A return value of true indicates:
//     a. test.size() is s, and
//     b. test.is_current() is has_cursor.
//   Otherwise the return value is false.
//   In either case, a description of the test result is printed to cout.
// **************************************************************************
bool test_basic(const sequence<Item>& test, size_t s, bool has_cursor) {
    bool answer;

    cout << "Testing that size() returns " << s << " ... ";
    cout.flush( );
    answer = (test.size( ) == s);
    cout << (answer ? "Passed." : "Failed.") << endl;
    
    if (answer) {
        cout << "Testing that is_current() returns ";
        cout << (has_cursor ? "true" : "false") << " ... ";
        cout.flush( );
        answer = (test.is_current( ) == has_cursor);
        cout << (answer ? "Passed." : "Failed.") << endl;
    }

    return answer;
}


// **************************************************************************
// bool test_items(sequence<Item>& test, size_t s, size_t i, long items[])
//   The function determines if the test sequence has the correct items
//   Precondition: The size of the items array is at least s.
//   Postcondition: A return value of true indicates that test.get_current()
//   is equal to items[i], and after test.advance() the result of
//   test.get_current() is items[i+1], and so on through items[s-1].
//   At this point, one more advance takes the cursor off the sequence.
//   If any of this fails, the return value is false.
//   NOTE: The test sequence has been changed by advancing its cursor.
// **************************************************************************
bool test_items(sequence<Item>& test, size_t s, size_t i, long items[]) {
    bool answer = true;
    
    cout << "The cursor should be at item [" << i << "]" << " of the sequence\n";
    cout << "(counting the first item as [0]). I will advance the cursor\n";
    cout << "to the end of the sequence, checking that each item is correct...";
    cout.flush( );
    while ((i < s) && test.is_current( ) && (test.get_current( ) == items[i])) {
        i++;
        test.advance( );
    }

    if ((i != s) && !test.is_current( )) {   // The test.is_current( ) function returns false too soon.
        cout << "\n    Cursor fell off the sequence too soon." << endl;
        answer = false;
    }
    else if (i != s) {   
        // The test.get_current( ) function returned a wrong value.
        cout << "\n    The item [" << i << "] should be " << items[i] << ",\n";
        cout << "    but it was " << test.get_current( ) << " instead.\n";
        answer = false;
    }
    else if (test.is_current( )) {
        // The test.is_current( ) function returns true after moving off the sequence.
        cout << "\n    The cursor was moved off the sequence,";
        cout << " but is_current still returns true." << endl;
        answer = false;
    }

    cout << (answer ? "Passed." : "Failed.") << endl;
    return answer;
}


// **************************************************************************
// bool correct(sequence<Item>& test, size_t s, size_t cursor_spot, long items[])
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
bool correct(sequence<Item>& test, size_t size, size_t cursor_spot, long items[]) {
    bool has_cursor = (cursor_spot < size); 

    // Check the sequence's size and whether it has a cursor.
    if (!test_basic(test, size, has_cursor)) {
        cout << "Basic test of size() or is_current() failed." << endl << endl;
        return false;
    }

    // If there is a cursor, check the items from cursor to end of the sequence.
    if (has_cursor && !test_items(test, size, cursor_spot, items)) {
        cout << "Test of the sequence's items failed." << endl << endl;
        return false;
    }

    // Restart the cursor at the front of the sequence and test items again.
    cout << "I'll call start() and look at the items one more time..." << endl;
    test.start( );
    if (has_cursor && !test_items(test, size, 0, items)) {
        cout << "Test of the sequence's items failed." << endl << endl;
        return false;
    }

    // If the code reaches here, then all tests have been passed.
    cout << "All tests passed for this sequence." << endl << endl;
    return true;
}


// **************************************************************************
// int test1( )
//   Performs some basic tests of add_before, add_after, and the constant member
//   functions. Returns POINTS[1] if the tests are passed. Otherwise returns 0.
// **************************************************************************
int test1( ) {
    sequence<Item> empty;                            // An empty sequence
    sequence<Item> test;                             // A sequence to add items to
    long items1[4] = { 5, 10, 20, 30 };  // These 4 items are put in a sequence
    long items2[4] = { 10, 15, 20, 30 }; // These are put in another sequence

    // Test that the empty sequence is really empty
    cout << "Starting with an empty sequence." << endl;
    if (!correct(empty, 0, 0, items1)) return 0;

    // Test the add_after function to add something to an empty sequence 
    cout << "I am now using add_after to put 10 into an empty sequence." << endl;
    test.add_after(10);
    if (!correct(test, 1, 0, items2)) return 0;
    
    // Test the add_before function to add something to an empty sequence
    cout << "I am now using add_before to put 10 into an empty sequence." << endl;
    test = empty;
    test.add_before(10);
    if (!correct(test, 1, 0, items2)) return 0;
    
    // Test the add_after to put items at the end of a sequence
    cout << "I am now using add_after to put 10,20,30 in an empty sequence.\n";
    cout << "Then I move the cursor to the start and add_before 5." << endl;
    test = empty;
    test.add_after(10);
    test.add_after(20);
    test.add_after(30);
    test.start( );
    test.add_before(5);
    if (!correct(test, 4, 0, items1)) return 0;
    
    // Test the add_before function to add an item in the middle of a sequence
    cout << "I am now using add_after to put 10,20,30 in an empty sequence.\n";
    cout << "Then I move the cursor to the start, advance once, ";
    cout << "and add_before 15." << endl;
    test = empty;
    test.add_after(10);
    test.add_after(20);
    test.add_after(30);
    test.start( );
    test.advance( );
    test.add_before(15);
    if (!correct(test, 4, 1, items2)) return 0;

    // Test the add_after function to add an item in the middle of a sequence
    cout << "I am now using add_after to put 10,20,30 in an empty sequence.\n";
    cout << "Then I move the cursor to the start and add_after 15 ";
    cout << "after the 10." << endl;
    test = empty;
    test.add_after(10);
    test.add_after(20);
    test.add_after(30);
    test.start( );
    test.add_after(15);
    if (!correct(test, 4, 1, items2)) return 0;

    // All tests have been passed
    cout << "All tests of this first function have been passed." << endl;
    return POINTS[1];
}


// **************************************************************************
// int test2( )
//   Performs a test to ensure that the cursor can correctly be run off the end
//   of the sequence. Also tests that add_after/add_before work correctly when there is
//   no cursor. Returns POINTS[2] if the tests are passed. Otherwise returns 0.
// **************************************************************************
int test2( ) {
    const size_t TESTSIZE = 30;
    sequence<Item> test;
    size_t i;

    // Put three items in the sequence
    cout << "Using add_after to put 20 and 30 in the sequence, and then calling\n";
    cout << "advance, so that is_current should return false ... ";
    cout.flush( );
    test.add_after(20);
    test.add_after(30);
    test.advance( );
    if (test.is_current( )) {
        cout << "failed." << endl;
        return 0;
    }
    cout << "passed." << endl;

    // Insert 10 at the front and run the cursor off the end again
    cout << "Inserting 10, which should go at the sequence's front." << endl;
    cout << "Then calling advance three times to run cursor off the sequence ...";
    cout.flush( );
    test.add_before(10);
    test.advance( ); // advance to the 20
    test.advance( ); // advance to the 30
    test.advance( ); // advance right off the sequence
    if (test.is_current( )) {
        cout << " failed." << endl;
        return false;
    }
    cout << " passed." << endl;
    
    // Attach more items until the sequence becomes full.
    // Note that the first add_after should attach to the end of the sequence.
    cout << "Calling add_after to put the numbers 40, 50, 60 ...";
    cout << TESTSIZE*10 << " at the sequence's end." << endl;
    for (i = 4; i <= TESTSIZE; i++)
        test.add_after(i*10);

    // Test that the sequence is correctly filled.
    cout << "Now I will test that the sequence has 10, 20, 30, ...";
    cout << TESTSIZE*10 << "." << endl;
    test.start( );
    for (i = 1; i <= TESTSIZE; i++) {
        if ((!test.is_current( )) || test.get_current( ) != i*10) {
            cout << "    Test failed to find " << i*10 << endl;
            return 0;
        }
        test.advance( );
    }
    if (test.is_current( )) {
        cout << "    There are too many items on the sequence." << endl;
        return false;
    }

    // All tests passed
    cout << "All tests of this second function have been passed." << endl;    
    return POINTS[2];
}


// **************************************************************************
// int test3( )
//   Performs basic tests for the remove_current function.
//   Returns POINTS[3] if the tests are passed.
//   Otherwise returns 0.
// **************************************************************************
int test3( ) {
    const size_t TESTSIZE = 30;
    
    sequence<Item> test;

    // Within this function, I create several different sequences using the
    // items in these arrays:
    long items1[1] = { 30 };
    long items2[2] = { 10, 30 };
    long items3[3] = { 10, 20, 30 };
    
    size_t i;       // for-loop control variable

    // Build a sequence with three items 10, 20, 30, and remove the middle,
    // and last and then first.
    cout << "Using add_after to build a sequence with 10,30." << endl;
    test.add_after(10);
    test.add_after(30);
    cout << "Insert a 20 before the 30, so entire sequence is 10,20,30." << endl;
    test.add_before(20);
    if (!correct(test, 3, 1, items3)) return 0;
    cout << "Remove the 20, so entire sequence is now 10,30." << endl;
    test.start( );
    test.advance( );
    test.remove_current( );
    if (!correct(test, 2, 1, items2)) return 0;
    cout << "Remove the 30, so entire sequence is now just 10 with no cursor.";
    cout << endl;
    test.start( );
    test.advance( );
    test.remove_current( );
    if (!correct(test, 1, 1, items2)) return 0;
    cout << "Set the cursor to the start and remove the 10." << endl;
    test.start( );
    test.remove_current( );
    if (!correct(test, 0, 0, items2)) return 0;

    // Build a sequence with three items 10, 20, 30, and remove the middle,
    // and then first and then last.
    cout << "Using add_after to build another sequence with 10,30." << endl;
    test.add_after(10);
    test.add_after(30);
    cout << "Insert a 20 before the 30, so entire sequence is 10,20,30." << endl;
    test.add_before(20);
    if (!correct(test, 3, 1, items3)) return 0;
    cout << "Remove the 20, so entire sequence is now 10,30." << endl;
    test.start( );
    test.advance( );
    test.remove_current( );
    if (!correct(test, 2, 1, items2)) return 0;
    cout << "Set the cursor to the start and remove the 10," << endl;
    cout << "so the sequence should now contain just 30." << endl;
    test.start( );
    test.remove_current( );
    if (!correct(test, 1, 0, items1)) return 0;
    cout << "Remove the 30 from the sequence, resulting in an empty sequence." << endl;
    test.start( );
    test.remove_current( );
    if (!correct(test, 0, 0, items1)) return 0;

    // Build a sequence with three items 10, 20, 30, and remove the first.
    cout << "Build a new sequence by add_beforeing 30, 10, 20 (so the sequence\n";
    cout << "is 20, then 10, then 30). Then remove the 20." << endl;
    test.add_before(30);
    test.add_before(10);
    test.add_before(20);
    test.remove_current( );
    if (!correct(test, 2, 0, items2)) return 0;
    test.start( );
    test.remove_current( );
    test.remove_current( );

    // Just for fun, fill up the sequence, and empty it!
    cout << "Just for fun, I'll empty the sequence then fill it up, then\n";
    cout << "empty it again." << endl;
    /*During this process, I'll try to determine\n";
    cout << "whether any of the sequence's member functions access the\n";
    cout << "array outside of its legal indexes." << endl; */
    for (i = 0; i < TESTSIZE; i++)
        test.add_before(i);
    for (i = 0; i < TESTSIZE; i++)
        test.remove_current( );

    // All tests passed
    cout << "All tests of this third function have been passed." << endl;    
    return POINTS[3];
}


// **************************************************************************
// int test4( )
//   Performs some tests of the copy constructor.
//   Returns POINTS[4] if the tests are passed. Otherwise returns 0.
// **************************************************************************
int test4( ) {
    const size_t TESTSIZE = 30;
    sequence<Item> original; // A sequence that we'll copy.
    long items[2*TESTSIZE];
    size_t i;

    // Set up the items array to conatin 1...2*TESTSIZE.
    for (i = 1; i <= 2*TESTSIZE; i++)
        items[i-1] = i;
    
    // Test copying of an empty sequence. After the copying, we change original.
    cout << "Copy constructor test: for an empty sequence." << endl;
    sequence<Item> copy1(original);
    original.add_after(1); // Changes the original sequence, but not the copy.
    //cout << "Original:" << original << endl;
    //cout << "Copy:" << copy1 << endl;
    if (!correct(copy1, 0, 0, items)) return 0;

    // Test copying of a sequence with current item at the tail.
    cout << "Copy constructor test: for a sequence with cursor at tail." << endl;
    for (i=2; i <= 2*TESTSIZE; i++)
        original.add_after(i);
    //cout << "Original:" << original << endl;
    sequence<Item> copy2(original);
    cout << "Copy:" << copy2 << endl;
    original.remove_current( ); // Delete tail from original, but not the copy
    //cout << "Original:" << original << endl;
    original.start( );
    original.advance( );
    original.remove_current( ); // Delete 2 from original, but not the copy.
    //cout << "Original:" << original << endl;
    //cout << "Copy:" << copy2 << endl;
    if (!correct
        (copy2, 2*TESTSIZE, 2*TESTSIZE-1, items)
        )
        return 0;

    // Test copying of a sequence with cursor near the middle.
    cout << "Copy constructor test: with cursor near middle." << endl;
    original.add_before(2);
    for (i = 1; i < TESTSIZE; i++)
        original.advance( );
    // Cursor is now at location [TESTSIZE] (counting [0] as the first spot).
    sequence<Item> copy3(original);
    original.start( );
    original.advance( );
    original.remove_current( ); // Delete 2 from original, but not the copy.
    //cout << "Original:" << original << endl;
    //cout << "Copy:" << copy3 << endl;
    if (!correct
        (copy3, 2*TESTSIZE-1, TESTSIZE, items)
        )
        return 0;

    // Test copying of a sequence with cursor at the front.
    cout << "Copy constructor test: for a sequence with cursor at front." << endl;
    original.add_before(2);
    original.start( );
    // Cursor is now at the front.
    sequence<Item> copy4(original);
    original.start( );
    original.advance( );
    original.remove_current( ); // Delete 2 from original, but not the copy.
    //cout << "Original:" << original << endl;
    //cout << "Copy:" << copy4 << endl;
    if (!correct
        (copy4, 2*TESTSIZE-1, 0, items)
        )
        return 0;

    // Test copying of a sequence with no current item.
    cout << "Copy constructor test: for a sequence with no current item." << endl;
    original.add_before(2);
    while (original.is_current( ))
        original.advance( );
    // There is now no current item.
    sequence<Item> copy5(original);
    original.start( );
    original.advance( );
    original.remove_current( ); // Delete 2 from original, but not the copy.
    //cout << "Original:" << original << endl;
    //cout << "Copy:" << copy5 << endl;
    if (!correct
        (copy5, 2*TESTSIZE-1, 2*TESTSIZE, items)
        )
        return 0;

    // All tests passed
    cout << "All tests of this fourth function have been passed." << endl;    
    return POINTS[4];
} 


// **************************************************************************
// int test5( )
//   Performs some tests of the assignment operator.
//   Returns POINTS[5] if the tests are passed. Otherwise returns 0.
// **************************************************************************
int test5( ) {
    const size_t TESTSIZE = 30;
    sequence<Item> original; // A sequence that we'll copy.
    long items[2*TESTSIZE];
    size_t i;

    // Set up the items array to conatin 1...2*TESTSIZE.
    for (i = 1; i <= 2*TESTSIZE; i++)
        items[i-1] = i;
    
    // Test copying of an empty sequence. After the copying, we change original.
    cout << "Assignment operator test: for an empty sequence." << endl;
    sequence<Item> copy1;
    copy1 = original;
    original.add_after(1); // Changes the original sequence, but not the copy.
    if (!correct(copy1, 0, 0, items)) return 0;

    // Test copying of a sequence with current item at the tail.
    cout << "Assignment operator test: cursor at tail." << endl;
    for (i=2; i <= 2*TESTSIZE; i++)
        original.add_after(i);
    sequence<Item> copy2;
    copy2 = original;
    original.remove_current( ); // Delete tail from original, but not the copy
    original.start( );
    original.advance( );
    original.remove_current( ); // Delete 2 from original, but not the copy.
    if (!correct
        (copy2, 2*TESTSIZE, 2*TESTSIZE-1, items)
        )
        return 0;

    // Test copying of a sequence with cursor near the middle.
    cout << "Assignment operator test: with cursor near middle." << endl;
    original.add_before(2);
    for (i = 1; i < TESTSIZE; i++)
        original.advance( );
    // Cursor is now at location [TESTSIZE] (counting [0] as the first spot).
    sequence<Item> copy3;
    copy3 = original;
    original.start( );
    original.advance( );
    original.remove_current( ); // Delete 2 from original, but not the copy.
    if (!correct
        (copy3, 2*TESTSIZE-1, TESTSIZE, items)
        )
        return 0;

    // Test copying of a sequence with cursor at the front.
    cout << "Assignment operator test: with cursor at front." << endl;
    original.add_before(2);
    original.start( );
    // Cursor is now at the front.
    sequence<Item> copy4;
    copy4 = original;
    original.start( );
    original.advance( );
    original.remove_current( ); // Delete 2 from original, but not the copy.
    if (!correct
        (copy4, 2*TESTSIZE-1, 0, items)
        )
        return 0;

    // Test copying of a sequence with no current item.
    cout << "Assignment operator test: with no current item." << endl;
    original.add_before(2);
    while (original.is_current( ))
        original.advance( );
    // There is now no current item.
    sequence<Item> copy5;
    copy5 = original;
    original.start( );
    original.advance( );
    original.remove_current( ); // Deletes 2 from the original, but not copy.
    if (!correct
        (copy5, 2*TESTSIZE-1, 2*TESTSIZE, items)
        )
        return 0;

    cout << "Checking correctness of a self-assignment x = x;" << endl;
    original.add_before(2);
    original = original;
    if (!correct
        (original, 2*TESTSIZE-1, 1, items)
        )
        return 0;

    // All tests passed
    cout << "All tests of this fifth function have been passed." << endl;    
    return POINTS[5];
} 


// **************************************************************************
// int test6( )
//   Performs some basic tests of add_before and add_after for the case where the
//   current capacity has been reached (in case of array implementation).
//   Returns POINTS[6] if the tests are passed. Otherwise returns 0.
// **************************************************************************
int test6( ) {
    const size_t TESTSIZE = 30;
    sequence<Item> testa, testi;
    long items[2*TESTSIZE];
    size_t i;

    // Set up the items array to conatin 1...2*TESTSIZE.
    for (i = 1; i <= 2*TESTSIZE; i++)
        items[i-1] = i;
    
    cout << "Testing to see that add_after works correctly \n";
    //cout << "current capacity is exceeded." << endl;
    for (i = 1; i <= 2*TESTSIZE; i++)
        testa.add_after(i);
    if (!correct
        (testa, 2*TESTSIZE, 2*TESTSIZE-1, items)
        )
        return 0;

    cout << "Testing to see that add_before works correctly \n";
    //cout << "current capacity is exceeded." << endl;
    for (i = 2*TESTSIZE; i >= 1; i--)
        testi.add_before(i);
    if (!correct
        (testi, 2*TESTSIZE, 0, items)
        )
        return 0;

    // All tests passed
    cout << "All tests of this sixth function have been passed." << endl;    
    return POINTS[6];
}  


// **************************************************************************
// int test7( )
//   Performs some basic tests of == and !=
//   Returns POINTS[7] if the tests are passed. Otherwise returns 0.
// **************************************************************************
int test7( ) {
    const size_t TESTSIZE = 30;
    sequence<Item> testa, testi, empty;
    long items[2*TESTSIZE];
    size_t i;

    // Set up the items to contain 1...2*TESTSIZE.
    for (i = 1; i <= 2*TESTSIZE; i++)
        items[i-1] = i;
    
    cout << "Creating two sequences with exactly the same items using add_after\n";
    cout << "Testing ==\n";
    for (i = 1; i <= 2*TESTSIZE; i++) {
        testa.add_after(i);
        testi.add_after(i);
    }
    if ( !(testa == testi) ) return 0;
      
    cout << "The two sequences are ==\n";
    cout << "testa " << testa << endl;
    cout << "testi " << testi << endl;
      

    testa = empty;
    testi = empty;
    cout << "Creating two sequences with exactly the same items using add_before\n";
    cout << "Testing ==\n";
    for (i = 1; i <= 2*TESTSIZE; i++) {
        testa.add_before(i);
        testi.add_before(i);
    }
    if ( !(testa == testi) ) return 0;
    cout << "The two sequences are ==\n";
    cout << "testa " << testa << endl;
    cout << "testi " << testi << endl; 
    cout << "I will now call advance on one of the sequences\n";
    cout << "Testing !=\n";
    testa.advance();
    cout << "testa " << testa << endl;
    cout << "testi " << testi << endl; 
    if ( !(testa != testi) ) return 0;

    // All tests passed
    cout << "All tests of this seventh function have been passed." << endl;    
    return POINTS[7];
}  

// **************************************************************************
// int test8( )
//   Performs some basic tests of +
//   Returns POINTS[8] if the tests are passed. Otherwise returns 0.
// **************************************************************************
int test8( ) {
    const size_t TESTSIZE = 30;
    long items[2*TESTSIZE];
    sequence<Item> seq1, seq2, empty;  //each created empty with capacity of 1
    size_t i;

    cout << "Testing + for two empty sequences\n";
    try {
        seq1 = seq2 + empty; //This could cause exception if it fails
        cout << "Test passed ...\n\n";
    }
    catch ( ...) {
        cout << "Test failed ...\n\n";
        return 0;
    }

    // Set up the items to contain 1...2*TESTSIZE.
    for (i = 1; i <= 2*TESTSIZE; i++)
        items[i-1] = i;
 
    cout << "Creating two sequences with exactly the same items using add_after\n";
    cout << "Testing ==\n";
    for (i = 1; i <= 2*TESTSIZE; i++) {
        seq1.add_after(i);
        seq2.add_after(i);
    }
    if ( !(seq1 == seq2) ) return 0;
    cout << "Test passed ...\n\n";
    /*
    cout << "The two sequences are ==\n";
    cout << "seq1 " << seq1 << endl;
    cout << "seq2 " << seq2 << endl;
    */

    cout << "Emptying seq1, then executing seq1 = seq2 + empty\n";
    seq1 = empty;
    seq1 = seq2 + empty;

    cout << "Testing that seq1 has no current item\n";
    if ( seq1.is_current() ) return 0; //there should not be a current cursor
    cout << "Test passed ...\n\n";

    cout << "Setting cursor to beginning of seq1 and testing that all elements are correct\n";
    seq1.start();
    // If there is a cursor, check the items from cursor to end of the sequence.
    if (!test_items(seq1, 2*TESTSIZE, 0, items)) {
        cout << "Test of the sequence's items failed." << endl << endl;
        return 0;
    }
    cout << "Test passed ...\n\n";

    // Set up the items to contain 1...TESTSIZE,1...TESTSIZE
    for (i = 1; i <= TESTSIZE; i++) {
        items[i-1] = i;
        items[i-1+TESTSIZE] = i;
    }
    cout << "Filling each sequence with value 1 .. TESTSIZE using add_after\n";
    seq1 = seq2 = empty;
    for ( i = 1; i <= TESTSIZE; i++ ) {
        seq1.add_after(i);
        seq2.add_after(i);
    }

    cout << "Invoking + by seq1 = seq1 + seq2\n";
    seq1 = seq1 + seq2;
    cout << "Setting cursor to beginning of seq1 and testing that all elements are correct\n";
    seq1.start();
    // check the items from cursor to end of the sequence.
    if (!test_items(seq1, 2*TESTSIZE, 0, items)) {
        cout << "Test of the sequence's items failed." << endl << endl;
        return 0;
    }
    cout << "Test passed ...\n\n";

    // All tests passed
    cout << "All tests of this eighth function have been passed." << endl;    
    return POINTS[8];
}


// **************************************************************************
// int run_a_test
//      This is the test runner function -- returns points returned by 
//      function it is asked to run.
// **************************************************************************
int run_a_test(int number, const char message[], int test_function( ), int max) {
    int result;
    
    cout << endl << "START OF TEST " << number << ":" << endl;
    cout << message << " (" << max << " points)." << endl;
    result = test_function( );
    if (result > 0) {
        cout << "Test " << number << " got " << result << " points";
        cout << " out of a possible " << max << "." << endl;
    }
    else
        cout << "Test " << number << " failed." << endl;
    cout << "END OF TEST " << number << "." << endl << endl;
    
    return result;
}


// **************************************************************************
// int main( )
//   The main program calls all tests and prints the sum of all points
//   earned from the tests.
// **************************************************************************
int main( ) {
    int sum = 0;
    
    cout << "Running " << DESCRIPTION[0] << endl;

    sum += run_a_test(1, DESCRIPTION[1], test1, POINTS[1]); cout << sum << endl;
    sum += run_a_test(2, DESCRIPTION[2], test2, POINTS[2]); cout << sum << endl;
    sum += run_a_test(3, DESCRIPTION[3], test3, POINTS[3]); cout << sum << endl;
    sum += run_a_test(4, DESCRIPTION[4], test4, POINTS[4]); cout << sum << endl;
    sum += run_a_test(5, DESCRIPTION[5], test5, POINTS[5]); cout << sum << endl;
    sum += run_a_test(6, DESCRIPTION[6], test6, POINTS[6]); cout << sum << endl;
    sum += run_a_test(7, DESCRIPTION[7], test7, POINTS[7]); cout << sum << endl;
    sum += run_a_test(8, DESCRIPTION[8], test8, POINTS[8]); cout << sum << endl;

    cout << "If you submit this sequence to Prof. Haiduk now, you will have up to \n";
    cout << sum << " points out of the " << POINTS[0];
    cout << " points from this test program.\n";
    
    return EXIT_SUCCESS;

}
