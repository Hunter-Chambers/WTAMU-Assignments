// FILE: statexam.cxx
// This program calls five test functions to test the statisitician class.
// Maximum number of points from this program is 100

#include <iostream>
#include <cstdlib>
#include <cmath>
#include "statistician.h"

using namespace std;

bool close(double a, double b) {
    const double EPSILON = 1e-5;
    return (fabs(a-b) < EPSILON);
}

int test1( ) {
    // Test program for basic statistician functions.
    // Returns 51 if everything goes okay; otherwise returns 0.

    statistician s, t;
    int points = 0;

    int i;
    double r = 0.0;
    double rsq = 0.0;

    if (s.length( ) || t.length( )) return 0;
    points += 2;
    cout << "Test1 -- part 1 OK \n";

    if (s.sum_X( ) || t.sum_X( )) return 0;
    points += 2;
    cout << "Test1 -- part 2 OK \n";

    if ( !isnan( s.mean() ) ) return 0;
    cout << "Test1 -- part 2a OK \n";
    points += 3;

    for (i = 1; i <= 10000; i++) {
	s.nextNumber(i);
	r += i;
        rsq += (i * i);
    };

    if (t.length( ) || t.sum_X( )) return 0;
    points += 2;
    cout << "Test1 -- part 3 OK \n";

    if (s.length( ) != 10000) return 0;
    points += 4;
    cout << "Test1 -- part 4 OK \n";

    if (!close(s.sum_X( ), r)) return 0;
    points += 4;
    cout << "Test1 -- part 5 OK \n";

    if (!close(s.mean( ), r/10000.0)) return 0;
    points += 5;
    cout << "Test1 -- part 6 OK \n";

    double mean = r/10000.0;
    double stdDev = sqrt( rsq/10000.0 - mean*mean );
    //cout << "Test stdDev is " << stdDev << endl;
    //cout << "statistician's stdDev() is " << s.stdDev() << endl;

    if ( !close(s.stdDev(), stdDev ) ) return 0;
    cout << "Test1 -- part 7 OK \n";
    points += 6;

    
    // Reset and then retest everything
    s.reset( );
    t.reset( );
    r = 0.0;
    rsq = 0.0;
    
    if (s.length( ) || t.length( )) return 0;
    cout << "Test1 -- part 8 OK \n";
    points += 2;

    if (s.sum_X( ) || t.sum_X( )) return 0;
    cout << "Test1 -- part 9 OK \n";
    points += 2;

    if ( !isnan( s.mean() ) ) return 0;
    points += 4;
    cout << "Test1 -- part 10 OK \n";

    for (i = 1; i <= 10000; i++) {
	s.nextNumber(i);
	r += i;
        rsq += (i * i);
    };

    if (t.length( ) || t.sum_X( )) return 0;
    points += 2;
    cout << "Test1 -- part 11 OK \n";

    if (s.length( ) != 10000) return 0;
    cout << "Test1 -- part 12 OK \n";
    points += 2;

    if (!close(s.sum_X( ), r)) return 0;
    cout << "Test1 -- part 13 OK \n";
    points += 2;

    if (!close(s.mean( ), r/10000.0)) return 0;
    cout << "Test1 -- part 14 OK \n";
    points += 3;

    mean = r/10000.0;
    stdDev = sqrt( rsq/10000.0 - mean*mean );
    if ( !close(s.stdDev(), stdDev) ) return 0;
    cout << "Test1 -- part 15 OK \n";
    points += 6;

    cout << "points from this test " << points << endl;
    return points;
}

int test2( ) {
    // Test program for minimum/maximum statistician functions.
    // Returns 10 if everything goes okay; otherwise returns 0.

    statistician s, t;
    int points = 0;

    if (s.length( ) || t.length( )) return 0;
    points += 1;
    cout << "Test 2 -- part 1 OK \n";

    if (s.sum_X( ) || t.sum_X( )) return 0;
    cout << "Test 2 -- part 2 OK \n";
    points += 1;

    if ( !isnan( s.minimum() ) ) return 0;
    cout << "Test 2 -- part 3 OK \n";
    points += 1;

    if ( !isnan( s.maximum() ) ) return 0;
    cout << "Test 2 -- part 4 OK \n";
    points += 1;

    double r = 1.39804e-76;
    r = 1/r;
    //cout << "r is now " << r << endl;
    s.nextNumber(r);
    if ((s.minimum( ) != r) || (s.maximum( ) != r)) return 0;
    cout << "Test 2 -- part 5 OK \n";
    points += 2;

    r *= -1;
    t.nextNumber(r);
    if ((t.minimum( ) != r) || (t.maximum( ) != r)) return 0;
    cout << "Test 2 -- part 6 OK \n";
    points += 2;

    statistician u;
    u.nextNumber(100); u.nextNumber(-1); u.nextNumber(101); u.nextNumber(3);
    if ((u.minimum( ) != -1) || (u.maximum( ) != 101)) return 0;
    cout << "Test 2 -- part 7 OK \n";
    points += 2;

    /*
    statistician *up = new statistician();
    up->nextNumber(100); up->nextNumber(-1); up->nextNumber(101); (*up).nextNumber(3);
    if ((up->minimum( ) != -1) || (up->maximum( ) != 101)) return 0;
    cout << "Test 2 -- part 8 OK \n";
    points += 2;

    delete up;
    */

    cout << "points from this test " << points << endl;
    return points;
}

