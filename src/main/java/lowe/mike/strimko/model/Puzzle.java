package lowe.mike.strimko.model;

import static lowe.mike.strimko.solver.Solver.solve;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lowe.mike.strimko.solver.Result;

/**
 * {@code Puzzle} instances are intended to represent Strimko and Sudoku
 * puzzles.
 * <p>
 * Instance information includes the {@code Puzzle}'s {@link Type},
 * {@link Difficulty}, {@link Grid}, solution and a {@link List} of hints.
 * 
 * @author Mike Lowe
 */
public final class Puzzle implements Serializable {
	private static final long serialVersionUID = 1440568892047817063L;

	public static final int MIN_SIZE = 3;
	public static final int MAX_SIZE = 9;

	private final Type type;
	private final Difficulty difficulty;
	private final Grid grid;
	private final Grid solution;
	private final List<Position> hints = new ArrayList<>();

	/**
	 * Creates a new {@code Puzzle} given a {@link Grid}.
	 * 
	 * @param grid
	 *            the {@link Grid}
	 * @throws IllegalArgumentException
	 *             if the {@link Grid} is not solvable or has multiple solutions
	 */
	public Puzzle(Grid grid) {
		this.grid = new Grid(grid);
		this.type = determinePuzzleType();
		// solve and validate
		Result result = solve(this.grid);
		checkSolvable(result);
		checkMultipleSolutions(result);
		// initialise variables dependent on the puzzle being solved
		this.difficulty = result.getDifficulty();
		this.solution = result.getSolution();
		this.hints.addAll(result.getHints());
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
		this.hints.addAll(puzzle.getHints());
	}

	private Type determinePuzzleType() {
		if (isSudokuPuzzle())
			return Type.SUDOKU;
		return Type.STRIMKO;
	}

	/**
	 * Goes through each block of 9 cells to check that each cell in the block
	 * are in the same stream.
	 */
	private boolean isSudokuPuzzle() {
		int size = grid.getSize();

		if (size != 9)
			return false;

		for (int row = 0; row < size; row += 3)
			for (int column = 0; column < size; column += 3)
				if (!isSudokuBlock(grid, row, column))
					return false;

		return true;
	}

	/**
	 * Checks a block of nine cells to check that they are in the same stream.
	 */
	private static boolean isSudokuBlock(Grid grid, int startingRow, int startingColumn) {
		int stream = grid.getCell(startingRow, startingColumn).getPosition().getStream();

		for (int row = startingRow; row < startingRow + 3; row++) {
			for (int column = startingColumn; column < startingColumn + 3; column++) {
				Cell cell = grid.getCell(row, column);
				if (cell.getPosition().getStream() != stream)
					return false;
			}
		}

		return true;
	}

	/**
	 * Checks if the puzzle is solvable, throwing an exception if not.
	 */
	private static void checkSolvable(Result result) {
		if (!result.isSolvable())
			throw new IllegalArgumentException("puzzle is not solvable");
	}

	/**
	 * Checks if the puzzle has multiple solutions, throwing an exception if so.
	 */
	private static void checkMultipleSolutions(Result result) {
		if (result.hasMultipleSolutions())
			throw new IllegalArgumentException("puzzle has multiple solutions");
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
	 * @return this {@code Puzzle}'s solution
	 */
	public Grid getSolution() {
		return new Grid(solution);
	}

	/**
	 * @return this {@code Puzzle}'s {@link List} of {@link Position}s which act
	 *         as hints
	 */
	public List<Position> getHints() {
		return new ArrayList<>(hints);
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