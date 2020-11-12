from typing import TypeVar

Stack = TypeVar('Stack')

class stack:
    '''
    This Stack is a fixed-sized stack whose
    capacity is determined by the constructor and for
    which no capacity adjustments are provided.  The
    element type of this stack is int
    '''
    # Class invariant:
    #   This implementation of a stack mimics that way stacks grow in
    #   memory of modern day operating systems.  That means that some
    #   amount of space is allocated arbitrarily -- in this case using
    #   a Python list with all cells initialized to None.  The list
    #   is named __data.  An index named __top indicates which
    #   stack element is at the "top" of the stack if there are any items
    #   on the stack.  This __top is initially set to the length of the
    #   __data list which is effectively the "bottom" if the stack.
    #
    #   The stack is empty if __top == __capacity
    #
    #   push does the following (assuming stack not full):
    #       __top is decremented
    #       element to be pushed is copied onto stack at index __top position
    #
    #   pop does the following (assuming stack not empty):
    #       the element at index __top is copied into a variable to be returned
    #       the reference at index __top is set to None
    #       __top is incremented
    #       the copied element is returned to the caller
    #
    #   top does the following (assuming stack not empty):
    #       returns a reference to the top element on the stack
    #
    def __init__(self, size=1):
        '''
        This constructor defines three attributes:
             (1) __data -- a reference to whatever is chosen
                 to manage the elements on the stack,
             (2) __top -- a reference to the top item of the
                 stack, and
             (3) __capacity -- how many elements could potentially
                 be defined on the stack

        Preconditions:  size is an integer and size >= 1

        Raises:  ValueError if size is None
                 TypeError if size is not int
                 ValueError if size < 1
        '''
        # STUDENTS Enforce the Preconditions

        self.__data = [None] * size   # creates list of size elements
        self.__top  = len(self.__data)
        self.__capacity = size
    # end __init__

    def is_empty(self) -> bool:
        '''
        returns true if the state of the stack indicates
        that it is managing no elements; false otherwise
        '''
        pass
    # end is_empty

    def is_full(self) -> bool:
        '''
        returns true if the state of the stack indicates
        that the stack is full--the number of elements on
        the stack equals the stack capacity; false otherwise
        '''
        pass
    # end is_full

    def push(self, element:int) -> None:
        '''
        alters the state of the stack by adjusting the top of stack
        and copying reference to element to the new top

        Preconditions:  stack must NOT be full
                        value of element must NOT be None
                        type of element MUST be int

        Raises:  RuntimeError if the stack is full
                 ValueError if element is None
                 TypeError if the type of element is not int
        '''
        # STUDENTS Enforce the Preconditions and then implement
        # according to class invariant
        pass
    # end push

    def pop(self) -> int:
        '''
        returns a copy of the top element on the stack and adjusts top

        Precondition: stack must NOT be empty

        Postcondition: copy of reference to top element returned
                       and the reference in stack set to None

        Raises:  RuntimeError if stack is empty
       '''
        # STUDENTS Enforce the Preconditions and then implement
        # according to class invariant
        pass
    # end pop

    def top(self) -> int:
        '''
        returns a copy of the top element on the stack
        but does NOT adjust top

        Precondition:  stack must NOT be empty

        Raises:  RuntimeError if stack is empty
        '''
        # STUDENTS Enforce the Preconditions and then implement
        # according to class invariant
        pass
    # end top

    def size(self) -> int:
        '''
        returns the number of elements on the stack
        '''
        pass
    # end size

    def reset(self) -> None:
        '''
        returns the stack to its state as defined by its
        constructor -- should do NO data allocation

        Postcondition:  stack in same state as a newly constructed stack
        '''
        # NOTE:  all cells of the list should be set to None
        pass
    # end reset

    def __str__(self) -> str:
        '''
        returns a human-readable representation of the
        stack to support debugging
        '''
        # NOTE:  Use previous code provided to you to generate
        #        a string that is a reasonable, human-readable
        #        representation of the stack -- don't show those
        #        cells of the stack beyond top
        pass
    # end __str__
# end stack
