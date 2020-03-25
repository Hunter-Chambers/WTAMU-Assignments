#include "sequence.h"
#include <gtest/gtest.h>

using namespace std;

    const int TEST_SIZE = 500;  //may want to make this small if debug prints embedded
    typedef double test_type;

    bool check_match( sequence<test_type>& test_object, test_type test_array[] ) {
        int index = 0;
        //NOTE:  must use the iterator here so as to avoid altering state
        //       of the test_object by using first and advance
        // NOTE: Iterator usage requiring programmer know details
        /*
        for (sequence<test_type>::iterator 
                fi = test_object.begin(); fi != test_object.end(); ++fi, ++index ) {
                         if ( *fi != test_array[index] ) return false;
        }
        */
        // NOTE: Use of iterator in its most elegant form
        for (const auto element : test_object) {
            if ( element != test_array[index] ) return false;
            ++index;
        }

        return true;
    }

    TEST(SequenceTest, Constructor_Default) {
        sequence<test_type> test;
        EXPECT_EQ( test.size(), 0);
        EXPECT_FALSE( test.is_current() );
    }

    TEST(SequenceTest, add_after) {
        int test_elements = TEST_SIZE;
        test_type result;
        sequence<test_type> test;
        test_type * items = new test_type[ test_elements + 1 ];
        for ( int item = 0; item < test_elements; ++item ) {
            items[ item ] = (item + 1);
            test.add_after( (test_type) (item + 1) );
        }
        EXPECT_EQ( test.size(), test_elements );
        EXPECT_TRUE( test.is_current() );
        result = test.current();
        EXPECT_EQ( result, test_elements );
        EXPECT_TRUE( check_match( test, items ) );
        test.advance(); //make cursor undefined
        test.add_after( test_elements + 1 );
        result = test.current();
        EXPECT_EQ( result, test_elements + 1 );
        items[ test_elements ] = (test_type) (test_elements + 1);
        EXPECT_TRUE( check_match( test, items ) );

        delete [] items; items = NULL;
    }

    TEST(SequenceTest, Copy_Constructor_assignment_and_operator_equal_equal) {
        int test_elements = TEST_SIZE;
        test_type result;
        sequence<test_type> test;
        test_type * items = new test_type[ test_elements + 1 ];
        for ( int item = 1; item <= test_elements; ++item ) {
            items[ item ] = item;
            test.add_after( (test_type) item );
        }
        EXPECT_EQ( test.size(), test_elements );
        EXPECT_EQ( test.is_current(), true );
        result = test.current();
        items[ 0 ] = 0;
        test.first();
        test.add_before(0);
        EXPECT_EQ( result, test_elements );
        EXPECT_TRUE( check_match( test, items ) );

        sequence<test_type> testa(test); //using copy constructor
        EXPECT_TRUE( check_match( testa, items ) );
        EXPECT_TRUE( testa.is_current() );
        EXPECT_EQ( test, testa ); //checking == operator

        sequence<test_type> testb = testa;
        EXPECT_TRUE( check_match( testb, items ) ); // checking = operator
        EXPECT_EQ( testa, testb ); // checking == operator
        EXPECT_NE( &testa, &testb ); //checking that these are different objects

        testa.advance();
        EXPECT_NE( testa, testb );
        //cout << "operator != OK\n";

        delete [] items; items = NULL;
    }

    TEST(SequenceTest, CopyConstructor) {
        test_type* array1 = new test_type[0];
        sequence<test_type> test1;
        sequence<test_type> test2(test1);
        EXPECT_TRUE(check_match(test1, array1));
        EXPECT_TRUE(check_match(test2, array1));
        EXPECT_EQ(test1, test2);
        test_type* array2 = new test_type[TEST_SIZE - 400];
        sequence<test_type> test3;
        for (int i = 1; i < (TEST_SIZE - 400); i++) {
            array2[i] = i;
            test3.add_after((test_type) i);
        }
        array2[0] = 0;
        test3.first();
        test3.add_before(0);
        test3.retreat();
        sequence<test_type> test4(test3);
        EXPECT_TRUE(check_match(test3, array2));
        EXPECT_TRUE(check_match(test4, array2));
        EXPECT_EQ(test3, test4);
        test_type* array3 = new test_type[TEST_SIZE];
        sequence<test_type> test5;
        for (int i = 1; i < TEST_SIZE; i++) {
            array3[i] = i;
            test5.add_after((test_type) i);
        }
        array3[0] = 0;
        test5.first();
        test5.add_before(0);
        sequence<test_type> test6(test5);
        EXPECT_TRUE(check_match(test5, array3));
        EXPECT_TRUE(check_match(test6, array3));
        EXPECT_EQ(test5, test6);
        delete [] array1;
        delete [] array2;
        delete [] array3;
    }


    int main(int argc, char **argv) {
        ::testing::InitGoogleTest( &argc, argv );
        return RUN_ALL_TESTS();
    }

