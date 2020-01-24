# Makefile for the C++ timer test

testTimer  : testTimer.o
	g++ -o testTimer -std=c++17 testTimer.o

testTimer.o : testTimer.cxx timer.h
	g++ -c -std=c++17 testTimer.cxx

run	: testTimer
	./testTimer

clean :
	rm -f testTimer.o testTimer
