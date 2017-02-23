package de.isse.alumni.muenchner_kindl_buam.cache;

import java.net.URI;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;

public class CacheProblem {

	public static void main(String[] args) throws Exception {
		final CacheProblem problem = new CacheProblem();
		problem.run();

	}

	void run() throws Exception {
		final URI problemFile = getClass().getClassLoader().getResource("datacenters/example.in").toURI();
		final ProblemSetReader reader = new ProblemSetReader(problemFile);
		final Input input = reader.parseInputFile();

		System.out.println(input);
	}
}
