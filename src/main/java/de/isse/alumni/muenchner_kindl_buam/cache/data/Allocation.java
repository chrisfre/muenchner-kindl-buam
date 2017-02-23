package de.isse.alumni.muenchner_kindl_buam.cache.data;

import java.util.ArrayList;
import java.util.List;

public class Allocation {
	/**
	 * C x V
	 */
	private final int[][] allocation;
	private Input input;

	private long counts = 0;
	private long latencies = 0;

	private int[][] endPointVideoDiff;

	public Allocation(Input input) {
		this.allocation = new int[input.getC()][input.getV()];
		this.input = input;

		endPointVideoDiff = new int[input.getE()][input.getV()];

		for (int e = 0; e < input.getE(); e++) {
			for (int v = 0; v < input.getV(); v++) {
				counts += input.getRequest(e, v);
				endPointVideoDiff[e][v] = 0;
			}
		}

	}

	public void allocateTo(int video, int cache) {
		if (getUsedCapacity(cache) + input.getVideoSize(video) > input.getX()) {
			throw new IllegalStateException(video + " " + cache);
		}

		allocation[cache][video] = input.getVideoSize(video);

		for (int e = 0; e < input.getE(); e++) {
			if (input.getLatency(e, cache) == 0) {
				continue;
			}
			if (input.getRequest(e, video) > 0) {
				int oldDiff = endPointVideoDiff[e][video];
				int newDiff = input.getDcLink(e) - input.getLatency(e, cache);
				int count = input.getRequest(e, video);

				if (oldDiff < newDiff) {
					latencies = latencies + (newDiff - oldDiff) * (long) count;
					endPointVideoDiff[e][video] = newDiff;
				}
			}
		}
	}

	public long getOldScore() {
		long latencies = 0;
		long counts = 0;
		for (int e = 0; e < input.getE(); e++) {
			for (int v = 0; v < input.getV(); v++) {
				int count = input.getRequest(e, v);
				if (count > 0) {
					// a valid request description
					int dcLatency = input.getDcLink(e);
					int minLatency = dcLatency;
					for (int c = 0; c < input.getC(); c++) {
						int eToCLatency = input.getLatency(e, c);
						if (allocation[c][v] > 0 && eToCLatency > 0 && minLatency > eToCLatency) {
							minLatency = eToCLatency;
						}
					}
					int diff = dcLatency - minLatency;

					assert diff >= 0;

					latencies += (long) count * (long) diff;
					counts += count;
				}
			}
		}

		return latencies * 1000 / counts;
	}

	public long getScore() {
		long result = (latencies * 1000) / counts;
		System.err.println(result);
		System.err.println(getOldScore());
		return result;
	}

	public List<Integer> getUsedCacheServers() {
		List<Integer> result = new ArrayList<>(input.getC());
		for (int c = 0; c < input.getC(); c++) {
			if (getUsedCapacity(c) > 0) {
				result.add(c);
			}
		}
		return result;
	}

	public long getUsedCapacity(int cache) {
		long result = 0;
		for (int i = 0; i < input.getV(); i++) {
			result += allocation[cache][i];
		}
		return result;
	}

	public List<Integer> getVideos(int cache) {
		List<Integer> result = new ArrayList<>(input.getV());

		for (int v = 0; v < input.getV(); v++) {
			if (allocation[cache][v] > 0) {
				result.add(v);
			}
		}

		return result;
	}

	public boolean isAllocated(int video, int cache) {
		return allocation[cache][video] > 0;
	}

	public boolean isValid() {
		// from 1 since 0 is data center
		for (int i = 1; i < input.getC(); i++) {
			if (getUsedCapacity(i) > input.getX()) {
				return false;
			}
		}
		return true;
	}
}
