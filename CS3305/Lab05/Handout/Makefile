# Makefile for DLinkedSequence
# 
#
run_test : SequenceTest.class DLinkedSequence.class
	java SequenceTest

SequenceTest.class : SequenceTest.java DLinkedSequence.class
	javac SequenceTest.java

run_exam : SequenceExam.class DLinkedSequence.class
	java SequenceExam

SequenceExam.class : SequenceExam.java DLinkedSequence.class
	javac SequenceExam.java

run_Junit_test : JTestDLinkedSequence.class DLinkedSequence.class
	java -cp .:/usr/share/java/junit4.jar:/usr/share/java/hamcrest-core.jar org.junit.runner.JUnitCore JTestDLinkedSequence

JTestDLinkedSequence.class : JTestDLinkedSequence.java DLinkedSequence.class
	javac -cp .:/usr/share/java/junit4.jar JTestDLinkedSequence.java

DLinkedSequence.class : DLinkedSequence.java DLNode.class
	javac DLinkedSequence.java

DLNode.class	:  DLNode.java
	javac DLNode.java

docs	:  DLinkedSequence.java DLNode.java
	javadoc   -cp .:/usr/share/java/junit4.jar:/usr/share/java/hamcrest-core.jar  \
		  -d javadocs \
		  -version \
		  -author \
		  -tag note.:a:"Note" \
	          -tag pre.:a:"Precondition" \
	          -tag post.:a:"Postcondition" \
		  -exclude JTestDLinkedSequence.java \
		  DLinkedSequence.java DLNode.java
	firefox javadocs/index.html &

clean	:
	rm -rf javadocs *.class

