// FILE: sequence_test.cxx
// An interactive test program for the new sequence class
#include <cctype>       // Provides toupper
#include <iostream>     // Provides cout and cin
#include <cstdlib>      // Provides EXIT_SUCCESS and system
#include "sequence.h"   // With value_type defined as double

// PROTOTYPES for functions used by this test program:
void print_menu( );
// Postcondition: A menu of choices for this program has been written to cout.

char get_user_command( );
// Postcondition: The user has been prompted to enter a one character command.
// The next character has been read (skipping blanks and newline characters), 
// and this character has been returned.

void show_sequence(const sequence<double>& display);
// Postcondition: The items on display have been printed to cout.

double get_number( );
// Postcondition: The user has been prompted to enter a number. The
// number has been read, echoed to the screen, and returned by the function.

void pressEnterToContinue( );
// Postcondition:  Waits for user to press the return key

using namespace std;

int main( ) {
    sequence<double> test1; // sequences that we'll perform tests on
    sequence<double> test2;
    char choice;   // A command character entered by the user

    system("clear");
    cout << "I have initialized two empty sequences of numbers." << endl;
    cout << "\tPress enter to continue . . .";
    cin.ignore();

    do {
        system("clear");
        print_menu( );
        choice = get_user_command( );
        string result;
        switch (choice) {
            case 'i': cout << "\t\t forward iterate over test1 [ ";
                      for (sequence<double>::iterator fi = test1.begin();
                           fi != test1.end(); ++fi )
                          cout << *fi << " ";
                      cout << "]" << endl;
                      pressEnterToContinue();
                      break;
            case 'I': cout << "\t\t forward iterate over test2 [ ";
                      for (const auto element : test1) cout << element << " ";
                      cout << "]" << endl;
                      pressEnterToContinue();
                      break;
            
            case 'x': cout << "\t\t reverse iterate over test1 [ ";
                      for (sequence<double>::iterator ri = test1.rbegin();
                           ri != test1.rend(); --ri )
                          cout << *ri << " ";
                      cout << "]" << endl;
                      pressEnterToContinue();
                      break;
            case 'X': cout << "\t\t reverse iterate over test2 [ ";
                      for (sequence<double>::iterator ri = test2.rbegin();
                           ri != test2.rend(); --ri)
                          cout << *ri << " ";
                      cout << "]" << endl;
                      pressEnterToContinue();
                      break;
           
            case 'f': test1.first( );
                      break;
            case 'F': test2.first( );
                      break;
            case 'l': test1.last( );
                      break;
            case 'L': test2.last( );
                      break;
            case 'a': if ( test1.is_current() ) 
                          test1.advance();
                      else cout << "\t\tCannot advance since cursor is not current\n";
                      pressEnterToContinue();
                      break;
            case 'A': if ( test2.is_current() )
                          test2.advance();
                      else cout << "\t\tCannot advance since cursor is not current\n";
                      pressEnterToContinue();
                      break;
            case 'r': if ( test1.is_current() )
                        test1.retreat( );
                      else cout << "\t\tCannot retreat since cursor is not current\n";
                      pressEnterToContinue();
                      break;
            case 'R': if ( test2.is_current() )
                        test2.retreat( );
                      else cout << "\t\tCannot retreat since cursor is not current\n";
                      pressEnterToContinue();
                      break;
            case 'c': test2 = test1;
                      pressEnterToContinue();
                      break;
            case 'C': cout << "sequence<double>(test1) is: " << 
                               sequence<double>(test1) << endl;
                      pressEnterToContinue();
                      break;
            case '?': if (test1.is_current( ))
                          cout << "\t\tthere is a current item in test1." << endl;
                      else 
                          cout << "\t\tThere is no current item in test1." << endl;
                      pressEnterToContinue( );
                      break;
            case 'g': if (test1.is_current( )) 
                          cout << "\t\tCurrent item in test1 is: " << test1.current( ) << endl;
                      else
                          cout << "\t\tThere is no current element!" << endl;
                      pressEnterToContinue( );
                      break;
            case '1': show_sequence(test1);
                      pressEnterToContinue( );
                      break;
            case '2': show_sequence(test2);
                      pressEnterToContinue( );
                      break;
            case 's': cout << "\t\ttest1 size is " << test1.size( ) << '.' << endl;
                      pressEnterToContinue( );
                      break;
            case 'S': cout << "\t\ttest2 size is " << test2.size( ) << '.' << endl;
                      pressEnterToContinue( );
                      break;
            case '>': test1.add_after(get_number( ));
                      break;
            case '<': test1.add_before(get_number( ));
                      break;
            case 'd': if ( test1.is_current( ) ) {
                          test1.remove_current( );
                          cout << "\t\tThe current item has been removed from test1." << endl;
                      } else cout << "\t\tCannot remove since test1 has no current item." << endl;
                      pressEnterToContinue( );
                      break;     
            case 'D': if ( test2.is_current() ) {
                          test2.remove_current( );
                          cout << "\t\tThe current item has been removed from test2." << endl;
                      } else cout << "\t\tCannot remove since test2 has no current item." << endl;
                      pressEnterToContinue( );
                      break;
            case 'e': result = (test1 == test2 ) ? "true" : "false";
                      cout << "\t\ttesting whether test1 == test2? " << result << endl;
                      pressEnterToContinue( );
                      break;
            case 'n': result = ( test1 != test2 ) ? "true" : "false";
                      cout << "\t\ttesting whether test1 != test2? " << result << endl;
                      pressEnterToContinue( );
                      break;
            case '+': test1 += test2;
                      cout << "\t\ttest 1 after operation test1 += test2 \n" << test1 << endl;
                      pressEnterToContinue( );
                      break;
            case 'p': cout << "\t\ttest1 + test2\n" << test1+test2 << endl;
                      pressEnterToContinue( );
                      break;
            case 'q':
            case 'Q': cout << "\t\tRidicule is the best test of truth." << endl;
                      pressEnterToContinue( );
                      break;
            default:  cout << "\t\t" << choice << " is invalid." << endl;
                      pressEnterToContinue( );
        }
    }
    while ((choice != 'Q') && (choice != 'q'));

    return EXIT_SUCCESS;
}

