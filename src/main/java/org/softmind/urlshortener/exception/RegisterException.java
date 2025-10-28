package org.softmind.urlshortener.exception;

public class RegisterException extends RuntimeException{

    public RegisterException(String message, Exception e) {
        super(message, e);
    }

}
