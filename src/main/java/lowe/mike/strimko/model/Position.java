package lowe.mike.strimko.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * {@code Position} instances contain the row and column numbers for a
 * {@link Cell}.
 * <p>
 * {@code Position} instances are immutable.
 * 
 * @author Mike Lowe
 */
public final class Position implements Serializable {
	private static final long serialVersionUID = -4025052816744893697L;

	private final int row;
	private final int column;

	/**
	 * Creates a new {@code Position} instance given the row and column numbers.
	 * 
	 * @param row
	 *            the row number
	 * @param column
	 *            the column number
	 */
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * @return the row number
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column number
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(row, column);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position [row=" + row + ", column=" + column + "]";
	}
}