package de.isse.alumni.muenchner_kindl_buam.pizza;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class ProblemSetReader {
	private final File inputFile;

	public ProblemSetReader(URI inputFile) {
		this.inputFile = Paths.get(inputFile).toFile();
	}
	
	public Params parseInputFile() {
		try (final BufferedReader in = new BufferedReader(new FileReader(inputFile))) {
			final Params.ParamsBuilder builder = new Params.ParamsBuilder();
			final String headerLine = in.readLine();
			
			StringTokenizer tokenizer = new StringTokenizer(headerLine);
			builder.rows(Integer.parseInt(tokenizer.nextToken()));
			builder.cols(Integer.parseInt(tokenizer.nextToken()));
			builder.ingredientsPerSlice(Integer.parseInt(tokenizer.nextToken()));
			builder.maxSliceSize(Integer.parseInt(tokenizer.nextToken()));
			
			return builder.build();
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
