package lowe.mike.strimko.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;

/**
 * {@code Cell} instances are to intended to provide information about cells
 * that make up a {@link Grid}.
 * <p>
 * Information about the {@code Cell} includes the {@link Position}, the number
 * contained in the cell, a {@link Set} of possible numbers and if the
 * {@code Cell} is locked. If a {@code Cell} is locked, this means that the
 * number can no longer be changed.
 * 
 * @author Mike Lowe
 */
public final class Cell extends Observable implements Serializable {
	private static final long serialVersionUID = -5286952684477993384L;

	private final Position position;
	private int number;
	private final Set<Integer> possibleNumbers = new TreeSet<>();
	private boolean locked;

	/**
	 * Creates a new {@code Cell} instance given the {@link Position}.
	 * 
	 * @param position
	 *            the {@link Position}
	 */
	public Cell(Position position) {
		this.position = position;
	}

	/**
	 * Copy constructor creates a new instance of a {@code Cell} given an
	 * existing instance.
	 * 
	 * @param cell
	 *            the existing {@code Cell} instance
	 */
	public Cell(Cell cell) {
		this.position = cell.position;
		this.number = cell.number;
		this.possibleNumbers.addAll(cell.possibleNumbers);
		this.locked = cell.locked;
	}

	/**
	 * @return the {@link Position}
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @return this {@code Cell}'s number, 0 if it is empty
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sets the number of this {@code Cell}, if it is not locked. If this
	 * {@code Cell} is locked, then the current number will not be changed.
	 * 
	 * @param number
	 *            the number to set
	 */
	public void setNumber(int number) {
		if (!locked && this.number != number) {
			this.number = number;
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * Clears the number from this {@code Cell}, if it is not locked. If this
	 * {@code Cell} is locked, then the current number will remain.
	 */
	public void clearNumber() {
		setNumber(0);
	}

	/**
	 * @return {@code true} if this {@code Cell}'s number has been set;
	 *         {@code false} otherwise
	 */
	public boolean isSet() {
		return number != 0;
	}

	/**
	 * @return {@code true} if this {@code Cell}'s number has not been set;
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return !isSet();
	}

	/**
	 * @return this {@code Cell}'s {@link Set} of possible numbers
	 */
	public Set<Integer> getPossibleNumbers() {
		return possibleNumbers;
	}

	/**
	 * If this {@code Cell} is locked, this means that no further changes can be
	 * made to it.
	 * 
	 * @return {@code true} if this {@code Cell} is locked; {@code false}
	 *         otherwise
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Locks this {@code Cell}, meaning that no further changes can be made once
	 * this method has been called.
	 */
	public void lock() {
		locked = true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(position, number, possibleNumbers, locked);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (locked != other.locked)
			return false;
		if (number != other.number)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (possibleNumbers == null) {
			if (other.possibleNumbers != null)
				return false;
		} else if (!possibleNumbers.equals(other.possibleNumbers))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cell [position=" + position + ", number=" + number + ", possibleNumbers=" + possibleNumbers
				+ ", locked=" + locked + "]";
	}
}