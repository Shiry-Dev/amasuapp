package com.lacontraloria.amasuapp.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "CURSO")
public class Curso implements Serializable {

    @Id
    @Column(name = "IDCURSO")
    private Long idCurso;

    @Column(name = "DESCCURSO", nullable = false)
    private String descCurso;
//
//    @OneToMany(mappedBy = "curso")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private List<CiudadanoCurso> ciudadanosCursos;
}
