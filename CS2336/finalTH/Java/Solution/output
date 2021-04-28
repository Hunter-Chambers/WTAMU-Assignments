import java.io.PushbackInputStream;
import java.io.IOException;
import java.util.Iterator;

public class Reverse2 {

    public static void reverseLine( PushbackInputStream pbis ) {
        final int EOLN = 10;
        int aChar;
        Stack<Character> ls = new Stack<Character>( );
        try { 
            // Students should consume all characters but EOLN one at a time 
            // and push them onto stack.  Then, students should use the Stack's 
            // iterator to output the characters.  It is preferred that students's
            // use the lambda expression style iterator . . . ls.forEach but should
            // show the pre-Java 8 style iterator in comments.
            //
            // delete the following line placed in code to avoid compile error
            //
            // Here is the logic for the foreach method pre-java 8:
            //     for (char character : ls) System.out.print(character);

            ls.push((char) EOLN);

            while ((aChar = pbis.read()) != EOLN) ls.push((char) aChar);

            ls.forEach(System.out::print);
        }
        catch (IOException e) {}
    }


    public static void main( String[] args ) {
        final int EOF = -1;
        PushbackInputStream pbis = new PushbackInputStream(System.in);
        int aChar;
        try { 
            while ( ( aChar = pbis.read() ) != EOF ) {
                                      //so the reverseLine method gets entire line
                pbis.unread( aChar ); //put character back into stream
                reverseLine( /*using*/ pbis );
            }       
            pbis.close(); 
        }
        catch (IOException e) {}
    }
}


