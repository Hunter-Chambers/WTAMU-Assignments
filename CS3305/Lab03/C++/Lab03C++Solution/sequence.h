// FILE: sequence.h

#ifndef SEQUENCE_H
#define SEQUENCE_H
#include <cstdlib>  // Provides size_t
#include <string>   // Provides string
#include <iterator> // Provides iterator

class sequence {
   public:
        // TYPEDEFS and MEMBER CONSTANTS
        typedef long value_type;
        typedef std::size_t size_type;
        typedef value_type* elementList;

   private:
        size_type capacity;
        size_type used;
        size_type cursor;
        value_type* data;

   public:
        // CONSTRUCTORS and DESTRUCTOR
        sequence( int initialCapacity = 1 );
        sequence( const sequence& source );
        ~sequence( );

        // MODIFICATION MEMBER FUNCTIONS
        void start( );
        void advance( );
        void add_before(value_type entry);
        void add_after(value_type entry);
        void remove_current( );
        sequence& operator =(const sequence& source);
        void ensure_capacity(size_type minimum_capacity);
        void trim_to_size();

        // CONSTANT MEMBER FUNCTIONS
        size_type size( ) const { return this->used; }
        bool is_current( ) const { return (cursor < used && cursor >= 0); }
        value_type get_current( ) const;
        size_type get_capacity( ) const { return this->capacity; }
        std::string toString( ) const;
        bool operator ==(const sequence& s) const; 
        bool operator !=(const sequence& s) const; 
        sequence operator +(const sequence& source) const;

        // FUNCTIONS TO PROVIDE ITERATOR
        typedef value_type * seq_iterator;
        seq_iterator begin( ) const { return data; }
        seq_iterator end( )   const { return data+used; }
};

// NONMEMBER FUNCTION for the sequence class
std::ostream& operator<<(std::ostream& outs, const sequence& seq);

#endif

