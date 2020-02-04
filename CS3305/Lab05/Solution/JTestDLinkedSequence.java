import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runners.*;
import org.junit.*;
import java.util.Arrays;
import java.util.ListIterator;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JTestDLinkedSequence {

    //TEST_SIZE should be >= 10
    public static final int TEST_SIZE = 16;

    public boolean check_match( DLinkedSequence<Integer> testObject, 
                                Integer[] testArray                  ) {
        int index = 0;
        for ( int item: testObject ) { // use our iterator
            if ( item != testArray[index] ) return false;
            index++;
        }
        return true;
    }

    @Test
    public void test_01_Constructor() {
        System.out.printf("\ntesting constructor\n");
        DLinkedSequence<Integer> as = new DLinkedSequence<Integer>();
        assertEquals( 0, as.size() );
        assertFalse( as.isCurrent() );
        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_02_Copy_Constructor() {
        System.out.printf("\ntest copy constructor\n");
        DLinkedSequence<String> test1 = new DLinkedSequence<String>();
        DLinkedSequence<String> test2 = new DLinkedSequence<String>(test1);
        assertEquals( "hash codes should be the same",
                      test1.hashCode(), test2.hashCode() );
        System.out.printf("passed . . .\n");
    }
    @Test
    public void test_03_Equals() {
        System.out.printf("\ntest equals method\n");
        DLinkedSequence<Double> test1, test2, test3;
        DLinkedSequence<Long> test4;
        DLinkedSequence<String> test5 = new DLinkedSequence<String>();
        test1 = new DLinkedSequence<Double>();
        test2 = new DLinkedSequence<Double>();
        test3 = new DLinkedSequence<Double>();
        test4 = new DLinkedSequence<Long>();
        assertNotSame("test1 and test2 should be different objects",
                      test1, test2);
        assertEquals( "both objects should be equal",
                      test1, test2 );
        assertEquals( "both objects should be equal",
                      test2, test1 );
        assertEquals( "both objects should be equal",
                      test1, test3 );
        assertEquals( "both objects should be equal",
                      test2, test3 );
        assertEquals( "both objects should be equal",
                      test1, test4 );
        assertEquals( "both objects should be equal",
                      test1, test5 );

        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_04_addAfter_equals_copy_constructor() {
        System.out.printf("\ntesting addAfter\n");
        int test_elements = TEST_SIZE;
        int result;
        DLinkedSequence<Integer> test = new DLinkedSequence<Integer>();
        Integer [] items = new Integer[ test_elements + 1 ];
        for ( int item = 0; item < test_elements; ++item ) {
            items[ item ] = item + 1;
            test.addAfter( item + 1 );
        }
        assertEquals( test_elements, test.size() );
        assertTrue( test.isCurrent() );
        result = test.getCurrent();
        assertEquals( test_elements, result );
        assertTrue( check_match( test, items ) );

        test.advance(); // make cursor undefined
        test.addAfter( test_elements + 1 );  // should be added at end of sequence
        result = test.getCurrent();
        assertEquals( test_elements+1, result );
        items[ test_elements ] = test_elements + 1;
        assertTrue( check_match( test, items ) );

        DLinkedSequence<Integer> test1 = new DLinkedSequence<Integer>(test);
        assertEquals( "hash codes should be the same",
                      test.hashCode(), test1.hashCode() );
        assertEquals( "sequences should be equal",
                      test, test1 );
        assertEquals( "sequences should be equal",
                      test1, test );
        assertNotSame("test and test1 should be different objects",
                      test, test1);
        assertFalse("object should not be equals(null)",
                     test.equals(null));
        assertTrue("object should be equals with itself",
                    test.equals(test));

        //System.out.printf("the test sequence\n%s\n\n", test);
        //System.out.printf("the test1 sequence\n%s\n\n", test1);

        test.begin();
        //System.out.printf("test.size(): %d\n", test.size());
        int index;
        for (index=1; index < test.size()/2; ++index) {
            test.advance();
            //System.out.printf("the test sequence\n%s\n\n", test);
        }
        test.addAfter(77);
        //System.out.printf("the test sequence\n%s\n\n", test);
        test.advance();
        result = test.getCurrent();
        assertEquals( index+1, result );

        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_05_addBefore_advance() {
        System.out.printf("\ntesting addBefore\n");
        int test_elements = TEST_SIZE;
        int result;
        DLinkedSequence<Integer> test = new DLinkedSequence<Integer>();
        Integer [] items = new Integer[ test_elements + 1 ];
        for ( int item = TEST_SIZE; item >= 0; --item ) {
            items[ item ] = item;
            test.addBefore( item );
        }
        //System.out.printf("the test sequence\n%s\n\n", test);
        assertEquals( test_elements+1, test.size() );
        assertTrue( test.isCurrent() );
        result = test.getCurrent();
        assertEquals( 0, result );
        assertTrue( check_match( test, items ) );

        test.advance();
        assertTrue( test.isCurrent() );
        result = test.getCurrent();
        assertEquals( 1, result );
        //System.out.printf("the test sequence\n%s\n\n", test);
        test.begin();
        int index = 0;
        while ( test.isCurrent() ) {
            result = test.getCurrent();
            assertEquals(index, result);
            int temp = items[index];
            assertEquals(result, temp);
            test.advance(); ++index;
        }

        test.begin();
        //System.out.printf("test.size(): %d\n", test.size());
        for (index=1; index < test.size()/2; ++index) {
            test.advance();
            //System.out.printf("the test sequence\n%s\n\n", test);
        }
        test.addBefore(77);
        //System.out.printf("the test sequence\n%s\n\n", test);
        test.advance();
        result = test.getCurrent();
        assertEquals( index-1, result );

        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_06_removeCurrent() {
        System.out.printf("\ntesting removeCurrent\n");
        DLinkedSequence<Integer> test1 = new DLinkedSequence<Integer>();
        Integer [] items = new Integer[TEST_SIZE];

        for (int i = 0; i < TEST_SIZE; ++i) {
            items[i] = i;
            test1.addAfter(i);
        }
        Integer [] items1 = Arrays.copyOfRange(items, 0, items.length);
        //System.out.printf("%s\n", Arrays.toString(items));
        //System.out.printf("%s\n", test1);
        DLinkedSequence<Integer>test2 = new DLinkedSequence<Integer>(test1);
        test2.begin();
        test2.removeCurrent();

        items = Arrays.copyOfRange( items, 1, items.length );
        //System.out.printf("%s\n", Arrays.toString(items));
        //System.out.printf("%s\n", test1);
        assertTrue( check_match( test2, items ) );
        test2.advance(); test2.advance();
        test2.removeCurrent();
        int result = test2.getCurrent();
        assertEquals ( 4, result );
        
        //System.out.printf("%s\n", test1);
        int index = 0;
        test1.begin();
        while ( index < test1.size()-1 ) {
            //System.out.printf("%s\n", test1);
            test1.advance();
            ++index;
        }
        //System.out.printf("%s\n", test1);
        test1.removeCurrent();
        //System.out.printf("test1 after removeCurrent: %s\n", test1);
        items1 = Arrays.copyOfRange( items1, 0, items.length);
        //System.out.printf("%s\n", test1);
        //System.out.printf("%s\n", Arrays.toString(items1));
        assertTrue( check_match( test1, items1 ) );

        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_07_forwardIterator() {
        System.out.printf("\ntesting forwardIterator\n");
        DLinkedSequence<Integer> test1 = new DLinkedSequence<Integer>();
        Integer [] items = new Integer[TEST_SIZE];

        for (int i = 0; i < TEST_SIZE; ++i) {
            items[i] = i;
            test1.addAfter(i);
        }

        int i = 0;
        for ( Integer element : test1 ) {
            assertEquals( items[i], element );
            i++;
        }
        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_08_reverseIterator() {
        System.out.printf("\ntesting reverseIterator\n");

        DLinkedSequence<Integer> test1 = new DLinkedSequence<Integer>();
        Integer [] items = new Integer[TEST_SIZE];

        for (int i = 0; i < TEST_SIZE; ++i) {
            items[TEST_SIZE-i-1] = i;
            test1.addBefore(i);
        }
        //System.out.printf("%s\n", Arrays.toString(items));

        //testing forward again
        int i = 0;
        for ( Integer element : test1 ) {
            assertEquals( items[i], element );
            i++;
        }

        ListIterator<Integer> li = test1.listIterator( test1.size() );
        i = TEST_SIZE-1;
        while ( li.hasPrevious() ) {
            Integer element = li.previous();
            //System.out.printf("%d %d ", element, items[i]);
            assertEquals( items[i], element );
            --i;
        }
        //System.out.printf("\n");
        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_09_advance_and_retreat() {
        System.out.printf("\ntesting advance and retreat\n");

        DLinkedSequence<Integer> test1 = new DLinkedSequence<Integer>();
        Integer [] items = new Integer[TEST_SIZE];

        for (int i = 0; i < TEST_SIZE; ++i) {
            items[TEST_SIZE-i-1] = i;
            test1.addBefore(i);
        }

        int i;
        test1.begin();
        i = 0;
        while ( test1.isCurrent() ) {
            assertEquals( items[i], test1.getCurrent() );
            ++i;
            test1.advance();
        }

        test1.end();
        i = TEST_SIZE - 1;
        while ( test1.isCurrent() ) {
            assertEquals( items[i], test1.getCurrent() );
            --i;
            test1.retreat();
        }
        System.out.printf("passed . . .\n");
    }

}

