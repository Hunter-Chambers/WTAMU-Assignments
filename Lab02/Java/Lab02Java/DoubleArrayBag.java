// File: DoubleArrayBag.java 

import java.util.Objects;
import java.util.Arrays;

/******************************************************************************
* An DoubleArrayBag is an unordered collection of double numbers and in which
* the same number may appear multiple times.
*
* @note.
*   (1) A bag's capacity cannot exceed the maximum MAX_CAPACITY
*   <p>
*   (2) Because of the slow linear algorithms of this
*       class, large bags will have poor performance.
*
* @author
*    H. Paul Haiduk with credit given to Michael Main
*
* @version
*    September 17, 2018
*
******************************************************************************/
public class DoubleArrayBag implements Cloneable  {
   // Invariant of the DoubleArrayBag class:
   //   1. The number of elements in the bag is in the instance variable 
   //      used, which is no more than data.length.
   //   2. For an empty bag, we do not care what is stored in any of data;
   //      for a non-empty bag, the elements in the bag are stored in data[0]
   //      through data[used-1], and we don't care what's in the
   //      rest of data.  
   //   3. Capacity of the bag is given by data.length.

   public static final int MAX_CAPACITY = 4000;
   private double[ ] data;
   private int used; 
   
   /**
   * Initialize an empty bag
   * @post.
   *   This bag is empty and has a capacity of MAX_CAPACITY.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for allocating the array 
   **/   
   public DoubleArrayBag( ) {
      used = 0;
      data = new double[MAX_CAPACITY];
   }


   /**
    * Copy Constructor that creates a new bag as an exact copy of source
    * @param source
    *   reference to bag that is to be copied
    * @exception NullPointerException
    *   occurs when source is null
    * @post.
    *   This bag is a copy of source and has a capacity of
    *   MAX_CAPACITY
    **/
   public DoubleArrayBag( DoubleArrayBag source ) {
       if ( source == null ) {
           throw new NullPointerException("source must not be null");
       }

       //Appropriately update this.used and allocate space for data.
       //Copy over only the used data from source into this.data. 
       // STUDENT IMPLEMENTATION OF ABOVE HERE
   }

     
   /**
   * Generate a copy of this bag.
   * @post.
   *    x.clone() != x
   *    x.clone().getClass() == x.getClass()
   *    x.clone().equals( x )
   *
   * @return
   *   The return value is a copy of this bag. Subsequent changes to the
   *   copy will not affect the original, nor vice versa.
   *
   **/ 
   public DoubleArrayBag clone( ) {  // Clone an DoubleArrayBag object.
      DoubleArrayBag answer;
      /*
       * By convention, the object returned by this method should be independent of this
       * object (which is being cloned). To achieve this independence, it may be necessary
       * to modify one or more fields of the object returned by super.clone before returning
       * it. Typically, this means copying any mutable objects that comprise the internal 
       * "deep structure" of the object being cloned and replacing the references to these 
       * objects with references to the copies. If a class contains only primitive fields
       * or references to immutable objects, then it is usually the case that no fields 
       * in the object returned by super.clone need to be modified. 
       */
      
      try {
         answer = (DoubleArrayBag) super.clone( ); //copies primitive attributes
         answer.data = data.clone( ); //OK since elements of data are primitives
         return answer;
      }
      catch (CloneNotSupportedException e) {  
         // This exception should not occur. But if it does, it would probably
         // indicate a programming error that made super.clone unavailable.
         // The most common error would be forgetting the "implements Cloneable"
         // clause at the start of this class.
         throw new RuntimeException ("This class does not implement Cloneable");
      }
   }

      
   /**
    * Compare this DoubleArrayBag to another object for equality of value
    * @param other
    *   reference to another Object
    *
    * @post.
    *   x.equals(x) is true
    *   if x.equals(y) then y.equals(x)
    *   x.equals(null) is false
    *   and if other an instance of DoubleArrayBag,
    *   then x.hashCode() == ( (DoubleArrayBag) other ).hashCode()
    *
    * @return
    *   true if number of elements in this and other are the same AND if
    *   the values of all the elements in the bag are the same and in the
    *   same position in the bag
    *
    * @note.
    *   If the value of other is null, then the return is false
    **/
   @Override
   public boolean equals(Object other) {
       if ( other == null ) return false;

       if ( !(other instanceof DoubleArrayBag) ) return false;

       DoubleArrayBag candidate = (DoubleArrayBag) other;
       //are we comparing this with this
       if (this == other) return true;

       if ( candidate.used != this.used ) return false;

       boolean hashEqual = this.hashCode() == candidate.hashCode();

       int index = 0;
       boolean elementsEqual = true;
       // Inspect data from data[0] through data[used-1] at most
       // and compare with respective elements of candidate.data
       // to determine if all are ==.  If a pair are not ==, then
       // set elementsEqual to false -- which terminates the loop.
       // NOTE:  This is a search while elementsEqual and more place
       //        to look -- implement with a while.
       // STUDENT IMPLEMENTATION OF ABOVE HERE
       
       return (hashEqual && elementsEqual);
   }

