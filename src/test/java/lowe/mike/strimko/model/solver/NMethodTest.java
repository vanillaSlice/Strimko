package lowe.mike.strimko.model.solver;

import static java.util.Arrays.asList;
import static lowe.mike.strimko.model.solver.NMethod.runHiddenNOverColumns;
import static lowe.mike.strimko.model.solver.NMethod.runHiddenNOverRows;
import static lowe.mike.strimko.model.solver.NMethod.runHiddenNOverStreams;
import static lowe.mike.strimko.model.solver.NMethod.runNakedNOverColumns;
import static lowe.mike.strimko.model.solver.NMethod.runNakedNOverRows;
import static lowe.mike.strimko.model.solver.NMethod.runNakedNOverStreams;
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
public final class NMethodTest {
	private static final Grid GRID_1;
	private static final Grid GRID_2;
	private static final Grid GRID_3;
	private static final Grid GRID_4;
	private static final Grid GRID_5;
	private static final Grid GRID_6;

	static {
		int[][] grid1LockedNumbers = { { 4, 0, 0, 2, 7, 0, 6, 0, 0 }, { 7, 9, 8, 1, 5, 6, 2, 3, 4 },
				{ 0, 2, 0, 8, 4, 0, 0, 0, 7 }, { 2, 3, 7, 4, 6, 8, 9, 5, 1 }, { 8, 4, 9, 5, 3, 1, 7, 2, 6 },
				{ 5, 6, 1, 7, 9, 2, 8, 4, 3 }, { 0, 8, 2, 0, 1, 5, 4, 7, 9 }, { 0, 7, 0, 0, 2, 4, 3, 0, 0 },
				{ 0, 0, 4, 0, 8, 7, 0, 0, 2 } };

		GRID_1 = newSudokuGrid(grid1LockedNumbers);

		int[][] grid2LockedNumbers = { { 8, 9, 1, 0, 0, 5, 7, 6, 0 }, { 5, 3, 7, 6, 9, 0, 2, 0, 8 },
				{ 4, 6, 2, 0, 0, 0, 0, 0, 5 }, { 2, 4, 3, 5, 1, 0, 8, 0, 6 }, { 1, 5, 6, 0, 0, 0, 4, 0, 0 },
				{ 9, 7, 8, 0, 4, 6, 5, 0, 0 }, { 3, 1, 9, 0, 5, 0, 6, 8, 7 }, { 6, 8, 4, 0, 0, 0, 0, 5, 2 },
				{ 7, 2, 5, 8, 6, 0, 3, 0, 0 } };

		GRID_2 = newSudokuGrid(grid2LockedNumbers);

		int[][] grid3LockedNumbers = { { 6, 2, 4, 9, 0, 0, 0, 0, 0 }, { 7, 3, 9, 1, 0, 0, 0, 0, 8 },
				{ 8, 1, 5, 0, 0, 4, 0, 0, 0 }, { 4, 0, 0, 0, 0, 9, 3, 7, 0 }, { 3, 0, 0, 0, 4, 0, 0, 0, 6 },
				{ 5, 9, 1, 0, 0, 3, 0, 0, 2 }, { 9, 0, 0, 4, 0, 0, 2, 0, 0 }, { 1, 0, 0, 2, 9, 6, 0, 0, 4 },
				{ 2, 4, 8, 3, 5, 7, 1, 6, 9 } };

		GRID_3 = newSudokuGrid(grid3LockedNumbers);

		int[][] grid4LockedNumbers = { { 0, 6, 0, 3, 9, 0, 1, 0, 0 }, { 0, 0, 3, 1, 5, 0, 0, 9, 0 },
				{ 1, 9, 0, 4, 2, 6, 3, 0, 0 }, { 8, 3, 0, 5, 7, 9, 4, 1, 0 }, { 9, 0, 0, 0, 6, 1, 0, 0, 0 },
				{ 0, 5, 1, 0, 4, 3, 0, 0, 9 }, { 4, 1, 9, 6, 3, 5, 8, 2, 7 }, { 0, 2, 0, 9, 8, 4, 5, 0, 1 },
				{ 0, 8, 0, 7, 1, 2, 9, 4, 0 } };

		GRID_4 = newSudokuGrid(grid4LockedNumbers);

		int[][] grid5LockedNumbers = { { 0, 4, 9, 1, 3, 2, 0, 0, 0 }, { 0, 8, 1, 4, 7, 9, 0, 0, 0 },
				{ 3, 2, 7, 6, 8, 5, 9, 1, 4 }, { 0, 9, 6, 0, 5, 1, 8, 0, 0 }, { 0, 7, 5, 0, 2, 8, 0, 0, 0 },
				{ 0, 3, 8, 0, 4, 6, 0, 0, 5 }, { 8, 5, 3, 2, 6, 7, 0, 0, 0 }, { 7, 1, 2, 8, 9, 4, 5, 6, 3 },
				{ 9, 6, 4, 5, 1, 3, 0, 0, 0 } };

		GRID_5 = newSudokuGrid(grid5LockedNumbers);

		int[][] grid6LockedNumbers = { { 2, 8, 0, 0, 0, 0, 4, 7, 3 }, { 5, 3, 4, 8, 2, 7, 1, 9, 6 },
				{ 0, 7, 1, 0, 3, 4, 0, 8, 0 }, { 3, 0, 0, 5, 0, 0, 0, 4, 0 }, { 0, 0, 0, 3, 4, 0, 0, 6, 0 },
				{ 4, 6, 0, 7, 9, 0, 3, 1, 0 }, { 0, 9, 0, 2, 0, 3, 6, 5, 4 }, { 0, 0, 3, 0, 0, 9, 8, 2, 1 },
				{ 0, 0, 0, 0, 8, 0, 9, 3, 7 } };

		GRID_6 = newSudokuGrid(grid6LockedNumbers);
	}

