// File: SLNode.java 

/******************************************************************************
* A SLNode&lt;Item&gt; provides a "generic" node for a linked list with 
* a reference to Item data in each node.
*
* @note.
*   Lists of nodes can be made of any length, limited only by the amount of
*   free memory in the heap. But beyond Integer.MAX_VALUE (2,147,483,647),
*   the answer from listLength is incorrect because of arithmetic
*   overflow. 
*
* @author H. Paul Haiduk based on code by Michael Main
* @version Fall 2020
*
******************************************************************************/
public class SLNode<Item> implements Cloneable {
   // Invariant of the SLNode<Item> class:
   //   1. The node's Item data reference is in the instance variable data.
   //      NOTE:  data is a reference variable NOT a primitive value
   //   2. For the final node of a list, the link part is null.
   //      Otherwise, the link part is a reference to the
   //      next node of the list.
   private Item         data;
   private SLNode<Item> link;   

   /**
   * Initialize a node with a specified initial data and link to the next
   * node --  Note that the initialLink may be the null reference, 
   * which indicates that the new node has nothing after it.
   * @param initialData
   *   the initial data of this new node
   * @param initialLink
   *   a reference to the node after this new node--this reference may be null
   *   to indicate that there is no node after this new node.
   * @post.
   *   This node contains the specified data and link to the next node.
   **/   
   public SLNode(Item initialData, SLNode<Item> initialLink) {
      this.data = initialData;
      this.link = initialLink;
   }

   /**
    * Default constructor
    *
    * @post. 
    *   The node exists with null references for both data and link
    **/
   public SLNode( ) {
       this(null, null);
   }

   /**
    * Constructor for initial reference to data and null for link
    *
    * @param initialData
    *   a reference to the data for this node
    *
    * @post.
    *   This node is initialized with reference to data and null for link
    **/
   public SLNode(Item initialData) {
       this(initialData, null);
   }

   /**
    * A "copy" constructor
    *
    * @param source a reference to a SLNode&lt;Item&gt; to be copied
    *
    * @pre.
    *   parameter source must not be null
    *
    * @post.
    *   The new copy contains a deep copy of its data if the data
    *   object supports the clone() method; othewise the data is 
    *   "shallow" copied
    *
    * @throws
    *   NullPointerException if source is null
    **/
   public SLNode(SLNode<Item> source) {
       this.link = source.link;
       this.data = source.dataCopy();
   }

   /**
   * Generate a copy of this node.
   * @post.
   *    x.clone() != x <br>
   *    x.clone().getClass() == x.getClass() <br>
   *    x.clone().equals( x ) <br>
   *
   * @return
   *   The return value is a copy of this node. Subsequent changes to the
   *   copy will not affect the original, nor vice versa.  If, however, 
   *   the object referenced by the node does not have a "deep" copying
   *   clone, then there will be a shallow copy of the object.
   *
   **/
   @SuppressWarnings("unchecked")
   @Override
   public SLNode<Item> clone( ) {  // Clone an SLNode<Item> object.
      SLNode<Item> answer;

      try {
         answer = (SLNode<Item>) super.clone( );
         answer.data = this.dataCopy( );
         return answer;
      }
      catch (CloneNotSupportedException e) {
         // This exception should not occur. But if it does, it would probably
         // indicate a programming error that made super.clone unavailable.
         // The most common error would be forgetting the "Implements Cloneable"
         // clause at the start of this class.
         throw new RuntimeException ("This class does not implement Cloneable");
      }
   }

