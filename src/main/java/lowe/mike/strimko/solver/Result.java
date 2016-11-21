package lowe.mike.strimko.solver;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import lowe.mike.strimko.model.Difficulty;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;
import lowe.mike.strimko.model.Puzzle;

/**
 * {@code Result} instances are intended to provide information from the
 * {@link Solver} after attempting to solve a {@link Grid}.
 * <p>
 * Stored information includes whether a {@link Grid} could be solved, if it has
 * multiple solutions, the difficulty, the solution and a {@link Set} of hints.
 * <p>
 * {@code Result} instances are immutable.
 * 
 * @author Mike Lowe
 */
public final class Result {
	private final boolean solvable;
	private final boolean multipleSolutions;
	private final Difficulty difficulty;
	private final Grid solution;
	private final Set<Position> hints = new LinkedHashSet<>();

	/**
	 * Creates a new unsolvable {@code Result} instance. This indicates that a
	 * {@link Grid} could not be solved and no further information such as a
	 * solution can be provided.
	 * 
	 * @return a new unsolvable {@code Result} instance
	 */
	public static Result newUnsolvableInstance() {
		return new Result(false);
	}

	/**
	 * Creates a new multiple solutions {@code Result} instance. This indicates
	 * that a {@link Grid} has multiple solutions. No further information can be
	 * provided because a {@link Grid} should have a distinct solution.
	 * 
	 * @return a new multiple solutions {@code Result} instance
	 */
	public static Result newMultipleSolutionsInstance() {
		return new Result(true);
	}

	/**
	 * Creates a new solvable {@code Result} instance given the determined
	 * {@link Difficulty}, the solved {@link Grid} and {@link Set} of hints.
	 * 
	 * @param difficulty
	 *            the determined {@link Difficulty}
	 * @param solution
	 *            the solved {@link Grid}
	 * @param hints
	 *            the {@link Set} of hints
	 * @return a new solvable {@code Result} instance
	 */
	public static Result newSolvableInstance(Difficulty difficulty, Grid solution, Set<Position> hints) {
		return new Result(difficulty, solution, hints);
	}

	/**
	 * Creates a new {@code Result} instance given an existing instance.
	 * 
	 * @param result
	 *            the existing {@code Result} instance
	 * @return a new {@code Result} instance copied from the existing one
	 */
	public static Result copyOf(Result result) {
		return new Result(result);
	}

	private Result(boolean multipleSolutions) {
		this.solvable = multipleSolutions;
		this.multipleSolutions = multipleSolutions;
		this.difficulty = null;
		this.solution = null;
	}

	private Result(Difficulty difficulty, Grid grid, Set<Position> hints) {
		this.solvable = true;
		this.multipleSolutions = false;
		this.difficulty = difficulty;
		this.solution = new Grid(grid);
		this.hints.addAll(hints);
	}

	private Result(Result result) {
		this.solvable = result.solvable;
		this.multipleSolutions = result.multipleSolutions;
		this.difficulty = result.difficulty;
		this.solution = new Grid(result.solution);
		this.hints.addAll(result.hints);
	}

	/**
	 * @return {@code true} if the {@link Grid} could be solved; {@code false}
	 *         otherwise
	 */
	public boolean isSolvable() {
		return solvable;
	}

	/**
	 * @return {@code true} if the {@link Grid} has multiple solutions;
	 *         {@code false} otherwise
	 */
	public boolean hasMultipleSolutions() {
		return multipleSolutions;
	}

	/**
	 * @return the determined {@link Difficulty} of the {@link Puzzle}
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * @return the solution to the {@link Puzzle}
	 */
	public Grid getSolution() {
		return new Grid(solution);
	}

	/**
	 * @return a {@link Set} of hints
	 */
	public Set<Position> getHints() {
		return new LinkedHashSet<>(hints);
	}

	@Override
	public int hashCode() {
		return Objects.hash(solvable, multipleSolutions, difficulty, solution, hints);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Result other = (Result) obj;
		if (difficulty != other.difficulty)
			return false;
		if (hints == null) {
			if (other.hints != null)
				return false;
		} else if (!hints.equals(other.hints))
			return false;
		if (multipleSolutions != other.multipleSolutions)
			return false;
		if (solution == null) {
			if (other.solution != null)
				return false;
		} else if (!solution.equals(other.solution))
			return false;
		if (solvable != other.solvable)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Result [solvable=" + solvable + ", multipleSolutions=" + multipleSolutions + ", difficulty="
				+ difficulty + ", solution=" + solution + ", hints=" + hints + "]";
	}
}