import java.io.PushbackInputStream;
import java.io.IOException;

public class Reverse1 {

    public static void reverse( PushbackInputStream pbis ) {
        final int EOLN = 10;
        int aChar;
        try { 
            if ( ( aChar = pbis.read() ) != EOLN ) {
                reverse( pbis );
                System.out.printf("%c", (char) aChar);
            }
        }
        catch (IOException e) {}
    }

    public static void reverseLine( PushbackInputStream pbis ) {
        final int EOLN = 10;
        reverse( pbis );
        System.out.printf("%c", EOLN);
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
