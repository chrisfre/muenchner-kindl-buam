package de.isse.alumni.muenchner_kindl_buam.cache;

import java.net.URI;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Allocation;
import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;
import de.isse.alumni.muenchner_kindl_buam.cache.output.AllocationWriter;
import de.isse.alumni.muenchner_kindl_buam.cache.solver.GreedySolver2;
import de.isse.alumni.muenchner_kindl_buam.cache.solver.Solver;

public class CacheProblem {
	public static void main(String[] args) throws Exception {
		String problem = "kittens";
		if (args.length > 0) {
			problem = args[0];
		}
		System.out.println("Processing problem " + problem);

		final CacheProblem app = new CacheProblem();
		app.run(problem);
	}

	void run(String problem) throws Exception {
		final URI problemFile = getClass().getClassLoader().getResource("datacenters/" + problem + ".in").toURI();
		final ProblemSetReader reader = new ProblemSetReader(problemFile);
		final Input input = reader.parseInputFile();
		final Solver solver = new GreedySolver2();

		final Allocation allocation = solver.solve(input);
		final String resultFileName = String.format("%s_%s.out", problem, solver.getClass().getSimpleName());
		AllocationWriter.write(allocation, resultFileName);
	}
}
