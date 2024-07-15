package com.lacontraloria.amasuapp.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CURSOSEQ")
    @SequenceGenerator(name = "CURSOSEQ", sequenceName = "CURSOSEQ", allocationSize = 1)
    @Column(name = "IDCURSO")
    private Long idCurso;

    @Column(name = "DESCCURSO", nullable = false)
    private String descCurso;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "DNIRENIEC", referencedColumnName = "DNIRENIEC", nullable = false)
    private Persona persona;
}
