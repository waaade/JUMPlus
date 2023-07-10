package com.cognixia.FurnitureAPI.exception;

public class OutOfStockException extends Exception {
	private static final long serialVersionUID = 1L;

	public OutOfStockException() {
		super("The item you requested is out of stock.");
	}

}
