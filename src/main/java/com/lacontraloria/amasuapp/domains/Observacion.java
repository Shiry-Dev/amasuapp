package com.lacontraloria.amasuapp.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.io.Serializable;

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
    @Column(name = "IDOBSERVACION")
    private Long idObservacion;

    @Column(name = "DESCOBSERVACION", nullable = false)
    private String descObservacion;
}
