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
public final class NakedNMethodTest {
	private static final Grid GRID_1;
	private static final Grid GRID_2;
	private static final Grid GRID_3;

	static {
		int[][] grid1LockedNumbers = { { 4, 0, 0, 2, 7, 0, 6, 0, 0 }, { 7, 9, 8, 1, 5, 6, 2, 3, 4 },
				{ 0, 2, 0, 8, 4, 0, 0, 0, 7 }, { 2, 3, 7, 4, 6, 8, 9, 5, 1 }, { 8, 4, 9, 5, 3, 1, 7, 2, 6 },
				{ 5, 6, 1, 7, 9, 2, 8, 4, 3 }, { 0, 8, 2, 0, 1, 5, 4, 7, 9 }, { 0, 7, 0, 0, 2, 4, 3, 0, 0 },
				{ 0, 0, 4, 0, 8, 7, 0, 0, 2 }, };

		GRID_1 = new Grid(SUDOKU_SIZE);
		GRID_1.initStreams(SUDOKU_STREAMS);
		GRID_1.initLockedNumbers(grid1LockedNumbers);
		GRID_1.updatePossibleNumbers();

		int[][] grid2LockedNumbers = { { 8, 9, 1, 0, 0, 5, 7, 6, 0 }, { 5, 3, 7, 6, 9, 0, 2, 0, 8 },
				{ 4, 6, 2, 0, 0, 0, 0, 0, 5 }, { 2, 4, 3, 5, 1, 0, 8, 0, 6 }, { 1, 5, 6, 0, 0, 0, 4, 0, 0 },
				{ 9, 7, 8, 0, 4, 6, 5, 0, 0 }, { 3, 1, 9, 0, 5, 0, 6, 8, 7 }, { 6, 8, 4, 0, 0, 0, 0, 5, 2 },
				{ 7, 2, 5, 8, 6, 0, 3, 0, 0 }, };

		GRID_2 = new Grid(SUDOKU_SIZE);
		GRID_2.initStreams(SUDOKU_STREAMS);
		GRID_2.initLockedNumbers(grid2LockedNumbers);
		GRID_2.updatePossibleNumbers();

		int[][] grid3LockedNumbers = { { 6, 2, 4, 9, 0, 0, 0, 0, 0 }, { 7, 3, 9, 1, 0, 0, 0, 0, 8 },
				{ 8, 1, 5, 0, 0, 4, 0, 0, 0 }, { 4, 0, 0, 0, 0, 9, 3, 7, 0 }, { 3, 0, 0, 0, 4, 0, 0, 0, 6 },
				{ 5, 9, 1, 0, 0, 3, 0, 0, 2 }, { 9, 0, 0, 4, 0, 0, 2, 0, 0 }, { 1, 0, 0, 2, 9, 6, 0, 0, 4 },
				{ 2, 4, 8, 3, 5, 7, 1, 6, 9 } };

		GRID_3 = new Grid(SUDOKU_SIZE);
		GRID_3.initStreams(SUDOKU_STREAMS);
		GRID_3.initLockedNumbers(grid3LockedNumbers);
		GRID_3.updatePossibleNumbers();
	}

	@Test
	public void nakedNInRowTest() {
		boolean changed = NakedNMethod.runOverRows(GRID_1, 2);
		assertTrue(changed);
		Cell changedCell1 = GRID_1.getCell(8, 0);
		Cell changedCell2 = GRID_1.getCell(8, 7);
		Set<Integer> expectedPossibles1 = new TreeSet<>(Arrays.asList(3, 6, 9));
		Set<Integer> expectedPossibles2 = new TreeSet<>(Arrays.asList(6));
		assertEquals(expectedPossibles1, changedCell1.getPossibleNumbers());
		assertEquals(expectedPossibles2, changedCell2.getPossibleNumbers());
	}

	@Test
	public void nakedNInColumnTest() {
		boolean changed = NakedNMethod.runOverColumns(GRID_2, 3);
		assertTrue(changed);
		Cell changedCell1 = GRID_2.getCell(2, 3);
		Cell changedCell2 = GRID_2.getCell(4, 3);
		Cell changedCell3 = GRID_2.getCell(7, 3);
		Set<Integer> expectedPossibles1 = new TreeSet<>(Arrays.asList(1, 7));
		Set<Integer> expectedPossibles2 = new TreeSet<>(Arrays.asList(7, 9));
		Set<Integer> expectedPossibles3 = new TreeSet<>(Arrays.asList(1, 7, 9));
		assertEquals(expectedPossibles1, changedCell1.getPossibleNumbers());
		assertEquals(expectedPossibles2, changedCell2.getPossibleNumbers());
		assertEquals(expectedPossibles3, changedCell3.getPossibleNumbers());
	}

	@Test
	public void nakedNInStreamTest() {
		boolean changed = NakedNMethod.runOverStreams(GRID_3, 4);
		assertTrue(changed);
		Cell changedCell1 = GRID_3.getCell(1, 6);
		Cell changedCell2 = GRID_3.getCell(1, 7);
		Cell changedCell3 = GRID_3.getCell(2, 6);
		Cell changedCell4 = GRID_3.getCell(2, 7);
		Set<Integer> expectedPossibles1 = new TreeSet<>(Arrays.asList(4, 6));
		Set<Integer> expectedPossibles2 = new TreeSet<>(Arrays.asList(2, 4));
		Set<Integer> expectedPossibles3 = new TreeSet<>(Arrays.asList(6, 9));
		Set<Integer> expectedPossibles4 = new TreeSet<>(Arrays.asList(2, 9));
		assertEquals(expectedPossibles1, changedCell1.getPossibleNumbers());
		assertEquals(expectedPossibles2, changedCell2.getPossibleNumbers());
		assertEquals(expectedPossibles3, changedCell3.getPossibleNumbers());
		assertEquals(expectedPossibles4, changedCell4.getPossibleNumbers());
	}

}