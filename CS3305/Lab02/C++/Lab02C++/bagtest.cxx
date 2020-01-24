// FILE: bagtest.cxx
// An interactive test program for the new bag class, implemented with
// a binary search tree.

#include <ctype.h>    // Provides toupper
#include <iostream>   // Provides cout, cin
#include <stdlib.h>   // Provides EXIT_SUCCESS, size_t
#include "bag1.h"     // Provides the bag class 

using namespace std;

// PROTOTYPES for the functions used in this test program.
void print_menu( );
// Postcondition: A menu of choices for this program has been written to cout.

char get_user_command( );
// Postcondition: The user has been prompted to enter a one character command.
// A line of input (with at least one character) has been read, and the first
// character of the input line is returned.

void show_bags(const bag& b1, const bag& b2);
// Postcondition: The function has tested whether the numbers 0..9 are in
// the two bags, and printed the results to standard output.

bag copybag(bag b);
// Postcondition: The return value is a copy of b.

double get_number( );
// Postcondition: The user has been prompted to enter bag::value_type  number. The
// number has been read, echoed to the screen, and returned by the function.

int main( ) {
    bag b1, b2;  // bags that we'll perform tests on
    char choice; // A command character entered by the user

    cout << "\tI have initialized two empty bags of doubles." << endl;


    do {
        print_menu( );
        choice = get_user_command( );
        switch (choice) {
	    case 'A': b1 = b2;
		      break;
	    case 'a': b2 = b1;
		      break;
            case 'C': b1 = copybag(b2);
                      break;
            case 'c': b2 = copybag(b1);
                      break;
            case 'S':
            case 's': cout << "The bags' sizes are " << b1.size( );
                      cout << " and " << b2.size( ) << endl;
                      break;
            case 'I': b1.insert(get_number( ));
                      break;
            case 'i': b2.insert(get_number( ));
                      break;
            case 'R': b1.erase(get_number( ));
                      break;
            case 'r': b2.erase(get_number( ));
                      break;
            case 'X': b1.erase_one(get_number( ));
                      break;
            case 'x': b2.erase_one(get_number( ));
                      break;
            case 'O':
            case 'o': show_bags(b1, b2);
                      break;
	    case 'q':
	    case 'Q': cout << "Ridicule is the best test of truth." << endl;
                      break;
            default: cout << choice << " is invalid. Sorry." << endl;
        }
    }
    while ((toupper(choice) != 'Q'));

    return EXIT_SUCCESS;

}

void print_menu( ) {
// Library facilties used: iostream.h
    cout << "\n\n\tThe following choices are available with 2 bags: " << endl;
    cout << "\t\t A  Use the assignment operator to make b1 equal to b2" << endl;
    cout << "\t\t a  Use the assignment operator to make b2 equal to b1" << endl;
    cout << "\t\t C  Use the copy constructor to make b1 equal to b2" << endl;
    cout << "\t\t c  Use the copy constructor to make b2 equal to b1" << endl;
    cout << "\t\t I  Insert an item into b1" << endl;
    cout << "\t\t i  Insert an item into b2" << endl;
    cout << "\t\t R  Erase all of item from b1" << endl;
    cout << "\t\t r  Erase all of item from b2" << endl;
    cout << "\t\t X  Erase one of item from b1" << endl;
    cout << "\t\t x  Erase one of item from b2" << endl;
    cout << "\t\t O  Display both bags" << endl;
    cout << "\t\t S  Print the result from the size( ) functions" << endl;
    cout << "\t\t Q  Quit this test program" << endl;
}

char get_user_command( ) {
// Library facilties used: iostream.h
    char command;

    cout << "\t\tEnter choice: ";
    cin >> command; 
   
    return command;
}

void show_bags(const bag& b1, const bag& b2) {
// Library facilties used: iostream
    cout << "\t\tb1 " << b1 << endl;
    cout << "\t\tb2 " << b2 << endl;
}

bag copybag(bag b) {
    return b;
}

double get_number( ) {
// Library facilties used: iostream
    bag::value_type result;

    cout << "\t\tPlease enter a bag::value_type number for the bag: ";
    cin  >> result;
    cout << "\t\t" << result << " has been read." << endl;
    return result;
}
