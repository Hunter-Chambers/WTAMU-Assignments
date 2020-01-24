# Makefile for the Java timer test

testTimer.class  : testTimer.java Timer.class
	javac testTimer.java

Timer.class	: Timer.java
	javac Timer.java

run	: testTimer.class
	java testTimer

clean :
	rm -f *.class
