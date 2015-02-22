2048
====

Adaptation of 2048

**Programming Languages used:** Java

2048 is a single-player online game created in March 2014 by 19-year-old Italian web developer Gabriele Cirulli, 
in which the objective is to slide numbered tiles on a grid to combine them and create a tile with the number 2048.
It can be regarded as a type of sliding block puzzle, and is very similar to the Threes! app released a month earlier.

This is an adaptation of the game made in java.

Gabriel Cirulli’s original game can be seen here: http://gabrielecirulli.github.io/2048/

---------------------------------------------------------------------------------
**Used:**

*	Swing library (to draw the board)

*	Java KeyEvents for keylisteners (up, down, left, right) plus a hashmap to map them to functions. You can probably use a normal map.

*	Custom classes grid and tile (made a normal array, had to cheat to make it function as a two dimensional array), too lazy to fix, might fix later.

*	enum used to store numbers and colors as constants (you want the 1024 tile or the 512 tile to be different colors but same to each other)

**If there is time:**

*	Cache of the most commonly used tiles (i.e. 0, 2, 4) (faster?)

*	Score?

*	Let the game continue past 2048

