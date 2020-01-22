// FILE: sequence.h

#ifndef SEQUENCE_H
#define SEQUENCE_H
#include <cstdlib>  // Provides size_t
#include <string>   // Provides string
#include <iterator> // Provides iterator

template <class Item> class sequence {
   public:
        // TYPEDEFS and MEMBER CONSTANTS
        typedef std::size_t size_type;

   private:
        size_type capacity;
        size_type used;
        size_type cursor;
        Item * data;

   public:
        // CONSTRUCTORS and DESTRUCTOR
        sequence( size_type initialCapacity = 1 );
        sequence( const sequence& source );
        ~sequence( );

        // MODIFICATION MEMBER FUNCTIONS
        void start( );
        void advance( );
        void add_before(Item entry);
        void add_after(Item entry);
        void remove_current( );
        sequence& operator =(const sequence& source);
        void ensure_capacity(size_type minimum_capacity);
        void trim_to_size();

        // CONSTANT MEMBER FUNCTIONS
        size_type size( ) const { return this->used; }
        bool is_current( ) const { return (cursor < used); }
        Item get_current( ) const;
        size_type get_capacity( ) const { return this->capacity; }
        std::string toString( ) const;
        bool operator ==(const sequence& s) const; 
        bool operator !=(const sequence& s) const; 
        sequence operator +(const sequence& source) const;

        // FUNCTIONS TO PROVIDE ITERATOR
        typedef Item * seq_iterator;
        seq_iterator begin( ) const { return data; }
        seq_iterator end( )   const { return data+used; }
};

// NONMEMBER FUNCTION for the sequence class
template <class Item>
std::ostream& operator<<(std::ostream& outs, const sequence<Item>& seq);

//CLASS implemented:  sequence<Item>
//INVARIANT for the sequence ADT:
//
/*
#   Class Invariant:
#       1.  The number of elements in the sequence is stored in the
#           instance variable used which can be no
#           greater than capacity
#       2.  For an empty sequence, we do not care what is stored in
#           any element of data.  For a non-empty sequence, the elements
#           are stored at data[0] through data[used-1]
#           and we don't care what is stored in any elements beyond
#           that.
#       3.  The array data can store only long or int items.  Attempts
#           to store items of other types will cause a run-time error
#           to be raised.
#       4.  The relative ordering of items placed in this sequence is
#           maintained through all operations and is controlled by the
#           client of an object of this class.:
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


using namespace std;
typedef std::size_t size_type;

template <class Item>
sequence<Item>::sequence( size_type initialCapacity ) {
    if ( initialCapacity < 1 ) {
        throw invalid_argument("initialCapacity cannot be < 1");
    }

    this->capacity = initialCapacity;
    this->cursor = 0;
    this->used = 0;
    this->data = new Item[ initialCapacity ];
}

template <class Item>
sequence<Item>::sequence(const sequence& source) {
// Library facilities used: algorithm
    
    size_type newCapacity = source.used > 0 ? source.used : 1;
    this->used = source.used;
    this->capacity = newCapacity;
    this->cursor = source.cursor;
    this->data = new Item[ newCapacity ];
    /* copying is "deep" provided Item has a copy constructor */
    copy( source.data, source.data + source.used, this->data );
}

template <class Item>
sequence<Item>::~sequence( ) {
// Library facilities used: cstdlib
    delete [] this->data;
    this->data = NULL;
}

template <class Item>
void sequence<Item>::start( ) {
    this->cursor = 0;
}

template <class Item>
void sequence<Item>::advance( ) {
// Library facilities used:  stdexcept
    if (!this->is_current())
        throw out_of_range("Cannot advance when no current item"); 

    this->cursor++;
}

template <class Item>
void sequence<Item>::add_before( Item entry ) {
    /*
    cout << "Entering add_before . . .  \n";
    cout << *this << endl;
    */
    if ( this->used == this->capacity )
        ensure_capacity( this->capacity * 2 );
    
    int where;
    if ( this->is_current() )
        where = this->cursor;
    else
        where = 0;

    this->cursor = where;
    int index = this->used;
    if ( index == 0 )
        this->data[ index ] = entry;
    else {
        while ( index > where ) {
            this->data[ index ] = this->data[ index - 1 ];
            --index;
        }
        this->data[ where ] = entry;
    }

    this->used++;
    /*
    cout << "Exiting add_before . . .  \n";
    cout << *this << endl;
    */
}

