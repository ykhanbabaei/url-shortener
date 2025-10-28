package org.softmind.urlshortener.exception;

public class UrlNotFoundException extends RuntimeException{

    public UrlNotFoundException(String msg) {
        super(msg);
    }

}
