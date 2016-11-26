package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.solver.Util.getCellsContainingPossible;

import java.util.HashSet;
import java.util.Set;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;

/**
 * {@code GroupInteractionsMethod} represents the 'Pointing N' and 'Stream Line
 * Reduction N' solving methods.
 * <p>
 * The 'Pointing N' method is as follows:
 * <p>
 * If there are N cells containing a possible number in a stream and all these
 * cells belong to the same row/column, we can safely remove this number from
 * all the the other cells in the row/column but outside of the stream.
 * <p>
 * The 'Stream Line Reduction N' method is as follows:
 * <p>
 * If there are N cells containing a possible number in a row/column and all
 * these cells belong to the same stream, we can safely remove this number from
 * all the other cells in the stream but outside of the row/column.
 * <p>
 * Instances of {@code GroupInteractionsMethod} cannot be created.
 * 
 * @author Mike Lowe
 */
final class GroupInteractionsMethod {
	private enum Mode {
		POINTING, STREAM_LINE_REDUCTION;
	}

	// don't want instances
	private GroupInteractionsMethod() {
	}

	/**
	 * Runs 'Pointing N' method.
	 * 
	 * @param grid
	 *            the {@link Grid} to run method over
	 * @param n
	 *            number of possible numbers that must be shared
	 * @return {@code true} if any changes where made to the {@link Grid},
	 *         {@code false} otherwise
	 */
	static boolean runPointingN(Grid grid, int n) {
		return run(Mode.POINTING, grid, n);
	}

	/**
	 * Runs 'Stream Line Reduction N' method.
	 * 
	 * @param grid
	 *            the {@link Grid} to run method over
	 * @param n
	 *            number of possible numbers that must be shared
	 * @return {@code true} if any changes where made to the {@link Grid},
	 *         {@code false} otherwise
	 */
	static boolean runStreamLineReductionN(Grid grid, int n) {
		return run(Mode.STREAM_LINE_REDUCTION, grid, n);
	}

	private static boolean run(Mode mode, Grid grid, int n) {
		if (runOverRows(mode, grid, n))
			return true;
		if (runOverColumns(mode, grid, n))
			return true;

		return false;
	}

	private static boolean runOverRows(Mode mode, Grid grid, int n) {
		if (mode == Mode.STREAM_LINE_REDUCTION)
			return run(mode, grid.getRows(), grid, n, grid.getSize());
		return run(mode, grid.getStreams(), grid, n, grid.getSize());
	}

	private static boolean runOverColumns(Mode mode, Grid grid, int n) {
		if (mode == Mode.STREAM_LINE_REDUCTION)
			return run(mode, grid.getColumns(), grid, n, grid.getSize());
		return run(mode, grid.getStreams(), grid, n, grid.getSize());
	}

	static boolean runPointingNOverRows(Grid grid, int n) {
		return runOverRows(Mode.POINTING, grid, n);
	}

	static boolean runPointingNOverColumns(Grid grid, int n) {
		return runOverColumns(Mode.POINTING, grid, n);
	}

	static boolean runStreamLineReductionNOverRows(Grid grid, int n) {
		return runOverRows(Mode.STREAM_LINE_REDUCTION, grid, n);
	}

	static boolean runStreamLineReductionNOverColumns(Grid grid, int n) {
		return runOverColumns(Mode.STREAM_LINE_REDUCTION, grid, n);
	}

	private static boolean run(Mode mode, Set<Set<Cell>> groups, Grid grid, int n, int size) {
		for (Set<Cell> group : groups) {
			for (int number = 1; number <= size; number++) {
				Set<Cell> cellsContainingNumber = getCellsContainingPossible(group, number);

				if (foundGroupInteraction(cellsContainingNumber, n))
					if (removeFromCells(mode, number, grid, n, cellsContainingNumber))
						return true;
			}
		}
		return false;
	}

	private static boolean foundGroupInteraction(Set<Cell> cellsContainingNumber, int n) {
		return cellsContainingNumber.size() == n;
	}

	private static boolean removeFromCells(Mode mode, int number, Grid grid, int n, Set<Cell> cellsContainingNumber) {
		Set<Integer> rowNumbers = new HashSet<>();
		Set<Integer> columnNumbers = new HashSet<>();
		Set<Integer> streamNumbers = new HashSet<>();

		countNumberOfCellsInEachGroup(cellsContainingNumber, rowNumbers, columnNumbers, streamNumbers);

		return removeNumber(mode, grid, cellsContainingNumber, number, rowNumbers, columnNumbers, streamNumbers);
	}

	private static void countNumberOfCellsInEachGroup(Set<Cell> cellsContainingNumber, Set<Integer> rowNumbers,
			Set<Integer> columnNumbers, Set<Integer> streamNumbers) {
		for (Cell cell : cellsContainingNumber) {
			rowNumbers.add(cell.getPosition().getRow());
			columnNumbers.add(cell.getPosition().getColumn());
			streamNumbers.add(cell.getStream());
		}
	}

	private static boolean removeNumber(Mode mode, Grid grid, Set<Cell> cellsContainingNumber, int number,
			Set<Integer> rowNumbers, Set<Integer> columnNumbers, Set<Integer> streamNumbers) {
		if (mode == Mode.STREAM_LINE_REDUCTION)
			return removeNumberFromStream(grid, cellsContainingNumber, number, streamNumbers);
		else
			return removeNumberFromRowAndColumn(grid, cellsContainingNumber, number, rowNumbers, columnNumbers);
	}

	private static boolean removeNumberFromStream(Grid grid, Set<Cell> cellsContainingNumber, int number,
			Set<Integer> streamNumbers) {
		if (streamNumbers.size() == 1)
			if (removePossibleFromGroupExcept(number, grid.getStream(getGroupNumber(streamNumbers)),
					cellsContainingNumber))
				return true;
		return false;
	}

	private static boolean removeNumberFromRowAndColumn(Grid grid, Set<Cell> cellsContainingNumber, int number,
			Set<Integer> rowNumbers, Set<Integer> columnNumbers) {
		if (rowNumbers.size() == 1)
			if (removePossibleFromGroupExcept(number, grid.getRow(getGroupNumber(rowNumbers)), cellsContainingNumber))
				return true;

		if (columnNumbers.size() == 1)
			if (removePossibleFromGroupExcept(number, grid.getColumn(getGroupNumber(columnNumbers)),
					cellsContainingNumber))
				return true;

		return false;
	}

	static boolean removePossibleFromGroupExcept(int possible, Set<Cell> group, Set<Cell> except) {
		boolean changed = false;

		for (Cell cell : group)
			if (!except.contains(cell))
				if (cell.getPossibleNumbers().remove(possible))
					changed = true;

		return changed;
	}

	private static int getGroupNumber(Set<Integer> groupNumbers) {
		return groupNumbers.iterator().next();
	}
}