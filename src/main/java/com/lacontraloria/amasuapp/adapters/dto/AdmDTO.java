package com.lacontraloria.amasuapp.adapters.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AdmDTO(
        String idAdministrador,
        Long celular,
        String emailSecundario
) {
}