int test3( ) {
    // Test program for + operator of the statistician
    // Returns 10 if everything goes okay; otherwise returns 0.

    statistician s, t, u, v;
    int points = 0;

    if (s.length( ) || t.length( )) return 0;
    if (s.sum_X( ) || t.sum_X( )) return 0;

    t.nextNumber(5);
    u.nextNumber(0); u.nextNumber(10); u.nextNumber(10); u.nextNumber(20);

    v = s + s;
    if (v.length( ) || v.sum_X( )) return 0;
    points += 2;

    v = s + u;
    if (!(u == v)) return 0;
    points += 2;

    v = t + s;
    if (!(t == v)) return 0;
    points += 2;

    v = t + u;
    if (v.length( ) != 5) return 0;
    if (!close(v.sum_X( ), 45)) return 0;
    if (v.minimum( ) != 0) return 0;
    if (v.maximum( ) != 20) return 0;
    if (!close(v.mean( ), 45.0/5)) return 0;
    points += 2;

    v = v + t;
    if (v.length( ) != 6) return 0;
    if (!close(v.sum_X( ), 50)) return 0;
    if (v.minimum( ) != 0) return 0;
    if (v.maximum( ) != 20) return 0;
    if (!close(v.mean( ), 50.0/6)) return 0;
    points += 2;

    cout << "points from this test " << points << endl;
    return points;
}

int test4( ) {
    // Test program for * operator of the statistician
    // Returns 17 if everything goes okay; otherwise returns 0.

    statistician s, t, u;
    int points = 0;

    if (s.length( ) || t.length( )) return 0;
    if (s.sum_X( ) || t.sum_X( )) return 0;

    u.nextNumber(0); u.nextNumber(10); u.nextNumber(10); u.nextNumber(20);

    s = 2*u;
    if (s.length( ) != 4) return 0;
    if (!close(s.sum_X( ), 80)) return 0;
    if (s.minimum( ) != 0) return 0;
    if (s.maximum( ) != 40) return 0;
    if (!close(s.mean( ), 80.0/4)) return 0;
    points += 2;

    s = -2*u;
    if (s.length( ) != 4) return 0;
    if (!close(s.sum_X( ), -80)) return 0;
    if (s.minimum( ) != -40) return 0;
    if (s.maximum( ) != 0) return 0;
    if (!close(s.mean( ), -80.0/4)) return 0;
    points += 11;

    s = 0*u;
    if (s.length( ) != 4) return 0;
    if (!close(s.sum_X( ), 0)) return 0;
    if (s.minimum( ) != 0) return 0;
    if (s.maximum( ) != 0) return 0;
    if (!close(s.mean( ), 0)) return 0;
    points += 2;

    s = 10 * t;
    if (s.length( ) != 0) return 0;
    if (s.sum_X( ) != 0) return 0;
    points += 2;

    cout << "points from this test " << points << endl;
    return points;
}

int test5( ) {
    // Test program for == operator of the statistician.
    // Returns 10 if everything goes okay; otherwise returns 0.

    statistician s, t, u, v, w, x;
    int points = 0;

    t.nextNumber(10);
    u.nextNumber(0); u.nextNumber(10); u.nextNumber(10); u.nextNumber(20);
    v.nextNumber(5); v.nextNumber(0); v.nextNumber(20); v.nextNumber(15);
    w.nextNumber(0);
    x.nextNumber(0); x.nextNumber(0);
    
    if (!(s == s)) return 0;
    cout <<"Test 5 -- part 1 OK \n";
    points += 2;

    if (s == t) return 0;
    cout <<"Test 5 -- part 2 OK \n";
    points += 2;

    if (t == s) return 0;
    cout <<"Test 5 -- part 3 OK \n";
    points += 2;

    if (u == t) return 0;
    points += 2;
    cout <<"Test 5 -- part 4 OK \n";

    if (u == v) return 0;
    points += 2;
    cout <<"Test 5 -- part 5 OK \n";

    if (w == x) return 0;
    cout <<"Test 5 -- part 6 OK \n";
    points += 2;

    cout << "points from this test " << points << endl;
    return points;
}

int main( ) {
    int value = 0;
    int result;
    
    cerr << "Running statistician tests:" << endl;
 
    cerr << "TEST 1:" << endl;
    cerr << "Testing next, reset, length, sum, and mean (51 points).\n";
    result = test1( );
    value += result;
    if (result > 0) cerr << "Test 1 passed." << endl << endl;
    else cerr << "Test 1 failed." << endl << endl; 
 
    cerr << "\nTEST 2:" << endl;
    cerr << "Testing minimum and maximum member functions (10 points).\n";
    result = test2( );
    value += result;
    if (result > 0) cerr << "Test 2 passed." << endl << endl;
    else cerr << "Test 2 failed." << endl << endl; 
 
    cerr << "\nTEST 3:" << endl;
    cerr << "Testing the + operator (10 points).\n";
    result = test3( );
    value += result;
    if (result > 0) cerr << "Test 3 passed." << endl << endl;
    else cerr << "Test 3 failed." << endl << endl; 
 
    cerr << "\nTEST 4:" << endl;
    cerr << "Testing the * operator (17 points).\n";
    result = test4( );
    value += result;
    if (result > 0) cerr << "Test 4 passed." << endl << endl;
    else cerr << "Test 4 failed." << endl << endl; 

    cerr << "\nTEST 5:" << endl;
    cerr << "Testing the == operator (12 points).\n";
    result = test5( );
    value += result;
    if (result > 0) cerr << "Test 5 passed." << endl << endl;
    else cerr << "Test 5 failed." << endl << endl; 

    cerr << "If you submit the statistician to Prof. Haiduk now, this part of the\n";
    cerr << "grade will be " << value << " points out of 100.\n";
    
    return EXIT_SUCCESS;

}
