// FILE: bag.h 
// TEMPLATE CLASS PROVIDED: bag<Item>
//     (a container template class for a collection of items)
//

#ifndef BAG_H 
#define BAG_H
#include <cstdlib>     // Provides NULL and size_t
#include "bintree.h"   // Provides binary_tree_node and related functions

template <class Item>
class bag {
    public:
        // TYPEDEFS
	typedef std::size_t size_type;
        typedef Item value_type;
        typedef binary_tree_node_iterator<Item> iterator;
        typedef const_binary_tree_node_iterator<Item> const_iterator;

        // CONSTRUCTORS and DESTRUCTOR
        bag( );
        bag(const bag& source);
        ~bag( );	

        // MODIFICATION functions
        size_type erase(const Item& target);
        bool erase_one(const Item& target);
        void insert(const Item& entry);
        bag<Item>& operator =(const bag& source);	
        bag<Item>& operator +=(const bag& addend);

        // CONSTANT functions
        size_type size( ) const;
        size_type count(const Item& target) const;
        void display( ) const;

        // FUNCTIONS TO PROVIDE ITERATORS
        iterator begin( ) { return iterator( root_ptr ); }
        const_iterator cbegin( ) const  { return const_iterator( root_ptr ); }
        iterator end( ) { return iterator( NULL ); }
        const_iterator cend( ) const { return const_iterator( NULL ); }

    private:
        binary_tree_node<Item>* root_ptr; // Root pointer of binary search tree
        void insert_all(binary_tree_node<Item>* addroot_ptr);
};

// NONMEMBER functions for the bag<Item> template class
template <class Item>
bag<Item> operator +(const bag<Item>& b1, const bag<Item>& b2);


// -------------------------------------------------------------------------
// -------------------------------------------------------------------------
// TEMPLATE CLASS IMPLEMENTED: bag<Item> 
// INVARIANT of the ADT:
//      1.  The Items in the bag are stored in a binary search tree
//      2.  The root pointer of the binary search tree is stored in
//          member variable root_ptr.  This may be NULL if the tree
//          is empty
// -------------------------------------------------------------------------
// -------------------------------------------------------------------------

#include <exception>
#include <cstdlib>
#include <iostream>


template <class Item>
bag<Item>::bag( ) {
    root_ptr = NULL;
}

// or in more modern style
// template <class Item>
// bag<Item>::bag( ) : root_ptr(NULL) {}

template <class Item>
bag<Item>::bag(const bag<Item>& source) {
// Library facilities used: bintree.h
    root_ptr = tree_copy(source.root_ptr);
}

template <class Item>
bag<Item>::~bag( ) {
// Header file used: bintree.h
    tree_clear(root_ptr);
}

template <class Item>
typename bag<Item>::size_type bag<Item>::erase(const Item& target) {
    return bst_remove_all(root_ptr, target);
}

template <class Item>
bool bag<Item>::erase_one(const Item& target) {
    return bst_remove(root_ptr, target);
}

template <class Item>
void bag<Item>::insert(const Item& entry) {
// Header file used: bintree.h
    binary_tree_node<Item>* cursor = root_ptr;

    if (cursor == NULL) {
        // Add the first node of the binary search tree:
        root_ptr = new binary_tree_node<Item>(entry);
    }

    else {
        // Move down the tree and add a new leaf:
        /* STUDENT WORK */
        if (entry < root_ptr->data()) {
            root_ptr = root_ptr->left();
            insert(entry);
            cursor->set_left(root_ptr);
        } else {
            root_ptr = root_ptr->right();
            insert(entry);
            cursor->set_right(root_ptr);
        }
        root_ptr = cursor;
    }
}

template <class Item>
bag<Item>& bag<Item>::operator +=(const bag<Item>& addend) {
    /* STUDENT WORK */
    if (addend.root_ptr == NULL) return *this;
    bag<Item> temp = addend;
    insert_all(temp.root_ptr);

    return *this;
}

template <class Item>
bag<Item>& bag<Item>::operator =(const bag<Item>& source) {
    // Header file used: bintree.h
    if (root_ptr == source.root_ptr)
        return *this;
    /* STUDENT WORK */
    tree_clear(root_ptr);
    insert_all(source.root_ptr);

    return *this;
}

template <class Item>
typename bag<Item>::size_type bag<Item>::size( ) const {
// Header file used: bintree.h
    return tree_size(root_ptr);
}

template <class Item>
typename bag<Item>::size_type bag<Item>::count(const Item& target) const {
    size_type answer = 0;
    binary_tree_node<Item> *cursor = root_ptr;

    if ( cursor == NULL ) return 0;
    /* STUDENT WORK */
    while (cursor != NULL) {
        if (target == cursor->data()) answer += 1;
        if (target < cursor->data()) cursor = cursor->left();
        else cursor = cursor->right();
    }

    return answer;
}

template <class Item>
void bag<Item>::display( ) const {
    print( root_ptr, 0 );
}

template <class Item>
void bag<Item>::insert_all(binary_tree_node<Item>* addroot_ptr) {
    // Precondition: addroot_ptr is the root pointer of a binary search tree that
    // is separate from the binary search tree of the bag that activated this
    // method.
    // Postcondition: All the items from the addroot_ptr's binary search tree
    // have been added to the binary search tree of the bag that activated this
    // method.
    if (addroot_ptr != NULL) {
        insert(addroot_ptr->data( ));
        insert_all(addroot_ptr->left( ));
        insert_all(addroot_ptr->right( ));
    }
}

template <class Item>
bag<Item> operator +(const bag<Item>& b1, const bag<Item>& b2) {
    bag<Item> answer = b1;
    /* STUDENT WORK */
    answer += b2;
    return answer;
}


