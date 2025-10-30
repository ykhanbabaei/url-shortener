package org.softmind.urlshortener.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softmind.urlshortener.exception.AlreadyRegisteredException;
import org.softmind.urlshortener.exception.NotFoundException;
import org.softmind.urlshortener.exception.SaveException;
import org.softmind.urlshortener.exception.UrlNullException;
import org.softmind.urlshortener.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Controller
public class UrlShortenerWebController {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerWebController.class);

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerWebController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/")
    public String registerForm(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public CompletableFuture<String> urlSubmit(@ModelAttribute("url") String url, Model model) {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return urlShortenerService.register(url).thenApply(code-> prepareRegisterModel(code, model, baseUrl));
    }

    private String prepareRegisterModel(String code, Model model, String baseUrl) {
        model.addAttribute("surl", baseUrl + "/" + code);
        return "result";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{code}")
    public CompletableFuture<ResponseEntity<Void>> findUrlAndRedirect(@PathVariable("code") String code){
        return urlShortenerService.findUrl(code).thenApply(url->ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url)).build());
    }

    @ExceptionHandler(SaveException.class)
    public ModelAndView handleSaveException(SaveException e){
        logger.error("error in url registration", e);
        return createErrorModelAndView("Error in url registration.");
    }

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ModelAndView handleRegisterException(AlreadyRegisteredException e){
        logger.warn("url already registered ", e);
        return createErrorModelAndView("url already registered");
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(NotFoundException e){
        logger.warn("url not found ", e);
        return createErrorModelAndView("url not found");
    }

    @ExceptionHandler(UrlNullException.class)
    public ModelAndView handleUrlNullException(UrlNullException e){
        logger.warn("url is null or empty", e);
        return createErrorModelAndView("url is null or empty");
    }

    private static ModelAndView createErrorModelAndView(String errorDescription){
        ModelAndView mav = new ModelAndView();
        mav.addObject("description", errorDescription);
        mav.setViewName("error");
        return mav;
    }

}
