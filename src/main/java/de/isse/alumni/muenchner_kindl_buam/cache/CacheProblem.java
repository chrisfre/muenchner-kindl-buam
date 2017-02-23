package de.isse.alumni.muenchner_kindl_buam.cache;

import java.net.URI;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Allocation;
import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;
import de.isse.alumni.muenchner_kindl_buam.cache.output.AllocationWriter;

public class CacheProblem {

	private static String problem = "me_at_the_zoo";

	public static void main(String[] args) throws Exception {
		final CacheProblem problem = new CacheProblem();
		problem.run();

	}

	void run() throws Exception {
		final URI problemFile = getClass().getClassLoader().getResource("datacenters/" + problem + ".in").toURI();
		final ProblemSetReader reader = new ProblemSetReader(problemFile);
		final Input input = reader.parseInputFile();

		Allocation allocation = new Allocation(input);

		AllocationWriter.write(allocation, problem + ".out");
	}
}
