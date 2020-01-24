//FILE:  simple_test.cxx

#include <cstdlib>   // Provides NULL and size_t
#include <iostream>  // Provides << operator
#include <stdexcept> // Provides exceptions
#include <string>    // Provides string operations

#include "sequence.h"

using namespace std;

int main() {

    sequence test;
    cout << "test after creation " << test << endl;
    sequence empty;
    // Test the add_after function to add an item in the middle of a sequence
    cout << "I am now using add_after to put 10,20,30 in an empty sequence.\n";
    cout << "Then I move the cursor to the start, advance once, ";
    cout << "and add_before 15." << endl;
    test = empty;
    cout << "test after assignment test = empty " << test << endl;
    test.add_after(10);
    cout << "test after adding 10 " << test << endl;
    test.add_after(20);
    cout << "test after adding 20 " << test << endl;
    test.add_after(30);
    cout << "test after adding 10, 20, 30 " << test << endl;
    test.start( );
    test.advance( );
    test.add_before(15);
    cout << "test after the operations " << test << endl << endl;

    // Test the add_before function to add an item in the middle of a sequence
    cout << "I am now using add_before to put 10,20,30 in an empty sequence.\n";
    cout << "Then I move the cursor to the start, advance ";
    cout << "and add_after 15." << endl;
    test = empty;
    test.add_before(10);
    test.add_before(20);
    test.add_before(30);
    test.start( );
    test.advance(); 
    test.add_after(15);
    cout << "test after the operations " << test << endl << endl;

    sequence::seq_iterator seqIterator;
    for ( seqIterator = test.begin();
          seqIterator != test.end(); 
          ++seqIterator) {
        cout << *seqIterator << " ";
    }
    cout << endl;

    for (sequence::value_type& element : test)
        cout << element << " ";
    cout << endl;

    return EXIT_SUCCESS;
}
