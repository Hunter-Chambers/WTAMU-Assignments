/******************************************************************************
* A DLNode&lt;Item&gt; provides a generic node for a doubly-linked list;  each node 
* contains a piece of data (which is a reference to an Item object) and two links;
* One link is to the previous node if it exists; otherwise, it is null;
* The other link is to the next node if it exists; otherwise, it is null; 
* The reference to the Item object can be null.
*
* @note.
*   Lists of nodes can be made of any length, limited only by the amount of
*   free memory in the heap. But beyond Integer.MAX_VALUE (2,147,483,647),
*   the answer from listLength is incorrect because of arithmetic
*   overflow. 
******************************************************************************/
public class DLNode<Item> {
   // Invariant of the DLNode class:
   //   1. Each node has one reference to an Item Object, stored in the instance
   //      variable data.
   //   2. If there is no previous node for a given node, the previous link is null
   //   3. If there is no next node for a given node, the next link is null
   //      Otherwise, previous is a link to the previous node and next is a link
   //      to the next node of the list.
   private DLNode<Item> previous;   
   private Item data;
   private DLNode<Item> next;

   private DLNode( ) {
       this.previous = this.next = null;
       this.data = null;
   }

   /**
   * Initialize a node with a specified initial data and links to the next
   * node and previous nodes -- Note that these initial links may be the null reference 
   * @param initialData
   *   the initial data of this new node -- the reference may be null
   * @param initialPrevious
   *   a reference to the node previous to this new node--this reference may be null
   *   to indicate that there is no node previous to this new node.
   * @param initialNext
   *   a reference to the node after this new node--this reference may be null
   *   to indicate that there is no node after this new node.
   * @post.
   *   This node contains the specified data and links 
   **/   
   public DLNode(DLNode<Item> initialPrevious,
                 Item initialData, 
                 DLNode<Item> initialNext) {
      this.previous = initialPrevious;
      this.data = initialData;
      this.next = initialNext;
   }

   /**
   * Accessor method to get the data from this node.   
   * @return
   *   a reference to the data from this node
   **/
   public Item getData( ) {
      return this.data;
   }

   /**
   * Modification method to set the data in this node.   
   * @param newData
   *   the new data to place in this node
   * @post.
   *   The data of this node has been set to newData.
   *   This data is allowed to be null.
   **/
   public void setData(Item newData)   {
      this.data = newData;
   }                                                               

   /**
   * Accessor method to get a reference to the previous node before this node. 
   * @return
   *   a reference to the node before this node (or the null reference if there
   *   is nothing before this node)
   **/
   public DLNode<Item> getPrevious( ) {
      return this.previous;                                               
   } 
   
   /**
   * Modification method to set the link to the node before this node.
   * @param newLink
   *   a reference to the node that should appear before this node in the linked
   *   list (or the null reference if there is no node before this node)
   * @post.
   *   The link to the node after this node has been set to newLink.
   *   Any other node (that used to be in this link) is no longer connected to
   *   this node.
   **/
   public void setPrevious(DLNode<Item> newLink) {                    
      this.previous = newLink;
   }

   /**
   * Accessor method to get a reference to the next node after this node. 
   * @return
   *   a reference to the node after this node (or the null reference if there
   *   is nothing after this node)
   **/
   public DLNode<Item> getNext( ) {
      return this.next;                                               
   } 
   
   /**
   * Modification method to set the link to the next node after this node.
   * @param newLink
   *   a reference to the node that should appear after this node in the linked
   *   list (or the null reference if there is no node after this node)
   * @post.
   *   The link to the node after this node has been set to newLink.
   *   Any other node (that used to be in this link) is no longer connected to
   *   this node.
   **/
   public void setNext(DLNode<Item> newLink) {                    
      this.next = newLink;
   }

   
   /**
   * Modification method to add a new node before this node.   
   * @param element
   *   the data to place in the new node
   * @post.
   *   A new node has been created and placed before this node.
   *   The data for the new node is element. Any other nodes
   *   that used to be before this node are now before the new node.
   * @note. May throw OutOfMemoryError if there is insufficient memory for a new 
   *   DLNode. 
   **/
   public void addBefore(Item element) {
       DLNode<Item> newNode = new DLNode<Item>( this.previous, element, this );
       if ( this.previous != null )
           this.previous.next = newNode;
       this.previous = newNode;
   }