   /**
    * @return the hash code for this object
    *
    */
   @Override 
   public int hashCode( ) {
       int usedHashValue = Integer.hashCode( this.used );
       int dataHashValue = 0;

       for ( int i = 0; i < used; ++i ) {
           dataHashValue += Double.hashCode( data[i] );
       }

       return usedHashValue + dataHashValue;
   }


   /**
   * Erases all copies of a specified element from this bag if target exists in bag.
   *
   * @param target
   *   the element(s) to remove from the bag
   * @post.
   *   If target was found in the bag, then all copies of
   *   target have been removed and the method returns number of items removed. 
   * @return
   *   int value from 0 to number of items erased from bag
   **/
   public int erase( double target ) {
       int count = 0;

       //not the most efficient way to do this -- performance is O(N^2) -- improve
       //while ( erase_one(target) ) {
       //    ++count;
       //}
       //
       // Pattern your logic somewhat after the logic for erase_one but keep going
       // searching for a match with target and "erasing" it as is done in erase_one.
       // Of course increment the count each time an item is "erased"
       // STUDENT IMPLEMENTATION OF ABOVE HERE

       return count;
   }
              
   /**
   * Remove one copy of a specified element from this bag.
   *
   * @param target
   *   the element to remove from the bag
   * @post.
   *   If target was found in the bag, then one copy of
   *   target has been removed and the method returns true. Note that
   *   the removed item is replaced with the last item in the bag.
   *   Otherwise the bag remains unchanged and the method returns false. 
   * @return
   *   true or false depending on whether target exists in the bag
   **/
   public boolean erase_one(double target) {
       // First, set index to the location of target in the data array,
       // which could be as small as 0 or as large as used-1; If target
       // is not in the array, then index will be set equal to used;
       // Use classic sequential search logic for this.
       boolean found = false;
       int index = 0;
       while ( !found && index < used ) {
           if ( target == data[ index ] )
               found = true;
           else
               ++index;
       }

       if ( !found )
          // The target was not found, so nothing is removed.
          return false;
       else {
          // The target was found at data[index].
          // So reduce used by 1 and copy the last element onto data[index].
          data[index] = data[ --used ];
          return true;
       }
   }
   
   /**
   * Add a new element to this bag -- If the new element would take this
   * bag beyond its current capacity, then OutOfMemoryError is thrown
   * 
   * @param newItem
   *   the new element that is being inserted
   * @post.
   *   A new copy of the element has been added to this bag.
   **/
   public void insert(double newItem) {
       if ( used == MAX_CAPACITY ) {
           throw new OutOfMemoryError("bag overflowed");
       }
       this.data[ this.used++ ] = newItem;
   }

