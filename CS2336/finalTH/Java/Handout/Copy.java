import java.io.PushbackInputStream;
import java.io.IOException;

public class Copy {

    public static void copyLine( PushbackInputStream pbis ) {
        final int EOLN = 10;
        int aChar;
        try { 
            while ( ( aChar = pbis.read() ) != EOLN ) {
                System.out.printf("%c", (char) aChar);
            }
            System.out.printf("%c", (char) EOLN);
        }
        catch (IOException e) {}
    }

    public static void main( String[] args ) {
        final int EOF = -1;
        PushbackInputStream pbis = new PushbackInputStream(System.in);
        int aChar;
        try { 
            while ( ( aChar = pbis.read() ) != EOF ) {
                //so the copy method gets entire line
                pbis.unread(aChar); //put character back into stream
                copyLine( /*using*/ pbis );
            }       
            pbis.close(); 
        }
        catch (IOException e) {}
    }
}
