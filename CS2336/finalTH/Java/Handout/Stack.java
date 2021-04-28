import java.util.LinkedList;
import java.util.Iterator;
import java.util.ListIterator;
/* 
 * Given that the Java Collections' implementation of Stack
 * is based on a vector (array), its implementation leaves
 * something to be desired in overall performance.  This
 * implementation of Stack utilizes a concept called design
 * by composition.  This means that this implementation of
 * the Stack ADT is accomplished by using another abstraction
 * but only revealing those capabilities relevant to a Stack.
 *
 * In this case the underlying data structure is a Java Collections
 * LinkedList.  Please check out:
 * https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html
 * to observe the richness of that implementation . . . it is designed
 * to support what is done here.
 *
 * NOTE:  We are not supporting the methods clone and equals
 *        and, thus, do not need hashCode as well.
 *
 */

public class Stack<Item> implements Iterable<Item>  {

    private LinkedList<Item> theStack;

    public Stack( ) {
        theStack = new LinkedList<Item>( );
    }

    public void push( Item newElement ) {
        theStack.push( newElement );

    }

    public Item pop( ) {
        if ( theStack.isEmpty() )
            throw new IllegalStateException("Cannot pop from empty stack");
        return theStack.pop( );
    }

    public Item peek( ) {
        if ( theStack.isEmpty() )
            throw new IllegalStateException("Cannot peek into empty stack");
        return theStack.peek( );
    }

    public int size( ) {
        return theStack.size( );
    }

    public void reset( ) {
        theStack.clear( );
    }

    public boolean isEmpty( ) {
        return theStack.size( ) == 0;
    }

    public Iterator<Item> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<Item> {
        ListIterator<Item> li;

        public StackIterator( ) {
            li = Stack.this.theStack.listIterator(0);
        }

        public boolean hasNext() {
            return li.hasNext();
        }

        public Item next() {
            return li.next();
        }

        public void remove() {
            throw new UnsupportedOperationException("Must use pop to remove an item");
        }

    }


}
