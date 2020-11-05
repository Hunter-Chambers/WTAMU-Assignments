from typing import TypeVar

Stack = TypeVar('Stack')

class stack:
    '''This Stack is a fixed-sized stack whose
       capacity is determined by the constructor and for
       which no capacity adjustments are provided.  The
       element type of this stack is int'''
    def __init__(self, size=1):
        '''This constructor defines three attributes:
             (1) __data -- a reference to whatever is chosen
                 to manage the elements on the stack,
             (2) __top -- a reference to the top item of the
                 stack, and
             (3) __capacity -- how many elements could potentially
                 be defined on the stack

           Preconditions:  size is an integer and size >= 1
        '''
        # Enforce the Preconditions
        self.__data = [None] * size   # creates list of size elements
        self.__top = -1
        self.__capacity = size
    # end __init__

    def is_empty(self) -> bool:
        '''returns true if the state of the stack indicates
        that it is managing no elements'''
        pass
    # end is_empty

    def push(self, element:int) -> None:
        ''' alters the state of the stack by copying element
        onto the stack and adjusting top

        Preconditions:  stack must NOT be full
                        value of element must NOT be None
                        type of element MUST be int
        '''
        pass
    # end push

    def pop(self) -> int:
        ''' returns a copy of the top element on the stack
        and ajusts top

        Precondition: stack must NOT be empty
       '''
        pass
    # end pop

    def top(self) -> int:
        '''returns a copy of the top element on the stack
        but does NOT adjust top

        Precondition:  stack must NOT be empty
        '''
        pass
    # end top

    def size(self) -> int:
        '''returns the number of elements on the stack'''
        pass
    # end size

    def reset(self) -> None:
        '''returns the stack to its state as defined by its
        constructor -- should do NO data allocation'''
        # NOTE:  all cells of the list should be set to None
        pass
    # end reset

    def __str__(self) -> str:
        '''returns a human-readable representation of the
        stack to support debugging'''
        # NOTE:  Use previous code provided to you to generate
        #        a string that is a reasonable, human-readable
        #        representation of the stack -- don't show those
        #        cells of the stack beyond top
        pass
    # end __str__
# end stack
