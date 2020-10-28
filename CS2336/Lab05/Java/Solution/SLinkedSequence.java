// FILE SLinkedSequence.java
//
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * This class provides the ADT SLinkedSequence&lt;Item&gt; which is 
 * a sequence implemented using a singly-linked list
 *
 * @author H. Paul Haiduk based on code by Michael Main
 * @author Hunter Chambers
 * @version October, 2020
 */

public class SLinkedSequence<Item> extends Object implements Cloneable, Iterable<Item> {
//INVARIANT for the sequence ADT:
//      1.  The items in the sequence are stored in a linked list
//      2.  The head pointer of the list is stored in the member variable head
//          whose value is null if the sequence is empty
//      3.  The tail pointer of the list is stored in the member variable tail
//          whose value is null if the sequence is empty
//      4.  The current node in the list is pointed to by the member variable cursor
//          whose value is null if there is no current node
//      5.  A pointer to the node that precedes the current node is stored in the
//          member variable precursor.  Precursor has value null if there is no current
//          node in the list OR if the current node is the first node in the list.
//      6.  The total number of items in the list is stored in the member variable
//          used.
//
    private SLNode<Item> head, tail;
    private SLNode<Item> precursor, cursor;
    private int used;

    /**
     * Default constructor.
     *
     * @post. SLinkedSequence&lt;Item&gt; properly initialized as an empty sequence
     */
    public SLinkedSequence( ) {
        this.head = this.tail = this.precursor = this.cursor = null;
        used = 0;
    }

    /**
     * Copy constructor
     * @param sequenceToCopy
     *        a reference to another sequence that is to be copied
     * @pre. sequenceToCopy != null<br>
     *       NullPointerException thrown if it is null
     *
     * @post. SLinkedSequence&lt;Item&gt; properly initialized as a deep copy<br>
     *        of sequenceToCopy including having precursor and cursor<br>
     *        referencing the correct nodes in the copy relative to <br>
     *        their position in sequenceToCopy
     * @note. The newly created object is a "deep" copy of this object at two levels<br>
     *  1) The underlying list and all attributes are "deep" copied
     *  2) The data in each node is "deep" copied if the data object supports
     *     clone(); otherwise, the data is "shallow" copied
     **/
    @SuppressWarnings("unchecked")
    public SLinkedSequence( SLinkedSequence<Item> sequenceToCopy ) {
        if ( sequenceToCopy == null ) 
            throw new NullPointerException("Cannot copy a non-list");
        /* are we copying empty list */
        if ( sequenceToCopy.used == 0 ) {
            this.head = this.tail = this.precursor = this.cursor = null;
            this.used = 0;
        }
        //below logic as per hints in text on p. 235
        else if ( sequenceToCopy.isCurrent() && (sequenceToCopy.cursor != sequenceToCopy.head) ) {
            this.used = sequenceToCopy.used;
            //last bullet top page 235
            //copy from head through precursor
            // STUDENT COMPLETE HERE

            SLNode<Item> otherCursor = sequenceToCopy.head.getLink();
            head = precursor = new SLNode<Item>(sequenceToCopy.head.getData());

            while (otherCursor != sequenceToCopy.cursor) {
                precursor.setLink(new SLNode<Item>(otherCursor.getData()));
                precursor = precursor.getLink();
                otherCursor = otherCursor.getLink();
            }

            cursor = precursor;

            while (otherCursor != null) {
                cursor.setLink(new SLNode<Item>(otherCursor.getData()));
                cursor = cursor.getLink();
                otherCursor = otherCursor.getLink();
            }

            tail = cursor;
            cursor = precursor.getLink();
        } else { //no cursor or cursor is at head
            // first two bullets top page 235
            this.used = sequenceToCopy.used;
            Object [] result =
                SLNode.<Item> listCopyWithTail( sequenceToCopy.head );
            this.head = (SLNode<Item>)result[0]; this.tail = (SLNode<Item>)result[1];
            if ( !sequenceToCopy.isCurrent() ) 
                this.precursor = this.cursor = null;
            else {//cursor is defined in sequenceToCopy and is at head
                this.precursor = null; this.cursor = head;
            } 
        }
    }

