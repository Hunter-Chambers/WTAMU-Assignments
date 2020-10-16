#!/usr/bin/env python3

from LongArraySequence import LongArraySequence
import array

POINTS = [300,  # Total points for all tests
          50,   # Test 1 points
          50,   # Test 2 points
          50,   # Test 3 points
          30,   # Test 4 points
          30,   # Test 5 points
          30,   # Test 6 points
          30,   # Test 7 points
          30    # Test 8 points
]

DESCRIPTION = [
         "Tests for the LongArraySequence class with dynamic array allocation",
         "Testing addBefore, addAfter, and the non-mutating methods",
         "Testing situations where the cursor goes off the sequence",
         "Testing removeCurrent",
         "Testing the copy method",
         "Testing the ensureCapacity/trimToSize methods",
         "Testing addBefore/addAfter for somewhat larger sequence",
         "Testing == method",
         "Testing + method"
]

def run_a_test(number, message, max) :
    result = 0

    print("\n START OF TEST " + str(number) + ":")
    print( message + " (" + str(max) + " points).")

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
    elif number == 6:
        result = test6()
    elif number == 7:
        result = test7()
    elif number == 8:
        result = test8()
    else:
        print("Something foobarred")
    # end if
    if result > 0 :
        print("Test " + str(number) + " got " + str(result) + " points")
        print(" out of a possible " + str(max) + ".")
    else:
        print("Test " + str(number) + " failed.")
    # end if
    print("END OF TEST " + str(number) + ".\n" )

    return result
# end run_a_test

#  **************************************************************************
#  test_basic(LongArraySequence test, int size, boolean has_cursor)
#    Postcondition: A return value of true indicates:
#      a. test.size() is s, and
#      b. test.isCurrent() is has_cursor.
#    Otherwise the return value is false.
#    In either case, a description of the test result is printed to cout.
#  **************************************************************************
def test_basic(test, size, has_cursor):
    answer = False

    print("Testing that size() returns %d" % size)
    answer = str((test.size() == size))
    print("%s" %  answer)

    if (answer) :
        print("%s" % "Testing that isCurrent() returns ", end='')
        print("%s" %  str(has_cursor))
        answer = str((test.isCurrent( ) == has_cursor))
        print("%s\n" % answer)
    # end if

    return answer
# end test_basic


#  **************************************************************************
#  test_items(LongArraySequence test, int s, int i, long items[])
#    The function determines if the test sequence has the correct items
#    Precondition: The size of the items array is at least s.
#    Postcondition: A return value of true indicates that test.getCurrent()
#    is equal to items[i], and after test.advance() the result of
#    test.getCurrent() is items[i+1], and so on through items[s-1].
#    At this point, one more advance takes the cursor off the sequence.
#    If any of this fails, the return value is false.
#    NOTE: The test sequence has been changed by advancing its cursor.
# **************************************************************************
def test_items(test, s, i, items) :
    answer = True

    print("The cursor should be at item [" + str(i) + "] of the sequence")
    print("(counting the first item as [0]). I will advance the cursor")
    print("to the end of the sequence, checking that each item is correct...")
    while ((i < s) and test.isCurrent( ) and (test.getCurrent( ) == items[i])):
        i += 1
        test.advance()
    # end while

    if ((i != s) and not test.isCurrent()): #  The test.isCurrent( )
                                            #  function returns false too soon.
        print("\n    Cursor fell off the sequence too soon.")
        answer = False
    elif (i != s):
        #  The test.getCurrent( ) function returned a wrong value.
        print("\n    The item [" + str(i) + "] should be " + str(items[i])
              + ",")
        print("    but it was " + str(test.getCurrent( )) + " instead.")
        answer = False
    elif (test.isCurrent( )) :
        # The test.isCurrent( ) function returns true
        # after moving off the sequence.
        print("\n    The cursor was moved off the sequence,", end='')
        print(" but isCurrent still returns true.")
        answer = False
    # end if

    print("%s" % str(answer))
    return answer
