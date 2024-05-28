package com.github.creme332.utils.exception;

public class InvalidIconSizeException extends Exception {
    public InvalidIconSizeException(String message) {
        super(message);
    }

    public InvalidIconSizeException(String message, Throwable cause) {
        super(message, cause);
    }
}