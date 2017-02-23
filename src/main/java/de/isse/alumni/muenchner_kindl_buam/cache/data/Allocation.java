package de.isse.alumni.muenchner_kindl_buam.cache.data;

public class Allocation {
	private final boolean[][] allocation;

	public Allocation(Input input) {
		this.allocation = new boolean[input.getV()][input.getC()];
	}

	public void allocateTo(int video, int cache) {
		allocation[video][cache] = true;
	}

	public int getScore() {
		return 0;
	}

	public boolean isAllocated(int video, int cache) {
		return allocation[video][cache];
	}

	public boolean validate() {
		return false;
	}
}
