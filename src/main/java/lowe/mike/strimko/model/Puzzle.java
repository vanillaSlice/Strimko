package lowe.mike.strimko.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * {@code Puzzle} instances are intended to represent Strimko and Sudoku
 * puzzles.
 * <p>
 * Instance information includes the {@code Puzzle}'s {@link Type},
 * {@link Difficulty}, {@link Grid}, solution and a {@link Set} of hints.
 * 
 * @author Mike Lowe
 */
public final class Puzzle implements Serializable {
	private static final long serialVersionUID = 1440568892047817063L;

	private final Type type;
	private final Difficulty difficulty;
	private final Grid grid;
	private final Grid solution;
	private final Set<Position> hints = new LinkedHashSet<>();

	/**
	 * Creates a new {@code Puzzle} given the {@link Type}, {@link Difficulty},
	 * {@link Grid}, solution {@link Grid} and {@link Set} of hints.
	 * 
	 * @param type
	 *            the {@link Type}
	 * @param difficulty
	 *            the {@link Difficulty}
	 * @param grid
	 *            the {@link Grid}
	 * @param solution
	 *            the solution {@link Grid}
	 * @param hints
	 *            the {@link Set} of hints
	 */
	public Puzzle(Type type, Difficulty difficulty, Grid grid, Grid solution, Set<Position> hints) {
		this.type = type;
		this.difficulty = difficulty;
		this.grid = new Grid(grid);
		this.solution = new Grid(solution);
		this.hints.addAll(hints);
	}

	/**
	 * Copy constructor creates a new {@code Puzzle} from an existing instance.
	 * 
	 * @param puzzle
	 *            the existing {@code Puzzle} instance to create the new
	 *            instance from
	 */
	public Puzzle(Puzzle puzzle) {
		this.type = puzzle.type;
		this.difficulty = puzzle.difficulty;
		this.grid = new Grid(puzzle.grid);
		this.solution = new Grid(puzzle.solution);
		this.hints.addAll(puzzle.hints);
	}

	/**
	 * @return this {@code Puzzle}'s {@link Type}
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return this {@code Puzzle}'s {@link Difficulty}
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * @return this {@code Puzzle}'s {@link Grid}
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * @return this {@code Puzzle}'s solution {@link Grid}
	 */
	public Grid getSolution() {
		return new Grid(solution);
	}

	/**
	 * @return this {@code Puzzle}'s {@link Set} of {@link Position}s which act
	 *         as hints
	 */
	public Set<Position> getHints() {
		return new LinkedHashSet<>(hints);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, difficulty, grid, solution, hints);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Puzzle other = (Puzzle) obj;
		if (difficulty != other.difficulty)
			return false;
		if (grid == null) {
			if (other.grid != null)
				return false;
		} else if (!grid.equals(other.grid))
			return false;
		if (hints == null) {
			if (other.hints != null)
				return false;
		} else if (!hints.equals(other.hints))
			return false;
		if (solution == null) {
			if (other.solution != null)
				return false;
		} else if (!solution.equals(other.solution))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Puzzle [type=" + type + ", difficulty=" + difficulty + ", grid=" + grid + ", solution=" + solution
				+ ", hints=" + hints + "]";
	}
}