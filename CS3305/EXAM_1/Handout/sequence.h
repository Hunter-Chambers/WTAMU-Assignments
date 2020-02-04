// FILE: sequence.h
#ifndef SEQUENCE_H
#define SEQUENCE_H
#include <cstdlib>  // Provides size_t
#include <string>   // Provides string
#include "dlnode.h" // Provides dlnode class

template <class Item>
class sequence {
//INVARIANT for the sequence ADT:
//      1.  The items in the sequence are stored in a doubly-linked list supported by
//          two dummy nodes to simplify the logic
//      2.  The head pointer of the list is stored in the member variable head_ptr->next()
//          whose value is tail_ptr if the list is empty
//      3.  The tail pointer of the list is stored in the member variable tail_ptr->previous()
//          whose value is head_ptr if the sequence is empty
//      4.  The current node in the list is pointed to by the member variable cursor
//          whose value is either head_ptr or tail_ptr if there is no current node
//      5.  The total number of items in the list is stored in the member variable
//          number_nodes.
//      6.  Class supports value semantics; thus, in addition to default constructor, it must
//          implement copy constructor, assignment operator, and == and !=.
//
    private:
	dlnode<Item>*     head_ptr;
	dlnode<Item>*     tail_ptr;
	dlnode<Item>*     cursor;
        std::size_t       used;

    public:
        // TYPEDEFS and MEMBER CONSTANTS
        typedef dlnode_iterator<Item> iterator;
        typedef const_dlnode_iterator<Item> const_iterator;


        // CONSTRUCTORS and DESTRUCTOR
        sequence( );
        sequence(const sequence<Item>& source);
        ~sequence( );

        // MODIFICATION MEMBER FUNCTIONS
        void first( );
        void last( );
        void advance( );
        void retreat( );
        void add_after(const Item& entry);
        void add_before(const Item& entry);
        void remove_current( );
        void operator += (const sequence<Item>& source);
        sequence<Item>& operator =(const sequence<Item>& source);

        // CONSTANT MEMBER FUNCTIONS
        std::size_t size( ) const { return used; }
        bool is_current( ) const;
        Item current( ) const;
        std::string toString( ) const;
        sequence<Item> operator + ( const sequence<Item>& other ) const;

        // FUNCTIONS TO PROVIDE ITERATORS
        iterator begin( ) { return iterator(head_ptr->next( )); }
        const_iterator cbegin( ) const { return const_iterator(head_ptr->next( )); }
        iterator end( ) { return iterator( tail_ptr ); }
        const_iterator cend( ) const { return const_iterator( tail_ptr ); }

        iterator rbegin( ) { return iterator(tail_ptr->previous( )); }
        const_iterator crbegin( ) const { return const_iterator(tail_ptr->previous( )); }
        iterator rend( ) { return iterator( head_ptr ); } 
        const_iterator crend( ) const { return const_iterator( head_ptr ); } 


        // FRIEND FUNCTIONS
        template <class Itema>
        friend bool operator ==(const sequence<Itema>& s1, const sequence<Itema>& s2); 

        template <class Itema>
        friend bool operator !=(const sequence<Itema>& s1, const sequence<Itema>& s2); 
};

// NONMEMBER FUNCTION for the sequence class
template <class Item>
std::ostream& operator<<(std::ostream& outs, const sequence<Item>& seq);


#include <cstdlib>   // Provides NULL and size_t
#include <iostream>  // Provides << operator
#include <stdexcept> // Provides exceptions
#include <string>    // Provides string operations
#include <sstream>   // Provides stringstream

using namespace std;

template <class Item>
sequence<Item>::sequence( ) {
// Library facilities used: dlnode.h
    this->used = 0;
    this->head_ptr = new dlnode<Item>( );
    this->tail_ptr = new dlnode<Item>( );
    this->head_ptr->next() = this->tail_ptr;
    this->tail_ptr->previous() = this->head_ptr;
    this->cursor = this->head_ptr;
}

