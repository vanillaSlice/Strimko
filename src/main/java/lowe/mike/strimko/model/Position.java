package lowe.mike.strimko.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * {@code Position} instances are intended to provide positional information for
 * a {@link Cell}.
 * <p>
 * This information includes:
 * <ul>
 * <li>the row number</li>
 * <li>the column number</li>
 * <li>the stream number</li>
 * </ul>
 * <p>
 * {@code Position} instances are immutable.
 * 
 * @author Mike Lowe
 */
public final class Position implements Serializable {
	private static final long serialVersionUID = -2562262607952775743L;

	private final int row;
	private final int column;
	private final int stream;

	/**
	 * Creates a new {@code Position} instance given the row, column and stream.
	 * 
	 * @param row
	 *            the row number
	 * @param column
	 *            the column number
	 * @param stream
	 *            the stream number
	 */
	public Position(int row, int column, int stream) {
		this.row = row;
		this.column = column;
		this.stream = stream;
	}

	/**
	 * Copy constructor creates a new {@code Position} given an existing
	 * instance.
	 * 
	 * @param position
	 *            the existing {@code Position} instance to create a new one
	 *            from
	 */
	public Position(Position position) {
		this(position.row, position.column, position.stream);
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @return the stream
	 */
	public int getStream() {
		return stream;
	}

	@Override
	public int hashCode() {
		return Objects.hash(row, column, stream);
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
		if (stream != other.stream)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position [row=" + row + ", column=" + column + ", stream=" + stream + "]";
	}
}