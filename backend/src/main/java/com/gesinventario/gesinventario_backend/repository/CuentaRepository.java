package com.gesinventario.gesinventario_backend.repository;

import com.gesinventario.gesinventario_backend.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
    Optional<Cuenta> findByEmail(String email);
}