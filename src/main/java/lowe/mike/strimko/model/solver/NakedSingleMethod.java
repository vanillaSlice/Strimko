package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.solver.Util.setNumberUpdateGridAndAddToHints;

import java.util.Set;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * {@code NakedSingleMethod} represents the 'Naked Single' solving method.
 * <p>
 * When a cell has only one candidate in its set of possible numbers, then it
 * must be this number.
 * <p>
 * Instances of {@code NakedSingleMethod} cannot be created.
 * 
 * @author Mike Lowe
 */
final class NakedSingleMethod {

	// don't want instances
	private NakedSingleMethod() {
	}

	/**
	 * Runs 'Naked Single' method.
	 * 
	 * @param grid
	 *            the {@link Grid} to run method over
	 * @param hints
	 *            the {@link Set} of hints to update
	 * @return {@code true} if any changes where made to the {@link Grid},
	 *         {@code false} otherwise
	 */
	static boolean run(Grid grid, Set<Position> hints) {
		for (Set<Cell> row : grid.getRows()) {
			for (Cell cell : row) {
				if (foundCellWithOnePossibleNumber(cell)) {
					setNumberUpdateGridAndAddToHints(cell, getNumber(cell), grid, hints);
					return true;
				}
			}
		}

		return false;
	}

	private static boolean foundCellWithOnePossibleNumber(Cell cell) {
		return cell.getPossibleNumbers().size() == 1;
	}

	private static int getNumber(Cell cell) {
		return cell.getPossibleNumbers().iterator().next();
	}
}