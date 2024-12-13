package com.pa.gav.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private int capacidadMaxima;

    @Column(nullable = false, unique = true)
    private String placa;

    public Vehiculo() {
    }

    public Vehiculo(Long id, String marca, String modelo, int capacidadMaxima, String placa) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidadMaxima = capacidadMaxima;
        this.placa = placa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(id, vehiculo.id) && Objects.equals(placa, vehiculo.placa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, placa);
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", capacidadMaxima=" + capacidadMaxima +
                ", placa='" + placa + '\'' +
                '}';
    }
}