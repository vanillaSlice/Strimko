package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.Constants.SUDOKU_SIZE;
import static lowe.mike.strimko.model.Constants.SUDOKU_STREAMS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;

/**
 * @author Mike Lowe
 */
final class TestHelper {

	// don't want instances
	private TestHelper() {
	}

	static Grid newStrimkoGrid(int size, int[][] streams, int[][] lockedNumbers) {
		Grid grid = new Grid(size);
		grid.initStreams(streams);
		grid.initLockedNumbers(lockedNumbers);
		grid.updatePossibleNumbers();
		return grid;
	}

	static Grid newSudokuGrid(int[][] lockedNumbers) {
		return newStrimkoGrid(SUDOKU_SIZE, SUDOKU_STREAMS, lockedNumbers);
	}

	static void testCellsAndExpectedPossibles(boolean changed, Map<Cell, Set<Integer>> cellsAndExpectedPossibles) {
		assertTrue(changed);
		for (Cell cell : cellsAndExpectedPossibles.keySet()) {
			Set<Integer> expectedPossibles = cellsAndExpectedPossibles.get(cell);
			assertEquals(expectedPossibles, cell.getPossibleNumbers());
		}
	}
}