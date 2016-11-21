package lowe.mike.strimko.model;

import org.junit.Test;

/**
 * @author Mike Lowe
 */
public final class StreamValidatorTest {

	private static final int[][] validStreams = { { 1, 2, 1 }, { 2, 1, 2 }, { 3, 3, 3 } };
	private static final int[][] nonSetStreams = new int[3][3];
	private static final int[][] unevenStreams = { { 1, 1, 1 }, { 1, 1, 1 }, { 2, 2, 3 } };
	private static final int[][] nonConnectedStreams = { { 3, 1, 1 }, { 2, 2, 2 }, { 1, 3, 3 } };

	/**
	 * should not throw exception to ensure validity
	 */
	@Test
	public void validStreamTest() {
		StreamValidator.validate(validStreams);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nonSetStreams() {
		StreamValidator.validate(nonSetStreams);
	}

	@Test(expected = IllegalArgumentException.class)
	public void unevenStreamsTest() {
		StreamValidator.validate(unevenStreams);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nonConnectedStreamsTest() {
		StreamValidator.validate(nonConnectedStreams);
	}
}