    /**
     * overrides Object class clone()
     *
     * @post.   returns a "deep" copy of this object at two levels<br>
     *  1) The underlying list and all attributes are "deep" copied
     *  2) The data in each node is "deep" copied if the data object supports
     *     clone(); otherwise, the data is "shallow" copied
     **/
    @Override
    @SuppressWarnings("unchecked")
    public SLinkedSequence<Item> clone() {
        SLinkedSequence<Item> result;

        try {
            //provides only a shallow copy of this object
            result = (SLinkedSequence<Item>) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("SLinkedSequence<Item> does not implement Cloneable");
        }
        //provides a deep copy
        result = new SLinkedSequence<Item>(this);
        return result;
    }



    /**
     * Accessor equals method that overrides equals of class Object.
     *
     * @param other a reference to another object to be compared with
     *
     * @return boolean whether other an SLinkedSequence is the same as this
     *
     * @post.   for any non-null reference value x, x.equals(x) returns true<br>
     *          for any non-null reference values x and y, x.equals(y) returns true
     *            if and only if y.equals(x) returns true<br>
     *          for any non-null reference values x, y and z, if x.equals(y) returns true
     *            and y.equals(z) returns true, then x.equals(z) should return true<br>
     *          for any non-null reference values x and y, multiple invocations of x.equals(y) 
     *            consistently return true or false provided neither x nor y is modified between
     *            invocations<br>
     *          for any non-null reference values x and y, if x.equals(y), then x.hashCode() == y.hashCode()<br>
     *          for any non-null reference x, x.equals(null) should return false<br>
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals( Object other ) {
        /* cannot be equal to nothing */
        if ( other == null ) return false;
        if ( !(other instanceof SLinkedSequence) ) return false;
        SLinkedSequence<Item> candidate = (SLinkedSequence<Item>) other;
        if ( this == candidate ) return true;
        /* cannot be equal if number of nodes differs */
        if ( candidate.used != this.used ) return false;
        /* cannot be equal if hashCodes differ */
        if ( candidate.hashCode() != this.hashCode() ) return false;


        //we now know that both lists have same number of nodes
        SLNode<Item> thisList = this.head;
        SLNode<Item> otherList = candidate.head;
        boolean isEqual = true;

        //must traverse both lists to check for a mis-match in data
        //while so doing, check for cursor mismatch
        // STUDENT COMPLETE HERE

        while (thisList != null && isEqual) {
            if (thisList.getData() != otherList.getData()) isEqual = false;
            else if (thisList == this.cursor && otherList != candidate.cursor) isEqual = false;
            else if (thisList != this.cursor && otherList == candidate.cursor) isEqual = false;
            else {
                thisList = thisList.getLink();
                otherList = otherList.getLink();
            }
        }

