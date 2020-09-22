// File: Statistician.java
// This is an assignment for students to complete after reading Chapter 2 of
// "Data Structures and Other Objects Using Java" by Michael Main.

import java.util.Objects;
import static java.lang.Math.sqrt;


/******************************************************************************
* This class is a homework assignment designed to help students begin
* their transition from Python into Java --
*
* A <CODE>Statistician</CODE> keeps track of statistics about a sequence of
* double numbers.<br>
*
* @version
*   September 2020  
******************************************************************************/
public class Statistician {   

   //  Here we define the "instance" variables that hold
   //  the "state" information for objects of this class.
   //  We must manage those "instance" variables appropriately
   //  since we are obligated to report to a user such "statistics" 
   //  as mean, sumX, largest, smallest, length and standard deviation. 
   //  Note that we need NOT save all the values
   //
   //  Class invariant:  the attributes/instance variables N, sumX,
   //  sumXsq, largest and smallest are used to derive the statistics
   //  and define the state of a statistician object

    private int    N;        // How many numbers given to statistician
    private double sumX;     // Sum of all the numbers given to statistician
    private double sumXsq;   // Sum of the squares of all numbers processed
    private double smallest; // The smallest number given to statistician
    private double largest;  // The largest number given to statistician


   /**
   * Initialize a new Statistician.
   * <b>Postcondition:</b><br>
   *   This Statistician is newly initialized and has not yet been given any 
   *   numbers.
   **/   
   public Statistician( ) {
      this.N        = 0;
      this.sumX     = 0.0;
      this.sumXsq   = 0.0;
      this.smallest = +Double.MAX_VALUE;
      this.largest  = -Double.MAX_VALUE;
   } 


   /**
   * Initialize a new Statistician as a copy of another Statistician.<br><br>
   * @param other
   *     other is a non-null reference to another Statisticiana<br><br>
   * <b>Precondition:</b><br>
   *   Other is not null.  If it is, then throw NullPointerException<br><br>
   * <b>Postcondition:</b><br>
   *   This Statistician is newly initialized and has been given attributes
   *   equal to those of other
   * @throws NullPointerException
   **/   
   public Statistician( Statistician other ) {
       if ( other == null )
           throw new NullPointerException("other cannot be null");

       this.N = other.N;
      // Student implementation -- initialize all remaining attributes
      // of "this" object with the values of the "other" object
   }  


   /**
   * Compare this <CODE>Statistician</CODE> to another object for equality.
   * @param obj
   *   an object with which this <CODE>Statistician</CODE> will be compared
   * @return
   *   A return value of <CODE>true</CODE> indicates that 
   *   <CODE>obj</CODE> refers to a 
   *   <CODE>Statistican</CODE> object with the same length, sum,
   *   sum of all numbers squared, smallest and largest as this 
   *   <CODE>Statistician</CODE>. Otherwise the return value is 
   *   <CODE>false</CODE>.<br>
   * <b>Note:</b><br>
   *   If <CODE>obj</CODE> is null or does not refer to a 
   *   <CODE>Statistician</CODE> object, then the return is 
   *   <CODE>false</CODE>.
   **/  
   @Override
   public boolean equals(Object obj) {
      if ( obj == null ) return false;
      if ( !(obj instanceof Statistician) ) return false;

      Statistician temp = (Statistician) obj;
      if ( this == temp ) return true; //Are we comparing with ourself
      if ( this.length( ) == 0 && temp.length( ) == 0 ) return true;
      // NOTE:  if two objects are equal, their hashCodes are equal
      if ( this.hashCode() != temp.hashCode() ) return false;

      // The student's code will remove the following return and replace
      // it with appropriate logic that compares each and every attribute
      // of this with attributes of temp
      
      return false;
   } 
   
   /**
    * Return a hashcode that is unique to an instance of
    * class Statistician based on the value of its 
    * attributes.
    * @return
    *   an int hash code that is unique to an object of
    *   class Statistician by combining the hash codes
    *   of all its attributes
    **/
   @Override
   public int hashCode() {
       return Objects.hash( this.N,
                            this.sumX,
                            this.sumXsq,
                            this.smallest,
                            this.largest 
                          );
   }
   
   /**
   * Determine how many numbers have been given to this Statistician.
   * @return
   *   the N of how many numbers have been given to this Statistician
   *   since it was initialized or reinitialized <br>
   * <b>Note:</b><br>
   *   Giving a Statistician more than
   *   <CODE>Integer.MAX_VALUE</CODE> numbers, will
   *   cause failure with an arithmetic overflow.
   **/ 
   public int length( ) {
      return this.N;
   }

   /**
   * Determine the largest number that has been given 
   * to this Statistician.
   * @return
   *   the largest number that has been given to this 
   *   Statistician since it was initialized or reinitialized<br>
   * <b>Note:</b><br>
   *   If <CODE>length()</CODE> is zero, then the return from this method
   *   is <CODE>Double.NaN</CODE>.
   **/ 
   public double largest( ) {
      // The student's code will replace the following return statement with
      // logic that matches specifications.
      return 0;
   }

