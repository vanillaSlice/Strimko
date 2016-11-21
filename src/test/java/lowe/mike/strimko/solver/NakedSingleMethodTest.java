package lowe.mike.strimko.solver;

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
		int gridSize = 4;
		int[][] gridStreams = new int[][] { { 1, 1, 2, 2 }, { 2, 2, 1, 1 }, { 3, 3, 4, 4 }, { 4, 4, 3, 3 } };
		int[][] gridLockedNumbers = { { 0, 0, 0, 0 }, { 0, 4, 0, 2 }, { 0, 3, 0, 1 }, { 0, 0, 0, 0 } };

		GRID = new Grid(gridSize);
		GRID.initStreams(gridStreams);
		GRID.initLockedNumbers(gridLockedNumbers);
		GRID.updatePossibleNumbers();
	}

	@Test
	public void nakedSingleTest() {
		Set<Position> hints = new LinkedHashSet<>();
		boolean changed = NakedSingleMethod.run(GRID, hints);
		assertTrue(changed);
		Position changedPosition = new Position(0, 1);
		assertEquals(1, GRID.getCell(changedPosition).getNumber());
		assertTrue(hints.contains(changedPosition));
	}
}