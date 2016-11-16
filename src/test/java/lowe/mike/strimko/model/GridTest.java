package lowe.mike.strimko.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author Mike Lowe
 */
public final class GridTest {
	private static final int[][] streams = new int[3][3];
	private static final Map<Position, Integer> lockedNumbers = new HashMap<>();

	static {
		streams[0][0] = 1;
		streams[0][1] = 2;
		streams[0][2] = 1;
		streams[1][0] = 2;
		streams[1][1] = 1;
		streams[1][2] = 2;
		streams[2][0] = 3;
		streams[2][1] = 3;
		streams[2][2] = 3;

		lockedNumbers.put(new Position(0, 0, 0), 1);
		lockedNumbers.put(new Position(2, 2, 3), 3);
	}

	@Test
	public void newInstanceTest() {
		Grid grid = new Grid(3, streams, lockedNumbers);

		// check a cell with number has been initialised properly
		Cell cell1 = grid.getCell(0, 0);
		assertEquals(1, cell1.getNumber());
		assertTrue(cell1.isLocked());
		assertTrue(cell1.getPossibleNumbers().isEmpty());

		// check a cell in the same row has been updated accordingly
		Cell cell2 = grid.getCell(0, 1);
		assertFalse(cell2.isSet());
		assertFalse(cell2.getPossibleNumbers().contains(1));
		assertTrue(cell2.getPossibleNumbers().contains(2));

		// check that a cell is updated after another cell changes number
		Cell cell3 = grid.getCell(1, 0);
		assertTrue(cell3.getPossibleNumbers().contains(3));
		cell2.setNumber(3);
		assertTrue(cell2.isSet());
		assertTrue(cell2.getPossibleNumbers().isEmpty());
		assertFalse(cell3.getPossibleNumbers().contains(3));

		// check grid is reset properly
		grid.reset();
		assertTrue(cell1.getPossibleNumbers().isEmpty());
		assertTrue(cell3.getPossibleNumbers().contains(3));
	}

	@Test
	public void copyOfTest() {
		Grid grid = new Grid(3, streams, lockedNumbers);
		Grid copy = new Grid(grid);
		assertEquals(grid, copy);
		grid.getCell(1, 0).setNumber(1);
		assertNotEquals(grid, copy);
	}
}