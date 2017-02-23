package de.isse.alumni.muenchner_kindl_buam.pizza;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class Params {
	private final int rows;
	private final int cols;
	private final int ingredientsPerSlice;
	private final int maxSliceSize;
	private final Ingredient[][] pizza;
}

enum Ingredient {
	Tomato, Mushroom
};