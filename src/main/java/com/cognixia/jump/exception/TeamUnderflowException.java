package com.cognixia.jump.exception;

public class TeamUnderflowException extends Exception {

	private static final long serialVersionUID = 1L;

	public TeamUnderflowException() {
		super("Your team is empty!");
	}
}