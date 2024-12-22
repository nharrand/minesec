#!/bin/bash

PROJECT_ROOT="/home/lyadis/Documents/Minecraft-sec"
EXPERIMENT_DIR="static-res"
#list jars
PATH_TO_MODS="$PROJECT_ROOT/jars"
#PACKAGE_LIST="java/lang/ClassLoader,ClassLoader,java/io,java/nio,java/net"
CSV_FILE="$PROJECT_ROOT/$EXPERIMENT_DIR/static-detection-results.csv"
ENTROPY_FILE="$PROJECT_ROOT/$EXPERIMENT_DIR/entropy-results.csv"
FAILED_ENTROPY_FILE="$PROJECT_ROOT/$EXPERIMENT_DIR/entropy-failures.log"
LOG_FILE="$PROJECT_ROOT/$EXPERIMENT_DIR/detection.log"

MALDET="$PROJECT_ROOT/maldet/target/maldet-1.0-SNAPSHOT-jar-with-dependencies.jar"
ENTROPY="$PROJECT_ROOT/entropy/entropy"
#Write header
echo "Jar,$PACKAGE_LIST" > $CSV_FILE
#echo "Jar,File,Entropy" > $ENTROPY_FILE
echo "Jar,java/lang/Runtime.exec,java/lang/ProcessBuilder.<init>,java/lang/ProcessBuilder.command,java/lang/ProcessBuilder.start,java/lang/System.load,java/lang/System.loadLibrary,java/awt/Desktop.open,jdk/JShell.eval,javax/script/ScriptEngine.eval,java/util/Base64\$Decoder.decode,java/util/Base64\$Encoder.encode,java/util/Base64\$Encoder.encodeToString,java/net/Socket.<init>,java/net/Socket.getInputStream,java/net/Socket.getOutputStream,java/net/URL.<init>,java/net/URL.openConnection,java/net/URL.openStream,java/net/URI.<init>,java/net/URI.create,java/net/URLConnection.getInputStream,java/net/http/HttpRequest\$Builder.GET,java/net/http/HttpRequest\$Builder.POST,java/net/URLClassLoader.<init>,java/lang/ClassLoader.loadClass,java/lang/Class.forName,java/lang/Class.getDeclaredMethod,java/lang/Class.getDeclaredField,java/lang/Class.newInstance,java/lang/Method.invoke,java/beans/Introspector.getBeanInfo,java/lang/System.getProperty,java/lang/System.getProperties,java/lang/System.getEnv,java/net/InetAddress.getHostName,java/io/File.<init>,java/io/FileOutputStream.<init>,java/io/FileOutputStream.write,java/nio/Files.newBufferedWriter,java/nio/Files.newOutputStream,java/nio/Files.write,java/nio/Files.writeString,java/nio/Files.copy,java/io/FileWriter.write,java/io/BufferedWriter.write,java/io/RandomAccessFile.write,java/io/RandomAccessFile.read,java/io/RandomAccessFile.readFully,java/nio/Files.newInputStream,java/nio/Files.newBufferedReader,java/nio/Files.readAllBytes,java/nio/Files.readAllLines,java/io/FileInputStream.<init>,java/io/FileInputStream.read,java/io/FileReader.read,java/io/BufferedWriter.write,java/util/Scanner.<init>,java/io/BufferedReader.read" > $ENTROPY_FILE

#for each jars
for j in $(find $PATH_TO_MODS -type "f")
do
	cd $PROJECT_ROOT
	JAR_NAME=$(echo "$j" | cut -d '.' -f1 | rev | cut -d '/' -f1 | rev)
	echo " ---- $JAR_NAME ---- " >> $LOG_FILE
	if [ -z "$JAR_NAME" ]
	then
		echo " ---- EMPTY ---- " >> $LOG_FILE
	else
		# upack
		mkdir $PROJECT_ROOT/$EXPERIMENT_DIR/tmp/$JAR_NAME
		cd $PROJECT_ROOT/$EXPERIMENT_DIR/tmp/$JAR_NAME
		jar xf $j
		cd $PROJECT_ROOT
		
		# run maldet
		#java -jar $MALDET -class-dir $PROJECT_ROOT/$EXPERIMENT_DIR/tmp/$JAR_NAME -csv $CSV_FILE -package-list $PACKAGE_LIST >> $LOG_FILE 2>&1
		java -jar $MALDET -class-dir $PROJECT_ROOT/$EXPERIMENT_DIR/tmp/$JAR_NAME -csv $CSV_FILE >> $LOG_FILE 2>&1
		
		# for each file
		for c in $(find $PROJECT_ROOT/$EXPERIMENT_DIR/tmp/$JAR_NAME -name '*.class')
		do
			# run entropy
			ENT=$(timeout 1s $ENTROPY $c)
			if [ $? -ne 0 ]; then
				echo "$c" >> $FAILED_ENTROPY_FILE
			fi
			echo "$JAR_NAME,$c,$ENT" >> $ENTROPY_FILE
		done
		
		rm -rf $PROJECT_ROOT/$EXPERIMENT_DIR/tmp/$JAR_NAME
	fi
done
