package org.softmind.urlshortener.controller;

import org.softmind.urlshortener.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Controller
public class UrlShortenerWebController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerWebController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{code}")
    public CompletableFuture<ResponseEntity<Void>> findUrl(@PathVariable("code") String code){
        return urlShortenerService.findUrl(code).thenApply(this::redirectResponse);
    }

    private ResponseEntity<Void> redirectResponse(String url) {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }

}
