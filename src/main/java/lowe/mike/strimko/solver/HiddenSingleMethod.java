package lowe.mike.strimko.solver;

import java.util.Set;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * {@code HiddenSingleMethod} represents the 'Hidden Single' solving method.
 * <p>
 * If a candidate only appears once in a set of possible numbers for a row,
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
	 *            the {@link Set} of hints to update
	 * @return {@code true} if any changes where made to the {@link Grid},
	 *         {@code false} otherwise
	 */
	static boolean run(Grid grid, Set<Position> hints) {
		if (runOverRows(grid, hints))
			return true;
		if (runOverColumns(grid, hints))
			return true;
		if (runOverStreams(grid, hints))
			return true;

		return false;
	}

	static boolean runOverRows(Grid grid, Set<Position> hints) {
		return run(grid, hints, grid.getRows());
	}

	static boolean runOverColumns(Grid grid, Set<Position> hints) {
		return run(grid, hints, grid.getColumns());
	}

	static boolean runOverStreams(Grid grid, Set<Position> hints) {
		return run(grid, hints, grid.getStreams());
	}

	private static boolean run(Grid grid, Set<Position> hints, Set<Set<Cell>> groups) {
		int size = grid.getSize();

		for (Set<Cell> group : groups)
			// check if this possible number appears once in the group of cells
			for (int number = 1; number <= size; number++)
				if (runMethod(grid, hints, group, number))
					return true;

		return false;
	}

	private static boolean runMethod(Grid grid, Set<Position> hints, Set<Cell> group, int number) {
		int count = 0;
		Position position = null;

		// count occurrences of number in the group
		for (Cell cell : group) {
			if (cell.getPossibleNumbers().contains(number)) {
				count++;
				position = cell.getPosition();
			}
		}

		// if it occurs once, then we have found a hidden single
		if (count == 1) {
			// set number and add to hints
			Cell cell = grid.getCell(position);
			cell.setNumber(number);
			grid.updatePossibleNumbers();
			hints.add(position);
			return true;
		}

		return false;
	}
}