void print_menu( ) {
// Library facilities used: iostream
    cout << endl; // Print blank line before the menu
    cout << "\tThe following choices are available: " << endl;
    cout << "\t\t f  Activate the first( ) function for test1" << endl;
    cout << "\t\t F  Activate the first( ) function for test2" << endl;
    cout << "\t\t l  Activate the last( ) function for test1" << endl;
    cout << "\t\t L  Activate the last( ) function for test2" << endl;
    cout << "\t\t a  Activate the advance( ) function for test1" << endl;
    cout << "\t\t A  Activate the advance( ) function for test2" << endl;
    cout << "\t\t r  Activate the retreat( ) function for test1" << endl;
    cout << "\t\t R  Activate the retreat( ) function for test2" << endl;
    cout << "\t\t c  Copy test1 into test2 with the assignment: test2 = test1" << endl;
    cout << "\t\t C  Invoke copy constructor sequence<double>(test1)" << endl;
    cout << "\t\t e  Test whether test1 == test2" << endl;
    cout << "\t\t n  Test whether test1 != test2" << endl;
    cout << "\t\t 1  Display test1" << endl;
    cout << "\t\t 2  Display test2" << endl;
    cout << "\t\t i  Forward iterate over elements of test1" << endl;
    cout << "\t\t x  Reverse iterate over elements of test1" << endl;
    cout << "\t\t I  Forward iterate over elements of test2" << endl;
    cout << "\t\t X  Reverse iterate over elements of test2" << endl;
    cout << "\t\t s  Activate size( ) for test1" << endl;
    cout << "\t\t S  Activate size( ) for test2" << endl;
    cout << "\t\t >  Activate add_after( ) for test1" << endl;
    cout << "\t\t <  Activate add_before( ) for test1" << endl;
    cout << "\t\t d  Activate remove_current( ) for test1" << endl;
    cout << "\t\t D  Activate remove_current( ) for test2" << endl;
    cout << "\t\t +  Activate test1 += test2" << endl;
    cout << "\t\t p  Activate test1 + test2" << endl;
    cout << "\t\t Q  Quit this test program" << endl;
}

char get_user_command( ) {
// Library facilities used: iostream
    char command;

    cout << "\t\tEnter choice: ";
    cin >> command; // Input of characters skips blanks and newline character

    return command;
}

void show_sequence(const sequence<double>& display) {
// Library facilities used: iostream
    cout << display << endl;
}

double get_number( ) {
// Library facilities used: iostream
    double result;
    
    cout << "Please enter a number for the sequence: ";
    cin  >> result;
    cout << result << " has been read." << endl;
    return result;
}

void pressEnterToContinue( ) {
// Library facilities used:  iostream
    char ch;
    cout << "\n\t\tPress enter to continue  . . . ";
    cin.ignore(); cin.ignore();
}
