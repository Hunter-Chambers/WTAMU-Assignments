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
#include <iostream>   // Provides cout
#include <cstring>    // Provides memcpy
#include "bag.h"

using namespace std;

// Descriptions and points for each of the tests:
const size_t MANY_TESTS = 6;
const int POINTS[MANY_TESTS+1] = {
   300, // Total points for all tests.
    80,  // Test 1 points
    40,  // Test 2 points
    40,  // Test 3 points
    40,  // Test 4 points
    40,  // Test 5 points
    60   // Test 6 points
};
const char DESCRIPTION[MANY_TESTS+1][256] = {
    "tests for bag class",
    "Testing insert and the constant member functions",
    "Testing the copy constructor",
    "Testing the assignment operator",
    "Testing erase_one function",
    "Testing erase function",
    "Testing operators + and +="
};


// **************************************************************************
// bool correct(
//   const bag& test,
//   bag<int>::size_type n ,
//  bag<int>::size_type count[ ],
//   int max
//    
//   This function determines if the bag (test) is "correct" according to
//   these requirements:
//   a. it has exactly n items.
//   b. For each i in the range 0...(max-1), the bag contains count[i]
//      copies of i.
// **************************************************************************
bool correct(
   const bag<int>& test, bag<int>::size_type n, bag<int>::size_type count[], int max ) {

    int i;
    bool answer = true;
    if (test.size( ) != n)
        answer = false;
    else
        for (i = 0; answer && (i <= max); i++)
            if (count[i] != test.count(i))
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
    const bag<int>::size_type TESTSIZE = 3000;
    bag<int> test;
    bag<int>::size_type items[8] = { 0, 0, 0, 0, 0, 0, 0, 0 };
    bag<int>::size_type rand_items[50];
    char test_letter = 'A';
    bag<int>::size_type i;
    int next;

    cout << test_letter++ << ". ";
    cout << "Testing size and count for an empty bag.";
    cout << endl;
    if (!correct(test, 0, items, 7)) return 0;

    cout << test_letter++ << ". ";
    cout << "Adding the number 4 to the bag, and then testing\n";
    cout << "   size and count.";
    cout << endl;
    test.insert(4);
    items[4]++;
    if (!correct(test, 1, items, 7)) return 0;

    cout << test_letter++ << ". ";
    cout << "Inserting the number 2 into the bag.\n";
    cout << "   Then checking size and count.";
    cout << endl;
    test.insert(2);
    items[2]++;
    if (!correct(test, 2, items, 7)) return 0;

    cout << test_letter++ << ". ";
    cout << "Inserting the number 1 into the bag.\n";
    cout << "   Then checking size and count.";
    cout << endl;
    test.insert(1);
    items[1]++;
    if (!correct(test, 3, items, 7)) return 0;

    cout << test_letter++ << ". ";
    cout << "Inserting the number 3 into the bag.\n";
    cout << "   Then checking size and count.";
    cout << endl;
    test.insert(3);
    items[3]++;
    if (!correct(test, 4, items, 7)) return 0;

    cout << test_letter++ << ". ";
    cout << "Inserting another 2 into the bag.\n";
    cout << "   Then checking size and count.";
    cout << endl;
    test.insert(2);
    items[2]++;
    if (!correct(test, 5, items, 7)) return 0;

    cout << test_letter++ << ". ";
    cout << "Inserting the numbers 5, 6, and 7 into the bag.\n";
    cout << "   Then checking size and count.";
    cout << endl;
    test.insert(5);
    test.insert(6);
    test.insert(7);
    items[5]++;
    items[6]++;
    items[7]++;
    if (!correct(test, 8, items, 7)) return 0;

    cout << test_letter++ << ". ";
    cout << "Inserting " << TESTSIZE << " random items between 0 and 49\n";
    cout << "   and then checking size and count.";
    cout << endl;
    for (i = 0; i < 50; i++)
        rand_items[i] = (i < 8) ? items[i] : 0;
    for (i = 0; i < TESTSIZE; i++) {
        next = rand( ) % 50;
        rand_items[next]++;
        test.insert(next);
    }
    if (!correct(test, TESTSIZE+8, rand_items, 49)) return 0;

    return POINTS[1];
}



