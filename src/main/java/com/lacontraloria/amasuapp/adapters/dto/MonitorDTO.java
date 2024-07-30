package com.lacontraloria.amasuapp.adapters.dto;

import java.time.LocalDate;

public record MonitorDTO(
        String idMonitor,
        Long celular,
        String emailSecundario,
        LocalDate anioIngreso,
        String regionInscripcion,
        String provinciaInscripcion,
        String distritoInscripcion,
        String ubigeoInei,
        String regionActual,
        String provinciaActual,
        String distritoActual,
        String estudios,
        String ocupacion,
        String carrera,
        String experiencia,
        String profesionDepurada,
        String convocatoriaIngreso) {
}
