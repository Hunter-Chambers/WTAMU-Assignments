// FILE: sequence_test.cxx
// An interactive test program for the new sequence class
#include <cctype>       // Provides toupper
#include <iostream>     // Provides cout and cin
#include <cstdlib>      // Provides EXIT_SUCCESS and system
#include "sequence.h"   // With value_type defined as double
using namespace std;

typedef sequence::size_type size_type;
typedef sequence::value_type value_type;

// PROTOTYPES for functions used by this test program:
void print_menu( );
// Postcondition: A menu of choices for this program has been written to cout.

char get_user_command( );
// Postcondition: The user has been prompted to enter a one character command.
// The next character has been read (skipping blanks and newline characters), 
// and this character has been returned.

void show_sequence(const sequence& display);
// Postcondition: The items on display have been printed to cout.

double get_number( );
// Postcondition: The user has been prompted to enter a number. The
// number has been read, echoed to the screen, and returned by the function.

size_t get_whole_number( );
// Postcondition:  The user has been prompted to enter a non-negative whole number.
// The number has been read, echoed to the screen, and returned by the function.

void pressEnterToContinue( );



int main( ) {
    sequence test1, test2, test3; // Sequences that we'll perform tests on
    char choice;   // A command character entered by the user
    
    system("clear");
    cout << "I have initialized empty sequences of numbers." << endl;
    cout << "\tPress enter to continue . . . ";
    cin.ignore();

    do {
        system("clear");
        print_menu( );
        choice = get_user_command( );
        switch (choice) {
            case 's': test1.start( );
                      break;
            case 'a': if (test1.is_current())
                          test1.advance( );
                      else
                          cout << "Cannot advance since cursor is not current" << endl;
                      pressEnterToContinue();
                      break;
            case '?': if (test1.is_current( ))
                          cout << "There is an item." << endl;
                      else 
                          cout << "There is no current item." << endl;
                      pressEnterToContinue();
                      break;
            case 'c': if (test1.is_current( ))
                           cout << "Current item is: " << test1.get_current( ) << endl;
                      else
                          cout << "There is no current item." << endl;
                      pressEnterToContinue();
                      break;
            case '1': show_sequence(test1);
                      pressEnterToContinue();
                      break;
            case '2': show_sequence(test2);
                      pressEnterToContinue();
                      break;
            case '3': show_sequence(test3);
                      pressEnterToContinue();
                      break;
            case 'S': cout << "Size is " << test1.size( ) << '.' << endl;
                      pressEnterToContinue();
                      break;
            case 'I': test1.add_before(get_number( ));
                      break;
            case 'A': test1.add_after(get_number( ));
                      break;
            case 'R': test1.remove_current( );
                      cout << "The current item has been removed." << endl;
                      pressEnterToContinue();
                      break;   
            case '=': test2 = test1;
                      break;
            case '+': test3 = test1 + test2;
                      break;
            case 'e': cout << "test1 == test3? " << (test1==test3 ? "true" : "false") << endl;
                      pressEnterToContinue();
                      break;
            case 'i': test1.ensure_capacity(get_whole_number());
                      break;
            case 't': test1.trim_to_size();
                      break;
            case 'C': test3 = sequence(test1);
                      break;
            case 'q':
            case 'Q': cout << "\t\tRidicule is the best test of truth." << endl;
                      pressEnterToContinue();
                      break;
            default:  cout << "\t\t" << choice << " is invalid." << endl;
                      pressEnterToContinue();
                      break;
        }
    }
    while ((choice != 'Q') && (choice != 'q'));

    return EXIT_SUCCESS;
}

void print_menu( ) {
// Library facilities used: iostream
    cout << endl; // Print blank line before the menu
    cout << "\n\t\tThe following choices are available: " << endl;
    cout << "\t\t s   Activate the start( ) function for test1" << endl;
    cout << "\t\t a   Activate the advance( ) function for test1" << endl;
    cout << "\t\t ?   Print the result from the is_item( ) for test1" << endl;
    cout << "\t\t c   Print the result from the current( ) for test1" << endl;
    cout << "\t\t 1   Print a copy of test1" << endl;
    cout << "\t\t 2   Print a copy of test2" << endl;
    cout << "\t\t 3   Print a copy of test3" << endl;
    cout << "\t\t S   Print the result from the size( ) function for test1" << endl;
    cout << "\t\t I   Insert a new number with the insert(...) function for test1" << endl;
    cout << "\t\t A   Attach a new number with the attach(...) function for test1" << endl;
    cout << "\t\t R   Activate the remove_current( ) function for test1" << endl;
    cout << "\t\t =   Assign test1 to test2" << endl;
    cout << "\t\t +   Assign test3 expression test1 + test2" << endl;
    cout << "\t\t e   Check whether test1 == test3" << endl;
    cout << "\t\t i   Ensure capacity on test1" << endl;
    cout << "\t\t t   Trim to size on test1" << endl;
    cout << "\t\t C   Use copy constructor test3 = sequence(test1)" << endl;
    cout << "\t\t Q   Quit this test program" << endl;
}

char get_user_command( ) {
// Library facilities used: iostream
    char command;

    cout << "\t\tEnter choice: ";
    cin >> command; // Input of characters skips blanks and newline character

    return command;
}

void show_sequence(const sequence& display) {
// Library facilities used: iostream
    cout << display << endl;
}

double get_number( ) {
// Library facilities used: iostream
    value_type result;
    
    cout << "Please enter a number for the sequence: ";
    cin  >> result;
    cout << result << " has been read." << endl;
    return result;
}

size_t get_whole_number( ) {
// Library facilities used:  iostream
    size_t result;

    cout << "\t\tPlease enter a non-negative whole number: ";
    cin >> result;
    cout << "\t\t" << result << " has been read." << endl;
    return result;
}

void pressEnterToContinue( ) {
// Library facilities used:  iostream
    char ch;
    cout << "\n\t\tPress enter to continue  . . . ";
    cin.ignore(); cin.ignore();
}
