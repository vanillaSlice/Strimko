package lowe.mike.strimko.solver;

import java.util.Collection;
import java.util.List;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * {@code HiddenSingleMethod} represents the 'Hidden Single' solving method.
 * <p>
 * If a candidate only appears once in a list of possible numbers for a row,
 * column or stream, then the cell containing this possible must be this number.
 * <p>
 * Instances of {@code HiddenSingleMethod} cannot be created.
 * 
 * @author Mike Lowe
 */
final class HiddenSingleMethod {

	// don't want instances
	private HiddenSingleMethod() {
	}

	/**
	 * Runs 'Hidden Single' method.
	 * 
	 * @param grid
	 *            the {@link Grid} to run method over
	 * @param hints
	 *            the {@link List} of hints to update
	 * @return {@code true} if any changes where made to the {@link Grid},
	 *         {@code false} otherwise
	 */
	static boolean run(Grid grid, List<Position> hints) {
		if (run(grid, hints, grid.getRows()))
			return true;
		if (run(grid, hints, grid.getColumns()))
			return true;
		if (run(grid, hints, grid.getStreams()))
			return true;

		return false;
	}

	private static boolean run(Grid grid, List<Position> hints, Collection<List<Cell>> groups) {
		int size = grid.getSize();

		for (List<Cell> group : groups)
			for (int number = 1; number <= size; number++)
				if (runMethod(grid, hints, group, number))
					return true;

		return false;
	}

	private static boolean runMethod(Grid grid, List<Position> hints, List<Cell> group, int number) {
		int count = 0;
		Position position = null;

		for (Cell cell : group) {
			if (cell.getPossibleNumbers().contains(number)) {
				count++;
				position = cell.getPosition();
			}
		}

		if (count == 1) {
			grid.getCell(position).setNumber(number);
			hints.add(position);
			return true;
		}

		return false;
	}
}