#include <iostream>
#include <string>
#include <sstream>
#include <stdexcept>
#include <cfloat>
#include <functional>
#include <cmath>

#include "statistician.h"

statistician::statistician( ) {
    this->N        = 0;
    this->sumX     = 0.0;
    this->sumXsq   = 0.0;
    this->smallest =  DBL_MAX; 
    this->largest  = -DBL_MAX ; 
}

statistician::statistician(const statistician& other) {
    this->N        = other.N;
    //STUDENT complete the copy of attributes
    sumX     = other.sumX;
    sumXsq   = other.sumXsq;
    smallest = other.smallest;
    largest  = other.largest;
}

void statistician::nextNumber(double r) {
    this->N++;
    //STUDENT complete the correct updating of attributes
    sumX += r;
    sumXsq += (r * r);
    if (smallest > r) smallest = r;
    if (largest < r) largest = r;
}

void statistician::reset( ) {
    //STUDENT complete appropriate initialization of all attributes
    N        = 0;
    sumX     = 0.0;
    sumXsq   = 0.0;
    smallest = DBL_MAX;
    largest  = -DBL_MAX;
}

statistician& statistician::operator =(const statistician& other) {
    //Check for possible self-assignment
    if (this == &other) return *this;

    //STUDENT complete appropriate destructive assignment of other's
    //        attributes to this-> attributes
    N        = other.N;
    sumX     = other.sumX;
    sumXsq   = other.sumXsq;
    smallest = other.smallest;
    largest  = other.largest;

    return *this; //the dereference of a pointer yields a reference
}

size_t statistician::hashCode( ) const {
    size_t hashValue;
    std::hash<double> hash; 
    hashValue = 101 * ( hash(this->N) +
                        hash(this->sumX) +
                        hash(this->sumXsq) +
                        hash(this->smallest) +
                        hash(this->largest) 
                      );
    return hashValue;
}

int statistician::length( ) const {
        return this->N;
}

double statistician::mean( ) const {
    if ( this->length( ) == 0 )
        return std::nan("");

    //STUDENT  replace the below bogus return with one that
    //         returns value of expression that calculates the mean
    return (sumX / N);
}

double statistician::minimum( ) const {
    if ( this->length( ) == 0 )
        return std::nan("");
    return smallest;
}

double statistician::maximum( ) const {
    if ( this->length( ) == 0 )
        return std::nan("");
    return largest;
}

double statistician::stdDev( ) const {
    if ( this->length() == 0 )
        return std::nan("");
    //STUDENT  replace the below bogus return with one that
    //         returns value of expression that calculates the
    //         standard deviation.  Do this as effecienntly as
    //         possible
    //         HINT: with the includes at top of this file, you
    //               can invoke the function sqrt()
    return (sqrt((sumXsq / (double) N) - pow(mean(), 2)));
}

double statistician::sum_X( ) const {
    return this->sumX;
}

double statistician::sum_Xsq( ) const {
    return this->sumXsq;
}

std::string statistician::toString( ) const {
    //Library facilities used:  sstream, string
    std::stringstream outs;
    outs << "Statistician: \n";
    outs << "\tN        = " << this->N  << "\n";
    outs << "\tsumX     = " << this->sumX  << "\n"; 
    outs << "\tsumXsq   = " << this->sumXsq << "\n";
    if ( this->N == 0 ) {
        outs << "\tmean     = NOT DEFINED\n";
        outs << "\tstd. dev = NOT DEFINED\n"; 
        outs << "\tminimum  = NOT DEFINED\n";
        outs << "\tmaximum  = NOT DEFINED\n";
    }
    else {
        outs << "\tmean     = " << this->mean()  << "\n";
        outs << "\tstd. dev = " << this->stdDev() << "\n";
        outs << "\tsmallest = " << this->smallest << "\n";
        outs << "\tlargest  = " << this->largest << "\n";
    }
    return outs.str();
}

//FRIEND functions
statistician  operator *(double scale, const statistician& s) {
    statistician temp(s);

    //STUDENT complete the appropriate scaling of attributes
    //
    //CAUTION: if the scale is negative, largest and smallest
    //         should be swapped.  You can do this efficiently
    //         with the function std::swap( value1, value2 )
    //NOTE:    The object that is target of the scaling is
    //         temp.
    temp.sumX     *= scale;
    temp.sumXsq   *= scale;
    temp.smallest *= scale;
    temp.largest  *= scale;

    if (temp.smallest > temp.largest) std::swap(temp.smallest, temp.largest);

    return temp;
}

statistician operator +(const statistician& s1, const statistician& s2) {
    statistician temp;

    //STUDENT complete the logic that renders temp the union of s1 and
    //        s2. 
    //NOTE:   the union's largest and smallest are NOT the sum of two 
    //        objects largest and smallest, respectively.  Rather,
    //        the union's largest is the largest of the two largest
    //        attributes AND the union's smallest is the smallest of
    //        the two smallest attributes.
    temp.N      = (s1.N + s2.N);
    temp.sumX   = (s1.sumX + s2.sumX);
    temp.sumXsq = (s1.sumXsq + s2.sumXsq);

    (s1.smallest < s2.smallest) ? (temp.smallest = s1.smallest) :
        temp.smallest = s2.smallest;
    //if (s1.smallest < s2.smallest)
    //    temp.smallest = s1.smallest;
    //else
    //    temp.smallest = s2.smallest;

    (s1.largest > s2.largest) ? (temp.largest = s1.largest) :
        temp.largest = s2.largest;
    //if (s1.largest > s2.largest)
    //    temp.largest = s1.largest;
    //else
    //    temp.largest = s2.largest;

    return temp;
}

//NON-MEMBER functions
bool operator ==(const statistician& s1, const statistician& s2) {
    if (s1.length( ) == 0 && s2.length( ) == 0) return true;

    if (s1.hashCode() != s2.hashCode()) return false;

    //STUDENT: CAUTION, this is a non-member, non-friend function
    //         that cannot reach inside s1 and s2 and access their
    //         respective attributes.  Rather, this function must
    //         use the member functions to get at the values of
    //         the attributes.
    //
    //         Thus, students must compare value of all attributes
    //         for equality. Remember the property of short-circuit
    //         evaluation of composite logical expressions.
    //
    //         BTW, logical and is denoted as && in C++
    if ((s1.length() == s2.length()) &&
        (s1.sum_X() == s2.sum_X()) &&
        (s1.sum_Xsq() == s2.sum_Xsq()) &&
        (s1.minimum() == s2.minimum()) &&
        (s1.maximum() == s2.maximum()))
        return true;

    return false;

}

bool operator !=(const statistician& s1, const statistician& s2) {
    return !(s1 == s2);
}

std::ostream& operator <<(std::ostream& outs, const statistician& s1) {
    outs << s1.toString();
    return outs;
}

