// FILE: CarwashE.java
// This program illustrates the use of the carWashSimulate method which uses
// a simple queue to simulate cars waiting at a car wash.

import java.util.LinkedList;
import java.util.Queue;
import utility.BooleanSource;
import utility.Washer;
import utility.Averager;
import collections.LinkedQueue;

/******************************************************************************
* The <CODE>CarWash</CODE> Java application illustrates the use of
* the <CODE>carWashSimulate</CODE> method.
* The illustration uses the following values:
*   <CODE>
*   <br>washTime = 240
*   <br>arrivalProbability = 0.004
*   <br>totalTime = 28800
*   </CODE>
*
* @version
*       November, 2020
******************************************************************************/
public class CarWashE {
   /**
   * @param args NOT used in this implementation.
   *
   * @note.
   * The main method activates <CODE>carWashSimulate</CODE> with the values:
   *   <CODE>
   *   <br>washTime = 240
   *   <br>arrivalProbability = 0.004
   *   <br>totalTime = 28800
   *   </CODE>
   **/
   public static void main(String[ ] args) {
      final int WASHTIME = 240;
      final double ARRIVALPROB = 0.004;
      final int TOTALTIME = 28800;
      
      carWashSimulate(WASHTIME, ARRIVALPROB, TOTALTIME);
   }
    
   
   /**
   * Simulate the running of a car washer for a specified amount of time.
   * @param washTime
   *   the number of seconds required to wash one car
   * @param arrivalProb
   *   the probability of a customer arriving in any second, for example
   *   0.1 is 10%
   * @param totalTime
   *   the total number of seconds for the simulation
   * @pre.
   *   <CODE>washTime</CODE> and <CODE>totalTime</CODE> are positive;
   *   <CODE>arrivalProb</CODE> lies in the range 0 to 1.
   * @post.
   *   The method has simulated a car wash where <CODE>washTime</CODE> is the
   *   number of seconds needed to wash one car, <CODE>arrivalProb</CODE> is
   *   the probability of a customer arriving in any second, and
   *   <CODE>totalTime</CODE> is the total number of seconds for the
   *   simulation. Before the simulation, the method has written its three
   *   parameters to <CODE>System.out</CODE>. After the simulation, the method
   *   has written three pieces of information to <CODE>System.out</CODE>:
   *   (1) The number of cars washed, (2) The average waiting time for
   *   customers that had their cars washed, and (3) the number of customers that
   *   are still in the queue.  Customers remaining in the queue are not included
   *   in the average waiting time.
   * @exception java.lang.IllegalArgumentException
   *   Indicates that one of the arguments violates the precondition.
   **/
   public static void carWashSimulate (int washTime, double arrivalProb, int totalTime) {
      //STUDENT -- for testing your implementation of LinkedQueue, comment out the 
      //           following line and uncomment the line after
      Queue<Integer> arrivalTimes = new LinkedList<Integer>( );  //the builtin queue
      //LinkedQueue<Integer> arrivalTimes = new LinkedQueue<Integer>( );  // student implemented queue
      int arrivalTime;
      BooleanSource arrival = new BooleanSource(arrivalProb);
      Washer machine = new Washer(washTime);
      Averager waitTimes = new Averager( );
      Averager waitTimesAfterEntranceClosed = new Averager( );
      Averager queueLength = new Averager();

      int currentSecond;
  
      // Write the parameters to System.out.
      System.out.printf("Seconds to wash one car: %d\n",  washTime);
      System.out.printf("Probability of customer arrival during a second: %.4f\n", arrivalProb);
      System.out.printf("Total simulation seconds: %d\n", totalTime); 
   
      // Check the precondition:
      if (washTime <= 0 || arrivalProb < 0 || arrivalProb > 1 || totalTime < 0)
          throw new IllegalArgumentException("Values out of range"); 

      for (currentSecond = 0; currentSecond < totalTime; currentSecond++) { 
          // Simulate the passage of one second of time.
          // Check whether a new customer has arrived.
          if (arrival.query( ))
             arrivalTimes.add(currentSecond);
  
         // Check whether we can start washing another car.
         if ((!machine.isBusy( ))  &&  (!arrivalTimes.isEmpty( ))) {
            arrivalTime = arrivalTimes.remove( );
            waitTimes.addNumber(currentSecond - arrivalTime);
            machine.startWashing( );
         }

         // Subtract one second from the remaining time in the current wash cycle.
         machine.reduceRemainingTime( );
         queueLength.addNumber( arrivalTimes.size( ) );
      }

      // Write the summary information about the simulation.
      System.out.printf("Customers served until entrance closed: %d\n", waitTimes.howManyNumbers( ) ); 
      if (waitTimes.howManyNumbers( ) > 0)
         System.out.printf("Average wait: %.2f sec\n", waitTimes.average( ) );
      if (queueLength.howManyNumbers( ) > 0)
          System.out.printf("Average queue length %.2f cars\n", queueLength.average( ) );

      System.out.printf("Customers remaining to be served after entrance closed: %d\n", arrivalTimes.size() );

      /* use iterator to show what is in queue at gate closing time */
      /*
      System.out.printf("Arrival times for waiting customers:\n\t ");
      for ( Integer aTime : arrivalTimes )
          System.out.printf(" %d ", aTime);
      System.out.println();
      */
      System.out.printf("Arrival times for waiting customers:\n\t");
      arrivalTimes.forEach(aTime -> System.out.printf(" %d ", aTime));
      System.out.println();

      //STUDENT work here
      //   Process the cars waiting in line after gate was closed
      //   NOTE: don't forget that there is likely a car still being washed
      //   NOTE: use the waitTimesAfterEntranceClosed averager to get
      //         the average wait time for those waiting after gate closed
      //   NOTE: there will be NO new arrivals to deal with
      //   NOTE: the clock continues to run as measured by currentSecond


      //STUDENT work here
      //   Be sure to finish up washing the last car with the elapsed time clock
      //   continuing to run

      // STUDENT
      //    Output average wait time for the clean up of waiting cars

      System.out.printf("Current second is %d\n", currentSecond);

      // STUDENT
      //    Now break the elapsed time down into hours and minutes (with minutes
      //    rounded up to the nearest whole minute
      /*
      int hours = ???;
      int minutes = ????;
      System.out.printf("Shut down after %d hours and %d minutes\n", hours, minutes);
      */
   }
}
