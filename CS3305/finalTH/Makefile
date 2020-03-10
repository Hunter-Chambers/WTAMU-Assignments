#Makefile for individual final take home by H. Paul Haiduk

sorttests	: sorttests.o
	g++ -O -Wall -std=c++17 -o sorttests sorttests.o

sorttests.o : sorttests.cxx 
	g++ -O -Wall -std=c++17 -c sorttests.cxx

run	: sorttests
	./sorttests

run_check : sorttests .valgrindrc
	valgrind --max-stackframe=134742048 ./sorttests
	#valgrind --main-stacksize=4294967295 ./sorttests

my_run	: sorttests
	make; \
	./sorttests > output.txt

clean :
	rm -f *.o sorttests core vgcore*

