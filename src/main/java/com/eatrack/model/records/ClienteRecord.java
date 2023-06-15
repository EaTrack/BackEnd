package com.eatrack.model.records;


import com.eatrack.model.type.TipoDocumento;
import com.eatrack.model.type.TipoPessoa;

public record ClienteRecord(
        String nome,

        String documento,

        TipoDocumento tipoDocumento,

        String celular,

        String email,

        TipoPessoa tipoPessoa,
        UserRecord userRecord
) {
}
