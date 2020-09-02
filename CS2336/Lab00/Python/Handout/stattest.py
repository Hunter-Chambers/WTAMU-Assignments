#!/usr/bin/env python3
''' provides interactive test of the Statistician class.'''
import os
from Statistician import Statistician


def print_menu():
    '''Display menu for user selection.'''
    print("\n")
    print("\tThe following choices are available:")
    print("\t 1  Add a new number to the 1st Statistician")
    print("\t 2  Add a new number to the 2nd Statistician")
    print("\t 3  Add a new number to the 3rd Statistician")
    print("\t D  Display the state of the statisticians 1, 2 and 3")
    print("\t E  Test whether statistician1 == statistician2")
    print("\t R  Activate one of the reset() functions")
    print("\t +  Set Statistician 3 equal to union of first two")
    print("\t *  Set Statistician 3 equal to scale( scale, statistician1 )")
    print("\t Q  Quit this test program")

# end print_menu


def get_user_command():
    '''Prompt for and input user choice.'''
    command = input("\tEnter choice: ")
    if command == "":
        return " "
    else:
        return command.upper()[0]
    # end if
# end get_user_input


def get_number():
    '''prompt for and input next real number.'''
    mesg = "\tPlease enter the next real number for the sequence: "
    validInput = False
    while not validInput:
        try:
            result = float(input(mesg))
            validInput = True
        except (TypeError, ValueError):
            continue
        # end try/except
    # end while
    return result
# end get_number


def main():
    ''' provides interactive test of the Statistician class.'''
    statistician1 = Statistician()
    statistician2 = Statistician()
    statistician3 = Statistician()

    choice = ' '

    os.system('clear')
    while choice != 'Q':
        print_menu()
        choice = get_user_command()
        if choice == 'R':
            print("\tWhich one should I reset ( 1, 2, 3 )?")
            choice = get_user_command()
            if choice in ('1', '2', '3'):
                if choice == '1':
                    statistician1.reset()
                elif choice == '2':
                    statistician2.reset()
                else:
                    statistician3.reset()
                print("\tReset activated for s" + choice)
            else:
                print("\tTry again -- bad choice")
        elif choice in ('1', '2', '3'):
            if choice == '1':
                statistician1.next_number(get_number())
            elif choice == '2':
                statistician2.next_number(get_number())
            else:
                statistician3.next_number(get_number())
        elif choice == 'D':
            print("statistician1 = \n%s" % str(statistician1))
            print("statistician2 = \n%s" % str(statistician2))
            print("statistician3 = \n%s" % str(statistician3))
        elif choice == 'E':
            if statistician1 == statistician2:
                print("\tstatistician1 and statistician2 are equal")
            else:
                print("\tstatistician1 and statistician2 are not equal")
        elif choice == '+':
            statistician3 = Statistician.union(statistician1, statistician2)
            print("\tstatistician3 has been set to union(s1, s2)")
        elif choice == '*':
            scale = float(input("\tPlease enter a value for scale: "))
            statistician3 = Statistician.scale(scale, statistician1)
            print("\tstatistician3 has been scaled")
        elif choice == 'Q':
            print("\tRidicule is the best test of truth . . . ")
        else:
            print("\t%s is invalid . . . sorry, try again" % choice)

    # end while choice != 'Q'


main()
