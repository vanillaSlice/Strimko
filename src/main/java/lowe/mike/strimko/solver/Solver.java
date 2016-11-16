package lowe.mike.strimko.solver;

import java.util.ArrayList;
import java.util.List;

import lowe.mike.strimko.model.Difficulty;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * {@code Solver} class provides a single method {@link #solve(Grid)} which
 * attempts to solve a given {@link Grid} and returns a {@link Result} object
 * which contains details about the solution etc.
 * <p>
 * Various methods are used to attempt to solve a {@link Grid}, these include:
 * <ul>
 * <li>'Naked Singles'</li>
 * <li>'Hidden Singles'</li>
 * 
 * <li>Brute-force (as a last resort)</li>
 * </ul>
 * Details about each of these methods can be found online.
 * <p>
 * Instances of {@code Solver} cannot be created.
 * 
 * @author Mike Lowe
 */
public final class Solver {

	// don't want instances
	private Solver() {
	}

	public static Result solve(Grid grid) {
		// create a copy so we don't alter the original
		grid = new Grid(grid);
		Result result = runSolvingMethods(grid);
		return result;
	}

	private static Result runSolvingMethods(Grid grid) {
		Difficulty difficulty = Difficulty.EASY;
		List<Position> hints = new ArrayList<>();

		while (!grid.isSolved()) {
			boolean changed = runEasyMethods(grid, hints);

			if (!changed) {
				difficulty = Difficulty.MEDIUM;
				changed = runMediumMethods(grid);
			}

			if (!changed) {
				difficulty = Difficulty.HARD;
				changed = runHardMethods(grid);
			}

			// last resort - return result from brute-force method
			if (!changed) {
				Result result = BruteForceMethod.run(grid, hints);
				return result;
			}
		}

		return Result.newSolvableInstance(difficulty, grid, hints);
	}

	private static boolean runEasyMethods(Grid grid, List<Position> hints) {
		if (NakedSingleMethod.run(grid, hints))
			return true;
		if (HiddenSingleMethod.run(grid, hints))
			return true;

		return false;
	}

	private static boolean runMediumMethods(Grid grid) {
		return false;
	}

	private static boolean runHardMethods(Grid grid) {
		return false;
	}
}