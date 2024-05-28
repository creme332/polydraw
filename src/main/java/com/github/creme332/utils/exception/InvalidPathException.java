package com.github.creme332.utils.exception;

public class InvalidPathException extends Exception {
    public InvalidPathException(String message) {
        super(message);
    }

    public InvalidPathException(String message, Throwable cause) {
        super(message, cause);
    }
}