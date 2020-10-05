# Makefile for DoubleArrayBag
# 
#
all	:  DoubleArrayBag.class BagExam.class BagTest.class

run_exam : BagExam.class DoubleArrayBag.class
	java BagExam

BagExam.class : BagExam.java
	javac BagExam.java

run_test : BagTest.class DoubleArrayBag.class
	java BagTest

BagTest.class : BagTest.java
	javac BagTest.java

DoubleArrayBag.class : DoubleArrayBag.java
	javac DoubleArrayBag.java

docs	:  DoubleArrayBag.java
	javadoc   -d javadocs \
		  -version \
		  -author \
		  -tag note.:a:"Note" \
	          -tag pre.:a:"Precondition" \
	          -tag post.:a:"Postcondition" \
		  DoubleArrayBag.java
	/usr/bin/google-chrome-stable javadocs/index.html 2>&1 > /dev/null &

clean	:
	rm -rf javadocs *.class

