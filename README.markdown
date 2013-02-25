# Scrabble Project
*@authors: Bernard Debecker, Arnaud Morel, Romain Foncier*

### Description :

Scrabble is a word game in which two to four players score points by forming words from individual lettered tiles on a gameboard marked with a 15-by-15 grid. The words are formed across and down in crossword fashion and must appear in a standard dictionary.

#### MAVEN CONFIGURATION ####

From now the projects are managed by [Maven](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) a very great *Project Management Tools* which allows to directly load dependencies, compile & build, launch the unittest, ...

All new modules, packages or plugins must be added in the `POM.xml` in each project folder. If you search a module, looking [here](http://search.maven.org/)

To add this module, just issue the following lines in the POM.xml file :

	// Example with Jackson :
	<dependency>
    <groupId>org.codehaus.jackson</groupId>
    <artifactId>jackson-mapper-asl</artifactId>
    <version>1.8.5</version>
  </dependency>

For more informations about Maven, here is few documentation :

+ [Maven comprehension](http://www.mkyong.com/tutorials/maven-tutorials/)
+ [Maven build cycle](http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
+ [Maven dependencies](http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html)
+ [Maven and Netbeans](http://wiki.netbeans.org/MavenBestPractices)
+ [Naming conventions](http://maven.apache.org/guides/mini/guide-naming-conventions.html)