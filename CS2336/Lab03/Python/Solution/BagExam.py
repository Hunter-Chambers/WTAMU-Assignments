#!/usr/bin/env python3

from DoubleArrayBag import DoubleArrayBag
import random

POINTS = [100,  # Total points for all tests
          32,   # Test 1 points
          12,   # Test 2 points
          12,   # Test 3 points
          32,   # Test 4 points
          12    # Test 5 points
          ]
DESCRIPTION = [
               "Tests for the DoubleArrayBag Class",
               "Testing insert, size and occurrences",
               "Testing the copy method",
               "Testing the equals methods",
               "Testing erase and erase_one methods",
               "Testing += and union methods"
               ]


def correct(test, n):
    answer = True
    if test.size() != n:
        answer = False
    if answer:
        print("Test passed\n")
    else:
        print("Test failed\n")
    return answer
# end correct


def test1():
    '''
    **************************************************************************
     int test1( )
       Performs some basic tests of insert, size and occurrences
       functions. Returns POINTS[1] if the tests are passed. Otherwise 0.
    **************************************************************************
    '''

    TESTSIZE = 3000
    test = DoubleArrayBag()
    test_letter = 'A'

    print(test_letter + ". Testing size for an empty bag.")
    test_letter = chr(ord(test_letter) + 1)
    if not correct(test, 0):
        return 0
    # end if

    print(test_letter + ". Inserting 4 into the bag, and then testing")
    print("   size")
    test_letter = chr(ord(test_letter) + 1)
    test.insert(4)
    if not correct(test, 1):
        return 0
    # end if

    print(test_letter + ". Inserting 2 into the bag, and then testing")
    print("   size")
    test_letter = chr(ord(test_letter) + 1)
    test.insert(2)
    if not correct(test, 2):
        return 0
    # end if

    print(test_letter + ". Inserting 1 into the bag, and then testing")
    print("   size")
    test_letter = chr(ord(test_letter) + 1)
    test.insert(1)
    if not correct(test, 3):
        return 0
    # end if

    print(test_letter + ". Inserting 3 into the bag, and then testing")
    print("   size")
    test_letter = chr(ord(test_letter) + 1)
    test.insert(3)
    if not correct(test, 4):
        return 0
    # end if

    print(test_letter + ". Inserting another 2 into the bag, and then testing")
    print("   size")
    test_letter = chr(ord(test_letter) + 1)
    test.insert(2)
    if not correct(test, 5):
        return 0
    # end if
    print("   Then checking occurrences of 2")
    if test.occurrences(2) != 2:
        return 0
    else:
        print("Test passed.\n")
    # end if

    print(test_letter + ". Inserting 5, 6, and 7 into the bag, and then")
    print("   testing size")
    test_letter = chr(ord(test_letter) + 1)
    test.insert(5)
    test.insert(6)
    test.insert(7)
    if not correct(test, 8):
        return 0
    # end if

    print(test_letter + ". Inserting two more 2's into the bag")
    print("   and then checking occurrences of 2's")
    test_letter = chr(ord(test_letter) + 1)
    test.insert(2)
    test.insert(2)
    if test.occurrences(2) != 4:
        return 0
    else:
        print("Test passed.\n")
    # end if

    print(test_letter + ". Inserting " + str(TESTSIZE) +
          " random items between 0 and 49")
    print("   and then checking size")
    random.seed()
    for i in range(0, TESTSIZE):
        next = random.randint(0, 49)
        test.insert(next)
    # end for
    if not correct(test, TESTSIZE + 10):
        return 0
    # end if

    return POINTS[1]
# end test1


def test2():
    '''
    **************************************************************************
     int test2( )
       Performs some tests of the copy method and the == method
       Returns POINTS[2] if the tests are passed. Otherwise returns 0.
    **************************************************************************
    '''
    test = DoubleArrayBag()

    print("A.  Testing that copy method works okay for empty bag ...")
    copy1 = test.copy()
    if not correct(copy1, 0):
        return 0
    # end if

    print("B. Testing copy method with 4-item bag ...")
    test.insert(1)
    test.insert(1)
    test.insert(1)
    test.insert(1)
    copy2 = test.copy()
    #print("test is %s" % test)
    #print("copy2 is %s" % copy2)
    print("   and now testing equals method...")
    if not test == copy2 or not copy2 == test:
        print("Test failed.\n")
        return 0
    else:
        print("Test passed.\n")
    # end if
    test.insert(1)  # alter the original, but not the copy
    print("   then checking size of the copy")
    if not correct(copy2, 4):
        return 0
    # end if
    print("   then checking size of original after inserting another item")
    if not correct(test, 5):
        return 0
    # end if
    print("Copy method seems okay.")
    return POINTS[2]
