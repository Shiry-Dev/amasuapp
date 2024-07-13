package com.lacontraloria.amasuapp.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    @Column(name = "IDALERTA")
    private Long idAlerta;

    @Column(name = "CUI", length = 15)
    private Long cui;

    @Column(name = "FECHACREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "COMENTARIOALERTA")
    private String comentarioAlerta;

    @ManyToOne
    @JoinColumn(name = "DNIRENIEC", referencedColumnName = "DNIRENIEC", nullable = false)
    private Persona persona;

//    @OneToOne(mappedBy = "alerta")
//    @JsonIgnoreProperties("alerta")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private imagen imagen;
}
