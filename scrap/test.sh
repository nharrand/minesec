#!/bin/bash

function test() {
	while IFS= read foo
	do
		echo $foo
	done
}

head ../data/mod-list.csv | test
