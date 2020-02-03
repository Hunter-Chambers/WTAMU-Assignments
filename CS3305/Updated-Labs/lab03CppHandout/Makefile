# Makefile for the second C++ bag
#
all	: bagexam bagtest

run_exam : bagexam
	./bagexam

run_test : bagtest
	./bagtest

bagexam	: bag2.o bagexam.o
	g++ -o bagexam -std=c++17 bagexam.o bag2.o

bagexam.o  : bagexam.cxx bag2.h
	g++ -c -std=c++17 bagexam.cxx

bagtest : bag2.o bagtest.o
	g++ -o bagtest -std=c++17 bagtest.o bag2.o

bagtest.o : bagtest.cxx bag2.h
	g++ -c -std=c++17 bagtest.cxx

bag2.o : bag2.cxx bag2.h
	g++ -c -std=c++17 bag2.cxx

check	: bagexam .valgrindrc
	valgrind ./bagexam

docs	: doxy.config
	doxygen doxy.config
	firefox doxyDocs/html/index.html 2>&1 > /dev/null &

clean :
	rm -rf *.o bagexam bagtest .*sw* doxyDocs