   /**
   * Modification method to add a new node after this node.   
   * @param element
   *   the data to place in the new node
   * @post.
   *   A new node has been created and placed after this node.
   *   The data for the new node is element. Any other nodes
   *   that used to be after this node are now after the new node.
   * @note. May throw OutOfMemoryError if there is insufficient memory for a new 
   *   DLNode. 
   **/
   public void addAfter(Item element) {
       DLNode<Item> newNode = new DLNode<Item>( this, element, this.next );
       if ( this.next != null )
           this.next.previous = newNode;
       this.next = newNode;
   }          

   /**
   * Modification method to remove the node before this node.   
   * @pre.
   *   This node must not be the head node of the list.
   * @post.
   *   The node before this node has been removed from the linked list.
   *   If there were further nodes before that one, they are still
   *   present on the list.
   * @exception IllegalArgumentException
   *   Indicates that this was the head node of the list, so there is nothing
   *   before it to remove.
   **/
   public void removeBefore( )   {
      if ( this.previous == null )
          throw new IllegalArgumentException("No node before this node");
      DLNode<Item> trash = this.previous;
      if ( trash.previous != null ) trash.previous.next = this;
      this.previous = trash.previous;
      //next steps are designed to assist in memory management
      trash.data = null;
      trash.previous = trash.next = null;
      trash = null;
   }          
   
   /**
   * Modification method to remove the node after this node.   
   * @pre.
   *   This node must not be the tail node of the list.
   * @post.
   *   The node after this node has been removed from the linked list.
   *   If there were further nodes after that one, they are still
   *   present on the list.
   * @exception IllegalArgumentException
   *   Indicates that this was the tail node of the list, so there is nothing
   *   after it to remove.
   **/
   public void removeAfter( )   {
      if ( this.next == null )
          throw new IllegalArgumentException("No node after");
      DLNode<Item> trash = this.next;
      if ( trash.next != null ) trash.next.previous = this;
      this.next = trash.next;
      //next steps are designed to assist in memory management
      trash.data = null;
      trash.previous = trash.next = null;
      trash = null;
   }          
    
   //copy method for data
   @SuppressWarnings({"unchecked", "rawtypes"})
   public Item dataCopy( ) {
        java.lang.Class dataClass;
        java.lang.reflect.Method  data_clone_method;
        try { //try to retrieve a deep copy via clone of this.data
            dataClass = this.data.getClass();
            data_clone_method = dataClass.getMethod("clone", new Class[0] );
            Object temp = data_clone_method.invoke( this.data, new Object[0] );
            //System.out.printf("Returning a deep copy\n");
            return (Item) temp;
        }
        catch (Exception e) {
            //the clone method for data wasn't available, so we have to accept
            //the shallow copy of the reference only
            //System.out.printf("Returning a shallow copy\n");
            return this.data;
        }
    }
 
   /**
   * Copy a list.
   * @param <Item>
   *    placeholder for actual type of elements in list
   * @param source
   *   the head of a linked list that will be copied (which may be
   *   an empty list if source is null)
   * @return
   *   The method has made a copy of the linked list starting at 
   *   source. The return value is the head reference for the
   *   copy. 
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new list.   
   **/ 
   public static <Item> DLNode<Item> listCopy(DLNode<Item> source) {
      DLNode<Item> copyHead;
      DLNode<Item> copyTail;
      
      // Handle the special case of the empty list.
      if (source == null)
         return null;
         
      // Make the first node for the newly created list.
      copyHead = new DLNode<Item>(null, source.dataCopy(), null);
      copyTail = copyHead;
      
      // Make the rest of the nodes for the newly created list.
      while (source.next != null) {
         source = source.next;
         copyTail.addAfter(source.dataCopy());
         copyTail = copyTail.next;
      }
 
      // Return the head reference for the new list.
      return copyHead;
   }
   
   
   /**
   * Copy a list, returning both a head and tail reference for the copy.
   * @param <Item>
   *    placeholder for actual type of elements in list
   * @param source
   *   the head of a linked list that will be copied (which may be
   *   an empty list in where source is null)
   * @return
   *   The method has made a copy of the linked list starting at 
   *   source.  The return value is an array where the [0] element is a head 
   *   reference for the copy and the [1] element is a tail reference for the copy.
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new list.   
   **/
   public static <Item> Object[ ] listCopyWithTail(DLNode<Item> source) {
      DLNode<Item> copyHead;
      DLNode<Item> copyTail;
      Object[ ] answer = new Object[2];
     
      // Handle the special case of the empty list.   
      if (source == null)
         return answer; // The answer has two null references .
      
      // Make the first node for the newly created list.
      copyHead = new DLNode<Item>(null, source.dataCopy(), null);
      copyTail = copyHead;
      
      // Make the rest of the nodes for the newly created list.
      while (source.next != null) {
         source = source.next;
         copyTail.addAfter( source.dataCopy() );
         copyTail = copyTail.next;
      }
      
      // Return the head and tail references.
      answer[0] = copyHead;
      answer[1] = copyTail;
      return answer;
   }
   