# end test_items

#  **************************************************************************
#  correct(LongArraySequence test, int size, int cursor_spot, long items[])
#    This function determines if the sequence (test) is "correct" according to
#    these requirements:
#    a. it has exactly s items.
#    b. the items (starting at the front) are equal to
#       items[0] ... items[s-1]
#    c. if cursor_spot < s, then test's cursor must be at
#       the location given by cursor_spot.
#    d. if cursor_spot >= s, then test must not have a cursor.
#  NOTE: The function also moves the cursor off the sequence.
#  **************************************************************************
def correct(test, size, cursor_spot, items):
    has_cursor = (cursor_spot < size)

    # Check the sequence's size and whether it has a cursor.
    if (not test_basic(test, size, has_cursor)):
        print("%s\n" % "Basic test of size() or isCurrent() failed.")
        return False
    # end if

    # If there is a cursor, check the items from cursor to end of the sequence.
    if (has_cursor and not test_items(test, size, cursor_spot, items)) :
        print("%s\n", "Test of the sequence's items failed.")
        return False
    # end if

    # Restart the cursor at the front of the sequence and test items again.
    print("%s" % "I'll call start() and look at the items one more time...")
    test.start( )
    if (has_cursor and not test_items(test, size, 0, items)) :
        print("%s\n" % "Test of the sequence's items failed.")
        return False
    # end if

    # If the code reaches here, then all tests have been passed.
    print("%s\n" % "All tests passed for this sequence.")
    return True
# end correct

#  **************************************************************************
#  int test1( )
#    Performs some basic tests of addBefore, addAfter, and the constant member
#    functions. Returns POINTS[1] if the tests are passed. Otherwise returns 0.
#  **************************************************************************
def test1( ):
    empty = LongArraySequence()
    test =  LongArraySequence()        #  A sequence to add items to
    items0 = [ ]
    items1 = [ 5, 10, 20, 30 ]  #  These 4 items are put in a sequence
    items2 = [ 10, 15, 20, 30 ] #  These are put in another sequence

    #  Test that the empty sequence is really empty
    print("%s\n" % "Starting with an empty sequence.")
    if (not correct(empty, 0, 0, items0)):
        print("empty is: %s" % empty)
        return 0

    #  Test the addAfter function to add something to an empty sequence
    print("%s" % "I am now using addAfter to put 10 into an empty sequence.")
    test.addAfter(10)
    if ( not correct(test, 1, 0, items2)):
        return 0

    #  Test the addBefore function to add something to an empty sequence
    print("%s" % "I am now using addBefore to put 10 into an empty sequence.")
    test = LongArraySequence()
    test.addBefore(10)
    if (not correct(test, 1, 0, items2)):
        return 0

    #  Test the addAfter to put items at the end of a sequence
    print("I am now using addAfter to put 10,20,30 in an empty sequence.")
    print("%s" % "Then I move the cursor to the start and addBefore 5.")
    test = LongArraySequence()
    # print("test sequence is: %s" % str(test))
    test.addAfter(10)
    # print("test sequence is: %s" % str(test))
    test.addAfter(20)
    # print("test sequence is: %s" % str(test))
    test.addAfter(30)
    # print("test sequence is: %s" % str(test))
    test.start( )
    test.addBefore(5)
    # print("test sequence is: %s" % str(test))
    if (not correct(test, 4, 0, items1)):
        return 0

    #  Test the addBefore function to add an item in the middle of a sequence
    print("I am now using addAfter to put 10,20,30 in an empty sequence.")
    print("Then I move the cursor to the start, advance once, ", end='')
    print("%s" % "and addBefore 15.")
    test = LongArraySequence()
    test.addAfter(10)
    test.addAfter(20)
    test.addAfter(30)
    test.start( )
    test.advance( )
    test.addBefore(15)
    if (not correct(test, 4, 1, items2)):
        return 0

    #  Test the addAfter function to add an item in the middle of a sequence
    print("I am now using addAfter to put 10,20,30 in an empty sequence.")
    print("Then I move the cursor to the start and addAfter 15 ", end='')
    print("after the 10.")
    test = LongArraySequence()
    test.addAfter(10)
    test.addAfter(20)
    test.addAfter(30)
    test.start( )
    test.addAfter(15)
    if (not correct(test, 4, 1, items2)):
        return 0

    #  All tests have been passed
    print("%s" % "All tests of this first function have been passed.")
    return POINTS[1]
