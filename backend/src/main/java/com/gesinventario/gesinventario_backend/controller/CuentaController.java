package com.gesinventario.gesinventario_backend.controller;

import com.gesinventario.gesinventario_backend.model.Cuenta;
import com.gesinventario.gesinventario_backend.model.enums.Role;
import com.gesinventario.gesinventario_backend.service.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService service;

    public CuentaController(CuentaService service) {
        this.service = service;
    }

    // Peticiones
    public record RegistroRequest(String nombre, String apellido, String apellido2,
                                  String email, String password) {}
    public record LoginRequest(String email, String password) {}
    public record RoleRequest(Role role) {}

    // Respuesta: NUNCA devolvemos la entidad Cuenta, porque incluiría la contraseña.
    public record CuentaResponse(Integer id, String nombre, String apellido,
                                 String apellido2, String email, Role role) {
        static CuentaResponse de(Cuenta c) {
            return new CuentaResponse(c.getId(), c.getNombre(), c.getApellido(),
                    c.getApellido2(), c.getEmail(), c.getRole());
        }
    }

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaResponse registrar(@RequestBody RegistroRequest r) {
        return CuentaResponse.de(service.crearCuenta(r.nombre(), r.apellido(),
                r.apellido2(), r.email(), r.password()));
    }

    @PostMapping("/login")
    public CuentaResponse login(@RequestBody LoginRequest r) {
        return CuentaResponse.de(service.iniciarSesion(r.email(), r.password()));
    }

    @GetMapping
    public List<CuentaResponse> listar() {
        return service.listarCuentas().stream().map(CuentaResponse::de).toList();
    }

    @PatchMapping("/{id}/role")
    public void cambiarRole(@PathVariable int id,
                            @RequestBody RoleRequest r,
                            @RequestHeader("X-Cuenta-Id") int idCuentaLogueada) {
        service.cambiarRole(id, r.role(), idCuentaLogueada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrar(@PathVariable int id,
                       @RequestHeader("X-Cuenta-Id") int idCuentaLogueada) {
        service.borrarCuenta(id, idCuentaLogueada);
    }
}