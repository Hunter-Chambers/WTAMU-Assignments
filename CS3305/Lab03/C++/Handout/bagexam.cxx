// FILE: bagexam.cxx
// Non-interactive test program for the bag class
//
// DESCRIPTION:
// Each function tests part of the bag class, returning some
// number of points to indicate how much of the test was passed.
// A description and result of each test is printed to cout.
// Maximum number of points awarded by this program is determined by the
// constants POINTS[1], POINTS[2]...
//
#include <iostream> // Provides cout
#include <cstring>  // Provides memcpy

#include "bag2.h"

using namespace std;

// Descriptions and points for each of the tests:
const size_t MANY_TESTS = 5;
const int POINTS[MANY_TESTS+1] = {
    100, // Total points for all tests.
     32,  // Test 1 points
     12,  // Test 2 points
     12,  // Test 3 points
     32,  // Test 4 points
     12   // Test 5 points
};

const char DESCRIPTION[MANY_TESTS+1][256] = {
    "tests for bag Class",
    "Testing insert and the constant member functions",
    "Testing the copy constructor and == methodr",
    "Testing the assignment operator",
    "Testing erase and erase_one functions",
    "Testing += method and non-instance method +"
};


// **************************************************************************
// bool correct(const bag& test, size_t n)
//   This function determines if the bag (test) is "correct" according to
//   this requirement:
//   a. it has exactly n items.
// **************************************************************************
bool correct(const bag& test, size_t n) {
    size_t i;
    bool answer = true;
    if (test.size( ) != n)
        answer = false;
    cout << (answer ? "Test passed.\n" : "Test failed.\n") << endl;
    return answer;
}


// **************************************************************************
// int test1( )
//   Performs some basic tests of insert and the constant member
//   functions. Returns POINTS[1] if the tests are passed. Otherwise returns 0.
// **************************************************************************
int test1( ) {
    const size_t TESTSIZE = 3000;
    bag test;
    char test_letter = 'A';
    size_t i;
    int next;

    cout << test_letter++ << ". ";
    cout << "Testing size for an empty bag.";
    cout << endl;
    if (!correct(test, 0)) return 0;

    cout << test_letter++ << ". ";
    cout << "Adding the number 4 to the bag, and then testing\n";
    cout << "   size." << endl;
    test.insert(4);
    if (!correct(test, 1)) return 0;

    cout << test_letter++ << ". ";
    cout << "Inserting the number 2 into the bag.\n";
    cout << "   Then checking size." << endl;
    test.insert(2);
    if (!correct(test, 2)) return 0;

    cout << test_letter++ << ". ";
    cout << "Inserting the number 1 into the bag.\n";
    cout << "   Then checking size." << endl;
    test.insert(1);
    if (!correct(test, 3)) return 0;

    cout << test_letter++ << ". ";
    cout << "Inserting the number 3 into the bag.\n";
    cout << "   Then checking size." << endl;
    test.insert(3);
    if (!correct(test, 4)) return 0;

    cout << test_letter++ << ". ";
    cout << "Inserting another 2 into the bag.\n";
    cout << "   Then checking size." << endl;
    test.insert(2);
    if (!correct(test, 5)) return 0;
    cout << "   Then checking occurrences of 2." << endl;
    if (test.occurrences(2) != 2) return 0;
    else cout << "Test passed." ;

    cout << test_letter++ << ". ";
    cout << "Inserting the numbers 5, 6, and 7 into the bag.\n";
    cout << "   Then checking size.";
    cout << endl;
    test.insert(5);
    test.insert(6);
    test.insert(7);
    if (!correct(test, 8)) return 0;

    cout << test_letter++ << ". ";
    cout << "Inserting two more 2's into the bag.\n";
    cout << "   and then checking occurrences of 2's\n\n";
    test.insert(2); test.insert(2);
    if ( test.occurrences(2) != 4) return 0;
    else cout << "Test passed.";

    cout << test_letter++ << ". ";
    cout << "Inserting " << TESTSIZE << " random items between 0 and 49\n";
    cout << "   and then checking size.";
    cout << endl;
    for (i = 0; i < TESTSIZE; i++) {
        next = rand( ) % 50;
        test.insert(next);
    }
    if (!correct(test, TESTSIZE+10)) return 0;

    return POINTS[1];
}