   /**
   * Copy part of a list, providing a head and tail reference for the new copy. 
   * @param <Item>
   *    placeholder for actual type of elements in list
   * @param start
   *   reference to head node of a doubly-linked list
   * @param end
   *    reference to end node of a doubly-linked list
   * @pre.
   *   start and end are non-null references to nodes on the same 
   *   linked list, with the start node at or before the end node. 
   * @return
   *   The method has made a copy of the part of a linked list, from the
   *   specified start node to the specified end node. The return value is an
   *   array where the [0] component is a head reference for the copy and the
   *   [1] component is a tail reference for the copy.
   * @exception IllegalArgumentException
   *   Indicates that start and end do not satisfy
   *   the precondition.
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new list.    
   **/   
   public static <Item> Object[ ] listPart(DLNode<Item> start, DLNode<Item> end) {
      DLNode<Item> copyHead;
      DLNode<Item> copyTail;
      DLNode<Item> cursor;
      Object[ ] answer = new Object[2];
      
      // Check for illegal null at start or end.
      if (start == null)
         throw new IllegalArgumentException("start is null");      
      if (end == null)
         throw new IllegalArgumentException("end is null");
      
      // Make the first node for the newly created list.
      copyHead = new DLNode<Item>(null, start.dataCopy(), null);
      copyTail = copyHead;
      cursor = start;
      
      // Make the rest of the nodes for the newly created list.
      while (cursor != end) {
         cursor = cursor.next;
         if (cursor == null)
            throw 
                new IllegalArgumentException ("end node was not found on the list");
         copyTail.addAfter( cursor.dataCopy() );
         copyTail = copyTail.next;
      }
      
      // Return the head and tail references
      answer[0] = copyHead;
      answer[1] = copyTail;
      return answer;
   }        
   
   
   /**
   * Find a node at a specified position in a linked list.
   * @param <Item>
   *    placeholder for actual type of elements in list
   * @param head
   *   the head reference for a linked list (which may be an empty list in
   *   which case the head is null)
   * @param position
   *   a node number
   * @pre.
   *   position &gt; 0.
   * @return
   *   The return value is a reference to the node at the specified position in
   *   the list. (The head node is position 1, the next node is position 2, and
   *   so on.) If there is no such position (because the list is too short),
   *   then the null reference is returned.
   * @exception IllegalArgumentException
   *   Indicates that position is zero or negative   
   **/   
   public static <Item> DLNode<Item> listPosition(DLNode<Item> head, int position) {
      DLNode<Item> cursor;
      int i;
      
      if (position <= 0)
           throw new IllegalArgumentException("position is zero or less");
      
      cursor = head;
      for (i = 1; (i < position) && (cursor != null); i++)
         cursor = cursor.next;

      return cursor;
   }

   /**
   * Search for a particular piece of data in a linked list.
   * @param <Item>
   *    placeholder for actual type of elements in list
   * @param head
   *   the head reference for a linked list (which may be an empty list in
   *   which case the head is null)
   * @param target
   *   a target to search for
   * @return
   *   The return value is a reference to the first node that contains the
   *   specified target. If the target is non-null, then the
   *   target.equals method is used to find such a node.
   *   The target may also be null, in which case the return value is a
   *   reference to the first node that contains a null reference for its
   *   data. If there is no node that contains the target, then the null
   *   reference is returned.     
   **/   
   public static <Item> DLNode<Item> listSearch(DLNode<Item> head, Item target) {
      DLNode<Item> cursor;
      
      if (target == null) {  
         // Search for a node in which the data is the null reference.
         for (cursor = head; cursor != null; cursor = cursor.next)
            if (cursor.data == null)
               return cursor;
      }
      else {  
         // Search for a node that contains the non-null target.
         for (cursor = head; cursor != null; cursor = cursor.next)
            if (target.equals(cursor.data))
               return cursor;
      }
        
      return null;
   }                                           

}
           
