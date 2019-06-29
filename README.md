# Petris

This is a tetris clone.

## Instructions

Move with ARROW KEYS and use ENTER KEY to select if you want to play.

You move the actual piece with ARROW KEYS.
Use UP ARROW KEY to rotate the piece, use DOWN ARROW KEY to accelerate the fall of the piece.

You can use the V KEY to hold a part your actual piece or exchange with an old one that is there.

The points are calculated this way:

* +500 for every line you delete
* +10 for every time you accelerate the fall of a piece 

## Building
To build an executbale *.jar file go into the root folder of the repository and open a terminal and type
```
./gradlew desktop:dist
```
Your *.jar will be in 
```
/desktop/build/libs/
```
