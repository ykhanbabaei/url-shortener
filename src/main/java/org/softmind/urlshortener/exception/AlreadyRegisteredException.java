package org.softmind.urlshortener.exception;

public class AlreadyRegisteredException extends RuntimeException {

    public AlreadyRegisteredException(String msg) {
        super(msg);
    }
}
