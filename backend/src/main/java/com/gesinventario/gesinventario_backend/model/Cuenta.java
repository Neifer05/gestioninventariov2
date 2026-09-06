package com.gesinventario.gesinventario_backend.model;


import com.gesinventario.gesinventario_backend.exception.InvalidCampAccountException;
import com.gesinventario.gesinventario_backend.model.enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, length = 50)
    private String apellido2;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    protected Cuenta () {} // constructor vacío para poder reconstruir el objeto al leerlo de la BD.

    public Cuenta(String nombre, String apellido, String apellido2, String email, String password, Role role) {
        setNombre(nombre);
        setApellido(apellido);
        setApellido2(apellido2);
        setEmail(email);
        setPassword(password);
        this.role = Role.EMPLEADO;
    }

    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new InvalidCampAccountException("El nombre no puede estar vacío.");
        }
        String nombreLimpio = nombre.trim();
        if (nombreLimpio.isEmpty()) {
            throw new InvalidCampAccountException("El nombre no puede estar vacío.");
        }
        if (nombreLimpio.length() > 20) {
            throw new InvalidCampAccountException("El nombre no puede contener una longitud mayor a 20 caracteres.");
        }
        String primeraLetra = nombreLimpio.substring(0, 1).toUpperCase();
        String restante = nombreLimpio.substring(1).toLowerCase();
        this.nombre = primeraLetra + restante;
    }
    public void setApellido(String apellido) {
        if (apellido == null) {
            throw new InvalidCampAccountException("El apellido no puede estar vacío.");
        }
        String apellidoLimpio = apellido.trim();
        if (apellidoLimpio.isEmpty()) {
            throw new InvalidCampAccountException("El apellido no puede estar vacío.");
        }
        if (apellidoLimpio.length() > 50) {
            throw new InvalidCampAccountException("El apellido no puede contener una longitud mayor a 50 caracteres.");
        }
        String primeraLetra = apellidoLimpio.substring(0, 1).toUpperCase();
        String restante = apellidoLimpio.substring(1).toLowerCase();
        this.apellido = primeraLetra + restante;
    }
    public void setApellido2(String apellido2) {
        if (apellido2 == null) {
            throw new InvalidCampAccountException("El apellido no puede estar vacío.");
        }
        String apellidoLimpio = apellido2.trim();
        if (apellidoLimpio.isEmpty()) {
            throw new InvalidCampAccountException("El apellido no puede estar vacío.");
        }
        if (apellidoLimpio.length() > 50) {
            throw new InvalidCampAccountException("El apellido no puede contener una longitud mayor a 50 caracteres.");
        }
        String primeraLetra = apellidoLimpio.substring(0, 1).toUpperCase();
        String restante = apellidoLimpio.substring(1).toLowerCase();
        this.apellido2 = primeraLetra + restante;
    }
    public void setEmail(String email) {
        if (email == null) {
            throw new InvalidCampAccountException("El campo del correo electrónico no puede estar vacío.");
        }
        String emailLimpio = email.trim();
        if (emailLimpio.isEmpty()) {
            throw new InvalidCampAccountException("El campo del correo electrónico no puede estar vacío.");
        }
        if (emailLimpio.length() > 100) {
            throw new InvalidCampAccountException("El email no puede contener una longitud mayor a 100 caracteres.");
        }
        if (!emailLimpio.contains("@")) {
            throw new InvalidCampAccountException("El email no es válido.");
        }
        this.email = emailLimpio;
    }
    public void setPassword(String password) {
        if (password == null) {
            throw new InvalidCampAccountException("El campo de la contraseña no puede estar vacío.");
        }
        String passwordLimpio = password.trim();
        if (passwordLimpio.isEmpty()) {
            throw new InvalidCampAccountException("El campo de la contraseña no puede estar vacío.");
        }
        if (passwordLimpio.length() < 8) {
            throw new InvalidCampAccountException("La longitud de la contraseña no puede ser menor a 8 caracteres.");
        }
        boolean tieneNumero = false;
        for (int i = 0; i < passwordLimpio.length(); i++) {
            if (Character.isDigit(passwordLimpio.charAt(i))) {
                tieneNumero = true;
                break;
            }
        }
        if (!tieneNumero) {
            throw new InvalidCampAccountException("La contraseña debe contener por lo menos un valor numérico.");
        }
        this.password = passwordLimpio;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getApellido2() { return apellido2; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
}
