package lowe.mike.strimko.solver;

import java.util.HashSet;
import java.util.Set;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

final class GroupInteractionsMethod {

	// don't want instances
	private GroupInteractionsMethod() {
	}

	static boolean run(Grid grid, int n) {
		if (runPointingNOverRows(grid, n))
			return true;
		if (runPointingNOverColumns(grid, n))
			return true;

		return false;
	}

	static boolean runPointingNOverRows(Grid grid, int n) {
		int size = grid.getSize();

		for (Set<Cell> stream : grid.getStreams()) {
			for (int number = 1; number <= size; number++) {
				Set<Cell> cellsContainingNumber = new HashSet<>();
				Set<Position> positions = new HashSet<>();

				for (Cell cell : stream) {
					if (cell.getPossibleNumbers().contains(number)) {
						cellsContainingNumber.add(cell);
						positions.add(cell.getPosition());
					}
				}

				if (cellsContainingNumber.size() == n)
					if (removeFromRowCells(number, grid, n, cellsContainingNumber, positions))
						return true;
			}
		}

		return false;
	}

	private static boolean removeFromRowCells(int number, Grid grid, int n, Set<Cell> cellsContainingNumber,
			Set<Position> positions) {
		Set<Integer> rowNumbers = new HashSet<>();

		for (Position position : positions) {
			rowNumbers.add(position.getRow());
		}

		if (rowNumbers.size() == 1)
			if (grid.removePossibleFromRowExcept(number, rowNumbers.iterator().next(), cellsContainingNumber))
				return true;

		return false;
	}

	static boolean runPointingNOverColumns(Grid grid, int n) {
		int size = grid.getSize();

		for (Set<Cell> stream : grid.getStreams()) {
			for (int number = 1; number <= size; number++) {
				Set<Cell> cellsContainingNumber = new HashSet<>();
				Set<Position> positions = new HashSet<>();

				for (Cell cell : stream) {
					if (cell.getPossibleNumbers().contains(number)) {
						cellsContainingNumber.add(cell);
						positions.add(cell.getPosition());
					}
				}

				if (cellsContainingNumber.size() == n)
					if (removeFromColumnCells(number, grid, n, cellsContainingNumber, positions))
						return true;
			}
		}

		return false;
	}

	private static boolean removeFromColumnCells(int number, Grid grid, int n, Set<Cell> cellsContainingNumber,
			Set<Position> positions) {
		Set<Integer> columnNumbers = new HashSet<>();

		for (Position position : positions) {
			columnNumbers.add(position.getColumn());
		}

		if (columnNumbers.size() == 1)
			if (grid.removePossibleFromColumnExcept(number, columnNumbers.iterator().next(), cellsContainingNumber))
				return true;

		return false;
	}
	
	static boolean runStreamLineReductionOverRows(Grid grid, int n) {
		int size = grid.getSize();

		for (Set<Cell> row : grid.getRows()) {
			for (int number = 1; number <= size; number++) {
				Set<Cell> cellsContainingNumber = new HashSet<>();
				Set<Position> positions = new HashSet<>();

				for (Cell cell : row) {
					if (cell.getPossibleNumbers().contains(number)) {
						cellsContainingNumber.add(cell);
						positions.add(cell.getPosition());
					}
				}

				if (cellsContainingNumber.size() == n)
					if (removeFromStreamCells(number, grid, n, cellsContainingNumber, positions))
						return true;
			}
		}

		return false;
	}
	
	static boolean runStreamLineReductionOverColumns(Grid grid, int n) {
		int size = grid.getSize();

		for (Set<Cell> column : grid.getColumns()) {
			for (int number = 1; number <= size; number++) {
				Set<Cell> cellsContainingNumber = new HashSet<>();
				Set<Position> positions = new HashSet<>();

				for (Cell cell : column) {
					if (cell.getPossibleNumbers().contains(number)) {
						cellsContainingNumber.add(cell);
						positions.add(cell.getPosition());
					}
				}

				if (cellsContainingNumber.size() == n)
					if (removeFromStreamCells(number, grid, n, cellsContainingNumber, positions))
						return true;
			}
		}

		return false;
	}
	
	private static boolean removeFromStreamCells(int number, Grid grid, int n, Set<Cell> cellsContainingNumber,
			Set<Position> positions) {
		Set<Integer> streamNumbers = new HashSet<>();

		for (Cell cell : cellsContainingNumber) {
			streamNumbers.add(cell.getStream());
		}
		
		if (streamNumbers.size() == 1)
			if (grid.removePossibleFromStreamExcept(number, streamNumbers.iterator().next(), cellsContainingNumber))
				return true;

		return false;
	}
}