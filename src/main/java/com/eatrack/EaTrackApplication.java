package com.eatrack;

import com.eatrack.model.Role;
import com.eatrack.model.Usuario;
import com.eatrack.model.type.Status;
import com.eatrack.repository.RoleRepository;
import com.eatrack.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class EaTrackApplication {

    public static void main(String[] args) {

        SpringApplication.run(EaTrackApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Permite qualquer origem
        config.addAllowedMethod("*"); // Permite qualquer método (GET, POST, etc)
        config.addAllowedHeader("*"); // Permite qualquer cabeçalho
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
