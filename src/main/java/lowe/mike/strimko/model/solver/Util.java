package lowe.mike.strimko.model.solver;

import java.util.HashSet;
import java.util.Set;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * {@code Util} provides utility methods that are used throughout the solving
 * process.
 * <p>
 * Instances of {@code Util} cannot be created.
 * 
 * @author Mike Lowe
 */
final class Util {

	// don't want instances
	private Util() {
	}

	static void setNumberAndUpdateGrid(Cell cell, int number, Grid grid) {
		cell.setNumber(number);
		grid.updatePossibleNumbers();
	}

	static void setNumberUpdateGridAndAddToHints(Cell cell, int number, Grid grid, Set<Position> hints) {
		setNumberAndUpdateGrid(cell, number, grid);
		hints.add(cell.getPosition());
	}

	static boolean cellContainsPossible(Cell cell, int number) {
		return cell.getPossibleNumbers().contains(number);
	}

	static Set<Cell> getCellsContainingPossible(Set<Cell> group, int number) {
		Set<Cell> cellsContainingPossible = new HashSet<>();

		for (Cell cell : group)
			if (cellContainsPossible(cell, number))
				cellsContainingPossible.add(cell);

		return cellsContainingPossible;
	}
}