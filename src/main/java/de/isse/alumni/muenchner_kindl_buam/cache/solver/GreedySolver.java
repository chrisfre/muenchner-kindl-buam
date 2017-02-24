package de.isse.alumni.muenchner_kindl_buam.cache.solver;

import java.util.Arrays;
import java.util.Comparator;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Allocation;
import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;
import lombok.Data;

public class GreedySolver implements Solver {
	@Data
	class Criterion {
		private int video;
		private int endpoint;
		private int cache;

		public int getWeight(Input input) {
			return (input.getDcLatency(endpoint) - input.getLatency(endpoint, cache)) * input.getRequestCount(endpoint, video);
		}
	}

	private final Preprocessing prep = new Preprocessing();

	@Override
	public Allocation solve(Input input) {
		final Allocation result = new Allocation(input);

		System.out.println("Step 0: Preprocessing");
		prep.process(input);

		///// BUILD PRIORITY QUEUE ACCORDING TO LINK PRODUCTS (largest savings
		///// per video)
		final boolean[][] served = new boolean[input.getE()][input.getV()];

		System.out.println("Step 1: Build criteria");
		// final int[][] crits = new int[input.getE() * input.getV() *
		// input.getC()][3];

		final int len = input.getE() * input.getV() * input.getC();
		final int[] arrV = new int[len];
		final int[] arrE = new int[len];
		final int[] arrC = new int[len];

		int count = 0;
		for (int e = 0; e < input.getE(); ++e) {
			// if (!prep.isRelevantEndpoint(e)) {
			// System.out.println("Skipping irrelevant endpoint: " + e);
			// continue;
			// }

			for (int c = 0; c < input.getC(); ++c) {
				if (input.getLatency(e, c) == 0) {
					continue;
				}

				for (int v = 0; v < input.getV(); ++v) {
					// if (!prep.isRelevantVideo(v)) {
					// System.out.println("Skipping irrelevant video: " + v);
					// continue;
					// }

					arrV[count] = v;
					arrE[count] = e;
					arrC[count] = c;

					++count;
				}
			}
		}

		System.out.println("Step 2a: Creating indices");
		final Integer[] indices = new Integer[count];
		for (int i = 0; i < count; ++i) {
			indices[i] = i;
			// pq.add(crits[i]);
		}

		final Comparator<Integer> comp = new Comparator<Integer>() {
			@Override
			public int compare(Integer i1, Integer i2) {
				final int score1 = getWeight(i1);
				final int score2 = getWeight(i2);

				return Integer.compare(score2, score1);
			}

			public int getWeight(Integer i) {
				final int video = arrV[i];
				final int endpoint = arrE[i];
				final int cache = arrC[i];

				return (input.getDcLatency(endpoint) - input.getLatency(endpoint, cache))
						* input.getRequestCount(endpoint, video);
			}
		};

		System.out.println("Step 2b: Sorting indices");
		Arrays.sort(indices, comp);
		/////

		System.out.println("PQ --------------------------------");
		final Criterion crit = new Criterion();
		for (Integer i : indices) {
			crit.setVideo(arrV[i]);
			crit.setEndpoint(arrE[i]);
			crit.setCache(arrC[i]);

			// System.out.printf("%s --> %d\n", crit, crit.getWeight(input));

			if (served[crit.endpoint][crit.video]) {
				System.out.printf("Already served video %d for EP %d\n", crit.video, crit.endpoint);
				continue;
			}

			// Find out if we can put the video on the requested cache
			final boolean canAllocate = result.getUsedCapacity(crit.cache) + input.getVideoSize(crit.video) <= input
					.getX();
			if (canAllocate) {

				System.out.printf("Allocating video %d to cache %d (for EP %d)\n", crit.video, crit.cache,
						crit.endpoint);
				result.allocateTo(crit.video, crit.cache);
				served[crit.endpoint][crit.video] = true;

				System.out.printf("  (new cache utilization = %d / %d MB)\n", result.getUsedCapacity(crit.cache),
						input.getX());
			}
		}
		System.out.println("-----------------------------------");

		return result;
	}
}
