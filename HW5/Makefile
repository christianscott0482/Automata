JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = lexaard.java

MAIN = lexaard

default: classes

classes:$(CLASSES:.java=.class)


clean:
	$(RM) lexaard.class lexaard\$$rule.class lexaard\$$grammar.class
