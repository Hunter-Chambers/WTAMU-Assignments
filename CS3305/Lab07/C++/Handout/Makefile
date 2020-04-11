#Makefile for lab07 by H. Paul Haiduk

all	: tabtest tabexam

tabtest	: tabtest.o
	g++ -Wall -std=c++17 -gstabs -o tabtest tabtest.o

tabtest.o : tabtest.cxx table.h node.h 
	g++ -Wall -std=c++17 -gstabs -c tabtest.cxx

run_tabtest	: tabtest
	./tabtest

tabexam : tabexam.o
	g++ -Wall -std=c++17 -gstabs -o tabexam tabexam.o

tabexam.o : tabexam.cxx table.h node.h 
	g++ -Wall -std=c++17 -gstabs -c tabexam.cxx

run_tabexam	: tabexam
	./tabexam

check_exam	: tabexam .valgrindrc
	valgrind -v ./tabexam

check_test	: tabtest .valgrindrc
	valgring -v ./tabtest

clean :
	rm -f *.o *.gch tabtest tabexam
