#!/usr/bin/env python3
''' FILE: statexam.py
    This program calls five test functions to test the Statistician class
    Maximum number of points from this program is 100.
'''
import math
from Statistician import Statistician


def close(first, second):
    '''determines if two floating point values are close enough
       to one another to be considered equal
    '''
    epsilon = 1e-5
    return math.fabs(first-second) < epsilon
# end close


def test1():
    '''Test function for basic Statistician functions;
       returns 51 if everything goes okay; otherwise returns 0.
    '''
    stat1 = Statistician()
    stat2 = Statistician()

    points = 0
    checker = 0.0
    checkerSq = 0.0

    if stat1.length() != 0 or stat2.length() != 0:
        return 0
    points += 2

    if stat1.sumX() != 0 or stat2.sumX() != 0:
        return 0
    points += 2

    if not math.isnan(stat1.mean()):
        return 0
    points += 3

    for i in range(1, 10001):
        stat1.next_number(i)
        checker += i
        checkerSq += (i*i)

    if stat2.length() != 0 or stat2.sumX() != 0:
        return 0
    points += 2

    if stat1.length() != 10000:
        return 0
    points += 4

    if not close(stat1.sumX(), checker):
        return 0
    points += 4

    if not close(stat1.mean(), float(checker)/10000):
        return 0
    points += 5

    stdDev = math.sqrt( float(checkerSq)/10000 - (float(checker)/10000)**2 )

    if not close(stat1.stdDev(), stdDev):
        return 0
    points += 6

    # Reset and then retest everything
    stat1.reset()
    stat2.reset()
    checker = 0
    checkerSq = 0

    if stat1.length() != 0 or stat2.length() != 0:
        return 0
    points += 2

    if stat1.sumX() != 0 or stat2.sumX() != 0:
        return 0
    points += 2

    if not math.isnan(stat1.mean()):
        return 0
    points += 4

    for i in range(1, 10001):
        stat1.next_number(i)
        checker += i
        checkerSq += (i*i)

    if stat2.length() != 0 or stat2.sumX() != 0:
        return 0
    points += 2

    if stat1.length() != 10000:
        return 0
    points += 2

    if not close(stat1.sumX(), checker):
        return 0
    points += 2

    if not close(stat1.mean(), float(checker)/10000):
        return 0
    points += 3

    stdDev = math.sqrt( float(checkerSq)/10000 - (float(checker)/10000)**2 )

    if not close(stat1.stdDev(), stdDev):
        return 0
    points += 6

    print("points from this test %d" % points)
    return points
# end test1


def test2():
    '''Test function for smallest/largest Statistician functions.

       Returns 10 if everything goes okay; otherwise 0
    '''
    stat1 = Statistician()
    stat2 = Statistician()
    stat3 = Statistician()

    points = 0

    if stat1.length() != 0 or stat2.length() != 0 or stat3.length() != 0:
        return 0
    points += 1

    if stat1.sumX() != 0 or stat2.sumX() != 0 or stat3.sumX() != 0:
        return 0
    points += 1

    if not math.isnan(stat1.smallest()):
        return 0
    points += 1

    if not math.isnan(stat1.largest()):
        return 0
    points += 1

    test_value = 1.39804e-76
    test_value = 1/test_value
    stat1.next_number(test_value)
    if stat1.smallest() != test_value or stat1.largest() != test_value:
        return 0
    points += 2

    test_value *= -1
    stat2.next_number(test_value)
    if stat2.smallest() != test_value or stat2.largest() != test_value:
        return 0
    points += 2

    stat3.next_number(100)
    stat3.next_number(-1)
    stat3.next_number(101)
    stat3.next_number(3)
    if stat3.smallest() != -1 or stat3.largest() != 101:
        return 0
    points += 2

    print("points from this test %d" % points)
    return points
# end test2


def test3():
    '''Test function for the == and != methods of the Statistician.
       Returns 12 if everythin okay; otherwise, returns 0
    '''
    stats = Statistician()
    statt = Statistician()
    statu = Statistician()
    statv = Statistician()
    statw = Statistician()
    statx = Statistician()

    points = 0

    statt.next_number(10)

    statu.next_number(0)
    statu.next_number(10)
    statu.next_number(10)
    statu.next_number(20)

    statv.next_number(5)
    statv.next_number(0)
    statv.next_number(20)
    statv.next_number(15)

    statw.next_number(0)

    statx.next_number(0)
    statx.next_number(0)

    if stats != stats:
        print("Failed test: stats != stats")
        return 0
    points += 2

    if stats == statt:
        print("Failed test: stats == statt")
        return 0
    points += 2

    if statt == stats:
        print("Failed test: statt == stats")
        return 0
    points += 2

    if statu == statt:
        print("Failed test: statu == statt")
        return 0
    points += 2

    if statu == statv:
        print("Failed test: statu == statv")
        return 0
    points += 2

    if statw == statx:
        print("Failed test: statw == statx")
        return 0
    points += 2

    print("points from this test %d" % points)
    return points

# end test3()


