package lowe.mike.strimko.solver;

import java.util.List;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * {@code NakedSingleMethod} represents the 'Naked Single' solving method.
 * <p>
 * When a cell which has only one candidate in its list of possible numbers,
 * then it must be this number.
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
	 *            the {@link List} of hints to update
	 * @return {@code true} if any changes where made to the {@link Grid},
	 *         {@code false} otherwise
	 */
	static boolean run(Grid grid, List<Position> hints) {
		for (List<Cell> row : grid.getRows()) {
			for (Cell cell : row) {
				if (cell.getPossibleNumbers().size() == 1) {
					int number = cell.getPossibleNumbers().iterator().next();
					cell.setNumber(number);
					hints.add(cell.getPosition());
					return true;
				}
			}
		}

		return false;
	}
}