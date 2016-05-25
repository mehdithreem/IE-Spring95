if [ -z ../db ]; then
    mkdir ../db
fi

LAST_HOME=$(pwd)

cd ../db
java -cp ../lib/hsqldb.jar org.hsqldb.server.Server
cd $LAST_HOME