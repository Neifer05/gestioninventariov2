package com.gesinventario.gesinventario_backend.model;

import com.gesinventario.gesinventario_backend.exception.InvalidCampProductException;
import com.gesinventario.gesinventario_backend.model.enums.TipoProducto;
import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombreProducto;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false)
    private float precio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProducto tipoProducto;

    protected Producto() {}

    public Producto(Integer id, String descripcion, String nombreProducto, float precio) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }
    public String getNombreProducto() {
        return nombreProducto;
    }
    public String getDescripcion() {
        if (descripcion.isEmpty()) {
            setDescripcion("El producto no cuenta con descripción.");
        }
        return descripcion;
    }
    public float getPrecio() {
        return precio;
    }
    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        if (nombreProducto == null) {
            throw new InvalidCampProductException("El nombre del producto no puede estar vacío.");
        }
        String nombreLimpio = nombreProducto.trim();

        if (nombreLimpio.isEmpty()) {
            throw new InvalidCampProductException("El nombre del producto no puede estar vacío.");
        }
        if (nombreProducto.length() > 50) {
            throw new InvalidCampProductException("El nombre no puede contener una longitud mayor a 50 caracteres.");
        }

        this.nombreProducto = nombreLimpio;
    }
    public void setDescripcion(String descripcion) {
        if (descripcion.length() > 200) {
            throw new InvalidCampProductException("La descripción del producto no puede contener una longitud mayor a 200 caracteres");
        }
        this.descripcion = descripcion;
    }
    public void setPrecio(float precio) {
        this.precio = precio;
    }
    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
}
