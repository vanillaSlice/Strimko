package lowe.mike.strimko.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * {@code PuzzleFileHandler} provides useful methods for reading and writing
 * puzzle files.
 * <p>
 * Instances of {@code PuzzleFileHandler} cannot be created.
 * 
 * @author Mike Lowe
 */
public final class PuzzleFileHandler {

	// don't want instances
	private PuzzleFileHandler() {
	}

	// move these to util

	// list files --- type(including all) --- difficulty (including all) ----
	// standard or user generated (all)

	public static Puzzle read(File file) throws ClassNotFoundException, FileNotFoundException, IOException {
		checkNotNull(file, "file cannot be null");
		try (FileInputStream fileInputStream = new FileInputStream(file);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
			return (Puzzle) objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("file '" + file.getAbsolutePath() + "' does not contain a puzzle");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("could not find file '" + file.getAbsolutePath() + "'");
		} catch (IOException e) {
			throw new IOException("an error occurred when reading the file '" + file.getAbsolutePath() + "'");
		}
	}

	public static void write(Puzzle puzzle) throws FileNotFoundException, IOException {
		checkNotNull(puzzle, "puzzle cannot be null");
		if (puzzle.getGrid().isSolved())
			throw new IllegalArgumentException("cannot write a puzzle that is already solved to file");
		File file = getFileToWritePuzzleTo(puzzle);
		try (FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
			objectOutputStream.writeObject(puzzle);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("could not find file '" + file.getAbsolutePath() + "'");
		} catch (IOException e) {
			throw new IOException("an error occurred when writing puzzle to the file '" + file.getAbsolutePath() + "'");
		}
	}

	private static File getFileToWritePuzzleTo(Puzzle puzzle) {
		String type = puzzle.getType().toString().toLowerCase();
		String difficulty = puzzle.getDifficulty().toString().toLowerCase();
		String directoryPath = getUserDataDirectory() + File.separator + type + File.separator + difficulty;
		File directory = new File(directoryPath);
		directory.mkdirs();
		return getNextAvailableFile(directory);
	}

	private static String getUserDataDirectory() {
		return System.getProperty("user.home") + File.separator + ".strimko" + File.separator + "puzzles";
	}

	private static File getNextAvailableFile(File directory) {
		List<File> files = Arrays.asList(directory.listFiles());

		int fileNumber = 1;
		while (true) {
			String fileName = fileNumber + ".ser";
			File file = new File(directory, fileName);
			if (!files.contains(file))
				return file;
			fileNumber++;
		}
	}
}