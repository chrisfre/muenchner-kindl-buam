package de.isse.alumni.muenchner_kindl_buam.output;

import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.isse.alumni.muenchner_kindl_buam.cache.ProblemSetReader;
import de.isse.alumni.muenchner_kindl_buam.cache.data.Allocation;
import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;
import de.isse.alumni.muenchner_kindl_buam.cache.output.AllocationWriter;

public class AllocationTest {
	private static final String exampleFilePath = "example.in";
	private Allocation allocation;

	@Test
	public void exampleTest1() throws Exception {
		AllocationWriter.write(allocation, "example1.out");
	}

	@Test
	public void exampleTest2() throws Exception {
		allocation.allocateTo(2, 0);
		allocation.allocateTo(3, 1);
		allocation.allocateTo(1, 1);
		allocation.allocateTo(0, 2);
		allocation.allocateTo(1, 2);

		AllocationWriter.write(allocation, "example2.out");

		Assert.assertEquals(allocation.getScore(), 462500);
		Assert.assertTrue(allocation.isValid());
	}

	@Before
	public void setup() throws Exception {
		final URI inputFile = getClass().getClassLoader().getResource(exampleFilePath).toURI();
		ProblemSetReader reader = new ProblemSetReader(inputFile);
		Input input = reader.parseInputFile();

		allocation = new Allocation(input);
	}

}
