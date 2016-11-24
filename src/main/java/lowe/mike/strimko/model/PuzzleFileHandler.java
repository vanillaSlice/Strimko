package lowe.mike.strimko.model;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * {@code PuzzleFileHandler} provides useful methods for reading and writing
 * puzzle files.
 * <p>
 * Instances of {@code PuzzleFileHandler} cannot be created.
 * 
 * @author Mike Lowe
 */
public final class PuzzleFileHandler {
	private static final String USER_PUZZLE_DIRECTORY = System.getProperty("user.home") + File.separator + ".strimko"
			+ File.separator + "puzzles";
	private static final String EXTENSION = ".ser";

	// don't want instances
	private PuzzleFileHandler() {
	}

	/**
	 * Copies {@link Puzzle} file resources to the user's home directory.
	 * 
	 * @throws IOException
	 *             if any I/O errors occur when reading and copying resources
	 */
	public static void syncPuzzlesToUserDirectory() throws IOException {
		Set<String> pathsToPuzzles = getSetOfPathsToPuzzles();

		for (String pathToPuzzle : pathsToPuzzles) {
			InputStream inputStream = PuzzleFileHandler.class.getResourceAsStream("/puzzles/" + pathToPuzzle);
			Path outputPath = Paths.get(USER_PUZZLE_DIRECTORY, pathToPuzzle);
			outputPath.toFile().getParentFile().mkdirs();
			Files.copy(inputStream, outputPath, REPLACE_EXISTING);
			inputStream.close();
		}
	}

	private static Set<String> getSetOfPathsToPuzzles() {
		Set<String> pathsToPuzzles = new HashSet<>();
		InputStream inputStream = PuzzleFileHandler.class.getResourceAsStream("/puzzles.txt");
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNextLine())
			pathsToPuzzles.add(scanner.nextLine());
		scanner.close();
		return pathsToPuzzles;
	}

	/**
	 * Returns a {@link Set} of puzzle file names of a given {@link Type} and
	 * {@link Difficulty}.
	 * 
	 * @param type
	 *            the {@link Type}
	 * @param difficulty
	 *            the {@link Difficulty}
	 * @return the {@link Set} of file names
	 */
	public static Set<String> listPuzzleFileNames(Type type, Difficulty difficulty) {
		Set<String> puzzleFileNames = new LinkedHashSet<>();

		File puzzleSubDirectory = getPuzzleSubDirectory(type, difficulty);

		if (!puzzleSubDirectory.exists())
			return puzzleFileNames;

		for (File file : puzzleSubDirectory.listFiles())
			puzzleFileNames.add(getFileName(file));

		return puzzleFileNames;
	}

	private static String getFileName(File file) {
		return file.getName().replace(EXTENSION, "");
	}

	/**
	 * Reads a {@link Puzzle} from a file given the {@link Puzzle}'s
	 * {@link Type}, {@link Difficulty} and name.
	 * 
	 * @param type
	 *            the {@link Type}
	 * @param difficulty
	 *            the {@link Difficulty}
	 * @param name
	 *            the name of the {@link Puzzle}
	 * @return the {@link Puzzle}
	 * @throws IOException
	 *             if the file does not exist, is a directory rather than a
	 *             regular file, or for some other reason cannot be opened for
	 *             reading
	 * @throws ClassNotFoundException
	 *             if a casting issue occurs when reading the file
	 */
	public static Puzzle read(Type type, Difficulty difficulty, String name)
			throws IOException, ClassNotFoundException {
		File fileToReadFrom = getFileToReadPuzzleFrom(type, difficulty, name);

		try (InputStream inputStream = new FileInputStream(fileToReadFrom);
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
			return (Puzzle) objectInputStream.readObject();
		}
	}

	private static File getPuzzleSubDirectory(Type type, Difficulty difficulty) {
		return new File(USER_PUZZLE_DIRECTORY + File.separator + type + File.separator + difficulty);
	}

	private static File getFileToReadPuzzleFrom(Type type, Difficulty difficulty, String name) {
		return new File(getPuzzleSubDirectory(type, difficulty), name + EXTENSION);
	}

	/**
	 * Writes a {@link Puzzle} to file.
	 * 
	 * @param puzzle
	 *            the {@link Puzzle} to write
	 * @throws FileNotFoundException
	 *             if the file exists but is a directory rather than a regular
	 *             file, does not exist but cannot be created, or cannot be
	 *             opened for any other reason
	 * @throws IOException
	 *             if an I/O error occurs while writing the file
	 */
	public static void write(Puzzle puzzle) throws FileNotFoundException, IOException {
		File fileToWriteTo = getFileToWritePuzzleTo(puzzle.getType(), puzzle.getDifficulty());

		try (FileOutputStream fileOutputStream = new FileOutputStream(fileToWriteTo);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
			objectOutputStream.writeObject(puzzle);
		}
	}

	private static File getFileToWritePuzzleTo(Type type, Difficulty difficulty) {
		File puzzleSubDirectory = getPuzzleSubDirectory(type, difficulty);
		puzzleSubDirectory.mkdirs();
		return getNextAvailableFile(puzzleSubDirectory, type);
	}

	private static File getNextAvailableFile(File puzzleSubDirectory, Type type) {
		List<File> files = asList(puzzleSubDirectory.listFiles());

		int fileNumber = 1;
		while (true) {
			String fileName = type + "-" + fileNumber + EXTENSION;
			File file = new File(puzzleSubDirectory, fileName);
			if (!files.contains(file))
				return file;
			fileNumber++;
		}
	}
}