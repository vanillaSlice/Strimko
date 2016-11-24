package lowe.mike.strimko;

import static lowe.mike.strimko.model.PuzzleFileHandler.syncPuzzlesToUserDirectory;

import java.io.IOException;

/**
 * @author Mike Lowe
 */
public final class Launcher {
	public static void main(String[] args) {
		try {
			syncPuzzlesToUserDirectory();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}