// **************************************************************************
// int test2( )
//   Performs some tests of the copy constructor and == method.
//   Returns POINTS[2] if the tests are passed. Otherwise returns 0.
// **************************************************************************
int test2( ) {
    bag test;

    cout << "A. Testing that copy constructor works okay for empty bag...\n";
    bag copy1(test);
    if (!correct(copy1, 0)) return 0;

    cout << "B. Testing copy constructor with 4-item bag...";
    cout << flush;
    test.insert(1);
    test.insert(1);
    test.insert(1);
    test.insert(1);
    bag copy2(test);
    cout << "   and now testing the == method...\n";
    if ( !(test == copy2) || !(copy2 == test) ) {
        cout << "Test failed.\n\n";
        return 0;
    }
    else cout << "Test passed.\n\n";

    test.insert(1); // Alter the original, but not the copy
    cout << "C. Then checking size of copy\n";
    if (!correct(copy2, 4)) return 0;
    cout << "D. Altering original but not the copy\n";
    if (!correct(test, 5)) return 0;

    cout << "Copy constructor seems okay." << endl;
    return POINTS[2];
}


// **************************************************************************
// int test3( )
//   Performs some tests of the assignment operator.
//   Returns POINTS[3] if the tests are passed. Otherwise returns 0.
// **************************************************************************
int test3( ) {
    bag test;
    char *oldbytes = new char[sizeof(bag)];
    char *newbytes = new char[sizeof(bag)];
    size_t i;

    cout << "A. Testing that assignment operator works okay for empty bag...\n";
    bag copy1;
    copy1.insert(1);
    copy1 = test;
    if (!correct(copy1, 0))  {
        delete [] oldbytes;
        delete [] newbytes;
        return 0;
    }

    cout << "B. Testing assignment operator with 4-item bag...\n";
    test.insert(1);
    test.insert(1);
    test.insert(1);
    test.insert(1);
    bag copy2;
    copy2 = test;
    test.insert(1); // Alter the original, but not the copy
    cout << "   altering original by an insertion...\n";
    if (test.occurrences(1) != 5 || copy2.occurrences(1) != 4) {
        cout << "Test failed.\n";
        delete [] oldbytes;
        delete [] newbytes;
        return 0;
    }
    cout << "Test passed.\n";
    cout << "   testing size of assigned to...\n";
    if (!correct(copy2, 4)) {
        delete [] oldbytes;
        delete [] newbytes;
        return 0;
    }
    cout << "   testing size of original...\n";
    if (!correct(test, 5)) {
        delete [] oldbytes;
        delete [] newbytes;
        return 0;
    }

    cout << "C. Testing assignment operator for a self-assignment...";
    cout << flush;
    memcpy(oldbytes, &test, sizeof(bag));
    test = test;
    memcpy(newbytes, &test, sizeof(bag));
    for (i=0; i < sizeof(bag); i++)
        if (oldbytes[i] != newbytes[i]) {
            cout << "failed." << endl;
            delete [] oldbytes;
            delete [] newbytes;
            return 0;
        }
    cout << "passed.\n";        

    delete [] oldbytes;
    delete [] newbytes;

    cout << "Assignment operator seems okay." << endl;
    return POINTS[3];
}


