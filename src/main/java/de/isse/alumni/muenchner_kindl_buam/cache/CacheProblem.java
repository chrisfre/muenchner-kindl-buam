package de.isse.alumni.muenchner_kindl_buam.cache;

import java.net.URI;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Allocation;
import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;
import de.isse.alumni.muenchner_kindl_buam.cache.solver.NopSolver;
import de.isse.alumni.muenchner_kindl_buam.cache.solver.Solver;

public class CacheProblem {

	public static void main(String[] args) throws Exception {
		final CacheProblem problem = new CacheProblem();
		problem.run();

	}

	void run() throws Exception {
		final URI problemFile = getClass().getClassLoader().getResource("datacenters/example.in").toURI();
		final ProblemSetReader reader = new ProblemSetReader(problemFile);
		final Solver solver = new NopSolver();

		final Input input = reader.parseInputFile();

		final Allocation allocation = solver.solve(input);

		System.out.println(input);
		System.out.println("------");

		for (int c = 0; c < input.getC(); ++c) {
			System.out.printf("Cache %d holds %s\n", c, allocation.getVideos(c));
		}
	}
}
