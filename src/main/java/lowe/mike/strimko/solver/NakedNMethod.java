package lowe.mike.strimko.solver;

import java.util.HashSet;
import java.util.Set;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;

/**
 * {@code NakedNMethod} represents the 'Naked N' solving method.
 * <p>
 * If there are N number of {@link Cell}s in a group that have a combined number
 * of possibles equal to N, then we can remove these numbers from all other
 * {@link Cell}s in the group.
 * <p>
 * Instances of {@code NakedNMethod} cannot be created.
 * 
 * @author Mike Lowe
 */
final class NakedNMethod {

	// don't want instances
	private NakedNMethod() {
	}

	/**
	 * Runs 'Naked N' method.
	 * 
	 * @param grid
	 *            the {@link Grid} to run method over
	 * @param n
	 *            number of possible numbers that must be shared
	 * @return {@code true} if any changes where made to the {@link Grid},
	 *         {@code false} otherwise
	 */
	static boolean run(Grid grid, int n) {
		if (runOverRows(grid, n))
			return true;
		if (runOverColumns(grid, n))
			return true;
		if (runOverStreams(grid, n))
			return true;

		return false;
	}

	static boolean runOverRows(Grid grid, int n) {
		return run(grid.getRows(), n, grid.getSize());
	}

	static boolean runOverColumns(Grid grid, int n) {
		return run(grid.getColumns(), n, grid.getSize());
	}

	static boolean runOverStreams(Grid grid, int n) {
		return run(grid.getStreams(), n, grid.getSize());
	}

	private static boolean run(Set<Set<Cell>> groups, int n, int size) {
		CombinationIterator iterator = new CombinationIterator(n, size);

		while (iterator.hasNext()) {
			Set<Integer> combination = iterator.next();
			// check to see if this combination of possible numbers makes a
			// 'Naked N'
			if (runMethod(groups, n, size, combination))
				return true;
		}

		return false;
	}

	private static boolean runMethod(Set<Set<Cell>> groups, int n, int size, Set<Integer> combination) {
		for (Set<Cell> group : groups) {
			// find cells only containing numbers in combination and make a note
			// of all the numbers in those cells
			Set<Integer> foundNumbers = new HashSet<>();
			Set<Cell> cellsContainingCombination = new HashSet<>();
			for (Cell cell : group) {
				if (cellContainsOnly(cell, combination)) {
					cellsContainingCombination.add(cell);
					foundNumbers.addAll(cell.getPossibleNumbers());
				}
			}

			// check we have found N cells containing numbers from the
			// combination
			if (cellsContainingCombination.size() == n && foundNumbers.containsAll(combination)) {
				boolean changed = false;
				// remove combination from other cells in group
				for (Cell otherCell : group)
					if (!cellsContainingCombination.contains(otherCell))
						if (otherCell.getPossibleNumbers().removeAll(combination))
							changed = true;

				// return true only if possible numbers have been removed
				if (changed)
					return true;
			}
		}
		return false;
	}

	private static boolean cellContainsOnly(Cell cell, Set<Integer> possible) {
		Set<Integer> original = new HashSet<>(cell.getPossibleNumbers());
		if (original.size() == 0)
			return false;
		original.removeAll(possible);
		return original.size() == 0;
	}
}