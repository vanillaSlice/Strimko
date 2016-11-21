package lowe.mike.strimko.solver;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;

/**
 * {@code HiddenNMethod} represents the 'Hidden N' solving method.
 * <p>
 * If there are N number of {@link Cells} with N candidates between them that
 * don't appear elsewhere in the same group, then the other possible numbers can
 * be removed from these {@link Cells}.
 * <p>
 * Instances of {@code HiddenNMethod} cannot be created.
 * 
 * @author Mike Lowe
 */
final class HiddenNMethod {

	// don't want instances
	private HiddenNMethod() {
	}

	/**
	 * Runs 'Hidden N' method.
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
			// 'Hidden N'
			if (runMethod(groups, n, size, combination))
				return true;
		}

		return false;
	}

	private static boolean runMethod(Set<Set<Cell>> groups, int n, int size, Set<Integer> combination) {
		for (Set<Cell> group : groups) {
			// find cells containing numbers in combination and make a note of
			// all the numbers in those cells
			Set<Integer> foundNumbers = new HashSet<>();
			Set<Cell> cellsContainingCombination = new HashSet<>();
			for (Cell cell : group) {
				//contains at least one number in the combination
				if (!Collections.disjoint(combination, cell.getPossibleNumbers())) {
					cellsContainingCombination.add(cell);
					foundNumbers.addAll(cell.getPossibleNumbers());
				}
			}

			// check we have found N cells containing numbers from the
			// combination
			if (cellsContainingCombination.size() == n && foundNumbers.containsAll(combination)) {
				boolean changed = false;
				// remove numbers outside of the combination from these cells
				for (int number = 1; number <= size; number++)
					if (!combination.contains(number))
						for (Cell cellContainingCombination : cellsContainingCombination)
							if (cellContainingCombination.getPossibleNumbers().remove(number))
								changed = true;
				// return true only if possible numbers have been removed
				if (changed)
					return true;
			}
		}
		return false;
	}
}