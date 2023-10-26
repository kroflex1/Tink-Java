package edu.hw2.Task3.Exceptions;

public class ConnectionException extends RuntimeException {

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable e) {
        super(message, e);
    }
}
