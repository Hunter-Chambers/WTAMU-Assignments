// FILE: table.h

#ifndef TABLE_H
#define TABLE_H

#include <cstdlib>    // Provides size_t
#include <string>     // Provides string
#include <sstream>    // Provides stringstream
#include "node.h"     // Provides the node type as used previously

template <class RecordType>
class table {

    public:
        static const std::size_t TABLE_SIZE = 811;
        // CONSTRUCTORS AND DESTRUCTOR
        table( );
        table(const table& source);
        ~table( );
        // MODIFICATION MEMBER FUNCTIONS
        void insert(const RecordType& entry);
        void remove(int key);
        void operator =(const table& source);
        // CONSTANT MEMBER FUNCTIONS
        void find(int key, bool& found, RecordType& result) const;
        bool is_present(int key) const;
        std::size_t size( ) const { return total_records; }
        std::string toString( ) const;

    private:
        node<RecordType> *data[TABLE_SIZE];
        std::size_t total_records;
        // HELPER MEMBER FUNCTION
        std::size_t hash(int key) const;
};

// NONMEMBER FUNCTION for the table class
template <class RecordType>
std::ostream& operator<<(std::ostream& outs, const table<RecordType>& seq);


// INVARIANT for the table ADT:
//      STUDENT: Provide a solid and complete invariant for this implementation
#include <stdexcept>  // Provides exceptions
#include <cstdlib>    // Provides size_t
#include <iostream>   // Provides <<
#include <sstream>    // Provides stringstream
#include <string>     // Provides string


template <class RecordType>
const std::size_t table<RecordType>::TABLE_SIZE;


// STUDENT -- implement ALL methods of the table class starting with 
//            constructors, destructor, etc.
        
template <class RecordType>
table<RecordType>::table( ) {
    std::size_t index;

    // STUDENT code here
}

template <class RecordType>
table<RecordType>::table( const table& source ) {
    std::size_t index;

    // STUDENT code here

}

template <class RecordType>
table<RecordType>::~table( ) {
    std::size_t index;

    // STUDENT code here

}

template <class RecordType>
void table<RecordType>::insert(const RecordType& entry) {

    std::size_t index = hash(entry.key);
    
    // STUDENT code here

}

template <class RecordType>
void table<RecordType>::remove(int key) {
    //std::cout << "Entering remove with key = " << key << "\n";
    std::size_t index = hash(key);

    // STUDENT code here

}

template <class RecordType>
void table<RecordType>::operator =( const table& source ) {
    
    size_t index;

    // STUDENT code here

}

template <class RecordType>
void table<RecordType>::find(int key, bool& found, RecordType& result) const {

    std::size_t index = hash(key);

    // STUDENT code here

}

template <class RecordType>
bool table<RecordType>::is_present(int key) const {

    size_t index = hash(key);
    bool found = false;
    node<RecordType>* ptr = data[index];

    // STUDENT code here

    return found;
}

template <class RecordType>
std::string table<RecordType>::toString( ) const {
// Library facilities used string, sstream, node.h
    std::stringstream outs;
    outs << "Table: number records " << total_records << "\n";
    outs << "[\n";
    if ( total_records == 0 ) {
        outs << "]";
        return outs.str();
    }

    size_t index;
    for ( index = 0; index < TABLE_SIZE; ++index ) {
        if ( data[index] != NULL ) {
            outs << "Slot " << index << ": ";
            const node<RecordType>* ptr = data[index];
            while ( ptr->link() != NULL ) {
                outs << "(" << ptr->data().key << "," << ptr->data().data << ") ";
                ptr = ptr->link();
            }
            outs << "(" << ptr->data().key << "," << ptr->data().data << ")\n";
        }
    }
    outs << "]\n";
    return outs.str();
}

template <class RecordType>
std::ostream& operator<<(std::ostream& outs, const table<RecordType>& tbl) {
//Library facilities used:  iostream
    outs << tbl.toString();
    return outs;
}

template <class RecordType>
inline std::size_t table<RecordType>::hash(int key) const {
    // returns value in range 0 <= retValue < TABLE_SIZE
    return key % TABLE_SIZE;
}

#endif
