# Makefile for take home portion of final for CS 2336
# 	This Makefile uses embedded shell scripting
#
SHELL := /bin/bash

all : Stack.class Copy.class Reverse1.class Reverse2.class 

Stack.class	: Stack.java
	javac Stack.java

Copy.class	: Copy.java
	javac Copy.java

Reverse1.class	: Reverse1.java 
	javac Reverse1.java

Reverse2.class 	: Reverse2.java
	javac Reverse2.java

clean:
	@rm -vf *.class .*swp 

compareCopy: Copy.class
	java Copy < Copy.java | java Copy > output
	diff Copy.java output
	@if [[ $$? -eq 0 ]]; then\
	    echo "Successful Copy";\
	else\
	    echo "No cigar!";\
	fi

compareReverse1: Reverse1.class
	java Reverse1 < Reverse1.java | java Reverse1 > output
	diff Reverse1.java output
	@if [[ $$? -eq 0 ]]; then\
	    echo "Successful Reverse1";\
	else\
	    echo "No cigar!";\
	fi

compareReverse2: Reverse2.class
	java Reverse2 < Reverse2.java | java Reverse2 > output
	diff Reverse2.java output
	@if [[ $$? -eq 0 ]]; then\
	    echo "Successful Reverse2";\
	else\
	    echo "No cigar!";\
	fi
