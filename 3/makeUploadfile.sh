javac -sourcepath src -classpath ./lib/coolserver.jar orderTypeSrc/*.java

if [ $? -eq 0 ]; then
	cd orderTypeSrc
	zip -r ../upload.zip *_Order.class
	rm *_Order.class
	cd ..
fi