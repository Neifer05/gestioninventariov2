package com.gesinventario.gesinventario_backend.controller;

import com.gesinventario.gesinventario_backend.model.Producto;
import com.gesinventario.gesinventario_backend.model.enums.TipoProducto;
import com.gesinventario.gesinventario_backend.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    public record ProductoRequest(String nombreProducto, String descripcion,
                                  float precio, TipoProducto tipoProducto) {}

    @GetMapping
    public List<Producto> listar() {
        return service.listaProductos();
    }

    @GetMapping("/{id}")
    public Producto obtener(@PathVariable int id) {
        return service.buscarProducto(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody ProductoRequest r) {
        return service.crearProducto(r.nombreProducto(), r.descripcion(),
                r.precio(), r.tipoProducto());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrar(@PathVariable int id) {
        service.borrarProducto(id);
    }
}