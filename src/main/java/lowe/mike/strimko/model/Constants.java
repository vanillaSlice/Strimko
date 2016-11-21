package lowe.mike.strimko.model;

/**
 * {@code Constants} defines constant values that are useful for the data model.
 * <p>
 * Instances of {@code Constants} cannot be created.
 * 
 * @author Mike Lowe
 */
public final class Constants {

	// don't want instances
	private Constants() {
	}

	public static final int MIN_STRIMKO_SIZE = 3;
	public static final int MAX_STRIMKO_SIZE = 7;
	public static final int SUDOKU_SIZE = 9;
	public static final int[][] SUDOKU_STREAMS = new int[][] { { 1, 1, 1, 2, 2, 2, 3, 3, 3 },
			{ 1, 1, 1, 2, 2, 2, 3, 3, 3 }, { 1, 1, 1, 2, 2, 2, 3, 3, 3 }, { 4, 4, 4, 5, 5, 5, 6, 6, 6 },
			{ 4, 4, 4, 5, 5, 5, 6, 6, 6 }, { 4, 4, 4, 5, 5, 5, 6, 6, 6 }, { 7, 7, 7, 8, 8, 8, 9, 9, 9 },
			{ 7, 7, 7, 8, 8, 8, 9, 9, 9 }, { 7, 7, 7, 8, 8, 8, 9, 9, 9 } };
}