if [ -z "$CATALINA_HOME" ]; then
    echo "The environment variable CATALINA_HOME must be set to the root of the Tomcat installation directory"
    exit 1
fi  

rm -rf target
mkdir target
mkdir target/WEB-INF
mkdir target/WEB-INF/classes
mkdir target/WEB-INF/lib

javac -sourcepath src -classpath $CATALINA_HOME/lib/servlet-api.jar:lib/opencsv-2.3.jar:lib/jstl-1.2.jar -d target/WEB-INF/classes src/*/*.java
cp conf/web.xml target/WEB-INF
cp lib/* target/WEB-INF/lib
cp -r pages/* target/