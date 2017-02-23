package de.isse.alumni.muenchner_kindl_buam.cache.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

public class ProblemSetReader {
	private final File inputFile;

	public ProblemSetReader(URI inputFile) {
		this.inputFile = Paths.get(inputFile).toFile();
	}

	public Input parseInputFile() {
		try (final BufferedReader in = new BufferedReader(new FileReader(inputFile))) {
			// final Params.ParamsBuilder builder = new Params.ParamsBuilder();
			// final String headerLine = in.readLine();
			//
			// StringTokenizer tokenizer = new StringTokenizer(headerLine);
			// final int numRows = Integer.parseInt(tokenizer.nextToken());
			// final int numCols = Integer.parseInt(tokenizer.nextToken());
			// final int minIngredient =
			// Integer.parseInt(tokenizer.nextToken());
			// final int maxArea = Integer.parseInt(tokenizer.nextToken());

			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
