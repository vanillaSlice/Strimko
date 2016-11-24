package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.solver.BruteForceMethod.run;
import static lowe.mike.strimko.model.solver.TestHelper.newStrimkoGrid;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

import lowe.mike.strimko.model.Grid;

/**
 * @author Mike Lowe
 */
public final class BruteForceMethodTest {
	private static final Grid GRID_1;
	private static final Grid GRID_2;
	private static final Grid GRID_3;
	private static final Grid GRID_4;

	static {
		int grid1Size = 5;
		int[][] grid1Streams = { { 1, 1, 2, 2, 2 }, { 2, 2, 1, 3, 3 }, { 1, 1, 3, 4, 3 }, { 5, 4, 4, 5, 3 },
				{ 5, 5, 5, 4, 4 } };
		int[][] grid1LockedNumbers = { { 0, 0, 3, 0, 0 }, { 0, 4, 0, 1, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 0, 0, 5, 0, 0 } };

		GRID_1 = newStrimkoGrid(grid1Size, grid1Streams, grid1LockedNumbers);

		int grid2Size = 3;
		int[][] grid2Streams = { { 1, 1, 1 }, { 2, 2, 2 }, { 3, 3, 3 } };
		int[][] grid2LockedNumbers = { { 0, 1, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };

		GRID_2 = newStrimkoGrid(grid2Size, grid2Streams, grid2LockedNumbers);

		int grid3Size = 4;
		int[][] grid3Streams = { { 1, 2, 2, 3 }, { 2, 1, 3, 2 }, { 4, 3, 1, 4 }, { 3, 4, 4, 1 } };
		int[][] grid3LockedNumbers = { { 2, 0, 2, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

		GRID_3 = newStrimkoGrid(grid3Size, grid3Streams, grid3LockedNumbers);

		int grid4Size = 6;
		int[][] grid4Streams = { { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 },
				{ 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 } };
		int[][] grid4LockedNumbers = { { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 },
				{ 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 } };

		GRID_4 = newStrimkoGrid(grid4Size, grid4Streams, grid4LockedNumbers);
	}

	@Test
	public void solvableTest() {
		Result result = run(GRID_1, new HashSet<>());
		assertTrue(result.isSolvable());
	}

	@Test
	public void multipleSolutionsTest() {
		Result result = run(GRID_2, new HashSet<>());
		assertTrue(result.hasMultipleSolutions());
	}

	@Test
	public void unsolvableTest() {
		Result result = run(GRID_3, new HashSet<>());
		assertFalse(result.isSolvable());
	}

	@Test
	public void filledTest() {
		Result result = run(GRID_4, new HashSet<>());
		assertFalse(result.isSolvable());
	}
}