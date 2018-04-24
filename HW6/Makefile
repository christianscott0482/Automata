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
	$(RM) lexaard.class lexaard\$$rule.class lexaard\$$grammar.class lexaard\$$PDA.class lexaard\$$PDA_state.class lexaard\$$token.class lexaard\$$token_group.class lexaard\$$token_pair.class
