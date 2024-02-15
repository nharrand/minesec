#!/bin/bash

PROJECT_ROOT="/home/lyadis/Documents/Minecraft-sec"
EXPERIMENT_DIR="static-res"
#list jars
PATH_TO_MODS="$PROJECT_ROOT/jars"
PACKAGE_LIST="java/lang/ClassLoader,ClassLoader,java/io,java/nio,java/net"
CSV_FILE="$PROJECT_ROOT/$EXPERIMENT_DIR/static-detection-results.csv"
ENTROPY_FILE="$PROJECT_ROOT/$EXPERIMENT_DIR/entropy-results.csv"
LOG_FILE="$PROJECT_ROOT/$EXPERIMENT_DIR/detection.log"

MALDET="$PROJECT_ROOT/maldet/target/maldet-1.0-SNAPSHOT-jar-with-dependencies.jar"
ENTROPY="$PROJECT_ROOT/entropy/entropy"
#Write header
echo "Jar,$PACKAGE_LIST" > $CSV_FILE
echo "Jar,File,Entropy" > $ENTROPY_FILE

#for each jars
for j in $(find $PATH_TO_MODS -type "f")
do
	JAR_NAME=$(echo "$j" | cut -d '.' -f1 | rev | cut -d '/' -f1 | rev)
	echo " ---- $JAR_NAME ---- " >> $LOG_FILE
	
	# upack
	mkdir $PROJECT_ROOT/$EXPERIMENT_DIR/tmp/$JAR_NAME
	cd $PROJECT_ROOT/$EXPERIMENT_DIR/tmp/$JAR_NAME
	jar xf $j
	
	# run maldet
	java -jar $MALDET -class-dir $PROJECT_ROOT/$EXPERIMENT_DIR/tmp/$JAR_NAME -csv $CSV_FILE -package-list $PACKAGE_LIST >> $LOG_FILE 2>&1
	
	# for each file
	for c in $(find $PROJECT_ROOT/$EXPERIMENT_DIR/tmp/$JAR_NAME -name '*.class')
	do
		# run entropy
		ENT=$($ENTROPY $c)
		echo "$JAR_NAME,$c,$ENT" >> $ENTROPY_FILE
	done
	
	rm -rf $PROJECT_ROOT/$EXPERIMENT_DIR/tmp/$JAR_NAME
done