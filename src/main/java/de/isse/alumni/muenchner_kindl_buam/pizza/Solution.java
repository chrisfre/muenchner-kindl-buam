package de.isse.alumni.muenchner_kindl_buam.pizza;

import java.util.Collection;

import lombok.Value;

@Value
public class Solution {
	private final Collection<Slice> slices;

	public boolean validate() {
		// TODO: Check if slices overlap
		return true;
	}

	public int getScore() {
		return slices.stream()
				.filter(Slice::isValid)
				.mapToInt(Slice::getScore)
				.sum();
	}
}
