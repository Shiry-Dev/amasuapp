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

    @Column(name = "IDCOORDINADOR", length = 30)
    private String idCoordinador;

    @Column(name = "IDADMINISTRADOR", length = 30)
    private String idAdministrador;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Curso> cursos;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Alerta> alertas;

}
