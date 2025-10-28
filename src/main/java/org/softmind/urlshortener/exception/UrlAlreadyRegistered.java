package org.softmind.urlshortener.exception;

public class UrlAlreadyRegistered extends RuntimeException {

    public UrlAlreadyRegistered(String msg) {
        super(msg);
    }
}
