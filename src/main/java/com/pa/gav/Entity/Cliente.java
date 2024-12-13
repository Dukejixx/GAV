package com.pa.gav.Entity;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Column(nullable = false, unique = true)
    private String numeroDocumento;

    public enum TipoDocumento {
        CEDULA,
        PASAPORTE,
        CEDULA_EXTRANJERIA
    }

    public Cliente() {
    }

    public Cliente(Long id, String nombreCompleto, String apellidos, String telefono, String email, String username, String password, TipoDocumento tipoDocumento, String numeroDocumento) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.username = username;
        this.password = password;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(telefono, cliente.telefono) && Objects.equals(email, cliente.email) && Objects.equals(username, cliente.username) && Objects.equals(numeroDocumento, cliente.numeroDocumento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, telefono, email, username, numeroDocumento);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", tipoDocumento=" + tipoDocumento +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                '}';
    }
}