template <class Item>
void sequence<Item>::add_after( Item entry ) {
    /*
    cout << "Entering add_after . . .  \n";
    cout << *this << endl;
    */
    if ( this->used == this->capacity )
        ensure_capacity( this->capacity * 2 );

    int where;
    if ( !this->is_current() ) {
        this->data[ used ] = entry;
        this->cursor = used;
    }
    else {
        int index = this->used-1;
        int where = ++this->cursor;
        while ( index >= where ) {
            this->data[index+1] = this->data[index];
            --index;
        }
        this->data[where] = entry;
    }
    this->used++;
    /*
    cout << "Exiting add after . . .\n";
    cout << *this << endl;
    */
}

template <class Item>
void sequence<Item>::remove_current( ) {
// Library facilities used: stdexcept
    /*
    cout << "Entering remove_current\n";
    cout << *this << endl;
    */
    if (!is_current())
        throw out_of_range("Cannot remove current when no current item");

    for (int index = this->cursor; index < this->used; ++index) {
        this->data[index] = this->data[index+1];
    }
    this->used--;
    /*
    cout << "Exiting remove_current\n";
    cout << *this << endl;
    */
}

template <class Item>
sequence<Item>& sequence<Item>::operator =( const sequence& source ) {
// Library facilities used: algorithm
    // Check for possible self-assignment
    if (this == &source) return *this;

    Item * new_data = new Item[source.capacity];
    /* the copy is "deep" if Item has a copy constructor */
    copy(source.data, source.data + source.used, new_data);
    delete [] this->data; this->data = NULL;

    this->data = new_data; 
    this->used = source.used;
    this->capacity = source.capacity;
    this->cursor = source.cursor;

    return *this;
}

template <class Item>
void sequence<Item>::ensure_capacity(size_type minimum_capacity) {
// Library facilities used: stdexcept, algorithm
    if ( minimum_capacity < 1 )
        throw invalid_argument("minimum_capacity must be at least 1");

    /*
    cout << "Entering ensure_capacity . . .\n";
    cout << *this << endl;
    */
    if ( this->capacity < minimum_capacity ) {
        Item * new_data = new Item[minimum_capacity];
        this->capacity = minimum_capacity;
        //if above successful, then we need to copy items from 
        //current data into new_data
        /* copy is "deep" if Item has a copy constructor */
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

template <class Item>
void sequence<Item>::trim_to_size( ) {
    if ( this->used == 0 ) {
        delete[] data;
        data = new Item[1];
        this->cursor = 0;
        this->capacity = 1;
        return;
    }

    if ( this->capacity > this->used ) {
        Item * trimmed_array = new Item[this->used];
        this->capacity = this->used;
        /* the copy is "deep" if Item has a copy constructor */
        copy ( this->data, this->data + this->used, trimmed_array );
        /*
        for (size_type index=0; index < this->used; ++index)
            trimmed_array[index] = this->data[index];
        */
        delete [] data; data = NULL;
        data = trimmed_array;
    }
}

template <class Item>
Item sequence<Item>::get_current( ) const {
// Library facilities used: stdexcept
    if (!is_current())
        throw out_of_range("Cannot return current value when no current item");

    return this->data[this->cursor];
}

template <class Item>
string sequence<Item>::toString( ) const {
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

template <class Item>
bool sequence<Item>::operator ==(const sequence<Item>& s) const {
    if ( this->used != s.used ) return false;
    if ( this->cursor != s.cursor ) return false;
    bool isEqual = true;
    int index = 0;
    while ( isEqual && index < this->used ) {
        /* != valid if Item supports != */
        if ( this->data[index] != s.data[index] )
            isEqual = false;
        else
            ++index;
    }
    return isEqual; 
}

template <class Item>
bool sequence<Item>::operator !=(const sequence<Item>& s) const {
    return !( *this == s );
}

template <class Item>
sequence<Item> sequence<Item>::operator +(const sequence<Item>& source) const {
    size_type new_capacity = this->used + source.used;
    if ( new_capacity < 1 ) new_capacity = 1;

    sequence<Item> result(new_capacity);
    /* the copy is "deep" if Item has a copy constructor */
    copy( this->data, this->data + this->used, result.data );
    copy( source.data, source.data + source.used, result.data + this->used );
    result.cursor = new_capacity;
    result.used = this->used + source.used;

    return result;
}


template <class Item>
ostream& operator<<(ostream& outs, const sequence<Item>& seq) {
// Library facilities used:  iostream
    outs << seq.toString();
    return outs;
}

#endif

