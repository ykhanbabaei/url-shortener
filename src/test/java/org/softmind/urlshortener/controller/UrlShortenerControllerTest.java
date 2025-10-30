package org.softmind.urlshortener.controller;

import org.junit.jupiter.api.Test;
import org.softmind.urlshortener.dto.UrlDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlShortenerControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void testRegister() {
        ResponseEntity<String> response = restTemplate.postForEntity("/api/register", new UrlDto("https://exampl.com"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(5);

    }

    @Test
    void testRegisterInvalidUrl() {
        ResponseEntity<String> response = restTemplate.postForEntity("/api/register", new UrlDto("invalidUrl"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testUrlAlreadyRegistered()  {
        String url = "https://example1.com";
        restTemplate.postForEntity("/api/register", new UrlDto(url), String.class);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/register", new UrlDto(url), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testRegisterAndFindUrl() {
        String url = "https://example.com";
        ResponseEntity<String> response = restTemplate.postForEntity("/api/register", new UrlDto(url), String.class);
        String code = response.getBody();
        response = restTemplate.getForEntity("/api/" + code, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(url);
    }

    @Test
    void testUrlCache() {
        var url = "https://example3.com";
        ResponseEntity<String> response = restTemplate.postForEntity("/api/register", new UrlDto(url), String.class);
        var code = response.getBody();
        response = restTemplate.getForEntity("/api/" + code, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(url);

        var cachedResult = cacheManager.getCache("urls").get(code);
        assertThat(cachedResult).isNotNull();
        assertThat(cachedResult.get()).isEqualTo(url);
    }


}