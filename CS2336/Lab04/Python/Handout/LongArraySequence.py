#!/usr/bin/env python3
import array
from typing import TypeVar

LongArraySequence = TypeVar('LongArraySequence')
ElementType = int


class LongArraySequence(object):
    '''
    An LongArraySequence is a collection of whole numbers for which ordering
    of elements is maintained via all operations

    Limitations:
        1.  The capacity of an LongArraySequence can grow to an arbitrarily
            large number of items after the initial sequence creation.  This
            growth is limited by the amount of available free dynamic
            memory allowed by Python.
        2.  Because of the linear algorithms used in this implementation.
            large LongArraySequences will have poor performance.
        3.  For efficiency, one should create an LongArraySequence with a
            capacity near what the user expects.  This avoids unnecessary
            dynamic memory allocation.
    '''

#   Class Invariant:
#       1.  The number of elements in the sequence is stored in the
#           instance variable __used which can be no greater than
#           len(__data)
#       2.  For an empty sequence, we do not care what is stored in
#           any element of __data.  For a non-empty sequence, the elements
#           are stored at __data[0] through __data[used-1] and we don't
#           care what is stored in any elements beyond that.
#       3.  The array __data can store only long or int items.  Attempts
#           to store items of other types will cause a run-time error
#           to be raised.
#       4.  The relative ordering of items placed in this sequence is
#           maintained through all operations and is controlled by the
#           client of an object of this class.
#       5.  If there is a current element of the sequence, it is "pointed"
#           to by the instance variable __cursor.  This cursor is defined
#           if its value is in the range 0 .. used-1.  Otherwise, it is
#           undefined.

    # ***************************************************************
    #                   CONSTRUCTORS
    # ***************************************************************
    def __init__(self, initialCapacity: int = 1) -> LongArraySequence:
        '''
        Precondition:
            initialCapacity must be strictly positive

        Postcondition:
            The sequence has been initialized
            and is in a well-defined, empty
            state with initialCapacity elements for
            storage

        Args:
            initialCapacity (int)

        Raises:
            TypeError if initialCapacity not an integer,
            ValueError if initialCapacity not > 0, and
            MemoryError if not able to allocate space to
            accommodate initialCapacity items
        '''
        if not(isinstance(initialCapacity, int)):
            raise TypeError("initialCapacity must be an integer")
        # end if
        if not initialCapacity > 0:
            raise ValueError("initialCapacity must be > 0")
        # end if

        self.__used = 0
        self.__cursor = 0
        try:
            self.__data = array.array('l', range(0, initialCapacity))
        except MemoryError:
            raise MemoryError("Could not accommodate capacity request")
        # end try/except

        # print("In constructor: " + str(self.__data) )
    # end __init__

    def copy(self) -> LongArraySequence:
        '''
        this is the copy constructor that makes an exact copy of self
        and returns the copy with exactly the capacity to hold the
        all the elements of this object -- thus, capacity of copy
        may be smaller than capacity of self

        Postcondition:
            creates a new sequence with the capacity to hold
            all the defined elements of this sequence

        Return:
            a new LongArraySequence object that contains a copy of all
            the elements in self

        Raises:
            MemoryError if dynamic memory allocation fails
        '''
        # if self.__used  == 0:
        #     newCapacity = 1
        # else:
        #     newCapacity = self.__used
        # end if
        newCapacity = 1 if self.__used == 0 else self.__used
        newSequence = LongArraySequence(newCapacity)

        # STUDENT IMPLEMENTATION GOES HERE
        # all attributes of self must be copied into new sequence

        return newSequence
    # end copy

    # ***************************************************************
    #                   ACCESSORS
    # ***************************************************************
    def __hash__(self) -> int:
        '''
        Return:
            a hash of all elements of self
        '''
        hashValue = hash(self.__used)
        index = 0
        while index < self.__used:
            element = self.__data[index]
            hashValue = 101 * hashValue + hash(element)
            index += 1
        # end while
        return hashValue
    # end __hash__

    def __eq__(self, other: LongArraySequence) -> bool:
        '''
        compare self with an other LongArraySequence -- this means
        that collection of items in self are pairwise equal to items
        in other and that the cursors of self and other are equal

        Precondition:
            other must be an instance of LongArraySequence

        Postcondition:
            if a == b, then b == a
            a == a is True
            a == None is False
            if a == b, then hash(a) == hash(b)

        Args:
            other must be an instance of LongArraySequence

        Return:
            boolean whether self is equal to other
        '''
        if other is None:
            return False
        # end if

        if not isinstance(other, self.__class__):
            return False
        # end if

        isEqual = True  # assume equality

        hashEqual = hash(self) == hash(other)
        if not hashEqual:
            return False
        # end if

        # STUDENT IMPLEMENTATION GOES HERE
        # must ensure that all attributes of self and other
        # match including testing whether elements in the
        # data arrays are pair-wise equal

        return isEqual
    # end __eq__

    def __ne__(self, other: LongArraySequence) -> bool:
        '''
        compare self with an other LongArraySequence

        Precondition:
            other must be an instance of LongArraySequence

        Postcondition:
            if a != b, then b != a
            a != a is False
            a != None is True
            if a != b, then hash(a) != hash(b)

        Args:
            other must be an instance of LongArraySequence

        Return:
            boolean whether self is not equal to other
        '''
        return not self == other

    # end __ne__

    def __str__(self) -> str:
        '''
        renders a string representation of the sequence

        Postcondition:
            sequence remains in state it was in prior to call

        Return:
            a string representing current state of the sequence
        '''
        result = "LongArraySequence: "
        result += str(self.__used)
        result += " items"
        result += "-->[ "
        if self.__used > 0:
            if self.__cursor == 0:
                result += "^"
            result += str(self.__data[0])
            for index in range(1, self.__used):
                result += ", "
                if index == self.__cursor:
                    result += "^"
                result += str(self.__data[index])
            # end for
        # end if
        result += " ]"
        result += " Capacity: " + str(len(self.__data))
        return result
    # end __str__

    def __repr__(self) -> str:
        '''
        returns a string representation of this sequence

        Postcondition:
            sequence remains in state it was in prior to call

        Return:
            a string representation of this object
        '''
        return str(self)
    # end __repr__

    def size(self) -> int:
        '''
        returns an integer representing number of items in the sequence

        Postcondition:
            sequence remains in state it was in prior to call

        Return:
            an integer representing numbers of items in the sequence
        '''
        return self.__used
    # end size

    def getCapacity(self) -> int:
        '''
        returns an integer representing sequence's current capacity

        Postcondition:
            sequence remains in state it was in prior to call

        Return:
            an integer representing capacity of the sequence
        '''
        return len(self.__data)
    # end getCapacity

    def getCurrent(self) -> ElementType:
        '''
        returns value of element stored at position of sequence's cursor
        if the cursor is currently defined

        Postcondition:
            sequence remains in state it was in prior to call

        Return:
            the value in the container at the container's
            cursor position

        Raises:
            RuntimeWarning if cursor not currently defined
        '''
        if not self.isCurrent():
            raise RuntimeWarning("Sequence cursor beyond end of sequence")
        return self.__data[self.__cursor]
    # end getCurrent

    def isCurrent(self) -> int:
        '''
        Return:
            a boolean indicating whether the container's
            internal cursor is currently defined
        '''
        # STUDENT IMPLEMENTATION GOES HERE
        # replace below bogus return with correct logic that matches
        # class invariant
        return False

    # end isCurrent

    def __add__(self, other) -> LongArraySequence:
        '''
        this method overloads the + operator for concatenation meaning that
        this method combines all the elements in this sequence and all the
        elements in the other sequence by placing them in a new sequence and
        maintains the relative ordering of the two sequences

        Precondition:
            other must be an instance of LongArraySequence
            otherwise TypeError is raised

        Postcondition:
            a new LongArraySequence is created containing
            copies of all the elements in this sequence plus
            all the elements in other.  The new sequence's
            capacity is exactly equal to the number of
            elements in both sequences.  The enhanced sequence's
            cursor should be undefined.

        Args:
            other (LongArraySequence)

        Returns:
            a new sequence that is the combination of all elements in this
            sequence and the other sequence

        Raises:
            TypeError if other not an instance of LongArraySequence
            MemoryError if dynamic memory is exhausted
        '''
        if not isinstance(other, self.__class__):
            raise TypeError("argument to + NOT another LongArraySequence")
        # end if

        newCapacity = self.__used + other.__used
        if newCapacity < 1:
            newSequence = LongArraySequence(1)
        else:
           newSequence = LongArraySequence(newCapacity)
        # end if
        # STUDENT IMPLEMENTATION GOES HERE

        return newSequence
    # end __add__
    # ***************************************************************
    #                    MUTATORS
    # ***************************************************************
    def ensureCapacity(self, minimumCapacity: int) -> None:
        '''
        potentially increase capacity of the sequence

        Precondition:
            minimumCapacity must be an integer and > 0

        Postcondition:
            The sequence's capacity is at least minimumCapacity.
            If the capacity was already at or greater than
            minimumCapacity, then the capacity is left unchanged.
            If the capacity is increased, then all items of self.__data
            should be copied to new larger array.  The cursor should
            remain in the same position.

        Args:
            minimumCapacity (int)

        Raises:
            TypeError if minimumCapacity is not an integer
            ValueError if minimumCapacity not > 1
            MemoryError if dynamic memory exhausted

        '''
        if not isinstance(minimumCapacity, int):
            raise TypeError("minimumCapacity must be an integer")
        # end if
        if not minimumCapacity >= 1:
            raise ValueError("minimumCapacity must be >= 1")
        # end if

        if len(self.__data) < minimumCapacity:
            biggerArray = array.array("l", range(0, minimumCapacity))
            # STUDENT IMPLEMENTATION GOES HERE

        # end if
    # end ensureCapacity

    def trimToSize(self) -> None:
        '''
        reduce capacity to current size if there is excess capacity

        Postcondition:
            Capacity is reduced to current number of items
            in sequence or left unchanged if capacity equals to
            number of items in sequence.  If the capacity is reduced,
            the cursor should remain at the same relative position.

        Raises:
            MemoryError if not enough dynamic memory to
            support additional allocation

        '''
        if self.__used == 0:
            self.__data = array.array('l', [0])
            self.__cursor = 1  # make cursor undefined
            return
        # end if
        if len(self.__data) != self.__used:
            trimmedArray = array.array("l", range(0, self.__used))
            # STUDENT IMPLEMENTATION GOES HERE
        # end if
    # end trimToSize


    def start(self) -> None:
        '''
        repositions internal cursor to beginning of sequence

        Postcondition:
            internal cursor is placed at the first element of
            the sequence
        '''
        self.__cursor = 0
    # end start

    def advance(self) -> None:
        '''
        moves the internal cursor to the next element in the sequence

        Postcondition:
            the internal cursor is moved to the next element in
            the sequence

        Raises:
            IndexError if attempt to advance beyond end of sequence
        '''
        if self.__cursor == self.__used:
            raise IndexError("Attempted to advance cursor beyond end " +
                             "of sequence")
        else:
            self.__cursor += 1
        # end if
    # end advance

    def addBefore(self, newEntry: ElementType) -> None:
        '''
        adds newEntry to the LongArraySequence if capacity available;
        otherwise, capacity is expanded to accommodate addition -- newEntry
        placed in position immediately before the cursor's position
        or at beginning of sequence if there is no valid current position
        of cursor

        Precondition:
            newEntry must be an integer; otherwise,
            method raises a TypeError exception.

        Postcondition:
            If isCurrent() is True then newEntry is placed
            in sequence just before the entry at the cursor's
            position.  If not isCurrent(), then newEntry is
            placed at the beginning of the sequence. The
            internal cursor remains at same relative position
            and, thus, is "pointing" to the newEntry.

        Args:
            newEntry (int)

        Raises:
            TypeError is newElement not an int or long and
            MemoryError if not enough dynamic memory
            available to accommodate expansion
        '''
        if not(isinstance(newEntry, ElementType)):
            raise TypeError("newEntry must be an integer or long")
        # we must first check to see if we have enough room to add newEntry
        # and if not expand capacity by doubling capacity at each expansion
        # print("In addBefore before adding: " + str(self.__data) )
        if len(self.__data) == self.__used:
            self.ensureCapacity(self.__used * 2)
        # end if

        # if self.isCurrent():
        #     location = self.__cursor
        # else:
        #     location = 0
        # end if
        location = self.__cursor  if  self.isCurrent() else 0
        index = self.__used
        if index == 0:
            self.__data[index] = newEntry
        else:
            # STUDENT IMPLEMENTATION GOES HERE
            pass

        # end if

        # print("In addBefore after adding: " + str(self.__data) )
    # end addBefore

    def addAfter(self, newEntry: ElementType) -> None:
        '''
        adds newEntry to the LongArraySequence if capacity available;
        otherwise, capacity is expanded to accommodate addition -- newEntry
        placed in position immediately after the cursor's position
        or at end of sequence if there is no valid current position of cursor

        Precondition:
            newEntry must be an integer or long; otherwise,
            method raises a TypeError exception.

        Postcondition:
            If isCurrent() is True then newEntry is placed
            in sequence just after the entry at the cursor's
            position.  If not isCurrent(), then newEntry is
            placed at the end of the sequence.  The internal
            cursor "points" to this newEntry.  Otherwise, relative
            ordering of elements remains unchanged except for the
            newElement being inserted into the sequence.

        Args:
            newEntry (int)

        Raises:
            MemoryError if not enough dynamic memory
            available to accommodate expansion
        '''
        if not(isinstance(newEntry, ElementType)):
                raise TypeError("newEntry must be an integer or long")
        # we must first check to see if we have enough room to add newEntry
        # and if not expand capacity by doubling capacity at each expansion
        # print("In addAfter before adding ", str(self.__data) )
        if len(self.__data) == self.__used:
            self.ensureCapacity(self.__used * 2)
        # end if
        # if self.isCurrent():
        #     location = self.__cursor + 1
        # else:
        #     location = self.__used
        # end if
        location = self._cursor + 1 if self.isCurrent() else self.__used
        index = self.__used - 1
        # STUDENT IMPLEMENTATION GOES HERE

        # print("In addAfter after adding ", str(self.__data) )
    # end add

    def removeCurrent(self) -> None:
        '''
        removes element at position "pointed" to by the internal
        cursor

        Postcondition:
            repositions the container's elements so as to overwrite
            the element at the internal cursor's position.  Otherwise,
            relative ordering elements remains unchanged.  The cursor
            should reference the next element in the sequence, if it
            exists; otherwise, the cursor is undefined.

        Raises:
            ValueError if there is no current element to remove
        '''
        if not self.isCurrent():
            raise ValueError("No current element -- cannot complete removal")
        # end if
        self.__used -= 1
        # STUDENT IMPLEMENTATION GOES HERE

    # end remove

    # ***************************************************************
    #                    ITERATOR
    # ***************************************************************
    # define an iterator over all the elements in this sequence
    def __iter__(self):
        '''
        Initialize the iterator as a "forward" iterator by
        setting the iterator cursor to "point" to the first
        element in the container
        '''
        self.__iteratorIndex = 0
        return self
    # end __iter__

    def __next__(self) -> ElementType:
        '''
        Return a reference to next element of the container if it exists
        and advance the iterator cursor to the next element
        '''
        if self.__iteratorIndex >= self.__used:
            raise StopIteration
        else:
            nextValue = self.__data[self.__iteratorIndex]
            self.__iteratorIndex += 1
            return nextValue
        # end if
    # end __next__

