package org.softmind.urlshortener.repository;

import org.softmind.urlshortener.model.UrlShortener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Annotation;
import java.util.Optional;

@Repository
public interface UrlShortenerRepository extends JpaRepository<UrlShortener, Long> {

    Optional<UrlShortener> findByCode(String surl);

    Optional<UrlShortener> findByUrl(String url);

}
