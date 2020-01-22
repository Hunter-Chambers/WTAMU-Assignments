#include <iostream>
#include <iomanip>

using namespace std;

class Counter {

    public:

        Counter() {
            this->theCounter = 0;
        }

        Counter( int initValue ) {
            this->theCounter = initValue;
        }

        void incr( ) {
            this->theCounter++;
        }

        int getValue( ) const {
            return this->theCounter;
        }

        string toString() const {
            stringstream outs;

            outs << this->theCounter;
            return outs.str();
        }


    private:
        int theCounter;

}; //NB this trailing semi-colon must exist here

ostream& operator <<(ostream& outs, const Counter& ctr) {
    outs << ctr.toString();
    return outs;
}

int main() {
    cout << "Testing the Counter class " << endl;

    Counter counter;
    cout << "Value of counter after constructor " << counter << endl;   

    for (int i=1; i <= 20; ++i) counter.incr();
    cout << "Value of counter after calling incr 20 times " << counter << endl;
    cout << "And value of counter from calling getValue " << counter.getValue() << endl << endl;

    cout << "Let's add 50 more to the counter" << endl;
    for (int i=1; i <= 50; ++i) counter.incr();
    cout << "Value of counter after calling incr 50 more times " << counter << endl;
    cout << "And value of counter from calling getValue "  <<counter.getValue() << endl << endl;

    Counter counter1(100);
    cout << "Value of counter after constructor counter1(100) " << counter1 << endl;

    return 0;
}

