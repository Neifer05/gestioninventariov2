package com.gesinventario.gesinventario_backend.model.enums;

public enum Role {
    EMPLEADO("Empleado"),
    ADMIN("Administrador");

    private final String tituloRango;

    Role(String tituloRango) {
        this.tituloRango = tituloRango;
    }
    public String getTituloRango() {
        return tituloRango;
    }
}
