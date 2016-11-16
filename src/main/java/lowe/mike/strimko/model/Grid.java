package lowe.mike.strimko.model;

import static lowe.mike.strimko.model.StreamValidator.validate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Range;

/**
 * {@code Grid} instances are intended to provide information about a grid which
 * make up a {@link Puzzle}.
 * <p>
 * {@code Grid}s are backed by an array of {@link Cell}s.
 * 
 * @author Mike Lowe
 */
public final class Grid implements Observer, Serializable {
	private static final long serialVersionUID = 9139586453735621725L;

	private final int size;
	private final Cell[][] cells;

	/**
	 * Create {@link List} of each group so that it's easy to iterate over
	 * {@link Cell}s when solving.
	 */
	private final Map<Integer, List<Cell>> rows = new HashMap<>();
	private final Map<Integer, List<Cell>> columns = new HashMap<>();
	private final Map<Integer, List<Cell>> streams = new HashMap<>();

	/**
	 * Creates a new {@code Grid} instance given the size, the array of streams
	 * and a {@link Map} of locked numbers.
	 * 
	 * @param size
	 *            the size of the {@code Grid}
	 * @param streams
	 *            the streams that make up the {@code Grid}
	 * @param lockedNumbers
	 *            the {@link Map} of numbers locked in the {@code Grid}
	 * @throws IllegalArgumentException
	 *             if the {@code streams} array is invalid
	 */
	public Grid(int size, int[][] streams, Map<Position, Integer> lockedNumbers) {
		validate(streams);
		this.size = size;
		this.cells = new Cell[this.size][this.size];
		init(streams, lockedNumbers);
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
		initCopy(grid.cells);
	}

	private void init(int[][] streams, Map<Position, Integer> lockedNumbers) {
		initCells(streams);
		initMaps();
		initCellNumbers(lockedNumbers);
	}

	private void initCells(int[][] streams) {
		Set<Integer> possibleNumbers = getRangeOfPossibleNumbers();

		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				int stream = streams[row][column];
				Position position = new Position(row, column, stream);
				Cell cell = new Cell(position);
				cell.getPossibleNumbers().addAll(possibleNumbers);
				cells[row][column] = cell;
				cell.addObserver(this);
			}
		}
	}

	private Set<Integer> getRangeOfPossibleNumbers() {
		return ContiguousSet.create(Range.closed(1, size), DiscreteDomain.integers());
	}

	private void initMaps() {
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				addToMap(cell.getPosition().getRow(), cell, rows);
				addToMap(cell.getPosition().getColumn(), cell, columns);
				addToMap(cell.getPosition().getStream(), cell, streams);
			}
		}
	}

	private void addToMap(int key, Cell value, Map<Integer, List<Cell>> map) {
		List<Cell> cells = map.get(key);
		if (cells == null)
			cells = new ArrayList<>();
		cells.add(value);
		map.put(key, cells);
	}

	private void initCellNumbers(Map<Position, Integer> numbers) {
		for (Position position : numbers.keySet()) {
			int number = numbers.get(position);
			Cell cell = getCell(position);
			cell.setNumber(number);
			cell.lock();
		}
	}

	private void initCopy(Cell[][] cells) {
		initCopyCells(cells);
		initMaps();
	}

	private void initCopyCells(Cell[][] cells) {
		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells.length; column++) {
				Cell cell = new Cell(cells[row][column]);
				this.cells[row][column] = cell;
				cell.addObserver(this);
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
	 *            row of the {@link Cell}
	 * @param column
	 *            column of the {@link Cell}
	 * @return the {@link Cell} at the position indicated by the {@code row} and
	 *         {@code column}
	 */
	public Cell getCell(int row, int column) {
		return cells[row][column];
	}

	/**
	 * @param position
	 *            {@link Position} of the {@link Cell}
	 * @return the {@link Cell} at the specified {@link Position}
	 */
	public Cell getCell(Position position) {
		return getCell(position.getRow(), position.getColumn());
	}

	/**
	 * @return the {@link Collection} of rows
	 */
	public Collection<List<Cell>> getRows() {
		return rows.values();
	}

	/**
	 * @param row
	 *            row to get {@link Cell}s from
	 * @return the {@link List} of {@link Cell}s in the given {@code row}
	 */
	public List<Cell> getRow(int row) {
		return rows.get(row);
	}

	/**
	 * @return the {@link Collection} of columns
	 */
	public Collection<List<Cell>> getColumns() {
		return columns.values();
	}

	/**
	 * @param column
	 *            column to get {@link Cell}s from
	 * @return the {@link List} of {@link Cell}s in the given {@code column}
	 */
	public List<Cell> getColumn(int column) {
		return columns.get(column);
	}

	/**
	 * @return the {@link Collection} of streams
	 */
	public Collection<List<Cell>> getStreams() {
		return streams.values();
	}

	/**
	 * @param stream
	 *            stream to get {@link Cell}s from
	 * @return the {@link List} of {@link Cell}s in the given {@code stream}
	 */
	public List<Cell> getStream(int stream) {
		return streams.get(stream);
	}

	/**
	 * Resets all of the {@link Cell}s, that have not been locked, in this
	 * {@code Grid}.
	 */
	public void reset() {
		for (Cell[] row : cells)
			for (Cell cell : row)
				cell.clearNumber();
	}

	/**
	 * @return {@code true} if this {@code Grid} is in a solved state;
	 *         {@code false} otherwise
	 */
	public boolean isSolved() {
		Multiset<Integer> numberCount = HashMultiset.create();

		for (List<Cell> row : getRows())
			for (Cell cell : row)
				numberCount.add(cell.getNumber());

		for (int number = 1; number <= size; number++)
			if (numberCount.count(number) != size)
				return false;

		return true;
	}

	@Override
	public void update(Observable o, Object arg) {
		Cell cell = (Cell) o;

		if (cell.isEmpty()) {
			resetPossibleNumbers();
			updateAllPossibleNumbers();
		} else
			updateRelevantPossibleNumbers(cell);
	}

	private void resetPossibleNumbers() {
		Set<Integer> possibleNumbers = getRangeOfPossibleNumbers();

		for (Cell[] row : cells)
			for (Cell cell : row)
				cell.getPossibleNumbers().addAll(possibleNumbers);
	}

	private void updateAllPossibleNumbers() {
		for (Cell[] row : cells)
			for (Cell cell : row)
				if (cell.isSet())
					updateRelevantPossibleNumbers(cell);
	}

	private void updateRelevantPossibleNumbers(Cell cell) {
		cell.getPossibleNumbers().clear();
		int number = cell.getNumber();
		removePossibleNumberFromRow(number, cell.getPosition().getRow());
		removePossibleNumberFromColumn(number, cell.getPosition().getColumn());
		removePossibleNumberFromStream(number, cell.getPosition().getStream());
	}

	private void removePossibleNumberFromRow(int number, int row) {
		removePossibleNumberFromCells(number, getRow(row));
	}

	private void removePossibleNumberFromColumn(int number, int column) {
		removePossibleNumberFromCells(number, getColumn(column));
	}

	private void removePossibleNumberFromStream(int number, int stream) {
		removePossibleNumberFromCells(number, getStream(stream));
	}

	private void removePossibleNumberFromCells(int number, List<Cell> cells) {
		for (Cell cell : cells)
			cell.getPossibleNumbers().remove(number);
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