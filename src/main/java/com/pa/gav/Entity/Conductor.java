package com.pa.gav.Entity;


import jakarta.persistence.*;



import java.time.LocalDate;
import java.util.Objects;


@Entity
public class Conductor{

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
    private String cedula;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = false)
    private String password;

    @Column
    private Boolean disponible;

    @OneToOne
    @JoinColumn(name = "id_Vehiculo")
    private Vehiculo vehiculo;

    public Conductor() {
    }

    public Conductor(Long id, String nombreCompleto, String apellidos, String telefono, String email, String cedula, LocalDate fechaNacimiento, String username, String password, Boolean disponible, Vehiculo vehiculo) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.cedula = cedula;
        this.fechaNacimiento = fechaNacimiento;
        this.username = username;
        this.password = password;
        this.disponible = disponible;
        this.vehiculo = vehiculo;
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean diponible) {
        this.disponible = diponible;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conductor conductor = (Conductor) o;
        return Objects.equals(id, conductor.id) && Objects.equals(telefono, conductor.telefono) && Objects.equals(email, conductor.email) && Objects.equals(cedula, conductor.cedula) && Objects.equals(username, conductor.username) && Objects.equals(vehiculo, conductor.vehiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, telefono, email, cedula, username, vehiculo);
    }

    @Override
    public String toString() {
        return "Conductor{" +
                "vehiculo=" + vehiculo +
                ", diponible=" + disponible +
                ", fechaNacimiento=" + fechaNacimiento +
                ", cedula=" + cedula +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", id=" + id +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                '}';
    }
}
