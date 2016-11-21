package lowe.mike.strimko.solver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

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

		GRID_1 = new Grid(grid1Size);
		GRID_1.initStreams(grid1Streams);
		GRID_1.initLockedNumbers(grid1LockedNumbers);
		GRID_1.updatePossibleNumbers();

		int grid2Size = 3;
		int[][] grid2Streams = { { 1, 1, 1 }, { 2, 2, 2 }, { 3, 3, 3 } };
		int[][] grid2LockedNumbers = { { 0, 1, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };

		GRID_2 = new Grid(grid2Size);
		GRID_2.initStreams(grid2Streams);
		GRID_2.initLockedNumbers(grid2LockedNumbers);
		GRID_2.updatePossibleNumbers();

		int grid3Size = 4;
		int[][] grid3Streams = { { 1, 2, 2, 3 }, { 2, 1, 3, 2 }, { 4, 3, 1, 4 }, { 3, 4, 4, 1 } };
		int[][] grid3LockedNumbers = { { 2, 0, 2, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

		GRID_3 = new Grid(grid3Size);
		GRID_3.initStreams(grid3Streams);
		GRID_3.initLockedNumbers(grid3LockedNumbers);
		GRID_3.updatePossibleNumbers();

		int grid4Size = 6;
		int[][] grid4Streams = { { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 },
				{ 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 } };
		int[][] grid4LockedNumbers = { { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 },
				{ 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 }, };

		GRID_4 = new Grid(grid4Size);
		GRID_4.initStreams(grid4Streams);
		GRID_4.initLockedNumbers(grid4LockedNumbers);
		GRID_4.updatePossibleNumbers();
	}

	@Test
	public void solvableTest() {
		Set<Position> hints = new LinkedHashSet<>();
		Result result = BruteForceMethod.run(GRID_1, hints);
		assertTrue(result.isSolvable());
	}

	@Test
	public void multipleSolutionsTest() {
		Set<Position> hints = new LinkedHashSet<>();
		Result result = BruteForceMethod.run(GRID_2, hints);
		assertTrue(result.hasMultipleSolutions());
	}

	@Test
	public void unsolvableTest() {
		Set<Position> hints = new LinkedHashSet<>();
		Result result = BruteForceMethod.run(GRID_3, hints);
		assertFalse(result.isSolvable());
	}

	@Test
	public void filledTest() {
		Set<Position> hints = new LinkedHashSet<>();
		Result result = BruteForceMethod.run(GRID_4, hints);
		assertFalse(result.isSolvable());
	}
}