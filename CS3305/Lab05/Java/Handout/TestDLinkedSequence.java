import java.util.ListIterator;

public class TestDLinkedSequence {

    public static void forward( DLinkedSequence<Integer> seq ) {
        //Assumes that list items have toString() defined
        ListIterator<Integer> fli = seq.listIterator();
        System.out.printf("[");
        if ( fli.hasNext() ) System.out.printf(" %d", fli.next());
        while ( fli.hasNext() ) {
            System.out.printf(", %s", fli.next() );
        }
        System.out.printf(" ]");
    }

    public static void reverse( DLinkedSequence<Integer> seq ) {
        //Assumes that list items have toString() defined
        ListIterator<Integer> fli = seq.listIterator(seq.size());
        System.out.printf("[");
        if ( fli.hasPrevious() ) System.out.printf(" %d", fli.previous());
        while ( fli.hasPrevious() ) {
            System.out.printf(", %s", fli.previous() );
        }
        System.out.printf(" ]");
    }

    public static void main(String[] args) {

        DLinkedSequence<Integer> ls = new DLinkedSequence<Integer>();
        System.out.printf("After constructor ==> %s\n", ls); //test toString

        for ( int i = 2; i < 12; i += 2 ) {
            ls.addAfter( i );
            System.out.printf("addAfter %d ==> %s\n", i, ls); //test toString
        }
        ls.begin();
        System.out.printf("sequence after begin() ==> %s\n", ls);

        for ( int i = 0; i > -12; i -=2 ) {
            ls.addBefore( i );
            System.out.printf("addBefore %d ==> %s\n", i, ls); //test toString
        }

        ls.retreat();
        System.out.printf("sequence after retreat() ==> %s\n", ls);
        ls.addBefore( -12 );
        System.out.printf("addBefore %d ==> %s\n", -12, ls); //test toString

        ls.retreat();
        ls.addAfter( 12 );
        System.out.printf("retreat() and addAfter %d ==> %s\n", 12, ls); //test toString

        DLinkedSequence<Integer> copy = new DLinkedSequence<Integer>( ls );
        System.out.printf("The copy is \n%s\n", copy);

        System.out.printf("Testing forward and reverse iterators\n");
        forward( ls ); System.out.println(); //test forward iterator
        reverse( ls ); System.out.printf("\n\n"); //test reverse iterator

        //And another test of forward iterator
        for (Integer value : ls) {
            System.out.printf("%d ", value);
        }
        System.out.printf("\n");

        ls.begin();
        while ( ls.size() > 0 ) {
            ls.removeCurrent();
            System.out.printf("after removeCurrent() ==> %s\n", ls);
        }



    }
}
