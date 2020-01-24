#include "sequence.h"
#include <gtest/gtest.h>
#include <cmath> //provides pow and log

using namespace std;

    const int TEST_SIZE = 13;
    typedef double test_type;

    bool check_match( sequence<test_type>& test_object, test_type test_array[] ) {
        int index = 0;

        for (test_type *cursor = test_object.begin();
                  cursor != test_object.end(); 
                  ++cursor) {
            if (*cursor != test_array[index]) return false;
            ++index;
        }
        //cout << test_object << endl;
        /*test_object.start();
        cout << "before loop " << test_object << endl;
        while ( test_object.is_current() ) {
            if ( test_object.get_current() != test_array[index] ) return false;
            //cout << "in loop before advance " << test_object << endl;
            test_object.advance();
            //cout << "in loop after advance " << test_object << endl;
            //cout << " test_object.is_current() " << (test_object.is_current()? "true":"false") << endl;
            ++index;
        }
        */
        return true;
    }

    TEST(SequenceTest, Constructor_Default) {
        sequence<test_type> test;
        EXPECT_EQ( test.size(), 0);
        EXPECT_EQ( test.get_capacity(), 1);
        EXPECT_FALSE( test.is_current() );

        EXPECT_ANY_THROW( sequence<test_type> testa(-1) );
    }

    TEST(SequenceTest, Constructor_Parameterized) {
        sequence<test_type> test(1000);
        EXPECT_EQ( test.size(), 0);
        EXPECT_EQ( test.get_capacity(), 1000);
        EXPECT_FALSE( test.is_current() );
    }

    TEST(SequenceTest, ensure_capacity_and_trim_to_size) {
        sequence<test_type> test(1000);
        EXPECT_EQ( test.size(), 0);
        EXPECT_EQ( test.get_capacity(), 1000);
        EXPECT_EQ( test.is_current(), false );
        test.trim_to_size();
        EXPECT_EQ( test.size(), 0);
        EXPECT_EQ( test.get_capacity(), 1);
        EXPECT_FALSE( test.is_current() );
        test.ensure_capacity(10000);
        EXPECT_EQ( test.size(), 0);
        EXPECT_EQ( test.get_capacity(), 10000);
        EXPECT_FALSE( test.is_current() );
        test.trim_to_size();
        EXPECT_EQ( test.size(), 0);
        EXPECT_EQ( test.get_capacity(), 1);
        EXPECT_FALSE( test.is_current() );
    }

    TEST(SequenceTest, add_after_and_trim_to_size) {
        int test_elements = TEST_SIZE;
        test_type result;
        sequence<test_type> test;
        test_type * items = new test_type[ test_elements + 1 ];
        for ( int item = 0; item < test_elements; ++item ) {
            items[ item ] = item + 1;
            test.add_after( item + 1 );
        }
        EXPECT_EQ( test.size(), test_elements );
        EXPECT_TRUE( test.is_current() );
        result = test.get_current();
        EXPECT_EQ( result, test_elements );
        EXPECT_TRUE( check_match( test, items ) );

        test.advance(); //make cursor undefined
        test.add_after( test_elements + 1 );
        result = test.get_current();
        EXPECT_EQ( result, test_elements + 1 );
        items[ test_elements ] = test_elements + 1;
        EXPECT_TRUE( check_match( test, items ) );

        long expected_capacity = pow( 2, ceil( log(test_elements+1)/log(2) ) );
        EXPECT_EQ( test.get_capacity(), expected_capacity );

        test.trim_to_size();
        EXPECT_EQ( test.get_capacity(), test_elements+1 );
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
            test.add_after( item );
        }
        EXPECT_EQ( test.size(), test_elements );
        EXPECT_EQ( test.is_current(), true );
        result = test.get_current();
        items[ 0 ] = 0;
        test.start();
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

        delete [] items; items = NULL;
    }


    int main(int argc, char **argv) {
        ::testing::InitGoogleTest( &argc, argv );
        return RUN_ALL_TESTS();
    }

