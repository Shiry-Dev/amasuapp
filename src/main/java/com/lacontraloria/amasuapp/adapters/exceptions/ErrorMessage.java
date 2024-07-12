package com.lacontraloria.amasuapp.adapters.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessage {
    private int code;
    private String title;
    private String message;
    private final OffsetDateTime timestamp = OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS);
}
