# Makefile for LongArraySequence
# 
#
run_test : SequenceTest.class LongArraySequence.class
	java SequenceTest

SequenceTest.class : SequenceTest.java
	javac SequenceTest.java

run_exam : SequenceExam.class LongArraySequence.class
	java SequenceExam

SequenceExam.class : SequenceExam.java
	javac SequenceExam.java

run_mytest : TestLongArraySequence.class LongArraySequence.class
	java TestLongArraySequence

TestLongArraySequence.class : TestLongArraySequence.java
	javac TestLongArraySequence.java

LongArraySequence.class : LongArraySequence.java
	javac LongArraySequence.java

docs	:  LongArraySequence.java
	javadoc   -d javadocs \
		  -version \
		  -author \
		  -tag note.:a:"Note" \
	          -tag pre.:a:"Precondition" \
	          -tag post.:a:"Postcondition" \
		  LongArraySequence.java
	firefox javadocs/index.html &

clean	:
	rm -rf javadocs *.class

