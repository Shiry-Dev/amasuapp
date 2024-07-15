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
@Table(name = "ADMINISTRADOR")
public class Administrador implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADMSEQ")
//    @SequenceGenerator(name = "ADMSEQ", sequenceName = "ADMSEQ", allocationSize = 1)
    @Column(name = "DNIADMINISTRADOR")
    private Long dniAdministrador;

    @Column(name = "IDADMINISTRADOR", nullable = false, unique = true, length = 30)
    private String idAdministrador;

    @Column(name = "CELULAR", length = 15)
    private Long celular;

    @Column(name = "EMAILSECUNDARIO", length = 100)
    private String emailSecundario;

    @OneToOne
    @JoinColumn(name = "DNIADMINISTRADOR", referencedColumnName = "DNIRENIEC", foreignKey = @ForeignKey(name = "FK_ADMINISTRADOR_PERSONA"))
    @JsonIgnoreProperties("administrador")
    private Persona persona;

}
