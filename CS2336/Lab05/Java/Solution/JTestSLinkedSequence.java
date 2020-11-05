import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runners.*;
import org.junit.*;
import java.util.Arrays;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JTestSLinkedSequence {

    //TEST_SIZE should be >= 10
    public static final int TEST_SIZE = 50;

    public boolean check_match( SLinkedSequence<Integer> testObject, 
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
        SLinkedSequence<Integer> as = new SLinkedSequence<Integer>();
        assertEquals( 0, as.size() );
        assertFalse( as.isCurrent() );
        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_02_Copy_Constructor_Simple() {
        System.out.printf("\ntest copy constructor simple\n");
        SLinkedSequence<String> test1 = new SLinkedSequence<String>();
        SLinkedSequence<String> test2 = new SLinkedSequence<String>(test1);
        assertEquals( "hash codes should be the same",
                      test1.hashCode(), test2.hashCode() );
        //NOTE:  This a very imcomplete test in that sequences containing
        //       containing a large number of elements not copied
        System.out.printf("passed . . .\n");
    }
    @Test
    public void test_03_Equals_Simple() {
        System.out.printf("\ntest equals method simple\n");
        SLinkedSequence<Double> test1, test2, test3;
        SLinkedSequence<Long> test4;
        SLinkedSequence<String> test5 = new SLinkedSequence<String>();
        test1 = new SLinkedSequence<Double>();
        test2 = new SLinkedSequence<Double>();
        test3 = new SLinkedSequence<Double>();
        test4 = new SLinkedSequence<Long>();
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
        //NOTE:  This a very imcomplete test in that sequences containing
        //       containing a large number of elements not copied

        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_04_addAfter_equals_copy_constructor() {
        System.out.printf("\ntesting addAfter\n");
        int test_elements = TEST_SIZE;
        int result;
        SLinkedSequence<Integer> test = new SLinkedSequence<Integer>();
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

        SLinkedSequence<Integer> test1 = new SLinkedSequence<Integer>(test);
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

        test.start();
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
    public void test_05_addBefore() {
        System.out.printf("\ntesting addBefore\n");
        int test_elements = TEST_SIZE;
        int result;
        SLinkedSequence<Integer> test = new SLinkedSequence<Integer>();
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
        test.start();
        int index = 0;
        while ( test.isCurrent() ) {
            result = test.getCurrent();
            assertEquals(index, result);
            int temp = items[index];
            assertEquals(result, temp);
            test.advance(); ++index;
        }

        test.start();
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
        SLinkedSequence<Integer> test1 = new SLinkedSequence<Integer>();
        Integer [] items = new Integer[TEST_SIZE];

        for (int i = 0; i < TEST_SIZE; ++i) {
            items[i] = i;
            test1.addAfter(i);
        }
        Integer [] items1 = Arrays.copyOfRange(items, 0, items.length);
        //System.out.printf("%s\n", Arrays.toString(items));
        //System.out.printf("%s\n", test1);
        SLinkedSequence<Integer>test2 = new SLinkedSequence<Integer>(test1);
        test2.start();
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
        test1.start();
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
    public void test_07_copyConstructor() {
        System.out.printf("\ntesting the copy constructor\n");

        SLinkedSequence<Integer> test1 = new SLinkedSequence<Integer>();
        SLinkedSequence<Integer> test2 = new SLinkedSequence<Integer>(test1);
        assertEquals("Sequences should be equal", test1, test2);
        assertNotSame("Sequences should be separate objects", test1, test2);

        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_08_equals() {
        System.out.printf("\ntesting the equals method\n");

        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_09_addAfter() {
        System.out.printf("\ntesting addAfter\n");

        SLinkedSequence<Integer> test1 = new SLinkedSequence<Integer>();
        Integer[] items = new Integer[TEST_SIZE];
        for (int i = 0; i < TEST_SIZE; ++i) {
            items[i] = i;
            test1.addAfter(i);
        }
        //System.out.printf("test1 looks like: %s\n", test1);

        assertTrue(check_match(test1, items));
        assertTrue(test1.isCurrent());
        test1.advance();
        assertFalse(test1.isCurrent());

        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_10_addBefore() {
        System.out.printf("\ntesting addBefore\n");

        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_11_removeCurrent() {
        System.out.printf("\ntesting removeCurrent\n");

        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_12_concatenation() {
        System.out.printf("\ntesting the concatenation method\n");

        System.out.printf("passed . . .\n");
    }

}

