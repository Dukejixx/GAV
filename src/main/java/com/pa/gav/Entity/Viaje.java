package com.pa.gav.Entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String destino;

    @Enumerated(EnumType.STRING)
    private EstadoViaje estadoViaje;

    @Column(nullable = false)
    private int cantidadPasajeros;

    @Column(nullable = true)
    private double precio;

    @Column(nullable = false)
    private LocalDate fechaSolicitud;

    @ManyToOne
    @JoinColumn(name = "ADMINISTRADOR", nullable = true)
    private Administrador administrador;

    @ManyToOne
    @JoinColumn(name = "CONDUCTOR", nullable = true)
    private Conductor conductor;

    @ManyToOne
    @JoinColumn(name = "CLIENTE", nullable = false)
    private Cliente cliente;

    public enum EstadoViaje {
        SOLICITADO,
        ASIGNADO,
        EN_CURSO,
        COMPLETADO,
        CANCELADO
    }

    public Viaje() {
    }

    public Viaje(Administrador administrador, int cantidadPasajeros, Cliente cliente, Conductor conductor, String destino, EstadoViaje estadoViaje, LocalDate fechaSolicitud, Long id, double precio) {
        this.administrador = administrador;
        this.cantidadPasajeros = cantidadPasajeros;
        this.cliente = cliente;
        this.conductor = conductor;
        this.destino = destino;
        this.estadoViaje = estadoViaje;
        this.fechaSolicitud = fechaSolicitud;
        this.id = id;
        this.precio = precio;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public EstadoViaje getEstadoViaje() {
        return estadoViaje;
    }

    public void setEstadoViaje(EstadoViaje estadoViaje) {
        this.estadoViaje = estadoViaje;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Viaje viaje = (Viaje) o;
        return Objects.equals(id, viaje.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Viaje{" +
                "administrador=" + administrador +
                ", id=" + id +
                ", destino='" + destino + '\'' +
                ", estadoViaje=" + estadoViaje +
                ", cantidadPasajeros=" + cantidadPasajeros +
                ", precio=" + precio +
                ", fechaSolicitud=" + fechaSolicitud +
                ", conductor=" + conductor +
                ", cliente=" + cliente +

                '}';
    }
}
