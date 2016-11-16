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
public final class HiddenSingleMethodTest {
	private static final Grid row;
	private static final Grid column;
	private static final Grid stream;

	static {
		int rowSize = 4;
		int[][] rowStreams = { { 1, 2, 1, 2 }, { 3, 1, 2, 4 }, { 1, 3, 4, 2 }, { 3, 4, 3, 4 } };
		Map<Position, Integer> rowLockedNumbers = new HashMap<>();
		rowLockedNumbers.put(new Position(1, 1, 1), 3);
		rowLockedNumbers.put(new Position(1, 3, 4), 1);
		rowLockedNumbers.put(new Position(2, 1, 3), 4);

		row = new Grid(rowSize, rowStreams, rowLockedNumbers);

		int columnSize = 5;
		int[][] columnStreams = { { 1, 1, 1, 1, 1 }, { 2, 2, 3, 3, 4 }, { 2, 3, 3, 5, 4 }, { 2, 3, 5, 5, 4 },
				{ 2, 5, 5, 4, 4 } };
		Map<Position, Integer> columnLockedNumbers = new HashMap<>();
		columnLockedNumbers.put(new Position(1, 1, 2), 3);
		columnLockedNumbers.put(new Position(1, 4, 4), 4);
		columnLockedNumbers.put(new Position(4, 0, 2), 2);
		columnLockedNumbers.put(new Position(4, 3, 4), 5);

		column = new Grid(columnSize, columnStreams, columnLockedNumbers);

		int streamSize = 5;
		int[][] streamStreams = { { 1, 2, 3, 4, 4 }, { 1, 3, 2, 2, 4 }, { 1, 3, 3, 2, 4 }, { 1, 1, 2, 3, 4 },
				{ 5, 5, 5, 5, 5 } };
		Map<Position, Integer> streamLockedNumbers = new HashMap<>();
		streamLockedNumbers.put(new Position(0, 0, 1), 1);
		streamLockedNumbers.put(new Position(1, 1, 3), 2);
		streamLockedNumbers.put(new Position(3, 3, 3), 4);
		streamLockedNumbers.put(new Position(4, 4, 5), 5);

		stream = new Grid(streamSize, streamStreams, streamLockedNumbers);
	}

	@Test
	public void hiddenSingleRowTest() {
		List<Position> hints = new ArrayList<>();
		boolean changed = HiddenSingleMethod.run(row, hints);
		assertTrue(changed);
		Position changedPosition = new Position(0, 3, 2);
		assertEquals(3, row.getCell(changedPosition).getNumber());
		assertTrue(hints.contains(changedPosition));
	}

	@Test
	public void hiddenSingleColumnTest() {
		List<Position> hints = new ArrayList<>();
		boolean changed = HiddenSingleMethod.run(column, hints);
		assertTrue(changed);
		Position changedPosition = new Position(0, 0, 1);
		assertEquals(3, column.getCell(changedPosition).getNumber());
		assertTrue(hints.contains(changedPosition));
	}

	@Test
	public void hiddenSingleStreamTest() {
		List<Position> hints = new ArrayList<>();
		boolean changed = HiddenSingleMethod.run(stream, hints);
		assertTrue(changed);
		Position changedPosition = new Position(0, 3, 4);
		assertEquals(5, stream.getCell(changedPosition).getNumber());
		assertTrue(hints.contains(changedPosition));
	}
}