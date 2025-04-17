package com.hexaware.exception;

public class AssetNotFoundException extends Exception {
	private static final long serialVersionUID = 2L;
    /**
     * Constructs a new AssetNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public AssetNotFoundException(String message) {
        super(message);
    }
}