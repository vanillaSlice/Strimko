package lowe.mike.strimko.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Mike Lowe
 */
public final class CellTest {

	@Test
	public void newInstanceTest() {
		// test initial state
		Position position = new Position(0, 1, 2);
		Cell cell = new Cell(position);
		assertEquals(position, cell.getPosition());
		assertFalse(cell.isSet());
		assertTrue(cell.isEmpty());
		assertTrue(cell.getPossibleNumbers().isEmpty());
		assertFalse(cell.isLocked());

		// test after setting a number
		int number = 5;
		cell.setNumber(5);
		assertEquals(number, cell.getNumber());
		assertTrue(cell.isSet());
		assertFalse(cell.isEmpty());

		// test after clearing a number
		cell.clearNumber();
		assertNotEquals(number, cell.getNumber());
		assertFalse(cell.isSet());
		assertTrue(cell.isEmpty());

		// test after changing possible numbers
		cell.getPossibleNumbers().add(number);
		assertTrue(cell.getPossibleNumbers().contains(number));

		// test after locking
		cell.lock();
		assertTrue(cell.isLocked());
		cell.setNumber(number);
		assertNotEquals(number, cell.getNumber());
	}

	@Test
	public void copyOfTest() {
		Position position = new Position(2, 3, 6);
		Cell cell = new Cell(position);
		Cell copy = new Cell(cell);
		assertEquals(cell, copy);
		cell.setNumber(5);
		assertNotEquals(cell, copy);
	}
}