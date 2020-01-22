# Makefile for the generic sequence implemented with 
# doubly-linked list -- also includes unit testing with
# the Google Unit testing framework


all : sequence_test sequence_exam test_sequence

sequence_test : sequence_test.o  
	g++ -std=c++17 -gstabs -o sequence_test sequence_test.o

sequence_test.o : sequence_test.cxx sequence.h dlnode.h
	g++ -std=c++17 -gstabs -c sequence_test.cxx

sequence_exam : sequence_exam.o
	g++ -std=c++17 -gstabs -o sequence_exam sequence_exam.o

sequence_exam.o : sequence_exam.cxx sequence.h dlnode.h
	g++ -std=c++17 -gstabs -c sequence_exam.cxx

test_sequence	: test_sequence.cxx sequence.h dlnode.h
	g++ -std=c++17 test_sequence.cxx -o test_sequence -lgtest -lgtest_main -lpthread

run_test :  sequence_test
	./sequence_test

run_exam :  sequence_exam
	./sequence_exam

run_test_sequence : test_sequence
	./test_sequence

check1 : sequence_exam 
	valgrind ./sequence_exam

check2	: test_sequence
	valgrind ./test_sequence

clean :
	rm -rf *.o sequence_test sequence_exam test_sequence core vgcore.*