# end test1


# **************************************************************************
# int test2( )
#   Performs a test to ensure that the cursor can correctly be run off the end
#   of the sequence. Also tests that addAfter/addBefore work correctly when
#   there is no cursor. Returns POINTS[2] if the tests are passed. Otherwise
#   returns 0.
# **************************************************************************
def test2( ) :
    TESTSIZE = 30
    test = LongArraySequence()
    i = 0

    #  Put three items in the sequence
    print("Using addAfter to put 20 and 30 in the sequence, and then calling")
    print("advance, so that isCurrent should return false ... ")
    test.addAfter(20)
    test.addAfter(30)
    test.advance( )
    if (test.isCurrent( )) :
        print("failed.")
        return 0
    # end if
    print("passed.")

    #  Insert 10 at the front and run the cursor off the end again
    print("Inserting 10, which should go at the sequence's front.")
    print("Then calling advance three times to run cursor off the sequence ...")
    test.addBefore(10)
    test.advance( ) #  advance to the 20
    test.advance( ) #  advance to the 30
    test.advance( ) #  advance right off the sequence
    if (test.isCurrent( )) :
        print(" failed.\n")
        return 0
    # end if
    print(" passed.")

    #  Attach more items until the sequence becomes full.
    #  Note that the first addAfter should attach to the end of the sequence.
    print("Calling addAfter to put the numbers 40, 50, 60 ...", end='')
    print(str(TESTSIZE*10) + " at the sequence's end.")
    for i in range(4, TESTSIZE + 1):
        test.addAfter(i*10)

    #  Test that the sequence is correctly filled.
    print("Now I will test that the sequence has 10, 20, 30, ...", end='')
    print(str(TESTSIZE*10) + ".")
    test.start( )
    for i in range(1, TESTSIZE + 1) :
        if ((not test.isCurrent( )) or test.getCurrent( ) != i*10) :
            print("    Test failed to find " + str(i*10))
            return 0
        # end if
        test.advance( )
    # end if
    if (test.isCurrent( )) :
        print("    There are too many items on the sequence.")
        return 0
    # end if

    #  All tests passed
    print("All tests of this second function have been passed.")
    return POINTS[2]
# end test2


