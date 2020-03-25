# Makefile for CS 3305 bag implemented with a BST by H. Paul Haiduk

bagtest : bagtest.o 
	g++ -Wall -gstabs+ -std=c++17 bagtest.o -o bagtest

bagtest.o  : bagtest.cxx bag.h bintree.h 
	g++ -Wall -gstabs+ -std=c++17 -c bagtest.cxx

bagexam  : bagexam.o 
	g++ -Wall -gstabs+ -std=c++17 bagexam.o -o bagexam

bagexam.o  :  bagexam.cxx bag.h bintree.h 
	g++ -Wall -gstabs -std=c++17 -c bagexam.cxx 

myTreeTest  : myTreeTest.o 
	g++ -Wall -gstabs+ -std=c++17 myTreeTest.o -o myTreeTest

myTreeTest.o  :  myTreeTest.cxx bag.h bintree.h 
	g++ -Wall -gstabs -std=c++17 -c myTreeTest.cxx 

myBagTest  :  myBagTest.o
	g++ -Wall -gstabs -std=c++17 myBagTest.o -o myBagTest

myBagTest.o   :  myBagTest.cxx bag.h bintree.h
	g++ -Wall -gstabs -std=c++17 -c myBagTest.cxx

run_test  :  bagtest 
	./bagtest

run_exam  :  bagexam
	./bagexam

run_myTreeTest : myTreeTest
	./myTreeTest

check	:  bagexam .valgrindrc
	valgrind ./bagexam

clean	:
	rm -vf *.o bagtest bagexam myTreeTest myBagTest core



