#!/bin/sh

APP_MAINCLASS=tv.baikan.Server

CLASSPATH=./bin

for i in ./lib/*.jar; do
	CLASSPATH="$CLASSPATH":"$i"
done

JAVA_OPTS="-ms512m -mx512m -Xmn256m -Djava.awt.headless=true -XX:MaxPermSize=128m"

echo -n "Starting $APP_MAINCLASS ...\r\n"

nohup java $JAVA_OPTS -classpath $CLASSPATH $APP_MAINCLASS > out.log 2>&1 &