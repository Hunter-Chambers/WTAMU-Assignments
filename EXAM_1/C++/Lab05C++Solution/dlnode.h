// FILE: dlnode.h
// PROVIDES: A class for a node in a doubly linked list, and list manipulation
//           functions

#ifndef DNODE_H  
#define DNODE_H

#include <cstdlib>   // Provides size_t and NULL
#include <stdexcept> // Provides exception classes
#include <sstream>   // Provides ostream
#include <iterator>  // Provides iterator

template <class Item>
class dlnode {
    private:
	dlnode *previous_field;
	Item data_field;
	dlnode *next_field;

    public:
	// CONSTRUCTOR
	dlnode(
	    dlnode* init_previous = NULL,  
	    const Item& init_data = Item( ),
	    dlnode* init_next = NULL ) {
		this->previous_field = init_previous;
		this->data_field     = init_data;
		this->next_field     = init_next;
        }

	// Member functions to set the data and link fields:
    	void set_data(const Item& new_data)      { this->data_field = new_data; }
        Item& data( )                            { return data_field; }

    	void set_next(dlnode* new_next)          { this->next_field = new_next; }
        dlnode*& next( )                         { return this->next_field; }

    	void set_previous(dlnode* new_previous)  { this->previous_field = new_previous; }
        dlnode*& previous( )                     { return this->previous_field; }

	// const member functions to retrieve links and data:
	const Item& data( )   const { return data_field; }
	const dlnode* next( ) const { return this->next_field; }
	const dlnode* previous( ) const { return this->previous_field; }

        // Member functions to add add nodes relative to this node
        void add_before( const Item& element ) {
            dlnode<Item> * new_node = new dlnode<Item>( this->previous(), element, this );
            if ( this->previous() != NULL )
                this->previous()->next()= new_node;
            this->previous() = new_node;
        }

        void add_after( Item element ) {
            dlnode<Item> * new_node = new dlnode<Item>( this, element, this->next() );
            if ( this->next() != NULL )
                this->next()->previous() = new_node;
            this->next() = new_node;
        }

        // Member function to remove the node pointed to by this->previous()
        // and patch links
        void remove_before( ) {
            if ( this->previous() == NULL )
                throw std::invalid_argument("No node before -- cannot remove");
            dlnode<Item>* trash = this->previous();
            if ( trash->previous() != NULL ) trash->previous()->next() = this;
            this->previous() = trash->previous();
            trash->previous() = trash->next() = NULL;
            delete trash;
            trash = NULL;
        }

        // Member function to remove the node pointed to by this->next()
        // and patch links
        void remove_after( ) {
            if ( this->next() == NULL ) 
                throw std::invalid_argument("No node after -- cannot remove");
            dlnode<Item> *trash = this->next();
            if ( trash->next() != NULL ) trash->next()->previous() = this;
            this->next()= trash->next();
            trash->previous() = trash->next() = NULL;
            delete trash;
            trash = NULL;
        }

};

// Prototypes for supporting functions
template <class Item>
void list_clear( dlnode<Item>*& head_ptr, dlnode<Item>* tail_ptr );

template <class Item>
void list_copy( const dlnode<Item>*  source_ptr, const dlnode<Item>*  sentinel_ptr,
                      dlnode<Item>*& head_ptr, dlnode<Item>*& tail_ptr );
template <class Item>
void list_part( const dlnode<Item>* start_ptr, const dlnode<Item>* end_ptr, 
                 dlnode<Item>*& head_ptr, dlnode<Item>*& tail_ptr   );

template <class Item>
dlnode<Item>* list_locate( const dlnode<Item>* head_ptr, std::size_t position );

template <class Item>
dlnode<Item>* list_search( const dlnode<Item>* head_ptr, const Item& target );

// Implementation for supporting functions
template <class Item>
void list_clear( dlnode<Item>*& head_ptr, dlnode<Item>* tail_ptr ) {
    dlnode<Item> * remove_ptr;
    while ( head_ptr != tail_ptr ) {
        remove_ptr = head_ptr;
        head_ptr = head_ptr->next( );
        delete remove_ptr;
    }
}

template <class Item>
void list_copy( const dlnode<Item>*  source_ptr, const dlnode<Item>*  sentinel_ptr,
                      dlnode<Item>*& new_head_ptr, dlnode<Item>*& new_tail_ptr ) {
    // Copies all nodes beginning with source_ptr up to, but not including,
    // sentinel_ptr
    new_head_ptr = new_tail_ptr = NULL;

    // Handle case of the empty list
    if ( source_ptr == NULL ) return;

    // Make the head node for the newly created list
    new_head_ptr = new dlnode<Item>( NULL, source_ptr->data( ), NULL );
    new_tail_ptr = new_head_ptr;

    // Copy remaining nodes, one at a time, into new list
    source_ptr = source_ptr->next( );
    while ( source_ptr != sentinel_ptr ) {
        new_tail_ptr->add_after( source_ptr->data( ) );
        new_tail_ptr = new_tail_ptr->next( );
        source_ptr = source_ptr->next( );
    }
}

