package com.lacontraloria.amasuapp.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "COORDINADOR")
public class Coordinador implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COOSEQ")
//    @SequenceGenerator(name = "COOSEQ", sequenceName = "COOSEQ", allocationSize = 1)
    @Column(name = "DNICOORDINADOR", length = 8)
    private Long dniCoordinador;

    @Column(name = "IDCOORDINADOR", nullable = false, unique = true, length = 30)
    private String idCoordinador;

    @Column(name = "CELULAR", length = 15)
    private Long celular;

    @Column(name = "EMAILSECUNDARIO", length = 100)
    private String emailSecundario;

    @OneToOne
    @JoinColumn(name = "DNICOORDINADOR", referencedColumnName = "DNICOORDINADOR", foreignKey = @ForeignKey(name = "FK_COORDINADOR_PERSONA"))
    @JsonIgnoreProperties("coordinador")
    private Persona persona;

}
