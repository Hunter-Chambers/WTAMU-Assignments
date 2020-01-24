# Makefile for the first C++ bag
#

run_test : bagtest
	./bagtest

run_exam : bagexam
	./bagexam

bagexam	: bag1.o bagexam.o
	g++ -o bagexam -std=c++14 bagexam.o bag1.o

bagexam.o  : bagexam.cxx bag1.h
	g++ -c -std=c++14 bagexam.cxx

bagtest : bag1.o bagtest.o
	g++ -o bagtest -std=c++14 bagtest.o bag1.o

bagtest.o : bagtest.cxx bag1.h
	g++ -c -std=c++14 bagtest.cxx

bag1.o : bag1.cxx bag1.h
	g++ -c -std=c++14 bag1.cxx

check	: bagexam .valgrindrc
	valgrind ./bagexam

doxydocs	: doxy.config
	doxygen doxy.config
	firefox doxyDocs/html/index.html &

clean :
	rm -rf *.o bagexam bagtest .*sw* doxyDocs


