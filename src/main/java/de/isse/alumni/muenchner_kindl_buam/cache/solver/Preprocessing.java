package de.isse.alumni.muenchner_kindl_buam.cache.solver;

import de.isse.alumni.muenchner_kindl_buam.cache.data.Input;

public class Preprocessing {
	private boolean[] relevantEndpoints;
	private boolean[] relevantVideos;

	public boolean[] getRelevantEndpoints() {
		return relevantEndpoints;
	}

	public boolean[] getRelevantVideos() {
		return relevantVideos;
	}

	public boolean isRelevantEndpoint(Integer e) {
		return relevantEndpoints[e];
	}

	public boolean isRelevantVideo(Integer v) {
		return relevantVideos[v];
	}

	public void process(Input input) {
		relevantEndpoints = new boolean[input.getE()];
		relevantVideos = new boolean[input.getV()];

		for (int e = 0; e < input.getE(); ++e) {
			for (int c = 0; c < input.getC(); ++c) {
				if (input.getLatency(e, c) > 0) {
					relevantEndpoints[e] = true;
				}
			}

			for (int v = 0; v < input.getV(); ++v) {
				if (input.getRequestCount(e, v) > 0) {
					relevantVideos[v] = true;
				}
			}
		}
	}
}
