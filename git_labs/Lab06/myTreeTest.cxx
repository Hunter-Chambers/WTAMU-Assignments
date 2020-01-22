#include <iostream>

using namespace std;

#include "bintree.h"
void myPrint( double num ) {
    cout << num << " ";
}

int main() {

    binary_tree_node<double>* head = new binary_tree_node<double>(77);
    head->data() = 11;
    head->left() = new binary_tree_node<double>(7);
    head->left()->left() = new binary_tree_node<double>(5);
    head->left()->right() = new binary_tree_node<double>(9);
    head->right() = new binary_tree_node<double>(15);
    head->right()->right() = new binary_tree_node<double>(17);
    head->right()->left() = new binary_tree_node<double>(13);

    cout << "Size of tree is " << tree_size( head ) << endl;

    print( head, 0 );

    preorder( myPrint, head ); cout << endl;
    inorder( myPrint, head ); cout << endl;
    postorder( myPrint, head ); cout << endl;

    return 0;
}
