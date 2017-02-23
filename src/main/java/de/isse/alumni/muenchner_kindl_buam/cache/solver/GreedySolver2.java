package de.isse.alumni.muenchner_kindl_buam.cache.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Allocation;
import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;
import lombok.Value;

public class GreedySolver2 implements Solver {
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

		prep.process(input);

		List<ECTriple> sortedLatencies = new ArrayList<>();
		for (int e = 0; e < input.getE(); e++) {
			if (!prep.isRelevantEndpoint(e)) {
				System.out.println("Skipping irrelevant endpoint: " + e);
				continue;
			}

			for (int c = 0; c < input.getC(); c++) {
				sortedLatencies.add(new ECTriple(e, c, input.getLatency(e, c)));
			}
		}
		Collections.sort(sortedLatencies, Comparator.comparing(ECTriple::getLatency));

		List<EVTriple> sortedRequests = new ArrayList<>();
		for (int e = 0; e < input.getE(); e++) {
			if (!prep.isRelevantEndpoint(e)) {
				System.out.println("Skipping irrelevant endpoint: " + e);
				continue;
			}
			for (int v = 0; v < input.getV(); v++) {
				if (!prep.isRelevantVideo(v)) {
					System.out.println("Skipping irrelevant video: " + v);
					continue;
				}
				sortedRequests.add(new EVTriple(e, v, input.getRequest(e, v) * input.getDcLink(e)));
			}
		}
		Collections.sort(sortedRequests, Comparator.comparing(EVTriple::getCount));

		for (EVTriple req : sortedRequests) {
			boolean isAlreadyAllocated = false;
			for (int c = 0; c < input.getC(); c++) {
				if (result.isAllocated(req.getVideo(), c)) {
					isAlreadyAllocated = true;
					continue;
				}
			}

			if (isAlreadyAllocated) {
				continue;
			}

			for (ECTriple lat : sortedLatencies) {
				if (req.getEndpoint() == lat.getEndpoint()) {
					if (result.getUsedCapacity(lat.getCache()) + input.getVideoSize(req.getVideo()) <= input.getX()) {
						result.allocateTo(req.getVideo(), lat.getCache());
						break;
					}
				}
			}
		}

		for (EVTriple req : sortedRequests) {
			for (ECTriple lat : sortedLatencies) {
				if (req.getEndpoint() == lat.getEndpoint()) {
					if (result.getUsedCapacity(lat.getCache()) + input.getVideoSize(req.getVideo()) <= input.getX()) {
						result.allocateTo(req.getVideo(), lat.getCache());
						break;
					}
				}
			}
		}

		return result;
	}
}
