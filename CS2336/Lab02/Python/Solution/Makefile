# Makefile for the Python implementation of bag1

all :

run_test : BagTest.py DoubleArrayBag.py
	./BagTest.py

run_exam : BagExam.py DoubleArrayBag.py
	./BagExam.py

ad_hoc	: DoubleArrayBag.py
	./DoubleArrayBag.py

docs	: DoubleArrayBag.py
	pydoc3 -w DoubleArrayBag
	firefox DoubleArrayBag.html 2>&1 > /dev/null &

clean :
	rm -rf __pycache__  *.html .*sw* .rope*

