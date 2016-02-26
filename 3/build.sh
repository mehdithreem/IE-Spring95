if [ ! -d "bin" ]; then
	mkdir bin
fi
rm -rf bin/*
javac -d bin -sourcepath src -classpath ./lib/coolserver.jar src/*.java

if [ $? -eq 0 ]; then
	jar cvfm CA3.jar manifest.txt bin/*.class
fi