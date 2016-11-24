package lowe.mike.strimko.model.solver;

import static java.util.Arrays.asList;
import static lowe.mike.strimko.model.solver.GroupInteractionsMethod.runPointingNOverColumns;
import static lowe.mike.strimko.model.solver.GroupInteractionsMethod.runPointingNOverRows;
import static lowe.mike.strimko.model.solver.GroupInteractionsMethod.runStreamLineReductionNOverColumns;
import static lowe.mike.strimko.model.solver.GroupInteractionsMethod.runStreamLineReductionNOverRows;
import static lowe.mike.strimko.model.solver.TestHelper.newSudokuGrid;
import static lowe.mike.strimko.model.solver.TestHelper.testCellsAndExpectedPossibles;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;

/**
 * @author Mike Lowe
 */
public final class GroupInteractionsMethodTest {
	private static final Grid GRID_1;
	private static final Grid GRID_2;
	private static final Grid GRID_3;
	private static final Grid GRID_4;

	static {

		int[][] grid1LockedNumbers = { { 0, 3, 0, 7, 8, 1, 0, 5, 0 }, { 7, 0, 0, 2, 3, 9, 6, 8, 0 },
				{ 0, 0, 0, 6, 5, 4, 0, 7, 3 }, { 5, 0, 0, 0, 0, 7, 3, 9, 8 }, { 3, 1, 7, 9, 2, 8, 5, 4, 6 },
				{ 4, 8, 9, 3, 6, 5, 7, 1, 2 }, { 0, 0, 0, 0, 0, 2, 0, 3, 0 }, { 1, 0, 0, 0, 0, 3, 0, 6, 0 },
				{ 0, 0, 3, 0, 0, 6, 4, 2, 7 } };

		GRID_1 = newSudokuGrid(grid1LockedNumbers);

		int[][] grid2LockedNumbers = { { 9, 3, 0, 0, 5, 0, 0, 0, 0 }, { 2, 0, 0, 6, 3, 0, 0, 9, 5 },
				{ 8, 5, 6, 0, 0, 2, 0, 0, 0 }, { 0, 0, 3, 1, 8, 0, 5, 7, 0 }, { 0, 0, 5, 0, 2, 0, 9, 8, 0 },
				{ 0, 8, 0, 0, 0, 5, 0, 0, 0 }, { 0, 0, 0, 8, 0, 0, 1, 5, 9 }, { 5, 0, 8, 2, 1, 0, 0, 0, 4 },
				{ 0, 0, 0, 5, 6, 0, 0, 0, 8 } };

		GRID_2 = newSudokuGrid(grid2LockedNumbers);

		int[][] grid3LockedNumbers = { { 0, 1, 6, 0, 0, 7, 8, 0, 3 }, { 0, 9, 0, 8, 0, 0, 0, 0, 0 },
				{ 8, 7, 0, 0, 0, 1, 0, 6, 0 }, { 0, 4, 8, 0, 0, 0, 3, 0, 0 }, { 6, 5, 0, 0, 0, 9, 0, 8, 2 },
				{ 2, 3, 9, 0, 0, 0, 6, 5, 0 }, { 0, 6, 0, 9, 0, 0, 0, 2, 0 }, { 0, 8, 0, 0, 0, 2, 9, 3, 6 },
				{ 9, 2, 4, 6, 0, 0, 5, 1, 0 } };

		GRID_3 = newSudokuGrid(grid3LockedNumbers);

		GRID_4 = newSudokuGrid(grid3LockedNumbers);
	}

	@Test
	public void pointingNInRowTest() {
		boolean changed = runPointingNOverRows(GRID_1, 2);
		Map<Cell, Set<Integer>> cellsAndExpectedPossibles = new HashMap<>();
		cellsAndExpectedPossibles.put(GRID_1.getCell(6, 3), new TreeSet<>(asList(4, 5, 8)));
		cellsAndExpectedPossibles.put(GRID_1.getCell(6, 4), new TreeSet<>(asList(4, 7, 9)));
		testCellsAndExpectedPossibles(changed, cellsAndExpectedPossibles);
	}

	@Test
	public void pointingNInColumnTest() {
		boolean changed = runPointingNOverColumns(GRID_2, 3);
		Map<Cell, Set<Integer>> cellsAndExpectedPossibles = new HashMap<>();
		cellsAndExpectedPossibles.put(GRID_2.getCell(4, 5), new TreeSet<>(asList(4, 6, 7)));
		testCellsAndExpectedPossibles(changed, cellsAndExpectedPossibles);
	}

	@Test
	public void streamLineReductionNInRowTest() {
		boolean changed = runStreamLineReductionNOverRows(GRID_3, 2);
		Map<Cell, Set<Integer>> cellsAndExpectedPossibles = new HashMap<>();
		cellsAndExpectedPossibles.put(GRID_3.getCell(1, 4), new TreeSet<>(asList(3, 4, 5, 6)));
		cellsAndExpectedPossibles.put(GRID_3.getCell(2, 3), new TreeSet<>(asList(3, 4, 5)));
		cellsAndExpectedPossibles.put(GRID_3.getCell(2, 4), new TreeSet<>(asList(3, 4, 5, 9)));
		testCellsAndExpectedPossibles(changed, cellsAndExpectedPossibles);
	}

	@Test
	public void streamLineReductionNInColumnTest() {
		boolean changed = runStreamLineReductionNOverColumns(GRID_4, 2);
		Map<Cell, Set<Integer>> cellsAndExpectedPossibles = new HashMap<>();
		cellsAndExpectedPossibles.put(GRID_4.getCell(1, 6), new TreeSet<>(asList(1, 2, 7)));
		cellsAndExpectedPossibles.put(GRID_4.getCell(1, 8), new TreeSet<>(asList(1, 5, 7)));
		cellsAndExpectedPossibles.put(GRID_4.getCell(2, 6), new TreeSet<>(asList(2)));
		cellsAndExpectedPossibles.put(GRID_4.getCell(2, 8), new TreeSet<>(asList(5, 9)));
		testCellsAndExpectedPossibles(changed, cellsAndExpectedPossibles);
	}
}