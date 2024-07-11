package com.lacontraloria.amasuapp.domains;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "PERSONA")
public class Persona implements Serializable {

    @Id
    @Column(name = "DNIRENIEC")
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

}
