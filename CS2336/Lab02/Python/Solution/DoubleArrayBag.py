#!/usr/bin/env python3

import array    # provides access to primitive arrays
from typing import TypeVar

DoubleArrayBag = TypeVar('DoubleArrayBag')
Number =  TypeVar('Number', int, float)

#    Class Invariant:
#        1.  The number of elements in the bag is stored in the
#            instance variable __used which can be no greater than
#            len(__data)
#        2.  For an empty bag, we do not care what is stored in
#            any element of __data.  For a non-empty bag, the elements
#            are stored at __data[0] through __data[used-1]
#            and we don't care what is stored in any elements beyond
#            that.
#        3.  The array __data can store only numbers.  Attempts
#            to store items of other types will cause a run-time error
#            to be raised.


class DoubleArrayBag(object):
    '''
    An DoubleArrayBag is a collection of double numbers

    Limitations:
        1.  The capacity of an DoubleArrayBag is set to a fixed
            capacity of MAX_CAPACITY items.
        2.  Because of the linear algorithms used in this implementation.
            large DoubleArrayBags will have poor performance.

    '''
    MAX_CAPACITY = 4000 # this is a class variable

    def __init__(self) -> None:
        '''
        Precondition:   MAX_CAPACITY must be >= 0

        Postcondition:  The bag has been initialized
                        and is in a well-defined, empty
                        state with MAX_CAPACITY elements for
                        storage

        Raises:         MemoryError if not able to allocate space to
                        accommodate MAX_CAPACITY items
        '''
        self.__used = 0
        # Below allocates an array of doubles.  Elements of the array are
        # accessed with indices in range 0 <= index <= MAX_CAPACITY -1
        # In addition, only numbers may be stored in the array.  Disciplined
        # use of this array will avoid the methods append, remove, extend, etc.
        # which are very expensive in run-time performance.
        #
        # NOTE:  integers are converted to doubles; thus, integers can be
        #
        self.__data = array.array('d', range(0, DoubleArrayBag.MAX_CAPACITY))
    # end __init__

    def copy(self) -> DoubleArrayBag:
        '''
        this behaves like a copy constructor that makes a detailed copy of self

        Postcondition:
            x.copy() is x returns False
            type(x.copy()) == type(x) is True
            x.copy() == x  returns  True
            hash(x.copy()) == hash(x) is True

        Returns:    a new DoubleArrayBag object that contains a copy of all
                    the elements in self

        Raises:     MemoryError if dynamic memory allocation fails
        '''
        newBag = DoubleArrayBag()

        # OK to copy immutable object references; thus, next logic should copy
        # self.__used items from self.__data to newBag.__data and must update
        # newBags.__used
        # STUDENT CODE HERE TO ACCOMPLISH ABOVE

        for i in range(self.__used):
            newBag.__data[i] = self.__data[i]

        newBag.__used = self.__used

        return newBag
    # end copy

    def size(self) -> int:
        '''
        Returns:  an integer representing numbers of items in the bag.
        '''
        return self.__used
    # end size

    def __eq__(self, other: DoubleArrayBag) -> bool:
        '''
        compare self with an other DoubleArrayBag

        Precondition:
            other must be an instance of DoubleArrayBag

        Postconditions:
            x == x is True
            if x == y then y == x
            x == None is False
            hash(self) == hash(other)

        Args:
            other must be an instance of DoubleArrayBag

        Returns:
            boolean whether self is equal to other in number of items
            in the bag and in the location and value of the items in the bag

        NOTE:  if the value of other is None, return False
        '''
        if other is None:
            return False
        # end if

        if not isinstance(other, DoubleArrayBag):
            return False
        # end if

        if self is other:
            return True
        # end if

        if self.__used != other.__used:
            return False
        # end if

        if not hash(self) == hash(other):
            return False
        # end if

        # Code that compares self.__data[index] with
        # other.__data[index] and sets isEqual to False
        # The logic must iterate over elements from 0
        # to < __used.
        # STUDENT CODE HERE TO ACCOMPLISH ABOVE

        index = 0
        isEqual = True  # assume equality
        while isEqual and index < self.__used:
            if (self.__data[index] != other.__data[index]):
                isEqual = False
            else:
                index += 1
            # end if
        # end while

        return isEqual
    # end __eq__

    def __ne__(self, other: DoubleArrayBag) -> bool:
        if not isinstance(other, DoubleArrayBag):
            return True
        # end if
        return not self == other
    # end __ne__

    def __hash__(self) -> int:
        '''
        Calculates the hash value of this object

        Preconditions:  none

        Returns:  an integer representing the combined hash
                  values of all values of this object's
                  attributes -- includes only those items of
                  interest in the bag -- nothing from __used on
        '''
        hashValue = 101 * hash(self.__used)
        for index in range(0,self.__used):
            hashValue += hash( self.__data[index] )
        # end for
        return hashValue
    # end __hash__

    def insert(self, newEntry: Number) -> None:
        '''
        adds newEntry to the DoubleArrayBag if capacity available;

        Precondition:  newEntry must be a double or int; otherwise,
                       method raises a TypeError exception.

        Postcondition: newEntry is placed in Bag just after
                       items already existing and self.__used
                       is incremented by 1.

        Args:          newEntry (number)

        Raises:        MemoryError if bag is full

        '''
        if not ( isinstance(newEntry, int) or isinstance(newEntry, float) ):
                raise TypeError("newEntry must be a number")
        # end if
        if self.__used == DoubleArrayBag.MAX_CAPACITY:
            raise MemoryError("bag has overflowed")
        # end if
        self.__data[self.__used] = newEntry
        self.__used += 1
    # end add

    def plusEqual(self, addend: DoubleArrayBag) -> DoubleArrayBag:
        '''
        an implementation of the += operation

        Precondition:   addend is an instance of DoubleArrayBag and is not None

        Postcondition:  the elements from addend have been added to this bag

        Raises:         MemoryError if not enough memory
                        available to accommodate operation

                        TypeError if addend is None or is not an instance of
                        DoubleArrayBag
        '''
        if (addend is None) or not (isinstance(addend, DoubleArrayBag)):
            raise TypeError("addend is None or not instance of DoubleArrayBag")
        # end if
        if self.size() + addend.size() > DoubleArrayBag.MAX_CAPACITY:
            raise MemoryError("Not enough space to add elements of addend")
        # end if

        addendIndex = 0

        # We now know that we have enough room to copy the items from
        # addend onto the end of the array of items self.__data.  We also
        # need to update self.__used appropriately
        # STUDENT CODE HERE TO ACCOMPLISH ABOVE GOES BELOW

        while (addendIndex < addend.__used):
            self.__data[self.__used] = addend.__data[addendIndex]
            self.__used += 1
            addendIndex += 1
        # end while

        return self
    # end plusEqual

    def occurrences(self, target: Number) -> int:
        '''
        counts occurrences of target in the bag

        Precondition:   target must be a number; otherwise,
                        method raises a TypeError exception

        Postcondition:  bag's state is not changed

        Arg:            target (number)

        Returns:        an integer number of occurrences of
                        target in the bag
        '''
        if not ( isinstance(target, int) or isinstance(target, float) ):
            raise TypeError("target must be a number")
        # end if

        # Iterate over self.__data from position 0 to position self.__used - 1
        # to determine whether value in data is == to value of target.
        # Students may NOT use the count method for arrays.
        # Below statement is the "magic" and lazy way to count occurrences
        # return self.__data.count(target)
        # STUDENT CODE HERE TO ACCOMPLISH ABOVE GOES BELOW

        count = 0
        for i in range(self.__used):
            if (self.__data[i] == target):
                count += 1
            # end if
        # end for

        return count
    # end occurrences

    def erase_one(self, target: Number) -> bool:
        '''
        removes one copy of target from the DoubleArrayBag if target exists
        in the bag

        Precondition:  target must be a float or int; otherwise,
                       method raises a TypeError exception.

        Postcondition: if target exists in the bag, the first
                       occurrence of target is removed and
                       used is decremented.  Otherwise,
                       the bag's state is the same as it was
                       before the call.  The "empty" array slot is
                       filled with the item at self.__data[used-1]

        Arg:           target which must be a number

        Returns:       False or True depending on whether
                       target can be removed.
        '''
        if not ( isinstance(target, int) or isinstance(target, float) ):
            raise TypeError("target must be a Number")
        # end if

        # The first task is to locate where target may exist in the array.
        # This means that we must search, possibly the entire array if target
        # is not in the array.  Thus, this is an O(N) operation
        # NOTE:  the search logic is a classic sequential search pattern that
        #        searches while target not found and while more place to look
        found = False
        index = 0
        while not found and index < self.__used:
            if target == self.__data[index]:
                found = True
            else:
                index += 1
            # end if
        # end while
        if not found:
            return False
        else:
            # otherwise we know we found target at data[index]
            # now reduce used by 1 and copy the last element
            # onto data[index] since ordering doesn't matter
            # STUDENT CODE HERE TO ACCOMPLISH ABOVE GOES BELOW

            self.__used -= 1
            self.__data[index] = self.__data[self.__used]

            return True
        # end if
    # end erase_one

    def erase(self, target: Number) -> int:
        '''
        removes all copies of target from the DoubleArrayBag if target
        exists in the bag

        Precondition:  target must be a number; otherwise,
                       method raises a TypeError exception.

        Postcondition: if target exists in the bag, all
                       occurrences of target are removed and
                       used is decremented by number of
                       items removed.  Otherwise, the bag's state
                       is the same as it was before the call.

        Arg:           target which must be a number

        Returns:       an int representing number of items erased
        '''
        if not ( isinstance(target,int) or isinstance(target,float) ):
            raise TypeError("target must be a number")
        # end if

        number_removed = 0

        # Terribly inefficient method -- has performance O(N^2).  The only
        # redeeming quality of below is that it works
        # while self.erase_one(target):
        #    number_removed += 1
        # end while
        # A better approach is to effectively incorporate logic similar to
        # erase_one to find the first occurrence of target, increment the
        # count, decrement self.__used and then copy that last item into
        # "hole" that would be left by removing target . . .  and then continue
        # on down the array to see if any more items match target and repeat
        # process until we have exhaustively searched the entire array
        # STUDENT CODE HERE TO ACCOMPLISH ABOVE GOES BELOW

        i = 0
        while (i < self.__used):
            if (self.__data[i] == target):
                self.__used -= 1
                self.__data[i] = self.__data[self.__used]
                number_removed += 1
            else:
                i += 1
            # end if
        # end while

        return number_removed
    # end erase

    @staticmethod       # NOTE: this method belongs to the class
    def union(b1: DoubleArrayBag, b2: DoubleArrayBag) -> DoubleArrayBag:
        '''
        this method combines all the elements in b1 and all the
        elements in b2 by placing them in a new bag -- elements of
        b2 come after elements of b1

        Also note that this is a class method NOT an instance method

        Precondition:       b1 and b2 must be an instance of DoubleArrayBag
                            otherwise TypeError is raised

        Postcondition:      a new DoubleArrayBag is created containing copies
                            of all the elements in b1 plus all the elements
                            in b2.

        Args:               b1 and b2 (DoubleArrayBag)

        Raises:             TypeError if b1 or b2 not an instance of
                            DoubleArrayBag

                            MemoryError if union would overflow bag's
                            capacity
        '''

        if not(isinstance(b1, DoubleArrayBag)):
            raise TypeError("b1 not instance of DoubleArrayBag")
        # end if
        if not(isinstance(b2, DoubleArrayBag)):
            raise TypeError("b2 not instance of DoubleArrayBag")
        # end if
        if b1.size() + b2.size() > DoubleArrayBag.MAX_CAPACITY:
            raise MemoryError("union would cause bag to overflow")
        # end if

        # Task here is to create a new DoubleArrayBag object and then
        # copy all the elements out of b1 and b2 into the new object.
        # If we reach this part of the logic, we know that we have room
        # to do so.  Logic is somewhat similar to logic of plusEquals.
        # STUDENT CODE HERE TO ACCOMPLISH ABOVE GOES BELOW
        newBag = DoubleArrayBag()

        newBag.plusEqual(b1)
        newBag.plusEqual(b2)

        return newBag
    # end union

    def __str__(self) -> str:
        '''
        renders a string representation of the bag

        Postcondition:  bag remains in state it was in prior to call

        Returns:        a string representing current state of the bag
        '''
        result = "DoubleArrayBag with "
        result += str(self.__used)
        result += " elements: ["
        if self.__used > 0:
            result += str(self.__data[0])
            for index in range(1, self.__used):
                result += ", " + str(self.__data[index])
            # end for
        # end if
        result += " ]"
        return result
    # end __str__

    def __repr__(self) -> str:
        return str(self)
    # end __repr__

