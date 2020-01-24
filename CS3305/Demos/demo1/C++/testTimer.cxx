#include <iostream>
#include "timer.h"

// To execute this program:
//   g++ -o testTimer -std=c++11 testTimer.cxx
//   ./testTimer

using namespace std;

int main() {

    timer myTimer;

    cout << "Setting timer for 5 seconds . . . \n";
    myTimer.run_timer(5);

    cout << "Timer ran for " << myTimer << " seconds\n";

    return 0;
}