#  **************************************************************************
#  int test3( )
#    Performs basic tests for the removeCurrent function.
#    Returns POINTS[3] if the tests are passed.
#    Otherwise returns 0.
#  **************************************************************************
def test3( ) :
    TESTSIZE = 30

    test = LongArraySequence()

    #  Within this function, I create several different sequences using the
    #  items in these arrays:
    items1 = [ 30 ]
    items2 = [ 10, 30 ]
    items3 = [ 10, 20, 30 ]

    #  Build a sequence with three items 10, 20, 30, and remove the middle,
    #  and last and then first.
    print("Using addAfter to build a sequence with 10,30.")
    test.addAfter(10)
    test.addAfter(30)
    print("Insert a 20 before the 30, so entire sequence is 10,20,30.")
    test.addBefore(20)
    if (not correct(test, 3, 1, items3)):
        return 0
    print("Remove the 20, so entire sequence is now 10,30.")
    test.start( )
    test.advance( )
    test.removeCurrent( )
    if (not correct(test, 2, 1, items2)):
        return 0
    print("Remove the 30, so entire sequence is now just 10 with no cursor.")
    test.start( )
    test.advance( )
    test.removeCurrent( )
    if (not correct(test, 1, 1, items2)):
        return 0
    print("Set the cursor to the start and remove the 10.\n")
    test.start( )
    test.removeCurrent( )
    if (not correct(test, 0, 0, items2)):
        return 0

    #  Build a sequence with three items 10, 20, 30, and remove the middle,
    #  and then first and then last.
    print("Using addAfter to build another sequence with 10,30.")
    test.addAfter(10)
    test.addAfter(30)
    print("Insert a 20 before the 30, so entire sequence is 10,20,30.")
    test.addBefore(20)
    if (not correct(test, 3, 1, items3)):
        return 0
    print("Remove the 20, so entire sequence is now 10,30.")
    test.start( )
    test.advance( )
    test.removeCurrent( )
    if (not correct(test, 2, 1, items2)):
        return 0
    print("Set the cursor to the start and remove the 10,")
    print("so the sequence should now contain just 30.")
    test.start( )
    test.removeCurrent( )
    if (not correct(test, 1, 0, items1)):
        return 0
    print("Remove the 30 from the sequence, resulting in an empty sequence.")
    test.start( )
    test.removeCurrent( )
    if (not correct(test, 0, 0, items1)):
        return 0

    #  Build a sequence with three items 10, 20, 30, and remove the first.
    print("Build a new sequence by addBeforeing 30, 10, 20 (so the sequence")
    print("is 20, then 10, then 30). Then remove the 20.")
    test.addBefore(30)
    test.addBefore(10)
    test.addBefore(20)
    test.removeCurrent( )
    if (not correct(test, 2, 0, items2)):
        return 0
    test.start( )
    test.removeCurrent( )
    test.removeCurrent( )

    #  Just for fun, fill up the sequence, and empty it
    print("Just for fun, I'll empty the sequence then fill it up, then")
    print("empty it again.")
    '''print(During this process, I'll try to determine\n")
    print("whether any of the sequence's member functions access the")
    print("array outside of its legal indexes.") '''
    for i in range(0, TESTSIZE + 1):
        test.addBefore(i)
    for i in range(0, TESTSIZE + 1):
        test.removeCurrent( )

    #  All tests passed
    print("All tests of this third function have been passed.")
    return POINTS[3]
# end test3

