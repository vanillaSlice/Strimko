package lowe.mike.strimko.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

/**
 * {@code Grid} instances are intended to provide information about a grid which
 * make up a {@link Puzzle}.
 * <p>
 * {@code Grid}s are backed by an array of {@link Cell}s.
 * 
 * @author Mike Lowe
 */
public final class Grid implements Serializable {
	private static final long serialVersionUID = 7707032510798727615L;

	private final int size;
	private final Cell[][] cells;

	/**
	 * Creates a new {@code Grid} instance given the size.
	 * 
	 * @param size
	 *            the size of the {@code Grid}
	 */
	public Grid(int size) {
		this.size = size;
		this.cells = new Cell[this.size][this.size];
		initNewInstance();
	}

	/**
	 * Copy constructor creates a new instance of a {@code Grid} given an
	 * existing instance.
	 * 
	 * @param grid
	 *            the existing {@code Grid} instance
	 */
	public Grid(Grid grid) {
		this.size = grid.size;
		this.cells = new Cell[this.size][this.size];
		initCopyInstance(grid.cells);
	}

	private void initNewInstance() {
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				Position position = new Position(row, column);
				Cell cell = new Cell(position);
				cell.getPossibleNumbers().addAll(getRangeOfAllPossibleNumbers());
				cells[row][column] = cell;
			}
		}
	}

	private Set<Integer> getRangeOfAllPossibleNumbers() {
		return ContiguousSet.create(Range.closed(1, size), DiscreteDomain.integers());
	}

	private void initCopyInstance(Cell[][] cells) {
		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells.length; column++) {
				Cell original = cells[row][column];
				Cell copy = new Cell(original);
				this.cells[row][column] = copy;
			}
		}
	}

	/**
	 * Set up the streams for this {@code Grid} given an array of {@code int}s.
	 * 
	 * @param streams
	 *            the array of streams
	 */
	public void initStreams(int[][] streams) {
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				int stream = streams[row][column];
				Cell cell = cells[row][column];
				cell.setStream(stream);
			}
		}
	}

	/**
	 * Sets up the locked numbers in this {@code Grid} given an array of
	 * {@code int}s.
	 * 
	 * @param lockedNumbers
	 *            the array of locked numbers
	 */
	public void initLockedNumbers(int[][] lockedNumbers) {
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				int number = lockedNumbers[row][column];
				if (number != 0) {
					Cell cell = cells[row][column];
					cell.setNumber(number);
				}
			}
		}
	}

	/**
	 * @return the size of this {@code Grid}
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param row
	 *            row number of the {@link Cell}
	 * @param column
	 *            column number of the {@link Cell}
	 * @return the {@link Cell} at the position indicated by the {@code row} and
	 *         {@code column}
	 */
	public Cell getCell(int row, int column) {
		return cells[row][column];
	}

	/**
	 * @param position
	 *            the {@link Position} of the {@code Cell}
	 * @return the {@code Cell} at the given {@link Position}
	 */
	public Cell getCell(Position position) {
		return getCell(position.getRow(), position.getColumn());
	}

	/**
	 * @return the {@link Set} of rows
	 */
	public Set<Set<Cell>> getRows() {
		Set<Set<Cell>> rows = new LinkedHashSet<>();

		for (int row = 0; row < size; row++)
			rows.add(getRow(row));

		return rows;
	}

	/**
	 * @param row
	 *            row number to get {@link Cell}s from
	 * @return the {@link Set} of {@link Cell}s in the given {@code row}
	 */
	public Set<Cell> getRow(int row) {
		return new LinkedHashSet<>(Arrays.asList(cells[row]));
	}

	/**
	 * @return the {@link Set} of columns
	 */
	public Set<Set<Cell>> getColumns() {
		Set<Set<Cell>> columns = new LinkedHashSet<>();

		for (int column = 0; column < size; column++)
			columns.add(getColumn(column));

		return columns;
	}

	/**
	 * @param column
	 *            column number to get {@link Cell}s from
	 * @return the {@link Set} of {@link Cell}s in the given {@code column}
	 */
	public Set<Cell> getColumn(int column) {
		Set<Cell> columnCells = new LinkedHashSet<>();

		for (int row = 0; row < size; row++) {
			Cell cell = cells[row][column];
			columnCells.add(cell);
		}

		return columnCells;
	}

	/**
	 * @return the {@link Set} of streams
	 */
	public Set<Set<Cell>> getStreams() {
		Set<Set<Cell>> streams = new LinkedHashSet<>();

		for (int stream = 1; stream <= size; stream++)
			streams.add(getStream(stream));

		return streams;
	}

	/**
	 * @param stream
	 *            stream number to get {@link Cell}s from
	 * @return the {@link Set} of {@link Cell}s in the given {@code stream}
	 */
	public Set<Cell> getStream(int stream) {
		Set<Cell> streamCells = new LinkedHashSet<>();

		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				Cell cell = cells[row][column];
				if (cell.getStream() == stream)
					streamCells.add(cell);
			}
		}

		return streamCells;
	}

	/**
	 * Updates the possible numbers of each {@link Cell} in this {@code Grid}.
	 * In other words, for every {@link Cell} that has a number, this number is
	 * removed from the possible numbers of each other {@link Cell} in the same
	 * row, column and stream.
	 */
	public void updatePossibleNumbers() {
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				Cell cell = getCell(row, column);
				if (cell.isSet()) {
					int number = cell.getNumber();
					int stream = cell.getStream();
					cell.getPossibleNumbers().clear();
					removePossibleFromGroup(number, getRow(row));
					removePossibleFromGroup(number, getColumn(column));
					removePossibleFromGroup(number, getStream(stream));
				}
			}
		}
	}

	/**
	 * Resets the possible numbers of each {@link Cell} in this {@code Grid}.
	 */
	public void resetPossibleNumbers() {
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				Cell cell = getCell(row, column);
				cell.getPossibleNumbers().clear();
				cell.getPossibleNumbers().addAll(getRangeOfAllPossibleNumbers());
			}
		}
	}

	private boolean removePossibleFromGroup(int possible, Set<Cell> group) {
		boolean changed = false;

		for (Cell cell : group)
			if (cell.getPossibleNumbers().remove(possible))
				changed = true;

		return changed;
	}

	/**
	 * @return {@code true} if this {@code Grid} is in a solved state;
	 *         {@code false} otherwise
	 */
	public boolean isSolved() {
		return containsAllNumbers(getRows()) && containsAllNumbers(getColumns()) && containsAllNumbers(getStreams());
	}

	private boolean containsAllNumbers(Set<Set<Cell>> group) {
		for (Set<Cell> cells : group) {
			Set<Integer> numbersInGroup = new HashSet<>();
			for (Cell cell : cells) {
				int number = cell.getNumber();
				if (number == 0 || numbersInGroup.contains(number))
					return false;
				numbersInGroup.add(number);
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(size, cells);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grid other = (Grid) obj;
		if (!Arrays.deepEquals(cells, other.cells))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Grid [size=" + size + ", cells=" + Arrays.toString(cells) + "]";
	}
}