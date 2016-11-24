package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.solver.Util.getCellsContainingPossible;
import static lowe.mike.strimko.model.solver.Util.setNumberUpdateGridAndAddToHints;

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
			for (int number = 1; number <= size; number++)
				if (groupContainsHiddenSingle(grid, hints, group, number))
					return true;

		return false;
	}

	private static boolean groupContainsHiddenSingle(Grid grid, Set<Position> hints, Set<Cell> group, int number) {
		Set<Cell> cellsContainingPossible = getCellsContainingPossible(group, number);

		if (foundCellWithHiddenSingle(cellsContainingPossible)) {
			setNumberUpdateGridAndAddToHints(getCell(cellsContainingPossible), number, grid, hints);
			return true;
		}

		return false;
	}

	private static boolean foundCellWithHiddenSingle(Set<Cell> cellsContainingPossible) {
		return cellsContainingPossible.size() == 1;
	}

	private static Cell getCell(Set<Cell> cellsContainingPossible) {
		return cellsContainingPossible.iterator().next();
	}
}