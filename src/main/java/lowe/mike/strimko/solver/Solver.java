package lowe.mike.strimko.solver;

import java.util.LinkedHashSet;
import java.util.Set;

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
 * <li>'Pointing Pairs'</li>
 * <li>'Pointing Triples'</li>
 * <li>'Naked Pairs'</li>
 * <li>'Naked Triples'</li>
 * <li>'Naked Quads'</li>
 * <li>'Hidden Pairs'</li>
 * <li>'Hidden Triples'</li>
 * <li>'Hidden Quads'</li>
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
		Set<Position> hints = new LinkedHashSet<>();

		while (!grid.isSolved()) {
			boolean changed = runEasyMethods(grid, hints);

			if (!changed) {
				changed = runMediumMethods(grid);
				if (difficulty == Difficulty.EASY)
					difficulty = Difficulty.MEDIUM;
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

	private static boolean runEasyMethods(Grid grid, Set<Position> hints) {
		if (NakedSingleMethod.run(grid, hints))
			return true;
		if (HiddenSingleMethod.run(grid, hints))
			return true;

		return false;
	}

	private static boolean runMediumMethods(Grid grid) {
		// pointing pairs
		if (GroupInteractionsMethod.run(grid, 2))
			return true;
		// pointing triples
		if (GroupInteractionsMethod.run(grid, 3))
			return true;
		if (GroupInteractionsMethod.runStreamLineReductionOverRows(grid, 2))
			return true;
		if (GroupInteractionsMethod.runStreamLineReductionOverColumns(grid, 2))
			return true;
		// naked pairs
		if (NakedNMethod.run(grid, 2))
			return true;

		return false;
	}

	private static boolean runHardMethods(Grid grid) {
		// naked triples
		if (NakedNMethod.run(grid, 3))
			return true;
		// naked quads
		if (NakedNMethod.run(grid, 4))
			return true;
		// hidden pairs
		if (HiddenNMethod.run(grid, 2))
			return true;
		// hidden triples
		if (HiddenNMethod.run(grid, 3))
			return true;
		// hidden quads
		if (HiddenNMethod.run(grid, 4))
			return true;

		return false;
	}
}