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

template <class RecordType>
table<RecordType>::table() {
    total_records = 0;
    for (size_t i = 0; i < TABLE_SIZE; i++)
        data[i] = NULL;
}

template <class RecordType>
table<RecordType>::table(const table& source) {
    
    /*
    total_records = source.total_records;
    node<RecordType>* tail; // dummy tail

    // Note: cannot use the array iterator here
    // because the values get reset to their original
    // values when the loop finishes
    for (size_t i = 0; i < TABLE_SIZE; i++)
        list_copy(source.data[i], data[i], tail);
    */

    for (size_t i = 0; i < TABLE_SIZE; i++)
        data[i] = NULL;
    total_records = 0;
    if (source.total_records != 0) {
        for (size_t i = 0; i < TABLE_SIZE; i++) {
            node<RecordType>* cursor = source.data[i];
            while (cursor != NULL) {
                insert(cursor->data());
                cursor = cursor->link();
            }
        }
    }
}

template <class RecordType>
table<RecordType>::~table() {
    
    /*
    for (node<RecordType>* trash : data)
        list_clear(trash);
    */

    for (size_t i = 0; i < TABLE_SIZE; i++) {
        node<RecordType>* trash = data[i];
        while (trash != NULL) {
            node<RecordType>* cursor = trash->link();
            delete trash;
            trash = cursor;
        }
    }
}

template <class RecordType>
bool table<RecordType>::is_present(int key) const {
    bool found = false;
    size_t location = hash(key);
    node<RecordType>* head_ptr = data[location];

    while (!found && head_ptr != NULL) {
        if (head_ptr->data().key == key) found = true;
        else head_ptr = head_ptr->link();
    }

    return found;
}

template <class RecordType>
void table<RecordType>::insert(const RecordType& entry) {
    if (entry.key < 0) throw std::invalid_argument("received negative input");

    size_t location = hash(entry.key);
    node<RecordType>* cursor = data[location];
    bool found = is_present(entry.key);

    if (found) {
        while (cursor->data().key != entry.key) cursor = cursor->link();
        cursor->set_data(entry);
    } else {
        
        /*
        list_head_insert(head_ptr, entry);
        data[location] = head_ptr;
        */

        node<RecordType>* new_node = new node<RecordType>(entry, cursor);
        data[location] = new_node;

        total_records++;
    }

}

template <class RecordType>
void table<RecordType>::remove(int key) {
    size_t location = hash(key);
    node<RecordType>* cursor = data[location];
    if (cursor == NULL) return;
    node<RecordType>* trash = cursor->link();

    if (cursor->data().key == key) {
        data[location] = trash;
        delete cursor;
        cursor = NULL;
        total_records--;
    } else {
        while (trash != NULL) {
            if (trash->data().key == key) {
                cursor->set_link(trash->link());
                delete trash;
                trash = NULL;
                total_records--;
            } else {
                cursor = trash;
                trash = trash->link();
            }
        }
    }
}

template <class RecordType>
void table<RecordType>::operator =(const table& source) {
    if (this != &source) {
        
        /*
        for (node<RecordType>* trash : data)
            list_clear(trash);

        node<RecordType>* temp;

        for (size_t i = 0; i < TABLE_SIZE; i++)
            list_copy(source.data[i], data[i], temp);

        total_records = source.total_records;
        */
        
        for (size_t i = 0; i < TABLE_SIZE; i++) {
            node<RecordType>* trash = data[i];
            while (trash != NULL) {
                node<RecordType>* cursor = trash->link();
                delete trash;
                trash = cursor;
            }
            data[i] = NULL;
        }

        total_records = 0;

        if (source.total_records != 0) {
            for (size_t i = 0; i < TABLE_SIZE; i++) {
                node<RecordType>* cursor = source.data[i];
                while (cursor != NULL) {
                    insert(cursor->data());
                    cursor = cursor->link();
                }
            }
        }
    }
}

template <class RecordType>
void table<RecordType>::find(int key, bool& found, RecordType& result) const {
    found = false;
    size_t location = hash(key);
    node<RecordType>* head_ptr = data[location];

    while (!found && head_ptr != NULL) {
        if (head_ptr->data().key == key) {
            found = true;
            result = head_ptr->data();
        } else head_ptr = head_ptr->link();
    }
}

#endif
