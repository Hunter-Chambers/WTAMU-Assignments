# Makefile for LinkedQueue

run : CarWashE.class
	java CarWashE

# NOTE: for use of student implementation of LinkedQueue, comment out the
#       following four lines and uncomment those that are now commented
#CarWashE.class : CarWashE.java \
#		 utility/Averager.class utility/BooleanSource.class \
#		 utility/Washer.class
#	javac CarWashE.java

CarWashE.class : CarWashE.java \
		 collections/LinkedQueue.class \
		 utility/Averager.class utility/BooleanSource.class \
		 utility/Washer.class
	javac CarWashE.java

collections/LinkedQueue.class : collections/LinkedQueue.java collections/SLNode.class
	javac collections/LinkedQueue.java

collections/SLNode.class : collections/SLNode.java
	javac collections/SLNode.java

utility/Averager.class : utility/Averager.java
	javac utility/Averager.java

utility/BooleanSource.class : utility/BooleanSource.java
	javac utility/BooleanSource.java

utility/Washer.class : utility/Washer.java
	javac utility/Washer.java

docs	:  
	javadoc \
		  -d javadocs \
		  -version \
		  -author \
		  -tag note.:a:"Note" \
	          -tag pre.:a:"Precondition" \
	          -tag post.:a:"Postcondition" \
		  collections/LinkedQueue.java \
		  collections/SLNode.java \
		  utility/Averager.java \
		  utility/BooleanSource.java \
		  utility/Washer.java \
		  CarWashE.java
	firefox javadocs/index.html &

clean	:
	rm -rf javadocs *.class collections/*.class utility/*.class

