package lowe.mike.strimko.model;

/**
 * Enum representing difficulty of a {@link Puzzle}.
 * 
 * @author Mike Lowe
 */
public enum Difficulty {
	EASY, MEDIUM, HARD;

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}