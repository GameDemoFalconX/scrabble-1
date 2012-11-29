# Scrabble Project
*@authors: Bernard Debecker, Arnaud Morel, Romain Foncier*

### Description :

Scrabble is a word game in which two to four players score points by forming words from individual lettered tiles on a gameboard marked with a 15-by-15 grid. The words are formed across and down in crossword fashion and must appear in a standard dictionary.

### Details of implementation :
*Message structure*

	|----------| |----------------|
	   header           body
	           "#"
	             |---------| |------------|
	                token         args
	                       "@"
	                        |---------| |---------| | ...
	                           args1       args2
	                                  "_"

### XML Files structure
** Warning! Before use XML File, you must load the JDOM library manually.**

+ Click right on the Scrabble server folder > Properties.
+ Select Category "Library" and click on "Add .jar Files" after download JDOM binaries [here][0] (You'll find binaries and the bin/ folder in the Scrabble repository).
+ Please select the 2.0.4 version

[0]: http://jdom.org/downloads/index.html

*Players*

	<?xml version="1.0" encoding="UTF-8"?>
	<players>
  		<player uuid="[player-uuid]">
    		<uuid>[Player-uuid]</uuid>
    		<name>[player-name]</name>
    		<password>[player-password]</password>
   		</player>
  	</players>

*Games*

	<?xml version="1.0" encoding="UTF-8"?>
	<plays>
		<player id="[player-uuid]">
			<play id="[ind]">
				<uuid>[play-uuid]</uuid>
				<created>[play-created-date]</created>
				<modified>[play-last-modified-date]</modified>
				<score>[play-score]</score>
				<grid>
					<tile>
						[x:y__L:V]
						...
					</tile>
				</grid>
				<rack>
					[rack-informations]
				</rack>
			</play>
		</player>
	</plays>