// **************************************************************************
// int test2( )
//   Performs some tests of the copy constructor.
//   Returns POINTS[2] if the tests are passed. Otherwise returns 0.
// **************************************************************************
int test2( ) {
    bag<int> test;
    bag<int>::size_type items[4] = { 0, 0, 0, 0 };

    cout << "A. Testing that copy constructor works okay for empty bag...";
    cout << flush;
    bag<int> copy1(test);
    if (!correct(copy1, 0, items, 3)) return 0;

    cout << "B. Testing copy constructor with 4-item bag...";
    cout << flush;
    test.insert(1);
    test.insert(1);
    test.insert(1);
    test.insert(1);
    bag<int> copy2(test);
    test.insert(1); // Alter the original, but not the copy
    items[1] = 4; // Should be four 1's in the copy2.
    if (!correct(copy2, 4, items, 3)) return 0;

    cout << "Copy constructor seems okay." << endl;
    return POINTS[2];
}


// **************************************************************************
// int test3( )
//   Performs some tests of the assignment operator.
//   Returns POINTS[3] if the tests are passed. Otherwise returns 0.
// **************************************************************************
int test3( ) {
    bag<int> test;
    bag<int>::size_type items[4] = { 0, 0, 0, 0 };
    char *oldbytes = new char[sizeof(bag<int>)];
    char *newbytes = new char[sizeof(bag<int>)];
    bag<int>::size_type i;

    cout << "A. Testing that assignment operator works okay for empty bag...";
    cout << flush;
    bag<int> copy1;
    copy1.insert(1);
    copy1 = test;
    if (!correct(copy1, 0, items, 3)) {
        delete [] oldbytes;
        delete [] newbytes;
        return 0;
    }

    cout << "B. Testing assignment operator with 4-item bag...";
    cout << flush;
    test.insert(1);
    test.insert(1);
    test.insert(1);
    test.insert(1);
    bag<int> copy2;
    copy2 = test;
    test.insert(1); // Alter the original, but not the copy
    items[1] = 4; // Should be four 1's in the copy2.
    if (!correct(copy2, 4, items, 3)) {
        delete [] oldbytes;
        delete [] newbytes;
        return 0;
    }

    cout << "C. Testing assignment operator for a self-assignment...";
    cout << flush;
    /*
    memcpy(oldbytes, &test, sizeof(bag<int>));
    test = test;
    memcpy(newbytes, &test, sizeof(bag<int>));
    */
    memcpy(oldbytes, &copy2, sizeof(bag<int>));
    copy2 = copy2;
    memcpy(newbytes, &copy2, sizeof(bag<int>));
    for (i=0; i < sizeof(bag<int>); i++)
        if (oldbytes[i] != newbytes[i]) {
            cout << "failed." << endl;
            delete [] oldbytes;
            delete [] newbytes;
            return 0;
        }
    cout << "passed.\n";        

    cout << "Assignment operator seems okay." << endl;

    delete [] oldbytes;
    delete [] newbytes;
    return POINTS[3];
}


