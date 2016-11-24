package lowe.mike.strimko.model;

/**
 * {@code Type} enum represents the type of {@link Puzzle}.
 * 
 * @author Mike Lowe
 */
public enum Type {
	STRIMKO, SUDOKU;

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}