# end class LongArraySequence


def main():

    ages = LongArraySequence(2)
    print("%s %s %s\n" % ("After constructor with request for capacity of 2: ",
                          ages, "and capacity should be 2"))
    stuff = LongArraySequence(500)
    print("%s %s %s\n" % ("After constructor with request for capacity 500: ",
                          stuff, "and capacity should be 500"))
    stuff.addBefore(11)
    print("%s\n%s\n" % ("stuff.addBefore(11) cursor should be on 11 at " +
                        "end of sequence", stuff))
    stuff.trimToSize()
    print("%s\n%s\n" % ("after stuff.trimToSize() cursor should be on 11 " +
                        "at end of sequence", stuff))

    ages.addAfter(47)
    print("%s\n%s\n" % ("ages.addAfter(47) cursor should be on 47 at end " +
                        "of sequence", ages))
    ages.addAfter(33)
    print("%s\n%s\n" % ("ages.addAfter(33) cursor should be on 33 at end " +
                        "of sequence", ages))
    ages.addAfter(17)
    print("%s\n%s\n" % ("ages.addAfter(17) cursor should be on 17 at end " +
                        "of sequence", ages))
    ages.addAfter(77)
    print("%s\n%s\n" % ("ages.addAfter(77) cursor should be on 77 at end " +
                        "of sequence", ages))
    ages.addAfter(3)
    print("%s\n%s\n" % ("ages.addAfter(3) should be on 3 at end of sequence",
                        ages))
    ages.addAfter(1)
    print("%s\n%s\n" % ("ages.addAfter(1) cursor should be on 1 at end " +
                        "of sequence", ages))

    ages.addBefore(99)
    print("%s\n%s\n" % ("After ages.addBefore(99) cursor should be on 99 " +
                        "and the 99 before the 1", ages))
    ages.removeCurrent()
    print("%s\n%s\n" % ("After attempting removeCurrent() and the 99 " +
                        "should be removed and cursor on the 1", ages))
    ages.removeCurrent()
    print("%s\n%s\n" % ("After attempting removeCurrent() and the 1 should "
                        "be removed with no cursor", ages))

    try:
        print("Attempting ages.removeCurrent() -->", end='')
        ages.removeCurrent()
    except ValueError as err:
        print("Should have failed and did: ", str(err), "\n")
    # end try/except

    ages.start()
    ages.removeCurrent()
    print("%s\n%s\n" % ("ages after attempting start() and then " +
                        "removeCurrent() cursor should be at 33", ages))

    a = ages.copy()
    print("%s\n%s\n" % ("a after being copied from ages", a))
    print("%s %s" % (" a == ages should be True ", str(a == ages)))
    print("%s %s" % (" a is not ages should be True", str(a is not ages)))
    a.advance()
    print("%s\n%s\n" % ("a after a.advance cursor should be at 17", a))

    negatives = LongArraySequence(1)
    for index in range(-10, 0):
        negatives.addAfter(index)
    # end for
    print("%s\n%s\n" % ("negatives with ten negatives from -10 to -1, cursor" +
                        " should be on -1, capacity should be 16", negatives))

    b = a + negatives
    print("%s\n%s\n" % ("b after b = ages.concatenation( a ) cursor should " +
                        "be at 33 and capacity 14", b))
    b.addBefore(99)
    print("%s\n%s\n" % ("b after b.addBefore(99) -- cursor should be at 99 " +
                        "and 99 first element in sequence and capacity 28", b))

    b.start()
    while b.isCurrent():
        b.removeCurrent()
    # end while
    print("%s \n%s\n" % ("b after b.start() and then repeatedly removing " +
                         "current item -- should have 0 items, capacity " +
                         "remains at 28", b))

    b.trimToSize()
    print("%s\n%s\n" % ("b after b.trimToSize() -- should have 0 items. " +
                        "Capacity should be the minimum of 1", b))
    b.addBefore(7)
    print("%s\n %s\n" % ("b after b.addBefore(7)", b))
    b.removeCurrent()
    b.addAfter(7)
    print("%s\n %s\n" % ("b after b.removeCurrent() and b.addAfter(7)", b))

    b.removeCurrent()
    print("%s %s\n" % ("b after b.removeCurrent().  b.isCurrent() should be " +
                       "False and it is?", b.isCurrent()))

    ''' demonstrate ways to iterate over elements '''
    print("Using class methods to iterate over all elements in the sequence")
    negatives.start()
    ok = True
    while ok and negatives.isCurrent():
        print("%d " % negatives.getCurrent(), end='')
        try:
            negatives.advance()
        except IndexError:
            print("\n %s" % "Should have printed all ten negatives and " +
                            "advanced beyond end of sequence")
            ok = False
        # end try/except
    # end while
    print("\n")

    print("Using the class defined iterator explicitly")
    more = True
    it = iter(negatives)
    while more:
        try:
            print("%d " % next(it), end="")
        except StopIteration:
            more = False
        # end try/except
    # end while
    print("\n")

    print("Using the special for/in loop")
    for number in negatives:
        print("%d " % number, end="")
    # end for
    print("\n")

    exit()
# end main


if __name__ == "__main__":
    main()
# end if

