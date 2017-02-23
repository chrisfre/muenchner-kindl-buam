package de.isse.alumni.muenchner_kindl_buam.cache.output;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Allocation;

public class AllocationWriter {
	public static void write(Allocation a, String fileName) throws IOException {
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName))) {
			List<Integer> usedCacheServers = a.getUsedCacheServers();
			bw.write(String.valueOf(usedCacheServers.size()));
			bw.newLine();

			for (int c : usedCacheServers) {
				bw.write(String.valueOf(c));
				bw.write(" ");
				for (int v : a.getVideos(c)) {
					bw.write(String.valueOf(v));
					bw.write(" ");
				}
				bw.newLine();
			}
		}
	}
}
