package org.softmind.urlshortener.controller;

import org.softmind.urlshortener.dto.UrlDto;
import org.softmind.urlshortener.service.UrlShortenerService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "api/register")
    public CompletableFuture<String> register(@RequestBody UrlDto urlDto){
        return urlShortenerService.register(urlDto.url());
    }

    @RequestMapping(method = RequestMethod.GET, path = "api/{code}")
    public CompletableFuture<String> findUrl(@PathVariable("code") String code){
        return urlShortenerService.findUrl(code);
    }

}
