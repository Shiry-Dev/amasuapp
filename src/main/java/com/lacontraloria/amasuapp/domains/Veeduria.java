package com.lacontraloria.amasuapp.domains;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "VEEDURIA")
public class Veeduria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VEESEQ")
    @SequenceGenerator(name = "VEESEQ", sequenceName = "VEESEQ", allocationSize = 1)
    @Column(name = "IDVEEDURIA", length = 15)
    private Long idVeeduria;

    @Column(name = "CUI", length = 15)
    private Long cui;

    @Column(name = "FECHACREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHAVEEDURIA")
    private LocalDateTime fechaVeeduria;
}
