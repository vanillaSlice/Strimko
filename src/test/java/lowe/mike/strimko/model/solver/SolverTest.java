package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.Difficulty.EASY;
import static lowe.mike.strimko.model.Difficulty.HARD;
import static lowe.mike.strimko.model.Difficulty.MEDIUM;
import static lowe.mike.strimko.model.solver.Solver.solve;
import static lowe.mike.strimko.model.solver.TestHelper.newStrimkoGrid;
import static lowe.mike.strimko.model.solver.TestHelper.newSudokuGrid;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import lowe.mike.strimko.model.Difficulty;
import lowe.mike.strimko.model.Grid;

/**
 * @author Mike Lowe
 */
public final class SolverTest {
	private static final Grid GRID_1;
	private static final Grid GRID_2;
	private static final Grid GRID_3;
	private static final Grid GRID_4;
	private static final Grid GRID_5;
	private static final Grid GRID_6;

	static {
		int grid1Size = 4;
		int[][] grid1Streams = { { 1, 2, 2, 3 }, { 2, 1, 3, 2 }, { 4, 3, 1, 4 }, { 3, 4, 4, 1 } };
		int[][] grid1LockedNumbers = { { 0, 0, 0, 2 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 4, 0, 0, 1 } };

		GRID_1 = newStrimkoGrid(grid1Size, grid1Streams, grid1LockedNumbers);

		int[][] grid2LockedNumbers = { { 8, 0, 0, 0, 0, 0, 4, 6, 0 }, { 0, 9, 7, 6, 0, 1, 2, 0, 8 },
				{ 0, 4, 0, 8, 0, 5, 0, 1, 0 }, { 0, 0, 0, 2, 7, 9, 0, 0, 4 }, { 0, 5, 0, 0, 0, 0, 0, 2, 0 },
				{ 1, 0, 0, 4, 5, 3, 0, 0, 0 }, { 0, 7, 0, 3, 0, 6, 0, 9, 0 }, { 3, 0, 2, 9, 0, 7, 5, 4, 0 },
				{ 0, 6, 1, 0, 0, 0, 0, 0, 2 } };

		GRID_2 = newSudokuGrid(grid2LockedNumbers);

		int grid3Size = 5;
		int[][] grid3Streams = { { 1, 1, 2, 2, 2 }, { 3, 1, 1, 2, 4 }, { 3, 3, 1, 4, 2 }, { 3, 5, 4, 5, 4 },
				{ 3, 5, 5, 5, 4 } };
		int[][] grid3LockedNumbers = { { 0, 0, 0, 0, 3 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 2 },
				{ 4, 0, 0, 1, 0 } };

		GRID_3 = newStrimkoGrid(grid3Size, grid3Streams, grid3LockedNumbers);

		int[][] grid4LockedNumbers = { { 0, 7, 0, 0, 0, 6, 0, 0, 0 }, { 5, 0, 0, 0, 0, 8, 0, 2, 0 },
				{ 8, 1, 0, 0, 2, 4, 0, 0, 0 }, { 0, 6, 5, 0, 0, 0, 0, 0, 7 }, { 3, 0, 0, 7, 0, 1, 0, 0, 6 },
				{ 7, 0, 0, 0, 0, 0, 5, 1, 0 }, { 0, 0, 0, 2, 4, 0, 0, 7, 1 }, { 0, 8, 0, 3, 0, 0, 0, 0, 5 },
				{ 0, 0, 0, 6, 0, 0, 0, 9, 0 } };

		GRID_4 = newSudokuGrid(grid4LockedNumbers);

		int grid5Size = 7;
		int[][] grid5Streams = { { 1, 2, 3, 3, 2, 3, 3 }, { 2, 1, 2, 2, 3, 4, 3 }, { 2, 1, 1, 1, 1, 4, 3 },
				{ 2, 1, 5, 4, 4, 5, 4 }, { 6, 6, 6, 5, 4, 5, 7 }, { 6, 6, 5, 5, 5, 4, 7 }, { 6, 6, 7, 7, 7, 7, 7 } };
		int[][] grid5LockedNumbers = { { 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 3, 5, 6, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 5, 7, 2, 0 }, { 0, 0, 0, 4, 0, 0, 0 }, { 0, 0, 0, 0, 6, 3, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };

		GRID_5 = newStrimkoGrid(grid5Size, grid5Streams, grid5LockedNumbers);

		int[][] grid6LockedNumbers = { { 2, 0, 0, 0, 1, 0, 0, 0, 8 }, { 0, 4, 0, 8, 0, 9, 0, 2, 0 },
				{ 0, 0, 1, 0, 0, 0, 9, 0, 0 }, { 0, 3, 0, 2, 0, 1, 0, 7, 0 }, { 4, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 0, 9, 0, 4, 0, 8, 0, 3, 0 }, { 0, 0, 4, 0, 0, 0, 3, 0, 0 }, { 0, 7, 0, 1, 0, 5, 0, 9, 0 },
				{ 6, 0, 0, 0, 9, 0, 0, 0, 2 } };

		GRID_6 = newSudokuGrid(grid6LockedNumbers);
	}

	@Test
	public void solveEasyStrimkoTest() {
		solveTest(GRID_1, EASY);
	}

	@Test
	public void solveEasySudokuTest() {
		solveTest(GRID_2, EASY);
	}

	@Test
	public void solveMediumStrimkoTest() {
		solveTest(GRID_3, MEDIUM);
	}

	@Test
	public void solveMediumSudokuTest() {
		solveTest(GRID_4, MEDIUM);
	}

	@Test
	public void solveHardStrimkoTest() {
		solveTest(GRID_5, HARD);
	}

	@Test
	public void solveHardSudokuTest() {
		solveTest(GRID_6, HARD);
	}

	private void solveTest(Grid grid, Difficulty expectedDifficulty) {
		Result result = solve(grid);
		assertTrue(result.isSolvable());
		assertEquals(expectedDifficulty, result.getDifficulty());
	}
}