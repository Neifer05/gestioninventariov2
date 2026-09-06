package com.gesinventario.gesinventario_backend.repository;

import com.gesinventario.gesinventario_backend.model.Existencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExistenciaRepository extends JpaRepository<Existencia, Integer> {
}
