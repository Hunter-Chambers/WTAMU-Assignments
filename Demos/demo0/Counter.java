
public class Counter {

    private int theCounter;

    public Counter() {
        this.theCounter = 0;
    }

    public Counter( int initValue ) {
        this.theCounter = initValue;
    }

    public void incr( ) {
        this.theCounter++;
    }

    public int getValue( ) {
        return this.theCounter;
    }

    public String toString( ) {
        StringBuffer sb = new StringBuffer();
        sb.append( this.theCounter );
        return sb.toString();
    }

    public static void main( String[] args ) {
        System.out.printf("%s\n", "Testing the Counter class ");

        Counter counter = new Counter( );
        System.out.printf("%s %s\n", "Value of the counter after constructor ",
                             counter );  //implicit call to counter.toString()

        for (int i=1; i <= 20; i++) counter.incr( );
        System.out.printf( "%s %s\n", "Value of counter after calling incr 20 times ",
                            counter ); // implicit call to counter.toString()
        System.out.printf("%s %d\n", "And value of counter from calling getValue ", 
                            counter.getValue( ) ); 

        System.out.printf("%s\n",  "Let's add 50 more to the counter" );
        for (int i=1; i <= 50; i++) counter.incr( );
        System.out.printf("%s %s\n",  "Value of counter after calling incr 50 more times ",
                            counter ); // implicit call to counter.toString()
        System.out.printf("%s %d\n", "And value of counter from calling getValue ",
                            counter.getValue( ) ); 

        counter = new Counter(100);
        System.out.printf("%s %s\n", "Value of the counter after constructor new Counter(100)",
                             counter );  //implicit call to counter.toString()

    }

}