// **************************************************************************
// int test4( )
//   Performs basic tests for the erase_one function.
//   Returns POINTS[4] if the tests are passed.
//   Otherwise returns 0.
// **************************************************************************
int test4( ) {
    bag<int> test;
    bag<int>::size_type count[16] = { 0,1,1,1,1,1,1,1,1,0,2,1,1,1,1,1 };
    bag<int>::size_type cempty[16]= { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };

    cout << "Testing removal from empty bag (should have no effect) ...";
    cout << flush;
    if (test.erase_one(0)) {
	cout << "Erase function incorrectly returned true." << endl;
	return 0;
    }
    if (!correct(test, 0, cempty, 15)) return 0;
        
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
    if (!correct(test, 15, count, 15)) return 0;

    cout << "Removing 0 (which is not in bag, so bag should be unchanged) ...";
    cout << flush;
    if (test.erase_one(0)) {
	cout << "Erase function incorrectly returned true." << endl;
	return 0;
    }
    if (!correct(test, 15, count, 15)) return 0;

    cout << "Removing the 6 ..." << flush;
    if (!test.erase_one(6)) {
	cout << "Erase function incorrectly returned false." << endl;
	return 0;
    }
    count[6] = 0;
    if (!correct(test, 14, count, 15)) return 0;

    cout << "Removing one 10 ..." << flush;
    test.erase_one(10);
    count[10]--;
    if (!correct(test, 13, count, 15)) return 0;

    cout << "Removing the 1 ..." << flush;
    test.erase_one(1);
    count[1]--;
    if (!correct(test, 12, count, 15)) return 0;

    cout << "Removing the 15 ..." << flush;
    test.erase_one(15);
    count[15]--;
    if (!correct(test, 11, count, 15)) return 0;

    cout << "Removing the 5 ..." << flush;
    test.erase_one(5);
    count[5]--;
    if (!correct(test, 10, count, 15)) return 0;

    cout << "Removing the 11 ..." << flush;
    test.erase_one(11);
    count[11]--;
    if (!correct(test, 9, count, 15)) return 0;

    cout << "Removing the 3 ..." << flush;
    test.erase_one(3);
    count[3]--;
    if (!correct(test, 8, count, 15)) return 0;

    cout << "Removing the 13 ..." << flush;
    test.erase_one(13);
    count[13]--;
    if (!correct(test, 7, count, 15)) return 0;

    cout << "Removing the 2 ..." << flush;
    test.erase_one(2);
    count[2]--;
    if (!correct(test, 6, count, 15)) return 0;

    cout << "Removing the 14 ..." << flush;
    test.erase_one(14);
    count[14]--;
    if (!correct(test, 5, count, 15)) return 0;

    cout << "Removing the 4 ..." << flush;
    test.erase_one(4);
    count[4]--;
    if (!correct(test, 4, count, 15)) return 0;

    cout << "Removing the 12 ..." << flush;
    test.erase_one(12);
    count[12]--;
    if (!correct(test, 3, count, 15)) return 0;

    cout << "Removing the 8 ..." << flush;
    test.erase_one(8);
    count[8]--;
    if (!correct(test, 2, count, 15)) return 0;

    cout << "Removing the 7 ..." << flush;
    test.erase_one(7);
    count[7]--;
    if (!correct(test, 1, count, 15)) return 0;

    cout << "Inserting more 10's ..." << flush;
    test.insert(10);
    test.insert(10);
    test.insert(10);
    test.insert(10);
    test.insert(10);
    test.insert(10);

    cout << "Erase_one functions passed my tests." << endl;
    return POINTS[4];
}

// **************************************************************************
// int test5( )
//   Performs basic tests for the erase_one function.
//   Returns POINTS[5] if the tests are passed.
//   Otherwise returns 0.
// **************************************************************************
int test5( ) {
    bag<int> test;
        
    cout << "Inserting these: 8 6 10 1 7 10 15 3 13 2 5 9 11 14 4 12" << endl;
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
    test.insert( 9);
    test.insert(11);
    test.insert(14);
    test.insert( 4);
    test.insert(12);
    test.display();

    cout << "Inserting five more 10s" << endl;
    test.insert(10);
    test.insert(10);
    test.insert(10);
    test.insert(10);
    test.insert(10);
    test.display();

    cout << "Removing all 10's ...\n" << flush;
    int howMany = test.erase(10);
    test.display(); cout << "\n";
    if ( howMany != 7 ) return 0;

    cout << "Removing 15 ...\n" << flush;
    howMany = test.erase(15);
    test.display();
    if ( howMany != 1 ) return 0;

    cout << "Attempting to remove 15 again ...\n" << flush;
    howMany = test.erase(15);
    test.display();
    if ( howMany != 0 ) return 0;
    
    cout << "Erase function passed my tests." << endl;
    return POINTS[5];
}

