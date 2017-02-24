package de.isse.alumni.muenchner_kindl_buam.cache.data;

import java.util.ArrayList;
import java.util.List;

public class Allocation {
	/**
	 * C x V
	 */
	private final int[][] allocation;
	private final long[] usedCapacityPerCache;
	private final boolean[] allocationPerVideo;
	private Input input;

	public Allocation(Input input) {
		this.input = input;
		this.allocation = new int[input.getC()][input.getV()];
		this.usedCapacityPerCache = new long[input.getC()];
		this.allocationPerVideo = new boolean[input.getV()];
	}

	public void allocateTo(int video, int cache) {
		if (getUsedCapacity(cache) + input.getVideoSize(video) > input.getX()) {
			throw new IllegalStateException(video + " " + cache);
		}
		if (allocation[cache][video] > 0) {
			throw new IllegalStateException(video + " " + cache);
		}

		allocation[cache][video] = input.getVideoSize(video);
		usedCapacityPerCache[cache] += input.getVideoSize(video);
		allocationPerVideo[video] = true;
	}

	public long getScore() {
		long latencies = 0;
		long counts = 0;
		for (int e = 0; e < input.getE(); e++) {
			for (int v = 0; v < input.getV(); v++) {

				int dcLatency = input.getDcLatency(e);
				int minLatency = dcLatency;
				for (int c = 0; c < input.getC(); c++) {
					int eToCLatency = input.getLatency(e, c);
					if (eToCLatency > 0 && allocation[c][v] > 0 && eToCLatency < minLatency) {
						minLatency = eToCLatency;
					}
				}
				long diff = dcLatency - minLatency;

				long count = input.getRequestCount(e, v);
				latencies += count * diff;

				counts += count;
			}
		}

		return latencies * 1000 / counts;

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
		return usedCapacityPerCache[cache];
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

	public boolean isAllocated(int video) {
		return allocationPerVideo[video];
	}

	public boolean isAllocated(int video, int cache) {
		return allocation[cache][video] > 0;
	}

	public boolean isValid() {
		for (int i = 0; i < input.getC(); i++) {
			if (getUsedCapacity(i) > input.getX()) {
				return false;
			}
		}
		return true;
	}

}
