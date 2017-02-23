package de.isse.alumni.muenchner_kindl_buam.cache.solver;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Allocation;
import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;
import lombok.Value;

public class GreedySolver implements Solver {
	@Value
	class Criterion {
		private final int video;
		private final int endpoint;
		private final int cache;

		public int getWeight(Input input) {
			return (input.getDcLink(endpoint) - input.getLatency(endpoint, cache)) * input.getRequest(endpoint, video);
		}
	}

	private final Preprocessing prep = new Preprocessing();

	@Override
	public Allocation solve(Input input) {
		final Allocation result = new Allocation(input);

		final Comparator<Criterion> comp = new Comparator<Criterion>() {
			@Override
			public int compare(Criterion c1, Criterion c2) {
				final int score1 = c1.getWeight(input);
				final int score2 = c2.getWeight(input);

				return Integer.compare(score2, score1);
			}

		};

		System.out.println("Step 0: Preprocessing");
		prep.process(input);

		///// BUILD PRIORITY QUEUE ACCORDING TO LINK PRODUCTS (largest savings
		///// per video)
		final boolean[][] served = new boolean[input.getE()][input.getV()];

		System.out.println("Step 1: Build criteria");
		final Criterion[] crits = new Criterion[input.getE() * input.getV() * input.getC()];
		int count = 0;
		for (int e = 0; e < input.getE(); ++e) {
			if (!prep.isRelevantEndpoint(e)) {
				System.out.println("Skipping irrelevant endpoint: " + e);
				continue;
			}

			for (int v = 0; v < input.getV(); ++v) {
				if (!prep.isRelevantVideo(v)) {
					System.out.println("Skipping irrelevant video: " + v);
					continue;
				}

				for (int c = 0; c < input.getC(); ++c) {
					if (input.getLatency(e, c) == 0) {
						continue;
					}

					crits[count] = new Criterion(v, e, c);
					++count;
				}
			}
		}

		System.out.println("Step 2: Sort criteria");
		final SortedSet<Criterion> pq = new TreeSet<>(comp);
		for (int i = 0; i < count; ++i) {
			pq.add(crits[i]);
		}
		/////

		System.out.println("PQ --------------------------------");
		for (Criterion crit : pq) {
			System.out.printf("%s --> %d\n", crit, crit.getWeight(input));

			// Find out if we can put the video on the requested cache
			final boolean canAllocate = result.getUsedCapacity(crit.cache) + input.getVideoSize(crit.video) <= input
					.getX();
			if (canAllocate) {
				if (served[crit.endpoint][crit.video]) {
					System.out.printf("Already served video %d for EP %d\n", crit.video, crit.endpoint);
					continue;
				}
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
