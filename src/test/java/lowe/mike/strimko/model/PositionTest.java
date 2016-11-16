package lowe.mike.strimko.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Mike Lowe
 */
public final class PositionTest {

	@Test
	public void newInstanceTest() {
		int row = 1;
		int column = 2;
		int stream = 3;
		Position position = new Position(row, column, stream);
		assertEquals(row, position.getRow());
		assertEquals(column, position.getColumn());
		assertEquals(stream, position.getStream());
	}

	@Test
	public void copyOfTest() {
		Position position = new Position(4, 5, 6);
		Position copy = new Position(position);
		assertEquals(position, copy);
	}
}