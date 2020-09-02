#Makefile for CS 2336 lab 00

run_test	:  Statistician.py stattest.py
	./stattest.py

run_exam	: Statistician.py statexam.py
	./statexam.py

docs	:	Statistician.py
	pydoc3 -w Statistician
	firefox Statistician.html

clean	:
	rm -rf __pycache__ Statistician.html .rope*
