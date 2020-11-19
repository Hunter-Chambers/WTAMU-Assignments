// File: LinkedQueue.java from the package collections
//
// NOTE: to edit this file, ensure that your working directory 
//       is one level above the directory collections, e.g.
//       gvim collections/LinkedQueue.java

package collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import collections.SLNode;

/******************************************************************************
* A <CODE>LinkedQueue</CODE> is a queue of references to generic Item objects.
*
* <dl><dt><b>Limitations:</b><dd>
*   Beyond <CODE>Int.MAX_VALUE</CODE> items, <CODE>size</CODE> is wrong. 
* </dl>
*
* @note. This code is based on code provided by Michael Main and is updated
* to be consistent with Java 8 collections; this class does NOT implement
* the equals method.
*
* @version November, 2019
*
******************************************************************************/
public class LinkedQueue<Item> implements Cloneable, Iterable<Item> {
   // Invariant of the LinkedQueue class:
   //   1. The number of items in the queue is stored in the instance variable
   //      used.
   //   2. The items in the queue are stored in a linked list, with the front 
   //      of the queue stored at the head node, and the rear of the queue at 
   //      the final node.
   //   3. For a non-empty queue, the instance variable front is the head 
   //      reference of the linked list of items and the instance variable rear
   //      is the tail reference of the linked list. 
   //   4. For an empty queue, both front and rear are the null reference.
   private int  used;
   private SLNode<Item> front;
   private SLNode<Item> rear;


   /**
   * Initialize an empty queue.
   * @post.
   *   This queue is created and is empty.
   **/   
   public LinkedQueue( ) {
      this.used  = 0;
      this.front = null;
      this.rear  = null;
   }

    /**
     * Copy Constructor
     *
     * @param QueueToCopy
     *        a reference to another queue that is to be copied
     * @pre.  QueueToCopy != null<br>
     *        NullPointerException thrown if it is null
     * @post. LinkedQueue&lt;Item&gt; properly initialized as a deep copy<br>
     *        of QueueToCopy
     * @note. The newly created object is a "deep" copy of this object at two levels<br>
     *  1) The underlying list and all attributes are "deep" copied<br>
     *  2) The data in each node is "deep" copied if the data object supports
     *     clone(); otherwise, the data is "shallow" copied
     *
     **/
   @SuppressWarnings("unchecked")
   public LinkedQueue( LinkedQueue<Item> QueueToCopy ) {
       if ( QueueToCopy == null )
           throw new NullPointerException("Cannot copy a non-queue");
       /* are we copying empty queue */
       if ( QueueToCopy.used == 0 ) {
           this.front = this.rear = null;
           this.used = 0;
       }
       else {
           Object[] result = 
               SLNode.<Item>listCopyWithTail( QueueToCopy.front );
           this.front = (SLNode<Item>) result[0];
           this.rear  = (SLNode<Item>) result[1];
       }
   }

   
   /**
   * Generate a copy of this queue.
   * @return
   *   The return value is a copy of this queue. Subsequent changes to the
   *   copy will not affect the original, nor vice versa
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for creating the clone.
   **/ 
   @Override
   @SuppressWarnings("unchecked")
   public LinkedQueue<Item> clone( ) {  
      // Clone this LinkedQueue.
      LinkedQueue<Item> answer;
      
      try {
         answer = (LinkedQueue<Item>) super.clone( );
      }
      catch (CloneNotSupportedException e) { 
         throw new 
             RuntimeException ("This class does not implement Cloneable");
      }

      // provides a deep copy
      answer = new LinkedQueue<Item>(this);
      
      return answer;
   }        
 
   /**
   * Get the front item, removing it from this queue.
   * @pre. This queue is not empty.
   * @post.
   *   The return value is the front item of this queue, and the item has
   *   been removed.
   * @return the item stored in the head node of the linked list
   * @exception NoSuchElementException
   *   Indicates that this queue is empty.
   **/    
   public Item remove( ) {
      Item answer = null;

      if (used == 0)
         throw new NoSuchElementException("Queue underflow");

      //STUDENT 
      //    retrieve value at front of queue for return
      //    update front, used, and rear if used down to 0

      return answer;
   }

   /**
    * Retrieves, but does not remove, the item stored at the head/front of the queue.
    *
    * @return a reference to item stored at head/front of the queue
    *
    * @throws NoSuchElementException 
    *   if the queue is empty
    **/
   public Item element( ) {
       return this.front.getData();
   }
   
   /**
   * Place a new a new item at end of the queue. 
   * @param item
   *   the item to be placed at end of the queue.
   * @post.
   *   The item has been appended onto this queue.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for increasing the queue's capacity.
   * @note.
   *   An attempt to increase the capacity beyond
   *   <CODE>Integer.MAX_VALUE</CODE> will cause the queue to fail with an
   *   arithmetic overflow.
   **/    
   public void add(Item item) {
       //STUDENT
       //   Must deal with possibility of adding an item to an empty queue
       //   setting front and rear appropriately.
       //   Otherwise, add the item to rear of queue and update rear
       used++;
   }
              

   /**
   * Determine whether this queue is empty.
   * @return
   *   <CODE>true</CODE> if this queue is empty;
   *   <CODE>false</CODE> otherwise. 
   **/
   public boolean isEmpty( ) {
      return (used == 0);
   }


   /**
   * Accessor method to determine the number of items in this queue.
   * @return
   *   the number of items in this queue
   **/ 
   public int size( ) {
      return used;
   }

   /**
    * Supports iteration over all elements of the queue
    * @return QueueIterator
    *
    **/
   public Iterator<Item> iterator( ) {
       return new QueueIterator<Item>();
   }

   /** QueueIterator provides a "forward" iterator over all items in the queue */
   private class QueueIterator<Item> implements Iterator<Item> {
       private SLNode<Item> iterCursor;

       @SuppressWarnings("unchecked")
       public QueueIterator( ) {
           iterCursor = (SLNode<Item>)LinkedQueue.this.front;
       }

       public boolean hasNext( ) {
           return iterCursor != null;
       }

       public Item next( ) throws NoSuchElementException {
           if ( !hasNext() )
               throw new
                   NoSuchElementException("queue empty or iterator went past end of queue");
           Item retValue = iterCursor.getData();
           iterCursor = iterCursor.getLink();
           return retValue;
       }

       public void remove( ) throws UnsupportedOperationException {
           throw new UnsupportedOperationException();
       }
   }

}
