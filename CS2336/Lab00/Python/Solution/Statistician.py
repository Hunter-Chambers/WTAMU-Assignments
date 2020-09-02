#!/usr/bin/env python3
'''This class models a simple Statistician.'''

from sys import float_info
from typing import TypeVar
from math import sqrt

Statistician = TypeVar('Statistician')
Number = TypeVar('Number', int, float)


class Statistician(object):
    ''' A Statistician manages a stream of numbers and provides various
        statistics: mean, standard deviation, smallest, largest, etc.
    '''
    # Class invariant:
    #   The instance variables __N, __sumX, __sumXsq, __smallest,
    #   and __largest are used to maintain state of a
    #   Statistician object.  __N and __sumX are used to
    #   calculate the mean.  These attributes plus __sumXsq are used
    #   to calculate the standard deviation

    def __init__(self, other:Statistician=None) -> Statistician:
        '''
        Statistician class constructor front end

        Postcondition:
            creates a newly constructed, well-defined Statistician
            object -- initialized as though it has seen no
            numbers OR creates a copy of other
        '''
        if other is None:
            self.__default_constructor()
        else:
            if not isinstance(other, Statistician):
                raise TypeError("other not an instance of Statistician")
            self.__copy_constructor(other)
    # end __init__

    def __default_constructor(self) -> Statistician:
        '''
        Statistician class constructor.

        Postcondition:
            a newly constructed, well-defined Statistician
            object -- initialized as though it has seen no
            numbers
        '''
        self.__N       = 0                 # How many numbers in the sequence
        self.__sumX    = 0.0               # Sum of all the numbers
        self.__sumXsq  = 0.0               # Sum of all numbers squared
        self.__smallest = float_info.max   # The smallest number in the sequenc
        self.__largest = -float_info.max   # The largest number in the sequence
    # end __default_constructor

    def __copy_constructor(self, other:Statistician) -> Statistician:
        '''Statistician class copy constructor.

        Argument:
            other (a reference to a Statistician instance)

        Postcondition:
            a newly constructed Statistician that
            whose attributes are a copy of the
            attributes of other

        Raises:
            TypeError if other not reference to instance of Statistician
        '''
        if not isinstance(other, Statistician):
            raise TypeError("other not an instance of Statistician")
        self.__N        = other.__N
        self.__sumX     = other.__sumX
        self.__sumXsq   = other.__sumXsq
        self.__smallest = other.__smallest
        self.__largest  = other.__largest
    # end __copy_constructor

    def __eq__(self, other:Statistician) -> bool:
        '''
        Determins whether this instance is equal in value
           to other.

        Argument:
               other (a reference to a Statistician instance)

        Returns:
               True if other an instance of Statistician and other's
               attributes are identical to the attributes of this object

               False otherwise
        '''
        if not isinstance(other, Statistician):
            return False

        if self is other:  # are we comparing with ourself
            return True

        if not hash(self) == hash(other):
            return False

        return ( (self.__N == other.__N) and
                 (self.__sumX == other.__sumX) and
                 (self.__sumXsq == other.__sumXsq) and
                 (self.__smallest == other.__smallest) and
                 (self.__largest == other.__largest)
               )
    # end __eq__

    def __ne__(self, other:Statistician) -> bool:
        '''The negation of __eq__.'''
        return not (self == other)
    # end __ne__

    def __hash__(self) -> int:
        '''
        Calculates the hash value of this object

        Preconditions: none

        Returns: an integer representing the combined hash
                 value of all this object's attributes.
        '''
        return hash((self.__N, self.__sumX, self.__sumXsq, self.__smallest,
                    self.__largest))
    # end __hash__

    def length(self) -> int:
        '''
        Determine how many numbers have been given to this Statistician

        Returns:
            the count of how many numbers have been given to this
            Statistician since it was initialized or reinitialized
        '''
        return self.__N
    # end length

    def largest(self) -> float:
        '''
        Determine the largest number that has been given to this
        Statistician

        Returns:
            the largest number that has been given to this
            Statistician since it was initialized or reinitialized

            NOTE: if length() is 0, then returns float('nan')
        '''
        if self.length() == 0:
            return float('nan')
        else:
            return self.__largest

    def mean(self) -> float:
        '''
        Determine the arithmetic average of all the numbers
           given to this Statistician.

        Returns:
            the arithmetic average/mean of all the numbers given
            to this Statistician since it was initialized or reinitialized

            NOTE: if length() is 0, then returns float('nan')
        '''
        if self.length() == 0:
            return float('nan')
        else:
            # STUDENT complete this ensuring that result returned is float
            #         replace 0.0 with the appropriate expression
            return 0.0
    # end mean

    def next_number(self, number:Number) -> None:
        '''
        Give a new number to this Statistician.

        Argument:
            number must be a number

        Precondition:
            number must be a float number

        Postcondition:
            The specified number has been given to this Statistician
            and it will be included in any subsequent statistics

        Raises:
            TypeError if number not an instance of a number
        '''
        if not (isinstance(number, float) or isinstance(number, int)):
            raise TypeError("argument is not a number")

        # STUDENT completes implementation here.
        #   NOTE: if number is the first number given to the statistician,
        #         then number is both the largest and smallest seen so far.

    # end nextNumber

    def reset(self) -> None:
        '''
        Reset this Statistician to the initial state with no numbers
           in the sequence.

        Postcondition:
            This Statistician is reinitialized as if it has never been
            given any numbers
        '''
        self.__N       = 0                 # How many numbers in the sequence
        self.__sumX    = 0.0               # Sum of all the numbers
        self.__sumXsq  = 0.0               # Sum of all the squares of numbers
        self.__smallest = float_info.max   # The smallest number in the sequence
        self.__largest = -float_info.max   # The largest number in the sequence
    # end reset

    @staticmethod
    def scale(scale:Number, statistician:Statistician) -> Statistician:
        '''
        Create a new Statistician whose state is scaled by parameter
           scale -- such that statistics (other than length() are
           multiplied by scale.

        Arguments:
            scale -- must be a number
            statistician -- must be an instance of Statistician

        Raises:
            TypeError if either scale or statistician not of
            appropriate type

        Returns:
            a new Statistician that is a scaled version of statistician
        '''
        if not (isinstance(scale, float) or isinstance(scale, int)):
            raise TypeError("scale argument is not a float number")
        if not isinstance(statistician, Statistician):
            raise TypeError("statistician argument is not a Statistician")

        temp = Statistician(statistician)

        # STUDENT complete implementation here

        return temp
    # end scale

    def smallest(self) -> float:
        '''
        Determine the smallest number that has been given to this
           Statistician.

        Returns:
            the smallest number that has been given to this
            Statistician since it was initialized or reinitialized

            NOTE: if length() is 0, then returns float('nan')
        '''
        if self.length() == 0:
            return float('nan')
        else:
            return self.__smallest
    # end smallest

    def stdDev(self) -> float:
        '''
        Determine the standard deviation of the stream of values
           given to this statistician.

        Returns:
            The calculated standard deviation for a population of values
        '''
        # STUDENT complete this by replacing the pass with appropriate
        #         return
        pass
    # end stdDev

    def __str__(self) -> str:
        '''
        Create a string representation of this Statistician.

        Returns:
            a string representation of this Statistician

        NOTE:  Since strings are immutable, please note the method
               used
        '''
        buffer = []
        buffer.append("Statistician: \n")
        buffer.append("\tN = " + str(self.__N) + "\n")
        buffer.append("\tsumX = " + str(self.__sumX) + "\n")
        buffer.append("\tsumXsq = " + str(self.__sumXsq) + "\n")
        if self.__N == 0:
            buffer.append("\tmean    = NOT DEFINED\n")
            buffer.append("\tstd-dev = NOT DEFINED\n")
            buffer.append("\tmin     = NOT DEFINED\n")
            buffer.append("\tmax     = NOT DEFINED\n")
        else:
            buffer.append("\tmean     = " + str(self.mean()) + "\n")
            buffer.append("\tstd-dev  = " + str(self.stdDev()) + "\n")
            buffer.append("\tmin      = " + str(self.__smallest) + "\n")
            buffer.append("\tmax      = " + str(self.__largest) + "\n")
        # end if

        return ''.join(buffer)
    # end __str__

    def sumX(self) -> float:
        '''Determine the sum/total of all the numbers that have been given
           to this Statistician.

        Returns:
           The sum of all the numbers that have been given to this
           Statistician
        '''
        return self.__sumX
    # end sum

    @staticmethod
    def union(stat1:Statistician, stat2:Statistician) -> Statistician:
        '''Create and return a new Statistician that behaves as if it was
           given all the numbers from the two Statisticians stat1 and stat2.

        Preconditions:
           Both stat1 and stat2 must not be None and also must reference
           Statistician objects


Arguments:
            stat1  reference to a Statistician
            stat2  reference to a Statistician

        Returns:
            a new Statistician that behaves as if it was given all the
            numbers from the two Statisticians stat1 and stat2

        Raises:
            TypeError if either stat1 or stat2 is None or if either stat1 or
            stat2 not an instance of Statistician
        '''
        if stat1 is None or stat2 is None:
            raise TypeError("Either stat1 or stat2 is None")
        if not isinstance(stat1, Statistician) or\
           not isinstance(stat2, Statistician):
            raise TypeError("Either stat1 or stat2 is not a Statistician")

        temp = Statistician()

        # STUDENT completes implementation here
        return temp

# end class Statistician
