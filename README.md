# Strimko

Strimko is a logic puzzle similar to Sudoku. The objective of the game, given a grid of size *n*, is to fill
in the grid with missing numbers such that each:
* row contains numbers 1 to *n*
* column contains numbers 1 to *n*
* stream contains numbers 1 to *n*

See [Strimko™](http://www.strimko.com/index.htm) for more details.

## Screenshots
TODO:

## Getting Started

### Prerequisites
* Java 8
* Gradle (optional)

### Running
1. Clone the project
2. Navigate to the project directory in your terminal/command prompt
3. If you have Gradle installed locally run the Gradle Daemon:

    ```
    gradle clean fatjar
    ```
   If you don't have Gradle installed locally and are running on a Unix-like platform such as Linux or Mac OS X:
    ```
    ./gradlew clean fatjar
    ```
   If you don't have Gradle installed locally and are running on Windows:
    ```
    gradlew clean fatjar
    ```   
4. Go to build/libs and run:
    ```
    java -jar Strimko-all-X.X.jar
    ```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
