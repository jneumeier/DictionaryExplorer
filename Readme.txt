****************************************************************
Program Name:  Dictionary Explorer
Author:        John Neumeier
Language: Java
Requires: A text file containing words individually on each
	  line. Store in the root project folder. File ideally
	  should be a 'dictionary' of words only.
	  No definitions.
Abstract: This is a command line application.
	  I made this program to help find interesting names
	  for things, for spontaneity in creative writing,
	  and for naming songs, albums, and musical groups.
	  This program will output a random word, or word,
	  depending on the user's preference on how many words
	  they would like to see in one line. There are other
	  possibilities given to the user--to save a word,
	  look up a word by automatically launching a web
	  browser, type in a word manually, etc.
***************************************************************

To implement:
-faster word extraction when using max and min word length
	parameters by imbedding the length check inside of the
	core GetWordFromFile function
- a user-desired 'dictionary selection' allowing for more
	common words, rarely used words, or a mix of the two
	to be utilized at will
