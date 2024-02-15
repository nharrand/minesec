#!/bin/bash

echo "begin"

for (( j = 0; j < 100; j++ )); do
	echo "l:$j"
	for (( i = 0; i <= 255; i++ )); do
	    printf "\\x$(printf "%x" $i)" >> "byte_sequence.txt"
	done
done


echo "end"
