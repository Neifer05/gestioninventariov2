package com.gesinventario.gesinventario_backend.repository;

import com.gesinventario.gesinventario_backend.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
}