package lowe.mike.strimko.model;

import org.junit.Test;

/**
 * @author Mike Lowe
 */
public final class StreamValidatorTest {

	private static final int[][] validStreams = new int[3][3];
	private static final int[][] nonSetStreams = new int[3][3];
	private static final int[][] unevenStreams = new int[3][3];
	private static final int[][] nonConnectedStreams = new int[3][3];

	static {
		validStreams[0][0] = 1;
		validStreams[0][1] = 2;
		validStreams[0][2] = 1;
		validStreams[1][0] = 2;
		validStreams[1][1] = 1;
		validStreams[1][2] = 2;
		validStreams[2][0] = 3;
		validStreams[2][1] = 3;
		validStreams[2][2] = 3;

		unevenStreams[0][0] = 1;
		unevenStreams[0][1] = 1;
		unevenStreams[0][2] = 1;
		unevenStreams[1][0] = 1;
		unevenStreams[1][1] = 1;
		unevenStreams[1][2] = 1;
		unevenStreams[2][0] = 2;
		unevenStreams[2][1] = 2;
		unevenStreams[2][2] = 3;

		nonConnectedStreams[0][0] = 3;
		nonConnectedStreams[0][1] = 1;
		nonConnectedStreams[0][2] = 1;
		nonConnectedStreams[1][0] = 2;
		nonConnectedStreams[1][1] = 2;
		nonConnectedStreams[1][2] = 2;
		nonConnectedStreams[2][0] = 1;
		nonConnectedStreams[2][1] = 3;
		nonConnectedStreams[2][2] = 3;
	}

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