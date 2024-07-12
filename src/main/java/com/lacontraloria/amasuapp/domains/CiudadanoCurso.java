package com.lacontraloria.amasuapp.domains;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "CIUDADANO_CURSO")
public class CiudadanoCurso implements Serializable {

    @Id
    @Column(name = "IDCIUDADANOCURSO")
    private Long idCiudadanoCurso;

    @Column(name = "DNIRENIEC")
    private Long dniReniec;

    @Column(name = "IDCURSO")
    private Long idCurso;
//
//    @ManyToOne
//    @JoinColumn(name = "IDCURSO", referencedColumnName = "IDCURSO", insertable = false, updatable = false)
//    private Curso curso;
//
//    @ManyToOne
//    @JoinColumn(name = "DNIRENIEC", referencedColumnName = "DNIRENIEC", insertable = false, updatable = false)
//    private Persona persona;
}