def test4():
    '''Test function for the union method
       of the Statistician.
       Return 13 if everything is okay; otherwise, returns 0
    '''
    stats = Statistician()
    statt = Statistician()
    statu = Statistician()
    statv = Statistician()

    points = 0

    if stats.length() != 0 or statt.length() != 0 or \
       statu.length() != 0 or statv.length() != 0:
        return 0
    if stats.sumX() != 0 or statt.sumX() != 0 or\
       statu.sumX() != 0 or statv.sumX() != 0:
        return 0

    statt.next_number(5)
    statu.next_number(0)
    statu.next_number(10)
    statu.next_number(10)
    statu.next_number(20)

    statv = Statistician.union(stats, stats)
    if statv.length() != 0 or statv.sumX() != 0:
        return 0
    points += 1
    # print("Success with statv = union(stats, stats)")

    statv = Statistician.union(stats, statu)
    if statu != statv:
        return 0
    points += 1
    # print("Success with statv = union(stats, statu)")

    statv = Statistician.union(statt, stats)
    if statt != statv:
        return 0
    points += 1
    # print("Success with statv = union(statt, statu)")

    statv = Statistician.union(statt, statu)
    if statv.length() != 5:
        return 0
    if not close(statv.sumX(), 45):
        return 0
    if statv.smallest() != 0:
        return 0
    if statv.largest() != 20:
        return 0
    if not close(statv.mean(), 45.0/5):
        return 0
    points += 1
    # print("Success with statv = union(statt, statu)")

    statv = Statistician.union(statv, statt)
    if statv.length() != 6:
        return 0
    if not close(statv.sumX(), 50):
        return 0
    if statv.smallest() != 0:
        return 0
    if statv.largest() != 20:
        return 0
    if not close(statv.mean(), 50.0/6):
        return 0
    points += 1
    # print("Success with statv = union(statv, statt)")

    statv = None
    try:
        statv = Statistician.union(statv, statt)
        return 0
    except TypeError:
        points += 4

    statv = 101
    try:
        statv = Statistician.union(statv, statt)
        return 0
    except TypeError:
        points += 4

    print("points from this test %d" % points)
    return points

# end test4


def test5():
    '''Test function for the Statistician scale method.
       Returns 14 if everything goes okay; otherwise, returns 0
    '''
    stats = Statistician()
    statt = Statistician()
    statu = Statistician()
    statv = Statistician()

    if stats.length() != 0 or statt.length() != 0 or \
       statu.length() != 0 or statv.length() != 0:
        return 0
    if stats.sumX() != 0 or statt.sumX() != 0 or\
       statu.sumX() != 0 or statv.sumX() != 0:
        return 0

    points = 0

    statu.next_number(0)
    statu.next_number(10)
    statu.next_number(10)
    statu.next_number(20)

    stats = Statistician.scale(2, statu)
    if stats.length() != 4:
        return 0
    if not close(stats.sumX(), 80):
        return 0
    if stats.smallest() != 0:
        return 0
    if stats.largest() != 40:
        return 0
    if not close(stats.mean(), 80.0/4):
        return 0
    points += 2

    stats = Statistician.scale(-2, statu)
    if stats.length() != 4:
        return 0
    if not close(stats.sumX(), -80):
        return 0
    if stats.smallest() != -40:
        return 0
    if stats.largest() != 0:
        return 0
    if not close(stats.mean(), -80.0/4):
        return 0
    points += 8

    stats = Statistician.scale(0, statu)
    if stats.length() != 4:
        return 0
    if not close(stats.sumX(), 0):
        return 0
    if stats.smallest() != 0:
        return 0
    if stats.largest() != 0:
        return 0
    if not close(stats.mean(), 0):
        return 0
    points += 2

    stats = Statistician.scale(10, statt)
    if stats.length() != 0:
        return 0
    if not close(stats.sumX(), 0):
        return 0
    points += 2

    print("points from this test %d" % points)
    return points

# end test5


def main():
    ''' driver for the test functions. '''
    value = 0
    result = 0

    print("Running Statistician tests:")

    print("TEST 1:")
    print("Testing next_number, reset, length, sumX, and mean,\n" +
          "and standard deviation (51 points).")
    result = test1()
    value += result
    if result > 0:
        print("Test 1 passed\n")
    else:
        print("Test 1 failed\n")

    print("TEST 2:")
    print("Testing smallest and largest methods (10 points).")
    result = test2()
    value += result
    if result > 0:
        print("Test 2 passed\n")
    else:
        print("Test 2 failed\n")

    print("TEST 3:")
    print("Testing == and != methods (12 points).")
    result = test3()
    value += result
    if result > 0:
        print("Test 3 passed\n")
    else:
        print("Test 3 failed\n")

    print("TEST 4:")
    print("Testing union method (13 points).")
    result = test4()
    value += result
    if result > 0:
        print("Test 4 passed\n")
    else:
        print("Test 4 failed\n")

    print("TEST 5:")
    print("Testing scale method (14 points).")
    result = test5()
    value += result
    if result > 0:
        print("Test 5 passed\n")
    else:
        print("Test 5 failed\n")

    print("If you submit the Statistician to Prof. Haiduk now")
    print("this part of the grade will be %d points out of 100" % value)
# end main


if __name__ == "__main__":
    main()
# end if
