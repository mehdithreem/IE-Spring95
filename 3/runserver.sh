if [ ! -d "bin" ]; then
	mkdir bin
fi
rm -rf bin/*
javac -d bin -sourcepath src -classpath ./lib/coolserver.jar src/*.java

if [ $? -eq 0 ]; then
	echo "Starting Stocks Server ..."
	java -classpath bin:./lib/coolserver.jar StocksCore
fi