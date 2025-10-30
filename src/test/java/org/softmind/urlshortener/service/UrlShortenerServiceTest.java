package org.softmind.urlshortener.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.softmind.urlshortener.model.UrlShortener;
import org.softmind.urlshortener.repository.UrlShortenerRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @Mock
    private UrlShortenerRepository urlShortenerRepository;

    @InjectMocks
    private UrlShortenerService urlShortenerService;

    @Test
    void testRegister() throws ExecutionException, InterruptedException {
        when(urlShortenerRepository.findByUrl(anyString())).thenReturn(Optional.empty());
        when(urlShortenerRepository.save(any())).thenReturn(mockUrlShortener());
        String code = urlShortenerService.register("http://example.com").get();
        assertThat(code).isNotNull();
        assertThat(code.length()).isEqualTo(5);
    }

    private UrlShortener mockUrlShortener() {
        return new UrlShortener(1L, "http://example.com", "abcde", LocalDate.now());
    }

    @Test
    void testFindUrl() throws ExecutionException, InterruptedException {
        when(urlShortenerRepository.findByCode(anyString())).thenReturn(Optional.of(mockUrlShortener()));
        String url = urlShortenerService.findUrl("http://example.com").get();
        assertThat(url).isNotNull();
    }

}