# Strimko

Strimko is a logic puzzle similar to Sudoku. The objective of the game, given a grid of size *n*, is to fill
in the grid with missing numbers such that each:
* row contains numbers 1 to *n*
* column contains numbers 1 to *n*
* stream contains numbers 1 to *n*

See [Strimko™](http://www.strimko.com/index.htm) for more details.

## Features
![screenshot](/screenshots/screenshot-1.png)

There are two modes in the application:
* Play mode
* Solve mode

### Play Mode
* Play Strimko and Sudoku puzzles of easy, medium and hard difficulty
* Show pencil marks as an aid to solve each puzzle
* Hint highlighting
* Show solution

### Solve Mode
* Computer solves Strimko and Sudoku puzzles
* Determines if a puzzle is solvable or has multiple solutions
* Determines the difficulty of a puzzle
* Puzzle can be saved and played by the user

## Getting Started

### Prerequisites
* Java 8
* Gradle (optional)

### Running
To run the application:

1. Clone the project
2. Go to dist/ and double-click Strimko.jar or go to your terminal/command prompt and run:

    ```
    java -jar Strimko.jar
    ```

### Building
If you want to build the project yourself:

1. Navigate to the project directory in your terminal/command prompt
2. If you have Gradle installed locally, run the Gradle Daemon:

    ```
    gradle clean fatjar
    ```
   If you don't have Gradle installed locally and are running on a Unix-like platform such as Linux or Mac OS X, run:
    ```
    ./gradlew clean fatjar
    ```
   If you don't have Gradle installed locally and are running on Windows, run:
    ```
    gradlew clean fatjar
    ```   
3. Go to build/libs and run:

    ```
    java -jar Strimko-all-1.0.jar
    ```

## Technology Used
For those of you that are interested, the technology used in this projects includes:

* Java 8
* JavaFX (for the GUI)
* Guava (for validation and collections)
* JUnit (for unit testing)

## Useful Links
Resources useful for the completion of this project:

* [Strimko™](http://www.strimko.com/index.htm)
* [Sudoku Solutions](http://www.sudoku-solutions.com/)
* [JavaFX](http://docs.oracle.com/javase/8/javase-clienttechnologies.htm)
* [Guava](https://github.com/google/guava)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
