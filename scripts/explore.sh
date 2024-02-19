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

PROCYON="$PROJECT_ROOT/procyon-decompiler-0.6.0.jar"

DESC="$PROJECT_ROOT/data/mod-list.csv"

TMP_DIR="$PROJECT_ROOT/tmp"
JAR_NUMBER=$1

#color
Black='\033[0;30m'
DarkGray='\033[1;30m'
Red='\033[0;31m'
LightRed='\033[1;31m'
Green='\033[0;32m'
LightGreen='\033[1;32m'
Brown='\033[0;33m'
Yellow='\033[1;33m'
Blue='\033[0;34m'
LightBlue='\033[1;34m'
Purple='\033[0;35m'
LightPurple='\033[1;35m'
Cyan='\033[0;36m'
LightCyan='\033[1;36m'
LightGray='\033[0;37m'
White='\033[1;37m'
NC='\033[0m'

#printf "Colors: ${Black}Black${NC},${DarkGray}DarkGray${NC},${Red}Red${NC},${LightRed}LightRed${NC},${Green}Green${NC},${LightGreen}LightGreen${NC},${Brown}Brown${NC},${Yellow}Yellow${NC},${Blue}Blue${NC},${LightBlue}LightBlue${NC},${Purple}Purple${NC},${LightPurple}LightPurple${NC},${Cyan}Cyan${NC},${LightCyan}LightCyan${NC},${LightGray}LightGray${NC},${White}White${NC}."

function box_out()
{
  local s=("$@") b w
  for l in "${s[@]}"; do
    ((w<${#l})) && { b="$l"; w="${#l}"; }
  done
  tput setaf 3
  echo " -${b//?/-}-
| ${b//?/ } |"
  for l in "${s[@]}"; do
    printf '| %s%*s%s |\n' "$(tput setaf 4)" "-$w" "$l" "$(tput setaf 3)"
  done
  echo "| ${b//?/ } |
 -${b//?/-}-"
  tput sgr 0
}

JAR="$PATH_TO_MODS/$JAR_NUMBER.jar"
# Entropy verbose

clear

mkdir $TMP_DIR/$JAR_NUMBER
mkdir $TMP_DIR/$JAR_NUMBER/classes
mkdir $TMP_DIR/$JAR_NUMBER/sources

cd $TMP_DIR/$JAR_NUMBER/classes
jar xf $JAR
cd $PROJECT_ROOT

DESCRIPTION=$(grep "$JAR_NUMBER" $DESC)

TITLE=$(echo "$DESCRIPTION" | cut -d ',' -f1 | sed 's/"//g')
AUTHOR=$(echo "$DESCRIPTION" | cut -d ',' -f2 | sed 's/"//g')
LINK_R=$(echo "$DESCRIPTION" | cut -d ',' -f3 | sed "s|download/$JAR_NUMBER||" | sed 's/"//g')
LINK="https://curseforge.com$LINK_R"
NBDL=$(echo "$DESCRIPTION" | cut -d ',' -f4 | sed 's/"//g')

printf "${White}"
printf '%*s\n' "${COLUMNS:-$(tput cols)}" '' | tr ' ' -
printf "${NC}\n"
printf "${White}TITLE: ${LightRed} $TITLE      ${White}AUTHOR: ${LightRed} $AUTHOR      ${White}#DL: ${LightRed} $NBDL${NC}\n${White}LINK:${NC} $LINK\n"
printf "\n${White}"
printf '%*s\n' "${COLUMNS:-$(tput cols)}" '' | tr ' ' -
printf "${NC}\n"


# Maldet verbose
java -jar $MALDET -class-dir $TMP_DIR/$JAR_NUMBER/classes -v | sed "s/{/\x1b[1;36m/" | sed "s/}/\x1b[0m/" | sed "s/|/\x1b[1;31m/" | sed "s/|/\x1b[0m/"

printf "${White}"
printf '%*s\n' "${COLUMNS:-$(tput cols)}" '' | tr ' ' -
printf "${NC}\n"


# Procyon
java -jar $PROCYON -o $TMP_DIR/$JAR_NUMBER/sources $JAR > $TMP_DIR/$JAR_NUMBER/decompilation.log 2>&1

# tableau de bord
# gedit ?
read -p "Press enter to continue"
rm -rf $TMP_DIR/$JAR_NUMBER
# clean up
# next jar?