template <class Item>
void list_part( const dlnode<Item>* start_ptr, const dlnode<Item>* end_ptr, 
                 dlnode<Item>*& new_head_ptr, dlnode<Item>*& new_tail_ptr   ) {
    new_head_ptr = new_tail_ptr = NULL;

    if ( start_ptr == NULL || end_ptr == NULL ) return;

    //Make the head node for the newly created list, and put data in it
    new_head_ptr = new dlnode<Item>( NULL, start_ptr->data( ), NULL );
    new_tail_ptr = new_head_ptr;

    //Copy remaining nodes, one at a time, into new list
    while ( start_ptr != end_ptr->next() ) {
        new_tail_ptr->add_after( start_ptr->data( ) );
        new_tail_ptr = new_tail_ptr->next( );
        start_ptr = start_ptr->next( );
    }
}


template <class Item>
dlnode<Item>* list_locate( const dlnode<Item>* head_ptr, std::size_t position ) {
    dlnode<Item>* cursor;
    std::size_t i;

    if ( position < 1 )
        throw std::invalid_argument("position must be >= 1");

    cursor = head_ptr;
    for( i = 1; ( i < position ) && ( cursor != NULL ); ++i )
        cursor = cursor->next( );
    return cursor;
}

template <class Item>
dlnode<Item>* list_search( const dlnode<Item>* head_ptr, const Item& target ) {
    dlnode<Item>* cursor;
    bool found = false;

    cursor = head_ptr;

    for ( cursor = head_ptr; !found && cursor != NULL; cursor = cursor->next( ) )
        if ( target == cursor->data( ) ) found = true;

    return cursor;
}

template <class Item>
std::ostream& operator<<(std::ostream& outs, const dlnode<Item>& node) {
// Library facilities used:  iostream
    outs << "[" << node.previous() << "|" << node.data() << "|" << node.next() << "]";
    return outs;
}


// ITERATORS to step through the nodes of a linked list
// A node_iterator can change the underlying linked list through the
// * operator, so it may not be used with a const node. The
// dlnode_const_iterator cannot change the underlying linked list
// through the * operator, so it may be used with a const node.

template <class Item>
class dlnode_iterator
    : public std::iterator<std::bidirectional_iterator_tag, Item> {
    public:
        dlnode_iterator(dlnode<Item>* initial = NULL) { 
            cursor = initial; 
        }

        Item& operator *( ) const { 
            return cursor->data( ); 
        }

        dlnode_iterator& operator ++( ) /* Prefix ++ */{ 
            cursor = cursor->next( );
            return *this;
        }

        dlnode_iterator& operator --( ) /* Prefix --*/ {
            cursor = cursor->previous( );
            return *this;
        }

        dlnode_iterator operator ++(int) /* Postfix ++*/ {
            dlnode_iterator original(cursor);
            cursor = cursor->next( );
            return original;            
        }

        dlnode_iterator operator --(int) /* Postfix -- */ {
            dlnode_iterator original(cursor);
            cursor = cursor->previous( );
            return original;
        }

        bool operator ==(const dlnode_iterator other) const { 
            return cursor == other.cursor; 
        }

        bool operator !=(const dlnode_iterator other) const { 
            return cursor != other.cursor; 
        }

    private:
        dlnode<Item>* cursor;
};

template <class Item>
class const_dlnode_iterator
    : public std::iterator<std::bidirectional_iterator_tag, const Item> {

    public:
        const_dlnode_iterator(const dlnode<Item>* initial = NULL) { 
            cursor = initial; 
        }

        const Item& operator *( ) const { 
            return cursor->data( ); 
        }

        const_dlnode_iterator& operator ++( ) /* Prefix ++ */ {
            cursor = cursor->next( );
            return *this;
        }

        const_dlnode_iterator& operator --( ) /* Prefix -- */ {
            cursor = cursor->previous( );
            return *this;
        }

        const_dlnode_iterator operator ++(int) /* Postfix ++ */ {
            const_dlnode_iterator original(cursor);
            cursor = cursor->next( );
            return original;
        }

        const_dlnode_iterator operator --(int) /* Postfix -- */ {
            const_dlnode_iterator original(cursor);
            cursor = cursor->previous( );
            return original;
        }

        bool operator ==(const const_dlnode_iterator other) const { 
            return cursor == other.cursor; 
        }

        bool operator !=(const const_dlnode_iterator other) const { 
            return cursor != other.cursor; 
        }

    private:
        const dlnode<Item>* cursor;
};


#endif
