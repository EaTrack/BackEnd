package com.eatrack.repository;

import com.eatrack.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByUsuario(Long usuario);
}
