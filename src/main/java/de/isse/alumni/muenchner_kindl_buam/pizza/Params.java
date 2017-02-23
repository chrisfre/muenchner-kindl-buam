package de.isse.alumni.muenchner_kindl_buam.pizza;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(exclude = "pizza")
public class Params {
	private final int rows;
	private final int cols;
	private final int ingredientsPerSlice;
	private final int maxSliceSize;
	private final Ingredient[][] pizza;

	public void setIngredient(int row, int col, Ingredient i) {
		pizza[row][col] = i;
	}
}

enum Ingredient {
	Tomato, Mushroom;

	public static Ingredient fromChar(char ch) {
		if (ch == 'T') {
			return Ingredient.Tomato;
		}
		else if (ch == 'M') {
			return Ingredient.Mushroom;
		}
		throw new IllegalArgumentException("Invalid ingredient character: " + ch);
	}
};