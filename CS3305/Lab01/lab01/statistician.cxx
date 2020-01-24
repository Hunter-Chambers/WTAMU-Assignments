#include <iostream>
#include <string>
#include <sstream>
#include <stdexcept>
#include <cfloat>
#include <functional>

#include "statistician.h"

std::string statistician::toString( ) const {
    //Library facilities used:  sstream, string
    std::stringstream outs;
    outs << "Statistician: \n";
    outs << "\tcount = " << this->count  << "\n"; 
    outs << "\ttotal = " << this->total  << "\n";
    if ( this->count == 0 ) {
       outs << "\tmean  = NOT DEFINED\n";
       outs << "\tmin   = NOT DEFINED\n";
       outs << "\tmax   = NOT DEFINED\n";
    }
    else {
        outs << "\tmean  = " << this->mean()  << "\n";
        outs << "\tmin   = " << this->tinyest << "\n";
        outs << "\tmax   = " << this->largest << "\n";
    }
    return outs.str();
}

statistician::statistician( ) {
    this->count = 0;
    this->total = 0.0;
    this->tinyest =  DBL_MAX; 
    this->largest = -DBL_MAX ; 
}

statistician::statistician(const statistician& other) {
    /* student implements */
}

void statistician::next(double r) {
    /* student implements */
}

void statistician::reset( ) {
    /* student implements */
}

statistician& statistician::operator =(const statistician& other) {
    //Check for possible self-assignment
    if (this == &other) return *this;

    this->count   = other.count;
    this->total   = other.total;
    this->tinyest = other.tinyest;
    this->largest = other.largest;

    return *this;
}

size_t statistician::hashCode( ) const {
    size_t hashValue;
    std::hash<double> hash; 
    hashValue = 101 * ( hash(this->count) +
                        hash(this->total) +
                        hash(this->tinyest) +
                        hash(this->largest) 
                      );
    return hashValue;
}

int statistician::length( ) const {
    return count;
}

double statistician::sum( ) const {
    return total;
}

double statistician::mean( ) const {
    if ( this->length( ) == 0 )
        throw std::domain_error("Mean not defined as there is no data");
    return total / count;
}

double statistician::minimum( ) const {
    if ( this->length( ) == 0 )
        throw std::domain_error("Minimum not defined as there is no data");
    return tinyest;
}

double statistician::maximum( ) const {
    if ( this->length( ) == 0 )
        throw std::domain_error("Maximum not defined as there is no data");
    return largest;
}

statistician  operator *(double scale, statistician& s) {
    statistician temp(s);

    /* student completes implementation here */

    return temp;
}

statistician operator +(const statistician& s1, const statistician& s2) {
    statistician temp;

    /* student completes implementation here */

    return temp;
}

bool operator ==(const statistician& s1, const statistician& s2) {
    if (s1.length( ) == 0 && s2.length( ) == 0) return true;

    bool attrEqual;

    if (s1.hashCode() != s2.hashCode()) return false;

    /* student completes implementation here */

}


bool operator !=(const statistician& s1, const statistician& s2) {
    return !(s1 == s2);
}

std::ostream& operator <<(std::ostream& outs, const statistician& s1) {
    outs << s1.toString();
    return outs;
}

