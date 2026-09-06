package com.gesinventario.gesinventario_backend.model;


import com.gesinventario.gesinventario_backend.exception.InvalidCampExistenciaException;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "existencias")
public class Existencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int stockMinimo;

    @Column(nullable = false)
    private boolean caducable;

    private LocalDate fechaCaducidad;

    protected Existencia() {}

    public Existencia(Producto producto, int stock, int stockMinimo, boolean caducable, LocalDate fechaCaducidad) {
        setProducto(producto);
        setStockMinimo(stockMinimo);
        this.caducable = caducable;
        setFechaCaducidad(fechaCaducidad);
        setStock(stock);
    }

    public void setProducto(Producto producto) {
        if (producto == null) {
            throw new InvalidCampExistenciaException("Debes seleccionar un producto válido para la existencia.");
        }
        this.producto = producto;
    }
    public void setStock(int stock) {
        if (stock < 0) {
            throw new InvalidCampExistenciaException("El stock no puede ser un valor negativo.");
        }
        this.stock = stock;
    }
    public void setStockMinimo(int stockMinimo) {
        if (stockMinimo < 0) {
            throw new InvalidCampExistenciaException("El stock mínimo no puede ser un valor negativo.");
        }
        this.stockMinimo = stockMinimo;
    }
    public void setCaducable(boolean caducable) {
        this.caducable = caducable;
        if (!caducable) {
            this.fechaCaducidad = null;
        }
    }
    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        if (!this.caducable) {
            this.fechaCaducidad = null;
            return;
        }
        if (fechaCaducidad == null) {
            throw new InvalidCampExistenciaException("Debes indicar una fecha de caducidad para una existencia caducable.");
        }
        this.fechaCaducidad = fechaCaducidad;
    }

    public void añadirStock(int cantidad) {
        if (cantidad <= 0) {
            throw new InvalidCampExistenciaException("La cantidad a añadir debe ser mayor a 0.");
        }
        this.stock += cantidad;
    }
    public void quitarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new InvalidCampExistenciaException("La cantidad a quitar debe ser mayor a 0.");
        }
        if (cantidad > this.stock) {
            throw new InvalidCampExistenciaException("No hay suficiente stock disponible para quitar esa cantidad.");
        }
        this.stock -= cantidad;
    }
    public boolean estaBajoStockMinimo() {
        return this.stock <= this.stockMinimo;
    }

    public Integer getId() { return id; }
    public Producto getProducto() { return producto; }
    public int getStock() { return stock; }
    public int getStockMinimo() { return stockMinimo; }
    public boolean isCaducable() { return caducable; }
    public LocalDate getFechaCaducidad() { return fechaCaducidad; }
}