/************************************************************************/
/************************************************************************/
template <class Item>
void bst_remove_max(binary_tree_node<Item>*& root_ptr, Item& removed) {
// Precondition: root_ptr is a root pointer of a non-empty binary 
// search tree.
// Postcondition: The largest item in the binary search tree has been
// removed, and root_ptr now points to the root of the new (smaller) 
// binary search tree. The reference parameter, removed, has been set 
// to a copy of the removed item.
    /***STUDENT WORK***
     ** This recursive function should be implemented by the student, as
     ** discussed on page 528 of the fourth edition of the textbook.
     ** The base case occurs when there is no right child of the
     ** root_ptr node. In this case, the root_ptr should be moved down
     ** to its left child and then the original root node must be
     ** deleted. There is also a recursive case, when the root does
     ** have a right child. In this case, a recursive call can be made
     ** using root_ptr->right( ) as the first parameter. The right() 
     ** function has the prototype:
     **    binary_tree_node<Item>*&
     ** The & symbol in the return type means that the return value is
     ** a reference to the actual right pointer in the node. Any changes
     ** made to this pointer in the recursive call will directly change
     ** the right pointer in the root_ptr's node.
     */
    // Case 1 -- base case
    if ( root_ptr->right() == NULL ) {
    /* STUDENT WORK */
        binary_tree_node<Item>* old_root_ptr = root_ptr;
        removed = root_ptr->data();
        root_ptr = root_ptr->left();
        delete old_root_ptr;
    } else {
    /* STUDENT WORK */
        //Case 2 -- there are larger items in the tree
        bst_remove_max(root_ptr->right(), removed);
    }
}

template <class Item>
bool bst_remove(binary_tree_node<Item>*& root_ptr, const Item& target) {
// Precondition: root_ptr is a root pointer of a binary search tree 
// or may be NULL for an empty tree).
// Postcondition: If target was in the tree, then one copy of target
// has been removed, and root_ptr now points to the root of the new 
// (smaller) binary search tree. In this case the function returns true.
// If target was not in the tree, then the tree is unchanged (and the
// function returns false).
    binary_tree_node<Item>* oldroot_ptr;
    
    //Case 1
    if (root_ptr == NULL) { 
        // Empty tree
        return false;
    }

    //Case 2
    if (target < root_ptr->data( )) {
        // Continue looking in the left subtree
        // Note: Any change made to root_ptr->left by this recursive
        // call will change the actual left pointer (because the return
        // value from the left() member function is a reference to the
        // actual left pointer.
    /* STUDENT WORK */
        return bst_remove(root_ptr->left(), target);
    }

    //Case 3
    if (target > root_ptr->data( )) {
        // Continue looking in the right subtree
        // Note: Any change made to root_ptr->right by this recursive
        // call will change the actual right pointer (because the return
        // value from the right() member function is a reference to the
        // actual right pointer.
    /* STUDENT WORK */
        return bst_remove(root_ptr->right(), target);
    }

    //Case 4a
    //We know that target == root_ptr->data()
    if (root_ptr->left( ) == NULL) {
       // Target was found and there is no left subtree, so we can
       // remove this node, making the right child be the new root.
    /* STUDENT WORK */
        oldroot_ptr = root_ptr;
        root_ptr = root_ptr->right();
        delete oldroot_ptr;
        return true;
    } 

    // Case 4b
    // If code reaches this point, then we must remove the target from
    // the current node. We'll actually replace this target with the
    // maximum item in our left subtree.
    // Note: Any change made to root_ptr->left by bst_remove_max
    // will change the actual left pointer (because the return
    // value from the left() member function is a reference to the
    // actual left pointer.
    /* STUDENT WORK */
    bst_remove_max(root_ptr->left(), root_ptr->data());
    return true;
}

template <class Item>
typename bag<Item>::size_type bst_remove_all
(binary_tree_node<Item>*& root_ptr, const Item& target) {
    // Precondition: root_ptr is a root pointer of a binary search tree 
    // or may be NULL for an empty tree).
    // Postcondition: All copies of target have been removed from the tree
    // and root_ptr now points to the root of the new (smaller) binary
    // search tree. The return value tells how many copies of the target 
    // were removed.
    /** STUDENT WORK
     ** Note: This implementation is similar to bst_remove, except that
     ** all occurrences of the target must be removed, and the return
     ** value is the number of copies that were removed. This will be
     ** tricky done recursively and most efficiently.  If you cannot
     ** get there recursively, there is a simple iterative solution.
     */

    binary_tree_node<Item>* oldroot_ptr;

    if (root_ptr == NULL) {
        // Empty tree
        /* STUDENT WORK */
        return 0;
    }

    if (target < root_ptr->data( )) {
        // Continue looking in the left subtree
        /* STUDENT WORK */
        return bst_remove_all(root_ptr->left(), target);
    }

    if (target > root_ptr->data( )) {
        // Continue looking in the right subtree
        /* STUDENT WORK */
        return bst_remove_all(root_ptr->right(), target);
    }
    
    if (root_ptr->left( ) == NULL) {
        // Target was found and there is no left subtree, so we can
        // remove this node, making the right child be the new root.
        /* STUDENT WORK */
        oldroot_ptr = root_ptr;
        root_ptr = root_ptr->right();
        delete oldroot_ptr;
        return 1 + bst_remove_all(root_ptr, target);
    }
    

    // If code reaches this point, then we must remove the target from
    // the current node. We'll actually replace this target with the
    // maximum item in our left subtree. We then continue
    // searching for more copies of the target to remove.
    // This continued search must start at the current root (since
    // the maximum element that we moved up from our left subtree
    // might also be a copy of the target).
    /* STUDENT WORK */
    bst_remove_max(root_ptr->left(), root_ptr->data());
    return 1 + bst_remove_all(root_ptr, target);
}



#endif
