package com.lacontraloria.amasuapp.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "OBSERVACION")
public class Observacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OBSSEQ")
    @SequenceGenerator(name = "OBSSEQ", sequenceName = "OBSSEQ", allocationSize = 1)
    @Column(name = "IDOBSERVACION")
    private Long idObservacion;

    @Column(name = "DESCOBSERVACION", nullable = false)
    private String descObservacion;

    @ManyToMany(mappedBy = "observaciones", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Alerta> alertas;

}
