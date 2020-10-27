# Makefile for SLinkedSequence
# 
#
all : SLinkedSequence.class SLNode.class SequenceTest.class SequenceExam.class JTestSLinkedSequence.class

run_test : SequenceTest.class SLinkedSequence.class
	java SequenceTest

SequenceTest.class : SequenceTest.java SLinkedSequence.class
	javac SequenceTest.java

run_exam : SequenceExam.class SLinkedSequence.class
	java SequenceExam

SequenceExam.class : SequenceExam.java SLinkedSequence.class
	javac SequenceExam.java

run_mytest : TestSLinkedSequence.class SLinkedSequence.class
	java TestSLinkedSequence

run_Junit_test : JTestSLinkedSequence.class SLinkedSequence.class
	java -cp .:"/c/Program Files/Java/Third-Party-JARs/JUNIT/junit-4.13.1.jar":"/c/Program Files/Java/Third-Party-JARs/JUNIT/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore JTestSLinkedSequence

JTestSLinkedSequence.class : JTestSLinkedSequence.java SLinkedSequence.class
	javac JTestSLinkedSequence.java

TestSLinkedSequence.class : TestSLinkedSequence.java SLinkedSequence.class
	javac TestSLinkedSequence.java

SLinkedSequence.class : SLinkedSequence.java SLNode.class
	javac SLinkedSequence.java

SLNode.class	:  SLNode.java
	javac SLNode.java

docs	:  SLinkedSequence.java SLNode.java
	javadoc   -cp .:/usr/share/java/junit4.jar:/usr/share/java/hamcrest-core.jar  \
		  -d javadocs \
		  -version \
		  -author \
		  -tag note.:a:"Note" \
	          -tag pre.:a:"Precondition" \
	          -tag post.:a:"Postcondition" \
		  -exclude JTestSLinkedSequence.java \
		  SLinkedSequence.java SLNode.java
	/usr/bin/google-chrome javadocs/index.html &

clean	:
	rm -rf javadocs *.class

