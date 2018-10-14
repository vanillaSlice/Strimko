package lowe.mike.strimko.model;

import lowe.mike.strimko.model.Grid.GridBuilder;
import org.junit.Before;
import org.junit.Test;

import static lowe.mike.strimko.model.Type.STRIMKO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Mike Lowe
 */
public final class PuzzleTests {

  private Puzzle puzzle;

  @Before
  public void setup() {
    int size = 3;
    int[][] streams = {{1, 1, 2}, {1, 2, 3}, {2, 3, 3}};
    int[][] numbers = {{0, 0, 0}, {1, 3, 2}, {0, 0, 0}};
    Grid grid = new GridBuilder(size).setStreams(streams).setNumbers(numbers).build();
    puzzle = new Puzzle(STRIMKO, grid);
  }

  @Test
  public void test_getNextHint_firstHint() {
    // setup
    Cell expected = puzzle.getGrid().getCell(0, 1);

    // execution
    Cell result = puzzle.getNextHint();

    // verification
    assertEquals(expected, result);
  }

  @Test
  public void test_getNextHint_firstCellIncorrectNumber() {
    // setup
    Grid grid = puzzle.getGrid();
    grid.getCell(0, 1).setNumber(1);
    Cell expected = grid.getCell(0, 1);

    // execution
    Cell result = puzzle.getNextHint();

    // verification
    assertEquals(expected, result);
  }

  @Test
  public void test_getNextHint_secondHint() {
    // setup
    Grid grid = puzzle.getGrid();
    grid.getCell(0, 1).setNumber(2);
    Cell expected = grid.getCell(0, 0);

    // execution
    Cell result = puzzle.getNextHint();

    // verification
    assertEquals(expected, result);
  }

  @Test
  public void test_getNextHint_noMoreHints() {
    // setup
    Grid grid = puzzle.getGrid();
    grid.getCell(0, 0).setNumber(3);
    grid.getCell(0, 1).setNumber(2);
    grid.getCell(0, 2).setNumber(1);
    grid.getCell(2, 0).setNumber(2);
    grid.getCell(2, 1).setNumber(1);
    grid.getCell(2, 2).setNumber(3);

    // execution
    Cell result = puzzle.getNextHint();

    // verification
    assertNull(result);
  }

}
