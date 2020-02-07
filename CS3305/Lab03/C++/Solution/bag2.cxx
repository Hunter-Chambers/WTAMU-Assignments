// FILE: bag2.cxx
// CLASS IMPLEMENTED: bag (see bag2.h for documentation)
// INVARIANT for the bag class:
//   1. The number of items in the bag is in the member variable used;
//   2. The bag's capacity is maintained in the member variable capacity
//      and the capacity can grow and shrink as needed;
//   3. For an empty bag, we do not care what is stored in any of data; for a
//      non-empty bag the items in the bag are stored in data[0] through
//      data[used-1], and we don't care what's in the rest of data.
//   4. The bags capacity is maintained in member variable capacity.

#include <algorithm> // Provides copy function
#include <sstream>   // Provides ostream
#include <string>    // Provides string
#include <stdexcept> // Provides exceptions
#include <functional>// Provides hash functions
#include "bag2.h"

using namespace std;

typedef bag::size_type size_type;  //render typedef visible at outer scope

bag::bag(size_type initialCapacity) {
    this->used = 0;
    if ( initialCapacity < 1 )
        throw std::invalid_argument("initialCapacity must be > 0");
    this->data = new value_type[initialCapacity];
    this->capacity = initialCapacity;
}

bag::bag(const bag& other) {
// Library facilities used:  algorithm
    this->used = other.used;
    this->data = new value_type[other.used];
    this->capacity = other.used;
    copy(other.data, other.data + other.used, this->data);
}

bag::~bag() {
    //cout << "In destructor deleting data with " << this->capacity << " element array\n";
    delete [] this->data;
    this->data = NULL;
}

void bag::ensureCapacity(size_type newCapacity) {
// Library facilities used: algorithm, stdexcept
    if ( newCapacity < 1 )
        throw std::invalid_argument("newCapacity must be > 0");
    //cout << "this bag at entry to ensureCapacity " << *this << endl;
    if ( this->capacity < newCapacity ) {
        // Now we must allocate the bigger array, copy items from this->data
        // into bigger array, deallocate this->data, assign to this->data 
        // the bigger array, etc,
        // STUDENT WORK GOES HERE
        value_type* newData = new value_type[newCapacity];
        copy(data, data + used, newData);
        delete [] data;
        data = newData;
        capacity = newCapacity;
    }
}
    
bag::size_type bag::erase(const value_type& target) {
    size_type index = 0;
    size_type number_removed = 0;

    while (index < this->used) {
        bool found = false;
        while (!found && index < this->used) {
            if (target == this->data[index])
                found = true;
            else
                ++index;
        }
        if (found) {
            this->data[index] = this->data[--this->used];
            ++number_removed;
        }
    }
    return number_removed;
}

bool bag::erase_one(const value_type& target) {
    size_type index = 0; // The location of target in the data array    
    bool found = false;

    while (!found && index < this->used) {
        if ( target == this->data[index] )
            found = true;
        else
            ++index;
    }

    if (found)
        this->data[index] = this->data[--this->used];    
    return found;
}

size_type bag::getCapacity() const {
    return this->capacity;
}

void bag::insert(const value_type& entry) {
    if (this->size( ) == this->capacity)
        this->ensureCapacity( this->capacity * 2 );

    this->data[this->used++] = entry;
}

bag& bag::operator =(const bag& other) {
// Library facilities used:  algorithm
    //Check for possible self-assignment
    if (this == &other) return *this;

    this->used = other.used;
    value_type* newData = new value_type[other.used];

    copy(other.data, other.data + other.used, newData);
    delete [] this->data; this->data = NULL;

    this->data = newData;
    newData = NULL;

    return *this;
}

void bag::operator +=(const bag& addend) {
// Library facilities used: algorithm
    // STUDENT WORK GOES HERE
    //  Guarantee that we have enough room to hold all the items
    //  from addend and then copy them into this bag BUT ensure
    //  that needed capacity is at least 1, then copy all
    //  elements from added into this bag and ensure bag's
    //  attributes continue to correctly support class
    //  invariants
    if (size() || addend.size()) {
        ensureCapacity(used + addend.used);
        copy(addend.data, addend.data + addend.used, data + used);
        used += addend.used;
    }
}

size_type bag::hashCode() const {
// Library facilities used:  functional
    size_type hashValue;
    std::hash<size_type> hashST;
    std::hash<value_type> hashVT;

    hashValue = hashST(this->used);
    for (size_type i = 0; i < this->used; ++i) {
        hashValue += hashVT(this->data[ i ]);
    }
}

bool bag::operator ==(const bag& comparand) const {
    //check if comparing with self
    if (this == &comparand) return true;

    if (this->size() != comparand.size()) return false;

    if (this->hashCode() != comparand.hashCode()) return false;
    bool isEqual = true;
    int index = 0;
    while ( isEqual && index < this->used ) {
        if ( this->data[index] != comparand.data[index] )
            isEqual = false;
        else
            ++index;
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
    for (i = 0; i < this->used; ++i) {
        if (target == this->data[i]) ++answer;
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
    outs << " Capacity " << this->capacity;
    return outs.str();
}

void bag::trimToSize() {
// Library facilities used:  algorithm
    if ( this->used < this->capacity ) {
        //cout << "Entering trimToSize()\n" << *this << endl;
        int newCapacity;
        if ( this->used <= 1 ) 
            newCapacity = 1;
        else
            newCapacity = this->used;
        // STUDENT WORK GOES HERE
        //      We must allocate a new smaller array, copy the elements 
        //      from this->data into the smaller array, deallocate this->data,
        //      and then assign to this->data the smaller array
        value_type* newData = new value_type[newCapacity];
        copy(data, data + used, newData);
        delete [] data;
        data = newData;
        capacity = newCapacity;
    }
}

bag operator +(const bag& b1, const bag& b2) {
    // STUDENT WORK GOES HERE
    //  We must guarantee that the new bag has exactly the 
    //  capacity needed to hold all items from both b1 and b2
    //  and then copy the items from b1 and then b2 into the new
    //  bag -- watch out for b1 and b2 both being empty
    
    //  replace this with appropriate return
    bag newBag(b1);
    if (b2.size()) newBag += b2;
    return newBag;
}

std::ostream& operator <<(std::ostream& outs, const bag& b1) {
    outs << b1.toString();
    return outs;
}
