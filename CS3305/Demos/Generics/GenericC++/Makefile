# Makefile for lab02
#
all		: sequence_test sequence_exam simple_test test_sequence

sequence_test	: sequence_test.o 
	g++ -std=c++17 -gstabs -o sequence_test sequence_test.o 

sequence_exam	: sequence_exam.o 
	g++ -std=c++17 -gstabs -o sequence_exam sequence_exam.o 

simple_test	: simple_test.o
	g++ -std=c++17 -gstabs -o simple_test simple_test.o

simple_test.o	: simple_test.cxx sequence.h
	g++ -std=c++17 -gstabs -c simple_test.cxx

sequence_test.o	: sequence_test.cxx sequence.h 
	g++ -std=c++17 -gstabs -c sequence_test.cxx

sequence_exam.o	: sequence_exam.cxx sequence.h
	g++ -std=c++17 -gstabs -c sequence_exam.cxx

test_sequence : test_sequence.cxx
	g++ test_sequence.cxx -o test_sequence -lgtest -lgtest_main -lpthread

run_simple	: simple_test
	./simple_test

run_test_sequence : test_sequence
	./test_sequence

run_test	: sequence_test
	./sequence_test

run_exam	: sequence_exam
	./sequence_exam

check		: sequence_exam .valgrindrc
	valgrind ./sequence_exam

doxydocs	: doxy.config
	doxygen doxy.config
	firefox doxyDocs/html/index.html &

clean	:
	rm -rf *.o simple_test sequence_test sequence_exam test_sequence doxyDocs core