# end test2


def test3():
    '''
    **************************************************************************
     int test3( )
       Performs some tests of the copy, == and != methods.
       Returns POINTS[3] if the tests are passed. Otherwise returns 0.
    **************************************************************************
    '''
    test = DoubleArrayBag()

    print("A. Testing that copy works okay for empty bag ...")
    copy1 = DoubleArrayBag()
    copy1.insert(1)
    copy1 = test.copy()
    if not correct(copy1, 0):
        return 0
    # end if

    print("B. Testing copy method with 4-item bag ...")
    test.insert(1)
    test.insert(1)
    test.insert(1)
    test.insert(1)
    copy2 = test.copy()
    print("   Adding another item to the original and testing occurrences ...")
    test.insert(1)  # Alter the orginal but not the copy
    if test.occurrences(1) != 5 or copy2.occurrences(1) != 4:
        print("Test failed")
        return 0
    # end if
    print("   And now testing sizes of copy and altered original")
    if not correct(copy2, 4):
        return 0
    # end if
    if not correct(test, 5):
        return 0
    # end if

    print("C. Testing copy for equality ...")
    copy2 = test.copy()
    if copy2 != test:
        print("Test failed.\n")
        return 0
    # end if
    if test != copy2:
        print("Test failed.\n")
        return 0
    # end if
    print("Test passed.\n")

    print("Copy, == and != seem okay")

    return POINTS[3]
# end test3


def test4():
    '''
    **************************************************************************
     int test4( )
       Performs basic tests for the erase_one and erase functions
       Returns POINTS[4] if the tests are passed.  Otherwise returns 0.
    **************************************************************************
    '''
    test = DoubleArrayBag()
    print("Testing erase from empty bag (should have no effect) ...")
    test.erase(0)
    if not correct(test, 0):
        return 0
    # end if

    print("Inserting these: 8 6 10 1 7 10 15 3 13 2 5 11 14 4 12")
    test.insert(8)
    test.insert(6)
    test.insert(10)
    test.insert(1)
    test.insert(7)
    test.insert(10)
    test.insert(15)
    test.insert(3)
    test.insert(13)
    test.insert(2)
    test.insert(5)
    test.insert(11)
    test.insert(14)
    test.insert(4)
    test.insert(12)
    if not correct(test, 15):
        return 0
    # end if
    print("Now testing capacity -- should be 16")
    if test.getCapacity() != 16:
        print("Test failed.")
        print("%s", test)
        return 0
    else:
        print("Test passed.\n")
    # end if

    print("Erasing 0 (which is not in bag, so bag should be unchanged) ...")
    if test.erase_one(0):
        print("Test failed.")
        return 0
    # end if
    if not correct(test, 15):
        return 0
    # end if

    print("Erasing the 6 ...")
    if test.erase(6) != 1:
        print("Test failed.")
        return 0
    # end if
    if not correct(test, 14):
        return 0
    # end if

    print("Erasing one 10 ...")
    if not test.erase_one(10):
        print("Test failed.")
        return 0
    # end if
    if not correct(test, 13):
        return 0
    # end if

    print("Erasing the 1 ...")
    test.erase(1)
    if not correct(test, 12):
        return 0
    # end if

    print("Erasing the 15 ...")
    test.erase(15)
    if not correct(test, 11):
        return 0
    # end if

    print("Erasing the 5 ...")
    test.erase(5)
    if not correct(test, 10):
        return 0
    # end if

    print("Erasing the 11 ...")
    test.erase(11)
    if not correct(test, 9):
        return 0
    # end if

    print("Erasing the 3 ...")
    test.erase(3)
    if not correct(test, 8):
        return 0
    # end if

    print("Erasing the 13 ...")
    test.erase(13)
    if not correct(test, 7):
        return 0
    # end if

    print("Erasing the 2 ...")
    test.erase(2)
    if not correct(test, 6):
        return 0
    # end if

    print("Erasing the 14 ...")
    test.erase_one(14)  # yes, erase_one
    if not correct(test, 5):
        return 0
    # end if

    print("Erasing the 4 ...")
    test.erase(4)
    if not correct(test, 4):
        return 0
    # end if

    print("Erasing the 12 ...")
    test.erase(12)
    if not correct(test, 3):
        return 0
    # end if

    print("Erasing the 8 ...")
    test.erase(8)
    if not correct(test, 2):
        return 0
    # end if

    print("Erasing the 7 ...")
    test.erase(7)
    if not correct(test, 1):
        return 0
    # end if

    print("Erasing the other 10 ...")
    if not test.erase_one(10):
        print("Test failed ...")
        return 0
    # end if
    if not correct(test, 0):
        return 0
    # end if

    print("Testing capacity again")
    if test.getCapacity() != 16:
        print("Test failed.\n")
        return 0
    # end if
    print("Test passed.\n")

    print("Now trimming to size")
    test.trimToSize()
    if test.getCapacity() != 1:
        print("Test failed.")
        print("%s", test)
        return 0
    # end if
    print("Test passed.\n")

    print("Now trimming to size again")
    test.trimToSize()
    if test.getCapacity() != 1:
        print("Test failed.")
        print("%s", test)
        return 0
    # end if
    print("Test passed.\n")

    print("Inserting value 5000 into bag ...")
    test.insert(5000)
    print("Inserting three 5's into bag and then erasing all of them ...")
    test.insert(5)
    test.insert(5)
    test.insert(5)
    test.erase(5)
    if not correct(test, 1):
        return 0
    # end if

    print("Erase methods seem okay.")
    return POINTS[4]
