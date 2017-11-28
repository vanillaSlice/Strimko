package lowe.mike.strimko.model.solver;

import lowe.mike.strimko.model.Difficulty;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;
import lowe.mike.strimko.model.Puzzle;

import java.util.Arrays;
import java.util.Collection;

import static java.util.Arrays.deepEquals;
import static java.util.Objects.hash;

/**
 * {@code SolvingResult} instances are intended to provide information from the
 * {@link Solver} after solving a {@link Grid}.
 * <p>
 * Stored information includes a {@link Grid}'s {@link Difficulty}, the solution
 * and a {@link Collection} of hints.
 *
 * @author Mike Lowe
 */
public final class SolvingResult {

    private final Difficulty difficulty;
    private final int[][] solution;
    private final Collection<Position> hints;

    /**
     * Creates a new {@code SolvingResult} given the {@link Difficulty}, the
     * solution and a {@link Collection} of hints.
     *
     * @param difficulty the {@link Difficulty}
     * @param solution   the solution
     * @param hints      the {@link Collection} of hints
     */
    public SolvingResult(Difficulty difficulty, int[][] solution, Collection<Position> hints) {
        this.difficulty = difficulty;
        this.solution = solution;
        this.hints = hints;
    }

    /**
     * @return the determined {@link Difficulty} of the {@link Puzzle}
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * @return the solution to the {@link Puzzle}
     */
    public int[][] getSolution() {
        return solution;
    }

    /**
     * @return a {@link Collection} of hints
     */
    public Collection<Position> getHints() {
        return hints;
    }

    @Override
    public int hashCode() {
        return hash(difficulty, solution, hints);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SolvingResult other = (SolvingResult) obj;
        if (difficulty != other.difficulty)
            return false;
        if (!deepEquals(solution, other.solution))
            return false;
        if (hints == null) {
            if (other.hints != null)
                return false;
        } else if (!hints.equals(other.hints))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SolvingResult [difficulty=" + difficulty + ", solution=" + Arrays.toString(solution) + ", hints="
                + hints + "]";
    }

}