// **************************************************************************
// int test6( )
//   Performs basic tests for the operators = and +=.
//   Returns POINTS[6] if the tests are passed.
//   Otherwise returns 0.
// **************************************************************************
int test6( ) {
    bag<int> bag1, bag2, bag3, bag4;
    bag<int>::size_type count[17]  = { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1 };
    bag<int>::size_type count2[17] = { 0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2 };
    bag<int>::size_type count3[17] = { 0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3 };
    bag<int>::size_type cempty[17] = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };

    cout << "Inserting these: 8 6 10 1 7 16 15 3 13 2 5 9 11 14 4 12" << endl;
    bag1.insert( 8);
    bag1.insert( 6);
    bag1.insert(10);
    bag1.insert( 1);
    bag1.insert( 7);
    bag1.insert(16);
    bag1.insert(15);
    bag1.insert( 3);
    bag1.insert(13);
    bag1.insert( 2);
    bag1.insert( 5);
    bag1.insert( 9);
    bag1.insert(11);
    bag1.insert(14);
    bag1.insert( 4);
    bag1.insert(12);
    cout << "bag1's elements in order:\n[ ";
    for ( int number : bag1 ) 
        cout << number << " ";
    cout << "]\n";
    if ( !correct(bag1, 16, count, 16) ) return 0;

    cout << "Assigning bag3 = bag1 + bag2" << endl;
    bag3 = bag1 + bag2;
    cout << "bag3's elements in order:\n[ ";
    for ( int number : bag3 ) 
        cout << number << " ";
    cout << "]\n";
    if ( !correct(bag3, 16, count, 16) ) return 0;

    cout << "Assigning bag3 = bag1 + bag1" << endl;
    bag3 = bag1 + bag1;
    cout << "bag3's elements in order:\n[ ";
    for ( int number : bag3 ) 
        cout << number << " ";
    cout << "]\n";
    if ( !correct(bag3, 32, count2, 16) ) return 0;

    cout << "Assigning bag3 = bag1 + bag2 + bag1" << endl;
    bag3 = bag1 + bag2 + bag1;
    cout << "bag3's elements in order:\n[ ";
    for ( int number : bag3 ) 
        cout << number << " ";
    cout << "]\n";
    if ( !correct(bag3, 32, count2, 16) ) return 0;

    cout << "Assigning bag3 = bag4 + bag4" << endl;
    bag3 = bag4 + bag4;
    cout << "bag3's elements in order:\n[ ";
    for ( int number : bag3 ) 
        cout << number << " ";
    cout << "]\n";
    if ( !correct(bag3, 0, cempty, 16) ) return 0;

    cout << "bag3 += bag1" << endl;
    bag3 += bag1;
    cout << "bag3's elements in order:\n[ ";
    for ( int number : bag3 ) 
        cout << number << " ";
    cout << "]\n";
    if ( !correct(bag3, 16, count, 16) ) return 0;

    cout << "bag3 += bag3" << endl;
    bag3 += bag3;
    cout << "bag3's elements in order:\n[ ";
    for ( int number : bag3 ) 
        cout << number << " ";
    cout << "]\n";
    if ( !correct(bag3, 32, count2, 16) ) return 0;

    cout << "Emptying bag3 and then setting bag3 = bag1" << endl;
    cout << "next, bag3 += bag3 + bag1" << endl;
    bag3 = bag1;
    bag3 += bag3 + bag1;
    cout << "bag3's elements in order:\n[ ";
    for ( int number : bag3 ) 
        cout << number << " ";
    cout << "]\n";
    if ( !correct(bag3, 48, count3, 16) ) return 0;

    return POINTS[6];
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

    sum += run_a_test(1, DESCRIPTION[1], test1, POINTS[1]);
    sum += run_a_test(2, DESCRIPTION[2], test2, POINTS[2]);
    sum += run_a_test(3, DESCRIPTION[3], test3, POINTS[3]);
    sum += run_a_test(4, DESCRIPTION[4], test4, POINTS[4]);
    sum += run_a_test(5, DESCRIPTION[5], test5, POINTS[5]);
    sum += run_a_test(6, DESCRIPTION[6], test6, POINTS[6]);

    cout << "If you submit your bag implementation to Prof. Haiduk now, you will have\nup to ";
    cout << sum << " points out of the " << POINTS[0];
    cout << " points from this test program -- of course \ndepending on how well you implemented the required methods.\n";
    
    return EXIT_SUCCESS;

}

