package de.isse.alumni.muenchner_kindl_buam.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Scanner;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;

public class ProblemSetReader {
	private final File inputFile;

	public ProblemSetReader(URI inputFile) {
		this.inputFile = Paths.get(inputFile).toFile();
	}

	public Input parseInputFile() {
		try (final BufferedReader in = new BufferedReader(new FileReader(inputFile))) {
			final Input.InputBuilder builder = Input.builder();
			final String headerLine = in.readLine();

			Scanner scanner = new Scanner(headerLine);
			final int V = scanner.nextInt();
			final int E = scanner.nextInt();
			final int R = scanner.nextInt();
			final int C = scanner.nextInt();
			final int X = scanner.nextInt();
			scanner.close();

			builder.V(V);
			builder.E(E);
			builder.R(R);
			builder.C(C);
			builder.X(X);

			scanner = new Scanner(in.readLine());
			int[] videoSize = new int[V];
			for (int v = 0; v < V; ++v) {
				videoSize[v] = scanner.nextInt();
			}
			scanner.close();
			builder.videoSize(videoSize);

			final int[] dcLinks = new int[E];
			final int[][] links = new int[E][C];
			for (int endpoint = 0; endpoint < E; ++endpoint) {
				final String line = in.readLine();
				scanner = new Scanner(line);

				final int dcLatency = scanner.nextInt();
				dcLinks[endpoint] = dcLatency;

				final int numCaches = scanner.nextInt();
				for (int nc = 0; nc < numCaches; ++nc) {
					final String cacheLine = in.readLine();
					final Scanner cacheLineScanner = new Scanner(cacheLine);

					// Cache ID
					final int cacheId = cacheLineScanner.nextInt();

					// Latency current EP -> current cache
					final int latency = cacheLineScanner.nextInt();

					links[endpoint][cacheId] = latency;

					cacheLineScanner.close();
				}
				scanner.close();
			}

			builder.latency(links);
			builder.dcLinks(dcLinks);

			final int[][] reqs = new int[E][V];
			for (int request = 0; request < R; ++request) {
				scanner = new Scanner(in.readLine());
				final int reqVideo = scanner.nextInt();
				final int reqEndpoint = scanner.nextInt();
				final int reqCount = scanner.nextInt();

				reqs[reqEndpoint][reqVideo] = reqCount;

				scanner.close();
			}
			builder.requests(reqs);

			return builder.build();
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
