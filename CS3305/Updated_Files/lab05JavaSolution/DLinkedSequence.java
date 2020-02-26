import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/*
 * Class provides a "sequence" with a cursor and methods to position the cursor,
 * add items to sequence and remove items from the sequence.
 */
public class DLinkedSequence<Item> extends Object implements Cloneable, Iterable<Item> {
//INVARIANT for the sequence ADT:
//      1.  The items in the sequence are stored in a doubly-inked list
//      2.  The head reference of the list is a "dummy" DLNode<Item> and is
//          stored in the member variable head.  Head's next field references tail
//          if the sequence is empty.  Head's previous field is always null.
//      3.  The tail pointer of the list is a "dummy" DLNode<Item> and stored
//          in the member variable tail.  Tail's previous field references head
//          if the sequence is empty. Tails's next field is always null.
//      4.  The current node in the list is referenced by the member variable cursor.
//          Value is either head or tail if there is no current node.
//      6.  The total number of items in the list is stored in the member variable
//          used.
//      7.  The class supports both forward and reverse iterators.
//
    private DLNode<Item> head, tail;
    private DLNode<Item> cursor;
    private int used;

    /**
     * Default constructor.
     *
     * @post. DLinkedSequence properly initialized as an empty sequence
     *        such that head's next field references tail, tail's previous
     *        field references head, head's previous field is null and
     *        tail's next field is null, used is 0 AND cursor references
     *        head.
     */
    public DLinkedSequence( ) {
        this.used = 0;
        this.head = new DLNode<Item>( null, null, null );
        this.tail = new DLNode<Item>(head, null, null );
        this.cursor = head;
        this.head.setNext( this.tail );
    }

    /**
     * Copy constructor.
     * @param sequenceToCopy another sequence from which we wish to make a
     *  "deep" copy if possible
     * @pre. sequenceToCopy != null
     *       NullPointerException thrown if it is null
     *
     * @post. LinkedSequence properly initialized as a deep copy 
     *        of sequenceToCopy including having cursor
     *        referencing the correct node in the copy relative to 
     *        its position in sequenceToCopy
     * @exception NullPointerException of sequenceToCopy is null
     */
    public DLinkedSequence ( DLinkedSequence<Item> sequenceToCopy ) {
        if ( sequenceToCopy == null ) 
            throw new NullPointerException("Cannot copy a non-list");
        //STUDENT WORK COMES HERE 
        //NOTE -- the copy constructor should be defined in terms of clone()
        //        or clone() should be defined in terms of this constructor
        //        but both should not be doing the same thing
        DLinkedSequence<Item> temp = sequenceToCopy.clone();
        this.head = temp.head;
        this.tail = temp.tail;
        this.cursor = temp.cursor;
        this.used = temp.used;
        temp = null;
    }

    /**
     * An override of clone() method in class Object.
     *
     * @post.   returns a "deep" copy of this object at two levels<br>
     *  1) The underlying list and all attributes are "deep" copied
     *  2) The data in each node is "deep" copied if the data object supports
     *     clone(); otherwise, the data is "shallow" copied
     */
    @Override
    @SuppressWarnings("unchecked")
    public DLinkedSequence<Item> clone( ) {
        DLinkedSequence<Item> result;

        try {
            result = (DLinkedSequence<Item>) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("DLinkedSequence<Item> does not implement Cloneable");
        }

        //STUDENT WORK COMES HERE to ensure a "deep copy" of this
        result = new DLinkedSequence<Item>(); //to avoid the shallow copy of super.clone()
        if ( this.used == 0 ) return result;

        //below logic as per hints in text on p. 235 simplified due to double-linked nodes
        //and dummy head and tail
        result.used = this.used;
        if ( !this.isCurrent() ) { //a simple complete list copy will do the job
            Object[] headTail = DLNode.<Item>listCopyWithTail(this.head);
            result.head = (DLNode<Item>)headTail[0]; result.tail = (DLNode<Item>)headTail[1];
            result.cursor = result.head; 
        } else { //we have a cursor defined
            Object[] headTail = DLNode.<Item>listPart(this.head, this.cursor);
            result.head = (DLNode<Item>)headTail[0]; result.cursor = (DLNode<Item>)headTail[1];
            //now get rest of list
            headTail = DLNode.<Item>listPart(this.cursor.getNext(), this.tail);
            DLNode<Item> temp = (DLNode<Item>)headTail[0]; result.tail = (DLNode<Item>)headTail[1];
            //now splice the two pieces together
            result.cursor.setNext(temp);
            temp.setPrevious(result.cursor);
        }

        return result;
    }

