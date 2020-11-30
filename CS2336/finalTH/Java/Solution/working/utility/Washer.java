// File: Washer.java from the package utility

package utility;

/******************************************************************************
* An <CODE>Washer</CODE> simulates a simple washing machine.
*
* @version  April, 2019
******************************************************************************/
public class Washer {
   private int secondsForWash; // Seconds for a single wash
   private int washTimeLeft;   // Seconds until this washer is no longer busy

                      
   /**
   * Initialize a washer.
   * 
   * @param s
   *   the number of seconds required for one wash cyle of this washer
   * @post.
   *   This washer has been initialized so that it takes <CODE>s</CODE>
   *   seconds to complete one wash cycle.
   **/
   public Washer(int s) {
       secondsForWash = s;
       washTimeLeft = 0;
   }


   /**
   * Determine whether this washer is currently busy.
   * @return
   *   <CODE>true</CODE> if this washer is busy (in a wash cycle);
   *   otherwise <CODE>false</CODE>
   **/   
   public boolean isBusy( ) {
      return (washTimeLeft > 0);
   }
 

   /**
   * Reduce the remaining time in the current wash cycle by one second.
   * post.
   *   If a car is being washed, then the remaining time in the current
   *   wash cycle has been reduced by one second.
   **/
   public void reduceRemainingTime( ) {
      if (washTimeLeft > 0)
         washTimeLeft--;
   } 


   /**
   * Start a wash cycle for this washer.
   * @pre.
   *   <CODE>isBusy()</CODE> is <CODE>false</CODE>.
   * post.
   *   This washer has started simulating one wash cycle. Therefore, 
   *   <CODE>isBusy()</CODE> will return <CODE>true</CODE> until the required
   *   number of simulated seonds have passed.
   * @exception IllegalStateException
   *   Indicates that this washer is busy.
   **/
   public void startWashing( ) {
      if (washTimeLeft > 0)
         throw new IllegalStateException("Washer is already busy.");
      washTimeLeft = secondsForWash;
   }
   
}
