#!/usr/bin/env python3

from stack import Stack,stack


def tower_range(aStack : Stack) -> int :
    '''this function is given a stack as an argument with
    the assumptions:
        (1) the stack contains the height of at least one tower
        (2) any subsequent heights on the stack represent heights
            of towers to the left of top height
        (3) the function returns the range of the tower that was
            on top of the stack at entry to this function
        (4) minimum range is 1 as defined in problem statement
    '''
    pass
# end range


def main():
    '''This function sets up all the data structures required
    for solution of the problem and inputs the data.

    NOTE: Once the first item of data (N) is input, we know the
    capacity needed for all the data structures.

    Data structures needed are:

        H -- a list of N elements
        heightStack -- a stack with capacity of N
    '''
    heightStack = stack(100)

    pass

# end main

if __name__ == "__main__":
    main()
# end if



