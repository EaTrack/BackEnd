package com.eatrack.model;

import com.eatrack.model.type.Status;
import com.eatrack.model.type.TipoDocumento;
import com.eatrack.model.type.TipoPessoa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cliente", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "documento"
        })
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime dataIni;

    private LocalDateTime dataAlt;

    private LocalDateTime dataFim;

    private Long usuario;

    private String nome;

    private String documento;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    private String celular;

    private String email;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;
}
