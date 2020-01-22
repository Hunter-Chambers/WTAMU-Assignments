// FILE: tabtest.cxx
// An interactive test program for the table ADT.

#include <ctype.h>    // Provides toupper
#include <iostream>   // Provides cin, cout
#include <iomanip>    // Provides endl
#include <cstdlib>    // Provides EXIT_SUCCESS and size_t
#include "table.h"    // Provides the table class

using namespace std;

// Struct definition for the RecordType, which has a key and a double.
struct RecordType
{
    int key;
    double data;
};

// PROTOTYPES for functions used by this test program:
void print_menu( );
// Postcondition: A menu of choices for this program has been written to cout.

char get_user_command( );
// Postcondition: The user has been prompted to enter a one character command.
// The next character has been read (skipping blanks and newline characters), 
// and this character has been returned.

RecordType get_record( ); 
// Postcondition: The user has been prompted to enter data for a record. The
// key has been read, echoed to the screen, and returned by the function.

int get_key( );
// Postcondition: The user has been prompted to enter a key for a record. The
// items have been read, echoed to the screen, and returned by the function.

void pressEnterToContinue( );

int main( ) {
// Library facilities used: iostream, iomanip
    table<RecordType> test;    // A table that we'll perform tests on
    char choice;               // A command character entered by the user
    bool found;                // Value returned by find function
    RecordType result;         // Value returned by find function
    
    system("clear");
    cout << "\t\t" << "I have initialized an empty table. Each record in the table\n";
    cout << "\t\t" << "has an integer key and a real number as data." << endl;
    cout << "\t\t" <<"Press enter to continue . . .";
    cin.ignore();
    
    do {
        system("clear");
        print_menu( );
        choice = toupper(get_user_command( ));
        switch (choice) {
            case 'S': cout << "\t\t" << "The table size is " << test.size( ) << endl;
                      cout << "\t\t" << "The table now is:\n";
                      cout << test;
                      pressEnterToContinue();
                      break;
            case 'I': test.insert(get_record( ));
                      cout << "\t\t"  << "The record has been inserted." << endl;
                      cout << "\t\t" << "The table now is:\n";
                      cout << test;
                      pressEnterToContinue();
                      break;
            case 'R': test.remove(get_key( ));
                      cout << "\t\t" << "Remove has been called with that key." << endl;
                      cout << "\t\t" << "The table now is:\n";
                      pressEnterToContinue();
                      cout << test;
                      break;     
            case '?': if (test.is_present(get_key( )))
                          cout<< "\t\t"  << "That key is present." << endl;
                      else
                          cout << "\t\t" << "That key is not present." << endl;
                      pressEnterToContinue();
                      break;
            case 'F': test.find(get_key( ), found, result);
                      if (found)
                          cout << "\t\t" << "The key's data is: " << result.data << endl;
                      else
                          cout << "\t\t" << "That key is not present." << endl;
                      pressEnterToContinue();
                      break;
            case 'Q': cout << "\t\t" << "Ridicule is the best test of truth." << endl;
                      pressEnterToContinue();
                      break;
            default:  cout << "\t\t" << choice << " is invalid. Sorry." << endl;
                      pressEnterToContinue();
        }
    }
    while ((choice != 'Q'));

    return EXIT_SUCCESS;
}

void print_menu( ) {
// Library facilities used: iostream, iomanip
    cout << endl; // Print blank line before the menu
    cout << "\t\tThe following choices are available: " << endl;
    cout << "\t\t S   Print the result from the size( ) function" << endl;
    cout << "\t\t I   Insert a new record with the insert(...) function" << endl;
    cout << "\t\t R   Remove a record with the remove(...) function" << endl;
    cout << "\t\t ?   Check whether a particular key is present" << endl;
    cout << "\t\t F   Find the data associated with a specified key" << endl;
    cout << "\t\t Q   Quit this test program" << endl;
}

char get_user_command( ) {
// Library facilities used: iostream
    char command;

    cout << "\t\t" << "Enter choice: ";
    cin >> command; // Input of characters skips blanks and newline character

    return command;
}

RecordType get_record( ) {
// Library facilities used: iostream, iomanip
    RecordType result;
    
    cout << "\t\t" << "Please enter a real number for a record's data: ";
    cin  >> result.data;
    cout << "\t\t" << result.data << " has been read." << endl;
    result.key = get_key( );
    return result;
}

int get_key( ) {
// Library facilities used: iostream, iomanip
    int key;
    
    do {
        cout << "\t\t" << "Please enter a non-negative integer for a key: ";
        cin  >> key;
    }
    while (key < 0);

    cout << "\t\t" << key << " has been read." << endl;
    return key;
}

void pressEnterToContinue( ) {
// Library facilities used:  iostream
    cout << "\n\t\tPress enter to continue  . . . ";
    cin.ignore(); cin.ignore();
}
