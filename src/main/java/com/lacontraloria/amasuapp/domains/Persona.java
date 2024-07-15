package com.lacontraloria.amasuapp.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToOne(mappedBy = "persona")
    @JsonIgnoreProperties("persona")
//    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Administrador administrador;

    @OneToOne(mappedBy = "persona")
    @JsonIgnore
    private Coordinador coordinador;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Curso> cursos;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Alerta> alertas;

}
