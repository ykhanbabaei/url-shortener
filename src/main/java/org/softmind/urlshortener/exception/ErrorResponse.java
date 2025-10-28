package org.softmind.urlshortener.exception;

import java.time.LocalDateTime;

public record ErrorResponse(String title, String description, LocalDateTime localDateTime) {
}
