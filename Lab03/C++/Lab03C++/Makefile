# Makefile for sequence
#
all		: sequence_test sequence_exam simple_test

sequence_test	: sequence_test.o sequence.o
	g++ -std=c++17 -gstabs -o sequence_test sequence_test.o sequence.o

sequence_exam	: sequence_exam.o sequence.o
	g++ -std=c++17 -gstabs -o sequence_exam sequence_exam.o sequence.o

simple_test	: simple_test.o sequence.o
	g++ -std=c++17 -gstabs -o simple_test simple_test.o sequence.o

sequence_test.o	: sequence_test.cxx sequence.h 
	g++ -std=c++17 -gstabs -c sequence_test.cxx

sequence_exam.o	: sequence_exam.cxx sequence.h
	g++ -std=c++17 -gstabs -c sequence_exam.cxx

simple_test.o	: simple_test.cxx sequence.o
	g++ -std=c++17 -gstabs -c simple_test.cxx

sequence.o	: sequence.cxx sequence.h
	g++ -std=c++17 -gstabs -c sequence.cxx

run_simple	: simple_test
	./simple_test

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
	rm -rf *.o simple_test sequence_test sequence_exam doxyDocs core
