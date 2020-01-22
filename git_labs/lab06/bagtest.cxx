// FILE: bagtest.cxx
// An interactive test program for the new Bag class, implemented with
// a binary search tree.

#include <cctype>     // Provides toupper
#include <iostream>   // Provides cout, cin
#include <cstdlib>    // Provides EXIT_SUCCESS, size_t
#include "bag.h"      // Provides the bag<Item> class (with Item as a double)

using namespace std;

// PROTOTYPES for the functions used in this test program.
void print_menu( );
// Postcondition: A menu of choices for this program has been written to cout.

char get_user_command( );
// Postcondition: The user has been prompted to enter a one character command.
// A line of input (with at least one character) has been read, and the first
// character of the input line is returned.

void display_bags(const bag<double>& b1, const bag<double>& b2);
// Postcondition: The function has tested whether the numbers 0..9 are in
// the two bags, and printed the results to standard output.

void Display_bags(const bag<double>& b1, const bag<double>& b2);
// Postcondition: The function calls on the bags display function

bag<double> copybag(bag<double> b);
// Postcondition: The return value is a copy of b.

double get_number( );
// Postcondition: The user has been prompted to enter a double number. The
// number has been read, echoed to the screen, and returned by the function.

void pressEnterToContinue( );


int main( ) {
    bag<double> b1, b2;  // Bags that we'll perform tests on
    char choice; // A command character entered by the user

    system("clear");
    cout << "I have initialized two empty bags of doubles." << endl;
    cout << "\tPress enter to continue . . .";
    cin.ignore();

    do {
        system("clear");
        cout << "\n\n\n";
        print_menu( );
        choice = get_user_command( );
        int num, input;
        switch (choice) {
	    case 'A': b1 = b2;
		      break;
	    case 'a': b2 = b1;
		      break;
            case 'B': b1 = copybag(b2);
                      break;
            case 'b': b2 = copybag(b1);
                      break;
            case 'C': input = get_number( );
                      num = b1.count( input );
                      cout << "There are " << num << " occurrences of "
                           << input << " in b1" << endl;
                      pressEnterToContinue();
                      break;
            case 'c': input = get_number( );
                      num = b2.count( input );
                      cout << "There are " << num << " occurrences of "
                           << input << " in b2" << endl;
                      pressEnterToContinue();
                      break;
            case 'D': 
            case 'd': display_bags(b1, b2);
                      pressEnterToContinue();
                      break;
            case 'E': num = b1.erase(get_number( ));
                      cout << "\nRemoved " << num << " occurrences\n";
                      pressEnterToContinue();
                      break;
            case 'e': num = b2.erase(get_number( ));
                      cout << "\nRemoved " << num << " occurrences\n";
                      pressEnterToContinue();
                      break;
            case 'I': b1.insert(get_number( )); 
                      pressEnterToContinue();
                      break;
            case 'i': b2.insert(get_number( ));
                      pressEnterToContinue();
                      break;
            case 'O': b1.erase_one(get_number( ));
                      pressEnterToContinue();
                      break;
            case 'o': b2.erase_one(get_number( ));
                      pressEnterToContinue();
                      break;
            case 'P': cout << "b1 + b2 => [ ";
                      for (double aNum : b1+b2) 
                          cout << aNum << " ";
                      cout << "]\n";
                      pressEnterToContinue();
                      break;
            case 'p': cout << "b1 after b1 += b2 ==> [ ";
                      for (double aNum : b1 += b2)
                          cout << aNum << " ";
                      cout << "]\n";
                      pressEnterToContinue();
                      break;
            case 'S':
            case 's': cout << "The bags' sizes are " << b1.size( );
                      cout << " and " << b2.size( ) << endl;
                      pressEnterToContinue();
                      break;
            case 'T': cout << "\t\t iteration over b1 [ ";
                      for (double number : b1)
                          cout << number << " ";
                      cout << "]\n";
                      pressEnterToContinue();
                      break;
            case 't': cout << "\t\t iteration over b2 [ ";
                      for (double number : b2)
                          cout << number << " ";
                      cout << "]\n";
                      pressEnterToContinue();
                      break;
	    case 'q':
	    case 'Q': cout << "Ridicule is the best test of truth." << endl;
                      pressEnterToContinue();
                      break;
            default:  cout << choice << " is invalid. Sorry." << endl;
                      pressEnterToContinue();
        }
    } while ((toupper(choice) != 'Q'));

    return EXIT_SUCCESS;

}

void print_menu( ) {
// Library facilties used: iostream
    cout << "\t\tThe following choices are available with 2 bags: " << endl;
    cout << "\t\t A  Use the assignment operator to make b1 equal to b2" << endl;
    cout << "\t\t a  Use the assignment operator to make b2 equal to b1" << endl;
    cout << "\t\t B  Use the copy constructor to make b1 equal to b2" << endl;
    cout << "\t\t b  Use the copy constructor to make b2 equal to b1" << endl;
    cout << "\t\t C  Display number of occurrences of a value in b1" << endl;
    cout << "\t\t c  Display number of occurrences of a value in b2" << endl;
    cout << "\t\t D  Display contents of both bags" << endl;
    cout << "\t\t E  Erase all occurrences of item from b1" << endl;
    cout << "\t\t e  Erase all occurrences of item from b2" << endl;
    cout << "\t\t I  Insert an item into b1" << endl;
    cout << "\t\t i  Insert an item into b2" << endl;
    cout << "\t\t O  Erase one item from b1" << endl;
    cout << "\t\t o  Erase one item from b2" << endl;
    cout << "\t\t P  Display iteration over b1 + b2" << endl;
    cout << "\t\t p  Display iteration over b1 after b1 += b2" << endl;
    cout << "\t\t S  Print the result from the size( ) functions" << endl;
    cout << "\t\t T  Iterate over bag b1: " << endl;
    cout << "\t\t t  Iterate over bag b2: " << endl;
    cout << "\t\t Q  Quit this test program" << endl;
}

char get_user_command( ) {
// Library facilties used: iostream
    char command;

    cout << "Enter choice: ";
    cin >> command; 
   
    return command;
}

void display_bags(const bag<double>& b1, const bag<double>& b2) {
    cout << "Bag b1" << endl;
    b1.display(); cout << endl;
    cout << "Bag b2" << endl;
    b2.display(); cout << endl;
}

bag<double> copybag(bag<double> b) {
    return b;
}

double get_number( ) {
// Library facilties used: iostream
    double result;

    cout << "Please enter a double number for the bag: ";
    cin  >> result;
    cout << result << " has been read." << endl;
    return result;
}

void pressEnterToContinue( ) {
// Library facilities used:  iostream
    cout << "\n\t\tPress enter to continue  . . . ";
    cin.ignore(); cin.ignore();
}
