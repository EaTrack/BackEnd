package com.eatrack.helper;

import org.springframework.stereotype.Service;

@Service
public class FormatadorHelper {

    public String formatarCelularBanco(String celular){
        return celular.replaceAll("[^0-9]", "");
    }

    public String formatarDocumentoBanco(String documento){
        return documento
                .replace(".", "")
                .replace("-", "")
                .replace("/", "")
                .trim();
    }
}
