package org.softmind.urlshortener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softmind.urlshortener.exception.RegisterException;
import org.softmind.urlshortener.exception.UrlAlreadyRegistered;
import org.softmind.urlshortener.exception.UrlNotFoundException;
import org.softmind.urlshortener.model.UrlShortener;
import org.softmind.urlshortener.repository.UrlShortenerRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class UrlShortenerService {

    private static final int URL_LEN = 5;

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerService.class);

    private final UrlShortenerRepository urlShortenerRepository;

    public UrlShortenerService(UrlShortenerRepository urlShortenerRepository) {
        this.urlShortenerRepository = urlShortenerRepository;
    }

    public CompletableFuture<String> register(String url){
        return CompletableFuture.supplyAsync(()->handleRegister(url)).thenApply((UrlShortener::getCode));
    }

    private UrlShortener handleRegister(String url) {
        if(urlShortenerRepository.findByUrl(url).isPresent()){
            throw new UrlAlreadyRegistered(String.format("Url already registered: %s ", url));
        }
        try {
            UrlShortener urlShortener = urlShortenerRepository.save(new UrlShortener(null, url, createRandomCode(), LocalDate.now()));
            logger.info("new url registered. code: {}", urlShortener.getCode());
            return urlShortener;
        } catch (Exception e) {
            throw new RegisterException(String.format("Exception during url persistence: %s ", url), e);
        }
    }

    @Cacheable("urls")
    public CompletableFuture<String> findUrl(String code){
        return CompletableFuture.supplyAsync(()->handleUrl(code)).thenApply((UrlShortener::getUrl));
    }

    private UrlShortener handleUrl(String code){
        return urlShortenerRepository.findByCode(code).orElseThrow(()->new UrlNotFoundException(String.format("url for given code not found: %s ", code)));
    }

    private String createRandomCode() {
        return UUID.randomUUID().toString().substring(0, URL_LEN);
    }

}
