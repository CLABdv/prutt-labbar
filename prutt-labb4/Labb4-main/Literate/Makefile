LIB = /usr/share/java/junit-4.13.2.jar:/usr/share/java/hamcrest-core.jar

all: code tex

compile: Factory Main

Factory:
	javac -cp "$(LIB)" human/*.java Factory.java

Main:
	javac Main.java 

clean:
	rm -f *.class human/*.class 
	
runMain:
	java -cp . Main.java

runFactory:
	java -cp ".:$(LIB)" Factory.java

code:
	notangle -RAbstractComponent.java Main.nw > AbstractComponent.java
	notangle -RComponent.java Main.nw > Component.java
	notangle -RComposite.java Main.nw > Composite.java
	notangle -RLeaf.java Main.nw > Leaf.java
	notangle -RMain.java Main.nw > Main.java
	notangle -RBFIterator.java Main.nw > BFIterator.java
	notangle -RDFIterator.java Main.nw > DFIterator.java
	notangle -Rhuman/Human.java Main.nw > human/Human.java
	notangle -Rhuman/Man.java Main.nw > human/Man.java
	notangle -Rhuman/Woman.java Main.nw > human/Woman.java
	notangle -Rhuman/NonBinary.java Main.nw > human/NonBinary.java
	notangle -RFactory.java Main.nw > Factory.java
	
tex:
	@noweave Main.nw > Main.tex
pdf:
	pdflatex Main.tex
