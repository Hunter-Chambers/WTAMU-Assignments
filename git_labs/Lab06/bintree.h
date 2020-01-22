// FILE: bintree.h 
// PROVIDES: A template class for a node in a binary tree and functions for 
// manipulating binary trees. The template parameter is the type of data in
// each node.
// 
#ifndef BINTREE_H
#define BINTREE_H
#include <cstdlib>  // Provides NULL and size_t


template <class Item>
class binary_tree_node {
    public:
    // TYPEDEF
    typedef Item value_type;
    // CONSTRUCTOR
    binary_tree_node( const Item& init_data = Item( ),
                      binary_tree_node* init_left = NULL,
                      binary_tree_node* init_right = NULL  ) { 
        data_field  = init_data; 
        left_field  = init_left; 
        right_field = init_right;
    }
    // MODIFICATION MEMBER FUNCTIONS
    Item& data( ) { return data_field; }
    binary_tree_node*& left( ) { return left_field; }
    binary_tree_node*& right( ) { return right_field; }
    void set_data(const Item& new_data) { data_field = new_data; }
    void set_left(binary_tree_node* new_left) { left_field = new_left; }
    void set_right(binary_tree_node* new_right) { right_field = new_right; }

    // CONST MEMBER FUNCTIONS
    const Item& data( ) const { return data_field; }
    const binary_tree_node* left( ) const { return left_field; }
    const binary_tree_node* right( ) const { return right_field; }
    bool is_leaf( ) const 
        { return (left_field == NULL) && (right_field == NULL); }

    private:
        Item data_field;
        binary_tree_node* left_field;
        binary_tree_node* right_field;
};

// NON-MEMBER FUNCTIONS for the binary_tree_node<Item>:
template <class Process, class BTNode>
void inorder(Process f, BTNode* node_ptr); 

template <class Process, class BTNode>
void preorder(Process f, BTNode* node_ptr);

template <class Process, class BTNode>
void postorder(Process f, BTNode* node_ptr); 

template <class Item, class SizeType>
void print(binary_tree_node<Item>* node_ptr, SizeType depth);

template <class Item>
void tree_clear(binary_tree_node<Item>*& root_ptr);

template <class Item>
binary_tree_node<Item>* tree_copy(const binary_tree_node<Item>* root_ptr);

template <class Item>
std::size_t tree_size(const binary_tree_node<Item>* node_ptr);


#include <cstdlib>    // Provides NULL, std::size_t
#include <iomanip>    // Provides std::setw
#include <exception>  // Provides exceptions
#include <iostream>   // Provides std::cout

template <class Process, class BTNode>
void inorder(Process f, BTNode* node_ptr) {
// Library facilities used: cstdlib
    if (node_ptr != NULL) {
        inorder(f, node_ptr->left( ));
        f( node_ptr->data( ) );
        inorder(f, node_ptr->right( ));
    }
}

template <class Process, class BTNode>
void postorder(Process f, BTNode* node_ptr) {
// Library facilities used: cstdlib
    if (node_ptr != NULL) {
        postorder(f, node_ptr->left( ));
        postorder(f, node_ptr->right( ));
        f(node_ptr->data( ));
    }
}

template <class Process, class BTNode>
void preorder(Process f, BTNode* node_ptr) {
// Library facilities used: cstdlib
    if (node_ptr != NULL) {
        f( node_ptr->data( ) );
        preorder(f, node_ptr->left( ));
        preorder(f, node_ptr->right( ));
    }
}

template <class Item, class SizeType>
void print(binary_tree_node<Item>* node_ptr, SizeType depth) {
// Library facilities used: iomanip, iostream, stdlib
// This is essentially a reverse inorder traversal
    if (node_ptr != NULL) {
        print(node_ptr->right( ), depth+1);
        std::cout << std::setw(4*depth) << ""; // Indent 4*depth spaces.
        std::cout << node_ptr->data( ) << std::endl;
        print(node_ptr->left( ),  depth+1);
    }
}    
    
template <class Item>
void tree_clear(binary_tree_node<Item>*& root_ptr) {
// Library facilities used: cstdlib
// This is essentially a post-order traversal
    binary_tree_node<Item>* child;
    if (root_ptr != NULL) {
        child = root_ptr->left( );
        tree_clear( child );
        child = root_ptr->right( );
        tree_clear( child );
        delete root_ptr;
        root_ptr = NULL;
    }
}

