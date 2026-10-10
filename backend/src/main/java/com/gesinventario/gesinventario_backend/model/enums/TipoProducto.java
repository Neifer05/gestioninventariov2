package com.gesinventario.gesinventario_backend.model.enums;

public enum TipoProducto {
    TECNOLOGIA("Tecnología"), HERRAMIENTA("Herramienta"), MUEBLE("Mueble"),
    UTENSILIO("Utensilio"), PERSONAL("Personal"), OTRO("Otro");

    private final String tituloTipoProducto;
    TipoProducto(String t) { this.tituloTipoProducto = t; }
    public String getTituloTipoProducto() { return tituloTipoProducto; }
}
