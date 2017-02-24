package de.isse.alumni.muenchner_kindl_buam.cache.data;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(of = { "V", "E", "R", "C", "X" })
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

	/** Endpoint */
	private final int[] dcLinks;

	public int getDcLatency(int endpoint) {
		return dcLinks[endpoint];
	}

	public int getLatency(int endpoint, int cache) {
		return latency[endpoint][cache];
	}

	public int getRequestCount(int endpoint, int video) {
		return requests[endpoint][video];
	}

	public int getVideoSize(int video) {
		return videoSize[video];
	}
}
