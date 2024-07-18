package com.lacontraloria.amasuapp.domains;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "PERSONA")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Persona implements Serializable {

    @Id
    @Column(name = "DNIRENIEC", length = 8)
    private Long dniRieniec;

    @Column(name = "PATERNO", nullable = false, length = 100)
    private String paterno;

    @Column(name = "MATERNO", nullable = false, length = 100)
    private String materno;

    @Column(name = "NOMBRES", nullable = false, length = 100)
    private String nombres;

    @Column(name = "FECHANACIMIENTO", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "EMAILPRINCIPAL", nullable = false, length = 100)
    private String emailPrincipal;

    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    @Column(name = "SEXO", nullable = false, length = 15)
    private String sexo;

    @Column(name = "ROLETYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.USER;

    @Column(name = "CELULAR", length = 15)
    private Long celular;

    @Column(name = "EMAILSECUNDARIO", length = 100)
    private String emailSecundario;

    @Column(name = "IDCOORDINADOR", length = 30, unique = true)
    private String idCoordinador;

    @Column(name = "IDADMINISTRADOR", length = 30, unique = true)
    private String idAdministrador;

    @Column(name = "IDMONITOR", length = 30, unique = true)
    private String idMonitor;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Curso> cursos;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Alerta> alertas;

    @Column(name = "ANIOINGRESO")
    private LocalDate anioIngreso;

    @Column(name = "REGIONINSCRIPCION", length = 100)
    private String regionInscription;

    @Column(name = "PROVINCIAINSCRIPCION", length = 100)
    private String provinciaInscripcion;

    @Column(name = "DISTRITOINSCRIPCION", length = 100)
    private String distritoInscripcion;

    @Column(name = "UBIGEOINEI", length = 100)
    private String ubigeoInei;

    @Column(name = "REGIONACTUAL", length = 100)
    private String regionActual;

    @Column(name = "PROVINCIAACTUAL", length = 100)
    private String provinciaActual;

    @Column(name = "DISTRITOACTUAL", length = 100)
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
