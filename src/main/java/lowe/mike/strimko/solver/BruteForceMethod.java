package lowe.mike.strimko.solver;

import java.util.Set;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Difficulty;
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
	 *            the {@link Set} of hints to update
	 * @return the {@link Result} of running the brute-force method
	 */
	static Result run(Grid grid, Set<Position> hints) {
		// used to keep note of information when running algorithm
		Note note = new Note();

		runMethod(grid, note);

		// do check twice to 100% make sure that the puzzle is solved
		if (!note.foundSolution || !note.solution.isSolved())
			return Result.newUnsolvableInstance();

		if (note.foundMultipleSolutions)
			return Result.newMultipleSolutionsInstance();

		return Result.newSolvableInstance(Difficulty.HARD, note.solution, hints);
	}

	private static boolean runMethod(Grid grid, Note note) {
		int size = grid.getSize();

		for (Set<Cell> row : grid.getRows()) {
			for (Cell cell : row) {

				// only interested in cells that haven't been set yet
				if (cell.isEmpty()) {

					// if the cell is empty and has no possibilities, we must
					// return false as
					// something has gone wrong
					if (cell.getPossibleNumbers().isEmpty())
						return false;

					// try out each possible number to see if it is correct for
					// this cell
					for (int number = 1; number <= size; number++) {

						// we can try out this number
						if (cell.getPossibleNumbers().contains(number)) {
							cell.setNumber(number);
							grid.updatePossibleNumbers();

							// recursively call to see if cells after this can
							// be set
							if (runMethod(grid, note)) {

								// if we have found one solution, continue to
								// see if we can find another
								if (!note.foundSolution) {
									note.foundSolution = true;
									// make note of solution
									note.solution = new Grid(grid);
									// make sure we clear the number
									cell.clearNumber();
									grid.resetPossibleNumbers();
									grid.updatePossibleNumbers();
									return false;
								}

								// found another solution so we can exit the
								// method
								note.foundMultipleSolutions = true;
								return true;
							}
							// not this number, so clear it and try the next one
							cell.clearNumber();
							grid.resetPossibleNumbers();
							grid.updatePossibleNumbers();
						}
					}
					return false;
				}
			}
		}

		return true;
	}

	private static class Note {
		private boolean foundSolution;
		private boolean foundMultipleSolutions;
		private Grid solution;
	}
}