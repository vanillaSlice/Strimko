package lowe.mike.strimko.solver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * @author Mike Lowe
 */
public final class BruteForceMethodTest {

	private static final Grid solvable;
	private static final Grid multipleSolutions;
	private static final Grid unsolvable;
	private static final Grid filled;

	static {
		int solvableSize = 5;
		int[][] solvableStreams = { { 1, 1, 2, 2, 2 }, { 2, 2, 1, 3, 3 }, { 1, 1, 3, 4, 3 }, { 5, 4, 4, 5, 3 },
				{ 5, 5, 5, 4, 4 } };
		Map<Position, Integer> solvableLockedNumbers = new HashMap<>();
		solvableLockedNumbers.put(new Position(0, 2, 2), 3);
		solvableLockedNumbers.put(new Position(1, 1, 2), 4);
		solvableLockedNumbers.put(new Position(1, 3, 3), 1);
		solvableLockedNumbers.put(new Position(4, 2, 4), 5);

		solvable = new Grid(solvableSize, solvableStreams, solvableLockedNumbers);

		int multipleSolutionsSize = 3;
		int[][] multipleSolutionsStreams = { { 1, 1, 1 }, { 2, 2, 2 }, { 3, 3, 3 } };
		Map<Position, Integer> multipleSolutionsLockedNumbers = new HashMap<>();
		multipleSolutionsLockedNumbers.put(new Position(0, 0, 1), 1);

		multipleSolutions = new Grid(multipleSolutionsSize, multipleSolutionsStreams, multipleSolutionsLockedNumbers);

		int unsolvableSize = 4;
		int[][] unsolvableStreams = { { 1, 2, 2, 3 }, { 2, 1, 3, 2 }, { 4, 3, 1, 4 }, { 3, 4, 4, 1 } };
		Map<Position, Integer> unsolvableLockedNumbers = new HashMap<>();
		unsolvableLockedNumbers.put(new Position(0, 0, 1), 2);
		unsolvableLockedNumbers.put(new Position(0, 2, 2), 2);

		unsolvable = new Grid(unsolvableSize, unsolvableStreams, unsolvableLockedNumbers);

		int filledSize = 6;
		int[][] filledStreams = { { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 },
				{ 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6 } };
		Map<Position, Integer> filledLockedNumbers = new HashMap<>();
		for (int row = 0; row < filledSize; row++)
			for (int column = 0; column < filledSize; column++)
				filledLockedNumbers.put(new Position(row, column, column + 1), 1);

		filled = new Grid(filledSize, filledStreams, filledLockedNumbers);
	}

	@Test
	public void solvableTest() {
		Result result = BruteForceMethod.run(solvable, new ArrayList<>());
		assertTrue(result.isSolvable());
	}

	@Test
	public void multipleSolutionsTest() {
		Result result = BruteForceMethod.run(multipleSolutions, new ArrayList<>());
		assertTrue(result.hasMultipleSolutions());
	}

	@Test
	public void unsolvableTest() {
		Result result = BruteForceMethod.run(unsolvable, new ArrayList<>());
		assertFalse(result.isSolvable());
	}

	@Test
	public void filledTest() {
		Result result = BruteForceMethod.run(filled, new ArrayList<>());
		assertFalse(result.isSolvable());
	}
}