   //copy method for data
   @SuppressWarnings({"unchecked", "rawtypes"})
   private Item dataCopy( ) {
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
   * Modification method to set the data in this node.   
   *
   * @param newData
   *   a reference to the new data to place in this node
   * @post.
   *   The data of this node has been set to newData.
   **/
   public void setData(Item newData)   {
      this.data = newData;
   }                                                               
   
   
   /**
   * Accessor method to get a copy of the data from this node.   
   *
   * @return
   *   the data from this node
   **/
   public Item getData( ) {
      return dataCopy();
   }
   
   
   /**
   * Modification method to set the link to the next node after this node.
   *
   * @param newLink
   *   a reference to the node that should appear after this node in the linked
   *   list (or the null reference if there is no node after this node)
   * @post.
   *   The link to the node after this node has been set to newLink.
   *   Any other node (that used to be in this link) is no longer connected to
   *   this node.
   **/
   public void setLink(SLNode<Item> newLink) {                    
      this.link = newLink;
   }

   
   /**
   * Accessor method to get a reference to the next node after this node. 
   *
   * @return
   *   a reference to the node after this node (or the null reference if there
   *   is nothing after this node)
   **/
   public SLNode<Item> getLink( ) {
      return this.link;                                               
   } 
    
   /**
   * Modification method to remove the node after this node.   
   *
   * @pre.
   *   This node must not be the tail node of the list.
   * @post.
   *   The node after this node has been removed from the linked list.
   *   If there were further nodes after that one, they are still
   *   present on the list.
   * @exception NullPointerException
   *   Indicates that this was the tail node of the list, so there is nothing
   *   after it to remove.
   **/
   public void removeNodeAfter( ) {
      if ( this.link == null )
          throw new IllegalStateException("Cannot remove non-existent node");
      this.link = this.link.link;
   }          
   
   /**
   * Modification method to add a new node after this node.   
   *
   * @param item
   *   the data to place in the new node
   * @post.
   *   A new node has been created and placed after this node.
   *   The data for the new node is item. Any other nodes
   *   that used to be after this node are now after the new node.
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for a new 
   *   SLNode&lt;Item&gt;. 
   **/
   public void addNodeAfter(Item item) {
      this.link = new SLNode<Item>(item, this.link);
   }          
   
    
   /**
   * Copy a list given a reference to its head.
   *
   * @param source
   *   the head of a linked list that will be copied (which may be
   *   an empty list source is null)
   * @return
   *   The method has made a copy of the linked list starting at 
   *   source. The return value is the head reference for the
   *   copy. 
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new list.   
   **/ 
   public static <Item> SLNode<Item> listCopy(SLNode<Item> source) {
      SLNode<Item> copyHead;
      SLNode<Item> copyTail;
      
      // Handle the special case of the empty list.
      if (source == null)
         return null;
         
      // Make the first node for the newly created list.
      copyHead = new SLNode<Item>(source.dataCopy(), null);
      copyTail = copyHead;
      
      // Make the rest of the nodes for the newly created list.
      while (source.link != null) {
         source = source.link;
         copyTail.addNodeAfter(source.dataCopy());
         copyTail = copyTail.link;
      }
 
      // Return the head reference for the new list.
      return copyHead;
   }
   
   
   /**
   * Copy a list, returning both a head and tail reference for the copy.
   *
   * @param source
   *   the head of a linked list that will be copied (which may be
   *   an empty list if source is null)
   * @return
   *   The method has made a copy of the linked list starting at 
   *   source.  The return value is an
   *   array where the [0] element is a head reference for the copy and the [1]
   *   element is a tail reference for the copy.
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new list.   
   **/
   public static <Item> Object[ ] listCopyWithTail(SLNode<Item> source) {
      SLNode<Item> copyHead;
      SLNode<Item> copyTail;
      Object [ ] answer = new Object[2];

      // Handle the special case of the empty list.   
      if (source == null)
         return answer; // The answer has two null references .
      
      // Make the first node for the newly created list.
      copyHead = new SLNode<Item>(source.dataCopy(), null);
      copyTail = copyHead;
      
      // Make the rest of the nodes for the newly created list.
      while (source.link != null) {
         source = source.link;
         copyTail.addNodeAfter(source.dataCopy());
         copyTail = copyTail.link;
      }
      
      // Return the head and tail references.
      answer[0] = copyHead;
      answer[1] = copyTail;
      return answer;
   }
   
   
   /**
   * Compute the number of nodes in a linked list.
   *
   * @param head
   *   the head reference for a linked list (which may be an empty list
   *   with a null head)
   * @return
   *   the number of nodes in the list with the given head 
   * @note.
   *   A wrong answer occurs for lists longer than Integer.MAX_VALUE.     
   **/   
   public static <Item> int listLength(SLNode<Item> head) {
      SLNode<Item> cursor;
      int answer;
      
      answer = 0;
      for (cursor = head; cursor != null; cursor = cursor.link)
         answer++;
        
      return answer;
   }
   

   /**
   * Copy part of a list, providing a head and tail reference for the new copy. 
   * @param start
   *        reference to node at head of list to be copied
   * @param end
   *        reference to node at tail of list to be copied
   *
   * @pre.
   *   start and end are non-null references to nodes
   *   on the same linked list,
   *   with the start node at or before the end node. 
   *
   * @return
   *   The method has made a copy of the part of a linked list, from the
   *   specified start node to the specified end node. The return value is an
   *   array where the [0] component is a head reference for the copy and the
   *   [1] component is a tail reference for the copy.
   *
   * @exception IllegalArgumentException
   *   Indicates that start and end are not references
   *   to nodes on the same list.
   *
   * @exception NullPointerException
   *   Indicates that start or end is null.
   *
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new list.    
   **/   
   public static <Item> Object[ ] listPart(SLNode<Item> start, SLNode<Item> end) {
      SLNode<Item> copyHead;
      SLNode<Item> copyTail;
      SLNode<Item> cursor;
      Object[ ] answer = new Object[2];

      if ( start == null || end == null )
          throw new NullPointerException("neither start nor end may be null");
      
      // Make the first node for the newly created list. Notice that this will
      // cause a NullPointerException if start is null.
      copyHead = new SLNode<Item>(start.dataCopy(), null);
      copyTail = copyHead;
      cursor = start;
      
      // Make the rest of the nodes for the newly created list.
      while (cursor != end) {
         cursor = cursor.link;
         if (cursor == null) 
             throw new IllegalArgumentException("end node was not found on the list");
         copyTail.addNodeAfter(cursor.dataCopy());
         copyTail = copyTail.link;
      }
      
      // Return the head and tail references
      answer[0] = copyHead;
      answer[1] = copyTail;
      return answer;
   }        
   
   
   /**
   * Find a node at a specified position in a linked list.
   *
   * @param head
   *   the head reference for a linked list (which may be an empty list in
   *   which case the head is null)
   * @param position
   *   a node number
   * @pre.
   *   position %gt; 0.
   * @return
   *   The return value is a reference to the node at the specified position in
   *   the list. (The head node is position 1, the next node is position 2, and
   *   so on.) If there is no such position (because the list is too short),
   *   then the null reference is returned.
   * @exception IllegalArgumentException
   *   Indicates that position is not positive.    
   **/   
   public static <Item> SLNode<Item> listPosition(SLNode<Item> head, int position) {
      
      if (position <= 0)
           throw new IllegalArgumentException("position is not strictly positive");
      
      SLNode<Item> cursor = head;
      for (int i = 1; (i < position) && (cursor != null); i++)
         cursor = cursor.link;

      return cursor;
   }


   /**
   * Search for a particular piece of data in a linked list.
   * @param <Item>
   *    The type parameter for node
   * @param head
   *   the head reference for a linked list (which may be an empty list in
   *   which case the head is null)
   * @param target
   *   a piece of data to search for
   * @return
   *   The return value is a reference to the first node that contains the
   *   specified target. If there is no such node, the null reference is 
   *   returned.     
   **/   
   public static <Item> SLNode<Item> listSearch(SLNode<Item> head, Item target) {
      SLNode<Item> cursor;
     /* 
      for (cursor = head; cursor != null; cursor = cursor.link)
         if (target.equals(cursor.data))
            return cursor;
      return cursor;
     */
      boolean found = false;
      cursor = head;
      while ( !found && cursor != null ) {
          if (target.equals(cursor.data))
              found = true;
          else cursor = cursor.link;
      }
        
      return cursor;
   }

   /**
    * Render contents of node in human readable form
    *
    * @note.  This method overrides the toString() of super 
    *         class Object
    *
    **/
   @Override
   public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append("SLNode [ ");
       sb.append( this.data.toString() );
       sb.append(", ");
       sb.append( this.link == null ? "null" : "-->" );
       sb.append(" ] ");
       return sb.toString();
   }

