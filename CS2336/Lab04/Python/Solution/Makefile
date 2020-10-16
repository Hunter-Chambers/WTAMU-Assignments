# Makefile for the Python implementation of LongArraySequence
#
#
docs	: LongArraySequence.py
	pydoc3 -w LongArraySequence
	/usr/bin/google-chrome LongArraySequence.html &

run_exam  :  SequenceExam.py LongArraySequence.py
	./SequenceExam.py

run_test : SequenceTest.py LongArraySequence.py
	./SequenceTest.py

run_mytest  :  TestLongArraySequence.py LongArraySequence.py
	./TestLongArraySequence.py

run_internal : LongArraySequence.py
	./LongArraySequence.py

clean :
	rm -rf __pycache__  *.html .*sw* .rope*

