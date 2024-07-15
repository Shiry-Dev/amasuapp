package com.lacontraloria.amasuapp.domains;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "MONITOR")
public class Monitor implements Serializable {

    @Id
    @Column(name = "DNIMONITOR", length = 8)
    private Long dniMonitor;

    @Column(name = "IDMONITOR", nullable = false, unique = true)
    private String idMonitor;

    @Column(name = "ANIOINGRESO", nullable = false)
    private LocalDate anioIngreso;

    @Column(name = "REGIONINSCRIPCION", length = 100, nullable = false)
    private String regionInscription;

    @Column(name = "PROVINCIAINSCRIPCION", length = 100, nullable = false)
    private String provinciaInscripcion;

    @Column(name = "DISTRITOINSCRIPCION", length = 100, nullable = false)
    private String distritoInscripcion;

    @Column(name = "EMAILSECUNDARIO", length = 100)
    private String emailSecundario;

    @Column(name = "CELULAR", length = 15)
    private Long celular;

    @Column(name = "UBIGEOINEI", length = 100, nullable = false)
    private String ubigeoInei;

    @Column(name = "REGIONACTUAL", length = 100, nullable = false)
    private String regionActual;

    @Column(name = "PROVINCIAACTUAL", length = 100, nullable = false)
    private String provinciaActual;

    @Column(name = "DISTRITOACTUAL", length = 100, nullable = false)
    private String distritoActual;

    @Column(name = "ESTUDIOS", length = 100)
    private String estudios;

    @Column(name = "OCUPACION", length = 100)
    private String ocupacion;

    @Column(name = "CARRERA", length = 100)
    private String carrera;

    @Column(name = "EXPERIENCIA")
    private String experiencia;

    @Column(name = "PROFESIONDEPURADA")
    private String profesionDepurada;

    @Column(name = "CONVOCATORIAINGRESO")
    private String convocatoriaIngreso;


}

