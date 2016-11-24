package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.Difficulty.HARD;
import static lowe.mike.strimko.model.solver.Util.cellContainsPossible;
import static lowe.mike.strimko.model.solver.Util.setNumberAndUpdateGrid;

import java.util.Set;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * {@code BruteForceMethod} represents a brute-force solving method.
 * <p>
 * A depth-first search algorithm is used to test each possible number for a
 * cell until a solution has been found.
 * <p>
 * Instances of {@code BruteForceMethod} cannot be created.
 * 
 * @author Mike Lowe
 */
final class BruteForceMethod {

	// don't want instances
	private BruteForceMethod() {
	}

	/**
	 * Runs brute-force method.
	 * 
	 * @param grid
	 *            the {@link Grid} to run method over
	 * @param hints
	 *            the {@link Set} of hints to return in the {@link Result}
	 * @return the {@link Result} of running the brute-force method
	 */
	static Result run(Grid grid, Set<Position> hints) {
		// used to keep note of information when running algorithm
		Note note = new Note();

		run(grid, note);

		if (isUnsolvable(note))
			return Result.newUnsolvableInstance();

		if (note.foundMultipleSolutions)
			return Result.newMultipleSolutionsInstance();

		return Result.newSolvableInstance(HARD, note.solution, hints);
	}

	private static boolean run(Grid grid, Note note) {
		for (Set<Cell> row : grid.getRows()) {
			for (Cell cell : row) {
				if (shouldTryAndSet(cell)) {
					if (noMorePossibles(cell))
						return false;
					return tryAndSetNumbers(grid, cell, note);
				}
			}
		}

		return true;
	}

	private static boolean shouldTryAndSet(Cell cell) {
		return cell.isEmpty();
	}

	private static boolean noMorePossibles(Cell cell) {
		return cell.getPossibleNumbers().isEmpty();
	}

	private static boolean tryAndSetNumbers(Grid grid, Cell cell, Note note) {
		int size = grid.getSize();

		for (int number = 1; number <= size; number++) {
			if (cellContainsPossible(cell, number)) {
				setNumberAndUpdateGrid(cell, number, grid);

				if (foundSolution(grid, note)) {

					if (isFirstSolution(note)) {
						updateNote(note, grid);
						clearNumberAndUpdateGrid(cell, grid);
						return false;
					} else {
						note.foundMultipleSolutions = true;
						return true;
					}

				} else
					clearNumberAndUpdateGrid(cell, grid);
			}
		}
		return false;
	}

	private static boolean foundSolution(Grid grid, Note note) {
		return run(grid, note);
	}

	private static boolean isFirstSolution(Note note) {
		return !note.foundSolution;
	}

	private static void updateNote(Note note, Grid grid) {
		note.foundSolution = true;
		note.solution = new Grid(grid);
	}

	private static void clearNumberAndUpdateGrid(Cell cell, Grid grid) {
		cell.clearNumber();
		grid.resetPossibleNumbers();
		grid.updatePossibleNumbers();
	}

	private static boolean isUnsolvable(Note note) {
		return !note.foundSolution || !note.solution.isSolved();
	}

	private static class Note {
		private boolean foundSolution;
		private boolean foundMultipleSolutions;
		private Grid solution;
	}
}