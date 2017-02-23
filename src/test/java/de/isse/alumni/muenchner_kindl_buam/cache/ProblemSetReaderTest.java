package de.isse.alumni.muenchner_kindl_buam.cache;

import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;

@RunWith(JUnit4.class)
public class ProblemSetReaderTest {
	private static final String exampleFilePath = "example.in";
	private ProblemSetReader reader;
	private Input input;

	@Before
	public void setUp() throws Exception {
		final URI inputFile = getClass().getClassLoader().getResource(exampleFilePath).toURI();
		reader = new ProblemSetReader(inputFile);
		input = reader.parseInputFile();
	}

	@Test
	public void testCacheLinks() {
		assertArrayEquals(new int[] { 100, 300, 200 }, input.getLatency()[0]);
		assertArrayEquals(new int[] { 0, 0, 0 }, input.getLatency()[1]);
	}

	@Test
	public void testConstants() {
		assertEquals(5, input.getV());
		assertEquals(2, input.getE());
		assertEquals(4, input.getR());
		assertEquals(3, input.getC());
		assertEquals(100, input.getX());
	}

	@Test
	public void testDcLinks() {
		assertArrayEquals(new int[] { 1000, 500 }, input.getDcLinks());
	}

	public void testRequests() {
		assertArrayEquals(new int[] { 0, 1000, 0, 1500, 500 }, input.getRequests()[0]);
		assertArrayEquals(new int[] { 1000, 0, 0, 0, 0 }, input.getRequests()[1]);
	}
}
