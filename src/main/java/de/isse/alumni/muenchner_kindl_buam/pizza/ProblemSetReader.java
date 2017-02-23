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
			final int numRows = Integer.parseInt(tokenizer.nextToken());
			final int numCols = Integer.parseInt(tokenizer.nextToken());
			final int minIngredient = Integer.parseInt(tokenizer.nextToken());
			final int maxArea = Integer.parseInt(tokenizer.nextToken());
			builder.rows(numRows);
			builder.cols(numCols);
			builder.ingredientsPerSlice(minIngredient);
			builder.maxSliceSize(maxArea);
			
			builder.pizza(new Ingredient[numRows][numCols]);

			final Params result = builder.build();

			String line;
			int row = 0;
			while ((line = in.readLine()) != null) {
				final int[] chars = line.chars().toArray();
				int col = 0;
				for (int ch : chars) {
					result.setIngredient(row, col, Ingredient.fromChar((char) ch));
					col++;
				}
				row++;
			}

			return result;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
