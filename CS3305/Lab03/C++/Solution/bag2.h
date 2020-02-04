#ifndef BAG2_H
#define BAG2_H

#include <cstdlib>  // Provides size_t
#include <string>   // Provides string
#include <iostream> // Provides ostream

/** *************************************************************************
 * @file        bag2.h
 * @author      H. Paul Haiduk
 * @date        January, 2020
 * @version     0.9
 *
 * @brief       This class models a bag whose capacity can grow and 
 *              shrink as needed.
 *
 * @section     DESCRIPTION
 * A bag is a collection of value_type items for which no specific
 * ordering relationship is imposed.  Value semantics are supported 
 * such that assignments, the copy constructor, and comparison with 
 * == and != are all provided.
 *
 **************************************************************************/

class bag { 
    public:
        // TYPEDEFS
        /**
         * @typedef     value_type  The data type of items in the bag
         */
        // It may be any of the C++ built-in types (int, char, etc.),
        // or a class with a default constructor, an assignment
        // operator, and operators to test for equality (x == y) 
        // and non-equality (x != y).
        typedef double value_type;

        /** 
         * @typedef     size_type should be an unsigned integral type 
         */
         // It is the data type of any variable that keeps
         // track of how many items are in a bag or the bag's 
         // capacity.
        typedef std::size_t size_type;

        // CONSTRUCTORS
        /**
         * @brief       both default and parameterized constructor
         * @param       initialCapacity may be omitted or, if present, >= 1
         * @pre         initialCapacity must be >= 1
         * @post        The bag has been initialized as an empty bag.
         * @throws      May throw std::bad_alloc if not enough memory available to
         *              allocate space for initialCapacity; throws invalid
         *              argument if initialCapacity < 1
         */

        bag(size_type initialCapacity = 1);

        /**
         * @brief       copy constructor
         * @param       other reference to another bag of the same type
         * @post        a newly created bag that is equal in value to other
         * @throws      May throw std::bad_alloc if not enough memory available
         */
        bag(const bag& other);

        // DESTRUCTOR
        /**
         * @brief       deallocates dynamically allocated memory
         * @post        all dynamically allocated memory has be
         *              returned to the memory manager
         */
        ~bag();

        // MODIFICATION MEMBER FUNCTIONS
        /**
         * @brief       can be used to increase bag's capacity
         * @param       newCapacity 
         * @pre         newCapacity must be >= 1
         * @post        bag's capacity is at least newCapacity
         */
        void ensureCapacity(const size_type newCapacity);

        /**
         * @brief       removes all occurrences of target from the bag
         * @param       target the item to be removed from the bag
         * @post        all copies of target have been removed from the bag
         * @returns     a count of all copies of target removed -- may be zero
         *
         */
        size_type erase(const value_type& target);
        
        /**
         * @brief       attempts to remove first occurrence of target from the bag
         * @param       target the item to be removed from the bag
         * @post        first occurrence of target removed from bag if it exists in bag
         * @returns     true if target removed; false otherwise
         */
        bool erase_one(const value_type& target);

        /**
         * @brief       places entry into the bag
         * @param       entry a value_type item to be placed in the bag
         * @post        entry placed in the bag
         */
        void insert(const value_type& entry);

        /**
         * @brief       reduces capacity of bag to match number of items
         *              in the bag
         * @post        bag's capacity equals number of items in bag; however,
         *              capacity can never be reduced to be < 1
         * @throws      may throw std::bad_alloc if dynamic memory exhausted
         *              during processing
         */
        void trimToSize( );

        /*
         * @brief       assignment operator -- permits multiple assignment of
         *              for a = b = c
         * @pre         source is not this bag
         * @post        this bag's values are destroyed and replaced with
         *              values from source -- dynamic memory is managed
         * @throws      may throw std::bad_alloc if dynamic memory exhausted
         *              during processing
         */
        bag& operator =(const bag& source);

        /*
         * @brief       all items in addend are added to this bag
         * @pre         enough dynamic memory exists to potentially increase bag's
         *              capacity
         * @post        each item in addend has been added to this bag and the
         *              bag's capacity is at least large enough to accomodate 
         *              the additional items
         * @throws      may throw std::bad_alloc if dynamic memory exhausted
         */
        void operator +=(const bag& addend);

        // CONSTANT MEMBER FUNCTIONS
        /**
         * @brief       returns the bag's current capacity
         * @returns     maximum current capacity of the bag
         */
        size_type getCapacity( ) const;

        /**
         * @brief       determines whether this bag is equal in number of elements
         *              and pair-wise all items are the same in this bag and in
         *              comparand
         * @param       comparand another bag
         * @returns     true if this bag equal to comparand; false otherwise
         */
        bool operator ==(const bag& comparand) const;

        /**
         * @brief       accessor returning an unique hashCode
         *              based on all attributes of this bag -- used to
         *              strengthen test for ==
         * @return      a size_t value representing the hash code
         *              for this bag
         */
        size_t hashCode( ) const;



        /**
         * @brief       determines whether this bag is not equal to comparand
         * @param       comparand another bag
         * @returns     true is this bag not equal to comparand; false otherwise
         */
        bool operator !=(const bag& comparand) const;

        /**
         * @brief       returns a count of number of times target exists in this
         *              bag
         * @returns     returns a count of number of times target exists in 
         *              this bag -- may be 0
         */
        size_type occurrences(const value_type& target) const;

        /**
         * @brief       returns count of number of items in this bag
         * @returns     returns count of number of tiems in this bag -- may be 0
         */
        size_type size( ) const { return this->used; }

        /**
         * @brief       returns a string so that one may realize a
         *              human readable representation of this bag
         * @returns     std::string
         */
        std::string toString( ) const;

    private:
        value_type* data;   // The array to store items
        size_type used;     // How much of array is used
        size_type capacity; // How big is array holding data
};

    // NONMEMBER FUNCTIONS for the bag class
    /**
     * @brief   creates a new bag that is the union of its two parameters
     * @returns a new bag that is union of its parameters b1 and b2
     * @post    the new bag contains all the elements of b1 followed by
     *          all elements of b2; the new bag's capacity is exactly what
     *          is required to hold all the items of b1 and b2
     */
    bag operator +(const bag& b1, const bag& b2);

    /**
     * @brief   allows one to use cout << somebag
     */
    std::ostream& operator <<(std::ostream& outs, const bag& b1);
#endif