    /**
     * An override of the Object class equals.
     * @param other reference to another object against which this object is to be
     *     compared for equality
     * @return boolean indicating whether two DLinkedSequence objects are equal
     * @post.  returns boolean indicating whether this equals other<br>
     *         with the following checks<br>
     *         a. this cannot be equal to null, if so return false<br>
     *         b. if this.used != other.used, if so return false<br>
     *         c. iterate over both this list and other list comparing values
     *            of data field for a pair-wise match.  Note that this comparison
     *            must be made using the equals method.<br>
     *         d. Also, determine if the cursor is at the same position in both
     *            lists<br>
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals( Object other ) {
           /* cannot be equal to nothing */
        if ( other == null ) return false;
        DLinkedSequence<Item> candidate = (DLinkedSequence<Item>) other;
        /* cannot be equal if number of nodes differs */
        if ( candidate.used != this.used ) return false;
        if ( candidate.hashCode() != this.hashCode() ) return false;

        //we now know that both lists have same number of nodes
        //STUDENT WORK COMES HERE to check for detailed equality
        DLNode<Item> thisList = this.head;
        DLNode<Item> otherList = candidate.head;
        boolean isEqual = true;

        //must traverse both lists to check for a mismatch in data
        //and while so doing, check for cursor mismatch
        while ( isEqual && thisList != this.tail ) {
            if ( thisList.getData() != otherList.getData() )
                isEqual = false;
            else if ( (thisList == this.cursor && otherList != candidate.cursor) ||
                      (otherList == candidate.cursor && thisList != this.cursor)    )
                isEqual = false;
            else {
                thisList = thisList.getNext();
                otherList = otherList.getNext();
            }
        }
        return isEqual;
    }

    /**
     * Generates a hashCode for all attributes of the sequence.
     *
     * @return int hash code 
     *
     * @post.  when invoked multiple times, hashCode must consistently return the same
     *         information provided no modification to the object occurs between
     *         invocation <br>
     *         if two objects are equal according to the equals method, then the
     *         hashCode method for these two objects MUST return the same value <br>
     *
     * @note. This method overrides method by same name in class Object
     */
    @Override
    public int hashCode( ) {
        int hashValue = Objects.hash( this.used );

        DLNode<Item> listPtr = this.head.getNext();

        while ( listPtr != this.tail ) {
            hashValue = 101 * hashValue + Objects.hash( listPtr.getData() );
            listPtr = listPtr.getNext();
        }

        return hashValue;
    }

    /**
     * Generates a string representation of state of sequence including number of 
     * elements in sequence and position of cursor.
     *
     * @return a String
     *
     * @note. This method overrides method by same name in class Object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DLinkedSequence: ");
        sb.append(this.used);
        sb.append(" items");
        sb.append("==>[ ");
        DLNode<Item> ptr = this.head.getNext();
        if ( this.used > 0 ) {
            if ( this.cursor == ptr )
                sb.append("^");
            sb.append( ptr.getData() );
            ptr = ptr.getNext();
            while ( ptr != this.tail ) {
                sb.append(", ");
                if ( this.cursor == ptr )
                    sb.append("^");
                sb.append(ptr.getData());
                ptr = ptr.getNext();
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * Returns number of items in the sequence.
     *
     * @return an int representing number of items in sequence
     */
    public int size( ) {
        return this.used;
    }

    /**
     * Positions sequence cursor to beginning of sequence -- cursor will be current
     * if sequence contains one or more elements; otherwise cursor will not
     * be current.
     *
     * @post.   sequence cursor positioned to beginning of sequence
     */
    public void begin( ) {
        /* valid even if list is empty */
        this.cursor = head.getNext();
    }

    /**
     * Positions the cursor forward to the next item in the sequence.
     *
     * @pre.  cursor not at the last item in the sequence;
     *        if so throw IllegalStateException
     *
     * @post. cursor moved to next item in sequence
     */
    public void advance( ) {
        if (this.cursor == tail)
            throw new IllegalStateException("cannot move the cursor forward");
        this.cursor = this.cursor.getNext();
    }

    /**
     * Positions sequence cursor to end of sequence -- cursor will be current
     * if sequence contains one or more elements; otherwise cursor will not
     * be current.
     *
     * @post.   sequence cursor positioned to end of sequence
     */
    public void end( ) {
        /* valid even if list is empty */
        this.cursor = this.tail.getPrevious();
    }

    /**
     * Positions cursor backward to previous element in the sequence.
     *
     * @pre.  cursor not a first item in the sequence; 
     *        if so throw IllegalStateException
     *
     * @post. cursor moved to previous item in the sequence
     */
    public void retreat( ) {
        if ( this.cursor == this.head )
            throw new IllegalStateException("cannot move the cursor backward");
        this.cursor = this.cursor.getPrevious();
    }

    /**
     * Tests whether cursor is validly defined for this sequence.
     *
     * @return boolean whether cursor valid and referencing an item in the sequence
     *
     */
    public boolean isCurrent( ) {
        return ( (this.cursor != this.head) && (this.cursor != this.tail) );
    }

    /**
     * Returns value of current item in the sequence.
     *
     * @pre.  isCurrent() is true
     *        otherwise throw IllegalStateException
     *
     * @return  an Item -- value of current element in sequence
     */
    public Item getCurrent( ) {
           if ( !this.isCurrent() )
            throw new IllegalStateException("cannot getCurrent when no current node");
        return this.cursor.getData();
    }

    /**
     * Add a newValue after the current node.
     *
     * @param newValue -- a reference to an Item
     *
     * @post. 1) if list empty value inserted between head and tail<br>
     *        2) if this.isCurrent(), new node with newValue added after cursor<br>
     *        3) if not this.isCurrent(), new node with newValue added at end of list
     *           which means before the tail<br>
     *        4) in any case, the node added becomes the current node<br>
     */
    public void addAfter( Item newValue ) {
        this.used++;
        //STUDENT WORK COMES HERE 
        if ( this.head.getNext() == this.tail ) { //#1
            head.addAfter( newValue );
            this.cursor = head.getNext();
        } else if ( !this.isCurrent() ) { //#3
            tail.addBefore( newValue );
            this.cursor = tail.getPrevious(); 
        } else { //#2
            this.cursor.addAfter( newValue );
            this.cursor = this.cursor.getNext();
        }
        /* or to simplify
        if ( !this.isCurrent() ) { //handle #1 and #3
            this.cursor = this.tail.getPrevious();
        }
        this.cursor.addAfter( newValue );
        this.cursor.advance();
        */
    }

    /**
     * Add a newValue before the current node.
     *
     * @param newValue -- a reference to an Item
     *
     * @post.  1) if list empty value inserted between head and tail<br>
     *         2) if this.isCurrent(), new node with newValue added before cursor<br>
     *         3) if not this.isCurrent(), new node with newValue added at beginning 
     *         of list or after head<br>
     *         4) in any case, the node added becomes the current node<br>
     */
    public void addBefore( Item newValue ) {
        this.used++;
        //STUDENT WORK COMES HERE
        if ( this.head.getNext() == this.tail ) { //#1
            tail.addBefore( newValue );
            this.cursor = tail.getPrevious();
        } else if ( !this.isCurrent() ) { //#3
            this.head.addAfter( newValue );
            this.cursor = this.head.getNext();
        } else { //#2
            this.cursor.addBefore( newValue );
            this.cursor = this.cursor.getPrevious();
        }
        /* or to simplify
        if ( !this.isCurrent() ) { // handle #1 and #3
            this.cursor = this.head.getNext();
        }
        this.cursor.addBefore( newValue );
        this.retreat();
        */
    }

    /** 
     * Mutator that removes the current item if such exists.
     *
     * @pre.  isCurrent() is true; 
     *           otherwise throw new IllegalStateException
     * @post. the current element has been removed from this sequence and
     *        the following element (if there is one) is now the new current element.
     *        If there was no following element, then there is no current element.
     * @throws IllegalStateException if the cursor not current
     */
    public void removeCurrent() {
        if ( !this.isCurrent() )
            throw new IllegalStateException("cannot remove current node when there is no current node");
        this.used--;
        //STUDENT WORK COMES HERE
        this.cursor = this.cursor.getNext();
        this.cursor.removeBefore();
    }

    public DLinkedSequence<Item> concatenation( DLinkedSequence<Item> other ) {
        DLinkedSequence<Item> newSequence = new DLinkedSequence<Item>();

        DLNode<Item> thisPtr  = this.head.getNext();
        DLNode<Item> newPtr   = newSequence.head;
        DLNode<Item> otherPtr = other.head.getNext();
        // STUDENT WORK COMES HERE-- write the logic to do this; DO NOT use any
        //                           of the DLNode's copy methods
        while ( thisPtr != this.tail ) {
            newPtr.addAfter(thisPtr.getData());
            newPtr = newPtr.getNext();
            thisPtr = thisPtr.getNext();
        }

        while ( otherPtr != other.tail ) {
            newPtr.addAfter(otherPtr.getData());
            newPtr = newPtr.getNext();
            otherPtr = otherPtr.getNext();
        }

        newSequence.cursor = newSequence.head;
        newSequence.used = this.used + other.used;

        return newSequence;
    }

    /**
     * A forward/reverse iterator presenting all items in the sequence.
     *
     * @return Iterator&lt;Item&gt;
     */
    public Iterator<Item> iterator() {
        return new SequenceIterator();
    }

    /**
     * A forward/reverse list iterator presenting all items in the sequence
     * starting with the first item.
     *
     * @return ListIterator&lt;Item&gt;
     */
    public ListIterator<Item> listIterator() {
        return new SequenceIterator();
    }

    /**
     * A reverse/forward list iterator presenting all items in the sequence
     * starting with the last item.
     *
     * @param   index an int indicating position of last item in sequence
     *
     * @return ListIterator&lt;Item&gt;
     *
     * @note. This iterator may be invoked with an argument that is
     *        equal to size()
     */
    public ListIterator<Item> listIterator( int index ) {
        return new SequenceIterator( index );
    }

    /* Inner class to provide iterator support */
    private class SequenceIterator implements ListIterator<Item> {

        private DLNode<Item> iterCursor;
        private int index;

        public SequenceIterator() {
            iterCursor = DLinkedSequence.this.head.getNext();
            index = 0;
        }

        public SequenceIterator( int index ) {
            if ( index != DLinkedSequence.this.size() )
                throw new IllegalArgumentException(
                        "Cannot start reverse iterator anywhere but at end");
            iterCursor = DLinkedSequence.this.tail.getPrevious();
            index = DLinkedSequence.this.size()-1;
        }

        public boolean hasNext() {
            return iterCursor != tail;
        }

        public Item next() throws NoSuchElementException {
            if ( !hasNext() )
                throw new 
                    NoSuchElementException("iterator went past end of sequence");
            Item retValue = iterCursor.dataCopy();
            iterCursor = iterCursor.getNext();
            ++index;
            return retValue;
        }

        public int nextIndex() {
            if ( hasNext() ) return index;
            return DLinkedSequence.this.size();
        }

        public boolean hasPrevious() {
            return iterCursor != head;
        }

        public Item previous() throws NoSuchElementException {
            if ( !hasPrevious() )
                throw new
                    NoSuchElementException("iterator went past beginning of sequence");
            Item retValue = iterCursor.dataCopy();
            iterCursor = iterCursor.getPrevious();
            --index;
            return retValue;
        }

        public int previousIndex() {
            if ( hasPrevious() ) return index;
            return -1;
        }

        public void add(Item item) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("SequenceIterator: add() not implemented");
        }
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("SequenceIterator: remove() not implemented");
        }
        public void set(Item item) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("SequenceIterator: set() not implemented");
        }
    }

}
