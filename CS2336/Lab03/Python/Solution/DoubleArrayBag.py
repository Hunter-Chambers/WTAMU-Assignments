#!/usr/bin/env python3

import array    # provides access to primitive arrays
from typing import TypeVar

DoubleArrayBag = TypeVar('DoubleArrayBag')
Number = TypeVar('Number', int, float)


class DoubleArrayBag(object):
    '''
    An DoubleArrayBag is a collection of double numbers

    Limitations:
        1.  The capacity of an DoubleArrayBag can grow to an arbitrarily
            large number of items after the initial bag creation.  This
            growth is limited by the amount of available freee dynamic
            memory allowed by Python.
        2.  Because of the linear algorithms used in this implementation.
            large DoubleArrayBags will have poor performance.
        3.  For efficiency, one should create an DoubleArrayBag with a
            capacity near what the user expects. This avoids unnecessary
            dynamic memory allocation.
    '''
#   Class Invariant:
#       1.  The number of elements in the bag is stored in the
#           instance variable __used which can be no greater than
#           len(__data)
#       2.  For an empty bag, we do not care what is stored in
#           any element of __data.  For a non-empty bag, the elements
#           are stored at __data[0] through __data[__used-1]
#           and we don't care what is stored in any elements beyond
#           that.
#       3.  The array __data can store only double items.  Attempts
#           to store items of other types will cause a run-time error
#           to be raised.
#       4.  Capacity of this bag at any given time is len(self.__data)

    def __init__(self, initialCapacity: int = 1) -> None:
        '''
        Precondition:   initialCapacity must be > 0

        Postcondition:  The bag has been initialized
                        and is in a well-defined, empty
                        state with initialCapacity elements for
                        storage

        Raises:         MemoryError if not able to allocate space to
                        accommodate initialCapacity items
        '''
        if initialCapacity is None or type(initialCapacity) != int:
            raise TypeError("initialCapacity must be an int non-null")
        if initialCapacity < 1:
            raise ValueError("initialCapacity must be > 0")

        self.__used = 0
        self.__data = array.array('d', range(0, initialCapacity))

    # end __init__

    def copy(self) -> DoubleArrayBag:
        '''
        this behaves like a copy constructor that makes a detailed copy of self

        NOTE: the capacity of the copy matches the elements in self unless
              the bag is empty, in which case it should be at least 1

        Postcondition:
            x.copy() is x returns false
            type(x.copy()) == type(x)
            x.copy() == x

            internal capacity of copy is just enough to hold self.__used
            items

        Returns:    a new DoubleArrayBag object that contains a copy of all
                    the elements in self and its capacity is equal to
                    number of elements in this bag

        Raises:     MemoryError if dynamic memory allocation fails
        '''
        copyCapacity = self.__used
        if (copyCapacity < 1):
            copyCapacity = 1
        # end if

        newBag = DoubleArrayBag(copyCapacity)

        # STUDENT IMPLEMENTATION GOES HERE
        #   must copy all elements from self.__data into
        #   the newBag and update any attributes to
        #   adhere to the class invariant

        for i in range(self.__used):
            newBag.__data[i] = self.__data[i]
        # end for

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
            x != None is True

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

        if not isinstance(other, self.__class__):
            return False
        # end if

        if self is other:
            return True
        # end if

        if self.__used != other.__used:
            return False
        # end if

        index = 0
        isEqual = True  # assume equality
        while isEqual and index < self.__used:
            if self.__data[index] != other.__data[index]:
                isEqual = False
            else:
                index += 1
        # end while
        return isEqual
    # end __eq__

    def __ne__(self, other: DoubleArrayBag) -> bool:
        return not self == other
    # end __ne__

    def ensureCapacity(self, newCapacity: int) -> None:
        '''
        potentially increase capacity of the bag

        Precondition:   newCapacity must be an integer and > 0

        Postcondition:  The bag's capacity is at least newCapacity.
                        If the capacity was already at or greater than
                        newCapacity, then the capacity is left
                        unchanged.

        Args:           newCapacity (int)

        Raises:         TypeError if newCapacity is not an integer
                        ValueError if newCapacity not > 1
                        MemoryError if dynamic memory exhausted
        '''
        if not isinstance(newCapacity, int):
            raise TypeError("newCapacity must be an integer")
        if not newCapacity >= 1:
            raise ValueError("newCapacity must be >= 1")

        if len(self.__data) < newCapacity:
            # STUDENT WORK GOES HERE
            #   create a new, bigger array with the capacity
            #   newCapacity, copy all the elements from
            #   self.__data into the new array, and then
            #   update the reference self.__data to refer
            #   to the new array

            new_data = array.array('d', range(0, newCapacity))

            for i in range(self.__used):
                new_data[i] = self.__data[i]
            # end for

            self.__data = new_data
        # end if
    # end ensureCapacity

    def getCapacity(self) -> int:
        '''
        Returns:  an integer representing capacity of the bag.
        '''
        return len(self.__data)
    # end getCap acity

    def trimToSize(self) -> None:
        '''
        reduce capacity to current size if there is excess capacity

        Postcondition:  Capacity is reduced to current number of items
                        in bag or left unchanged if capacity equals to
                        number of items in bag

        Raises:         MemoryError if not enough dynamic memory to
                        support additional allocation

        '''
        if self.__used < len(self.__data):
            if self.__used <= 1:
                newCapacity = 1
            else:
                newCapacity = self._used
            # end if
            # STUDENT WORK HERE
            #   must create a new smaller array with capacity
            #   newCapacity, copy all the items from self.__data
            #   into the new array, and then update the reference
            #   self.__data to refer to the new array

            new_data = array.array('d', range(0, newCapacity))

            for i in range(self.__used):
                new_data[i] = self.__data[i]
            # end for

            self.__data = new_data
        # end if
    #  end trimToSize

    def insert(self, newEntry: Number) -> None:
        '''
        adds newEntry to the DoubleArrayBag if capacity available;

        Precondition:  newEntry must be a double; otherwise,
                       method raises a TypeError exception.

        Postcondition: newEntry is placed in Bag just after
                       items already existing and self.__used
                       is incremented by 1.

        Args:          newEntry (float)

        Raises:        MemoryError if not enough memory to make insertion

        '''
        if not (isinstance(newEntry, float) or (isinstance(newEntry, int))):
                raise TypeError("newEntry must be a double")
        # end if

        if  self.__used >= len(self.__data):
        # STUDENT WORK HERE
        #   must ensure that we have the capacity to hold the
        #   newly inserted item -- if not double the current
        #   capacity; then do a normal insertion

            self.ensureCapacity(len(self.__data) * 2)
        # end if

        self.__data[self.__used] = newEntry
        self.__used += 1
    # end add

    def __iadd__(self, addend: DoubleArrayBag) -> DoubleArrayBag:
        '''
        an implementation of the += operation

        Precondition:   addend is an instance of DoubleArrayBag and is not None

        Postcondition:  the elements from addend have been added to this bag

        Raises:         MemoryError if not enough memory
                        available to accommodate operation

                        TypeError if added is None or is not an instance of
                        DoubleArrayBag
        '''
        if (addend is None) or not(isinstance(addend,  self.__class__)):
            raise TypeError("addend is None or not instance of DoubleArrayBag")
        # end if
        # STUDENT WORK HERE
        #   guarantee that we have exactly enough room to hold all the items
        #   from addend and then copy them into this bag; then update any
        #   attributes to ensure that we are maintaining the class
        #   invariant

        if (addend.__used > 0):
            self.ensureCapacity(self.__used + addend.__used)

            amt = addend.__used
            for i in range(amt):
                self.__data[self.__used] = addend.__data[i]
                self.__used += 1
            # end for
        # end if

        return self
    # end plusEqual

    def occurrences(self, target: Number) -> int:
        '''
        counts occurrences of target in the bag

        Precondition:   target must be a float; otherwise,
                        method raises a TypeError exception

        Postcondition:  bag's state is not changed

        Arg:            target (float)

        Returns:        an integer number of occurrences of
                        target in the bag
        '''
        if not (isinstance(target, float) or (isinstance(target, int))):
            raise TypeError("target must be a float")
        count = 0
        for index in range(0, self.__used):
            if self.__data[index] == target:
                count += 1
            # end if
        # end for
        #
        # Below statement is the "magic" and lazy way to count occurrences
        # return self.__data.count(target)
        return count
    # end occurrences

    def erase_one(self, target: Number) -> bool:
        '''
        removes one copy of target from the DoubleArrayBag if target exists
        in the bag

        Precondition:  target must be a float; otherwise,
                       method raises a TypeError exception.

        Postcondition: if target exists in the bag, the first
                       occurrence of target is removed and
                       used is decremented.  Otherwise,
                       the bag's state is the same as it was
                       before the call.

        Arg:           target which must be an int

        Returns:       False or True depending on whether
                       target can be removed.
        '''
        if not (isinstance(target, float) or (isinstance(target, int))):
            raise TypeError("target must be a float")
        # end if

        found = False
        index = 0
        while not found and index < self.__used:
            if target == self.__data[index]:
                found = True
            else:
                index += 1
            # end if
        # end while
        if found:
            # we know we found target at data[index]
            # now reduce used by 1 and copy the last element
            # onto data[index] since ordering doesn't matter
            self.__used -= 1
            self.__data[index] = self.__data[self.__used]
        # end if
        return found
    # end erase_one

    def erase(self, target: Number) -> int:
        '''
        removes all copies of target from the DoubleArrayBag if target
        exists in the bag

        Precondition:  target must be an integer; otherwise,
                       method raises a TypeError exception.

        Postcondition: if target exists in the bag, all
                       occurrences of target are removed and
                       used is decremented by number of
                       items removed.  Otherwise, the bag's state
                       is the same as it was before the call.

        Arg:           target which must be an int

        Returns:       an int representing number of items erased
        '''
        if not (isinstance(target, float) or (isinstance(target, int))):
            raise TypeError("target must be a float")
        # end if

        number_removed = 0
        index = 0
        while index < self.__used:
            if self.__data[index] == target:
                self.__used -= 1
                self.__data[index] = self.__data[self.__used]
                number_removed += 1
            else:
                index += 1
            # end if
        # end while

        return number_removed
    # end erase

    @staticmethod
    def union(b1, b2) -> DoubleArrayBag:
        '''
        this method combines all the elements in this bag and all the
        elements in the other bag by placing them in a new bag

        Also note that this is a class method NOT an instance method

        Precondition:       b1 and b2 must be an instance of DoubleArrayBag
                            otherwise TypeError is raised

        Postcondition:      a new DoubleArrayBag is created containing copies
                            of all the elements in b1 plus all the elements
                            in b2.

        Args:               b1 and b2 (DoubleArrayBag)

        Raises:             TypeError if b1 or b2 not an instance of
                            DoubleArrayBag

                            MemoryError if note enough dynamic memory
                            to handle the union

        Returns:            A new DoubleArray bag that is the logical union
                            of the two arguments
        '''

        if not(isinstance(b1, DoubleArrayBag)):
            raise TypeError("b1 not instance of DoubleArrayBag")
        # end if
        if not(isinstance(b2, DoubleArrayBag)):
            raise TypeError("b2 not instance of DoubleArrayBag")
        # end if

        # STUDENT WORK HERE
        #   We must create (and ultimately return) a new bag that has exactly
        #   the capacity needed to hold all items from both b1 and b2 and
        #   then copy the items from b1 and then b2 into the new bag -- caution,
        #   watch out when both bags are empty -- be sure to test this

        new_bag = b1.copy()
        new_bag += b2

        return new_bag
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
        result += " Capacity: " + str(self.getCapacity())

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
