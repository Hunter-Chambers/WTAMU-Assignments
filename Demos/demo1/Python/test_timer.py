#!/usr/bin/env python3
'''
    To execute this program:
    ./testTimer.py
'''

from timer import Timer


def main():
    '''simple program to test the timer'''

    print("Setting timer for 5 seconds")
    my_timer = Timer()
    my_timer.run_timer(5)

    print("Timer ran for " + str(my_timer) + " seconds")

    exit(0)
# end main


if __name__ == "__main__":
    main()
