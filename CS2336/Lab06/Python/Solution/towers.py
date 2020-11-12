#!/usr/bin/env python3

from stack import Stack,stack


def tower_range(aStack : Stack) -> int :
    '''this function is given a stack as an argument with
       the assumptions:
        (1) the stack contains the height of at least one tower
        (2) any subsequent heights on the stack represent heights
            of towers to the left of the tower height at top of stack
        (3) the function returns the range of the tower that was
            on top of the stack at entry to this function
        (4) minimum range is 1 as defined in problem statement
    '''
    tower = aStack.pop()
    t_range = 1

    while ((not aStack.is_empty()) and tower >= aStack.top()):
        t_range += 1
        aStack.pop()
    # end while

    return t_range
# end range


def main():
    '''This function sets up all the data structures required
    for solution of the problem and inputs the data.

    NOTE: Once the first item of data (N) is input, we know the
    capacity needed for all the data structures.

    Data structures needed are:

        height -- a list of N elements
        heightStack -- a stack with capacity of N
    '''
    N = int(input())
    height = [None] * N
    heightStack = stack(N)

    for i in range(N):
        height[i] = int(input())
    # end for

    for i in range(N):
        for j in range(0, i + 1):
            heightStack.push(height[j])
        # end for
        print(tower_range(heightStack), end=' ')
        #print(heightStack, "\n")
        heightStack.reset()
    # end for

    print()
# end main

if __name__ == "__main__":
    main()
# end if

