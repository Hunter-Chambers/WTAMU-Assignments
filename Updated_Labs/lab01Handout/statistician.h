#ifndef STATS_H     // Prevent duplicate definition
#define STATS_H

#include <iostream>
#include <string>

/** *************************************************************
 * @file        statistician.cxx
 * @author      H. Paul Haiduk
 * @date        01/15/2020
 * @version     0.9
 *
 * @brief       This class models a simple statistician.
 *
 * @section     DESCRIPTION
 * A statistician that maintains simple statistics
 * and which can display itself.
 *
 ***************************************************************/

class statistician {

    // Class invariant:  N, sumX, sumXsq, smallest, and largest
    // maintain state of the statistician.  Assignments and the
    // copy constructor may be used with statistician objects;
    // thus value semanticis are supported

    public:
        // CONSTRUCTORS
        /**
         * @brief       default constructor
         * @post        the statistician is newly created
         *              and initialized as a statistician that
         *              has been given no values
         */
        statistician( );

        /**
         * @brief       copy constructor
         * @param       other -- reference to statistician to be
         *              copied
         * @post        a newly created statistician that is, in
         *              value, equal to other
         */
        statistician(const statistician& other);

        // MODIFICATION MEMBER FUNCTIONS
        /**
         * @brief       updates statistics based on value of r
         * @param       r -- a double value
         * @post        the statistics are updated based on value
         *              of r
         * @return      void
         */
        void nextNumber(double r);

        /**
         * @brief       sets the internal state to be that
         *              of a newly created statistician
         * @post        statistician has state equal to that
         *              of a newly created statistician
         * @return      void
         */
        void reset( );

        /**
         * @brief       redefines internal statistics to be equal
         *              to that of other
         * @param       other -- a const reference to another
         *              statistician
         * @post        state of this statistician set equal to 
         *              that of statistician other
         */
        statistician& operator =(const statistician& other);

        // CONSTANT MEMBER FUNCTIONS
        /**
         * @brief       accessor returning an unique hashCode
         *              based on all attributes of this 
         *              statistician
         * @return      a size_t value representing the hash code
         *              for this statistician
         */
        size_t hashCode( ) const;

        /**
         * @brief       accessor returning number of items given
         *              to this statistician
         * @return      number of values given to this statistician
         *              since creation or reset
         */
        int length( ) const;

        /**
         * @brief       accessor returning the arithetic average of
         *              all values given to this statistician
         * @return      arithmetic average of all values given to 
         *              this statistician since creation or reset
         */
        double mean( ) const;

        /**
         * @brief       accessor returning the smallest value given to
         *              this statistician
         * @return      the smallest value given to this statistician
         *              since creation or reset
         */
        double minimum( ) const;

        /**
         * @brief       accessor returning the largest value given to
         *              this statistician
         * @return      the largest value given to this statistician
         *              since creation or reset
         */
        double maximum( ) const;

        /**
         * @brief       accessor returning sum of all values given
         *              to this statistician
         * @return      sum of all values given to this statistician
         *              since creation or reset
         */
        double sum_X( ) const;

        /**
         * @brief       accessor returning sum of all values given
         *              to this statistician squared
         * @return      sum of all values given to this statistician
         *              since creation or reset squared
         */
        double sum_Xsq( ) const;

        /**
         * @brief       accessor returning the standard deviation of
         *              all values given to this statistician
         * @return      standard deviation of all values given to 
         *              this statistician since creation or reset
         */
        double stdDev( ) const;

        /**
         * @brief       accessor returning a string representation of
         *              this statistician -- suitable for display or print
         * @return      string
         */
        std::string toString( ) const;

        // FRIEND FUNCTIONS
        /**
         * @brief       operator * that multiplies the argument s
         *              by the scale -- adjusting the minimum and
         *              maximum in case scale is negative
         * @param       scale -- a double used to multiply state of
         *              parameter s
         * @param       s -- a const reference to another statistician
         * @return      a new statistician that is the equal to the
         *              scaling of s
         */
        friend 
            statistician operator *
                (double scale, const statistician& s);
        /**
         * @brief       operator + to "add" s1 and s2
         * @param       s1 -- a const reference to some statistician
         * @param       s2 -- a const reference to some statistician
         * @return      a new statistician
         */
        friend 
            statistician operator +
                (const statistician& s1, const statistician& s2);
    private:
        int    N;        // How many numbers in the sequence
        double sumX;     // The sum of all the numbers in the sequence
        double sumXsq;   // The sum of all the numbers squared
        double smallest; // The smallest number in the sequence
        double largest;  // The largest number in the sequence

};  //NOTE:  the semi-colon is required at end of the class

// NON-MEMBER functions for the statistician class
/**
 * @brief       operator == to test whether parms s1 and s2 are
 *              equal in value
 * @param       s1 -- a const reference to some statistician
 * @param       s2 -- a const reference to some statistician
 */
bool operator ==(const statistician& s1, const statistician& s2);

/**
 * @brief       operator != to test whether parms s1 and s2 are
 *              not equal in value
 * @param       s1 -- a const reference to some statistician
 * @param       s2 -- a const reference to some statistician
 */
bool operator !=(const statistician& s1, const statistician& s2);

/**
 * @brief       overloaded << operator to permit sending this
 *              statistician's string representation into an
 *              output string
 * @param       outs -- reference to an ostream
 * @param       s1 -- reference to a statistician
 * @returns     updated ostream
 */
std::ostream& operator <<(std::ostream& outs, const statistician& s1);

#endif
