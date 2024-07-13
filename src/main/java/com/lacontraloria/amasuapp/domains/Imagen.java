package com.lacontraloria.amasuapp.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "IMAGEN")
public class Imagen implements Serializable {

    @Id
    @Column(name = "IDIMAGEN")
    private Long idImagem;

    @Lob
    @Column(name = "IMAGEN1", nullable = false)
    private byte[] imagen1;

    @Lob
    @Column(name = "IMAGEN2")
    private byte[] imagen2;

    @Lob
    @Column(name = "IMAGEN3")
    private byte[] imagen3;

    @Lob
    @Column(name = "IMAGEN4")
    private byte[] imagen4;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "IDIMAGEN", referencedColumnName = "IDIMAGEN", foreignKey = @ForeignKey(name = "FK_ALERTA_IMAGEN"))
//    @JsonIgnoreProperties("administrador")
//    private Imagen imagen;

}
