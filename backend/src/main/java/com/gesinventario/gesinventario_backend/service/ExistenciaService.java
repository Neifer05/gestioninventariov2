package com.gesinventario.gesinventario_backend.service;

import com.gesinventario.gesinventario_backend.exception.InvalidCampExistenciaException;
import com.gesinventario.gesinventario_backend.model.Existencia;
import com.gesinventario.gesinventario_backend.model.Producto;
import com.gesinventario.gesinventario_backend.repository.ExistenciaRepository;
import com.gesinventario.gesinventario_backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExistenciaService {

    private final ExistenciaRepository existenciaRepository;
    private final ProductoRepository productoRepository;

    public ExistenciaService(ExistenciaRepository existenciaRepository,
                             ProductoRepository productoRepository) {
        this.existenciaRepository = existenciaRepository;
        this.productoRepository = productoRepository;
    }

    public Existencia crearExistencia(int productoId, int stock, int stockMinimo,
                                      boolean caducable, LocalDate fechaCaducidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new InvalidCampExistenciaException("No existe un producto con el ID indicado."));

        Existencia existencia = new Existencia(producto, stock, stockMinimo, caducable, fechaCaducidad);
        return existenciaRepository.save(existencia);
    }

    public List<Existencia> listarExistencias() {
        return existenciaRepository.findAll();
    }

    public List<Existencia> listarConStockBajo() {
        return existenciaRepository.findConStockBajo();
    }

    public Existencia obtenerExistencia(int id) {
        return existenciaRepository.findById(id)
                .orElseThrow(() -> new InvalidCampExistenciaException("No existe una existencia con el ID indicado."));
    }

    @Transactional
    public Existencia añadirStock(int id, int cantidad) {
        Existencia existencia = obtenerExistencia(id);
        existencia.añadirStock(cantidad);       // la validación vive en tu entidad, igual que antes
        return existenciaRepository.save(existencia);
    }

    @Transactional
    public Existencia quitarStock(int id, int cantidad) {
        Existencia existencia = obtenerExistencia(id);
        existencia.quitarStock(cantidad);
        return existenciaRepository.save(existencia);
    }

    @Transactional
    public Existencia modificarExistencia(int id, int stock, int stockMinimo,
                                          boolean caducable, LocalDate fechaCaducidad) {
        Existencia existencia = obtenerExistencia(id);
        // Mismo orden que en tu versión CLI: caducable antes que la fecha,
        // porque setFechaCaducidad depende de ese valor.
        existencia.setStockMinimo(stockMinimo);
        existencia.setCaducable(caducable);
        existencia.setFechaCaducidad(fechaCaducidad);
        existencia.setStock(stock);
        return existenciaRepository.save(existencia);
    }

    public void borrarExistencia(int id) {
        if (!existenciaRepository.existsById(id)) {
            throw new InvalidCampExistenciaException("No existe una existencia con el ID indicado.");
        }
        existenciaRepository.deleteById(id);
    }
}