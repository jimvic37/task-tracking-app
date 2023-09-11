package com.cognixia.jump.exception;

public class TeamOverflowException extends Exception {

	private static final long serialVersionUID = 1L;

	public TeamOverflowException() {
		super("You may not have a Pokémon team larger than 6 members!");
	}
}