package lowe.mike.strimko.model;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

/**
 * {@code StreamValidator} provides a method {@link #validate(int[][])} to
 * validate streams. This ensures that:
 * <ul>
 * <li>each {@link Cell} belongs to a stream</li>
 * <li>there are equal number of {@link Cell}s in each stream</li>
 * <li>{@link Cell}s in a stream are all joined together</li>
 * </ul>
 * <p>
 * Instances of {@code StreamValidator} cannot be created.
 */
public final class StreamValidator {

	// don't want instances
	private StreamValidator() {
	}

	/**
	 * Validates a streams array.
	 * 
	 * @param streams
	 *            array of streams
	 * @throws IllegalArgumentException
	 *             if the streams are invalid
	 */
	public static void validate(int[][] streams) {
		Multiset<Integer> streamsCount = getNumberOfCellsInEachStream(streams);
		checkAllCellsBelongToAStream(streamsCount);
		int size = streams.length;
		checkEqualNumberOfCellsInEachStream(streamsCount, size);
		checkCellsInStreamsAreJoined(streams, size);
	}

	private static Multiset<Integer> getNumberOfCellsInEachStream(int[][] streams) {
		Multiset<Integer> streamsCount = TreeMultiset.create();

		for (int[] row : streams)
			for (int stream : row)
				streamsCount.add(stream);

		return streamsCount;
	}

	private static void checkAllCellsBelongToAStream(Multiset<Integer> streamsCount) {
		checkArgument(streamsCount.count(0) == 0, "all cells must belong to a stream");
	}

	private static void checkEqualNumberOfCellsInEachStream(Multiset<Integer> streamsCount, int size) {
		for (int stream = 1; stream <= size; stream++)
			checkArgument(streamsCount.count(stream) == size, "each stream does not contain an equal number of cells");
	}

	private static void checkCellsInStreamsAreJoined(int[][] streams, int size) {
		boolean[][] visited = new boolean[size][size];

		for (int row = 0; row < size; row++)
			for (int column = 0; column < size; column++)
				if (!visited[row][column])
					checkCellsInStreamAreJoined(streams, size, visited, row, column);
	}

	private static void checkCellsInStreamAreJoined(int[][] streams, int size, boolean[][] visited, int row,
			int column) {
		int stream = streams[row][column];
		Multiset<Integer> connections = HashMultiset.create();
		countStreamConnections(streams, size, visited, row, column, stream, connections);
		checkArgument(connections.count(stream) == size, "cells in a stream must be connected");
	}

	private static void countStreamConnections(int[][] streams, int size, boolean visited[][], int row, int column,
			int stream, Multiset<Integer> connections) {
		if (streams[row][column] == stream && !visited[row][column]) {
			visited[row][column] = true;
			connections.add(stream);

			// top left
			if (continueVisiting(streams, visited, row - 1, column - 1, stream, size))
				countStreamConnections(streams, size, visited, row - 1, column - 1, stream, connections);
			// top
			if (continueVisiting(streams, visited, row - 1, column, stream, size))
				countStreamConnections(streams, size, visited, row - 1, column, stream, connections);
			// top right
			if (continueVisiting(streams, visited, row - 1, column + 1, stream, size))
				countStreamConnections(streams, size, visited, row - 1, column + 1, stream, connections);
			// right
			if (continueVisiting(streams, visited, row, column + 1, stream, size))
				countStreamConnections(streams, size, visited, row, column + 1, stream, connections);
			// below right
			if (continueVisiting(streams, visited, row + 1, column + 1, stream, size))
				countStreamConnections(streams, size, visited, row + 1, column + 1, stream, connections);
			// below
			if (continueVisiting(streams, visited, row + 1, column, stream, size))
				countStreamConnections(streams, size, visited, row + 1, column, stream, connections);
			// below left
			if (continueVisiting(streams, visited, row + 1, column - 1, stream, size))
				countStreamConnections(streams, size, visited, row + 1, column - 1, stream, connections);
			// left
			if (continueVisiting(streams, visited, row, column - 1, stream, size))
				countStreamConnections(streams, size, visited, row, column - 1, stream, connections);
		}
	}

	private static boolean continueVisiting(int[][] streams, boolean[][] visited, int row, int column, int stream,
			int size) {
		if (row < 0 || column < 0 || row >= size || column >= size)
			return false;
		return streams[row][column] == stream && !visited[row][column];
	}
}