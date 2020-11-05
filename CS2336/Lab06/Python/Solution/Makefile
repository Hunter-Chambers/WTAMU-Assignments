# The very simple Makefile for lab06
#

run	: towers.py towers.dat stack.py
	./towers.py < towers.dat

docs 	: stack.py
	pydoc3 -w stack
	firefox stack.html &

clean :
	rm -rf __pycache__ .ropeproject *.html
