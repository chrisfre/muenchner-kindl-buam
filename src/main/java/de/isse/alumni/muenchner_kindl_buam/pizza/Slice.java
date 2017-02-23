package de.isse.alumni.muenchner_kindl_buam.pizza;

import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(exclude = "params")
public class Slice {
	private final Params params;
	private final Rectangle coords;

	@Getter(lazy = true)
	private final int score = calculateScore();

	public int calculateScore() {
		return coords.getArea();
	}

	public boolean isValid() {
		System.out.println("[isValid] " + this);

		// Validate H constraint
		if (coords.getArea() > params.getMaxSliceSize()) {
			System.out.println("H constraint violated");
			return false;
		}

		int countM = 0;
		int countT = 0;
		for (int row = coords.getTop(); row <= coords.getBottom(); ++row) {
			for (int col = coords.getLeft(); col <= coords.getRight(); ++col) {
				final Ingredient ingredient = params.getPizza()[row][col];
				if (ingredient == Ingredient.Tomato) {
					++countT;
				}
				else {
					++countM;
				}
			}
		}
		// validate L constraint
		if (countM < params.getIngredientsPerSlice() || countT < params.getIngredientsPerSlice()) {
			System.out.printf("L constraint violated, M=%d, T=%d\n", countM, countT);
			return false;
		}
		return true;
	}
}