// **************************************************************************
// int test4( )
//   Performs basic tests for the erase functions
//   Returns POINTS[4] if the tests are passed.
//   Otherwise returns 0.
// **************************************************************************
int test4( ) {
    bag test;

    cout << "Testing erase from empty bag (should have no effect) ...";
    cout << flush;
    test.erase(0);
    if (!correct(test, 0)) return 0;
        
    cout << "Inserting these: 8 6 10 1 7 10 15 3 13 2 5 11 14 4 12" << endl;
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

    cout <<"Now testing capacity -- should be 16\n";
    if ( test.getCapacity() != 16) {
        cout << "Test failed.\n\n";
        cout << test << endl;
        return 0;
    }
    else cout << "Test passed.\n\n";

    cout << "Erasing 0 (which is not in bag, so bag should be unchanged) ...\n";
    cout << flush;
    if (test.erase_one(0)) {
        cout << "Test failed" << endl;
        return 0;
    }
    if (!correct(test, 15)) return 0;

    cout << "Erasing the 6 ..." << flush;
    test.erase(6);
    if (!correct(test, 14)) return 0;

    cout << "Erasing one 10 ..." << flush;
    if (!test.erase_one(10)) {
        cout << "Test failed" << endl;
        return 0;
    }
    if (!correct(test, 13)) return 0;

    cout << "Erasing the 1 ..." << flush;
    test.erase(1);
    if (!correct(test, 12)) return 0;

    cout << "Erasing the 15 ..." << flush;
    test.erase(15);
    if (!correct(test, 11)) return 0;

    cout << "Erasing the 5 ..." << flush;
    test.erase(5);
    if (!correct(test, 10)) return 0;

    cout << "Erasing the 11 ..." << flush;
    test.erase(11);
    if (!correct(test, 9)) return 0;

    cout << "Erasing the 3 ..." << flush;
    test.erase(3);
    if (!correct(test, 8)) return 0;

    cout << "Erasing the 13 ..." << flush;
    test.erase(13);
    if (!correct(test, 7)) return 0;

    cout << "Erasing the 2 ..." << flush;
    test.erase(2);
    if (!correct(test, 6)) return 0;

    cout << "Erasing the one and only 14 ..." << flush;
    test.erase_one(14);
    if (!correct(test, 5)) return 0;

    cout << "Erasing the 4 ..." << flush;
    test.erase(4);
    if (!correct(test, 4)) return 0;

    cout << "Erasing the 12 ..." << flush;
    test.erase(12);
    if (!correct(test, 3)) return 0;

    cout << "Erasing the 8 ..." << flush;
    test.erase(8);
    if (!correct(test, 2)) return 0;

    cout << "Erasing the 7 ..." << flush;
    test.erase(7);
    if (!correct(test, 1)) return 0;

    cout << "Erasing the other 10 ..." << flush;
    if ( !test.erase_one(10) ) {
        cout << "Test failed ...\n";
        return 0;
    }
    if (!correct(test, 0)) return 0;

    cout << "Testing capacity again...";
    if ( test.getCapacity() != 16 ) {
        cout << "Test failed.\n\n";
        return 0;
    }

    cout << "Now trimming to size\n";
    test.trimToSize();
    if ( test.getCapacity() != 1 ) {
        cout << "Test failed.\n\n";
        cout << test << endl;
        return 0;
    }
    cout << "Test passed.\n\n";

    cout << "Now trimming to size again\n";
    test.trimToSize();
    if ( test.getCapacity() != 1 ) {
        cout << "Test failed.\n\n";
        cout << test << endl;
        return 0;
    }
    cout << "Test passed.\n\n";

    cout << "Inserting value 5000 into the bag ... \n" << flush;
    cout << "Inserting three 5's into the bag and then erasing all of them ..." << flush;
    test.insert(5000);
    test.insert(5); test.insert(5); test.insert(5);
    test.erase(5);
    if (!correct(test,1)) return 0;
    
    cout << "Erase functions seem okay." << endl;
    return POINTS[4];
}

// **************************************************************************
// int test5( )
//   Performs basic tests for the += and + functions
//   Returns POINTS[5] if the tests are passed.
//   Otherwise returns 0.
// **************************************************************************
int test5() {

    bag test1, test2, test3;

    cout << "Testing the += function with empty bags: test1 += test" << endl;
    test1 += test2;

    cout << "Testing the + function test3 = test1 + test2" << endl;
    test3 = test1 + test2;

    cout << "Inserting 2000 1's into test1 and 2000 2's into test2\n";
    for (int i = 0; i < 2000; ++i) {
        test1.insert(1);
        test2.insert(2);
    }
    cout << "Now testing the += function ...\n";
    test1 += test2;
    cout << "  and now testing for occurrences of 1's and 2's in test1 ...\n";
    if ( test1.occurrences(1) == 2000 && test1.occurrences(2) == 2000 ) {
        cout << "Test passed.\n\n";
    } else {
        cout << "Test failed.\n\n";
        return 0;
    }

    cout << "Now testing the + function ...\n";
    test3 = test2 + test2;
    cout << "  and now testing for occurrences of 2's in test3 ...\n";
    if ( test3.occurrences(2) == 4000 ) {
        cout << "Test passed.\n\n";
    } else {
        cout << "Test failed.\n\n";
        return 0;
    }

    cout << "+= and + functions seem okay." << endl;
    return POINTS[5];
}

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
    char reply;
    bool done_erase;
    bool done_union;
    
    cout << "Running " << DESCRIPTION[0] << endl;
    cout << "Have you implemented erase yet? [Y or N]: ";
    cin >> reply;
    done_erase = (reply == 'Y' || reply == 'y');
    cout << "Have you implemented += and + yet? [Y or N]: ";
    cin >> reply;
    done_union = (reply == 'Y' || reply == 'y');

    sum += run_a_test(1, DESCRIPTION[1], test1, POINTS[1]);
    sum += run_a_test(2, DESCRIPTION[2], test2, POINTS[2]);
    sum += run_a_test(3, DESCRIPTION[3], test3, POINTS[3]);
    if (done_erase)
        sum += run_a_test(4, DESCRIPTION[4], test4, POINTS[4]);
    if (done_union)
        sum += run_a_test(5, DESCRIPTION[5], test5, POINTS[5]);

    cout << "If you submit your bag to Prof. Haiduk now, you will have\n";
    cout << sum << " points out of the " <<  POINTS[0];
    cout << " points from this test program.\n";
    
    return EXIT_SUCCESS;

}