   /**
    * An method to display an entire list using the toString()
    * method for displaying a single node.
    *
    * @note.  A rendering of the nodes in a linked-list.
    *
    **/
   public static <Item> void displayList( SLNode<Item> listHead ) {
       SLNode<Item> ptr = listHead;
       while ( ptr != null ) {
           System.out.printf("%s ", ptr);
           ptr = ptr.getLink();
       }
   }

   /**
    * Recursively reverse the order of nodes in a linked-list
    *
    **/
   public static <Item> SLNode<Item> reverseList( SLNode<Item> head ) {
       if ( head == null || head.getLink() == null ) 
           return head;

       //list has at least two nodes in it
       //  so get second node
       SLNode<Item> second = head.getLink();

       //set head node's link to null
       head.setLink( null );

       SLNode<Item> remainderOfList = reverseList( second );
       second.setLink( head );

       return remainderOfList;
   }



   public static void main( String [] args ) {

       SLNode<Integer> head;
       SLNode<Integer> tail;
       head = tail = new SLNode<>(0);
       System.out.printf("head node is: %s\n", head);

       for (int number = 2; number < 12; number += 2) {
           tail.setLink(new SLNode<>(number, tail.getLink()));
           tail = tail.getLink();
       }

       displayList( head );
       System.out.println();

       SLNode<Integer> temp = head;
       head = reverseList( head );
       tail = temp;
       displayList( head );
       System.out.println();
   }

}
           