# end class Bag


def main():

    ''' below testing is NOT formal testing -- rather it is ad hoc '''
    ages = DoubleArrayBag()
    print("%s %s" % ("After constructor: ", ages))

    ages = DoubleArrayBag()
    print("%s %s" % ("After constructor: ", ages))
    ages.insert(47)
    ages.insert(33)
    ages.insert(17)
    ages.insert(2147483647)
    ages.insert(-2147483648)
    ages.insert(99)
    print("%s\n%s" %
          ("After inserting 47, 33, 17, 2147483647, 2147483648 and 99", ages))
    ages.erase(17)
    print("%s\n%s" % ("After attempting to remove 17", ages))
    ages.erase(99)
    print("%s\n%s" % ("After attempting to remove 99", ages))
    ages.erase(2147483647)
    print("%s\n%s" % ("After attempting to remove 2147483647", ages))
    ages.erase(47)
    print("%s\n%s" % ("After attempting to remove 47", ages))

    a = ages.copy()
    print("%s\n%s" % ("a after being copied from ages", a))
    print("%s %s" % (" a == ages ", str(a == ages)))
    print("%s %s" % (" not a is ages ", str(a is not ages)))

    b = DoubleArrayBag.union(ages, a)
    print("%s\n%s" % ("b after b = DoubleArrayBag.union( ages, a )", b))
# end main


if __name__ == "__main__":
    main()
# end if
