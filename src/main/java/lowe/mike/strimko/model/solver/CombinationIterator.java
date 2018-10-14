package lowe.mike.strimko.model.solver;

import lowe.mike.strimko.model.Grid;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * {@code CombinationIterator} instances create combinations of possible numbers
 * when solving a puzzle.
 * <p>
 * For example, given n = 2 and the size of the grid given as 3, the possible
 * combinations would be: [1, 2], [1, 3] and [2, 3].
 *
 * @author Mike Lowe
 */
final class CombinationIterator implements Iterator<Collection<Integer>> {

  private final int n;
  private final int size;
  private final int numberOfSubsets;
  private int cursor = 1;
  private Collection<Integer> nextCombination;

  /**
   * Creates a new {@code CombinationIterator} given the size of each
   * combination and size of the {@link Grid}.
   *
   * @param n    size of each combination
   * @param size size of the {@link Grid}
   */
  CombinationIterator(int n, int size) {
    this.n = n;
    this.size = size;
    this.numberOfSubsets = 1 << this.size;
    this.nextCombination = getNextCombination();
  }

  @Override
  public boolean hasNext() {
    return nextCombination != null;
  }

  @Override
  public Collection<Integer> next() {
    Collection<Integer> currentCombination = nextCombination;
    nextCombination = getNextCombination();
    return currentCombination;
  }

  private Collection<Integer> getNextCombination() {
    Collection<Integer> nextCombination = new HashSet<>();

    for (int i = 0; i < size; i++) {
      if ((cursor & (1 << i)) > 0) {
        nextCombination.add(i + 1);
      }
    }

    cursor++;

    // no more combinations left
    if (cursor > numberOfSubsets) {
      return null;
    }

    // if combination is not the correct size, find the next one
    if (nextCombination.size() != n) {
      return getNextCombination();
    }

    return nextCombination;
  }

}
