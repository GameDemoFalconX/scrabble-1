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

