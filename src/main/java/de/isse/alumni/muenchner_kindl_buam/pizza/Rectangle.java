package de.isse.alumni.muenchner_kindl_buam.pizza;

import lombok.Value;

@Value
public class Rectangle {
	private final int top, left, right, bottom;

	public int getArea() {
		return (bottom - top + 1) * (right - left + 1);
	}
}
