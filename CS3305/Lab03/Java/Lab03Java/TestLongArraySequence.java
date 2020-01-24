import java.util.Iterator;

public class TestLongArraySequence {

    public static void main( String[] args) {
        LongArraySequence ages = new LongArraySequence();
        System.out.printf("%s\n %s %s\n\n" , "After constructor with request for default capacity: ", ages,
                                           "and capacity should be 1" );
        LongArraySequence stuff = new LongArraySequence(1);
        System.out.printf("%s\n %s %s\n\n" , "After constructor with request for capacity of 1: ", stuff,
                                           "and capacity should be 1" );
        stuff = new LongArraySequence(500);
        System.out.printf("%s\n %s %s\n\n" , "After constructor with request for capacity of 500: ", stuff,
                                           "and capacity should be 500" );

        stuff.addBefore(11);
        System.out.printf( "%s\n%s\n" , "stuff.addBefore(11) cursor should be on 11 at end of sequence", stuff );
          
        stuff.trimToSize();
        System.out.printf( "%s\n%s %s\n\n" ,
                "after stuff.trimToSize() cursor should be on 11 at end of sequence", stuff, "and capacity should be 1" ) ;

        ages = new LongArraySequence(2);
        ages.addAfter(47); 
        System.out.printf( "%s\n%s\n\n" , "ages.addAfter(47) cursor should be on 47 at end of sequence", ages );
        ages.addAfter(33);
        System.out.printf( "%s\n%s\n\n" , "ages.addAfter(33) cursor should be on 33 at end of sequence", ages );
        ages.addAfter(17);
        System.out.printf( "%s\n%s\n\n" , "ages.addAfter(17) cursor should be on 17 at end of sequence", ages );
        ages.addAfter(77);
        System.out.printf( "%s\n%s\n\n" , "ages.addAfter(77) cursor should be on 77 at end of sequence", ages );
        ages.addAfter(3);
        System.out.printf( "%s\n%s\n\n" , "ages.addAfter(3) should be on 3 at end of sequence", ages );
        ages.addAfter(1);
        System.out.printf( "%s\n%s\n\n" , "ages.addAfter(1) cursor should be on 1 at end of sequence", ages );

        ages.addBefore(99);
        System.out.printf( "%s\n%s\n\n" , "After ages.addBefore(99) cursor should be on 99 and the 99 before the 1", ages );
        ages.removeCurrent();
        System.out.printf( "%s\n%s\n\n" , "After attempting removeCurrent() and the 99 should be removed and cursor on the 1", ages );
        ages.removeCurrent();
        System.out.printf( "%s\n%s\n\n" , "After attempting removeCurrent() and the 1 should be removed with no cursor", ages );

        try {
            System.out.printf("%s", "Attempting ages.removeCurrent() with no current item -->");
            ages.removeCurrent();
        }
        catch (IllegalStateException except) {
            System.out.printf("\n%s %s\n\n", "Should have failed and did:", except.getMessage());
        }

        ages.start(); ages.removeCurrent(); 
        System.out.printf( "%s\n%s\n\n" , 
                "ages after attempting start() and then removeCurrent() cursor should be at 33", ages );


        LongArraySequence a = new LongArraySequence( ages );
        System.out.printf( "%s\n%s\n\n" , "a after being copied from ages", a );
        System.out.printf( "%s %s\n" , " a.equals( ages )should be true ", a.equals( ages ));
        System.out.printf( "%s %s\n\n" , " a  != ages should be true",  a != ages);  
        a.advance();
        System.out.printf( "%s\n%s\n\n" , "a after a.advance cursor should be at 17", a );

        LongArraySequence negatives = new LongArraySequence(1);
        for (int index = -10; index < 0; index++)
            negatives.addAfter( index );
        System.out.printf("%s\n%s\n\n" , 
                "negatives with ten negatives from -10 to -1, cursor should be on -1, capacity should be 16",negatives);

        LongArraySequence b = a.concatenation( negatives );
        System.out.printf( "%s\n%s\n\n" , 
                "b after b = ages.concatenation( a ) cursor should be at 33 and capacity 14", b );
        b.addBefore(99);
        System.out.printf( "%s\n%s\n\n" , 
                "b after b.addBefore(99) -- cursor should be at 99 and 99 first element in sequence and capacity 28", b );
    
        b.start();
        while ( b.isCurrent() )
            b.removeCurrent();
        System.out.printf( "%s \n%s\n\n" , 
                "b after b.start() and then repeatedly removing current item -- should have 0 items, capacity remains at 28", b); 

        b.trimToSize();
        System.out.printf( "%s\n%s\n\n" , 
                "b after b.trimToSize() -- should have 0 items. capacity should be the minimum of 1", b);
        b.addBefore(7);
        System.out.printf( "%s\n %s\n\n" , "b after b.addBefore(7)", b); 
        b.removeCurrent();
        b.addAfter(7);
        System.out.printf( "%s\n %s\n\n" , "b after b.removeCurrent() and b.addAfter(7)", b); 
    
        b.removeCurrent();
        System.out.printf( "%s %s\n\n" , 
                "b after b.removeCurrent().  b.isCurrent() should be False and it is?", b.isCurrent()); 

        negatives.start();
        int index = 0;
        System.out.printf("%s\n%s\n\n" , "After negatives.start() and before any advances", negatives); 
        System.out.printf("Iterating over the elements of negatives using class methods\n");
        negatives.start();
        boolean ok = true;
        while (ok && negatives.isCurrent() ) {
            System.out.printf("%d " , negatives.getCurrent());
            try {
                negatives.advance();
            }
            catch (IllegalStateException except) {
                System.out.printf("\n %s\n\n" , 
                        "Should have printed all ten negatives and advanced beyond end of sequence" );
                ok = false;
            }
        }
        System.out.printf("\n\n");

        System.out.printf("The next several examples ALL rely on the class implementing\n" +
                          "the Iterable<T> interface\n\n");

        System.out.printf("Now using the formal iterator to visit all elements of negatives\n" +
                          "This time iteration is under full control of programmer -- " +
                          "thus the iterator is active or external.\n");
        Iterator<Long> it = negatives.iterator();
        while ( it.hasNext() ) {
            System.out.printf("%d ", it.next());
        }
        System.out.printf("\n\n");

        System.out.printf("Now using the formal iterator to visit all elements of negatives\n" +
                          "This is using the enhanced for loop introduced in Java 5, and \n"  +
                          "is still known as an active or external iterator\n");
        for (Long number : negatives ) {
            System.out.printf("%d ", number);
        }
        System.out.printf("\n\n");

        System.out.printf("Now using the new (as of Java 8) internal iterator.\n" +
                          "This also known as a passive, call back, or implicit\n" +
                          "iterator -- programmer focuses only on the \"what\" not the \"how\"\n");
        negatives.forEach(number -> System.out.printf("%d ", number));
        System.out.println();
    }
}