#  **************************************************************************
#  int test4( )
#    Performs some tests of the copy constructor.
#    Returns POINTS[4] if the tests are passed. Otherwise returns 0.
#  **************************************************************************
def test4( ):
    TESTSIZE = 30
    original = LongArraySequence() #  A LongArraySequence that we'll copy.
    items = array.array('l',range(0, 2*TESTSIZE))
    print("items: " + str(items))

    #  Set up the items array to contain 1...2*TESTSIZE.
    for i in range(1, 2*TESTSIZE + 1):
        items[i-1] = i
    # print("items: " + str(items))

    #  Test copying of an empty LongArraySequence. After the copying,
    #  we change original.
    print("Copy constructor test: for an empty LongArraySequence.")
    copy1 = original.copy()
    original.addAfter(1); #  Changes the original LongArraySequence,
                          #  but not the copy.
    # print("Original:" + str(original))
    # print("Copy:" + str(copy1))
    if (not correct(copy1, 0, 0, items)):
        return 0

    #  Test copying of a LongArraySequence with current item at the tail.
    print("Copy constructor test: for a LongArraySequence with cursor at tail.")
    for i in range(2, 2*TESTSIZE + 1):
        original.addAfter(i)
    # print("Original:" + str(original))
    copy2 = original.copy()
    # print("Copy:" + str(copy2))
    original.removeCurrent( ); #  Delete tail from original, but not the copy
    # print("Original:" + str(original))
    original.start( )
    original.advance( )
    original.removeCurrent( ); #  Delete 2 from original, but not the copy.
    # print("Original:" + str(original))
    # print("Copy:" + str(copy2))
    if (not correct(copy2, 2*TESTSIZE, 2*TESTSIZE-1, items)):
        return 0

    #  Test copying of a LongArraySequence with cursor near the middle.
    print("Copy constructor test: with cursor near middle.")
    original.addBefore(2)
    for i in range(1, TESTSIZE):
        original.advance( )
    #  Cursor is now at location [TESTSIZE] (counting [0] as the first spot).
    copy3 = original.copy()
    original.start( )
    original.advance( )
    original.removeCurrent( ) #  Delete 2 from original, but not the copy.
    # print("Original:" + str(original))
    # print("Copy:" + str(copy3))
    if (not correct(copy3, 2*TESTSIZE-1, TESTSIZE, items)):
        return 0

    #  Test copying of a LongArraySequence with cursor at the front.
    print("Copy constructor test: for a LongArraySequence with cursor at front.")
    original.addBefore(2)
    original.start( )
    #  Cursor is now at the front.
    copy4 = original.copy()
    original.start( )
    original.advance( )
    original.removeCurrent( ) #  Delete 2 from original, but not the copy.
    # print("Original:" + str(original))
    # print("Copy:" + str(copy4))
    if (not correct(copy4, 2*TESTSIZE-1, 0, items)):
        return 0

    #  Test copying of a LongArraySequence with no current item.
    print("Copy constructor test: for a LongArraySequence with no current item.")
    original.addBefore(2)
    while (original.isCurrent( )):
        original.advance( )
    #  There is now no current item.
    copy5 = original.copy()
    original.start( )
    original.advance( )
    original.removeCurrent( ) #  Delete 2 from original, but not the copy.
    # print("Original:" + str(original))
    # print("Copy:" + str(copy5))
    if (not correct(copy5, 2*TESTSIZE-1, 2*TESTSIZE, items)):
        return 0

    #  All tests passed
    print("All tests of this fourth function have been passed.")
    return POINTS[4]
# end test4

#  **************************************************************************
#  int test5( )
#    Performs some tests of copy.
#    Returns POINTS[4] if the tests are passed. Otherwise returns 0.
#  **************************************************************************
def test5( ) :
    TESTSIZE = 30
    original = LongArraySequence(); #  A LongArraySequence that we will copy.

    #  Set up the items array to contain 1...2*TESTSIZE.
    items = array.array('l',range(1, 2*TESTSIZE+1))
    # print("Items: ", items)

    #  Test copying of an empty LongArraySequence. After the copying,
    #  we change original.
    print("copy test: for an empty LongArraySequence.")
    copy1 = original.copy()
    original.addAfter(1); #  Changes the original LongArraySequence,
                          #  but not the copy.
    # print("Original:" + str(original))
    # print("Copy:" + str(copy1))
    if (not correct(copy1, 0, 0, items)):
        return 0

    #  Test copying of a LongArraySequence with current item at the tail.
    print("copy test: for a LongArraySequence with cursor at tail.")
    for i in range(2, 2*TESTSIZE + 1):
        original.addAfter(i)
    # print("Original:" + str(original))
    copy2 = original.copy()
    # print("Copy:" + copy2")
    original.removeCurrent() #  Delete tail from original, but not the copy
    # print("Original:" + str(original))
    original.start()
    original.advance()
    original.removeCurrent(); #  Delete 2 from original, but not the copy.
    # print("Original:" + str(original))
    # print("Copy:" + str(copy2))
    if (not correct(copy2, 2*TESTSIZE, 2*TESTSIZE-1, items)):
        return 0

    #  Test copying of a LongArraySequence with cursor near the middle.
    print("copy test: with cursor near middle.")
    original.addBefore(2)
    for i in range(1, TESTSIZE):
        original.advance()
    #  Cursor is now at location [TESTSIZE] (counting [0] as the first spot).
    copy3= original.copy()
    original.start()
    original.advance()
    original.removeCurrent(); #  Delete 2 from original, but not the copy.
    # print("Original:" + str(original))
    # print("Copy:" + str(copy3))
    if (not correct(copy3, 2*TESTSIZE-1, TESTSIZE, items)):
        return 0

    #  Test copying of a LongArraySequence with cursor at the front.
    print("copy test: for a LongArraySequence with cursor at front.")
    original.addBefore(2)
    original.start()
    #  Cursor is now at the front.
    copy4 = original.copy()
    original.start()
    original.advance()
    original.removeCurrent(); #  Delete 2 from original, but not the copy.
    # print("Original:" + str(original))
    # print("Copy:" + str(copy4))
    if (not correct(copy4, 2*TESTSIZE-1, 0, items)):
        return 0

    #  Test copying of a LongArraySequence with no current item.
    print("copy test: for a LongArraySequence with no current item.")
    original.addBefore(2)
    while original.isCurrent():
        original.advance()
    #  There is now no current item.
    copy5 = original.copy()
    original.start()
    original.advance()
    original.removeCurrent(); #  Delete 2 from original, but not the copy.
    # print("Original:" + str(original))
    # print("Copy:" + str(copy5))
    if (not correct(copy5, 2*TESTSIZE-1, 2*TESTSIZE, items)):
        return 0

    #  All tests passed
    print("All tests of this fifth function have been passed.")
    return POINTS[4]
