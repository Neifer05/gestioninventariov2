package com.gesinventario.gesinventario_backend.controller;

import com.gesinventario.gesinventario_backend.model.Existencia;
import com.gesinventario.gesinventario_backend.service.ExistenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/existencias")
public class ExistenciaController {

    private final ExistenciaService service;

    public ExistenciaController(ExistenciaService service) {
        this.service = service;
    }

    public record CrearExistenciaRequest(int productoId, int stock, int stockMinimo,
                                         boolean caducable, LocalDate fechaCaducidad) {}
    public record ModificarExistenciaRequest(int stock, int stockMinimo,
                                             boolean caducable, LocalDate fechaCaducidad) {}
    public record CantidadRequest(int cantidad) {}

    @GetMapping
    public List<Existencia> listar() {
        return service.listarExistencias();
    }

    @GetMapping("/stock-bajo")
    public List<Existencia> stockBajo() {
        return service.listarConStockBajo();
    }

    @GetMapping("/{id}")
    public Existencia obtener(@PathVariable int id) {
        return service.obtenerExistencia(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Existencia crear(@RequestBody CrearExistenciaRequest r) {
        return service.crearExistencia(r.productoId(), r.stock(), r.stockMinimo(),
                r.caducable(), r.fechaCaducidad());
    }

    @PutMapping("/{id}")
    public Existencia modificar(@PathVariable int id, @RequestBody ModificarExistenciaRequest r) {
        return service.modificarExistencia(id, r.stock(), r.stockMinimo(),
                r.caducable(), r.fechaCaducidad());
    }

    @PatchMapping("/{id}/stock/anadir")
    public Existencia anadirStock(@PathVariable int id, @RequestBody CantidadRequest r) {
        return service.añadirStock(id, r.cantidad());
    }

    @PatchMapping("/{id}/stock/quitar")
    public Existencia quitarStock(@PathVariable int id, @RequestBody CantidadRequest r) {
        return service.quitarStock(id, r.cantidad());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrar(@PathVariable int id) {
        service.borrarExistencia(id);
    }
}