package lowe.mike.strimko.model;

/**
 * Enum representing difficulty of a {@link Puzzle}.
 * 
 * @author Mike Lowe
 */
public enum Difficulty {

	EASY("Easy"), MEDIUM("Medium"), HARD("Hard");

	private final String displayName;

	private Difficulty(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}

}