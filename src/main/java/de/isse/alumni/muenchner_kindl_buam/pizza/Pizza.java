package de.isse.alumni.muenchner_kindl_buam.pizza;

import java.net.URI;
import java.util.Arrays;

public class Pizza {

	public static void main(String[] args) throws Throwable {
		new Pizza().run();
	}

	void run() throws Throwable {
		final URI inputFile = getClass().getClassLoader().getResource("pizza/example.in").toURI();
		final ProblemSetReader reader = new ProblemSetReader(inputFile);
		final Params params = reader.parseInputFile();
		System.out.println(params);

		final Slice slice1 = new Slice(params, new Rectangle(0, 0, 1, 1));
		final Slice slice2 = new Slice(params, new Rectangle(2, 2, 3, 2));
		final Solution solution = new Solution(Arrays.asList(slice1, slice2));
		System.out.println(solution);
		System.out.println(solution.getScore());

	}
}
