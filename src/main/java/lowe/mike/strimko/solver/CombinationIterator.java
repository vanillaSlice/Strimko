package lowe.mike.strimko.solver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import lowe.mike.strimko.model.Grid;

/**
 * {@code CombinationIterator} instances create combinations of possible numbers
 * when solving a puzzle.
 * <p>
 * For example, given n = 2 and the size of the grid given as 3, the possible
 * combinations would be: [1, 2], [1, 3] and [2, 3].
 * 
 * @author Mike Lowe
 */
final class CombinationIterator implements Iterator<Set<Integer>> {
	private final int n;
	private final int size;
	private final int numberOfSubsets;
	private int current = 1;

	/**
	 * Creates a new {@code CombinationIterator} given the size of combinations
	 * and size of the {@link Grid}.
	 * 
	 * @param n
	 *            size of combinations
	 * @param size
	 *            size of the {@link Grid}
	 */
	CombinationIterator(int n, int size) {
		this.n = n;
		this.size = size;
		this.numberOfSubsets = 1 << this.size;
	}

	@Override
	public boolean hasNext() {
		return current < numberOfSubsets;
	}

	@Override
	public Set<Integer> next() {
		Set<Integer> subset = new HashSet<>();

		for (int i = 0; i < size; i++)
			if ((current & (1 << i)) > 0)
				subset.add(i + 1);

		current++;

		// if combination is not the correct size, find the next one
		if (subset.size() != n)
			return next();

		return subset;
	}
}