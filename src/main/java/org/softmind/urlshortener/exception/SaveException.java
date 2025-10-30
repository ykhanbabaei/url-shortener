package org.softmind.urlshortener.exception;

public class SaveException extends RuntimeException{

    public SaveException(String message, Exception e) {
        super(message, e);
    }

}