# end test 5

#  **************************************************************************
#  int test6( )
#    Performs some basic tests of addBefore and addAfter for the case where the
#    current capacity has been reached (in case of array implementation).
#    Returns POINTS[6] if the tests are passed. Otherwise returns 0.
#  **************************************************************************
def test6( ) :
    TESTSIZE = 30;
    testa = LongArraySequence();
    testi = LongArraySequence();

    #  Set up the items array to contain 1...2*TESTSIZE.
    items = array.array('l',range(1, 2*TESTSIZE+1))

    print("Testing to see that addAfter works correctly")
    for i in range(1, 2*TESTSIZE + 1):
        testa.addAfter(i);
    # print("testa: " + str(testa))
    if (not correct(testa, 2*TESTSIZE, 2*TESTSIZE-1, items)):
        return 0

    print("Testing to see that addBefore works correctly ")
    for i in range(2*TESTSIZE, 0, -1):
        testi.addBefore(i)
    # print("testi: %s" %  str(testi))
    if (not correct(testi, 2*TESTSIZE, 0, items)):
        return 0

    #  All tests passed
    print("All tests of this sixth function have been passed.");
    return POINTS[6];
# end test6

#  **************************************************************************
#  int test7( )
#    Performs some basic tests of equals
#    Returns POINTS[7] if the tests are passed. Otherwise returns 0.
#  **************************************************************************
def test7( ) :
    TESTSIZE = 30;
    testa = LongArraySequence()
    testi = LongArraySequence()
    empty = LongArraySequence()

    #  Set up the items array to contain 1...2*TESTSIZE.
    items = array.array('l',range(1, 2*TESTSIZE+1))

    print("Creating two sequences with exactly the same items using addAfter")
    print("Testing ==")
    for i in range(1, 2*TESTSIZE + 1):
        testa.addAfter(i)
        testi.addAfter(i)
    # end for
    if ( not(testa == testi) ):
        return 0

    # print("The two sequences are ==");
    # print("testa " + str(testa))
    # print("testi " + str(testi))

    testa = LongArraySequence()
    testi = LongArraySequence()
    print("Creating two sequences with exactly the same items using addBefore")
    print("Testing ==");
    for i in range(1, 2*TESTSIZE + 1):
        testa.addBefore(i)
        testi.addBefore(i)
    # for
    if ( not (testa == testi) ) :
        return 0
    # print("The two sequences are ==");
    # print("testa " + str(testa))
    # print("testi " + str(testi))
    print("I will now call advance on one of the sequences")
    print("Testing !=")
    testa.advance();
    # print("testa " + str(testa))
    # print("testi " + str(testi))
    if testa == testi:
        return 0

    #  All tests passed
    print("All tests of this seventh function have been passed.")
    return POINTS[7]