   /**
   * Determine the arithmetic average of all the numbers that have been given 
   * to this Statistician.
   * @return
   *   the arithmetic mean of all the number that have been given to this 
   *   Statistician
   *   since it was initialized or reinitialized<br>
   * <b>Note:</b><br>
   *   If this Statistician has been given more than
   *   <CODE>Integer.MAX_VALUE</CODE> numbers, then this method fails
   *   because of arithmetic overflow.
   *   If <CODE>length()</CODE> is zero, then the return from this method
   *   is <CODE>Double.NaN</CODE>.
   *   If <CODE>sum()</CODE> exceeds the bounds of double numbers, then the 
   *   return from this method may be 
   *   <CODE>Double.POSITIVE_INFINITY</CODE> or
   *   <CODE>Double.NEGATIVE_INFINITY</CODE>.
   **/ 
   public double mean( ) {
      // The student's code will replace the following return statement with
      // code that matches specifications
      return 0;
   }


   /**
   * Give a new number to this Statistician. 
   * @param number
   *   the new number that is being given to this Statisticiana<br>
   * <b>Postcondition:</b><br>
   *   The specified number has been given to this Statistician and
   *   it will be included in any subsequent statistics
   **/
   public void nextNumber(double number) {
      // Student implementation -- must update all the
      // instance variables
      // NOTE:  if number is the first number given to the statistician,
      //        then number is both the largest and smallest seen so far.

   }
   
   /**
   * Reset this Statistician to state with no numbers
   * in the sequence. <br>
   *
   * <b>Postcondition:</b><br>
   *   This Statistician is reinitialized as if it has never been given any 
   *   numbers
   **/
   public void reset( ) {
      // Student implementation.
   }

   /**
    * Create a new Statistician whose state is scaled by parameter scale--
    * such that "statistics" other than length() are multiplied by scale.
    * <b>Postcondition:</b><br>
    *   The returned Statistician is a scaled copy of parameter s which 
    *   is not modified.
    * @param scale
    *   a double scale factor
    * @param s
    *   a reference to a Statistician
    * @return
    *   a reference to a new Statiscian that is a scaled copy of parameter s
    **/
   public static Statistician scale(double scale, Statistician s) {
       Statistician temp = new Statistician(s);

      // Student implementation follows
      
       return temp;
   }

   /**
   * Determine the smallest number that has been given 
   * to this Statistician.
   * @return
   *   the smallest number that has been given to this 
   *   Statistician since it was initialized or reinitialized<br>
   * <b>Note:</b><br>
   *   If <CODE>length()</CODE> is zero, then the return from this method
   *   is <CODE>Double.NaN</CODE>.
   **/ 
   public double smallest( ) {
      // The student's code will replace the following return statement with
      // code that matches specifications
      return 0;
   }

   /**
   * Determine the standard deviation of the stream of numbers
   * given to this statistician.
   * <b>Postcondition:</b><br>
   *   State of this statistician is NOT altered
   *
   * @return
   *    The standard deviation of the stream of numbers given
   *    to this statistician -- stream of numbers treated as a
   *    population.
   * <b>Note:</b><br>
   *   If <CODE>length()</CODE> is zero, then the return from this method
   *   is <CODE>Double.NaN</CODE>.
   **/
   public double stdDev() {
       if ( this.length() == 0 )
           return Double.NaN;
       //Student implementation replace the following return with
       //appropriate computations and return
       return 0.0;
   }

   /**
   * Create a string representation of this Statistician.<br><br>
   * <b>Postcondition:</b><br>
   *   State of the Statistician remains unaltered by this method
   * @return String
   *   a human readable representation of the Statistician's internal
   *   state
   **/
   @Override
   public String toString( ) {
       StringBuffer sb = new StringBuffer();
       sb.append("Statistician: \n");
       sb.append("\tN      = " + this.N + "\n");
       sb.append("\tsumX   = " + this.sumX + "\n");
       sb.append("\tsumXsq = " + this.sumXsq + "\n");
       if ( this.N == 0 ) {
           sb.append("\tmean       = NOT DEFINED\n");
           sb.append("\tstd. dev   = NOT DEFINED\n");
           sb.append("\tsmallest   = NOT DEFINED\n");
           sb.append("\tlargest    = NOT DEFINED\n");
       }
       else {
           sb.append("\tmean       = " + this.mean()  + "\n");
           sb.append("\tstd. dev   = " + this.stdDev() + "\n");
           sb.append("\tsmallest   = " + this.smallest + "\n");
           sb.append("\tlargest    = " + this.largest + "\n");
       }
       return sb.toString();
   }

   /**
   * Determine the sum of all the numbers that have been given to this 
   * Statistician.
   * @return
   *   the sum of all the number that have been given to this 
   *   Statistician since it was initialized or reinitialized <br>
   * <b>Note:</b><br>
   *   If the sum exceeds the bounds of double numbers, then the answer
   *   from this method may be 
   *   <CODE>Double.POSITIVE_INFINITY</CODE> or
   *   <CODE>Double.NEGATIVE_INFINITY</CODE>.
   **/ 
   public double sumX( ) {
      // The student's code will replace the following return with the 
      // appropriate return
      return 0;
   }

   /**
   * Create a new Statistician that behaves as if it were given all the
   * numbers from two other statisticians.
   * @param  s1
   *   the first of two Statisticians
   * @param s2
   *   the second of two Statisticians <br>
   * <b>Precondition:</b><br>
   *   Neither s1 nor s2 is null.
   * @return
   *   a new Statistician that acts as if it were given all the numbers from
   *   both s1 and s2.
   * @throws NullPointerException
   *   Indicates that one or the other or both of the arguments are null.
   **/   
   public static Statistician union(Statistician s1, Statistician s2) {
      if ( s1 == null || s2 == null )
          throw new NullPointerException("One or both arguments are null");

      Statistician temp = new Statistician();
      // Student's code to correctly set temp to be union

      return temp;
   }
      
}
           
