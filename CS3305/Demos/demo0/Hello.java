/* This is a simple Java program
 * This text should be entered using vim or gvim
 * and saved in a file named Hello.java.  NOTE that
 * when a Java class is declared public, the class
 * definition MUST be saved in a file whose file name
 * matches the class name.
 *
 * Once the program has been entered and saved, at the CLI
 * prompt, enter the command:  javac Hello.java
 * The Java compiler will produce the file Hello.class if
 * there are no syntax errors in the code.
 *
 * To execute the program, at the CLI prompt, enter the
 * command:  java Hello
 *
 * This will invoke the JVM (Java Virtual Machine) interpreter
 * to execute the byte code stored in the file Hello.class
 */

public class Hello {

    public static void main(String[] args) {
        System.out.printf("%s\n", "Hello World!");
    }

}