# end test4


def test5():
    '''
    **************************************************************************
     int test5( )
       Performs basic tests for the += and union methods
       Returns POINTS[5] if the tests are passed.
       Otherwise returns 0.
    **************************************************************************
    '''

    test1 = DoubleArrayBag()
    test2 = DoubleArrayBag()
    test3 = DoubleArrayBag()

    print("Testing += with two empty bags")
    try:
        test3 += test1
        if test3.getCapacity() == 1 and test3.size() == 0:
            print("Test passed.\n")
    except:
        print("Test failed.\n")
        return 0
    # end try/except

    print("Testing union with two empty bags")
    try:
        test3 = DoubleArrayBag.union(test2, test3)
        if test3.getCapacity() == 1 and test3.size() == 0:
            print("Test passed.\n")
    except:
        print("Test failed.\n")
        return 0
    # end try/except

    print("Inserting 2000 1's into test1 and 2000 2's into test2")
    for i in range(0, 2000):
        test1.insert(1)
        test2.insert(2)
    # end for

    print("Now testing the += method with items in bags ...")
    test1 += test2
    print("   and now testing for occurrences of 1's and 2's in test1")
    if test1.occurrences(1) == 2000 and test1.occurrences(2) == 2000:
        print("Test passed.\n")
    else:
        print("Test failed.\n")
        return 0
    # end if



    print("Now testing the union method ...")
    test3 = DoubleArrayBag.union(test2, test2)
    print("   and now testing for occurrences of 2's in test3")
    if test3.occurrences(2) == 4000:
        print("Test passed.\n")
    else:
        print("Test failed.\n")
        return 0
    # end if

    return POINTS[5]
# end test5


def run_a_test(number, message, max):
    result = 0

    print("\n START OF TEST " + str(number) + ":")
    print(message + " (" + str(max) + " points.")
    if number == 1:
        result = test1()
    elif number == 2:
        result = test2()
    elif number == 3:
        result = test3()
    elif number == 4:
        result = test4()
    elif number == 5:
        result = test5()
    else:
        print("Something foobarred")
    # end if

    if result > 0:
        print("Test " + str(number) + " got " + str(result) + " points",
              end='')
        print(" out of a possible " + str(max) + ".")
    else:
        print("Test " + str(number) + " failed.")
    # end if
    print("END OF TEST " + str(number) + ".\n")

    return result
# end run_a_test


def main():
    '''
    **************************************************************************
      main( )
      The main program calls all tests and prints the sum of all points
       earned from the tests.
    **************************************************************************
    '''
    sum = 0
    answer = ""
    done_erase = False
    done_union = False

    print("Running " + DESCRIPTION[0])
    answer = input("Have you implemented erase and erase_one yet? [Y or N]: ")
    done_erase = answer in "Yy"
    answer = input("Have you implemented += and union yet? [Y or N]: ")
    done_union = answer in "Yy"

    sum += run_a_test(1, DESCRIPTION[1], POINTS[1])
    sum += run_a_test(2, DESCRIPTION[2], POINTS[2])
    sum += run_a_test(3, DESCRIPTION[3], POINTS[3])

    if done_erase:
        sum += run_a_test(4, DESCRIPTION[4], POINTS[4])
    if done_union:
        sum += run_a_test(5, DESCRIPTION[5], POINTS[5])

    print("If you submit your bag to Prof. Haiduk now, you will have")
    print("%d points out of the %d points from this test program." %
          (sum, POINTS[0]))
# end main


if __name__ == "__main__":
    main()
# end if
