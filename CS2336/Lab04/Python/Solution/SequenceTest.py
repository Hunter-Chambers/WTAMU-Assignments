#!/usr/bin/env python3
# FILE: SequenceTest.py
# An interactive test program for the LongArraySequence class

import os
from LongArraySequence import LongArraySequence

def print_menu():
    '''Display menu for user selection.'''
    print("\n")
    print("\n\n\t\tThe following choices are available:  ")
    print("\t\t s  Activate the start() method for test1")
    print("\t\t a  Activate the advance() method for test1")
    print("\t\t ?  Display the result from the isCurrent() for test1")
    print("\t\t c  Display the result from the getCurrent() for test1")
    print("\t\t 1  Display test1")
    print("\t\t 2  Display test2")
    print("\t\t 3  Display test3")
    print("\t\t S  Display the result for the size() method for test1")
    print("\t\t I  Add a new number to test1 with addBefore()")
    print("\t\t A  Add a new number to test1 with addAfter()")
    print("\t\t R  Remove current value from test1 with removeCurrent()")
    print("\t\t +  Display test1 + test2")
    print("\t\t e  Check whether test1 == test3")
    print("\t\t i  Ensure capacity on test1")
    print("\t\t t  Trim to size on test1")
    print("\t\t C  Use copy() to set test3 = test1.copy()")
    print("\t\t Q  Quit this test program")
# end print

def get_user_command():
    '''Prompt for and input user choice.'''
    command = input("\tEnter choice: ")
    if len(command) == 0:
        return ' '
    else:
        return command[0]
# end get_user_command()

def get_number(mesg="\tPlease enter a whole number: " ):
    '''prompt for and input next whole number.'''
    result = int(input(mesg))
    return result
# end get_number

def pressEnterToContinue():
    result = input("\t\tPress enter to continue . . .")
# end pressEnterToContinue

def main():
    '''provides interactive test of the LongArraySequence class'''
    test1 = LongArraySequence()
    test2 = LongArraySequence()
    test3 = LongArraySequence()

    os.system('clear')
    print("I have initialized three empty sequences:  test1, test2 and test3")
    pressEnterToContinue()

    choice = ' '

    while not choice in "Qq":
        os.system('clear')
        print_menu()
        choice = get_user_command()
        if choice == 's':
            test1.start()
        elif choice == 'a':
            if test1.isCurrent:
                test1.advance()
            else:
                print("Cannot advance since cursor is not defined")
                pressEnterToContinue()
            # end if
        # end if
        elif choice == '?':
            if test1.isCurrent():
                print("The cursor is current . . . there is a current item")
            else:
                print("The cursor is undefined . . . there is no current item")
            # end if
            pressEnterToContinue()
        elif choice == 'c':
            if test1.isCurrent():
                print("Current item is: %d" % test1.getCurrent())
            else:
                print("There is no current item!")
            # end if
            pressEnterToContinue()
        elif choice == '1':
            print(str(test1))
            pressEnterToContinue()
        elif choice == '2':
            print(str(test2))
            pressEnterToContinue()
        elif choice == '3':
            print(str(test3))
            pressEnterToContinue()
        elif choice == 'S':
            print("Size of test1 is %d" % test1.size())
            pressEnterToContinue()
        elif choice == 'I':
            test1.addBefore(get_number())
        elif choice == 'A':
            test1.addAfter(get_number())
        elif choice == 'R':
            if test1.isCurrent():
                test1.removeCurrent()
                print("test1's current item has been removed")
            else:
                print ("test1's cursor not current -- not removed")
            pressEnterToContinue()
        elif choice == '+':
            print(str( test1 + test2 ))
            pressEnterToContinue()
        elif choice == 'e':
            print("test1 == test3? %s" % str(test1 == test3))
            pressEnterToContinue()
        elif choice == 'i':
            test1.ensureCapacity(get_number())
        elif choice == 't':
            test1.trimToSize()
        elif choice == 'C':
            test3 = test1.copy()
        elif choice in "Qq":
            print("\t\tRidicule is the best test of truth . . .");
            pressEnterToContinue()
        else:
            print("\t\t"+choice+" is invalid . . . ")
            pressEnterToContinue()
        # end if
    # end while
# end main

main()
