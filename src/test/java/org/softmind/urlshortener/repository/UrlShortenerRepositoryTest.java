package org.softmind.urlshortener.repository;

import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.softmind.urlshortener.model.UrlShortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class UrlShortenerRepositoryTest {

    @Autowired
    private Validator validator;

    @Autowired
    UrlShortenerRepository urlShortenerRepository;

    @Test
    public void testSavingUrl(){
        UrlShortener persisted = urlShortenerRepository.save(new UrlShortener(null, "https://google.com", "abcd1", null));
        assertThat(persisted.getId()).isNotNull();
    }

    @Test
    public void testSavingNullUrl(){
        assertThrows(DataIntegrityViolationException.class,
                () ->  urlShortenerRepository.save(new UrlShortener(null, null, "abcd2", null)));
    }

    @Test
    public void testSavingComplicatedValidUrl(){
        UrlShortener persisted = urlShortenerRepository.save(new UrlShortener(null, "https://api.example.com:8443/v1/users/42/orders/active/details?filter=status%3Aopen%2Cpaid&sort=createdAt%3Adesc&include=items,shippingAddress&limit=50&offset=100&debug=true&redirect=https%3A%2F%2Fdashboard.example.com%2Fuser%3Ftab%3Dorders%23recent", "abcd3", null));
        assertThat(persisted.getId()).isNotNull();
    }

    @Test
    public void testSavingInvalidUrl(){
        assertThrows(TransactionSystemException.class,
                () ->  urlShortenerRepository.save(new UrlShortener(null, "invalidUrl", "abcd4", null)));
    }

    @Test
    public void testSavingNullCode(){
        assertThrows(DataIntegrityViolationException.class,
                () ->  urlShortenerRepository.save(new UrlShortener(null, "https://google.com", null, null)));
    }

}
