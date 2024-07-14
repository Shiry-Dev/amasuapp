package com.lacontraloria.amasuapp.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "ALERTA")
public class Alerta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALERTASEQ")
    @SequenceGenerator(name = "ALERTASEQ", sequenceName = "ALERTASEQ", allocationSize = 1)
    @Column(name = "IDALERTA")
    private Long idAlerta;

    @Column(name = "CUI", length = 15)
    private Long cui;

    @Column(name = "FECHACREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "COMENTARIOALERTA")
    private String comentarioAlerta;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "DNIRENIEC", referencedColumnName = "DNIRENIEC", nullable = false)
    private Persona persona;

    @OneToMany(mappedBy = "alerta", cascade = CascadeType.ALL)
    private List<Observacion> observaciones;

//    @OneToOne(mappedBy = "alerta")
//    @JsonIgnoreProperties("alerta")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private imagen imagen;
}
