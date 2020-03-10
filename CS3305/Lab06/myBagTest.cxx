#include <iostream>
#include <iterator>
#include "bag.h"
using namespace std;


int main() {

    bag<double> b;

    b.insert(25); b.insert(20); b.insert(30);

    b.display();

    for (double number : b)
        cout << number << " ";
    cout << endl;
    for ( bag<double>::iterator it = b.begin(); it != b.end(); ++it)
        cout << *it << " ";
    cout << endl;

    return 0;
}
