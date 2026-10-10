package com.gesinventario.gesinventario_backend.repository;

import com.gesinventario.gesinventario_backend.model.Existencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExistenciaRepository extends JpaRepository<Existencia, Integer> {

    @Query("SELECT e FROM Existencia e WHERE e.stock <= e.stockMinimo")
    List<Existencia> findConStockBajo();
}