# Makefile for DoubleArrayBag
# 
#
all : DoubleArrayBag.class BagTest.class BagExam.class

run_test : BagTest.class DoubleArrayBag.class
	java BagTest

run_exam : BagExam.class DoubleArrayBag.class
	java BagExam

BagTest.class : BagTest.java
	javac BagTest.java

BagExam.class : BagExam.java
	javac BagExam.java

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
	firefox javadocs/index.html

clean	:
	rm -rf javadocs *.class