	@Test
	public void nakedNInRowTest() {
		boolean changed = runNakedNOverRows(GRID_1, 2);
		Map<Cell, Set<Integer>> cellsAndExpectedPossibles = new HashMap<>();
		cellsAndExpectedPossibles.put(GRID_1.getCell(8, 0), new TreeSet<>(asList(3, 6, 9)));
		cellsAndExpectedPossibles.put(GRID_1.getCell(8, 7), new TreeSet<>(asList(6)));
		testCellsAndExpectedPossibles(changed, cellsAndExpectedPossibles);
	}

	@Test
	public void nakedNInColumnTest() {
		boolean changed = runNakedNOverColumns(GRID_2, 3);
		Map<Cell, Set<Integer>> cellsAndExpectedPossibles = new HashMap<>();
		cellsAndExpectedPossibles.put(GRID_2.getCell(2, 3), new TreeSet<>(asList(1, 7)));
		cellsAndExpectedPossibles.put(GRID_2.getCell(4, 3), new TreeSet<>(asList(7, 9)));
		cellsAndExpectedPossibles.put(GRID_2.getCell(7, 3), new TreeSet<>(asList(1, 7, 9)));
		testCellsAndExpectedPossibles(changed, cellsAndExpectedPossibles);
	}

	@Test
	public void nakedNInStreamTest() {
		boolean changed = runNakedNOverStreams(GRID_3, 4);
		Map<Cell, Set<Integer>> cellsAndExpectedPossibles = new HashMap<>();
		cellsAndExpectedPossibles.put(GRID_3.getCell(1, 6), new TreeSet<>(asList(4, 6)));
		cellsAndExpectedPossibles.put(GRID_3.getCell(1, 7), new TreeSet<>(asList(2, 4)));
		cellsAndExpectedPossibles.put(GRID_3.getCell(2, 6), new TreeSet<>(asList(6, 9)));
		cellsAndExpectedPossibles.put(GRID_3.getCell(2, 7), new TreeSet<>(asList(2, 9)));
		testCellsAndExpectedPossibles(changed, cellsAndExpectedPossibles);
	}

	@Test
	public void hiddenNInRowTest() {
		boolean changed = runHiddenNOverRows(GRID_4, 2);
		Map<Cell, Set<Integer>> cellsAndExpectedPossibles = new HashMap<>();
		Set<Integer> expectedPossibles = new TreeSet<>(asList(3, 5));
		cellsAndExpectedPossibles.put(GRID_4.getCell(4, 7), expectedPossibles);
		cellsAndExpectedPossibles.put(GRID_4.getCell(4, 8), expectedPossibles);
		testCellsAndExpectedPossibles(changed, cellsAndExpectedPossibles);
	}

	@Test
	public void hiddenNInColumnTest() {
		boolean changed = runHiddenNOverColumns(GRID_5, 2);
		Map<Cell, Set<Integer>> cellsAndExpectedPossibles = new HashMap<>();
		Set<Integer> expectedPossibles = new TreeSet<>(asList(1, 9));
		cellsAndExpectedPossibles.put(GRID_5.getCell(4, 8), expectedPossibles);
		cellsAndExpectedPossibles.put(GRID_5.getCell(6, 8), expectedPossibles);
		testCellsAndExpectedPossibles(changed, cellsAndExpectedPossibles);
	}

	@Test
	public void hiddenNInStreamTest() {
		boolean changed = runHiddenNOverStreams(GRID_6, 3);
		Map<Cell, Set<Integer>> cellsAndExpectedPossibles = new HashMap<>();
		cellsAndExpectedPossibles.put(GRID_6.getCell(7, 1), new TreeSet<>(asList(4, 5)));
		cellsAndExpectedPossibles.put(GRID_6.getCell(8, 1), new TreeSet<>(asList(2, 4, 5)));
		cellsAndExpectedPossibles.put(GRID_6.getCell(8, 2), new TreeSet<>(asList(2, 5)));
		testCellsAndExpectedPossibles(changed, cellsAndExpectedPossibles);
	}
}