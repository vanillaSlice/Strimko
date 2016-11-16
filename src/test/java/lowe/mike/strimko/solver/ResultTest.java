package lowe.mike.strimko.solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import lowe.mike.strimko.model.Difficulty;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * @author Mike Lowe
 */
public final class ResultTest {

	private static final Difficulty difficulty = Difficulty.EASY;
	private static final Grid solution;
	private static final List<Position> hints = new ArrayList<>();

	static {
		int size = 3;

		int[][] streams = new int[][] { { 1, 1, 2 }, { 1, 2, 3 }, { 2, 3, 3 } };

		Map<Position, Integer> lockedNumbers = new HashMap<>();
		lockedNumbers.put(new Position(1, 0, 1), 1);
		lockedNumbers.put(new Position(1, 1, 2), 3);
		lockedNumbers.put(new Position(1, 2, 3), 2);

		solution = new Grid(size, streams, lockedNumbers);

		hints.add(new Position(0, 0, 1));
	}

	@Test
	public void newUnsolvableInstanceTest() {
		Result result = Result.newUnsolvableInstance();
		assertFalse(result.isSolvable());
		assertFalse(result.hasMultipleSolutions());
	}

	@Test
	public void newMultipleSolutionsIntanceTest() {
		Result result = Result.newMultipleSolutionsInstance();
		assertTrue(result.isSolvable());
		assertTrue(result.hasMultipleSolutions());
	}

	@Test
	public void newSolvableTest() {
		Result result = Result.newSolvableInstance(difficulty, solution, hints);
		assertTrue(result.isSolvable());
		assertFalse(result.hasMultipleSolutions());
		assertEquals(difficulty, result.getDifficulty());
		assertEquals(solution, result.getSolution());
		assertEquals(hints, result.getHints());
	}

	@Test
	public void copyOfTest() {
		Result result = Result.newSolvableInstance(difficulty, solution, hints);
		Result copy = Result.copyOf(result);
		assertEquals(result, copy);
	}
}