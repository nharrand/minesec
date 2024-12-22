# Minesec

Utilities:
 - scrap: CurseForge scraper
 - scripts: helper for analysis results exploration
 - MaliJar: Malware prototype for testing purposes
 - R: R notebooks
 - data: mod lists, analyses results
 - server: Script to run a minecraft server with docker and docker-compose.

Detection tools:
 - entropy: find the highest entropy on a sliding windows on a file.
 - maldet: Static analyzer: Find a list static call to "suspicious API elements" from a jar.
 - sysdig: chisel to perform dynamic analysis on a running container. So far it monitors files access and network access.

## Road Map

 - Scrap modrynth
 - Scrap planetminecraft
 - Scrap Discord
 - Improve entropy detector with obfuscation testbed
 - Improve static analyser to reduce false positive
 - Improve dynamic analyzer by making a clean server capture to compare moded server with baseline
 - Add other detection tools
