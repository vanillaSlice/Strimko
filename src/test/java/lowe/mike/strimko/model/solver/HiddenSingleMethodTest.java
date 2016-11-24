package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.solver.HiddenSingleMethod.runOverColumns;
import static lowe.mike.strimko.model.solver.HiddenSingleMethod.runOverRows;
import static lowe.mike.strimko.model.solver.HiddenSingleMethod.runOverStreams;
import static lowe.mike.strimko.model.solver.TestHelper.newStrimkoGrid;
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

		GRID_1 = newStrimkoGrid(grid1Size, grid1Streams, grid1LockedNumbers);

		int grid2Size = 5;
		int[][] grid2Streams = { { 1, 1, 1, 1, 1 }, { 2, 2, 3, 3, 4 }, { 2, 3, 3, 5, 4 }, { 2, 3, 5, 5, 4 },
				{ 2, 5, 5, 4, 4 } };
		int[][] grid2LockedNumbers = { { 0, 0, 0, 0, 0 }, { 0, 3, 0, 0, 4 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 2, 0, 0, 5, 0 } };

		GRID_2 = newStrimkoGrid(grid2Size, grid2Streams, grid2LockedNumbers);

		int grid3Size = 5;
		int[][] grid3Streams = { { 1, 2, 3, 4, 4 }, { 1, 3, 2, 2, 4 }, { 1, 3, 3, 2, 4 }, { 1, 1, 2, 3, 4 },
				{ 5, 5, 5, 5, 5 } };
		int[][] grid3LockedNumbers = { { 1, 0, 0, 0, 0 }, { 0, 2, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 4, 0 },
				{ 0, 0, 0, 0, 5 } };

		GRID_3 = newStrimkoGrid(grid3Size, grid3Streams, grid3LockedNumbers);
	}

	@Test
	public void hiddenSingleInRowTest() {
		Set<Position> hints = new LinkedHashSet<>();
		boolean changed = runOverRows(GRID_1, hints);
		Position changedPosition = new Position(0, 3);
		hiddenSingleTest(GRID_1, hints, changed, 3, changedPosition);
	}

	@Test
	public void hiddenSingleInColumnTest() {
		Set<Position> hints = new LinkedHashSet<>();
		boolean changed = runOverColumns(GRID_2, hints);
		Position changedPosition = new Position(0, 0);
		hiddenSingleTest(GRID_2, hints, changed, 3, changedPosition);
	}

	@Test
	public void hiddenSingleInStreamTest() {
		Set<Position> hints = new LinkedHashSet<>();
		boolean changed = runOverStreams(GRID_3, hints);
		Position changedPosition = new Position(0, 3);
		hiddenSingleTest(GRID_3, hints, changed, 5, changedPosition);
	}

	private void hiddenSingleTest(Grid grid, Set<Position> hints, boolean changed, int number,
			Position changedPosition) {
		assertTrue(changed);
		assertEquals(number, grid.getCell(changedPosition).getNumber());
		assertTrue(hints.contains(changedPosition));
	}
}