   /**
   * Add the contents of another bag to this bag.
   * @param addend
   *   a bag whose contents will be added to this bag
   * @pre.
   *   The parameter, addend, is not null. 
   * @post.
   *   The elements from addend have been added to this bag.
   * @exception NullPointerException
   *   Indicates that addend is null. 
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to add addend to this bag
   * @note.
   *   If number of used items in this bag plus number of used items
   *   in addend greater than MAX_CAPACITY, then OutOfMemoryError is thrown
   **/
   public void plusEquals(DoubleArrayBag addend) {
       if ( addend == null ) {
           throw new NullPointerException("Cannot add from null object");
       }
       if (this.used + addend.used > MAX_CAPACITY) {
           throw new OutOfMemoryError("operation would exceed capacity of this bag");
       }
         
      // We know here that we have room to copy the items in addend onto the end
      // of the array data.  Use your own logic to accomplish this -- do NOT use
      // any fancy array copying methods.
      // STUDENT IMPLEMENTATION OF ABOVE HERE
      //
   }   

   /**
   * Determine the number of elements in this bag.
   * @return
   *   the number of elements in this bag
   **/ 
   public int size( ) {
      return this.used;
   }
   
   /**
   * Accessor method to count the number of occurrences of a particular element
   * in this bag.
   * @param target
   *   the element for which number of occurrences will be counted 
   * @return
   *   the number of times that target occurs in this bag
   **/
   public int occurrences(int target) {
      int answer = 0;
      
      // this algorithm requires iterating over the data array tallying
      // number of times target appears.  DO NOT investigate more than used
      // items.
      // STUDENT IMPLEMENTATION OF ABOVE HERE


      return answer;
   }

   /**
    * This method renders the bag's contents into a human readable form
    * @post.
    *    The bag is not altered by this method
    **/
   public String toString() {
       // StringBuffer is used since its contents are mutable.  Strings are
       // immutable and, thus, very inefficient for such purpose
       StringBuffer sb = new StringBuffer();
       sb.append("DoubleArrayBag with ");
       sb.append(this.size());
       sb.append(" elements: [");
       if ( this.size() == 0 ) {
           sb.append(" ]");
           return sb.toString();
       }
       for (int i=0; i < this.size()-1; ++i) {
           sb.append(" ");
           sb.append(this.data[i]);
           sb.append(",");
       }
       sb.append(" "); sb.append(this.data[this.size()-1]);
       sb.append(" ]");
       return sb.toString();
   }


   /**
   * Create a new bag that contains all the elements from two other bags -- note that
   * this is a class method NOT an instance method and must be called with class
   * name qualifier.
   * @param b1
   *   the first of two bags
   * @param b2
   *   the second of two bags
   * @pre.
   *   Neither b1 nor b2 is null, and b1.size() + b2.size() cannot
   *   exceed MAX_CAPACITY
   * @post.
   *   bag referenced by b1 and bag referenced by b2 are not altered
   * @return
   *   the union of b1 and b2
   * @exception NullPointerException
   *   Indicates that one of the arguments is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for the new bag.
   **/   
   public static DoubleArrayBag union(DoubleArrayBag b1, DoubleArrayBag b2) {
      if ( b1 == null || b2 == null ) {
          throw new NullPointerException("one or both bags reference is null");
      }
      if ( b1.size() + b2.size() > MAX_CAPACITY ) {
          throw new OutOfMemoryError("Not enough capacity to union the two bags");
      }

      DoubleArrayBag answer = new DoubleArrayBag();
      
      //If our logic arrives here, then we know that we have enough space to
      //complete the union.
      //This requires copying, first, all the used elements from b1's data
      //into answer's data followed by all the used elements from b2's data
      //into answer's data.  Of course answer's used must be updated.
      //Use your own logic to accomplish this task; do NOT use any fancy
      //array copying methods.
      // STUDENT IMPLEMENTATION OF ABOVE HERE

      return answer;
   }

}
           
