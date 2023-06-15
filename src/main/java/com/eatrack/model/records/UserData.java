package com.eatrack.model.records;

public record UserData(
        String token,
        Long id,
        String login
) {
}
