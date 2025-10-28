package org.softmind.urlshortener.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleUrlNotFoundException(Exception e){
        logger.error("url not found ", e);
        return new ErrorResponse("Url not found", "The given url not registered. First register the url.", LocalDateTime.now());
    }

    @ExceptionHandler(UrlAlreadyRegistered.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleUrlAlreadyRegistered(Exception e){
        logger.warn("url already registered ", e);
        return new ErrorResponse("url already registered", "The provided url already registered.", LocalDateTime.now());
    }

    @ExceptionHandler(RegisterException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse handleRegisterException(Exception e){
        logger.error("error in url registration ", e);
        return new ErrorResponse("url register error", "Error happened during the registration of the url. Try again later.", LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse handleDefaultException(Exception e){
        logger.error("Unknown error happened. Check the log for more information.", e);
        return new ErrorResponse("Unknown error", "Unknown error happened. Try again later.", LocalDateTime.now());
    }

}
