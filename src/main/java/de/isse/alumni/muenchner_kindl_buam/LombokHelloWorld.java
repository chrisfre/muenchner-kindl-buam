package de.isse.alumni.muenchner_kindl_buam;

import lombok.Data;

public @Data class LombokHelloWorld {
	private String member;
	private int age;

	public LombokHelloWorld(String member, int age) {
		super();
		this.member = member;
		this.age = age;
	}
}