template <class Item>
sequence<Item>::sequence(const sequence<Item>& source) {
// Library facilities used: dlnode.h, cstdlib
    cout << "Entering copy constructor . . . " << endl;
    cout << "source to be copied: " << source << endl;
    this->head_ptr = new dlnode<Item>( );
    this->tail_ptr = new dlnode<Item>( );
    this->cursor = this->head_ptr;
    // Are we copying an empty list; then logic essentially that of constructor
    if ( source.used == 0 ) {
        this->used = 0;
        this->head_ptr->next() = this->tail_ptr;
        this->tail_ptr->previous() = this->head_ptr;
        cout << "this after copying empty source: " << *this << endl;
        cout << "Exiting copy constructor . . . " << endl;
        return;
    }
    else { //we have one or more nodes to copy
        //this->used = source.used;

        // STUDENT WORK HERE -- complete without use of list_copy or list_part from dlnode
        this->head_ptr->set_next(this->tail_ptr);
        this->tail_ptr->set_previous(this->head_ptr);

        dlnode<Item> * temp = this->head_ptr;
        dlnode<Item> * ref = source.head_ptr->next();
        
        this->used = 0;
        while(ref != source.tail_ptr){
            this->add_after(ref->data());
            if(ref == source.cursor)
                temp = this->cursor;
            ref = ref->next();
        }
        this->cursor = temp;
                

        cout << "this after the copy:  " << *this << endl;
    }

    cout << "Exiting copy constructor . . . " << endl;
}

template <class Item>
sequence<Item>::~sequence( ) {
    // Library facilities used: node.h, cstdlib
    cout << "Entering destructor -- sequence is: " << *this << endl;

    // STUDENT COMPLETE HERE -- delete all list nodes, then the "dummy" nodes
    this->first();

    while(this->cursor != this->tail_ptr){
        remove_current();
        //dlnode<Item> * temp = this->head_ptr;
        //this->cursor->next()->set_previous(this->cursor->previous());
        //this->cursor->previous()->set_next(this->cursor->next());
        //this->cursor = this->cursor->next();
        //delete temp;
    }
    delete this->tail_ptr;
    delete this->head_ptr;


    cout << "Completed destruction . . . "  << endl;
}

template <class Item>
void  sequence<Item>::first( ) {
    this->cursor = this->head_ptr->next();
}

template <class Item>
void  sequence<Item>::last( ) {
    this->cursor = this->tail_ptr->previous();
}

template <class Item>
void sequence<Item>::advance( ) {
    if (!this->is_current( ))
        throw out_of_range("Cannot advance when no current item"); 

    this->cursor = this->cursor->next();
}

template <class Item>
void sequence<Item>::retreat( ) {
    if (!this->is_current( ))
        throw out_of_range("Cannot retreat when no current item");

    this->cursor = this->cursor->previous();
}

template <class Item>
void sequence<Item>::add_before(const Item& entry) {
    this->used++;
    // STUDENT COMPLETE HERE
    if(!this->is_current())
        this->first();
    
    dlnode<Item>* newNode = new dlnode<Item>(this->cursor->previous(), entry, this->cursor);
    this->cursor->previous()->set_next(newNode);
    this->cursor->set_previous(newNode);
    this->cursor = newNode;
}

template <class Item>
void sequence<Item>::add_after(const Item& entry) {
    this->used++;
    // STUDENT COMPLETE HERE
    if(!this->is_current())
        this->cursor = this->tail_ptr->previous();

    dlnode<Item>* newNode = new dlnode<Item>(this->cursor, entry, this->cursor->next());
    this->cursor->next()->set_previous(newNode);
    this->cursor->set_next(newNode);
    this->cursor = newNode;
}

template <class Item>
void sequence<Item>::remove_current( ) {
    if (!is_current( ))
        throw out_of_range("Cannot remove current when no current item");
    // STUDENT COMPLETE HERE
    dlnode<Item>* temp = this->cursor;
    this->cursor->next()->set_previous(this->cursor->previous());
    this->cursor->previous()->set_next(this->cursor->next());
    this->cursor = this->cursor->next();
    delete temp;
    this->used--;

}

template <class Item>
sequence<Item>& sequence<Item>::operator =(const sequence<Item>& source) {

    // Check for possible self-assignment
    cout << "Entering operator = . . . " << endl;
    cout << " source coming into assignment: " << source << endl;
    if (this == &source) return *this;
    cout << " this before assignment operations: " << *this << endl;

    //   remember that assignment destructive and we cannot just
    //   cut loose all the nodes in list pointed to by head_ptr
    //   rather, we must node by node return the node's memory back
    //   to available memory with delete as is done by removing all
    //   data nodes

    // STUDENT WORK HERE -- complete without use of list_copy or list_part from dlnode
    // NOTE that in C++ if we create a local, static object, its destructor will be called
    //     upon exit from the function ==> probably not a good idea to create it and then
    //     try to fake system from removing its internal list.  Thus . . . 
    // delete all nodes from this
    this->first();

    while(this->cursor != this->tail_ptr){
        remove_current();
        //dlnode<Item> * temp = this->cursor;
        //this->cursor->next()->set_previous(this->cursor->previous());
        //this->cursor->previous()->set_next(this->cursor->next());
        //this->cursor = this->cursor->next();
        //delete temp;
        //this->used--;
    }


    cout << " this after deleting all nodes: " << *this << endl;

    //check to see if source empty; if so, logic is essentially same as for constructor
    //but don't use constructor
    if ( source.used == 0 ) {
        // STUDENT COMPLETE HERE
        this->used = 0;
        this->head_ptr->next() = this->tail_ptr;
        this->tail_ptr->previous() = this->head_ptr;
        this->cursor = this->head_ptr;
    }
    
    else { //we have one or more nodes to copy
        //this->used = source.used;

        // STUDENT COMPLETE HERE without use of list_copy or list_part from dlnode
        
        dlnode<Item> * temp = this->head_ptr;
        dlnode<Item> * ref = source.head_ptr->next();

        while(ref != source.tail_ptr){
            this->add_after(ref->data());
            if(ref == source.cursor)
                temp = this->cursor;
            ref = ref->next();
        }
        this->cursor = temp;


    }
    cout << "this is now ... " << *this << endl;
    cout << "Exiting operator = . . . " << endl;
    return *this;
}

