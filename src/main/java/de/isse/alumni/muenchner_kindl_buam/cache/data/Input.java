package de.isse.alumni.muenchner_kindl_buam.cache.data;

import lombok.Value;

@Value
public class Input {
	/** Number of videos */
	private final int V;

	/** Number of endpoints */
	private final int E;

	/** Number of req. descriptions */
	private final int R;

	/** Number of caches */
	private final int C;

	/** Capacity of cache servers */
	private final int X;

	/** Endpoint x Video */
	private final int[][] requests;

	/** Endpoint x Cache */
	private final int[][] latency;

	/** Video */
	private final int[] videoSize;

	public int getLatency(int endpoint, int cache) {
		return latency[endpoint][cache];
	}

	public int getRequest(int endpoint, int video) {
		return requests[endpoint][video];
	}

	public int getVideoSize(int video) {
		return videoSize[video];
	}
}
