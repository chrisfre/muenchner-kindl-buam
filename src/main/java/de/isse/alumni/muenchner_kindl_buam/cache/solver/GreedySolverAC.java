package de.isse.alumni.muenchner_kindl_buam.cache.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Allocation;
import de.isse.alumni.muenchner_kindl_buam.cache.data.CacheLatency;
import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;
import de.isse.alumni.muenchner_kindl_buam.cache.data.RequestScoring;
import lombok.Value;

public class GreedySolverAC implements Solver {
	@Value
	class Criterion {
		private final int video;
		private final int endpoint;
		private final int cache;

		public int getWeight(Input input) {
			return (input.getDcLatency(endpoint) - input.getLatency(endpoint, cache))
					* input.getRequestCount(endpoint, video);
		}
	}

	private final Preprocessing prep = new Preprocessing();

	@Override
	public Allocation solve(Input input) {
		final Allocation allocation = new Allocation(input);

		prep.process(input);

		// List: index = endpoint, value = list of cache x latency to endpoint
		List<List<CacheLatency>> sortedLatencies = new ArrayList<>(input.getE());
		// List of requests (e x v x scoring) sorted by scoring
		List<RequestScoring> sortedRequests = new ArrayList<>(input.getE());

		for (int e = 0; e < input.getE(); e++) {

			List<CacheLatency> latenciesPerEndpoint = new ArrayList<>();
			sortedLatencies.add(latenciesPerEndpoint);

			if (!prep.isRelevantEndpoint(e)) {
				System.out.println("Skipping irrelevant endpoint: " + e);
				continue;
			}

			for (int c = 0; c < input.getC(); c++) {

				// only add if reachable
				int latency = input.getLatency(e, c);
				if (latency > 0) {
					latenciesPerEndpoint.add(new CacheLatency(c, latency));
				}
			}
			Collections.sort(latenciesPerEndpoint,
					Collections.reverseOrder(Comparator.comparing(CacheLatency::getLatency)));

			for (int v = 0; v < input.getV(); v++) {
				if (!prep.isRelevantVideo(v)) {
					System.out.println("Skipping irrelevant video: " + v);
					continue;
				}

				// only add if requested
				int requestCount = input.getRequestCount(e, v);
				if (requestCount > 0) {
					// good for kittens to add / videosize
					sortedRequests.add(
							new RequestScoring(e, v, requestCount * input.getDcLatency(e) / input.getVideoSize(v)));
				}
			}
		}
		Collections.sort(sortedRequests, Collections.reverseOrder(Comparator.comparing(RequestScoring::getScoring)));

		List<RequestScoring> requestsNotAllocated = new ArrayList<>();
		for (RequestScoring req : sortedRequests) {
			// for kittens good not to allocate twice, but for
			// videos good to allocate twice
			 if (allocation.isAllocated(req.getVideo())) {
			 requestsNotAllocated.add(req);
			 continue;
			 }

			for (CacheLatency c : sortedLatencies.get(req.getEndpoint())) {
				// if video was already allocated at a good cache -> break
				if (allocation.isAllocated(req.getVideo(), c.getCache())) {
					break;
				}
				// if we found a cache which can still allocate the video
				// -> allocate&break
				if (allocation.getUsedCapacity(c.getCache()) + input.getVideoSize(req.getVideo()) <= input.getX()) {
					allocation.allocateTo(req.getVideo(), c.getCache());
					break;
				}
			}
			if (!allocation.isAllocated(req.getVideo())) {
				requestsNotAllocated.add(req);
			}
		}

		for (RequestScoring req : requestsNotAllocated) {
			for (CacheLatency c : sortedLatencies.get(req.getEndpoint())) {
				if (allocation.isAllocated(req.getVideo(), c.getCache())) {
					break;
				}
				if (allocation.getUsedCapacity(c.getCache())
						+ input.getVideoSize(req.getVideo()) <= input.getX()) {
					allocation.allocateTo(req.getVideo(), c.getCache());
					break;
				}
			}
		}

		return allocation;
	}
}
