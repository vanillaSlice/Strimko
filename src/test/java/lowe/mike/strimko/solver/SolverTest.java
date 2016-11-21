package lowe.mike.strimko.solver;

import static lowe.mike.strimko.model.Constants.SUDOKU_SIZE;
import static lowe.mike.strimko.model.Constants.SUDOKU_STREAMS;
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

		GRID_1 = new Grid(grid1Size);
		GRID_1.initStreams(grid1Streams);
		GRID_1.initLockedNumbers(grid1LockedNumbers);
		GRID_1.updatePossibleNumbers();

		int[][] grid2LockedNumbers = { { 8, 0, 0, 0, 0, 0, 4, 6, 0 }, { 0, 9, 7, 6, 0, 1, 2, 0, 8 },
				{ 0, 4, 0, 8, 0, 5, 0, 1, 0 }, { 0, 0, 0, 2, 7, 9, 0, 0, 4 }, { 0, 5, 0, 0, 0, 0, 0, 2, 0 },
				{ 1, 0, 0, 4, 5, 3, 0, 0, 0 }, { 0, 7, 0, 3, 0, 6, 0, 9, 0 }, { 3, 0, 2, 9, 0, 7, 5, 4, 0 },
				{ 0, 6, 1, 0, 0, 0, 0, 0, 2 } };

		GRID_2 = new Grid(SUDOKU_SIZE);
		GRID_2.initStreams(SUDOKU_STREAMS);
		GRID_2.initLockedNumbers(grid2LockedNumbers);
		GRID_2.updatePossibleNumbers();

		int grid3Size = 5;
		int[][] grid3Streams = { { 1, 1, 2, 2, 2 }, { 3, 1, 1, 2, 4 }, { 3, 3, 1, 4, 2 }, { 3, 5, 4, 5, 4 },
				{ 3, 5, 5, 5, 4 }, };
		int[][] grid3LockedNumbers = { { 0, 0, 0, 0, 3 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 2 },
				{ 4, 0, 0, 1, 0 } };

		GRID_3 = new Grid(grid3Size);
		GRID_3.initStreams(grid3Streams);
		GRID_3.initLockedNumbers(grid3LockedNumbers);
		GRID_3.updatePossibleNumbers();

		int[][] grid4LockedNumbers = { { 0, 7, 0, 0, 0, 6, 0, 0, 0 }, { 5, 0, 0, 0, 0, 8, 0, 2, 0 },
				{ 8, 1, 0, 0, 2, 4, 0, 0, 0 }, { 0, 6, 5, 0, 0, 0, 0, 0, 7 }, { 3, 0, 0, 7, 0, 1, 0, 0, 6 },
				{ 7, 0, 0, 0, 0, 0, 5, 1, 0 }, { 0, 0, 0, 2, 4, 0, 0, 7, 1 }, { 0, 8, 0, 3, 0, 0, 0, 0, 5 },
				{ 0, 0, 0, 6, 0, 0, 0, 9, 0 }, };

		GRID_4 = new Grid(SUDOKU_SIZE);
		GRID_4.initStreams(SUDOKU_STREAMS);
		GRID_4.initLockedNumbers(grid4LockedNumbers);
		GRID_4.updatePossibleNumbers();

		int grid5Size = 7;
		int[][] grid5Streams = { { 1, 2, 3, 3, 2, 3, 3 }, { 2, 1, 2, 2, 3, 4, 3 }, { 2, 1, 1, 1, 1, 4, 3 },
				{ 2, 1, 5, 4, 4, 5, 4 }, { 6, 6, 6, 5, 4, 5, 7 }, { 6, 6, 5, 5, 5, 4, 7 }, { 6, 6, 7, 7, 7, 7, 7 } };
		int[][] grid5LockedNumbers = { { 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 3, 5, 6, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 5, 7, 2, 0 }, { 0, 0, 0, 4, 0, 0, 0 }, { 0, 0, 0, 0, 6, 3, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };

		GRID_5 = new Grid(grid5Size);
		GRID_5.initStreams(grid5Streams);
		GRID_5.initLockedNumbers(grid5LockedNumbers);
		GRID_5.updatePossibleNumbers();

		int[][] grid6LockedNumbers = { { 0, 3, 5, 0, 0, 0, 0, 0, 2 }, { 0, 4, 0, 0, 5, 0, 0, 0, 1 },
				{ 9, 0, 0, 8, 0, 0, 7, 0, 0 }, { 0, 0, 0, 0, 0, 6, 0, 8, 0 }, { 0, 2, 0, 5, 0, 8, 0, 7, 0 },
				{ 0, 5, 0, 9, 0, 0, 0, 0, 0 }, { 0, 0, 7, 0, 0, 2, 0, 0, 3 }, { 2, 0, 0, 0, 4, 0, 0, 9, 0 },
				{ 1, 0, 0, 0, 0, 0, 5, 2, 0 }, };

		GRID_6 = new Grid(SUDOKU_SIZE);
		GRID_6.initStreams(SUDOKU_STREAMS);
		GRID_6.initLockedNumbers(grid6LockedNumbers);
		GRID_6.updatePossibleNumbers();
	}

	@Test
	public void solveEasyStrimkoTest() {
		Result result = Solver.solve(GRID_1);
		assertTrue(result.isSolvable());
		assertEquals(Difficulty.EASY, result.getDifficulty());
	}

	@Test
	public void solveEasySudokuTest() {
		Result result = Solver.solve(GRID_2);
		assertTrue(result.isSolvable());
		assertEquals(Difficulty.EASY, result.getDifficulty());
	}

	@Test
	public void solveMediumStrimkoTest() {
		Result result = Solver.solve(GRID_3);
		assertTrue(result.isSolvable());
		assertEquals(Difficulty.MEDIUM, result.getDifficulty());
	}

	@Test
	public void solveMediumSudokuTest() {
		Result result = Solver.solve(GRID_4);
		assertTrue(result.isSolvable());
		assertEquals(Difficulty.MEDIUM, result.getDifficulty());
	}

	@Test
	public void solveHardStrimkoTest() {
		Result result = Solver.solve(GRID_5);
		assertTrue(result.isSolvable());
		assertEquals(Difficulty.HARD, result.getDifficulty());
	}

	@Test
	public void solveHardSudokuTest() {
		Result result = Solver.solve(GRID_6);
		assertTrue(result.isSolvable());
		assertEquals(Difficulty.HARD, result.getDifficulty());
	}
}