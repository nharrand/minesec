#!/bin/bash



function handle_dl_link() {
	modurl=$1
	echo "arg: $modurl"
	projectURL_suf=$(echo "$modurl" | cut -d '/' -f 1,2,3,4)
	jarID=$(echo "$modurl" | cut -d '/' -f 6)
	projectURL="https://www.curseforge.com$projectURL_suf"
	echo "project page: $projectURL"
	node dl-page.js $projectURL project_pages/$jarID.html
	projectID=$(cat project_pages/$jarID.html | htmlq --pretty ".project-details-box" | grep -A 3 "Project ID" | tail -n 1 | sed 's/ //g')
	echo "jar at https://www.curseforge.com/api/v1/mods/$projectID/files/$jarID/download"
	curl -L --output "jars/$jarID.jar" "https://www.curseforge.com/api/v1/mods/$projectID/files/$jarID/download"
	echo "$jarID,$projectID" >> ./mod-jars.csv
}


while IFS= read foo
do
	handle_dl_link $foo
done < <(tail -n +742 ../data/mod-list.csv | cut -d '"' -f 6)
#done < <(tail -n +2 ../data/mod-list.csv | cut -d '"' -f 6)

#handle_dl_link "/minecraft/mc-mods/keepcuriosinventory/download/4808940"
