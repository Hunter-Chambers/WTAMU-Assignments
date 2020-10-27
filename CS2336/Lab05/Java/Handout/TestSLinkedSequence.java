
public class TestSLinkedSequence extends Object {

    public static void main( String[] args ) {
        SLinkedSequence<Long> list = new SLinkedSequence<Long>();
        System.out.printf("%s\n%s\n", "Sequence after default constructor", list);
        System.out.printf("%s%s\n", "isCurrent() is ", list.isCurrent() );
        System.out.printf("%s%d\n", "size() is ", list.size() );
        try {
            System.out.printf("%s%d\n", "Current value in list is ", list.getCurrent() );
        }
        catch (IllegalStateException ex) {
            System.out.printf("%s %s\n", "call to getCurrent() results in", ex.getMessage() );
        }
        try {
            list.advance();
            System.out.printf("Advance should have failed\n");
        }
        catch (IllegalStateException ex) {
            System.out.printf("%s %s\n\n", "advance() resulted in", ex.getMessage() );
        }

        for ( long number = -8; number <= 8; number += 2 ) 
            list.addAfter( number );
        System.out.printf("%s\n%s\n\n", "new sequence after multiple addAfter", list);
        list.start(); list.advance(); list.advance(); list.advance(); 
        list.addAfter(-1L); //this should put -1 in front of the zero
        System.out.printf("%s\n%s\n\n", 
                "sequence after start and three advances adding after -1", list);

        list.advance(); list.addAfter(1L); //this should put 1 after the zero
        System.out.printf("%s\n%s\n\n", 
                "sequence after advance and adding after 1", list);

        list.advance(); list.advance(); list.addBefore(3L); //this should put 3 in front of 4
        System.out.printf("%s\n%s\n", 
                "sequence after two advances and adding before 3", list);
        System.out.printf("%s%d\n\n", "And the list size is now ", list.size() );


        list = new SLinkedSequence<Long>();
        for ( long number = -8; number <= 8; number += 2 ) 
            list.addBefore( number );
        System.out.printf("%s\n%s\n\n", "new sequence after multiple addBefore", list);

        list.start();
        while ( list.isCurrent() ) list.advance();
        System.out.printf("%s\n%s\n", "Sequence after advancing cursor past end of list", list);
        try {
            list.advance();
            System.out.println("Should not see this print -- control failed if we do");
        }
        catch (IllegalStateException exception) {
            System.out.printf("%s %s\n\n", 
                    "We correctly caught exception from advance: ", exception.getMessage() );
        }

        list.start();
        SLinkedSequence<Long> list2 = new SLinkedSequence<Long>( list );
        System.out.printf("%s\n%s\n", "the original list is:", list);
        System.out.printf("%s\n%s\n\n", "the new list created with copy constructor:", list2);

        System.out.printf("%s%s\n\n", 
                "Testing whether list and list2 are equal -- we should get true and we got ", 
                list.equals(list2) );

        System.out.printf("%s\n%s\n", "the list is:", list);
        list.removeCurrent();
        System.out.printf("%s\n%s\n\n", "the list after removeCurrent is:", list);

        list.advance(); list.removeCurrent();
        System.out.printf("%s\n%s\n\n", 
                "the list after an advance and removeCurrent is:", list);

        list.advance(); list.advance(); list.advance(); list.advance(); list.removeCurrent();
        System.out.printf("%s\n%s\n\n", 
                "the list after four more advances and removeCurrent is:", list);

        list.removeCurrent();
        System.out.printf("%s\n%s\n\n", 
                "the list after removeCurrent is:", list);

        try {
            System.out.println("Attempting to removeCurrent when there is no current");
            list.removeCurrent();
            System.out.println("This print should not happen if removeCurrent implemented properly");
        }
        catch (IllegalStateException exception) {
            System.out.printf("%s%s\n\n", "Correctly caught IllegalStateException -- ",
                                            exception.getMessage() );
        }

        System.out.printf("%s\n%s\n", "list before copy constructor", list);
        System.out.printf("%s\n%s\n\n", "list2 before copy constructor", list2);
        list = new SLinkedSequence<Long>(list2);
        System.out.printf("%s\n%s\n", "list after copy constructor", list);
        System.out.printf("%s\n%s\n\n", "list2 after copy constructor", list2);
        System.out.printf("The two lists should now be equal\n");
        System.out.printf("\tComparing list.equals(list2) -- should be true and it is %s\n", list.equals(list2));

        System.out.printf("Advancing one position in list\n");
        list.advance();
        System.out.printf("The two lists should now NOT be equal\n");
        System.out.printf("\tComparing list.equals(list2) -- should be false and it %s\n", 
                            list.equals(list2)?"is NOT":"is");
        System.out.printf("List is %s\n", list);
        System.out.printf("List2 is %s\n", list2);

        list2 = null;
        System.out.printf("\nAttempting to invoke copy constructor on null list\n");
        try {
            list = new SLinkedSequence<Long>( list2 );
            System.out.println("If this message displays, then copy constructor not implemented correctly.");
        }
        catch (NullPointerException npe) {
            System.out.printf("%s%s\n\n", 
                    "Caught NullPointerException as we should have ==> ", npe.getMessage() );
        }

    }
}

