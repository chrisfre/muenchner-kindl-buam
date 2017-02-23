package de.isse.alumni.muenchner_kindl_buam.pizza;

import java.net.URI;

public class Pizza {

	public static void main(String[] args) throws Throwable {
		new Pizza().run();
	}

	void run() throws Throwable {
		final URI inputFile = getClass().getClassLoader().getResource("pizza/example.in").toURI();
		final ProblemSetReader reader = new ProblemSetReader(inputFile);
		final Params params = reader.parseInputFile();
		
		System.out.println(params);
	}
}
