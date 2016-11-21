package lowe.mike.strimko.solver;

import static lowe.mike.strimko.model.Constants.SUDOKU_SIZE;
import static lowe.mike.strimko.model.Constants.SUDOKU_STREAMS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;

/**
 * @author Mike Lowe
 */
public final class HiddenNMethodTest {
	private static final Grid GRID_1;
	private static final Grid GRID_2;
	private static final Grid GRID_3;

	static {
		int[][] grid1LockedNumbers = { { 0, 6, 0, 3, 9, 0, 1, 0, 0 }, { 0, 0, 3, 1, 5, 0, 0, 9, 0 },
				{ 1, 9, 0, 4, 2, 6, 3, 0, 0 }, { 8, 3, 0, 5, 7, 9, 4, 1, 0 }, { 9, 0, 0, 0, 6, 1, 0, 0, 0 },
				{ 0, 5, 1, 0, 4, 3, 0, 0, 9 }, { 4, 1, 9, 6, 3, 5, 8, 2, 7 }, { 0, 2, 0, 9, 8, 4, 5, 0, 1 },
				{ 0, 8, 0, 7, 1, 2, 9, 4, 0 }, };

		GRID_1 = new Grid(SUDOKU_SIZE);
		GRID_1.initStreams(SUDOKU_STREAMS);
		GRID_1.initLockedNumbers(grid1LockedNumbers);
		GRID_1.updatePossibleNumbers();

		int[][] grid2LockedNumbers = { { 0, 4, 9, 1, 3, 2, 0, 0, 0 }, { 0, 8, 1, 4, 7, 9, 0, 0, 0 },
				{ 3, 2, 7, 6, 8, 5, 9, 1, 4 }, { 0, 9, 6, 0, 5, 1, 8, 0, 0 }, { 0, 7, 5, 0, 2, 8, 0, 0, 0 },
				{ 0, 3, 8, 0, 4, 6, 0, 0, 5 }, { 8, 5, 3, 2, 6, 7, 0, 0, 0 }, { 7, 1, 2, 8, 9, 4, 5, 6, 3 },
				{ 9, 6, 4, 5, 1, 3, 0, 0, 0 }, };

		GRID_2 = new Grid(SUDOKU_SIZE);
		GRID_2.initStreams(SUDOKU_STREAMS);
		GRID_2.initLockedNumbers(grid2LockedNumbers);
		GRID_2.updatePossibleNumbers();

		int[][] grid3LockedNumbers = { { 2, 8, 0, 0, 0, 0, 4, 7, 3 }, { 5, 3, 4, 8, 2, 7, 1, 9, 6 },
				{ 0, 7, 1, 0, 3, 4, 0, 8, 0 }, { 3, 0, 0, 5, 0, 0, 0, 4, 0 }, { 0, 0, 0, 3, 4, 0, 0, 6, 0 },
				{ 4, 6, 0, 7, 9, 0, 3, 1, 0 }, { 0, 9, 0, 2, 0, 3, 6, 5, 4 }, { 0, 0, 3, 0, 0, 9, 8, 2, 1 },
				{ 0, 0, 0, 0, 8, 0, 9, 3, 7 } };

		GRID_3 = new Grid(SUDOKU_SIZE);
		GRID_3.initStreams(SUDOKU_STREAMS);
		GRID_3.initLockedNumbers(grid3LockedNumbers);
		GRID_3.updatePossibleNumbers();
	}

	@Test
	public void hiddenNInRowTest() {
		boolean changed = HiddenNMethod.runOverRows(GRID_1, 2);
		assertTrue(changed);
		Cell changedCell1 = GRID_1.getCell(4, 7);
		Cell changedCell2 = GRID_1.getCell(4, 8);
		Set<Integer> expectedPossibles = new TreeSet<>(Arrays.asList(3, 5));
		assertEquals(expectedPossibles, changedCell1.getPossibleNumbers());
		assertEquals(expectedPossibles, changedCell2.getPossibleNumbers());
	}

	@Test
	public void hiddenNInColumnTest() {
		boolean changed = HiddenNMethod.runOverColumns(GRID_2, 2);
		assertTrue(changed);
		Cell changedCell1 = GRID_2.getCell(4, 8);
		Cell changedCell2 = GRID_2.getCell(6, 8);
		Set<Integer> expectedPossibles = new TreeSet<>(Arrays.asList(1, 9));
		assertEquals(expectedPossibles, changedCell1.getPossibleNumbers());
		assertEquals(expectedPossibles, changedCell2.getPossibleNumbers());
	}

	@Test
	public void hiddenNInStreamTest() {
		boolean changed = HiddenNMethod.runOverStreams(GRID_3, 3);
		assertTrue(changed);
		Cell changedCell1 = GRID_3.getCell(7, 1);
		Cell changedCell2 = GRID_3.getCell(8, 1);
		Cell changedCell3 = GRID_3.getCell(8, 2);
		Set<Integer> expectedPossibles1 = new TreeSet<>(Arrays.asList(4, 5));
		Set<Integer> expectedPossibles2 = new TreeSet<>(Arrays.asList(2, 4, 5));
		Set<Integer> expectedPossibles3 = new TreeSet<>(Arrays.asList(2, 5));
		assertEquals(expectedPossibles1, changedCell1.getPossibleNumbers());
		assertEquals(expectedPossibles2, changedCell2.getPossibleNumbers());
		assertEquals(expectedPossibles3, changedCell3.getPossibleNumbers());
	}
}