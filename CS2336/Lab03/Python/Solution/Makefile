# Makefile for the Python implementation of DoubleArrayBag using dynamic array

run_test : BagTest.py DoubleArrayBag.py
	./BagTest.py

run_exam : BagExam.py DoubleArrayBag.py
	./BagExam.py

docs	: DoubleArrayBag.py
	pydoc3 -w DoubleArrayBag
	/usr/bin/google-chrome-stable DoubleArrayBag.html 2>&1 > /dev/null &

clean :
	rm -rf __pycache__  *.html .*sw* .ropeproject

