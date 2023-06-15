package com.eatrack.helper;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataHelper {

    public LocalDateTime getDataHora() {
        LocalDateTime date = LocalDateTime.now();
        return date;
    }
}
