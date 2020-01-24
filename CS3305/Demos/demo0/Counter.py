#! /usr/bin/env python3

class Counter:

    def __init__(self, counter = 0):
        self.theCounter = counter
    #end constructor

    def incr( self ):
        self.theCounter += 1
    #end incr

    def getValue( self ):
        return self.theCounter
    #end getValue

    def __str__( self ):
        return str( self.theCounter )
    #end __str__

# end class Counter

def main():
    print("%s" % "Testing the Counter class")

    counter = Counter()
    # implicit call to counter's __str__ method
    print( "%s %s" % ("Value of the counter after constructor ", counter) )

    for i in range(1,21): counter.incr()
    print( "%s %s" % ("Value of counter after calling incr 20 times ",
            counter) )
    print( "%s %d" % ("And value of counter from calling getValue ",
            counter.getValue() ) )

    print("%s" % "Let's add 50 more to the counter" )

    for i in range(1,51): counter.incr()

    print("%s %s" % ( "Value of counter after calling incr 50 more times ",
             counter ) )
    print( "%s %d" % ("And value of counter from calling getValue ",
             counter.getValue() ) )

    counter = Counter(100) #create new counter object with initial value 100
    print( "%s %s" % ("Value of the counter after constructor Counter(100) ", counter) )

# end main

if __name__ == "__main__":
    main()