template <class Item>
binary_tree_node<Item>* tree_copy(const binary_tree_node<Item>* root_ptr) {
// Library facilities used: cstdlib {
// This is essentially a post-order traversal of the tree to copy
    binary_tree_node<Item> *l_ptr;
    binary_tree_node<Item> *r_ptr;

    if (root_ptr == NULL)
        return NULL;
    else {
        l_ptr = tree_copy( root_ptr->left( ) );
        r_ptr = tree_copy( root_ptr->right( ) );
        return
            new binary_tree_node<Item>( root_ptr->data( ), 
                                        l_ptr, r_ptr);
    }
}

template <class Item>
size_t tree_size(const binary_tree_node<Item>* node_ptr) {
// Library facilities used: cstdlib
    if (node_ptr == NULL)
        return 0;
    else 
        return 1 + 
            tree_size(node_ptr->left( )) + 
            tree_size(node_ptr->right( ));
}    

// ITERATORS to step through the nodes of a binary_tree.
// A binary_tree_node_iterator can change the underlying binary_tree 
// through the * operator, so it may not be used with a const node. The
// binary_tree_node_const_iterator cannot change the underlying tree
// through the * operator, so it may be used with a const node.

#include <vector>
template <class Item>
class binary_tree_node_iterator
    : public std::iterator<std::bidirectional_iterator_tag, Item> {

    private:
        binary_tree_node<Item> * cursor;
        std::vector<binary_tree_node<Item> *> iter_container;
        std::size_t index;

        void build_iter( binary_tree_node<Item> *root ) {
            if ( root != NULL ) {
                if ( root->left() != NULL ) build_iter( root->left() );
                iter_container.push_back( root );
                if ( root->right() != NULL ) build_iter( root->right() );
            }
        }

    public:
        binary_tree_node_iterator(binary_tree_node<Item>* initial = NULL) { 
            build_iter( initial );
            iter_container.push_back( NULL );
            index = 0;
            cursor = iter_container.front();
        }

        Item& operator *( ) const { 
            return cursor->data( ); 
        }

        binary_tree_node_iterator& operator ++( ) /* Prefix ++ */{ 
            cursor = iter_container[ ++index ];
            return *this;
        }
        

        binary_tree_node_iterator& operator --( ) /* Prefix --*/ {
            cursor = iter_container[ --index ];
            return *this;
        }

        binary_tree_node_iterator operator ++(int) /* Postfix ++*/ {
            throw std::runtime_error("Postfix operator ++ not implemented");
            return *this;
        }

        binary_tree_node_iterator operator --(int) /* Postfix -- */ {
            throw std::runtime_error("Postfix operator -- not implemented");
            return *this;
        }

        bool operator ==(const binary_tree_node_iterator other) const { 
            return cursor == other.cursor; 
        }

        bool operator !=(const binary_tree_node_iterator other) const { 
            return cursor != other.cursor; 
        }
};

template <class Item>
class const_binary_tree_node_iterator
    : public std::iterator<std::bidirectional_iterator_tag, const Item> {

    private:
        const binary_tree_node<Item>* cursor;
        std::vector<binary_tree_node<Item> *> iter_container;
        std::size_t index;

        void build_iter( binary_tree_node<Item> *root ) {
            if ( root != NULL ) {
                if ( root->left() != NULL ) build_iter( root->left() );
                iter_container.push_back( root );
                if ( root->right() != NULL ) build_iter( root->right() );
            }
        }

    public:
        const_binary_tree_node_iterator(const binary_tree_node<Item>* initial = NULL) { 
            build_iter( initial );
            iter_container.push_back( NULL );
            index = 0;
            cursor = iter_container.front();
        }

        const Item& operator *( ) const { 
            return cursor->data( ); 
        }

        const_binary_tree_node_iterator& operator ++( ) /* Prefix ++ */ {
            cursor = iter_container[ ++index ];
            return *this;
        }

        const_binary_tree_node_iterator& operator --( ) /* Prefix -- */ {
            cursor = iter_container[ --index ];
            return *this;
        }

        const_binary_tree_node_iterator operator ++(int) /* Postfix ++ */ {
            throw std::runtime_error("Postfix operator ++ not implemented");
            return *this;
        }

        const_binary_tree_node_iterator operator --(int) /* Postfix -- */ {
            throw std::runtime_error("Postfix operator -- not implemented");
            return *this;
        }

        bool operator ==(const const_binary_tree_node_iterator other) const { 
            return cursor == other.cursor; 
        }

        bool operator !=(const const_binary_tree_node_iterator other) const { 
            return cursor != other.cursor; 
        }
};

#endif
