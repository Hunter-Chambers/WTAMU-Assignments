public class TestDLNode {

    public static void forwardTraversal( DLNode<Integer> head ) {
        while ( head.getNext() != null ) {
            //Note the unboxing that takes place here
            System.out.printf("%d ",  head.getData() );
            head = head.getNext();
        }
        System.out.printf("\n");
    }

    public static void backTraversal( DLNode<Integer> tail ) {
        while ( tail.getPrevious() != null ) {
            //Note the unboxing that takes place here
            System.out.printf("%d ", tail.getData() );
            tail = tail.getPrevious();
        }
        System.out.printf("\n");
    }

    public static void main(String[] args) {

        DLNode<Integer> head = new DLNode<Integer>(null, null, null);
        DLNode<Integer> tail = new DLNode<Integer>(head, null, null);
        head.setNext( tail );

        DLNode<Integer> ptr = head;

        for ( int i = 2; i < 15; i +=2 ) {
            ptr.addAfter( i );
            ptr = ptr.getNext();
        }

        forwardTraversal( head.getNext() );
        backTraversal( tail.getPrevious() );

        ptr = head.getNext().getNext().getNext();
        ptr.removeAfter();
        System.out.printf("Removing the middle node\n");
        forwardTraversal( head.getNext() );
        backTraversal( tail.getPrevious() );

        head.removeAfter();
        System.out.printf("Removing the head node\n");
        forwardTraversal( head.getNext() );
        backTraversal( tail.getPrevious() );

        System.out.printf("Removing the tail node\n");
        tail.removeBefore();
        forwardTraversal( head.getNext() );
        backTraversal( tail.getPrevious() );

        @SuppressWarnings("unchecked")
        Object[] headTail =  DLNode.<Integer>listPart( head, tail );
        @SuppressWarnings("unchecked")
        DLNode<Integer> newHead = (DLNode<Integer>)headTail[0];
        @SuppressWarnings("unchecked")
        DLNode<Integer> newTail = (DLNode<Integer>)headTail[1];
        System.out.printf("The copied list\n");
        forwardTraversal( newHead.getNext() );
        backTraversal( newTail.getPrevious() );

        newHead.getNext().setData( 7 );
        System.out.printf("The copied list after altering first node of copy\n");
        forwardTraversal( newHead.getNext() );
        backTraversal( newTail.getPrevious() );
        System.out.printf("The original list after altering first node of copy\n");
        forwardTraversal( head.getNext() );
        backTraversal( tail.getPrevious() );

        while ( /*head.getNext() != tail*/ tail.getPrevious() != head ) {
            System.out.printf("Removing the tail node from original list\n");
            tail.removeBefore();
            forwardTraversal( head.getNext() );
            backTraversal( tail.getPrevious() );
        }

        while ( /*head.getNext() != tail*/ newTail.getPrevious() != newHead ) {
            System.out.printf("Removing the head node from copied list\n");
            newHead.removeAfter();
            forwardTraversal( newHead.getNext() );
            backTraversal( newTail.getPrevious() );
        }

    }
}
