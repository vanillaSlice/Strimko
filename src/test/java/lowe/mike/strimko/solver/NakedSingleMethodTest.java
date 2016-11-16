package lowe.mike.strimko.solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * @author Mike Lowe
 */
public final class NakedSingleMethodTest {
	private static final Grid present;

	static {
		int size = 4;
		int[][] streams = new int[][] { { 1, 1, 2, 2 }, { 2, 2, 1, 1 }, { 3, 3, 4, 4 }, { 4, 4, 3, 3 } };
		Map<Position, Integer> lockedNumbers = new HashMap<>();
		lockedNumbers.put(new Position(1, 1, 2), 4);
		lockedNumbers.put(new Position(1, 3, 1), 2);
		lockedNumbers.put(new Position(2, 1, 3), 3);
		lockedNumbers.put(new Position(2, 3, 4), 1);

		present = new Grid(size, streams, lockedNumbers);
	}

	@Test
	public void nakedSingleTest() {
		List<Position> hints = new ArrayList<>();
		boolean changed = NakedSingleMethod.run(present, hints);
		assertTrue(changed);
		Position changedPosition = new Position(0, 1, 1);
		assertEquals(1, present.getCell(changedPosition).getNumber());
		assertTrue(hints.contains(changedPosition));
	}
}