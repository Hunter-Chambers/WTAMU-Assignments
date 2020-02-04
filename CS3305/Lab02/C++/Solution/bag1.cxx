// FILE: bag1.cxx
// CLASS IMPLEMENTED: bag (see bag1.h for documentation)
// INVARIANT for the bag class:
//   1. The number of items in the bag is in the member variable used;
//   2. For an empty bag, we do not care what is stored in any of data; for a
//      non-empty bag the items in the bag are stored in data[0] through
//      data[used-1], and we don't care what's in the rest of data.

#include <algorithm> // Provides copy function
#include <cassert>   // Provides assert function
#include <sstream>   // Provides ostream
#include <string>    // Provides string
#include <stdexcept> // Provides exceptions
#include "bag1.h"
using namespace std;

const bag::size_type bag::CAPACITY;

bag::bag(const bag& other) {
    this->used = other.used;
    copy(other.data, other.data + other.used, this->data);
}
    
bag::size_type bag::erase(const value_type& target) {
    size_type index = 0;
    size_type number_removed = 0;

    //STUDENT IMPLEMENTATION HERE
    // do not use do-while, because
    // this->used could be 0
    while (index < used) {
        if (data[index] == target) {
            data[index] = data[--used];
            number_removed++;
        } else index++;
    }

    return number_removed;
}

bool bag::erase_one(const value_type& target) {
    size_type index; // The location of target in the data array    

    // First, set index to the location of target in the data array,
    // which could be as small as 0 or as large as used-1. If target is not
    // in the array, then index will be set equal to used.
    index = 0;
    bool found = false;

    /* while ((index < this->used) && (this->data[index] != target))
        ++index; */
    while ( !found && index < this->used ) {
        if ( this->data[index] == target )
            found = true;
        else
            ++index;
    }

    //STUDENT IMPLEMENTATION HERE
    if (found) {
        data[index] = data[--used];
    }

    return found;
}

void bag::insert(const value_type& entry) {
// Library facilities used: stdexcept
    if (this->size( ) == bag::CAPACITY)
        throw overflow_error("not enough space to complete operation");

    this->data[this->used++] = entry;
}

bag& bag::operator =(const bag& other) {
// Library facilities used:  algorithm
    //Check for possible self-assignment
    if (this == &other) return *this;

    this->used = other.used;
    copy(other.data, other.data + other.used, this->data);

    return *this;
}

void bag::operator +=(const bag& addend) {
// Library facilities used: algorithm, stdexcept
    if ( this->size() + addend.size() > bag::CAPACITY )
        throw overflow_error("not enough space to complete operation");
    
    //If we are here, we know that we have enough space to
    //copy addend's items onto this's items.  Investigate use of the
    //highly efficient copy function for fast copying array elements to
    //array elements
    //STUDENT IMPLEMENTATION HERE
    copy(addend.data, addend.data + addend.used, data + used);
    used += addend.used;
}

bool bag::operator ==(const bag& comparand) const {
    //check if comparing with self
    if (this == &comparand) return true;

    if (this->size() != comparand.size()) return false;
    bool isEqual = true;
    int index = 0;

    //STUDENT IMPLEMENTATION HERE
    while (isEqual && index < size()) {
        if (data[index] != comparand.data[index]) isEqual = false;
        index++;
    }

    return isEqual;
}

bool bag::operator !=(const bag& comparand) const {
    return !(*this == comparand);
}


bag::size_type bag::occurrences(const value_type& target) const {
    size_type answer;
    size_type i;

    answer = 0;
    //iterate over all used items in data and tally 
    //STUDENT IMPLEMENTATION HERE
    for (i = 0; i < used; i++) {
        if (data[i] == target) answer++;
    }

    return answer;
}

string bag::toString( ) const {
//Library facilities used:  sstream, string
    std::stringstream outs;
    outs << "bag with " << this->size() << " elements: [";
    if ( this->size() > 0 ) {
        for (int i=0; i < this->size()-1; ++i) {
            outs << " " << this->data[i];
            outs << ",";
        }
        outs << " " << this->data[this->size()-1];
    }
    outs << " ]";
    return outs.str();
}

bag operator +(const bag& b1, const bag& b2) {
// Library facilities used: cassert
    bag answer;

    if ( b1.size() + b2.size() > bag::CAPACITY )
        throw overflow_error("not enough space to complete operation");

    // answer should be built up using the += operator with b1 and
    // then b2
    //STUDENT IMPLEMENTATION HERE
    answer += b1;
    answer += b2;
    
    return answer;
}

std::ostream& operator <<(std::ostream& outs, const bag& b1) {
    outs << b1.toString();
    return outs;
}
