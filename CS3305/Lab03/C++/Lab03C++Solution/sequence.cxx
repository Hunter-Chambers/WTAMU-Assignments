//FILE:  sequence.cxx
//CLASS implemented:  sequence (see sequence.h for documentation)
//INVARIANT for the sequence ADT:
//
/*
#   Class Invariant:
#       1.  The number of elements in the sequence is stored in the
#           instance variable __used which can be no
#           greater than len(__data)
#       2.  For an empty sequence, we do not care what is stored in
#           any element of __data.  For a non-empty sequence, the elements
#           are stored at __data[0] through __data[used-1]
#           and we don't care what is stored in any elements beyond
#           that.
#       3.  The array __data can store only long or int items.  Attempts
#           to store items of other types will cause a run-time error
#           to be raised.
#       4.  The relative ordering of items placed in this sequence is
#           maintained through all operations and is controlled by the
#           client of an object of this class.
#       5.  The instance variable capacity is used to hold the current
#           capacity of this container.
#       6.  The instance variable cursor is used to indicate which element
#           of the sequence is the current element
*/

#include <cstdlib>   // Provides NULL and size_t
#include <iostream>  // Provides << operator
#include <stdexcept> // Provides exceptions
#include <string>    // Provides string operations
#include <sstream>   // Provides stringstream

#include "sequence.h"

using namespace std;
typedef sequence::size_type size_type;
typedef sequence::value_type value_type;

sequence::sequence( const int initialCapacity ) {
    if ( initialCapacity < 1 ) {
        throw invalid_argument("initialCapacity cannot be < 1");
    }

    this->capacity = initialCapacity;
    this->cursor = 1;
    this->used = 0;
    this->data = new value_type[ initialCapacity ];
}

sequence::sequence(const sequence& source) {
// Library facilities used: algorithm

    // STUDENT IMPLEMENTATION GOES HERE
    if (source.used == 0) {
        capacity = 1;
        data = new value_type[1];
    } else {
        capacity = source.used;
        data = new value_type[source.used];
        copy(source.data, source.data + source.used, data);
    }

    cursor = source.cursor;
    used = source.used;
}

sequence::~sequence( ) {
// Library facilities used: cstdlib
    delete [] this->data;
    this->data = NULL;
}

void sequence::start( ) {
    this->cursor = 0;
}

void sequence::advance( ) {
// Library facilities used:  stdexcept
    if (!this->is_current())
        throw out_of_range("Cannot advance when no current item"); 

    ++this->cursor;
}

void sequence::add_before( value_type entry ) {
    /*
    cout << "Entering add_before . . .  \n";
    cout << *this << endl;
    */
    if ( this->used >= this->capacity )
        ensure_capacity( this->capacity * 2 );

    // STUDENT IMPLEMENTATION GOES HERE
    if (!is_current()) cursor = 0;

    for (int i = used; i >= (int) cursor; i--) {
        data[i] = data[i - 1];
    }

    data[cursor] = entry;
    used++;

    /*
    cout << "Exiting add_before . . .  \n";
    cout << *this << endl;
    */
}

void sequence::add_after( value_type entry ) {
    /*
    cout << "Entering add_after . . .  \n";
    cout << *this << endl;
    */
    if ( this->used >= this->capacity )
        ensure_capacity( this->capacity * 2 );

    // STUDENT IMPLEMENTATION GOES HERE
    if (!is_current()) cursor = used;
    if (cursor != used) cursor++;
    for (int i = used; i >= (int) cursor; i--) {
        data[i] = data[i - 1];
    }

    data[cursor] = entry;
    used++;

    /*
    cout << "Exiting add after . . .\n";
    cout << *this << endl;
    */
}

void sequence::remove_current( ) {
// Library facilities used: stdexcept
    /*
    cout << "Entering remove_current\n";
    cout << *this << endl;
    */
    if (!is_current())
        throw out_of_range("Cannot remove current when no current item");

    // STUDENT IMPLEMENTATION GOES HERE
    for (int i = cursor; i < (int) used; i++) {
        data[i] = data[i + 1];
    }

    used--;

    /*
    cout << "Exiting remove_current\n";
    cout << *this << endl;
    */
}

sequence& sequence::operator =( const sequence& source ) {
// Library facilities used: algorithm
    // Check for possible self-assignment
    if (this == &source) return *this;

    // STUDENT IMPLEMENTATION GOES HERE
    ensure_capacity(source.capacity);
    copy(source.data, source.data + source.used, data);
    used = source.used;
    cursor = source.cursor;
    return *this;
}

void sequence::ensure_capacity(size_type minimum_capacity) {
// Library facilities used: stdexcept, algorithm
    if ( minimum_capacity < 1 )
        throw invalid_argument("minimum_capacity must be at least 1");

    /*
    cout << "Entering ensure_capacity . . .\n";
    cout << *this << endl;
    */
    if ( this->capacity < minimum_capacity ) {
        elementList new_data = new value_type[minimum_capacity];
        this->capacity = minimum_capacity;
        //if above successful, then we need to copy items from 
        //current data into new_data
        copy ( this->data, this->data + this->used, new_data );
        /*
        for (int index=0; index < this->used; ++index)
            new_data[index] = this->data[index];
        */

        delete [] this->data; this->data = NULL;
        this->data = new_data;
    }
    /*
    cout << "Exiting ensure_capacity . . .\n";
    cout << *this << endl;
    */
}

void sequence::trim_to_size( ) {
    if ( this->used == 0 ) {
        delete[] data;
        data = new value_type[1];
        this->cursor = this->capacity = 1;
        return;
    }
    // STUDENT IMPLEMENTATION GOES HERE
    elementList temp = new value_type[used];

    for (size_type i = 0; i < used; i++) {
        temp[i] = data[i];
    }

    delete [] data; data = NULL;
    data = temp;
}

value_type sequence::get_current( ) const {
// Library facilities used: stdexcept
    if (!is_current())
        throw out_of_range("Cannot return current value when no current item");

    return this->data[this->cursor];
}

string sequence::toString( ) const {
// Library facilities used: string, sstream
    stringstream outs;
    outs << "sequence:  " << this->used << " items -->[ ";
    if ( this->used > 0 ) {
        if ( this->cursor == 0 ) outs << "^";
        outs << this->data[0];
        for (size_type index=1; index < this->used; ++index) {
            outs << ", ";
            if ( index == this->cursor ) outs << "^";
            outs << this->data[index];
        }
    }
    outs << " ] Capacity: " << this->capacity;

    return outs.str();
}

bool sequence::operator ==(const sequence& s) const {
    if ( this->used != s.used ) return false;
    if ( this->cursor != s.cursor ) return false;
    bool isEqual = true;
    int index = 0;
    while ( isEqual && index < this->used ) {
        if ( this->data[index] != s.data[index] )
            isEqual = false;
        else
            ++index;
    }
    return isEqual; 
}

bool sequence::operator !=(const sequence& s) const {
    return !( *this == s );
}

sequence sequence::operator +(const sequence& source) const {
    size_type new_capacity = this->used + source.used;

    //sequence result(new_capacity);
    // STUDENT IMPLEMENTATION GOES HERE
    sequence result(source);

    if (used + source.used != 0) {
        result.ensure_capacity(used + source.used);
    }

    for (size_type i = 0; i < used; i++) {
        result.data[result.used++] = data[i];
    }

    result.cursor = -1;
    return result;
}


ostream& operator<<(ostream& outs, const sequence& seq) {
// Library facilities used:  iostream
    outs << seq.toString();
    return outs;
}

