package com.lacontraloria.amasuapp.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "DNIRENIEC", referencedColumnName = "DNIRENIEC", nullable = false)
    private Persona persona;

    @OneToMany(mappedBy = "alerta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Observacion> observaciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alerta alerta = (Alerta) o;
        return Objects.equals(idAlerta, alerta.idAlerta) && Objects.equals(cui, alerta.cui) && Objects.equals(fechaCreacion, alerta.fechaCreacion) && Objects.equals(comentarioAlerta, alerta.comentarioAlerta) && Objects.equals(persona, alerta.persona) && Objects.equals(observaciones, alerta.observaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAlerta, cui, fechaCreacion, comentarioAlerta, persona, observaciones);
    }

    @Override
    public String toString() {
        return "Alerta{" +
                "idAlerta=" + idAlerta +
                ", cui=" + cui +
                ", fechaCreacion=" + fechaCreacion +
                ", comentarioAlerta='" + comentarioAlerta + '\'' +
                ", persona=" + persona +
                ", observaciones=" + observaciones +
                '}';
    }
}
