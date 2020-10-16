#!/usr/bin/env python3

from LongArraySequence import LongArraySequence


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
    print("%s\n%s\n" % ("b after b = a + negatives  cursor should " +
                        "be undefined and capacity 14", b))
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