template <class Item>
bool sequence<Item>::is_current( ) const {
    return ( (this->cursor != this->head_ptr) &&
             (this->cursor != this->tail_ptr) &&
             (this->cursor != NULL) );
}

template <class Item>
Item sequence<Item>::current( ) const {
    // Library facilities used: node.h, stdexcept
    if (!is_current( ))
        throw out_of_range("Cannot return current value when no current item");

    return this->cursor->data( );
}

template <class Item>
string sequence<Item>::toString( ) const {
    // Library facilities used: string, sstream, node.h
    stringstream outs;

    if ( this->head_ptr->next() == this->tail_ptr &&
         this->used != 0 ) {
        outs <<"sequence: [ ]";
        return outs.str();
    }
    outs << "sequence: " << this->used << " items";
    outs << "==>[ ";

    const dlnode<Item> *ptr = this->head_ptr->next( );
    if ( this->used > 0 ) {
        if ( this->cursor == ptr ) 
            outs << "^";
        outs << ptr->data( );
        ptr = ptr->next();
        while ( ptr != this->tail_ptr ) {
            outs << ", ";
            if ( this->cursor == ptr )
                outs << "^";
            outs << ptr->data( );
            ptr = ptr->next( );
        }
    }
    outs << " ]";
    
    return outs.str();
}

template <class Item>
void sequence<Item>::operator += (const sequence<Item>& other) {

    //STUDENT COMPLETE HERE
    dlnode<Item>* temp = other.head_ptr->next();
    dlnode<Item>* ref = this->cursor;
    int size = this->used + other.used;
    while(this->used != size){
        this->add_after(temp->data());
        temp = temp->next();
    }
    this->cursor = ref;
        
}
template <class Item>
sequence<Item> sequence<Item>::operator +( const sequence<Item>& other ) const{

    sequence<Item> new_sequence = sequence<Item>();


    // STUDENT COMPLETE HERE
    new_sequence += *this;
    new_sequence += other;

    return new_sequence;
}

template <class Item>
bool operator ==(const sequence<Item>& s1, const sequence<Item>& s2) {
	// Library facilities used: dlnode.h
    if ( s1.used != s2.used ) return false;
    if ( s1.is_current() && !s2.is_current() ) return false;
    if ( s2.is_current() && !s1.is_current() ) return false;

    dlnode<Item> * s1Ptr = s1.head_ptr;
    dlnode<Item> * s2Ptr = s2.head_ptr;
    bool isEqual = true;
    //must traverse both lists to check for a mismatch of the data
    //while so doing, check for cursor mismatch
    //
    // STUDENT COMPLETE HERE
    s1Ptr = s1Ptr->next();
    s2Ptr = s2Ptr->next();
    while(isEqual && s1Ptr != s1.tail_ptr){
        if (((s1Ptr == s1.cursor) && (s2Ptr != s2.cursor)) ||
                ((s1Ptr != s1.cursor) && (s2Ptr == s2.cursor)))
            isEqual = false;
        else if (s1Ptr->data() != s2Ptr->data()) isEqual = false;
        else {
            s1Ptr = s1Ptr->next();
            s2Ptr = s2Ptr->next();
        }
    }

    return isEqual; 
}

template <class Item>
bool operator !=(const sequence<Item>& s1, const sequence<Item>& s2) {
    return !( s1 == s2 );
}

template <class Item>
ostream& operator<<(ostream& outs, const sequence<Item>& seq) {
// Library facilities used:  iostream
    outs << seq.toString();
    return outs;
}

#endif

