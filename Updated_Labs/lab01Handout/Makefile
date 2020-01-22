#Makefile for the C++ implementation of lab 01
# by H. Paul Haiduk
#
all :	stattest statexam

stattest :	stattest.o statistician.o
	g++ -o stattest -std=c++17 stattest.o statistician.o

stattest.o :	stattest.cxx statistician.h
	g++ -c -std=c++17 stattest.cxx

statexam :	statexam.o statistician.o
	g++ -o statexam -std=c++17 statexam.o statistician.o

statexam.o :	statexam.cxx statistician.h
	g++ -c -std=c++17 statexam.cxx

statistician.o : statistician.cxx statistician.h
	g++ -c -std=c++17 statistician.cxx

run_test	: stattest
	./stattest

run_exam	: statexam
	./statexam

docs	: doxy.config
	doxygen doxy.config
	firefox doxyDocs/html/index.html &

clean	:
	rm -rf stattest statexam *.o .*.sw* doxyDocs

