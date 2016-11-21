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
public final class GroupInteractionsMethodTest {
	private static final Grid GRID_1;
	private static final Grid GRID_2;
	private static final Grid GRID_3;
	private static final Grid GRID_4;

	static {

		int[][] grid1LockedNumbers = { { 0, 3, 0, 7, 8, 1, 0, 5, 0 }, { 7, 0, 0, 2, 3, 9, 6, 8, 0 },
				{ 0, 0, 0, 6, 5, 4, 0, 7, 3 }, { 5, 0, 0, 0, 0, 7, 3, 9, 8 }, { 3, 1, 7, 9, 2, 8, 5, 4, 6 },
				{ 4, 8, 9, 3, 6, 5, 7, 1, 2 }, { 0, 0, 0, 0, 0, 2, 0, 3, 0 }, { 1, 0, 0, 0, 0, 3, 0, 6, 0 },
				{ 0, 0, 3, 0, 0, 6, 4, 2, 7 }, };

		GRID_1 = new Grid(SUDOKU_SIZE);
		GRID_1.initStreams(SUDOKU_STREAMS);
		GRID_1.initLockedNumbers(grid1LockedNumbers);
		GRID_1.updatePossibleNumbers();

		int[][] grid2LockedNumbers = { { 9, 3, 0, 0, 5, 0, 0, 0, 0 }, { 2, 0, 0, 6, 3, 0, 0, 9, 5 },
				{ 8, 5, 6, 0, 0, 2, 0, 0, 0 }, { 0, 0, 3, 1, 8, 0, 5, 7, 0 }, { 0, 0, 5, 0, 2, 0, 9, 8, 0 },
				{ 0, 8, 0, 0, 0, 5, 0, 0, 0 }, { 0, 0, 0, 8, 0, 0, 1, 5, 9 }, { 5, 0, 8, 2, 1, 0, 0, 0, 4 },
				{ 0, 0, 0, 5, 6, 0, 0, 0, 8 } };

		GRID_2 = new Grid(SUDOKU_SIZE);
		GRID_2.initStreams(SUDOKU_STREAMS);
		GRID_2.initLockedNumbers(grid2LockedNumbers);
		GRID_2.updatePossibleNumbers();

		int[][] grid3LockedNumbers = { { 0, 1, 6, 0, 0, 7, 8, 0, 3 }, { 0, 9, 0, 8, 0, 0, 0, 0, 0 },
				{ 8, 7, 0, 0, 0, 1, 0, 6, 0 }, { 0, 4, 8, 0, 0, 0, 3, 0, 0 }, { 6, 5, 0, 0, 0, 9, 0, 8, 2 },
				{ 2, 3, 9, 0, 0, 0, 6, 5, 0 }, { 0, 6, 0, 9, 0, 0, 0, 2, 0 }, { 0, 8, 0, 0, 0, 2, 9, 3, 6 },
				{ 9, 2, 4, 6, 0, 0, 5, 1, 0 }, };

		GRID_3 = new Grid(SUDOKU_SIZE);
		GRID_3.initStreams(SUDOKU_STREAMS);
		GRID_3.initLockedNumbers(grid3LockedNumbers);
		GRID_3.updatePossibleNumbers();

		GRID_4 = new Grid(SUDOKU_SIZE);
		GRID_4.initStreams(SUDOKU_STREAMS);
		GRID_4.initLockedNumbers(grid3LockedNumbers);
		GRID_4.updatePossibleNumbers();
	}

	@Test
	public void pointingNInRowTest() {
		boolean changed = GroupInteractionsMethod.runPointingNOverRows(GRID_1, 2);
		assertTrue(changed);
		Cell changedCell1 = GRID_1.getCell(6, 3);
		Cell changedCell2 = GRID_1.getCell(6, 4);
		Set<Integer> expectedPossibles1 = new TreeSet<>(Arrays.asList(4, 5, 8));
		Set<Integer> expectedPossibles2 = new TreeSet<>(Arrays.asList(4, 7, 9));
		assertEquals(expectedPossibles1, changedCell1.getPossibleNumbers());
		assertEquals(expectedPossibles2, changedCell2.getPossibleNumbers());
	}

	@Test
	public void pointingNInColumnTest() {
		boolean changed = GroupInteractionsMethod.runPointingNOverColumns(GRID_2, 3);
		assertTrue(changed);
		Cell changedCell = GRID_2.getCell(4, 5);
		Set<Integer> expectedPossibles = new TreeSet<>(Arrays.asList(4, 6, 7));
		assertEquals(expectedPossibles, changedCell.getPossibleNumbers());
	}

	@Test
	public void streamLineReductionInRowTest() {
		boolean changed = GroupInteractionsMethod.runStreamLineReductionOverRows(GRID_3, 2);
		assertTrue(changed);
		Cell changedCell1 = GRID_3.getCell(1, 4);
		Cell changedCell2 = GRID_3.getCell(2, 3);
		Cell changedCell3 = GRID_3.getCell(2, 4);
		Set<Integer> expectedPossibles1 = new TreeSet<>(Arrays.asList(3, 4, 5, 6));
		Set<Integer> expectedPossibles2 = new TreeSet<>(Arrays.asList(3, 4, 5));
		Set<Integer> expectedPossibles3 = new TreeSet<>(Arrays.asList(3, 4, 5, 9));
		assertEquals(expectedPossibles1, changedCell1.getPossibleNumbers());
		assertEquals(expectedPossibles2, changedCell2.getPossibleNumbers());
		assertEquals(expectedPossibles3, changedCell3.getPossibleNumbers());
	}

	@Test
	public void streamLineReductionInColumnTest() {
		boolean changed = GroupInteractionsMethod.runStreamLineReductionOverColumns(GRID_4, 2);
		assertTrue(changed);
		Cell changedCell1 = GRID_4.getCell(1, 6);
		Cell changedCell2 = GRID_4.getCell(1, 8);
		Cell changedCell3 = GRID_4.getCell(2, 6);
		Cell changedCell4 = GRID_4.getCell(2, 8);
		Set<Integer> expectedPossibles1 = new TreeSet<>(Arrays.asList(1, 2, 7));
		Set<Integer> expectedPossibles2 = new TreeSet<>(Arrays.asList(1, 5, 7));
		Set<Integer> expectedPossibles3 = new TreeSet<>(Arrays.asList(2));
		Set<Integer> expectedPossibles4 = new TreeSet<>(Arrays.asList(5, 9));
		assertEquals(expectedPossibles1, changedCell1.getPossibleNumbers());
		assertEquals(expectedPossibles2, changedCell2.getPossibleNumbers());
		assertEquals(expectedPossibles3, changedCell3.getPossibleNumbers());
		assertEquals(expectedPossibles4, changedCell4.getPossibleNumbers());
	}
}