        return isEqual;
    }

    /**
     * Accessor hashCode that overrides hashCode of class Object.
     *
     * @post.  when invoked multiple times, hashCode must consistently return the same information
     *           provided no modification to the object occurs between invocations<br>
     *         if two objects are equal according to the equals method, then the hashCode method for
     *           these two objects MUST return the same value<br>
     *
     * @return int the unique hashcode for this object
     */
    @Override
    public int hashCode( ) {
        int hashValue = Objects.hash( this.used );

        SLNode<Item> listPtr = this.head;
        while ( listPtr != null ) {
            hashValue = 101 * hashValue + Objects.hash( listPtr.getData() );
            listPtr = listPtr.getLink();
        }

        return hashValue;
    }

    /**
     * Accessor toString() overrides toString() of class Object.
     *
     * @return String a displayable representation of the
     *         sequence including showing position of both
     *         the precursor (+) and the cursor (^).
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SLinkedSequence: ");
        sb.append(this.used);
        sb.append(" items");
        sb.append("==>[ ");
        if ( this.head == null ) {
            sb.append("]");
            return sb.toString();
        }
        SLNode<Item> ptr = this.head;
        if ( this.used > 0 ) {
            if ( this.precursor == ptr )
                sb.append("+");
            if ( this.cursor == ptr )
                sb.append("^");
            sb.append( ptr.getData() );
            ptr = ptr.getLink();
            while ( ptr != null ) {
                sb.append(", ");
                if ( this.precursor == ptr )
                    sb.append("+");
                if ( this.cursor == ptr )
                    sb.append("^");
                sb.append( ptr.getData() );
                ptr = ptr.getLink();
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * Accessor that returns number of items in the sequence.
     *
     * @return int used in list
     */
    public int size( ) {
        return this.used;
    }

    /**
     * Mutator that repositions the cursor to beginning of sequence.
     *
     * @post.   precursor set to null<br>
     *          cursor set to head
     */
    public void start( ) {
        /* valid even if list is empty */
        this.precursor = null;
        this.cursor = head;
    }

    /**
     * Mutator that moves the pre-cursor and cursor forward one position in the sequence.
     *
     * @pre.  this.cursor != null<br>
     *        if so throw IllegalStateException
     *
     * @post. precursor set to value of cusor before advance<br>
     *        cursor advanced to next node 
     **/
    public void advance() {
        if (this.cursor == null)
            throw new IllegalStateException("cannot advance the cursor");
        if ( this.cursor.getLink() == null ) 
            this.precursor = null;
        else
            this.precursor = this.cursor;
        this.cursor = this.cursor.getLink();
    }

    /**
     * Accessor that returns whether the cursor is within the sequence.
     * @return boolean true if cursor within the sequence; false otherwise
     *
     */
    public boolean isCurrent( ) {
        return this.cursor != null;
    }

    /**
     * Accessor that returns value of "current" item in the list if there is
     * a current item.
     *
     * @pre.  isCurrent() is true<br>
     *        otherwise throw IllegalStateException
     *
     * @return
     *        the data value stored in the current node
      **/
    public Item getCurrent( ) {
        if ( !this.isCurrent() )
            throw new IllegalStateException("cannot getCurrent when no current node");
        return this.cursor.getData();
    }

    /**
     * Mutator that adds a new item after the current item or at the tail if no current item
     *
     * @param
     *        newValue an int value to be added to sequence
     *
     * @post. 1) if list empty value becomes head and tail node of list<br>
     *        2) if this.isCurrent(), new node with newValue added after cursor<br>
     *        3) if not this.isCurrent(), new node with newValue added at end of list<br> 
     *        4) used incremented
     **/
    public void addAfter( Item newValue ) {
        if ( this.used == 0 ) { //handle #1 above
            /* adding new node to empty list */
            // STUDENT COMPLETE HERE

            head = tail = cursor = new SLNode<Item>(newValue);
        }
        else if ( !this.isCurrent() || cursor == tail ) { //handle #3 above
            /* adding new node at end of list */
            // STUDENT COMPLETE HERE

            cursor = new SLNode<Item>(newValue);
            tail.setLink(cursor);
            precursor = tail;
            tail = cursor;
        } else { //handle #2 above
            /* adding new node after cursor */
            // STUDENT COMPLETE HERE

            precursor = cursor;
            cursor = new SLNode<Item>(newValue, precursor.getLink());
            precursor.setLink(cursor);
        }
        this.used++;
    }

    /**
     * Mutator that adds a new item before the current item.
     *
     * @param  newValue
     *         an int value to be added to sequence
     * @post.  1) if list empty value becomes head and tail node of list<br>
     *         2) if this.isCurrent(), new node with newValue added before cursor<br>
     *         3) if not this.isCurrent(), new node with newValue added at beginning of list<br>
     *         4) used incremented
     **/
    public void addBefore( Item newValue ) {
        /* are we adding node to empty list */
        if ( this.used == 0 ){ //handle #1 above
            /* adding new node to empty list */
            // STUDENT COMPLETE HERE

            head = tail = cursor = new SLNode<Item>(newValue);
        }
        else if ( this.isCurrent() && ( this.cursor != this.head ) ) { //handle #2 above
            /* adding before a node that is not the head node */
            // STUDENT COMPLETE HERE

            cursor = new SLNode<Item>(newValue, cursor);
            precursor.setLink(cursor);
        }
        else {
            /* logically -- we are adding before head node irrespective of isCurrent() */
            // STUDENT COMPLETE HERE

            cursor = new SLNode<Item>(newValue, head);
            head = cursor;
        }
        this.used++;
    }

    /**
     * Mutator that removes the current item if such exists.
     *
     * @pre.    isCurrent() must be true<br>
     *          otherwise, throw IllegalStateException
     *
     * @post.   1) if removing head node, cursor advanced to next node<br>
     *          2) if removing tail node, precursor and cursor set to null<br>
     *          3) if removing an interior node, precursor references node following
     *             node to be removed and cursor advanced to node following precursor<br>
     *          4) used decremented
     *          5) if used == 0; then all other attributes set to null
     *
     * @throws  IllegalStateException if !isCurrent()
     *
     */
    public void removeCurrent() {
        if ( !this.isCurrent() )
            throw new IllegalStateException("cannot remove current node when there is no current node");
        SLNode<Item> trash = this.cursor;
        /* are we removing the head node */
        if ( trash == this.head ) {
            // STUDENT COMPLETE HERE
            
            cursor = head = head.getLink();
        /* are we removing the tail node */
        } else if ( trash == this.tail ) {
            // STUDENT COMPLETE HERE

            tail = precursor;
            tail.setLink(null);
            cursor = precursor = null;
        /* logically follows that we are removing an internal node */
        } else {
            // STUDENT COMPLETE HERE

            cursor = cursor.getLink();
            precursor.setLink(cursor);
        }
        // STUDENT COMPLETE HERE
        /* this to raise conscienceness about memory management */
        trash.setLink(null); trash = null;
        used--;
    }

    /**
     * Accessor that creates a new sequence formed from the concatenation
     * of this sequence with other sequence.
     *
     * @pre. other must NOT be null
     *
     * @param other 
     *        a non-null reference to a valid sequence
     *
     * @return a new SLinkedSequence&lt;Item&gt;
     *
     * @post. the newly created sequence is a concatenation of the other
     *        sequence onto this sequence
     *
     * @throws IllegalArgumentException if other is null
     *
     */
    public SLinkedSequence<Item> concatenation( SLinkedSequence<Item> other ) {
        if ( other == null )
            throw new IllegalArgumentException("other cannot be null");
        SLinkedSequence<Item> newSequence = null;

        // STUDENT WORK HERE
        // first check if other is empty; if so return a copy of this
        // making certain the copy is NOT current

        // next check if this is empty; if so return a copy of other
        // making certain the copy is NOT current

        // let newSequence be a copy of this and then add to it elements of other

        newSequence = new SLinkedSequence<Item>(this);

        newSequence.cursor = newSequence.tail;
        SLNode<Item> otherCursor = other.head;

        while (otherCursor != null) {
            newSequence.precursor = newSequence.cursor;
            newSequence.cursor = new SLNode<Item>(otherCursor.getData());
            newSequence.precursor.setLink(newSequence.cursor);
            otherCursor = otherCursor.getLink();
        }

        // be sure all attributes are properly updated including
        // making sequence NOT current

        newSequence.used += other.used;
        newSequence.tail = newSequence.cursor;
        newSequence.cursor = null;

        return newSequence;
    }


    /**
     * Returns a forward iterator.
     *
     * @return iterator&lt;Item&gt;
     *
     */
    public Iterator<Item> iterator() {
        return new SequenceIterator();
    }

    public ListIterator<Item> listIterator() {
        return new SequenceIterator();
    }


    /* Inner class to provide iterator support */
    private class SequenceIterator implements ListIterator<Item> {

        private SLNode<Item> iterCursor;
        private int index;

        public SequenceIterator() {
            iterCursor = SLinkedSequence.this.head;
            index = 0;
        }

        public boolean hasNext() {
            return iterCursor != null;
        }

        public Item next() throws NoSuchElementException {
            if ( !hasNext() )
                throw new 
                    NoSuchElementException("iterator went past end of sequence");
            Item retValue = iterCursor.getData();
            iterCursor = iterCursor.getLink();
            ++index;
            return retValue;
        }

        public void remove () throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        public int nextIndex() {
            if ( hasNext() ) return index;
            return SLinkedSequence.this.size();
        }

        public boolean hasPrevious() {
            throw new UnsupportedOperationException();
        }

        public Item previous() throws NoSuchElementException {
            throw new NoSuchElementException();
        }

        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        public void add(Item item) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        public void set(Item item) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

}
