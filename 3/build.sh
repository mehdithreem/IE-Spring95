rm bin/*.class
javac -d bin -sourcepath src -classpath ./lib/coolserver.jar src/*.java

if [ $? -eq 0 ]; then
	jar cvfm CA2.jar manifest.txt bin/*.class
fi