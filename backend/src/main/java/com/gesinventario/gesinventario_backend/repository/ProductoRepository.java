package com.gesinventario.gesinventario_backend.repository;

import com.gesinventario.gesinventario_backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}