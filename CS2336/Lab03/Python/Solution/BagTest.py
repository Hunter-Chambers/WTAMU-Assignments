#!/usr/bin/env python3
# FILE: BagTest.py
# An interactive test program for the DoubleArrayBag class

import os
from DoubleArrayBag import DoubleArrayBag

def print_menu():
    '''Display menu for user selection.'''
    print("\n")
    print("\n\n\tThe following choices are available with 2 bags:  ")
    print("\t\t c  Set bag1 to a new bag with user-defined capacity")
    print("\t\t I  Insert an item into bag1")
    print("\t\t i  Insert an item into bag2")
    print("\t\t X  Erase one of item from bag1")
    print("\t\t x  Erase one of item from bag2")
    print("\t\t R  Erase all of item from bag1")
    print("\t\t r  Erase all of item from bag2")
    print("\t\t C  Use copy to make bag2 equal to bag1")
    print("\t\t e  Test if bag1 equal to bag2 using ==")
    print("\t\t n  Test if bag1 not equal to bag2 using !=")
    print("\t\t E  Ensure capacity for bag1")
    print("\t\t T  Trim to size for bag1")
    print("\t\t P  Use += to make bag1 += bag2")
    print("\t\t D  Display both bags")
    print("\t\t S  Print the result from calling the size() functions")
    print("\t\t U  Display the union of bag1 and bag2")
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

def get_number(mesg="\tPlease enter the next number for the bag: "):
    '''prompt for and input next double number.'''
    result = float(input(mesg))
    return result
# end get_number

def main():
    '''provides interactive test of the DoubleArrayBag class'''
    bag1 = DoubleArrayBag()
    bag2 = DoubleArrayBag()

    choice = ' '

    while not choice in "Qq":
        os.system('clear')
        print_menu()
        choice = get_user_command()
        if choice == 'c':
            number = int(input("What capacity do you want? "))
            bag1 = DoubleArrayBag(number)
        elif choice == 'I':
            bag1.insert(get_number())
        elif choice == 'i':
            bag2.insert(get_number())
        elif choice in "Xx":
            number = get_number("\tEnter number to be erased: ")
            if choice == 'X':
                result = bag1.erase_one(number)
            else:
                result = bag2.erase_one(number)
            print("Attempt to erase %d was successful? %s" % \
                    (number, result))
            print("Press Enter to continue . . .", end='')
            cr = input()
        elif choice in "Rr":
            number = get_number("\tEnter number to be erased: ")
            if choice == 'R':
                result = bag1.erase(number)
            else:
                result = bag2.erase(number)
            print("%d occurrences of %d were erased" % \
                    (result, number))
            print("Press Enter to continue . . .", end='')
            cr = input()
        elif choice == 'C':
            bag2 = bag1.copy()
        elif choice in 'en':
            if choice == 'e':
                print("bag1 == bag2? %s" % str(bag1 == bag2))
            else:
                print("bag1 != bag2? %s" % str(bag1 != bag2))
            print("Press Enter to continue . . .", end='')
            cr = input()
        elif choice == 'E':
            number = int(input("What capacity do you want? "))
            bag1.ensureCapacity(number)
        elif choice == 'T':
            bag1.trimToSize()
        elif choice == 'D':
            print("bag1 =\n%s" % str(bag1))
            print("bag2 =\n%s" % str(bag2))
            print("Press Enter to continue . . .", end='')
            cr = input()
        elif choice == 'P':
            bag1 += bag2
        elif choice == 'S':
            print("bag1.size() = %d and bag2.size() = %d" % \
                  (bag1.size(), bag2.size()))
            print("Press Enter to continue . . .", end='')
            cr = input()
        elif choice == 'U':
            print("The union of bag1 and bag2 is: \n%s" % \
                  str(DoubleArrayBag.union(bag1, bag2)))
            print("Press Enter to continue . . .", end='')
            cr = input()
        elif choice in "Qq":
            print("Ridicule is the best test of truth . . .")
        else:
            print("\t\tCommand not recognized")
    # end while
# end main

main()
