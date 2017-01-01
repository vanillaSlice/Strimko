package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.solver.NakedSingleMethod.run;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.junit.Test;

import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * @author Mike Lowe
 */
public final class NakedSingleMethodTests extends SolvingMethodTests {

	@Test
	public void test_run() {
		// setup
		int size = 4;
		int[][] streams = { { 1, 1, 2, 2 }, { 2, 2, 1, 1 }, { 3, 3, 4, 4 }, { 4, 4, 3, 3 } };
		int[][] numbers = { { 0, 0, 0, 0 }, { 0, 4, 0, 2 }, { 0, 3, 0, 1 }, { 0, 0, 0, 0 } };
		Grid grid = newStrimkoGrid(size, streams, numbers);
		Collection<Position> hints = new LinkedHashSet<>();
		Position changedPosition = new Position(0, 1);
		int expectedNumber = 1;

		// execution
		boolean changed = run(grid, hints);

		// verification
		assertTrue(changed);
		int actualNumber = grid.getCell(changedPosition).getNumber();
		assertEquals(expectedNumber, actualNumber);
		assertTrue(hints.contains(changedPosition));
	}

	@Test
	public void test_run_noChange() {
		// setup
		Grid grid = newGridWithNoNumbers();
		Collection<Position> hints = new LinkedHashSet<>();

		// execution
		boolean changed = run(grid, hints);

		// verification
		assertFalse(changed);
	}

}