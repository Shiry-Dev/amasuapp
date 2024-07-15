package com.lacontraloria.amasuapp.adapters.dto;

public record ImagenDTO(
        Long idImagen,
        byte[] imagen1,
        byte[] imagen2,
        byte[] imagen3,
        byte[] imagen4
) {
}
