package com.lacontraloria.amasuapp.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    @OneToOne(mappedBy = "alerta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Imagen imagen;

    @JsonIgnore
    @ManyToMany(mappedBy = "alertas", fetch = FetchType.LAZY)
    private Set<Persona> personas = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "ALERTA_OBSERVACION",
            joinColumns = @JoinColumn(name = "IDALERTA"),
            inverseJoinColumns = @JoinColumn(name = "IDOBSERVACION")
    )
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Observacion> observaciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alerta alerta = (Alerta) o;
        return idAlerta != null && idAlerta.equals(alerta.idAlerta);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
