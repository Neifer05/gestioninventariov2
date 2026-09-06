package com.gesinventario.gesinventario_backend.service;

import com.gesinventario.gesinventario_backend.exception.InvalidCampAccountException;
import com.gesinventario.gesinventario_backend.model.Cuenta;
import com.gesinventario.gesinventario_backend.model.enums.Role;
import com.gesinventario.gesinventario_backend.repository.CuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    // Spring ve que CuentaService necesita un CuentaRepository y le pasa automáticamente el que ya gestiona él (un "bean").
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public Cuenta crearCuenta(String nombre, String apellido, String apellido2, String email, String password) {
        if (cuentaRepository.findByEmail(email.trim()).isPresent()) {
            throw new InvalidCampAccountException("Este correo electrónico ya está siendo utilizado.");
        }

        Cuenta cuenta = new Cuenta(nombre, apellido, apellido2, email, password); // valida en el constructor, igual que antes

        if (cuentaRepository.count() == 0) {
            cuenta.setRole(Role.ADMIN); // la primera cuenta creada en el sistema es admin
        }

        return cuentaRepository.save(cuenta);
    }

    public Cuenta iniciarSesion(String email, String password) {
        return cuentaRepository.findByEmail(email.trim())
                .filter(c -> c.getPassword().equals(password))
                .orElseThrow(() -> new InvalidCampAccountException(
                        "Los datos de inicio de sesión no son correctos o la cuenta no existe."));
    }

    public List<Cuenta> listarCuentas() {
        return cuentaRepository.findAll();
    }

    public Cuenta obtenerCuenta(int id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new InvalidCampAccountException("No existe una cuenta con el ID indicado."));
    }

    @Transactional
    public void cambiarRole(int idCuentaObjetivo, Role nuevoRole, int idCuentaLogueada) {
        if (idCuentaObjetivo == idCuentaLogueada) {
            throw new InvalidCampAccountException("No puedes modificar el rol de tu propia cuenta.");
        }
        Cuenta cuenta = obtenerCuenta(idCuentaObjetivo);
        cuenta.setRole(nuevoRole);
        cuentaRepository.save(cuenta);
    }

    public void borrarCuenta(int idCuentaObjetivo, int idCuentaLogueada) {
        if (idCuentaObjetivo == idCuentaLogueada) {
            throw new InvalidCampAccountException("No puedes eliminar tu propia cuenta mientras tienes la sesión iniciada.");
        }
        if (!cuentaRepository.existsById(idCuentaObjetivo)) {
            throw new InvalidCampAccountException("No existe una cuenta con el ID indicado.");
        }
        cuentaRepository.deleteById(idCuentaObjetivo);
    }
}