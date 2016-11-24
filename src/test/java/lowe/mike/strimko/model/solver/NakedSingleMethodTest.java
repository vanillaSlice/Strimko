package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.solver.NakedSingleMethod.run;
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
public final class NakedSingleMethodTest {
	private static final Grid GRID;

	static {
		int size = 4;
		int[][] streams = new int[][] { { 1, 1, 2, 2 }, { 2, 2, 1, 1 }, { 3, 3, 4, 4 }, { 4, 4, 3, 3 } };
		int[][] lockedNumbers = { { 0, 0, 0, 0 }, { 0, 4, 0, 2 }, { 0, 3, 0, 1 }, { 0, 0, 0, 0 } };

		GRID = newStrimkoGrid(size, streams, lockedNumbers);
	}

	@Test
	public void nakedSingleTest() {
		Set<Position> hints = new LinkedHashSet<>();
		boolean changed = run(GRID, hints);
		assertTrue(changed);
		Position changedPosition = new Position(0, 1);
		assertEquals(1, GRID.getCell(changedPosition).getNumber());
		assertTrue(hints.contains(changedPosition));
	}
}