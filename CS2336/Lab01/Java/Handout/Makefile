# Makefile for CS 2336 lab 01

all:  Statistician.class Stattest.class Statexam.class

runTest	: Statistician.class Stattest.class
	java Stattest

runExam	:	Statistician.class Statexam.class
	java Statexam

Statistician.class	: Statistician.java
	javac Statistician.java

Statexam.class	:	Statexam.java
	javac Statexam.java

Stattest.class	: 	Stattest.java
	javac Stattest.java

docs	:	Statistician.java
	javadoc -d javadocs Statistician.java 
	firefox javadocs/index.html & 2>&1 > /dev/null

clean	:
	rm -rf javadocs	*.class
