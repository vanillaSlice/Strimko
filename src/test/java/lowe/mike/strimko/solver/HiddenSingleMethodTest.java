package lowe.mike.strimko.solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * @author Mike Lowe
 */
public final class HiddenSingleMethodTest {
	private static final Grid GRID_1;
	private static final Grid GRID_2;
	private static final Grid GRID_3;

	static {
		int grid1Size = 4;
		int[][] grid1Streams = { { 1, 2, 1, 2 }, { 3, 1, 2, 4 }, { 1, 3, 4, 2 }, { 3, 4, 3, 4 } };
		int[][] grid1LockedNumbers = { { 0, 0, 0, 0 }, { 0, 3, 0, 1 }, { 0, 4, 0, 0 }, { 0, 0, 0, 0 } };

		GRID_1 = new Grid(grid1Size);
		GRID_1.initStreams(grid1Streams);
		GRID_1.initLockedNumbers(grid1LockedNumbers);
		GRID_1.updatePossibleNumbers();

		int grid2Size = 5;
		int[][] grid2Streams = { { 1, 1, 1, 1, 1 }, { 2, 2, 3, 3, 4 }, { 2, 3, 3, 5, 4 }, { 2, 3, 5, 5, 4 },
				{ 2, 5, 5, 4, 4 } };
		int[][] grid2LockedNumbers = { { 0, 0, 0, 0, 0 }, { 0, 3, 0, 0, 4 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 2, 0, 0, 5, 0 } };

		GRID_2 = new Grid(grid2Size);
		GRID_2.initStreams(grid2Streams);
		GRID_2.initLockedNumbers(grid2LockedNumbers);
		GRID_2.updatePossibleNumbers();

		int grid3Size = 5;
		int[][] grid3Streams = { { 1, 2, 3, 4, 4 }, { 1, 3, 2, 2, 4 }, { 1, 3, 3, 2, 4 }, { 1, 1, 2, 3, 4 },
				{ 5, 5, 5, 5, 5 } };
		int[][] grid3LockedNumbers = { { 1, 0, 0, 0, 0 }, { 0, 2, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 4, 0 },
				{ 0, 0, 0, 0, 5 } };

		GRID_3 = new Grid(grid3Size);
		GRID_3.initStreams(grid3Streams);
		GRID_3.initLockedNumbers(grid3LockedNumbers);
		GRID_3.updatePossibleNumbers();
	}

	@Test
	public void hiddenSingleInRowTest() {
		Set<Position> hints = new LinkedHashSet<>();
		boolean changed = HiddenSingleMethod.runOverRows(GRID_1, hints);
		assertTrue(changed);
		Position changedPosition = new Position(0, 3);
		assertEquals(3, GRID_1.getCell(changedPosition).getNumber());
		assertTrue(hints.contains(changedPosition));
	}

	@Test
	public void hiddenSingleInColumnTest() {
		Set<Position> hints = new LinkedHashSet<>();
		boolean changed = HiddenSingleMethod.runOverColumns(GRID_2, hints);
		assertTrue(changed);
		Position changedPosition = new Position(0, 0);
		assertEquals(3, GRID_2.getCell(changedPosition).getNumber());
		assertTrue(hints.contains(changedPosition));
	}

	@Test
	public void hiddenSingleInStreamTest() {
		Set<Position> hints = new LinkedHashSet<>();
		boolean changed = HiddenSingleMethod.runOverStreams(GRID_3, hints);
		assertTrue(changed);
		Position changedPosition = new Position(0, 3);
		assertEquals(5, GRID_3.getCell(changedPosition).getNumber());
		assertTrue(hints.contains(changedPosition));
	}
}