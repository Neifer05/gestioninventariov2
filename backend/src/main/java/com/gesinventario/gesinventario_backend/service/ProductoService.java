package com.gesinventario.gesinventario_backend.service;

import com.gesinventario.gesinventario_backend.exception.InvalidCampProductException;
import com.gesinventario.gesinventario_backend.model.Producto;
import com.gesinventario.gesinventario_backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto crearProducto(String nombreProducto, String descripcion, float precio) {
        Producto producto = new Producto(nombreProducto, descripcion, precio);
        return productoRepository.save(producto);
    }

    public List<Producto> listaProductos () {
        return productoRepository.findAll();
    }

    public Producto buscarProducto (int id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new InvalidCampProductException("No existe un producto con el ID indicado."));
    }

    public void borrarProducto(int idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new InvalidCampProductException("No existe un producto con el ID indicado.");
        }
        productoRepository.deleteById(idProducto);
    }
}