# end test7

#  **************************************************************************
#  int test8( )
#    Performs some basic tests of + method
#    Returns POINTS[8] if the tests are passed. Otherwise returns 0.
#  **************************************************************************
def test8( ):
    TESTSIZE = 30

    #  Set up the items array to contain 1...2*TESTSIZE.
    items = array.array('l',range(1, 2*TESTSIZE+1))

    seq1 = LongArraySequence()
    seq2 = LongArraySequence()
    empty = LongArraySequence()

    print("Testing concatenation of two empty sequences\n")
    try:
        seq1 = seq2 + empty # This could cause exception if it fails
        if ( seq1.size() != 0 ):
            return 0;
        print("Test passed ...\n")
    except:
        print("Test failed ...\n")
        return 0
    # end try/except

    print("Creating two sequences with exactly the same items using addAfter")
    print("Testing ==")
    for i in range(1, 2*TESTSIZE + 1):
        seq1.addAfter(i)
        seq2.addAfter(i)
    # end for
    if ( not seq1 == seq2 ):
        return 0
    print("Test passed ...")
    '''
    print("The two sequences are ==")
    print("seq1 " + str(seq1))
    print("seq2 " + str(seq2))
    '''

    print("Emptying seq1, then executing seq1 = seq2 + LongArraySequence()")
    seq1 = LongArraySequence()
    seq1 = seq2 + LongArraySequence()

    print("Testing that seq1 has no current item")
    if ( seq1.isCurrent() ):
        return 0; # there should not be a current cursor
    print("Test passed ...\n")

    print("Setting cursor to beginning of seq1 and testing that all elements " +
          "are correct")
    seq1.start()
    #  If there is a cursor, check the items from cursor to end of the sequence.
    if (not test_items(seq1, 2*TESTSIZE, 0, items)):
        print("Test of the sequence's items failed.")
        return 0
    # end if
    print("Test passed ...\n")

    #  Set up the items to contain 1...TESTSIZE,1...TESTSIZE
    for i in range(1, TESTSIZE+1):
        items[i-1] = i
        items[i-1+TESTSIZE] = i
    # end for
    print("Filling each sequence with value 1 .. TESTSIZE using addAfter")
    seq1 = LongArraySequence()
    seq2 = LongArraySequence()
    for i in range(1, TESTSIZE+1):
        seq1.addAfter(i)
        seq2.addAfter(i)
    # end for

    print("Invoking concatenation by seq1 = seq1 + seq2")
    seq1 = seq1 + seq2
    print("Setting cursor to beginning of seq1 and testing that all elements" +
          " are correct")
    seq1.start()
    #  check the items from cursor to end of the sequence.
    if ( not test_items(seq1, 2*TESTSIZE, 0, items)) :
        print("Test of the sequence's items failed.\n")
        return 0
    # end if
    print("Test passed ...\n")

    #  All tests passed
    print("All tests of this eighth function have been passed.")
    return POINTS[8];
# end test8

def main() :
    sum = 0

    print( "Running " + DESCRIPTION[0])
    sum += run_a_test(1, DESCRIPTION[1], POINTS[1])
    sum += run_a_test(2, DESCRIPTION[2], POINTS[2])
    sum += run_a_test(3, DESCRIPTION[3], POINTS[3])
    sum += run_a_test(4, DESCRIPTION[4], POINTS[4])
    sum += run_a_test(5, DESCRIPTION[5], POINTS[5])
    sum += run_a_test(6, DESCRIPTION[6], POINTS[6])
    sum += run_a_test(7, DESCRIPTION[7], POINTS[7])
    sum += run_a_test(8, DESCRIPTION[8], POINTS[8])

    print("If you submit your sequence to Prof. Haiduk now, you will have")
    print("%d points out of the " % sum, end='')
    print("%d" % POINTS[0], end='')
    print(" points from this test program." )
# end main

main()
