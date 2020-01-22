import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runners.*;
import org.junit.*;
import java.util.Arrays;
import java.util.ListIterator;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyJTestDLinkedSequence {

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
        DLinkedSequence<Integer> seq1 = new DLinkedSequence<Integer>();
        DLinkedSequence<Integer> seq2 = new DLinkedSequence<Integer>(seq1);
        assertNotSame(seq1, seq2);
        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_02_Copy_Constructor() {
        System.out.printf("\ntest copy constructor\n");
        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_03_Equals() {
        System.out.printf("\ntest equals method\n");
        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_04_addAfter_equals_copy_constructor() {
        System.out.printf("\ntesting addAfter\n");
        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_05_addBefore_advance() {
        System.out.printf("\ntesting addBefore\n");
        System.out.printf("passed . . .\n");
    }

    @Test
    public void test_06_removeCurrent() {
        System.out.printf("\ntesting removeCurrent\n");
        System.out.printf("passed . . .\n");
    }
}

