// File: DoubleArrayBag.java 

import java.util.Arrays;
import java.util.Objects;

/******************************************************************************
* An DoubleArrayBag is an unordered collection of double numbers and in which
* the same number may appear multiple times -- the bag's capacity can grow as
* needed and can be reduced.
*
* @note.
*   Because of the slow linear algorithms of this
*   class, large bags will have poor performance.
*
* @author
*    H. Paul Haiduk with credit given to Michael Main
*
* @version
*    September, 2020
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
   //   3. Capacity of this bag at any given time is this.data.length

   private double[ ] data;
   private int used; 
   
   /**
   * Initialize an empty bag
   * @post.
   *   This bag is empty and has a capacity of 1.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for allocating the array 
   **/   
   public DoubleArrayBag( ) {
      this.used = 0;
      this.data = new double[1];
   }

   /**
    * Initialize an empty bag having a capacity of initialCapacity
    * @param
    *   initialCapacity an int greater than 0
    * @pre.
    *   initialCapacity must be greater than 0
    * @post.
    *   This bag is empty and has a capacity of initialCapacity
    * @exception
    *   IllegalArgumentException thrown if initialCapacity not greater than 0
    *   OutOfMemoryError thrown if insufficient memory for allocating the array
    **/
   public DoubleArrayBag( int initialCapacity ) {
       if ( initialCapacity < 1 )
           throw new IllegalArgumentException("initialCapacity < 1");
       this.used = 0;
       this.data = new double[initialCapacity];
   }

   /**
    * Initialize a new bag as an exact copy of source
    * @param source
    *   reference to bag that is to be copied
    * @exception NullPointerException
    *   occurs when source is null
    * @post.
    *   This bag is a copy of source and has a capacity
    *   of number of elements in source
    **/
   public DoubleArrayBag( DoubleArrayBag source ) {
       if ( source == null ) {
           throw new NullPointerException("source must not be null");
       }

       this.used = source.used;
       int myCapacity = source.used;
       if (myCapacity < 1) myCapacity = 1;
       this.data = new double[myCapacity]; //NOTE: only allocating needed space
       /* below logic we would write if we did not have System.arraycopy
       for (int index = 0; index < source.used; ++index) {
           this.data[index] = source.data[index];
       }
       */
       /* Documentation from Oracle about System.arraycopy is:
        * arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
        * Copies an array from the specified source array, beginning at the specified
        * position, to the specified position of the destination array.
        **/
       System.arraycopy(source.data, 0, this.data, 0, source.used);
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
      
      try {
         answer = (DoubleArrayBag) super.clone( );
         answer.data = data.clone( );
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

      
   /**
    * Compare this DoubleArrayBag to another object for equality of value
    * @param other
    *   reference to another DoubleArrayBag
    *
    * @post.
    *   x.equals(x) is true
    *   if x.equals(y) then y.equals(x)
    *   x.equals(null) is false
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

       boolean isEqual = false;
       if ( other instanceof DoubleArrayBag ) {
           DoubleArrayBag candidate = (DoubleArrayBag) other;
           //are we comparing this with this
           if (this == other) return true;

           if ( candidate.used != this.used ) return false;
           if ( candidate.hashCode() != this.hashCode() ) return false;

           int index = 0;
           isEqual = true;
           while ( isEqual && index < this.used ) {
               if ( this.data[ index ] != candidate.data[ index ] )
                   isEqual = false;
               else
                   index++;
           }
       }
       return isEqual;
   }

   /**
    * Potentially increase capacity of this bag
    *
    * @pre.
    *   newCapacity must be greater than 0
    *
    * @post.
    *   The bag's capacity is at least newCapacity.  If the capacity
    *   was already at or greater than newCapacity, then the capacity
    *   is left unchanged.
    *
    * @param
    *   newCapacity an integer greater than 0
    *
    * @exception
    *   OutOfMemoryError if not enough dynamic available to allocate
    *   additional space
    * @exception
    *   IllegalArgumentException if newCapacity not greater than 0
    *
    **/
   public void ensureCapacity( int newCapacity ) {
       if ( newCapacity < 1)
           throw new IllegalArgumentException("newCapacity < 1");

       if ( this.data.length < newCapacity ) {
           //STUDENT WORK GOES HERE

           double[] newData = new double[newCapacity];

           for (int i = 0; i < used; i++)
               newData[i] = data[i];

           data = newData;
       }
   }

   /**
    * returns the current capacity of the bag
    * @post.
    *   This method does not alter state of the bag
    * @return
    *   an int that represents total capacity of this bag
    **/
   public int getCapacity( ) {
       return this.data.length;
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
           dataHashValue += Double.hashCode( this.data[i] );
       }

       return usedHashValue + dataHashValue;

   }

   /**
   * Erases all copies of a specified element from this bag if target exists in bag.
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
       int index = 0;

       while ( index < used ) {
           boolean found = false;
           while ( !found && index < this.used ) {
               if ( target == this.data[ index ] )
                   found = true;
               else
                   ++index;
           }
           if (found) {
               this.data[ index ] = this.data[ --this.used ];
               ++count;
           }
       }

       return count;
   }
              
   /**
   * Remove one copy of a specified element from this bag.
   * @param target
   *   the element to remove from the bag
   * @post.
   *   If target was found in the bag, then one copy of
   *   target has been removed and the method returns true. 
   *   Otherwise the bag remains unchanged and the method returns false. 
   * @return
   *   true or false depending on whether target exists in the bag
   **/
   public boolean erase_one(double target) {
       int index = 0; // The location of target in the data array.
       boolean found = false;

       while (!found && index < this.used) {
           if ( target == this.data[index] )
               found = true;
           else
               ++index;
       }
       
       if (found) 
           this.data[index] = this.data[--this.used];

       return found;
   }
   
   /**
   * Add a new element to this bag doubling capacity if needed
   * 
   * @param newItem
   *   the new element that is being inserted
   * @post.
   *   A new copy of the element has been added to this bag.
   **/
   public void insert(double newItem) {
       if ( used == this.data.length ) {
           this.ensureCapacity( this.data.length * 2 );
       }
       this.data[ used++ ] = newItem;
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
   **/
   public void plusEquals(DoubleArrayBag addend) {
       if ( addend == null ) {
           throw new NullPointerException("Cannot add from null object");
       }

       //STUDENT WORK GOES HERE

       if (addend.used > 0) {
           ensureCapacity(used + addend.used);

           int amt = addend.used;
           for (int i = 0; i < amt; i++)
               data[used++] = addend.data[i];
       }
   }   

   /**
   * Determine the number of elements in this bag.
   * @return
   *   the number of elements in this bag
   * @post.
   *   the bag is not altered by this method
   **/ 
   public int size( ) {
       return used;
   }
   
   /**
   * Accessor method to count the number of occurrences of a particular element
   * in this bag.
   * @param target
   *    the element for which number of occurrences will be counted 
   * @post.
   *    the bag is not altered by this method
   * @return
   *    the number of times that target occurs in this bag
   **/
   public int occurrences(double target) {
       int answer = 0;
      
       for (int index = 0; index < this.used; ++index) {
           if (target == this.data[index]) ++answer; 
       }
      return answer;
   }

   /**
    * This method renders the bag's contents into a human readable form
    * @return
    *    a string representation of the bag
    * @post.
    *    The bag is not altered by this method
    **/
   @Override
   public String toString() {
       // StringBuffer is used since its contents are mutable.  Strings are
       // immutable and, thus, very inefficient for such purpose
       StringBuffer sb = new StringBuffer();
       sb.append("DoubleArrayBag with ");
       sb.append(this.size());
       sb.append(" elements: [");
       if ( this.size() == 0 ) {
           sb.append(" ]");
           sb.append(" Capacity: "); 
           sb.append(this.getCapacity());
           return sb.toString();
       }
       for (int i=0; i < this.size()-1; ++i) {
           sb.append(" ");
           sb.append(this.data[i]);
           sb.append(",");
       }
       sb.append(" "); sb.append(this.data[this.size()-1]);
       sb.append(" ]");
       sb.append(" Capacity: "); sb.append(this.getCapacity());
       return sb.toString();
   }

   /**
    * reduce capacity of this bag to current size if there is
    * excess capacity
    *
    * @post.
    *   capacity of this bag is reduced to the current number
    *   of items in bag or left unchanged if capacity equals to
    *   number of items in bag but must be at least 1
    *
    * @exception
    *   OutOfMemoryError if not enough dynamic memory to
    *   support allocation and deallocation of memory
    **/
    public void trimToSize( ) {
        if ( this.used < this.data.length ) {
            int newCapacity;
            if ( this.used <= 1 )
                newCapacity = 1;
            else
                newCapacity = this.used;

            // STUDENT WORK GOES HERE

            double[] newData = new double[newCapacity];

            for (int i = 0; i < used; i++)
                newData[i] = data[i];

            data = newData;
        }
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
   *   the union of b1 and b2 as a new DoubleArrayBag
   * @exception NullPointerException
   *   Indicates that one of the arguments is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for the new bag.  Only happens
   *   when not enough dynamic memory to allocate a new bag of
   *   adequate size.
   **/   
   public static DoubleArrayBag union(DoubleArrayBag b1, DoubleArrayBag b2) {
      if ( b1 == null || b2 == null ) {
          throw new NullPointerException("one or both bags reference is null");
      }

      // guarantee that the new bag has exactly the capacity needed to
      // hold all items from both b1 and b2 and then copy the items from
      // b1 and then b2 into the new bag

      //DoubleArrayBag newBag = null; // = new DoubleArrayBag( ?? );
      //STUDENT WORK HERE
      
      DoubleArrayBag newBag = b1.clone();

      if (b2.used > 0) {
          newBag.ensureCapacity(newBag.used + b2.used);

          for (int i = 0; i < b2.used; i++)
              newBag.data[newBag.used++] = b2.data[i];
      }

      return newBag;
   }

}
           
